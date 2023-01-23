package resourses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

    @Test
    void AES_encrypt() throws Exception {

        String message = "ptaki lataja kluczami";
        AES sut= new AES();
        String encrypted = sut.encrypt(message);


    assertNotEquals(message,encrypted);
    }

    @Test
    void AES_decrypt() throws Exception {

        String message = "ptaki lataja kluczami";
        AES sut= new AES();
        String encrypted = sut.encrypt(message);
        String decrypted = sut.decrypt(encrypted);


        assertEquals(message,decrypted);
    }
    @Test
    void AES_encrypt_single_digit () throws Exception {

        String message = "11";
        AES sut= new AES();
        String encrypted = sut.encrypt(message);


        assertNotEquals(message,encrypted);
    }
    @Test
    void AES_decrypt_single_digit() throws Exception {

        String message = "11";
        AES sut= new AES();
        String encrypted = sut.encrypt(message);
        String decrypted = sut.decrypt(encrypted);

        assertEquals(message,decrypted);
    }
    @Test
    void AES_decrypt_long_message() throws Exception {

        String message = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et quam ut orci mattis pellentesque vitae at massa. Vivamus nunc diam, finibus quis tincidunt ut, maximus non nunc. Ut pharetra varius nibh nec auctor. Ut lacus purus, efficitur sit amet nibh eu, faucibus ultrices turpis. Aliquam arcu tellus, posuere vel libero consectetur, finibus molestie nisl. Ut vehicula lorem vel odio aliquam porta. Cras efficitur leo erat, eget dictum nunc posuere at. Sed cursus ultricies sagittis.
                Maecenas accumsan eros ac nulla tristique consectetur. Phasellus hendrerit metus et turpis eleifend commodo. Maecenas interdum pretium nunc non elementum. Phasellus placerat arcu ligula, quis auctor nibh scelerisque at. Duis faucibus, ante nec sodales faucibus, mi leo dictum eros, id egestas lorem nisi eleifend eros. Pellentesque ut ligula blandit, ornare sapien non, fermentum augue. Interdum et malesuada fames ac ante ipsum primis in faucibus. Cras metus augue, consectetur quis erat at, rhoncus luctus nulla. Nunc finibus magna in dolor interdum consectetur. Proin tempor tincidunt nisl, ut vulputate nunc. Ut est velit, dignissim quis sapien a, vulputate interdum sem. Mauris vel sem in nisl mollis fringilla. Sed vestibulum, enim nec iaculis pretium, turpis lacus tempus purus, id molestie velit lacus et magna. Aenean egestas, sem sed vehicula porta, ante sem iaculis massa, in eleifend velit quam non enim.
                Cras sit amet porttitor ante. Integer vehicula commodo diam volutpat efficitur. Nulla sagittis nunc eget dui mollis, vitae scelerisque nibh sagittis. Mauris sit amet mi eu orci elementum tempor. Sed vitae ornare ante. Nulla vel felis a orci maximus ultrices id eu nunc. Donec facilisis eleifend arcu eu vehicula. Pellentesque gravida risus quam, eu sollicitudin urna fermentum ac. In blandit volutpat pharetra.
                Vestibulum id odio sit amet nunc rhoncus rutrum. Nunc pellentesque cursus viverra. In ut faucibus ante. Ut non ex quis tortor viverra posuere eu eget risus. Nulla commodo ac tortor nec convallis. Cras mollis mi at justo ornare, eu condimentum nisi tempus. Nulla vitae semper sapien. Integer blandit non quam in aliquet. Sed non sodales neque. Vivamus posuere pharetra hendrerit. In malesuada in tellus nec finibus. Aliquam at maximus metus. Aenean mollis leo dictum metus luctus commodo.
                Curabitur sed sollicitudin felis, pretium ornare turpis. Nulla congue augue sit amet libero sodales vulputate. Ut pretium sem tortor, id feugiat purus feugiat a. Nullam urna erat, ultricies sed porta id, facilisis non tellus. Fusce hendrerit tellus nec erat imperdiet dignissim. Donec iaculis dui dui. Vivamus maximus massa lectus, et porta nulla feugiat ac. Sed elementum cursus justo, ac venenatis ante. Phasellus aliquet orci ac dolor hendrerit, interdum aliquam mauris ullamcorper.
                Donec molestie, velit ut tincidunt mattis, eros nisl mattis augue, ut mattis metus augue sit amet tortor. Pellentesque euismod odio a sapien hendrerit tincidunt. Cras est ex, aliquam vitae vulputate id, fringilla at neque. Curabitur lacinia eu risus ac varius. Maecenas porttitor neque ac risus aliquam ultricies. Sed vitae sapien arcu. Mauris faucibus eleifend tristique. Praesent mattis lacus vel nisl imperdiet, non hendrerit nulla venenatis. Phasellus ultricies, justo maximus aliquet semper, est nibh ornare risus, vitae interdum nibh urna nec felis. Etiam ac tristique purus, in auctor elit. Curabitur ut elit nec lectus accumsan pellentesque. Aenean in dignissim sapien. Nunc malesuada, nulla at volutpat sollicitudin, ligula odio scelerisque tortor, ac efficitur turpis libero ut elit. Vivamus et porta arcu. Nunc est mi, commodo nec quam quis, tristique mattis lectus. Pellentesque semper vestibulum ligula.
                """;
        AES sut= new AES();
        String encrypted = sut.encrypt(message);
        String decrypted = sut.decrypt(encrypted);

        assertEquals(message,decrypted);
    }

    @Test
    void AES_special_characters() throws Exception {
        String message ="!>+_+.,:;-&&.<(.<&+!-*;&,)$-+?>*>?_-:?<*#&)>_'',;&_;.$<!'>!+_$#<+$>!?-.,'&#'!(!!;_(_!-(*(!:-_:)%_,'!?.++<(#?*(&:%.;.$;(:,";
        AES sut= new AES();
        String encrypted = sut.encrypt(message);
        String decrypted = sut.decrypt(encrypted);

        assertEquals(message,decrypted);

    }

}