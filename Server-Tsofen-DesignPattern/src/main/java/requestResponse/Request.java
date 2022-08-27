package requestResponse;

import enums.ActionType;

public class Request extends Data{


    public Request(String actionType, String contentType, String fileName, String requestId, String fileContent) {
        super(actionType, contentType, fileName, requestId, fileContent);
    }
}
