/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import sun.misc.BASE64Decoder;
import x509.implementation.CRLVerifier;

/**
 *
 * @author phoenix
 */
public class Main {

  public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
    String certString = "MIIFhDCCBGygAwIBAgIKGkGk4AAAAAAAUDANBgkqhkiG9w0BAQsFADCBjjELMAkGA1UEBhMCVVMxGTAXBgNVBAoTEFN1cmVzY3JpcHRzIExMQy4xLjAsBgNVBAsTJVN1cmVzY3JpcHRzIENlcnRpZmljYXRpb24gQXV0aG9yaXRpZXMxNDAyBgNVBAMTK1N1cmVzY3JpcHRzIElzc3VpbmcgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTMwNjE5MTkyMjIzWhcNMTUwNjE5MTkyMjIzWjCBqTELMAkGA1UEBhMCVVMxEjAQBgNVBAgTCU1pbm5lc290YTEUMBIGA1UEBxMLTWlubmVhcG9saXMxFDASBgNVBAoTC1N1cmVzY3JpcHRzMRQwEgYDVQQLEwtEZXZlbG9wbWVudDEZMBcGA1UEAxMQVGVzdFN1aXRlMS12YWxpZDEpMCcGCSqGSIb3DQEJARYaamltLm1hcnRpbkBzdXJlc2NyaXB0cy5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDbKFXQf50to6SEMbpWnttjrddJ69ukPMiDLosLRWe9XHL+Xf6AQFfPwKwpfvANIp6rPyBvDMBF5sc4VfjFBONrsP1hwLAiVKp2Dx6BjuS7tYmPKHI/zAvwx6BoPm4mHKlr66+FNRA96ZNrycw635lgjvSDf/ZnHT/VtWqk0tXAyNFpLOnD1lV53S8FrdIrHllJ29wodYf1sBKo3paaYpXU9RXT4pOcFqyZy/gYrbEDHbrsPO+Sx+Medk77Wg1ARDp/yUowo4b8nUslO5LvWaW+ptcmOvKWmyG7V86Qh6itAoNGMbOphQVtgB1SO4jkKTD99hj8sxdoQsZKbmjhhKnBAgMBAAGjggHFMIIBwTAOBgNVHQ8BAf8EBAMCBaAwPgYJKwYBBAGCNxUHBDEwLwYnKwYBBAGCNxUIhND4T4Pe/zSH/Zksgs+weYWnjAuBfYXKyRiG1uBIAgFkAgEHMB0GA1UdDgQWBBTYG1zC+KpsL8PkTYTEWeWJLzk+lDAfBgNVHSMEGDAWgBRUkp4XGGrE3wF7f0vzTnl/B9TzTjBIBgNVHR8EQTA/MD2gO6A5hjdodHRwOi8vcGtpLnN1cmVzY3JpcHRzLm5ldC9yZXBvc2l0b3J5L1N1cmVzY3JpcHRzQ0EuY3JsMIGABggrBgEFBQcBAQR0MHIwQwYIKwYBBQUHMAKGN2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L3JlcG9zaXRvcnkvU3VyZXNjcmlwdHNDQS5jcnQwKwYIKwYBBQUHMAGGH2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L29jc3AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMBoGA1UdIAQTMBEwDwYNKwYBBAGCpU+DfTIBAzAnBgkrBgEEAYI3FQoEGjAYMAoGCCsGAQUFBwMCMAoGCCsGAQUFBwMBMA0GCSqGSIb3DQEBCwUAA4IBAQBI0dqxaiDh7ogAAPMN5YAFhAC9m58MLSy/y517S8tdOOX7VwLBZbQhSpco+eJW4eClh0NJWvqsrfuXbmo/lEUxd0QbgxUyFfCy3ObAp/yOohlZmDsyqEG1R3VFcy9Nr2pxQXdJvd3LsZZGyHuZ3n+GNv0v4qK3dRONeixHtH4tlvMmChTSuCUItPLzq0UmTlgFAch1hvwoCu617wLMTmCGAHZ7NsKWHORkBBVUSBEkGmRZBZDUBPcOLBP7Dv6twCN9T5C46gmKVTbgxcwUDITRwDTfPfW0fjTMlftcQ4H7HPl5ehdj/7DK3bT17TBQDTZ5ET84GeHoNq1NiLpXhLuc";
    String expiredCert = "MIIFfjCCBGagAwIBAgIKGjxNAQAAAAAATjANBgkqhkiG9w0BAQsFADCBjjELMAkGA1UEBhMCVVMxGTAXBgNVBAoTEFN1cmVzY3JpcHRzIExMQy4xLjAsBgNVBAsTJVN1cmVzY3JpcHRzIENlcnRpZmljYXRpb24gQXV0aG9yaXRpZXMxNDAyBgNVBAMTK1N1cmVzY3JpcHRzIElzc3VpbmcgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTMwNjE5MTkxNjMzWhcNMTMwNjIwMTkxNjMzWjCBpDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAm1uMRQwEgYDVQQHEwtNaW5uZWFwb2xpczEUMBIGA1UEChMLU3VyZXNjcmlwdHMxFDASBgNVBAsTC0RldmVsb3BtZW50MRswGQYDVQQDExJUZXN0U3VpdGUxLWV4cGlyZWQxKTAnBgkqhkiG9w0BCQEWGmppbS5tYXJ0aW5Ac3VyZXNjcmlwdHMuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt/JF0drYKpL00iHUgNIdAaupLvxNKqj6ami0jYYvfVWw7DMXNgj9pq1cxjGwS2f2mTuMk5PfoZvE7BLzjwgbXKOvBmnUde63a4kDXkDbi04wQ3b8H2O4+FZQDnwkuIKVqd2JsxMCfm8MmBThL3lISJzkModeO5GxN1XqmEOfvGqBwHE/GzyOs9klU3Pj8b4HsMUziMA1jIVHZkNn6bN9R7uQUR9bMOTAQ5Yyr1BBwOweUuxiydvRLJD5jBcFiTNjJ7sPQyMFugunctM7C0jN1pWz49IuT1ofe6/rD4jCmf5ANcP8GCEtzrOu919QqtGUzuhSGzkcnUaCLVAiVRBUjQIDAQABo4IBxDCCAcAwDgYDVR0PAQH/BAQDAgWgMD0GCSsGAQQBgjcVBwQwMC4GJisGAQQBgjcVCITQ+E+D3v80h/2ZLILPsHmFp4wLgX229FaGmvZKAgFkAgEJMB0GA1UdDgQWBBT2QlD/r5TkeDRF12vzR8jgZPskjjAfBgNVHSMEGDAWgBRUkp4XGGrE3wF7f0vzTnl/B9TzTjBIBgNVHR8EQTA/MD2gO6A5hjdodHRwOi8vcGtpLnN1cmVzY3JpcHRzLm5ldC9yZXBvc2l0b3J5L1N1cmVzY3JpcHRzQ0EuY3JsMIGABggrBgEFBQcBAQR0MHIwQwYIKwYBBQUHMAKGN2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L3JlcG9zaXRvcnkvU3VyZXNjcmlwdHNDQS5jcnQwKwYIKwYBBQUHMAGGH2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L29jc3AwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMBoGA1UdIAQTMBEwDwYNKwYBBAGCpU+DfTIBAzAnBgkrBgEEAYI3FQoEGjAYMAoGCCsGAQUFBwMBMAoGCCsGAQUFBwMCMA0GCSqGSIb3DQEBCwUAA4IBAQA+m9cxmmIFw2e+xyX2lZ2fPiq6+Qf1qsdWVsNrfRWml3Z7EONfm9G4rvCUST9XV/21b8cGjNwGOefd90R2GK1UB6bSWwexUqTQfh4OlaXaOr4IiHRNVxutesJ99NCkUyIZE2uUk8pcF02vldkoD6KFT7CaYrXjanGJ7StsoiHo0uxx6Hk9YRoThdeU/Bsh4IQnm1R6if0PFlTxRHXt4cu8ruWZlCz0h/dBwL3i4EAwvfXbokIH4DgfAfeKkEVb2XUKanOZwIQhmjVD0EhNg/t9adDWwkJ+pVrMGvOJ7cL/Edt0nMfgvhB+U9KRRSD4RziJr20Zb1TayRh0gfVuvNmF";
    String revokedCert = "MIIFhjCCBG6gAwIBAgIKGkN7AwAAAAAAUTANBgkqhkiG9w0BAQsFADCBjjELMAkGA1UEBhMCVVMxGTAXBgNVBAoTEFN1cmVzY3JpcHRzIExMQy4xLjAsBgNVBAsTJVN1cmVzY3JpcHRzIENlcnRpZmljYXRpb24gQXV0aG9yaXRpZXMxNDAyBgNVBAMTK1N1cmVzY3JpcHRzIElzc3VpbmcgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTMwNjE5MTkyNDIzWhcNMTUwNjE5MTkyNDIzWjCBqzELMAkGA1UEBhMCVVMxEjAQBgNVBAgTCU1pbm5lc290YTEUMBIGA1UEBxMLTWlubmVhcG9saXMxFDASBgNVBAoTC1N1cmVzY3JpcHRzMRQwEgYDVQQLEwtEZXZlbG9wbWVudDEbMBkGA1UEAxMSVGVzdFN1aXRlMS1yZXZva2VkMSkwJwYJKoZIhvcNAQkBFhpqaW0ubWFydGluQHN1cmVzY3JpcHRzLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJKOkuOhG7zHbBDuIzgMm1CcWeS85j9r8Vatl+zmPQNQyvR+eQqHm/nm7oYdCNXbbV79jal/ObAoebRByR2PmMbdgcYXbO4I2ptJW1NZYoWUck8Dg7S8mx77zFTakO0lCx4KzseeQbx+KDaZoY4XPgoYNAonwGN1sep6jVvSJ5FP04A5QL8VgJXFk7WAHGqIarS6AdLrodUTlpdWVfREabqYyupkt8XomRpnHz+ALI+KtNddeFu29+yxUIoGgYXPdk0of9rBP1UtroxP2NHYn7duf637XugeYH1sWQ0dhLBBzmeQnTYOv2L4UsxATj5I76KIWKEX0Jrcj5P4b5JjIgUCAwEAAaOCAcUwggHBMA4GA1UdDwEB/wQEAwIFoDA+BgkrBgEEAYI3FQcEMTAvBicrBgEEAYI3FQiE0PhPg97/NIf9mSyCz7B5haeMC4F9hcrJGIbW4EgCAWQCAQcwHQYDVR0OBBYEFOHWSq2vrbgogStTXpJrpiiBaio1MB8GA1UdIwQYMBaAFFSSnhcYasTfAXt/S/NOeX8H1PNOMEgGA1UdHwRBMD8wPaA7oDmGN2h0dHA6Ly9wa2kuc3VyZXNjcmlwdHMubmV0L3JlcG9zaXRvcnkvU3VyZXNjcmlwdHNDQS5jcmwwgYAGCCsGAQUFBwEBBHQwcjBDBggrBgEFBQcwAoY3aHR0cDovL3BraS5zdXJlc2NyaXB0cy5uZXQvcmVwb3NpdG9yeS9TdXJlc2NyaXB0c0NBLmNydDArBggrBgEFBQcwAYYfaHR0cDovL3BraS5zdXJlc2NyaXB0cy5uZXQvb2NzcDAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwEwGgYDVR0gBBMwETAPBg0rBgEEAYKlT4N9MgEDMCcGCSsGAQQBgjcVCgQaMBgwCgYIKwYBBQUHAwIwCgYIKwYBBQUHAwEwDQYJKoZIhvcNAQELBQADggEBAJqs+HW6tthBDMEuFVzD4rdSeP1gDzGkXZrVCrd873gNwM47pEfzMU6qYSTdXoXAutUHOYRO6qHsPWu8d2yv3jWAENT+BbcfQpZoloGkhLtUJyqWocAEOqagzvbUg+SaLZuZgEiSRUrhdf5ngM5D4h6Sxp1tlx0B5/4Em2rUV6fAjBOY2lJJ/px90ScHmxnRQ9EE221T3Gth+exk5/JbrMkHKAyOWrglZaGh/nshG0jCJu/L4T4o3UIUtsNXxuX1v9q406qg+fJnqmpRDrm61a+vUR+zX2l2t2f46o1/XmaAB/V6VplVAqTfHwKHmBtQvNlGio5/wah6RgnvddWGcao=";
    String selfSignedCert = "MIIEdzCCA1+gAwIBAgIJAMP9V08ObuZAMA0GCSqGSIb3DQEBBQUAMIGDMQswCQYDVQQGEwJVUzESMBAGA1UECBMJTWlubmVzb3RhMRQwEgYDVQQHEwtNaW5uZWFwb2xpczEUMBIGA1UEChMLU3VyZXNjcmlwdHMxFDASBgNVBAsTC0RldmVsb3BtZW50MR4wHAYDVQQDExVUZXN0U3VpdGUxLXNlbGZzaWduZWQwHhcNMTIwMTE3MjA1OTI4WhcNMTQwMTE2MjA1OTI4WjCBgzELMAkGA1UEBhMCVVMxEjAQBgNVBAgTCU1pbm5lc290YTEUMBIGA1UEBxMLTWlubmVhcG9saXMxFDASBgNVBAoTC1N1cmVzY3JpcHRzMRQwEgYDVQQLEwtEZXZlbG9wbWVudDEeMBwGA1UEAxMVVGVzdFN1aXRlMS1zZWxmc2lnbmVkMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1HmqFtDIpBNk+If5NtWnMeMcgycTX78A0qRpC9wEcST5ucrRUSup6Xn/9a+xmgClPyEENlZLJYYHT4hWVuUtBuU30wuBaKem9ynE48WDSoPq8+xdvP8lI35vUFkT2a1KlAUi2UU79rOYe2nBKkcX3oQjiQk7ge910beAljFkr2XzBqiz4SLMOkOuG4INp1z4tttQzHZ8xi5uInhNu2PJv2UlKMdGx1r5js5GiEmibFxOvXL/E76GcHah7nIRtpOAMecm5u28pIhpcTAW4LQawdUuIv427CW0/qCJnd7rV8aOp74QZW6NavgVPql7w/EncDN9oM7gTQoCH8pCmkq0IQIDAQABo4HrMIHoMB0GA1UdDgQWBBTEBUc8aKRkk+t9OPe6FQtYIcIHtTCBuAYDVR0jBIGwMIGtgBTEBUc8aKRkk+t9OPe6FQtYIcIHtaGBiaSBhjCBgzELMAkGA1UEBhMCVVMxEjAQBgNVBAgTCU1pbm5lc290YTEUMBIGA1UEBxMLTWlubmVhcG9saXMxFDASBgNVBAoTC1N1cmVzY3JpcHRzMRQwEgYDVQQLEwtEZXZlbG9wbWVudDEeMBwGA1UEAxMVVGVzdFN1aXRlMS1zZWxmc2lnbmVkggkAw/1XTw5u5kAwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQUFAAOCAQEAL745oJeFoTs2IQX0dx99d4MTRIyDVZj3QwgC5ak4hn3S7G/tSKl4bNOb/5YCUDIA3Xnhljm7jvcMGGyMOhYq/vxsjtcv8XMRj9x5vVYL1/4/IYN24MnvmQs/TbjlfmVGQdgFIIoCwGb6YaClp7P18GIBiAaSLdCSTrjM0JKx9ESKS9RJ1YpzvNAcZhmAfdujUqUrQJTuEHQfNNKNOgf5kXBSTQ9AnMaRj2yrLc9PbcGx3cDgO2zRQqXoyR3Y49R0R+IXEtigdDrpN4TVRmYi1XCcWM9urrUAp3qAI9Dl31Xic92t4bhK0rtAeHFwyBn/J3VbrGvO+vZe88UHZm6l6w==";
    BASE64Decoder decoder = new BASE64Decoder();
    InputStream is = new ByteArrayInputStream(decoder.decodeBuffer(certString));
    //InputStream is = new ByteArrayInputStream(decoder.decodeBuffer(expiredCert));
    //InputStream is = new ByteArrayInputStream(decoder.decodeBuffer(revokedCert));
    //InputStream is = new ByteArrayInputStream(decoder.decodeBuffer(selfSignedCert));
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    Certificate certificate = cf.generateCertificate(is);
    X509Certificate xcert = (X509Certificate) certificate;

    System.out.println("expired:" + CRLVerifier.isExpired(xcert));
    System.out.println("self signed:" + CRLVerifier.isSelfSigned(xcert));
    System.out.println("revoked:" + CRLVerifier.isRevoked(xcert));
  }

  public static boolean isExpired2(X509Certificate cert) {
    try {
      cert.checkValidity();
      return false;
    } catch (CertificateExpiredException e) {
      System.out.println("Certificate Expired");
      return true;
    } catch (CertificateNotYetValidException e) {
      System.out.println("Certificate Not Yet Valid");
      return true;
    } catch (Exception e) {
      System.out.println("Error checking Certificate Validity.  See admin.");
      return true;
    }
  }
}
