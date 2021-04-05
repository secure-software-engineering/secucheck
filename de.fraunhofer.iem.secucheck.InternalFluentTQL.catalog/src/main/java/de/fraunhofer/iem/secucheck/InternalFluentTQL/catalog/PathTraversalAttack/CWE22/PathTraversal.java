package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.PathTraversalAttack.CWE22;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * This class contains the example of Path traversal attack.
 * This example has been taken from the OWASP Benchmark: BenchmarkTest00001 and modified slightly.
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://github.com/OWASP/Benchmark/blob/master/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java" target="_blank">BenchmarkTest00001</a>
 */
public class PathTraversal extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * This method returns the content of the file with the filename given by the user, this method does not use sanitizer,
     * therefore this method contains path traversal attack.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to load the file.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName = request.getParameter("fileName");

        ClassLoader classLoader = PathTraversal.class.getClassLoader();

        File file = new File(classLoader.getResource("user1/" + fileName)
                .getFile());

        FileInputStream myFIS = new FileInputStream(file);

        BufferedReader myBR = new BufferedReader(new InputStreamReader(myFIS));

        String resp = "";
        String singlLine = "";
        while ((singlLine = myBR.readLine()) != null) {
            resp += singlLine + "\n";
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(resp);
    }

    /**
     * This method returns the content of the file with the filename given by the user, This method uses sanitizer,
     * therefore this method avoids path traversal attack. This method uses a simple user defined sanitizer. There are different
     * ways to avoid path traversal, this method is not recommended to avoid the path traversal.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) {

        String resp = "";
        String fileName = request.getParameter("fileName");

        ClassLoader classLoader = PathTraversal.class.getClassLoader();

        try {
            fileName = sanitizeForPATH(fileName);

            File file = new File(classLoader.getResource("user1/" + fileName)
                    .getFile());

            FileInputStream myFIS = new FileInputStream(file);

            BufferedReader myBR = new BufferedReader(new InputStreamReader(myFIS));


            String singlLine = "";
            while ((singlLine = myBR.readLine()) != null) {
                resp += singlLine + "\n";
            }


        } catch (IOException e) {
            resp = "Invalid File name. Please enter valid file name.";
        } catch (NullPointerException e) {
            resp = "Invalid File name. Please enter valid file name.";
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().append(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method sanitizes the file name with the black list technique. It encoded ../ ..\ ./ .\
     * This method is not recommended to avoid path traversal attack, this is used as a example here.
     *
     * @param fileName File name.
     * @return sanitized file name.
     */
    public String sanitizeForPATH(String fileName) {

        String result = "";
        result = fileName.replaceAll("\\.\\./", "0x2e0x2e0x2f");
        result = result.replaceAll("\\.\\.\\\\", "0x2e0x2e0x5c");
        result = result.replaceAll("\\.\\\\", "0x2e0x5c");
        result = result.replaceAll("\\./", "0x2e0x2f");

        return result;
    }
}
