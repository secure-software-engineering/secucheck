package de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.SecucheckHttpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.SecucheckHttpServer.utility.InputStreamUtility;
import de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.SecucheckHttpServer.utility.PrintUtility;
import de.fraunhofer.iem.secucheck.analysis.query.Solver;
import org.eclipse.lsp4j.MessageType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigSubmissionHandler implements HttpHandler {
/*    private static String getStringFromInputStream(java.io.InputStream inputStream) {

        System.out.println("Entered -----> 1");
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {

            System.out.println("Entered -----> 2");
            br = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Entered -----> 3");
            while ((line = br.readLine()) != null) {
                System.out.println("Entered -----> inf");
                sb.append(line);
            }
            System.out.println("Entered -----> 4");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Entered -----> 5");
        return sb.toString();
    }*/

    public void handle(HttpExchange t) throws IOException {
        if (FluentTQLAnalysisConfigurator.isNullJavaProjectService()) {
            FluentTQLAnalysisConfigurator.setProjectInformation();
        }

        Map<String, String> settings = InputStreamUtility.getOptionsFromInputStream(t.getRequestBody());

        Map<String, String> specSettings = settings.entrySet()
                .stream()
                .filter(map -> map.getKey().endsWith("-specs"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, String> entryPointSettings = settings.entrySet()
                .stream()
                .filter(map -> map.getKey().endsWith("-entryPoint"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        boolean isSuccess1 = FluentTQLAnalysisConfigurator.processFluentTQLSpecificationFiles(specSettings.keySet());
        boolean isSuccess2 = FluentTQLAnalysisConfigurator.processJavaFiles(entryPointSettings.keySet());
        FluentTQLAnalysisConfigurator.printForNow();

        String entryPointSettings1;

        for (Map.Entry<String, String> entry : settings.entrySet()) {
            if (entry.getKey().endsWith("solverOption")) {
                entryPointSettings1 = entry.getValue();

                if (entryPointSettings1.equals("boomerang"))
                    FluentTQLAnalysisConfigurator.setAnalysisSolver(Solver.BOOMERANG3);
                else if (entryPointSettings1.equals("flowdroid"))
                    FluentTQLAnalysisConfigurator.setAnalysisSolver(Solver.FLOWDROID);
                else
                    FluentTQLAnalysisConfigurator.setAnalysisSolver(Solver.BOOMERANG3);
            }
        }

        if (isSuccess1 && isSuccess2) {
            PrintUtility.printMessageInIDE(MessageType.Info,
                    "Configuration Submitted Successfully!!!");
        }

        OutputStream os = t.getResponseBody();
        t.getResponseHeaders().add("Cache-Control", "no-cache, no-store, must-revalidate");
        t.getResponseHeaders().add("Pragma", "no-cache");
        t.getResponseHeaders().add("Expires", "0");
        t.sendResponseHeaders(200, 0);
        os.close();
        /*
        System.out.println("Entered here!!!!");
        if (CurrentProjectInformation.isNullJavaProjectService()) {
            CurrentProjectInformation.setProjectInformation();
        }

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(t.getRequestBody()));
        String body = "";
        try {
            String temp = "";
            System.out.println("First read = " + reader.readLine());
            while ((temp = reader.readLine()) != null) {
                body += temp;
            }
         //   body = URLDecoder.decode(reader.lines().collect(Collectors.joining()), "UTF-8");
            reader.close();

            Map<String, String> requestOptions = new HashMap<>();
            String[] options = body.split("&");
            for (String option : options) {
                String[] pairs = option.split("=");
                if (pairs.length > 1) {
                    String key = pairs[0];
                    String value = pairs[1];
                    requestOptions.put(key, value);
                }
            }

            System.out.println("Second Check this = " + requestOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(body);
         */
   /*     OutputStream os = t.getResponseBody();
        String response = "";

        response = FluentTQLAnalysisConfigurator.getCurrentConfigHtmlPage();
        t.sendResponseHeaders(200, response.getBytes().length);
        os.write(response.getBytes());
        os.close();*/
    }
}
