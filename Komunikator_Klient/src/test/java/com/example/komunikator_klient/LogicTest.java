package com.example.komunikator_klient;

import org.junit.jupiter.api.Test;
import resourses.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;


class LogicTest {
    @Test
    void valid_normal_nick() {

        // create server
        new Thread (()->{

            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8000);
                Socket socket = serverSocket.accept();

                ObjectOutputStream objectOutputStream= new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream= new ObjectInputStream(socket.getInputStream());

                Message messageFromClient =(Message) objectInputStream.readObject();

                objectOutputStream.writeObject(new Message("SERVER","nickValidation","nickOK"
                        ,messageFromClient.getUuid()));

                objectOutputStream.flush();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }).start();

        String nickToValid ="Patryk";

        Logic sut = new Logic("localhost","8000",nickToValid);

        boolean status = sut.validNick();


        assertTrue(status);



        }






}