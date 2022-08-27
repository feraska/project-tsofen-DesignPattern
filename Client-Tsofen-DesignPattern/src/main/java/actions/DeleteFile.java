package actions;

import enums.ActionType;
import interfaces.Action;
import interfaces.Adapter;
import main.Client;
import main.Connection;
import requestResponse.Request;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Object.JSON;
public class DeleteFile implements Action {
    @Override
    public void action() {
        try {
            Client.print("enter path from sever to delete");
            List<String> list = new LinkedList<>();
            String input="";
            do {
                input = Client.input();
                if(!input.equals("")) {
                    list.add(input);
                }
            }
            while (!input.equals(""));
            for (String l:list) {
                Path off = Path.of(l);
                String contentType = Files.probeContentType(off);
                if(contentType==null){
                    contentType="not file";
                }
                String uniqueId = UUID.randomUUID().toString();
                Request request = new Request(
                        ActionType.deleteFile.name(),
                        contentType,
                        l,
                        uniqueId,
                        ""

                );
                Client.readwriteData(request);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
