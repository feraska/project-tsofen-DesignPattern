package actions;

import enums.ActionType;
import interfaces.Action;
import interfaces.Adapter;
import main.Client;
import requestResponse.Request;

import java.net.Socket;
import java.util.UUID;
import Object.JSON;
import requestResponse.Response;

public class GetFiles implements Action {
    @Override
    public void action() {
        try {
            String uniqueId = UUID.randomUUID().toString();
            Request request = new Request(
                    ActionType.getAllFiles.name(),
                    "files",
                    "",
                    uniqueId,
                    ""
            );
            Client.readwriteData(request);
            String lines=Client.bodyData();
            String[] l = lines.split(":");
            for (String s : l) {
                Client.print(s);
            }
        }
        catch (Exception e){

        }
    }
}
