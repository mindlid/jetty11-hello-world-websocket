package handlers

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import java.io.IOException

@WebSocket
class HelloWorldHandlerAdapter : WebSocketAdapter() {

    override fun onWebSocketConnect(sess: Session) {
        super.onWebSocketConnect(sess)
        println("WS Server: a client was connected")
    }

    override fun onWebSocketText(message: String) {
        super.onWebSocketText(message)
        println("WS Server received: $message")
        if (session.isOpen) {
            try {
                remote.sendString("$message world")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onWebSocketClose(statusCode: Int, reason: String) {
        println("WS Server: a client was disconnected")
    }
}