package actions;

import enums.ActionType;
import interfaces.Action;
import interfaces.Adapter;
import main.Client;
import main.Connection;
import requestResponse.Request;

import javax.swing.*;
import java.io.File;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;
import Object.JSON;
import requestResponse.Response;

public class DownloadFile implements Action {
    /**
     * save file in your computer
     * @return save location from your computer
     * */
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
            Client.print("enter file to download");
            String input=Client.input();
            String i = input;
            if(i.contains("/")){
                i=i.substring(i.lastIndexOf("/")+1);
            }
            Path of = Path.of(input);
            String contentType = Files.probeContentType(of);
            if(contentType==null){
                contentType="not file";
            }
            String path = saveFileGui(i);
            if(path.equals("")){
                throw new RuntimeException("not file");
            }
            String uniqueId = UUID.randomUUID().toString();

            Request request = new Request(
                    ActionType.downloadFile.name(),
                    contentType,
                    input,
                    uniqueId,
                    ""

            );
            Client.readwriteData(request);
            String dataFile =Client.bodyData();
            byte[]bytes= Base64.getDecoder().decode(dataFile.getBytes());
            Files.write(Path.of(path),bytes);
        } catch (Exception e) {
             Client.print(e.getMessage());
             e.printStackTrace();
        }
    }
}
