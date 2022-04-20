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
    public byte[] encryptKey(byte[] toEncrypt,PublicKey publicKey)throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte [] encryptedBytes = cipher.doFinal(toEncrypt);
        return encryptedBytes;
    }




    public KeyPair getKey(){
        KeyPair keyPair=null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
             keyPair = keyPairGenerator.generateKeyPair();

        }catch(Exception e){e.printStackTrace();}
        return keyPair;
    }



    public static void main(String[] args) {


        try{
            String sting="dupa";
            byte [] bity=Base64.getDecoder().decode(sting);
            String odkodowane = Base64.getEncoder().encodeToString(bity);
            System.out.println(odkodowane);



        }
        catch (Exception e){e.printStackTrace();}
    }

}
