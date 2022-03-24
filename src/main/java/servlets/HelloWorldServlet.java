package servlets;

import jakarta.servlet.annotation.WebServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

import java.time.Duration;

import handlers.HelloWorldHandler;

@SuppressWarnings("serial")
@WebServlet(name = "WebSocket Servlet", urlPatterns = {"/reverse"})
public class HelloWorldServlet extends JettyWebSocketServlet {

    @Override
    protected void configure(JettyWebSocketServletFactory factory) {
        // set session idle timeout
        System.out.println("Configured webapp servlet");
        factory.setIdleTimeout(Duration.ofHours(24));
        // register MyEchoSocket as the WebSocket to create on Upgrade
        factory.register(HelloWorldHandler.class);
    }
}


