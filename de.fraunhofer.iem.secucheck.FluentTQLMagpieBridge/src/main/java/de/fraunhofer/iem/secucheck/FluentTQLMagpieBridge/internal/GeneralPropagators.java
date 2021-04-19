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

    public static Method stringReader = new MethodConfigurator("java.io.StringReader: void <init>(java.lang.String)")
            .in().param(0)
            .out().thisObject()
            .configure();

    public static Method inputSource = new MethodConfigurator("org.xml.sax.InputSource: void <init>(java.io.Reader)")
            .in().param(0)
            .out().thisObject()
            .configure();

    //TODO: Check and model correctly to handle Java 11 dynamic calls for string concat
    static Method JAVA_11_STR_CONCAT1 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    static Method JAVA_11_STR_CONCAT2 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String)")
            .in().param(0).param(1)
            .out().returnValue()
            .configure();

    static Method JAVA_11_STR_CONCAT3 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String,java.lang.String)")
            .in().param(0).param(1).param(2)
            .out().returnValue()
            .configure();

    static Method JAVA_11_STR_CONCAT4 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String,java.lang.String,java.lang.String)")
            .in().param(0).param(1).param(2).param(3)
            .out().returnValue()
            .configure();

    static Method JAVA_11_STR_CONCAT5 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)")
            .in().param(0).param(1).param(2).param(3).param(4)
            .out().returnValue()
            .configure();


    public static Method kotlinSB = new MethodConfigurator("kotlin.jvm.internal.Intrinsics: java.lang.String stringPlus(java.lang.String,java.lang.Object)")
            .in().param(0).param(1)
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
        gp.add(Utility.getMethodImpl(stringReader));
        gp.add(Utility.getMethodImpl(inputSource));
        gp.add(Utility.getMethodImpl(JAVA_11_STR_CONCAT1));
        gp.add(Utility.getMethodImpl(JAVA_11_STR_CONCAT2));
        gp.add(Utility.getMethodImpl(JAVA_11_STR_CONCAT3));
        gp.add(Utility.getMethodImpl(JAVA_11_STR_CONCAT4));
        gp.add(Utility.getMethodImpl(JAVA_11_STR_CONCAT5));
        gp.add(Utility.getMethodImpl(kotlinSB));

        return gp;
    }
}
