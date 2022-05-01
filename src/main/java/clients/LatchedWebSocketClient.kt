package clients

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.*
import java.util.concurrent.CountDownLatch
import org.eclipse.jetty.websocket.client.WebSocketClient
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest
import java.io.IOException
import java.lang.Exception
import kotlin.Throws
import java.lang.InterruptedException
import java.net.URI
import java.time.Duration

@WebSocket(idleTimeout = Int.MAX_VALUE)
class LatchedWebSocketClient {
    private var session: Session? = null
    val latch = CountDownLatch(1)

    init {
        println("WS Client init")
    }

    fun connectAndWait(direction: String?) {
        val client = WebSocketClient()
        try {
            client.start()
            val uri = URI(direction)
            println("WS Client trying to connect")
            val request = ClientUpgradeRequest()
            client.connect(this, uri, request)
            waitUntilConnection()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OnWebSocketError
    fun onError(session: Session?, error: Throwable) {
        System.err.println("WS Client: " + error.message)
    }

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("WS Client connected")
        session.policy.idleTimeout = Duration.ofHours(24)
        session.idleTimeout = Duration.ofHours(24)
        this.session = session
        latch.countDown()
    }

    @OnWebSocketMessage
    fun onMessage(session: Session?, message: String) {
        println("WS Client received: $message")
    }

    @OnWebSocketClose
    fun onClose(session: Session?, statusCode: Int, reason: String?) {
        println("WS Client closing")
    }

    fun sendMessage(str: String?) {
        try {
            session!!.remote.sendString(str)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(InterruptedException::class)
    fun waitUntilConnection() {
        latch.await()
    }
}