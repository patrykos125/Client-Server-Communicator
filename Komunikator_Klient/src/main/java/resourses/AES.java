package resourses;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {

    private final int KEY_SIZE = 128;
   private SecretKey key;
   private Cipher cipher;
   private  byte []IV;
     private final int T_LEN =128;



    public AES(){
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(KEY_SIZE);
            cipher=Cipher.getInstance("AES/GCM/NoPadding");
            key= generator.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE,key);
            IV=cipher.getIV();
        }catch (Exception e){e.printStackTrace();}

    }
    public byte [] exportkey(){
        return key.getEncoded();

    }
    public byte [] exportVI(){
        return cipher.getIV();

    }
    public void initFromStrings(String secretKey,String IV){
        this.key= new SecretKeySpec(Base64.getDecoder().decode(secretKey),"AES");
        this.IV = Base64.getDecoder().decode(IV);

    }
    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }






}
