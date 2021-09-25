package de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Kotlin's data types transformer, Utility to transform the Kotlin's data type to Kotlin/JVM platform data types
 *
 * @author Ranjith Krishnamurthy
 */
public class KotlinDataTypeTransformer {
    private static final HashMap<String, String> typeMatcher = new HashMap<>();

    private static final Pattern FUNCTION_0_PATTERN = Pattern.compile("\\(\\)->[^->,]+");
    private static final Pattern FUNCTION_1_PATTERN = Pattern.compile("\\([^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_2_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_3_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_4_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_5_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_6_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_7_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_8_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_9_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_10_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_11_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_12_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_13_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_14_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_15_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_16_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_17_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_18_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_19_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_20_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_21_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_22_PATTERN = Pattern.compile("\\([^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+,[^->,]+\\)->[^->,]+");
    private static final Pattern FUNCTION_N_PATTERN = Pattern.compile("\\(([^->,]+,){22,}[^->,]+\\)->[^->,]+");
    private static final Map<Pattern, String> KOTLIN_FUNCTION_TYPE_PATTERN = new LinkedHashMap<>();

    static {
        typeMatcher.put("kotlin.Byte", "byte");
        typeMatcher.put("kotlin.Short", "short");
        typeMatcher.put("kotlin.Int", "int");
        typeMatcher.put("kotlin.Long", "long");
        typeMatcher.put("kotlin.Char", "char");
        typeMatcher.put("kotlin.Float", "float");
        typeMatcher.put("kotlin.Double", "double");
        typeMatcher.put("kotlin.Boolean", "boolean");

        typeMatcher.put("Byte", "byte");
        typeMatcher.put("Short", "short");
        typeMatcher.put("Int", "int");
        typeMatcher.put("Long", "long");
        typeMatcher.put("Char", "char");
        typeMatcher.put("Float", "float");
        typeMatcher.put("Double", "double");
        typeMatcher.put("Boolean", "boolean");

        typeMatcher.put("kotlin.Byte?", "java.lang.Byte");
        typeMatcher.put("kotlin.Short?", "java.lang.short");
        typeMatcher.put("kotlin.Int?", "java.lang.Int");
        typeMatcher.put("kotlin.Long?", "java.lang.Long");
        typeMatcher.put("kotlin.Char?", "java.lang.Char");
        typeMatcher.put("kotlin.Float?", "java.lang.Float");
        typeMatcher.put("kotlin.Double?", "java.lang.Double");
        typeMatcher.put("kotlin.Boolean?", "java.lang.Boolean");

        typeMatcher.put("Byte?", "java.lang.Byte");
        typeMatcher.put("Short?", "java.lang.Short");
        typeMatcher.put("Int?", "java.lang.Int");
        typeMatcher.put("Long?", "java.lang.Long");
        typeMatcher.put("Char?", "java.lang.Char");
        typeMatcher.put("Float?", "java.lang.Float");
        typeMatcher.put("Double?", "java.lang.Double");
        typeMatcher.put("Boolean?", "java.lang.Boolean");

        typeMatcher.put("kotlin.Any", "java.lang.Object");
        typeMatcher.put("kotlin.Cloneable", "java.lang.Cloneable");
        typeMatcher.put("kotlin.Comparable", "java.lang.Comparable");
        typeMatcher.put("kotlin.Enum", "java.lang.Enum");
        typeMatcher.put("kotlin.Annotation", "java.lang.Annotation");
        typeMatcher.put("kotlin.CharSequence", "java.lang.CharSequence");
        typeMatcher.put("kotlin.String", "java.lang.String");
        typeMatcher.put("kotlin.Number", "java.lang.Number");
        typeMatcher.put("kotlin.Throwable", "java.lang.Throwable");

        typeMatcher.put("kotlin.Any?", "java.lang.Object");
        typeMatcher.put("kotlin.Cloneable?", "java.lang.Cloneable");
        typeMatcher.put("kotlin.Comparable?", "java.lang.Comparable");
        typeMatcher.put("kotlin.Enum?", "java.lang.Enum");
        typeMatcher.put("kotlin.Annotation?", "java.lang.Annotation");
        typeMatcher.put("kotlin.CharSequence?", "java.lang.CharSequence");
        typeMatcher.put("kotlin.String?", "java.lang.String");
        typeMatcher.put("kotlin.Number?", "java.lang.Number");
        typeMatcher.put("kotlin.Throwable?", "java.lang.Throwable");

        typeMatcher.put("Any", "java.lang.Object");
        typeMatcher.put("Cloneable", "java.lang.Cloneable");
        typeMatcher.put("Comparable", "java.lang.Comparable");
        typeMatcher.put("Enum", "java.lang.Enum");
        typeMatcher.put("Annotation", "java.lang.Annotation");
        typeMatcher.put("CharSequence", "java.lang.CharSequence");
        typeMatcher.put("String", "java.lang.String");
        typeMatcher.put("Number", "java.lang.Number");
        typeMatcher.put("Throwable", "java.lang.Throwable");

        typeMatcher.put("Any?", "java.lang.Object");
        typeMatcher.put("Cloneable?", "java.lang.Cloneable");
        typeMatcher.put("Comparable?", "java.lang.Comparable");
        typeMatcher.put("Enum?", "java.lang.Enum");
        typeMatcher.put("Annotation?", "java.lang.Annotation");
        typeMatcher.put("CharSequence?", "java.lang.CharSequence");
        typeMatcher.put("String?", "java.lang.String");
        typeMatcher.put("Number?", "java.lang.Number");
        typeMatcher.put("Throwable?", "java.lang.Throwable");

        typeMatcher.put("Iterator", "java.util.Iterator");
        typeMatcher.put("Iterable", "java.lang.Iterable");
        typeMatcher.put("Collection", "java.util.Collection");
        typeMatcher.put("Set", "java.util.Set");
        typeMatcher.put("List", "java.util.List");
        typeMatcher.put("ListIterator", "java.util.ListIterator");
        typeMatcher.put("Map", "java.util.Map");
        typeMatcher.put("Map.Entry", "java.util.Map.Entry");

        typeMatcher.put("kotlin.collections.Iterator", "java.util.Iterator");
        typeMatcher.put("kotlin.collections.Iterable", "java.lang.Iterable");
        typeMatcher.put("kotlin.collections.Collection", "java.util.Collection");
        typeMatcher.put("kotlin.collections.Set", "java.util.Set");
        typeMatcher.put("kotlin.collections.List", "java.util.List");
        typeMatcher.put("kotlin.collections.ListIterator", "java.util.ListIterator");
        typeMatcher.put("kotlin.collections.Map", "java.util.Map");

        typeMatcher.put("Iterator?", "java.util.Iterator");
        typeMatcher.put("Iterable?", "java.lang.Iterable");
        typeMatcher.put("Collection?", "java.util.Collection");
        typeMatcher.put("Set?", "java.util.Set");
        typeMatcher.put("List?", "java.util.List");
        typeMatcher.put("ListIterator?", "java.util.ListIterator");
        typeMatcher.put("Map?", "java.util.Map");
        // Need to model Map.Entry completely
        typeMatcher.put("Map.Entry?", "java.util.Map.Entry");

        typeMatcher.put("kotlin.collections.Iterator?", "java.util.Iterator");
        typeMatcher.put("kotlin.collections.Iterable?", "java.lang.Iterable");
        typeMatcher.put("kotlin.collections.Collection?", "java.util.Collection");
        typeMatcher.put("kotlin.collections.Set?", "java.util.Set");
        typeMatcher.put("kotlin.collections.List?", "java.util.List");
        typeMatcher.put("kotlin.collections.ListIterator?", "java.util.ListIterator");
        typeMatcher.put("kotlin.collections.Map?", "java.util.Map");

        typeMatcher.put("MutableIterator", "java.util.Iterator");
        typeMatcher.put("MutableIterable", "java.lang.Iterable");
        typeMatcher.put("MutableCollection", "java.util.Collection");
        typeMatcher.put("MutableSet", "java.util.Set");
        typeMatcher.put("MutableList", "java.util.List");
        typeMatcher.put("MutableListIterator", "java.util.ListIterator");
        typeMatcher.put("MutableMap", "java.util.Map");
        typeMatcher.put("MutableMap.MutableEntry", "java.util.Map.Entry");

        typeMatcher.put("kotlin.collections.MutableIterator", "java.util.Iterator");
        typeMatcher.put("kotlin.collections.MutableIterable", "java.lang.Iterable");
        typeMatcher.put("kotlin.collections.MutableCollection", "java.util.Collection");
        typeMatcher.put("kotlin.collections.MutableSet", "java.util.Set");
        typeMatcher.put("kotlin.collections.MutableList", "java.util.List");
        typeMatcher.put("kotlin.collections.MutableListIterator", "java.util.ListIterator");
        typeMatcher.put("kotlin.collections.MutableMap", "java.util.Map");

        typeMatcher.put("MutableIterator?", "java.util.Iterator");
        typeMatcher.put("MutableIterable?", "java.lang.Iterable");
        typeMatcher.put("MutableCollection?", "java.util.Collection");
        typeMatcher.put("MutableSet?", "java.util.Set");
        typeMatcher.put("MutableList?", "java.util.List");
        typeMatcher.put("MutableListIterator?", "java.util.ListIterator");
        typeMatcher.put("MutableMap?", "java.util.Map");

        typeMatcher.put("kotlin.collections.MutableIterator?", "java.util.Iterator");
        typeMatcher.put("kotlin.collections.MutableIterable?", "java.lang.Iterable");
        typeMatcher.put("kotlin.collections.MutableCollection?", "java.util.Collection");
        typeMatcher.put("kotlin.collections.MutableSet?", "java.util.Set");
        typeMatcher.put("kotlin.collections.MutableList?", "java.util.List");
        typeMatcher.put("kotlin.collections.MutableListIterator?", "java.util.ListIterator");
        typeMatcher.put("kotlin.collections.MutableMap?", "java.util.Map");

        typeMatcher.put("kotlin.Unit", "void");
        typeMatcher.put("kotlin.Unit?", "kotlin.Unit");
        typeMatcher.put("Unit", "void");
        typeMatcher.put("Unit?", "kotlin.Unit");
        typeMatcher.put("kotlin.Nothing", "java.lang.Void");
        typeMatcher.put("kotlin.Nothing?", "java.lang.Void");
        typeMatcher.put("Nothing", "java.lang.Void");
        typeMatcher.put("Nothing?", "java.lang.Void");

        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_0_PATTERN, "kotlin.jvm.functions.Function0");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_1_PATTERN, "kotlin.jvm.functions.Function1");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_2_PATTERN, "kotlin.jvm.functions.Function2");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_3_PATTERN, "kotlin.jvm.functions.Function3");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_4_PATTERN, "kotlin.jvm.functions.Function4");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_5_PATTERN, "kotlin.jvm.functions.Function5");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_6_PATTERN, "kotlin.jvm.functions.Function6");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_7_PATTERN, "kotlin.jvm.functions.Function7");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_8_PATTERN, "kotlin.jvm.functions.Function8");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_9_PATTERN, "kotlin.jvm.functions.Function9");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_10_PATTERN, "kotlin.jvm.functions.Function10");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_11_PATTERN, "kotlin.jvm.functions.Function11");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_12_PATTERN, "kotlin.jvm.functions.Function12");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_13_PATTERN, "kotlin.jvm.functions.Function13");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_14_PATTERN, "kotlin.jvm.functions.Function14");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_15_PATTERN, "kotlin.jvm.functions.Function15");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_16_PATTERN, "kotlin.jvm.functions.Function16");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_17_PATTERN, "kotlin.jvm.functions.Function17");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_18_PATTERN, "kotlin.jvm.functions.Function18");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_19_PATTERN, "kotlin.jvm.functions.Function19");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_20_PATTERN, "kotlin.jvm.functions.Function20");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_21_PATTERN, "kotlin.jvm.functions.Function21");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_22_PATTERN, "kotlin.jvm.functions.Function22");
        KOTLIN_FUNCTION_TYPE_PATTERN.put(FUNCTION_N_PATTERN, "kotlin.jvm.functions.FunctionN");
    }

    /**
     * Transform the Kotlin's data type to Kotlin/JVM platform data types
     *
     * @param kotlinType Kotlin data type in String
     * @return if given Kotlin data type matched the type matcher then returns Kotlin/JVM platform data type in String
     * otherwise it returns the same given kotlinType
     */
    public static String transform(String kotlinType) {
        String res = typeMatcher.get(kotlinType);

        if (res == null) {
            return kotlinType;
        }

        return res;
    }

    /**
     * Transforms the comma seperated parameter list types into a Kotlin/JVM data type if present Kotlin's function type
     *
     * @param parameterString Comma seperated parameters type
     * @return Transformed comma seperated parameters type
     */
    public static String replaceFunctionType(String parameterString) {
        if (parameterString.equals("")) {
            return parameterString;
        }

        parameterString = parameterString.replaceAll("\\s++", "");

        for (Pattern pattern : KOTLIN_FUNCTION_TYPE_PATTERN.keySet()) {
            Matcher matcher = pattern.matcher(parameterString);

            if (matcher.find()) {
                parameterString = matcher.replaceAll(KOTLIN_FUNCTION_TYPE_PATTERN.get(pattern));
            }
        }

        return parameterString;
    }

    /**
     * Transforms the given return type into a Kotlin/JVM data type if present Kotlin's function type
     *
     * @param returnType Return type
     * @return Transformed return type
     */
    public static String replaceFunctionTypeForReturnType(String returnType) {
        if (returnType.equals("")) {
            return returnType;
        }

        for (Pattern pattern : KOTLIN_FUNCTION_TYPE_PATTERN.keySet()) {
            Matcher matcher = pattern.matcher(returnType);

            if (matcher.find()) {
                returnType = matcher.replaceAll(KOTLIN_FUNCTION_TYPE_PATTERN.get(pattern));
            }
        }

        return returnType;
    }
}