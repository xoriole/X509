package x509;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

public class SHAHashingExample {

  public static void main(String[] args) throws Exception {
    String password = "123456";

    MessageDigest md = MessageDigest.getInstance("SHA1");
    md.update(password.getBytes());

    byte byteData[] = md.digest();

    BASE64Encoder encoder = new BASE64Encoder();
    String base64CheckSum = encoder.encode(byteData);
    System.out.println("base64:" + base64CheckSum);

    //convert the byte to hex format method 1
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < byteData.length; i++) {
      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }
    System.out.println("Hex format : " + sb.toString());

  }
}
