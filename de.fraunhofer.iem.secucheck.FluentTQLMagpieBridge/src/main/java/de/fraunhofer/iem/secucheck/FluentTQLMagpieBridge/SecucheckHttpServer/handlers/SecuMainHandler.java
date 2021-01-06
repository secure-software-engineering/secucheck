package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.handlers;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.utility.FreeMarkerUtility;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.utility.ResourceUtility;

@SuppressWarnings("restriction")
public class SecuMainHandler implements HttpHandler {

    private static final int HTTP_OK_STATUS = 200;

    public void handle(HttpExchange t) throws IOException {
        if (FluentTQLAnalysisConfigurator.isNullJavaProjectService()) {
            FluentTQLAnalysisConfigurator.setProjectInformation();
        }

        String response = "";

        URI uri = t.getRequestURI();
        String path = uri.getPath();

        OutputStream os = t.getResponseBody();

        switch (path) {
            case "/specPathRequest":
                response = FreeMarkerUtility.setFirstPageFile(
                        FluentTQLAnalysisConfigurator.getSource());
                t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
                os.write(response.getBytes());
                os.close();
                FluentTQLAnalysisConfigurator.setCurrentConfigHtmlPage(response);
                break;

            case "/static/app.css":
                response = ResourceUtility.getResourceAsString("static/app.css");
                t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
                os.write(response.getBytes());
                os.close();
                break;

            case "/img/secu.png":
                File imageFile = ResourceUtility.getResourceAsFile("img/secu.png");
                t.sendResponseHeaders(HTTP_OK_STATUS, imageFile.length());
                Files.copy(imageFile.toPath(), os);
                break;
        }
    }

/*
    private String checkSpecPage(URI uri) {
        System.out.println(getResource("firstpage.ftl"));
        return getResource("firstpage.ftl");

        String fName = "";
        String lName = "";
        //Get the request query
        System.out.println();
        String query = uri.getQuery();
        System.out.println("Query = " + query);
        if (query != null) {
            System.out.println("Query: " + query);
            String[] queryParams = query.split(AND_DELIMITER);
            if (queryParams.length > 0) {
                for (String qParam : queryParams) {
                    String[] param = qParam.split(EQUAL_DELIMITER);
                    if (param.length > 0) {
                        for (int i = 0; i < param.length; i++) {
                            if (F_NAME.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
                                fName = param[PARAM_VALUE_IDX];
                            }
                            if (L_NAME.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
                                lName = param[PARAM_VALUE_IDX];
                            }
                        }
                    }
                }
            }
        }
    }
*/
}