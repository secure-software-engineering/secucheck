package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sources;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Multiple Source definition for Servlet application
 *
 * @author Ranjith Krishnamurthy
 * @author Goran
 */
public class ServletSources {
    //Below are methods for Servlet application that reads input from the user.

    public static Method source1 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getParameter(java.lang.String)")
            .out().returnValue()
            .configure();

    public static Method source2 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.util.Map getParameterMap()")
            .out().returnValue()
            .configure();

    public static Method source3 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String[] getParameterValues(java.lang.String)")
            .out().returnValue()
            .configure();

    public static Method source4 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.util.Enumeration getParameterNames()")
            .out().returnValue()
            .configure();

    public static Method source5 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getRequestedSessionId()")
            .out().returnValue()
            .configure();

   public static Method source6 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getQueryString()")
            .out().returnValue()
            .configure();

    public static Method source7 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getRemoteUser()")
            .out().returnValue()
            .configure();

    public static Method source8 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: javax.servlet.http.Cookie[] getCookies()")
            .out().returnValue()
            .configure();

    public static Method source9 = new MethodConfigurator("javax.servlet.ServletRequestWrapper: java.lang.String getParameter(java.lang.String)")
            .out().returnValue()
            .configure();

    public static Method source10 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: javax.servlet.ServletInputStream getInputStream()")
            .out().returnValue()
            .configure();

    public static Method source11 = new MethodConfigurator("javax.servlet.ServletRequestWrapper: javax.servlet.ServletInputStream getInputStream()")
            .out().returnValue()
            .configure();

    public static Method source12 = new MethodConfigurator("javax.servlet.http.HttpSession: java.lang.String getId()")
            .out().returnValue()
            .configure();

    //Below are methods for header manipulation in Servlet application.
    public static Method source13 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getScheme()")
            .out().returnValue()
            .configure();

    public static Method source14 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getProtocol()")
            .out().returnValue()
            .configure();

    public static Method source15 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getContentType()")
            .out().returnValue()
            .configure();

    public static Method source16 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getServerName()")
            .out().returnValue()
            .configure();

    public static Method source17 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getRemoteAddr()")
            .out().returnValue()
            .configure();

    public static Method source18 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getRemoteHost()")
            .out().returnValue()
            .configure();

    public static Method source19 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getRealPath()")
            .out().returnValue()
            .configure();

    public static Method source20 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getLocalName()")
            .out().returnValue()
            .configure();

    public static Method source21 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getLocalAddr()")
            .out().returnValue()
            .configure();

    public static Method source22 = new MethodConfigurator("javax.servlet.ServletRequest: java.lang.String getAuthType()")
            .out().returnValue()
            .configure();

    public static Method source23 = new MethodConfigurator("javax.servlet.ServletRequest: javax.servlet.RequestDispatcher getRequestDispatcher(java.lang.String)")
            .out().returnValue()
            .configure();

    public static Method source24 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getQueryString()")
            .out().returnValue()
            .configure();

    public static Method source25 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getRemoteUser()")
            .out().returnValue()
            .configure();

    public static Method source26 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: int getContentLength()")
            .out().returnValue()
            .configure();

    public static Method source27 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getContentType()")
            .out().returnValue()
            .configure();

    public static Method source28 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: java.lang.String getMethod()")
            .out().returnValue()
            .configure();

    /**
     * This MethodSet contains some of the source methods in Servlet application.
     */
    public static MethodSet servletSources = new MethodSet("servletSources")
            .addMethod(source1)
            .addMethod(source2)
            .addMethod(source3)
            .addMethod(source4)
            .addMethod(source5)
            .addMethod(source6)
            .addMethod(source7)
            .addMethod(source8)
            .addMethod(source9)
            .addMethod(source10)
            .addMethod(source11)
            .addMethod(source12)
            .addMethod(source13)
            .addMethod(source14)
            .addMethod(source15)
            .addMethod(source16)
            .addMethod(source17)
            .addMethod(source18)
            .addMethod(source19)
            .addMethod(source20)
            .addMethod(source21)
            .addMethod(source22)
            .addMethod(source23)
            .addMethod(source24)
            .addMethod(source25)
            .addMethod(source26)
            .addMethod(source27)
            .addMethod(source28);
}
