package actions;

import enums.Responses;
import interfaces.Action;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class GetFiles implements Action {
    StringBuilder files ;
    /**
     * give all files in folder
     * @param file the folder
     * */
    public void getFile(File file){
        try {

            File[] list = file.listFiles();

            assert list != null;
            for (File f : list) {
                if(f.isDirectory()){
                    getFile(f);
                }
                else{
                    int index=f.getPath().indexOf("\\");
                    files.append(f.getPath().substring(index+1)).append(":");
                }
            }
        }
        catch (Exception e){

        }
    }

    @Override
    public void action(PrintWriter printWriter, Request request) {
        Response response=null;
        try {
             files=new StringBuilder();
            File file = new File(Server.fileRoot);
            getFile(file);
             response = new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    files.toString().trim(),
                    Responses.success +" ==> "+Responses.success.getI()
            );

        }
        catch (Exception e){
             response= new Response(
                    request.getActionType(),
                    request.getContentType(),
                    request.getFileName(),
                    request.getRequestId(),
                    "",
                    Responses.fileNotFound +" ==> "+Responses.fileNotFound.getI()
            );
        }
        finally {
            Server.readWriteData(response,printWriter);
        }
    }
}
