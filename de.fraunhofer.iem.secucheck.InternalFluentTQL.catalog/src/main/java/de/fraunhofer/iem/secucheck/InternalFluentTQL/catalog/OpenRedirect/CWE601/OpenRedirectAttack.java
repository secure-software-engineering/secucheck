package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.OpenRedirect.CWE601;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class contains the example of Open-Redirect attack, this example has been taken from the CWE-601.
 * To avoid Open-Redirect, the best practice is to, not let the user provide the URL, even if they provide the URL,
 * make sure to have a entry in the server that maps the URL to correct URL and then redirects otherwise do not redirect.
 * In this example, we have consider redirectTable method as a sanitizer that maps the user input into valid URL, this techniques
 * similar to Whitelist input validation.
 *
 * @author Ranjith Krishnamurthy
 * @see <a href="https://cwe.mitre.org/data/definitions/601.html" target="_blank">CWE-601</a>
 */
public class OpenRedirectAttack extends HttpServlet {
    /**
     * This method redirects the page to requested URL. This does not use any sanitizer like redirectTable,
     * therefore this contains Open-Redirect attack.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) {

        String redirectLink = request.getParameter("redirectLink");

        try {
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", redirectLink);
            response.getWriter().append("Redirecting to: " + redirectLink);
            response.sendRedirect(redirectLink);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method redirects the page to requested URL. This uses sanitizer like redirectTable,
     * therefore this avoids Open-Redirect attack.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) {

        String param = request.getParameter("redirectLink");
        String redirectLink = redirectTable(param);

        if (redirectLink != null) {
            try {
                response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
                response.setHeader("Location", redirectLink);
                response.getWriter().write("Redirecting to: " + redirectLink);
                response.sendRedirect(redirectLink);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try {

                response.setContentType("text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().append("Invalid URL: " + param);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This is a simple redirect table that maps the user provided url or url-code to valid URL. Anything other
     * than the values entered in the table will be considered as invalid input and redirection will not happen.
     *
     * @param redirectLink URL or URL-code provided by the user.
     * @return Returns the valid URL otherwise null.
     */
    public String redirectTable(String redirectLink) {
        if ("secureMyPage".equals(redirectLink) || "http://www.myApp.com/secureMyPage".equals(redirectLink))
            return "http://www.myApp.com/secureMyPage";

        if ("trustedPage".equals(redirectLink) || "http://www.myApp.com/trustedPage".equals(redirectLink))
            return "http://www.myApp.com/secureMyPage";

        if ("belongsToThisWebSite".equals(redirectLink) || "http://www.myApp.com/belongsToThisWebSite".equals(redirectLink))
            return "http://www.myApp.com/secureMyPage";

        return null;
    }

}
