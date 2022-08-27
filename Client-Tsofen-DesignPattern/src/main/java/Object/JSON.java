package Object;

import enums.ActionType;
import enums.Keys;
import enums.Responses;
import enums.Words;
import interfaces.Adapter;
import org.json.JSONObject;
import requestResponse.Data;
import requestResponse.Request;
import requestResponse.Response;

public class JSON implements Adapter {

    @Override
    public String convertTo(Data data) {
        JSONObject main = new JSONObject();
        JSONObject header = new JSONObject();
        JSONObject body = new JSONObject();
       if(data instanceof Request request){
           JSONObject param = new JSONObject();
           param.put(Keys.actionType.name(),request.getActionType());
           param.put(Keys.fileName.name(), request.getFileName());
           param.put(Keys.contentType.name(), request.getContentType());
           header.put(Keys.requestId.name(), request.getRequestId());
           body.put(Keys.fileContent.name(), request.getFileContent());
           main.put(Words.param.name(),param);
       }
        if(data instanceof Response response){
            header.put(Keys.actionType.name(),response.getActionType());
            header.put(Keys.fileName.name(), response.getFileName());
            header.put(Keys.contentType.name(), response.getContentType());
            header.put(Keys.requestId.name(), response.getRequestId());
            header.put(Keys.response.name(),response.getResponses());
            body.put(Keys.fileContent.name(), response.getFileContent());
        }
        main.put(Words.header.name(),header);
        main.put(Words.body.name(), body);
        return JSONObject.valueToString(main);
    }

    @Override
    public Data convertFrom(String data) {
        JSONObject main = new JSONObject(data);
        JSONObject body=main.getJSONObject(Words.body.name());
        JSONObject header=main.getJSONObject(Words.header.name());
        if(main.has(Words.param.name())){
            JSONObject param=main.getJSONObject(Words.param.name());
            return new Request(
                    param.getString(Keys.actionType.name()),
                    param.getString(Keys.contentType.name()),
                    param.getString(Keys.fileName.name()),
                    header.getString(Keys.requestId.name()),
                    body.getString(Keys.fileContent.name()));
        }
        else{
            return new Response(
                    header.getString(Keys.actionType.name()),
                    header.getString(Keys.contentType.name()),
                    header.getString(Keys.fileName.name()),
                    header.getString(Keys.requestId.name()),
                    body.getString(Keys.fileContent.name()),
                    header.getString(Keys.response.name())
            );


        }

    }

}
