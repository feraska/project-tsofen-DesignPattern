package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private static Socket socket;
    private static PrintWriter printWriter;
    private static BufferedReader bufferedReader;

    public static Socket getSocket() {
        return socket;
    }

    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public static PrintWriter getPrintWriter() {
        return printWriter;
    }

    public Connection(){
        try {
            socket = new Socket(Client.getIp(), Client.getPort());
            printWriter=new PrintWriter(socket.getOutputStream(),true);
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception e){
            Client.print(e.getMessage());
        }
    }
    public void run(){
        ServerHandler serverHandler = new ServerHandler();
        serverHandler.start();
    }
}
