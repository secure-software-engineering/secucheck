package de.fraunhofer.iem.secucheck.InternalFluentTQL.MethodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.typeAlias.TestTypeAliases;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestToString {
    @Test
    public void test1() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .parameter("java.lang.String")
                .parameter("java.lang.String")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, java.lang.String)>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, java.lang.String)",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test
    public void test2() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .parameter("java.lang.String")
                .parameter("_")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, _)>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, _)",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test
    public void test3() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize()>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize()",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test
    public void test4() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .parameter()
                .parameter()
                .parameter()
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize()>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize()",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test
    public void test5() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .parameter("Int", "Float", "Double")
                .parameter("List", "Byte")
                .parameter("kotlin.Int", "kotlin.Double")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize(int, float, double, java.util.List, byte, int, double)>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize(int, float, double, java.util.List, byte, int, double)",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test(expected = NullPointerException.class)
    public void test6() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter((String[]) null)
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .configure();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test7() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter((String[]) null)
                    .parameter("kotlin.Int", "kotlin.Double")
                    .configure();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test8() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter((String[]) null)
                    .configure();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test9() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", null, "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test10() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", null, "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test11() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", null, "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test12() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass(null)
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test13() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(null)
                    .atClass("dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test14() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("dummy")
                    .returns(null)
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test15() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder()
                    .atClass("dummy")
                    .returns("java.lang.String")
                    .named(null)
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test16() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter((String[]) null)
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .configure();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test17() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter((String[]) null)
                    .parameter("kotlin.Int", "kotlin.Double")
                    .configure();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test18() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter((String[]) null)
                    .configure();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test19() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", null, "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test20() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", null, "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test21() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("de.fraunhofer.iem.Dummy")
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", null, "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test22() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass(null)
                    .returns("java.lang.String")
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test24() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("dummy")
                    .returns(null)
                    .named("sanitize")
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test25() {
        try {
            MethodSignature methodSignature = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
                    .atClass("dummy")
                    .returns("java.lang.String")
                    .named(null)
                    .parameter("List", "Byte")
                    .parameter("kotlin.Int", "kotlin.Double")
                    .parameter("Int", "Float", "Double")
                    .configure();

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}
