package requestResponse;

import enums.ActionType;

public abstract class Data {
    protected String actionType;
    protected String contentType;
    protected String fileName;
    protected String requestId;
    protected String fileContent;

    protected Data(String actionType, String contentType, String fileName, String requestId, String fileContent) {
        this.actionType = actionType;
        this.contentType = contentType;
        this.fileName = fileName;
        this.requestId = requestId;
        this.fileContent = fileContent;
    }

    public String getActionType() {
        return actionType;
    }

    public String getFileContent() {
        return fileContent;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRequestId() {
        return requestId;
    }
}
