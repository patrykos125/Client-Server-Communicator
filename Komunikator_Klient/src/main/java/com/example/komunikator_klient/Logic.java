package com.example.komunikator_klient;

import javafx.scene.layout.VBox;
import resourses.Message;

import java.io.*;
import java.net.Socket;

public class Logic {

    private Socket socket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;

    public Logic(String adresIP, String port, String username) {
        try {
            Socket socket = new Socket(adresIP, Integer.parseInt(port));

            this.username = username;
            this.socket = socket;
            this.objectInputStream = new ObjectInputStream( socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream( socket.getOutputStream());
        } catch (IOException e) {


            closeEverything();
        }
    }


    public void sendMessage(String messageToSend) {
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            objectOutputStream.writeObject(new Message(username,"message",messageToSend));
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


                        Controler_main.receiveMessage(msgFromGroupChat.getAuthor()+": "+msgFromGroupChat.getMessage(), vbox);


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


        if (response.getMessage().equals("nickOK") && response.getUuid() == request.getUuid()) return true;
        else {
            return false;
        }

    }


}




