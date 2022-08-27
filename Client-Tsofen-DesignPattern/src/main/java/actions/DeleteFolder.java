package actions;

import enums.ActionType;
import interfaces.Action;
import main.Client;
import requestResponse.Request;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class DeleteFolder implements Action {
    @Override
    public void action() {
        try {
            Client.print("enter directory from sever to delete");
            String dir = Client.input();
            Path off = Path.of(dir);
            String contentType = Files.probeContentType(off);
            if(contentType==null){
                contentType="not file";
            }
            String uniqueId = UUID.randomUUID().toString();
            Request request = new Request(
                    ActionType.deleteDir.name(),
                    contentType,
                    dir,
                    uniqueId,
                    ""

            );
                Client.readwriteData(request);

        }
        catch (Exception e){

        }
    }
}
