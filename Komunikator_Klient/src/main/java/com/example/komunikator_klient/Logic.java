package com.example.komunikator_klient;

import javafx.scene.layout.VBox;
import resourses.AES;
import resourses.Message;
import resourses.RSA;

import java.io.*;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Logic {

    private Socket socket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;

  private  AES aes;


    public Logic(String adresIP, String port, String username) {
        try {
            Socket socket = new Socket(adresIP, Integer.parseInt(port));

            this.username = username;
            this.socket = socket;
            this.objectInputStream = new ObjectInputStream( socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream( socket.getOutputStream());
            this.aes = new AES();
        } catch (IOException e) {


            closeEverything();
        }
    }


    public void sendMessage(String messageToSend) {
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            objectOutputStream.writeObject(new Message(username,"message", aes.encrypt(messageToSend)));
            objectOutputStream.flush();




        } catch (Exception e) {

            closeEverything();
        }
    }
}).start();


            }





    public void listenForMessage(VBox vbox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msgFromGroupChat;

                while (socket.isConnected()) {
                    try {

                        msgFromGroupChat = (Message) objectInputStream.readObject();


                        Controler_main.receiveMessage(msgFromGroupChat.getAuthor()+": "+aes.decrypt(msgFromGroupChat.getMessage()), vbox);


                    } catch (Exception e) {

                        closeEverything();
                    }
                }
            }
        }).start();
    }


    public void closeEverything() {

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

    public boolean validNick() {

        Message request = new Message("newUser","nickValidation",username);

        Message response=null;

        try {
            objectOutputStream.writeObject(new Message("newUser","nickValidation",username));
            objectOutputStream.flush();




            response = (Message) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (response.getMessage().equals("nickOK") && response.getUuid() == request.getUuid()) {
            synchroKey();return true;}
        else {
            return false;
        }

    }

    private void synchroKey() {
        RSA rsa= new RSA();

        PublicKey temporaryPublicKey = rsa.getPublic();
        try {
            //send rsa public key

            objectOutputStream.writeObject(temporaryPublicKey);
            objectOutputStream.flush();

            //get aes secret key

            String aesEmncryptedKey =(String) objectInputStream.readObject();
            String aesKey = rsa.decrypt(aesEmncryptedKey);

            //get aes IV

            String aesEncryptedIV = (String)objectInputStream.readObject();
            String aesIV = rsa.decrypt(aesEncryptedIV);

            //init aes
            aes.initFromStrings(aesKey,aesIV);


        }catch (Exception e){e.printStackTrace();}



    }


}




