package main;

import actions.*;
import enums.ActionType;
import interfaces.Action;
import interfaces.Adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import Object.JSON;
import requestResponse.Data;
import requestResponse.Request;
import requestResponse.Response;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    /***
     * constructor the role is client handler request and send response
     *  the response is json object(header,body)
     * @param socket from main.Listening class
     * */
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {

            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {

        }
    }


    /**
     * run thread
     * */
    @Override
    public void run() {

                while (!socket.isClosed()) {

                    processCommand();
            }
        }

    public void processCommand(){
        try {
            Adapter adapter = new JSON();
            String line=bufferedReader.readLine();
            System.out.println(line);
            Data data=adapter.convertFrom(line);
               Request request=(Request) data;
                Action action=null;
               switch (ActionType.valueOf(request.getActionType())){
                   case uploadFile:
                       action=new UploadFile();
                       break;
                   case downloadFile:
                       action = new DownloadFile();
                       break;
                   case deleteFile:
                       action = new DeleteFile();
                       break;
                   case getFileInDir:
                       action = new GetFilesByDirectory();
                       break;
                   case getAllFiles:
                       action = new GetFiles();
                       break;
                   case getAllDir:
                       action = new GetDirectories();
                       break;

                   case downloadDir:
                       action = new DownloadFolder();
                       break;
                   case deleteDir:
                       action = new DeleteFolder();
                       break;
                   case move:
                       action = new Move();
                       break;
               }
            assert action != null;
            action.action(printWriter,request);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
