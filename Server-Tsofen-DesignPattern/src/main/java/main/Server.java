package main;

import interfaces.Adapter;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import Object.JSON;
public class Server {
    public static final String fileRoot="file_root";//file_root server
    private static int port;

    public static int getPort() {
        return port;
    }

    /**
     * make file_root directory
     * */
    public static void mkdir() {
        File file = new File(fileRoot);
        if (!file.exists()) {
            file.mkdir();
        }
    }
    /**
     * write msg
     * @param msg string to write at screen
     * */
    public static void print(String msg){
        System.out.println(msg);
    }
    public static void readWriteData(Response response, PrintWriter printWriter){
        Adapter adapter = new JSON();
        String convert=adapter.convertTo(response);
        assert printWriter != null;
        printWriter.println(convert);
        Log.log(convert.substring(0,convert.indexOf("body")));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mkdir();
        print("enter port");
        port= Integer.parseInt(scanner.nextLine());
        Listening listening = new Listening();
        listening.start();
    }
}
