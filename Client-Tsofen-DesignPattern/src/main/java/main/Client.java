package main;

import interfaces.Adapter;
import requestResponse.Request;

import java.util.Scanner;
import Object.JSON;
import requestResponse.Response;

public class Client {
    private static String ip;
    private static int port;
    private static String dataLine;

    public static String getData() {
        return dataLine;
    }

    public static void print(String msg){
        System.out.println(msg);
    }
    public static boolean checkValidIp(String ip){
        String[]ips=ip.split("\\.");
        try {
            for (String ipv:ips) {
                Integer.parseInt(ipv);
            }
        }
        catch (Exception e){
            return false;
        }
        return ips.length == 4;
    }
    public static boolean checkValidPort(String port){
        try {

            Integer.parseInt(port);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);
            print("get ip address server");
            ip = scanner.nextLine();
            if(!checkValidIp(ip)){
                throw new RuntimeException("not valid ip address");
            }
            print("get port server");
            String portStr=scanner.nextLine();
            if(!checkValidPort(portStr)){
                throw new RuntimeException("not valid port");
            }
            port = Integer.parseInt(portStr);
            Connection connection = new Connection();
            connection.run();

        }
        catch (Exception e){

          print(e.getMessage());
        }
    }
    public static String input(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static void readwriteData(Request request){
        try {

            Adapter adapter = new JSON();
            String data = adapter.convertTo(request);
            Connection.getPrintWriter().println(data);
            String line = Connection.getBufferedReader().readLine();
            dataLine=line;
            Client.print(line.substring(0, line.indexOf("body")));
        }
        catch (Exception e){

        }
    }
    public static String bodyData(){
        Adapter  adapter = new JSON();
        Response response= (Response) adapter.convertFrom(getData());
        return response.getFileContent();
    }
    public static Response allData(){
        Adapter  adapter = new JSON();
        return (Response) adapter.convertFrom(getData());
    }
    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }
}
