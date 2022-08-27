package actions;

import enums.ActionType;
import enums.Responses;
import interfaces.Action;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

public class DeleteFolder implements Action {
    /**
     * delete all file form the folder
     * @param file the file to delete
     * */
    public void getFileAndDelete(File file,Request request,PrintWriter printWriter) {
        Response response = null;
        try {
            File[] list = file.listFiles();
            if(list==null) {
                throw new RuntimeException("not file");
            }
            for (File value : list) {

                if (value.isDirectory()) {
                    getFileAndDelete(value,request,printWriter);
                } else {
                    Files.delete(value.toPath());
                }

            }
            response = new Response(
                    ActionType.downloadDir.name(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    request.getFileContent(),
                    Responses.success + " ==> "+Responses.success.getI()
            );
            Files.delete(file.toPath());
        }
        catch (Exception e){
            response = new Response(
                    ActionType.downloadDir.name(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    request.getFileContent(),
                    Responses.fileNotFound + " ==> "+Responses.fileNotFound.getI()
            );
        }
        finally {
            Server.readWriteData(response,printWriter);
        }

    }

    @Override
    public void action(PrintWriter printWriter, Request request) {
        try {
            String dirName = request.getFileName();
            String path = Server.fileRoot + "/" + dirName;
            File file = new File(path);
            getFileAndDelete(file,request,printWriter);

        }
        catch (Exception e){

        }

    }
}
