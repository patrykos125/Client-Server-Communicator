

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;




public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();



    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;


    public ClientHandler(Socket socket) {
        try {


            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            validNick();

        } catch (IOException e) {

            closeEverything();
        }
    }
    public void validNick(){
        try{
        String nickToValid = bufferedReader.readLine();
        boolean valid=true;
        for(ClientHandler client:clientHandlers)
            {

            if(client.clientUsername.equals(nickToValid)){
                valid=false;
            }

            }
            if(valid){
                this.clientUsername=nickToValid;

                clientHandlers.add(this);

                    bufferedWriter.write("server:nickOK");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
            }
            else{
                bufferedWriter.write("badNick");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                closeEverything();

            }


        }
        catch(IOException e){e.printStackTrace();}

    }


    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {

                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {

                closeEverything();
                break;
            }
        }
    }


    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // You don't want to broadcast the message to the user who sent it.
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {

                closeEverything();
            }
        }
    }


    public void removeClientHandler() {
        if(clientUsername!=null)
            broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
        clientHandlers.remove(this);


    }


    public void closeEverything() {

        removeClientHandler();
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
    public static int amountOfClients(){
        return clientHandlers.size();
    }
}
