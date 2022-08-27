package requestResponse;

import enums.ActionType;
import enums.Responses;

public class Response extends Data {
    private String responses;

    public Response(String actionType, String contentType, String fileName, String requestId, String fileContent, String responses) {
        super(actionType, contentType, fileName, requestId, fileContent);
        this.responses=responses;
    }


    public String getResponses() {
        return responses;
    }
}
