package com.webSocket

import clients.LatchedWebSocketClient
import org.apache.log4j.PropertyConfigurator
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer
import servlets.HelloWorldServlet
import java.util.*

val port = 8888
val endpoint = "/hello-world"

fun main(args: Array<String>) {
    configureLogging()
    startClient()
    startServer()
}

fun configureLogging() {
    val prop = Properties()
    prop.setProperty("log4j.rootLogger", "A1")
    prop.setProperty("log4j.rootLogger", "WARN")
    System.setProperty("org.eclipse.jetty.LEVEL", "WARN")
    PropertyConfigurator.configure(prop)
}

fun startServer() {
    println("Server configuration on port " + port)
    val server = Server(port)
    val contextHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
    contextHandler.contextPath = "/"
    contextHandler.addServlet(HelloWorldServlet::class.java, endpoint)
    JettyWebSocketServletContainerInitializer.configure(contextHandler, null)
    try {
        server.stopTimeout = Long.MAX_VALUE
        server.handler = contextHandler
        server.start()
        server.join()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun startClient() {
    Thread {
        try {
            Thread.sleep(2000)
            System.out.println("starting client")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val client = LatchedWebSocketClient()
        client.connectAndWait("ws://localhost:$port$endpoint")
        client.sendMessage("hello")
    }.start()
}
