package de.fraunhofer.iem.secucheck.magpiebridge.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fraunhofer.iem.secucheck.magpiebridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.magpiebridge.http.utility.FreeMarkerUtility;
import de.fraunhofer.iem.secucheck.magpiebridge.http.utility.InputStreamUtility;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class SpecPathResponseHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        if (FluentTQLAnalysisConfigurator.isNullJavaProjectService()) {
            FluentTQLAnalysisConfigurator.setProjectInformation();
        }

        Map<String, String> requestOptions = InputStreamUtility.getOptionsFromInputStream(t.getRequestBody());

        String displayFormat = requestOptions.get("displayFormat");

        boolean isSuccess = FluentTQLAnalysisConfigurator.processFluentTQLSpecificationsPath(requestOptions.get("specPath"),
                "displayAsSpecFile".equals(displayFormat));

        OutputStream os = t.getResponseBody();
        String response = "";

        if (isSuccess) {
            String specDiv = FluentTQLAnalysisConfigurator.setConfig();
            String entryPointDiv = FluentTQLAnalysisConfigurator.setConfigWithJavaFiles();
            response = FreeMarkerUtility.setSecondPageFile(FluentTQLAnalysisConfigurator.getSource(), specDiv, entryPointDiv);
        } else {
            response = FluentTQLAnalysisConfigurator.getCurrentConfigHtmlPage();
        }

        t.getResponseHeaders().add("Cache-Control", "no-cache, no-store, must-revalidate");
        t.getResponseHeaders().add("Pragma", "no-cache");
        t.getResponseHeaders().add("Expires", "0");
        t.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
        os.close();
        FluentTQLAnalysisConfigurator.setCurrentConfigHtmlPage(response);
    }
}
