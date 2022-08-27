package actions;

import enums.ActionType;
import interfaces.Action;
import main.Client;
import requestResponse.Request;

import javax.swing.*;
import java.io.File;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import Object.JSON;
import requestResponse.Response;

public class DownloadFolder implements Action {
    public static String saveFileGui(String name) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setSelectedFile(new File(name));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            return fileToSave.getAbsolutePath();
        }
        return "";
    }
    @Override
    public void action() {
        try {

            Client.print("enter folder to download");
            String input = Client.input();
            String path = saveFileGui(input);
            if(path.equals("")){
                throw new RuntimeException("not file");
            }
            path=path.replace("\\","/");
//            request = new Request();
            Path off = Path.of(input);
            String contentType = Files.probeContentType(off);
            if(contentType==null){
                contentType="not file";
            }
            String uniqueId = UUID.randomUUID().toString();
            Request request = new Request(
                    ActionType.downloadDir.name(),
                    contentType,
                    input,
                    uniqueId,
                    ""

            );

//            prepareData(action, input,contentType);
//            dataOutputStream.println(request.toString());

            while (true) {
                Client.readwriteData(request);
                String bodyData = Client.bodyData();
               // System.out.println(bodyData);
                Response response=Client.allData();
                String filePath = response.getFileName();
               // System.out.println(filePath);
                if(bodyData.length()==0||filePath.length()==0){
                  //  System.out.println("FFF");
                    break;
                }
                byte[] bytes = Base64.getDecoder().decode(bodyData.getBytes());

                String dirs = filePath.substring( 0,filePath.lastIndexOf("/"));
                File file = new File( path+"/"+dirs);
                file.mkdirs();
                Path of = Path.of( path+"/"+filePath);
                Files.write(of, bytes);
            }

        }
        catch (Exception e){

        }
    }
}
