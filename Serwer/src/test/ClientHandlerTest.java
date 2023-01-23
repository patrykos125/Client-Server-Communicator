package test;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resourses.Message;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


class ClientHandlerTest {





    @Test void valid_first_user() throws IOException, ClassNotFoundException {
        new Thread(()->{
            //server
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8000);
                Server server = new Server(serverSocket);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String nick ="Patryk";
        //server


        //client
        Socket socket = new Socket("localhost", 8000);
        ObjectInputStream objectInputStream = new ObjectInputStream( socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream( socket.getOutputStream());
        objectOutputStream.writeObject(new Message("newUser","nickValidation",nick));
        objectOutputStream.flush();
        Message  response = (Message) objectInputStream.readObject();


      assertEquals("nickOK",response.getMessage());
    }

    @Test void valid_two_users_with_difrent_nicks_should_return_true() throws IOException, ClassNotFoundException, InterruptedException {
        new Thread(()->{
            //server
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8001);
                Server server = new Server(serverSocket);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        String nick ="Patryk";
        String nick2 ="Robert";
        //server


        //client
        Socket socket = new Socket("localhost", 8001);
        ObjectInputStream objectInputStream = new ObjectInputStream( socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream( socket.getOutputStream());
        objectOutputStream.writeObject(new Message("newUser","nickValidation",nick));
        objectOutputStream.flush();
        Message  response = (Message) objectInputStream.readObject();
        //second
        new Thread(()->{
            Socket socket1 = null;
            try {
                socket1 = new Socket("localhost", 8001);
                ObjectInputStream objectInputStream1 = new ObjectInputStream( socket1.getInputStream());
                ObjectOutputStream objectOutputStream1 = new ObjectOutputStream( socket1.getOutputStream());
                objectOutputStream1.writeObject(new Message("newUser","nickValidation",nick2));
                objectOutputStream1.flush();
                Message  response1 = (Message) objectInputStream1.readObject();
                assertEquals("nickOK",response1.getMessage());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }).start();

        assertEquals("nickOK",response.getMessage());

    }

    @Test void valid_two_users_with_same_nicks_should_return_false() throws IOException, ClassNotFoundException {
        //server
        new Thread(()->{
            //server
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8002);
                Server server = new Server(serverSocket);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String nick ="Patryk";
        String nick2 ="Patryk";



        //client 1
        Socket socket = new Socket("localhost", 8002);
        ObjectInputStream objectInputStream = new ObjectInputStream( socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream( socket.getOutputStream());
        objectOutputStream.writeObject(new Message("newUser","nickValidation",nick));
        objectOutputStream.flush();
        Message  response = (Message) objectInputStream.readObject();

        assertEquals("nickOK",response.getMessage());

        //client 2
        new Thread(()->{
            Socket socket1 = null;
            try {
                socket1 = new Socket("localhost", 8002);
                ObjectInputStream objectInputStream1 = new ObjectInputStream( socket1.getInputStream());
                ObjectOutputStream objectOutputStream1 = new ObjectOutputStream( socket1.getOutputStream());
                objectOutputStream1.writeObject(new Message("newUser","nickValidation",nick2));
                objectOutputStream1.flush();
                Message  response1 = (Message) objectInputStream1.readObject();

                assertEquals("nickWrong",response1.getMessage());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }).start();



    }



}