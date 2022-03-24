package clients;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

@WebSocket(idleTimeout = Integer.MAX_VALUE)
public class LatchedWebSocketClient {
    private Session session;

    final CountDownLatch latch = new CountDownLatch(1);

    public LatchedWebSocketClient() {
        System.out.println("WS Client init");
    }

    public void connectAndWait(String direction) {
        WebSocketClient client = new WebSocketClient();
        try {
            client.start();
            URI uri = new URI(direction);
            System.out.println("WS Client trying to connect");
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(this, uri, request);
            waitUntilConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        System.err.println("WS Client: " + error.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WS Client connected");
        session.getPolicy().setIdleTimeout(Duration.ofHours(24));
        session.setIdleTimeout(Duration.ofHours(24));
        this.session = session;
        latch.countDown();
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("WS Client received: " + message);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("WS Client closing");
    }

    public void sendMessage(String str) {
        try {
            session.getRemote().sendString(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitUntilConnection() throws InterruptedException {
        latch.await();
    }
}
