package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.TrustBoundaryViolation.CWE501;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class contains the example for Trust boundary violation.
 *
 * @author Ranjith Krishnamurthy
 */
public class TrustBoundaryViolation {

    /**
     * This is considered as a sanitizer that authenticate the user before setting the attribute "user" in session object.
     * If the authentication fails then it returns null so that tainted values will not reach the sinks.
     *
     * @param user User name
     * @param pass Password
     * @return Authentication successfull or not.
     */
    private String authenticate(String user, String pass) {
        if ("admin".equals(user)) {
            if ("mypass".equals(pass)) {
                return user;
            }
        }
        return null;
    }

    /**
     * A simple request from the user that has to be called by the user after the authentication is successfull.
     * If the authentication is not used and session object is set then this method can still be accessed with
     * invalid username this is because of the trust boundary violation attack.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to write the response.
     */
    public void requestAfterAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession mySession = request.getSession();

        if (mySession.getAttribute("user") != null) {

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append("Successfully processed the request.");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append("Please login to process this request");
        }
    }

    /**
     * A simple request from the user that authenticate and set the session object with "user" attribute.
     * Since authenticate is used here trust boundary violation is avoided.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to write the response.
     */
    public void doGetWithSanitizer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession mySession = request.getSession();
        mySession.getId();
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String resp = "";

        if (mySession.getAttribute("user") == null) {

            String sanitizedUser = authenticate(user, pass);
            mySession.setAttribute("user", sanitizedUser);

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        } else {
            if (mySession.getAttribute("user").equals(user)) {
                resp = "Already logged in.";
            } else {
                resp = "Invalid username!! Please log in again";
            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        }
    }

    /**
     * A simple request from the user that authenticate and set the session object with "user" attribute.
     * Since authenticate is not used to set the session object here. Independent of authentication result session object is set.
     * Therefore trust boundary violation will happen.
     *
     * @param request  HttpServlet request from the user.
     * @param response HttpServlet response to the user.
     * @throws IOException If fails to write the response.
     */
    public void doGetWithoutSanitizer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession mySession = request.getSession();
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String resp = "";

        if (mySession.getAttribute("user") == null) {

            mySession.setAttribute("user", user);

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        } else {
            if (mySession.getAttribute("user").equals(user)) {
                resp = "Already logged in.";
            } else {
                resp = "Invalid username!! Please log in again";
            }

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(resp);
        }
    }
}