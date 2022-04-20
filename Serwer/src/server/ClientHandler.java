package server;


import resourses.AES;
import resourses.Message;
import resourses.RSA;

import javax.crypto.SecretKey;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;


public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> users = new ArrayList<>();



    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private String clientUsername;
    private AES aes;





    public ClientHandler(Socket socket) {
        try {


            this.socket = socket;
            this.objectOutputStream= new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream= new ObjectInputStream(socket.getInputStream());
            this.aes= new AES();


            validNick();

        } catch (IOException e) {

            closeEverything();
        }
    }
    public void validNick(){
        try{

            Message message = (Message)objectInputStream.readObject();

            String nickToValid =message.getMessage();

        boolean valid=true;
        for(ClientHandler client: users)
            {

            if(client.clientUsername.equals(nickToValid)){
                valid=false;
                break;
            }

            }
            if(valid){
                this.clientUsername=nickToValid;

                users.add(this);


                objectOutputStream.writeObject(new Message("SERVER","nickValidation","nickOK"
                ,message.getUuid()));
                objectOutputStream.flush();

                synchroKey();

                broadcastMessage(new Message("SERVER","WelcomeMessage",clientUsername+" has entered the chat!"));

            }
            else{
                objectOutputStream.writeObject(new Message("SERVER","nickValidation","nickWrong"
                        ,message.getUuid()));
                objectOutputStream.flush();
                closeEverything();

            }


        }
        catch(Exception e){e.printStackTrace();}

    }

    private void synchroKey() {
RSA rsa = new RSA();



        try {
            //receive public rsa key
            PublicKey publicKey=(PublicKey) objectInputStream.readObject();

            // encrypt secret aes key with rsa public key
            String  aesKey=Base64.getEncoder().encodeToString(aes.exportkey());
            String aesEncryptedKey=rsa.encrypt(aesKey,publicKey);

            //send encrypted secret key

           objectOutputStream.writeObject(aesEncryptedKey);
           objectOutputStream.flush();

           //encrypt IV using public rsa key

           String aesIV=Base64.getEncoder().encodeToString(aes.exportVI());
           String aesEncryptedIV = rsa.encrypt(aesIV,publicKey);

           //send encrypted IV

           objectOutputStream.writeObject(aesEncryptedIV);
           objectOutputStream.flush();





        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    @Override
    public void run() {
        Message messageFromClient;

        while (socket.isConnected()) {
            try {


                messageFromClient =(Message) objectInputStream.readObject();
                //decrypt mesage
                Message messageToSend= decrypttMessage(messageFromClient);



                broadcastMessage(messageToSend);

            } catch (Exception e) {

                closeEverything();
                break;

            }
        }
    }


    public void broadcastMessage(Message message) {




        for (ClientHandler user : users) {
            try {

                if (!user.clientUsername.equals(clientUsername)) {

                    Message encryptedToSend=user.encryptMessage(message);

                   user.objectOutputStream.writeObject(encryptedToSend);
                    objectOutputStream.flush();
                     // aby zobzaczyc klucze odkomentuj
                    // System.out.println(encryptedToSend.getMessage());


                }
            } catch (IOException e) {

                closeEverything();
            }
        }
    }
    private Message decrypttMessage(Message encryptedMessage){
        Message message= new Message();
        try {
             message = new Message(
                    encryptedMessage.getAuthor(),
                    encryptedMessage.getTypy(),
                    aes.decrypt(encryptedMessage.getMessage()),
                    encryptedMessage.getUuid()
            );




        }catch (Exception e){e.printStackTrace();}


        return message;
    }
    private Message encryptMessage(Message plainmessage){
        Message message= new Message();
        try {
            message = new Message(
                    plainmessage.getAuthor(),
                    plainmessage.getTypy(),
                    aes.encrypt(plainmessage.getMessage()),
                    plainmessage.getUuid()
            );




        }catch (Exception e){e.printStackTrace();}


        return message;
    }



    public void removeClientHandler() {

    if(clientUsername!=null)
        broadcastMessage(new Message("SERVER","leftMessage",clientUsername+" has left the chat!"));
        users.remove(this);


    }


    public void closeEverything() {

        removeClientHandler();
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int amountOfClients(){
        return users.size();
    }
}
