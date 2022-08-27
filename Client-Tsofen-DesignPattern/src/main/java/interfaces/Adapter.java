package interfaces;

import requestResponse.Data;

public interface Adapter {
    String convertTo(Data data);
    Data convertFrom(String data);
}
