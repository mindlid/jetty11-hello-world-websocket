package servlets

import jakarta.servlet.annotation.WebServlet
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory
import handlers.HelloWorldHandler
import handlers.HelloWorldHandlerAdapter
import java.time.Duration

@WebServlet(name = "WebSocket Servlet", urlPatterns = ["/reverse"])
class HelloWorldServlet : JettyWebSocketServlet() {
    override fun configure(factory: JettyWebSocketServletFactory) {
        // set session idle timeout
        println("Configured webapp servlet")
        factory.idleTimeout = Duration.ofHours(24)
        // register MyEchoSocket as the WebSocket to create on Upgrade
        factory.register(HelloWorldHandlerAdapter::class.java)
    }
}