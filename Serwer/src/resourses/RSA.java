package resourses;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {


    public RSA (){

    }
    public String encrypt (String message,PublicKey publicKey) throws Exception{
        byte[] messgeToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte [] encryptedBytes = cipher.doFinal(messgeToBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public String decrypt(String encryptedMessage,PrivateKey privateKey) throws Exception {
        byte [] encryptedBytes = Base64.getDecoder().decode (encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return  new String(decryptedMessage,"UTF8");
    }










}
