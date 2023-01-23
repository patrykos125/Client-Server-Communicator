package resourses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    @Test
    void RSA_ecrypt() throws Exception {

        String message ="ptaki lataja kluczem";
        RSA sut= new RSA();
        String encrypted = sut.encrypt(message);


        assertNotEquals(message,encrypted);
    }
    @Test
    void RSA_decrypt() throws Exception {

        String message ="ptaki lataja kluczem";
        RSA sut= new RSA();
        String encrypted = sut.encrypt(message);
        String decrypt = sut.decrypt(encrypted);


        assertEquals(message,decrypt);
    }
    @Test
    void RSA_decrypt_digits() throws Exception {

        String message ="215487945345658542";
        RSA sut= new RSA();
        String encrypted = sut.encrypt(message);
        String decrypt = sut.decrypt(encrypted);


        assertEquals(message,decrypt);
    }   @Test

    void RSA_decrypt_random_test() throws Exception {

        String message ="8y/B?E(H+MbPeShV";
        RSA sut= new RSA();
        String encrypted = sut.encrypt(message);
        String decrypt = sut.decrypt(encrypted);


        assertEquals(message,decrypt);
    }
    @Test
    void RSA_decrypt_special_characters() throws Exception {

        String message ="!>+_+.,:;-&&.<(.<&+!-*;&,)$-+?>*>?_-:?<*#&)>_'',;&_;.$<!'>!+_$#<+$>!?-.,'&#'!(!!;_(_!-(*(!:-_:)%_,'!?.++<(#?*(&:%.;.$;(:,";
        RSA sut= new RSA();
        String encrypted = sut.encrypt(message);
        String decrypt = sut.decrypt(encrypted);


        assertEquals(message,decrypt);
    }


}