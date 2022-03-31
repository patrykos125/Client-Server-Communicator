package com.example.komunikator_klient;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Logic {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Logic(String adresIP, String port, String username) {
        try {
            Socket socket = new Socket(adresIP, Integer.parseInt(port));

            this.username = username;
            this.socket = socket;

            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {


            closeEverything();
        }
    }


    public void sendMessage(String messageToSend) {
new Thread(new Runnable() {
    @Override
    public void run() {
        try {


            bufferedWriter.write(username + ": " + messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {

            closeEverything();
        }
    }
}).start();


            }





    public void listenForMessage(VBox vbox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {

                        msgFromGroupChat = bufferedReader.readLine();


                        Controler_login.receiveMessage(msgFromGroupChat, vbox);


                    } catch (IOException e) {

                        closeEverything();
                    }
                }
            }
        }).start();
    }


    public void closeEverything() {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validNick() {


        String status = "";
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            status = bufferedReader.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (status.equals("badNick")) return false;
        else {
            return true;
        }

    }


}




