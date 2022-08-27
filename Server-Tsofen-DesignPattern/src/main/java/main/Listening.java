package main;

import java.net.ServerSocket;
import java.net.Socket;

public class Listening extends Thread{
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Server.getPort());

            while (!serverSocket.isClosed()) {
                Server.print("wait to connection");
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
                Server.print("connection successfully");

            }

        }
        catch (Exception e){

        }

    }
}
