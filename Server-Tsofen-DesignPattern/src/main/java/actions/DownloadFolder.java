package actions;

import enums.Responses;
import interfaces.Action;
import main.Server;
import requestResponse.Request;
import requestResponse.Response;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class DownloadFolder implements Action {
    /**
     * give folder to download
     * @param file folder to download
     * */
    public  void getFileAndDownload(File file,Request request,PrintWriter printWriter){
        Response response = null;
        try {

            File[] list = file.listFiles();

            if(list==null){
                throw new RuntimeException("not exists");
            }
            for (File value : list) {
                if (value.isDirectory()) {
                    getFileAndDownload(value, request, printWriter);
                } else {

                     try {
                    String fileName = value.getPath().substring(value.getPath().indexOf("\\") + 1);
                    fileName = fileName.replace("\\", "/");
                    byte[] bytes = Files.readAllBytes(Path.of(value.getPath()));
                    byte[] encode = Base64.getEncoder().encode(bytes);
                    String data = new String(encode);
                    response = new Response(
                            request.getActionType(),
                            Files.probeContentType(value.toPath()),
                            fileName,
                            request.getRequestId(),
                            data,
                            Responses.success + " ==> " + Responses.success.getI()
                    );
                  //  Server.readWriteData(response, printWriter);
                     }
                     catch (Exception e) {
                         response = new Response(
                                 request.getActionType(),
                                 Files.probeContentType(value.toPath()),
                                 "",
                                 request.getRequestId(),
                                 "",
                                 Responses.fileNotFound + " ==> " + Responses.fileNotFound.getI()
                         );

                      }
                      finally {
                        Server.readWriteData(response,printWriter);
                     }

                }
            }
            response = new Response(
                    request.getActionType(),
                    "",
                    "",
                    request.getRequestId(),
                    "",
                    Responses.success + " ==> " + Responses.success.getI()
            );


        }

        catch (Exception e){
            // e.printStackTrace();
            response = new Response(
                    request.getActionType(),
                    "",
                    "",
                    request.getRequestId(),
                    "",
                    Responses.fileNotFound+" ==> "+Responses.fileNotFound.getI()
            );
        }
        finally {
            Server.readWriteData(response,printWriter);
        }
    }

    @Override
    public void action(PrintWriter printWriter, Request request) {
        try {
                String fileName=request.getFileName();
                String path = Server.fileRoot + "/" + fileName;
                File file = new File(path);
                getFileAndDownload(file,request,printWriter);
            }
            catch (Exception e){

            }


        }
    }

