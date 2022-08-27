package actions;

import enums.ActionType;
import enums.Responses;
import interfaces.Action;
import interfaces.Adapter;
import main.Log;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import Object.JSON;

public class DownloadFile implements Action {
    @Override
    public void action(PrintWriter printWriter, Request request) {
        String fileName=request.getFileName();
        String path = Server.fileRoot + "/" + fileName;
        Response response=null;

        try {

            byte[] bytes = Files.readAllBytes(Path.of(path));
            byte[] encode = Base64.getEncoder().encode(bytes);
            String data = new String(encode);
             response = new Response(
                    ActionType.downloadFile.name(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    data,
                    Responses.success.name()+" ==> "+Responses.success.getI()
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new Response(
                    ActionType.downloadFile.name(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    "",
                    Responses.fileNotFound.name()+" ==> "+Responses.fileNotFound.getI()
            );

        }
        finally {
           Server.readWriteData(response,printWriter);
        }
    }
}
