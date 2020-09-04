package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.CommandInjection.CWE77;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SuppressLibraryOutput;

/**
 * This class contains the example for command injection.
 * This example has been taken from the OWASP Benchmark: BenchmarkTest00051.
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://github.com/OWASP/Benchmark/blob/master/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00051.java" target="_blank">BenchmarkTest00051</a>
 */
public class CommandInjection extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * This method runs the simple echo command with the message given by the user.
     * This method does not use any sanitizer, therefore, this method contains command injection attack.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");

        String param = request.getParameter("message");

        String a1 = "";
        String a2 = "";
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe";
            a2 = "/c";
        } else {
            a1 = "sh";
            a2 = "-c";
        }
        String[] args = {a1, a2, "echo " + param};

        ProcessBuilder pb = new ProcessBuilder(args);

        try {
            Process p = pb.start();
            printOSCommandResults(p, response);
        } catch (IOException e) {
            System.out.println("Problem executing cmdi - java.lang.ProcessBuilder(java.lang.String[]) Test Case");
        }
    }

    /**
     * This method runs the simple echo command with the message given by the user.
     * This method uses sanitizer, therefore, this method avoids command injection attack.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to load ESAPI properties file.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String param = sanitizeForCommand(request.getParameter("message"));

        System.out.println("Encoded = " + param);
        String a1 = "";
        String a2 = "";
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe";
            a2 = "/c";
        } else {
            a1 = "sh";
            a2 = "-c";
        }
        String[] args = {a1, a2, "echo " + param};

        ProcessBuilder pb = new ProcessBuilder(args);

        try {
            Process p = pb.start();
            printOSCommandResults(p, response);
        } catch (IOException e) {
            System.out.println("Problem executing cmdi - java.lang.ProcessBuilder(java.lang.String[]) Test Case");
        }
    }

    /*
     * This method creates a simple HTML response with the output of the command.
     */
    private static void printOSCommandResults(java.lang.Process proc, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.write("<html>\r\n" +
                "	<head>\r\n" +
                "		<meta charset=\"UTF-8\">\r\n" +
                "		<title>Your first echo command result</title>\r\n" +
                "	</head>\r\n" +
                "	<body>\r\n" +
                "		<p>\r\n" +
                "			Here is the standard output of the command:<br>");

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        try {
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                out.write(s);
                out.write("<br>");
            }

            out.write("<br>Here is the std err of the command (if any):<br>");
            while ((s = stdError.readLine()) != null) {
                out.write(s);
                out.write("<br>");
            }

            out.write("<br>		</p>\r\n" +
                    "	</body>\r\n" +
                    "</html>");
        } catch (IOException e) {
            System.out.println("An error occurred while printOSCommandResults");
        }
    }

    /**
     * This method uses OWASP ESAPI to sanitize the user input for the OS to avoid command injection.
     *
     * @param message Message.
     * @return sanitized message.
     * @throws IOException If fails to load ESAPI properties file.
     * @see <a href="https://www.javadoc.io/doc/org.owasp.esapi/esapi/2.0.1/org/owasp/esapi/Encoder.html#encodeForOS(org.owasp.esapi.codecs.Codec,%20java.lang.String)" target="_blank">encodeForOS</a>
     */
    public String sanitizeForCommand(String message) throws IOException {

        SuppressLibraryOutput.SupressOutput();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ESAPI.properties").getFile());

        DefaultSecurityConfiguration ob = new DefaultSecurityConfiguration();
        ob.setResourceDirectory(file.getAbsolutePath());

        String result = "";
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            result = ESAPI.encoder().encodeForOS(new WindowsCodec(), message);
        } else {
            result = ESAPI.encoder().encodeForOS(new UnixCodec(), message);
        }


        SuppressLibraryOutput.Restore();

        return result;
    }
}
