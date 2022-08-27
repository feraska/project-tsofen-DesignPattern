package actions;

import enums.Responses;
import interfaces.Action;
import interfaces.Adapter;
import main.ClientHandler;
import main.Log;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import Object.JSON;
public class UploadFile implements Action {

    @Override
    public void action(PrintWriter printWriter, Request request) {

        String fileName = request.getFileName();
        String b=request.getFileContent();
        String fileDes = Server.fileRoot + "/" + fileName;
        File file = new File(fileDes.substring(0, fileDes.lastIndexOf("/")));
        file.mkdirs();
        byte[] decode = b.getBytes();

        byte[] bytes = Base64.getDecoder().decode(decode);
        Response response = null;
        try {
                Files.write(Path.of(fileDes), bytes);
             response= new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    request.getFileContent(),
                    Responses.success.name()+" ==> "+Responses.success.getI()
            );


        } catch (Exception e) {
             response = new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    request.getFileContent(),
                    Responses.exception.name()+" ==> "+Responses.exception.getI()
            );
        }
        finally {
           Server.readWriteData(response,printWriter);
        }
    }
}
