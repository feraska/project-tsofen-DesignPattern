package interfaces;

import requestResponse.Request;

import java.io.PrintWriter;
import java.net.Socket;

public interface Action {
    void action(PrintWriter printWriter, Request request);
}
