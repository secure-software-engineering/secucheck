package de.fraunhofer.iem.secucheck.specifications.FuentTQLRepositories.GeneralPropagators;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.GeneralPropagator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class AdditionalGP {
    @GeneralPropagator
    public static Method classLoaderResource = new MethodConfigurator("java.lang.ClassLoader: java.net.URL getResource(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method getFile = new MethodConfigurator("java.net.URL: java.lang.String getFile()")
            .in().thisObject()
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method stringReader = new MethodConfigurator("java.io.StringReader: void <init>(java.lang.String)")
            .in().param(0)
            .out().thisObject()
            .configure();

    @GeneralPropagator
    public static Method inputSource = new MethodConfigurator("org.xml.sax.InputSource: void <init>(java.io.Reader)")
            .in().param(0)
            .out().thisObject()
            .configure();
}
