package actions;

import enums.ActionType;
import interfaces.Action;

import main.Client;
import requestResponse.Request;


import java.util.UUID;


public class GetFilesByDirectory implements Action {
    /**
     * save file in your computer
     * @return save location from your computer
     * */

    @Override
    public void action() {

        Client.print("enter directory path");
        String input = Client.input();
        try {
            String uniqueId = UUID.randomUUID().toString();
            Request request = new Request(
                    ActionType.getFileInDir.name(),
                    "directory",
                    input,
                    uniqueId,
                    ""

            );
            Client.readwriteData(request);
            String lines=Client.bodyData();
            String[]l = lines.split(":");
            for (String s:l){
                Client.print(s);
           }
       } catch (Exception e) {
      }
    }
}
