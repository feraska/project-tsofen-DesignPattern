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
import Object.JSON;
public class DeleteFile implements Action {
    @Override
    public void action(PrintWriter printWriter, Request request) {
        Response response = null;
        try {
            String fileName = request.getFileName();
            String path = Server.fileRoot + "/" + fileName;
            Files.delete(Path.of(path));
            response = new Response(
                    ActionType.deleteFile.name(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    "",
                    Responses.success.name()+" ==> "+Responses.success.getI()
            );

        }
        catch (Exception e) {
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
