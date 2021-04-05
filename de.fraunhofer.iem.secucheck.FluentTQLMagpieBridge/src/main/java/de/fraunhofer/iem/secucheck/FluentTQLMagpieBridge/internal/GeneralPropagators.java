package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.analysis.query.MethodImpl;

import java.util.ArrayList;
import java.util.List;

public class GeneralPropagators {

    static Method S_VALUE_OF = new MethodConfigurator("java.lang.String: java.lang.String valueOf(java.lang.Object)")
            .in().param(0)
            .out().returnValue()
            .configure();

    static Method SB_TO_STRING = new MethodConfigurator("java.lang.StringBuilder: java.lang.String toString()")
            .in().thisObject()
            .out().returnValue()
            .configure();

    static Method SB_APPEND = new MethodConfigurator("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)")
            .in().thisObject().param(0)
            .out().returnValue()
            .configure();

    static Method SB_INIT = new MethodConfigurator("java.lang.StringBuilder: void <init>(java.lang.String)")
            .in().param(0)
            .out().thisObject()
            .configure();

    public static Method classLoaderResource = new MethodConfigurator("java.lang.ClassLoader: java.net.URL getResource(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    public static Method getFile = new MethodConfigurator("java.net.URL: java.lang.String getFile()")
            .in().thisObject()
            .out().returnValue()
            .configure();

    public static List<MethodImpl> getGP() {
        List<MethodImpl> gp = new ArrayList<>();

        gp.add(Utility.getMethodImpl(S_VALUE_OF));
        gp.add(Utility.getMethodImpl(SB_TO_STRING));
        gp.add(Utility.getMethodImpl(SB_APPEND));
        gp.add(Utility.getMethodImpl(SB_INIT));
        gp.add(Utility.getMethodImpl(classLoaderResource));
        gp.add(Utility.getMethodImpl(getFile));

        return gp;
    }
}
