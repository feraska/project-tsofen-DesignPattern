package actions;

import enums.ActionType;
import interfaces.Action;
import interfaces.Adapter;
import main.Client;
import main.Connection;
import requestResponse.Request;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;
import Object.JSON;

public class UploadFolder implements Action {
    /**
     * upload file from folder from your computer
     * @return path folder selection
     * */
    public static String uploadFileGuiFolder(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            return jfc.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
    /**
     * prepare file to upload from folder
     * @param path the folder to store in server
     * @param file the file to upload
     * */
    public void getFile(String path,File file){
        try {
            File[] list = file.listFiles();
            assert list != null;
            for (File f : list) {
                if(f.isDirectory()){
                    getFile(path,f);
                }
                else{
                    uploadByFile(f.getAbsolutePath(),path);
                }
            }
        }
        catch (Exception e){

        }
    }
    /**
     * upload file to the server
     * @param fileAbsolutePath file path from your computer to upload to the server
     * @param dirP the folder to store in the server
     * */
    public void uploadByFile(String fileAbsolutePath,String dirP) {
        try {
            File file = new File(fileAbsolutePath);
            String filePath=file.getParent();
            filePath=filePath.substring(filePath.lastIndexOf("\\")+1);
            filePath=filePath+"/"+file.getName();
            if(dirP.equals("")){
                dirP=filePath;
            }
            else {
                dirP=dirP+"/"+filePath;
            }

            Path of = Path.of(fileAbsolutePath);
            String contentType = Files.probeContentType(of);
            if(contentType==null){
                contentType="not file";
            }
            String uniqueId = UUID.randomUUID().toString();
            byte[] bytes = Files.readAllBytes(of);
            byte[] encode = Base64.getEncoder().encode(bytes);
            String byteString = new String(encode);
            Request request = new Request(
                    ActionType.uploadFile.name(),
                    contentType,
                    dirP,
                    uniqueId,
                    byteString
            );
            Client.readwriteData(request);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void action() {
        try {
            Scanner scanner = new Scanner(System.in);
            String pathDir = uploadFileGuiFolder();
            if(pathDir.equals("")){
                throw new RuntimeException("not folder");
            }
            Client.print("enter path to save");
            String path = scanner.nextLine();
            if(path.equals("root")){
                path="";
            }
            File file = new File(pathDir);
            getFile(path,file);
        }
        catch (Exception e){
            Client.print(e.getMessage());
        }
    }
}
