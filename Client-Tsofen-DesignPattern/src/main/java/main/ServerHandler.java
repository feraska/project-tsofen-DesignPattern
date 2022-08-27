package main;

import actions.*;
import enums.ActionType;
import enums.Responses;
import interfaces.Action;
import interfaces.Adapter;
import requestResponse.Request;

import java.util.Scanner;
import Object.JSON;
public class ServerHandler extends Thread{

    public void prepareRequest() {
        while (!Connection.getSocket().isClosed()) {
            try {
                String[] options = {"1.upload file", "2.download file", "3.delete file", "4.get List By Directory", "5.get Files", "6.get Directories", "7.upload folder", "8.download folder", "9.delete folder","10.move file or folder"};
                for (String op : options) {
                    Client.print(op);
                }
                Scanner scanner = new Scanner(System.in);
                String reqName = scanner.nextLine();
                Action action = null;
                switch (reqName) {
                    case "1":
                        action = new UploadFile();
                        break;
                    case "2":
                        action = new DownloadFile();
                        break;
                    case "3":
                        action = new DeleteFile();
                        break;
                    case "4":
                        action = new GetFilesByDirectory();
                        break;
                    case "5":
                        action = new GetFiles();
                        break;
                    case "6":
                        action = new GetDirectories();
                        break;
                    case "7":
                        action = new UploadFolder();
                        break;
                    case "8":
                        action = new DownloadFolder();
                        break;
                    case "9":
                        action = new DeleteFolder();
                        break;
                    case "10":
                        action = new Move();
                        break;


                }
                if (action == null) {
                    throw new Exception(Responses.requestNotFound + " ==> " + Responses.requestNotFound.getI());

                }
                action.action();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    public void run() {
        prepareRequest();
    }
}
