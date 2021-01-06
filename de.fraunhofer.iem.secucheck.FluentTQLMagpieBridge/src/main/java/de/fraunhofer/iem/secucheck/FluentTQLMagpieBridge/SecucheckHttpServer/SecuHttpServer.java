package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.handlers.ConfigSubmissionHandler;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.handlers.SecuMainHandler;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.handlers.SpecPathResponseHandler;

@SuppressWarnings("restriction")
public class SecuHttpServer {

    private HttpServer httpServer;

    public SecuHttpServer() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(0), 0);

            httpServer.createContext("/specPathRequest", new SecuMainHandler());
            httpServer.createContext("/static/app.css", new SecuMainHandler());
            httpServer.createContext("/img/secu.png", new SecuMainHandler());
            httpServer.createContext("/specPathResponse", new SpecPathResponseHandler());
            httpServer.createContext("/configSubmit", new ConfigSubmissionHandler());
            httpServer.setExecutor(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String start() {
        this.httpServer.start();
        return "http://localhost:" + this.httpServer.getAddress().getPort() + "/specPathRequest";
    }
}