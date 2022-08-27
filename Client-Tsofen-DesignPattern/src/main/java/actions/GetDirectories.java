package actions;

import enums.ActionType;
import interfaces.Action;
import main.Client;
import requestResponse.Request;

import java.net.Socket;
import java.util.UUID;

public class GetDirectories implements Action {
    @Override
    public void action() {
        try {
            String uniqueId = UUID.randomUUID().toString();
            Request request = new Request(
                    ActionType.getAllDir.name(),
                    "directories",
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
