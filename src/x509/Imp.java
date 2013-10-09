/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author phoenix
 */
public class Imp {

  /**
   * Encrypt the plain text using public key.
   *
   * @param text : original plain text
   * @param key :The public key
   * @return Encrypted text
   * @throws java.lang.Exception
   */
  public static byte[] encrypt(String text, PublicKey key) {
    byte[] cipherText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance("RSA");
      // encrypt the plain text using the public key
      cipher.init(Cipher.ENCRYPT_MODE, key);
      cipherText = cipher.doFinal(text.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

  /**
   * Decrypt text using private key.
   *
   * @param text :encrypted text
   * @param key :The private key
   * @return plain text
   * @throws java.lang.Exception
   */
  public static String decrypt(byte[] text, PrivateKey key) {
    byte[] dectyptedText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance("RSA");

      // decrypt the text using the private key
      cipher.init(Cipher.DECRYPT_MODE, key);
      dectyptedText = cipher.doFinal(text);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return new String(dectyptedText);
  }
  /*
   public static void main(String[] args) {

   try {
   String userID = "189711";
   String companyCode = "ILIKEPIZZA";
   String combine = userID + "." + companyCode;
   String mod = "q0AwozeUj0VVkoksDQSCTj3QEgODomq4sAr02xMyIrWldZrNHhWfZAIcWt2MuAY3X6S3ZVUfOFXOrVbltRrO3F9Z6R8/jJIMv7wjkeVBFC5gncwGR0C3aV9gmF6II19jTKfF1sxb26iMEMAlMEOSnAAceNaJH91zBoaW7ZIh+qk=";
   String exp = "AQAB";
   byte[] modulusBytes = Base64.decode(mod.getBytes("UTF-8"));
   byte[] exponentBytes = Base64.decode(exp.getBytes("UTF-8"));
   String sign = "3753e672cfb21e3c182ef2df51f19edeffb63432ed338a47251326ccc14aa63883e910a140cf313754ebc6425aad434e309307cc882da6cd4a4f9f40bd14a9823aca145e5ffc97cd63dbb5925c049282416bdfd7d74ddeef7055065210a841793fe315dff5a44af19c1522daafdc2f7e61ce5a2b42ebf79dfb086e6d210168dd";
   BigInteger modulus = new BigInteger(1, modulusBytes);
   BigInteger exponent = new BigInteger(1, exponentBytes);
   RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
   KeyFactory fact = KeyFactory.getInstance("RSA");
   PublicKey pubKey = fact.generatePublic(rsaPubKey);


   Signature signature = Signature.getInstance("SHA1withRSA");
   //      byte[] sigBytes = hexStringToByteArray(sign);
   signature.initVerify(pubKey);
   signature.update(combine.getBytes("UTF-8"));
   System.out.println(signature.verify(signature.sign()));
   } catch (Exception e) {
   System.out.println("Error: " + e.toString());
   }
   }*/

  public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    String certString = "MIIFhDCCBGygAwIBAgIKGkGk4AAAAAAAUDANBgkqhkiG9w0BAQsFADCBjjELMAkGA1UEBhMCVVMxGTAXBgNVBAoTEFN1cmVzY3JpcHRzIExMQy4xLjAsBgNVBAsTJVN1cmVzY3JpcHRzIENlcnRpZmljYXRpb24gQXV0aG9yaXRpZXMxNDAyBgNVBAMTK1N1cmVzY3JpcHRzIElzc3VpbmcgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTMwNjE5MTkyMjIzWhcNMTUwNjE5MTkyMjIzWjCBqTELMAkGA1UEBhMCVVMxEjAQBgNVBAgTCU1pbm5lc290YTEUMBIGA1UEBxMLTWlubmVhcG9saXMxFDASBgNVBAoTC1N1cmVzY3JpcHRzMRQwEgYDVQQLEwtEZXZlbG9wbWVudDEZMBcGA1UEAxMQVGVzdFN1aXRlMS12YWxpZDEpMCcGCSqGSIb3DQEJARYaamltLm1hcnRpbkBzdXJlc2NyaXB0cy5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDbKFXQf50to6SEMbpWnttjrddJ69ukPMiDLosLRWe9XHL+Xf6AQFfPwKwpfvANIp6rPyBvDMBF5sc4VfjFBONrsP1hwLAiVKp2Dx6BjuS7tYmPKHI/zAvwx6BoPm4mHKlr66+FNRA96ZNrycw635lgjvSDf/ZnHT/VtWqk0tXAyNFpLOnD1lV53S8FrdIrHllJ29wodYf1sBKo3paaYpXU9RXT4pOcFqyZy/gYrbEDHbrsPO+Sx+Medk77Wg1ARDp/yUowo4b8nUslO5LvWaW+ptcmOvKWmyG7V86Qh6itAoNGMbOphQVtgB1SO4jkKTD99hj8sxdoQsZKbmjhhKnBAgMBAAGjggHFMIIBwTAOBgNVHQ8BAf8EBAMCBaAwPgYJKwYBBAGCNxUHBDEwLwYnKwYBBAGCNxUIhND4T4Pe/zSH/Zksgs+weYWnjAuBfYXKyRiG1uBIAgFkAgEHMB0GA1UdDgQWBBTYG1zC+KpsL8PkTYTEWeWJLzk+lDAfBgNVHSMEGDAWgBRUkp4XGGrE3wF7f0vzTnl/B9TzTjBIBgNVHR8EQTA/MD2gO6A5hjdodHRwOi8vcGtpLnN1cmVzY3JpcHRzLm5ldC9yZXBvc2l0b3J5L1N1cmVzY3JpcHRzQ0EuY3JsMIGABggrBgEFBQcBAQR0MHIwQwYIKwYBBQUHMAKGN2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L3JlcG9zaXRvcnkvU3VyZXNjcmlwdHNDQS5jcnQwKwYIKwYBBQUHMAGGH2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L29jc3AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMBoGA1UdIAQTMBEwDwYNKwYBBAGCpU+DfTIBAzAnBgkrBgEEAYI3FQoEGjAYMAoGCCsGAQUFBwMCMAoGCCsGAQUFBwMBMA0GCSqGSIb3DQEBCwUAA4IBAQBI0dqxaiDh7ogAAPMN5YAFhAC9m58MLSy/y517S8tdOOX7VwLBZbQhSpco+eJW4eClh0NJWvqsrfuXbmo/lEUxd0QbgxUyFfCy3ObAp/yOohlZmDsyqEG1R3VFcy9Nr2pxQXdJvd3LsZZGyHuZ3n+GNv0v4qK3dRONeixHtH4tlvMmChTSuCUItPLzq0UmTlgFAch1hvwoCu617wLMTmCGAHZ7NsKWHORkBBVUSBEkGmRZBZDUBPcOLBP7Dv6twCN9T5C46gmKVTbgxcwUDITRwDTfPfW0fjTMlftcQ4H7HPl5ehdj/7DK3bT17TBQDTZ5ET84GeHoNq1NiLpXhLuc";
    String sign = "dLAm0u9T8yk1LGEaLqCDpRA1T5J2pxwfdesARtw58+zHxlehKEmPtPTP49MYl9lL9YsrCC+zP7qDQWUHnXdQrDrmMJqPL+cedlf41TOj7Mwe8AzKHx/h1v428mzLdNJ0JfU/Xc78hHOVIW5lRnQiiOx2qoXBHFV+yE3JuzOXWKXrTcxGm9piC0KhTU3dfhytk+Muqo391HABn5xZqTER6SznuIqO5XoRZhzKpPoJpc/R0kYeNxOdI0Ot17bTakY39oBbMVZqV5VUBCCb92SafgLw+8IkxOQtbj7aOZ4AlHQJqZAoS/FOWoPlf0h1ZtZwVn4AsgHUUIHYCpLgO25Lfw==";
    String data = "II1234567Test000XMLPrescriber10dot6 MU000 Pending Response CtArlingtonVA22202WhiteSideKara23230 SeaportAkronOH44306Ambien 10 mg tablet10Take one tablet once a day after every meal. Do not take on empty stomach2013091920130919R0This is a schedule CIV medication";
    String data2 = "II1234567Test000XMLPrescriber10dot6 MU000 Pending Response CtArlingtonVA22202WhiteSideKara23230 SeaportAkronOH44306Ambien 10 mg tablet10Take one tablet once a day after every meal. Do not take on empty stomach2013091920130919R0This is a schedule CIV medication";
    String digest = "y3qkZYgfRVo2Sv1F9bHa3pDs044=";
    BASE64Decoder decoder = new BASE64Decoder();
    InputStream is = new ByteArrayInputStream(decoder.decodeBuffer(certString));
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    Certificate certificate = cf.generateCertificate(is);
    X509Certificate xcert = (X509Certificate) certificate;
    //System.out.println("public key:" + xcert.getPublicKey());

    String givenHashValue = "y3qkZYgfRVo2Sv1F9bHa3pDs044=";
    System.out.println("hash:" + getHash(data));
    System.out.println("hash:" + givenHashValue);

    Signature signature = Signature.getInstance("SHA1withRSA");
    signature.initVerify(xcert.getPublicKey());
    signature.update(data.getBytes("UTF-8"));
    boolean verify = signature.verify(decoder.decodeBuffer(sign));
    System.out.println("verified:" + verify);
    //    signer.initSign(privateKe);
    //    signer.update(message);
    //    byte[] signature = signer.sign();
  }

  public static String getHash(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("SHA1");
    md.update(data.getBytes("UTF-8"));

    byte byteData[] = md.digest();

    BASE64Encoder encoder = new BASE64Encoder();
    String base64CheckSum = encoder.encode(byteData);
    //System.out.println("base64:" + base64CheckSum);
    return base64CheckSum;
  }
}
