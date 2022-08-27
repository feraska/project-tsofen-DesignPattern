package actions;

import enums.ActionType;
import interfaces.Action;
import interfaces.Adapter;
import main.Client;
import main.Connection;
import requestResponse.Request;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;
import Object.JSON;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class UploadFile implements Action {

    /**
     * upload file from your computer
     * @return files selection store in array file
     * */
    public static File[] uploadFileGui(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setMultiSelectionEnabled(true);
        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            return jfc.getSelectedFiles();
        }
        return null;
    }


    @Override
    public void action() {
    File[]files=uploadFileGui();
        try {
            Client.print("enter path to save");
            String path = Client.input();
            String fileP = path;
            if (files==null) {
                throw new RuntimeException("not file");
            }
            for (File f:files) {
                String filePath = f.getAbsolutePath();
                File file = new File(filePath);

                if (fileP.equals("root")) {
                    fileP = file.getName();
                } else {
                    fileP = path + "/" + file.getName();
                }
                Path of = Path.of(filePath);
                String contentType = Files.probeContentType(of);
                if(contentType==null){
                    contentType="not file";
                }
                byte[] bytes = Files.readAllBytes(of);
                byte[] encode = Base64.getEncoder().encode(bytes);
                String byteString = new String(encode);
                String uniqueId = UUID.randomUUID().toString();
                Request request = new Request(
                        ActionType.uploadFile.name(),
                        contentType,
                        fileP,
                        uniqueId,
                        byteString

                );
                Client.readwriteData(request);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
