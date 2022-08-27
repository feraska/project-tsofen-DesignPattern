package actions;

import enums.Responses;
import interfaces.Action;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class GetFilesByDirectory implements Action {

    @Override
    public void action(PrintWriter printWriter, Request request) {
        String dirName=request.getFileName();
        String path = Server.fileRoot + "/" + dirName;
        Response response = null;
        try {

            File file = new File(path);
            String[] fileList = file.list();
            if (fileList == null) {
                throw new RuntimeException("directory not found");
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String f : fileList) {
                stringBuilder.append(f).append(":");
            }
             response = new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    stringBuilder.toString().trim(),
                    Responses.success+" == > "+Responses.success.getI()
            );

        }
        catch (Exception e){
            response = new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    "",
                    Responses.fileNotFound+" == > "+Responses.fileNotFound.getI()
            );
        }
        finally {
            Server.readWriteData(response,printWriter);
        }
    }
}
