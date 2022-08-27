package actions;

import enums.Responses;
import interfaces.Action;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class GetDirectories implements Action {
    StringBuilder dirs;
    /**
     * get folder by folder
     * @param dir folder to give all folder inside
     * */
    public void getDir(File dir){
        try {

            File[] list = dir.listFiles();

            assert list != null;
            for (File f : list) {
                if(f.isDirectory()){
                    dirs.append(f.getPath()).append(":");
                    getDir(f);
                }

            }
        }
        catch (Exception e){

        }
    }

    @Override
    public void action(PrintWriter printWriter, Request request) {
        Response response = null;
        try {
            dirs=new StringBuilder();
            File file = new File(Server.fileRoot);
            getDir(file);
             response= new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    dirs.toString().trim(),
                    Responses.success+" ==> "+Responses.success.getI()
            );

        }
        catch (Exception e){

        }
        finally {
            Server.readWriteData(response,printWriter);
        }
    }
}
