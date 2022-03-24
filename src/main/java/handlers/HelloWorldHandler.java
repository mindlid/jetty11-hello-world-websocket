package handlers;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

@WebSocket
public class HelloWorldHandler {

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("WS Server received: " + message);
        if (session.isOpen()) {
            session.getRemote().sendString(message + " world");
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WS Server: a client was connected");
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println("WS Server: a client was disconnected");
    }

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        error.printStackTrace(System.err);
    }
}