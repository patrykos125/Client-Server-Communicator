package server;


import resourses.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;




public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> users = new ArrayList<>();



    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private String clientUsername;


    public ClientHandler(Socket socket) {
        try {


            this.socket = socket;
            this.objectOutputStream= new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream= new ObjectInputStream(socket.getInputStream());

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
            }

            }
            if(valid){
                this.clientUsername=nickToValid;

                users.add(this);

                objectOutputStream.writeObject(new Message("server","nickValidation","nickOK"
                ,message.getUuid()));

                objectOutputStream.flush();


                broadcastMessage(new Message("SERVER","WelcomeMessage",clientUsername+" has entered the chat!"));

            }
            else{
                objectOutputStream.writeObject(new Message("server","nickValidation","nickWrong"
                        ,message.getUuid()));
                objectOutputStream.flush();
                closeEverything();

            }


        }
        catch(Exception e){e.printStackTrace();}

    }


    @Override
    public void run() {
        Message messageFromClient;

        while (socket.isConnected()) {
            try {


                messageFromClient =(Message) objectInputStream.readObject();
                broadcastMessage(messageFromClient);
            } catch (Exception e) {

                closeEverything();
                break;

            }
        }
    }


    public void broadcastMessage(Message messageToSend) {

        for (ClientHandler user : users) {
            try {

                if (!user.clientUsername.equals(clientUsername)) {

                   user.objectOutputStream.writeObject(messageToSend);
                    objectOutputStream.flush();

                }
            } catch (IOException e) {

                closeEverything();
            }
        }
    }


    public void removeClientHandler() {


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
