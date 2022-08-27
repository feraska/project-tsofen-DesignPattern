package actions;

import enums.ActionType;
import interfaces.Action;
import main.Client;
import requestResponse.Request;

import java.util.UUID;

public class Move implements Action {
    @Override
    public void action() {
        Client.print("enter source to move");
        String source = Client.input();
        Client.print("enter destination to move");
        String destination = Client.input();
        String uniqueId = UUID.randomUUID().toString();
        Request request = new Request(
                ActionType.move.name(),
                "",
                source+":"+destination,
                uniqueId,
                ""
        );
        Client.readwriteData(request);
    }
}
