package actions;

import enums.ActionType;
import enums.Responses;
import interfaces.Action;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Move implements Action {
    @Override
    public void action(PrintWriter printWriter, Request request) {
        Response response = null;
        try {
            String[] path = request.getFileName().split(":");
            String source = Server.fileRoot+"/"+path[0];
            String destination = Server.fileRoot+"/"+path[1];
            File file = new File(destination.substring(0,destination.lastIndexOf("/")));
            file.mkdirs();
            Files.move(new File(source).toPath(),new File(destination).toPath());
             response = new Response(
                    ActionType.move.name(),
                    "",
                    request.getFileName(),
                    request.getRequestId(),
                    "",
                    Responses.success + " ==> " + Responses.success.getI()
            );
        }
        catch (Exception e){
             response = new Response(
                    ActionType.move.name(),
                    "",
                    request.getFileName(),
                    request.getRequestId(),
                    "",
                    Responses.fileNotFound + " ==> " + Responses.fileNotFound.getI()
            );
            // e.printStackTrace();

        }
        finally {
            Server.readWriteData(response,printWriter);
        }
    }
}
