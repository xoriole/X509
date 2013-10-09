/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package x509;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author phoenix
 */
public class RSAExaple {

  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, SignatureException, IOException {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(2048);
    KeyPair kp = keyGen.genKeyPair();

    PublicKey publicKey = kp.getPublic();
    PrivateKey privateKey = kp.getPrivate();

    String text = "Testmessage";
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] x = cipher.doFinal(text.getBytes());

    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] y = cipher.doFinal(x);

    //BASE64Encoder encoder = new BASE64Encoder();
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] message = "hello world".getBytes("UTF-8");
//    String message = "hello cipher";
    String sign = "dLAm0u9T8yk1LGEaLqCDpRA1T5J2pxwfdesARtw58+zHxlehKEmPtPTP49MYl9lL9YsrCC+zP7qDQWUHnXdQrDrmMJqPL+cedlf41TOj7Mwe8AzKHx/h1v428mzLdNJ0JfU/Xc78hHOVIW5lRnQiiOx2qoXBHFV+yE3JuzOXWKXrTcxGm9piC0KhTU3dfhytk+Muqo391HABn5xZqTER6SznuIqO5XoRZhzKpPoJpc/R0kYeNxOdI0Ot17bTakY39oBbMVZqV5VUBCCb92SafgLw+8IkxOQtbj7aOZ4AlHQJqZAoS/FOWoPlf0h1ZtZwVn4AsgHUUIHYCpLgO25Lfw==";
    String data = "II1234567Test000XMLPrescriber10dot6 MU000 Pending Response CtArlingtonVA22202WhiteSideKara23230 SeaportAkronOH44306Ambien 10 mg tablet10Take one tablet once a day after every meal. Do not take on empty stomach2013091920130919R0This is a schedule CIV medication";
    String digest = "y3qkZYgfRVo2Sv1F9bHa3pDs044=";

    Signature signature = Signature.getInstance("SHA1withRSA");
    signature.initSign(privateKey);
    signature.update(data.getBytes("UTF-8"));
    byte[] signatureValue = signature.sign();
    BASE64Encoder encoder = new BASE64Encoder();
    String encode = encoder.encode(signatureValue);
    System.out.println("signature:" + encode);

    Signature signature2 = Signature.getInstance("SHA1withRSA");
    signature2.initVerify(publicKey);
    signature2.update(data.getBytes("UTF-8"));
    boolean ok = signature2.verify(decoder.decodeBuffer(encode));
    System.out.println("ok:" + ok);
  }
}
