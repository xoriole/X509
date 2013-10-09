package x509.implementation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/**
 * Class that verifies CRLs for given X509 certificate. Extracts the CRL distribution points from
 * the certificate (if available) and checks the certificate revocation status against the CRLs
 * coming from the distribution points. Supports HTTP, HTTPS, FTP and LDAP based URLs.
 *
 * @author Svetlin Nakov
 */
public class CRLVerifier {

  /**
   *
   * Checks whether given X.509 certificate is expired or not yet valid
   *
   */
  public static boolean isExpired(X509Certificate cert) {
    try {
      cert.checkValidity();
      return false;
    } catch (CertificateExpiredException e) {
      //System.out.println("Certificate Expired");
      return true;
    } catch (CertificateNotYetValidException e) {
      //System.out.println("Certificate Not Yet Valid");
      return true;
    } catch (Exception e) {
      //System.out.println("Error checking Certificate Validity.  See admin.");
      return true;
    }
  }

  /**
   *
   * Checks whether given X.509 certificate is revoked
   *
   */
  public static boolean isRevoked(X509Certificate cert) {
    try {
      verifyCertificateCRLs(cert);
      return false;
    } catch (CertificateVerificationException cve) {
      return true;
    }
  }

  /**
   *
   * Checks whether given X.509 certificate is self-signed.
   *
   */
  public static boolean isSelfSigned(X509Certificate cert)
          throws CertificateException, NoSuchAlgorithmException,
          NoSuchProviderException {
    try {
      // Try to verify certificate signature with its own public key
      PublicKey key = cert.getPublicKey();
      cert.verify(key);
      return true;
    } catch (SignatureException sigEx) {
      // Invalid signature --> not self-signed
      return false;

    } catch (InvalidKeyException keyEx) {
      // Invalid key --> not self-signed
      return false;
    }
  }

  /**
   * Extracts the CRL distribution points from the certificate (if available) and checks the
   * certificate revocation status against the CRLs coming from the distribution points. Supports
   * HTTP, HTTPS, FTP and LDAP based URLs.
   *
   * @param cert the certificate to be checked for revocation
   * @throws CertificateVerificationException if the certificate is revoked
   */
  public static void verifyCertificateCRLs(X509Certificate cert)
          throws CertificateVerificationException {
    try {
      List<String> crlDistPoints = getCrlDistributionPoints(cert);
      for (String crlDP : crlDistPoints) {
        X509CRL crl = downloadCRL(crlDP);
        if (crl.isRevoked(cert)) {
          throw new CertificateVerificationException(
                  "The certificate is revoked by CRL: " + crlDP);
        }
      }
    } catch (Exception ex) {
      if (ex instanceof CertificateVerificationException) {
        throw (CertificateVerificationException) ex;
      } else {
        throw new CertificateVerificationException(
                "Can not verify CRL for certificate: "
                + cert.getSubjectX500Principal());
      }
    }
  }

  /**
   * Downloads CRL from given URL. Supports http, https, ftp and ldap based URLs.
   */
  private static X509CRL downloadCRL(String crlURL) throws IOException,
          CertificateException, CRLException,
          CertificateVerificationException, NamingException {
    if (crlURL.startsWith("http://") || crlURL.startsWith("https://")
            || crlURL.startsWith("ftp://")) {
      X509CRL crl = downloadCRLFromWeb(crlURL);
      return crl;
    } else if (crlURL.startsWith("ldap://")) {
      X509CRL crl = downloadCRLFromLDAP(crlURL);
      return crl;
    } else {
      throw new CertificateVerificationException(
              "Can not download CRL from certificate "
              + "distribution point: " + crlURL);
    }
  }

  /**
   * Downloads a CRL from given LDAP url, e.g.
   * ldap://ldap.infonotary.com/dc=identity-ca,dc=infonotary,dc=com
   */
  private static X509CRL downloadCRLFromLDAP(String ldapURL)
          throws CertificateException, NamingException, CRLException,
          CertificateVerificationException {
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, ldapURL);

    DirContext ctx = new InitialDirContext(env);
    Attributes avals = ctx.getAttributes("");
    Attribute aval = avals.get("certificateRevocationList;binary");
    byte[] val = (byte[]) aval.get();
    if ((val == null) || (val.length == 0)) {
      throw new CertificateVerificationException(
              "Can not download CRL from: " + ldapURL);
    } else {
      InputStream inStream = new ByteArrayInputStream(val);
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509CRL crl = (X509CRL) cf.generateCRL(inStream);
      return crl;
    }
  }

  /**
   * Downloads a CRL from given HTTP/HTTPS/FTP URL, e.g.
   * http://crl.infonotary.com/crl/identity-ca.crl
   */
  private static X509CRL downloadCRLFromWeb(String crlURL)
          throws MalformedURLException, IOException, CertificateException,
          CRLException {
    URL url = new URL(crlURL);
    InputStream crlStream = url.openStream();
    try {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509CRL crl = (X509CRL) cf.generateCRL(crlStream);
      return crl;
    } finally {
      crlStream.close();
    }
  }

  /**
   * Gets a list of URLs from the specified certificate.
   *
   * @param cert The certificate to find the URLs in.
   * @return A list of CRL URLs in the certificate
   */
  public static List<String> getCrlDistributionPoints(X509Certificate cert) {
//    List<URL> urls = new LinkedList<URL>();
    List<String> urls = new LinkedList<String>();

    // Retrieves the raw ASN1 data of the CRL Dist Points X509 extension
    byte[] cdp = cert.getExtensionValue(X509Extensions.CRLDistributionPoints.getId());
    if (cdp != null) {
      try {
        // Wraps the raw data in a container class
        CRLDistPoint crldp = CRLDistPoint.getInstance(X509ExtensionUtil.fromExtensionValue(cdp));

        DistributionPoint[] distPoints = crldp.getDistributionPoints();

        for (DistributionPoint dp : distPoints) {
          // Only use the "General name" data in the distribution point entry.
          GeneralNames gns = (GeneralNames) dp.getDistributionPoint().getName();

          for (GeneralName name : gns.getNames()) {
            // Only retrieve URLs
            if (name.getTagNo() == GeneralName.uniformResourceIdentifier) {
              //System.out.println("name:" + name.getName());
              //urls.add(new URL(name.getName().toString()));
              urls.add(name.getName().toString());
//              DERString s = (DERString) name.getName();
//              urls.add(new URL(s.getString()));
            }
          }
        }
      } catch (IOException e) {
        // Could not retrieve the CRLDistPoint object. Just return empty url list.
      }
    }

    return urls;
  }
}
