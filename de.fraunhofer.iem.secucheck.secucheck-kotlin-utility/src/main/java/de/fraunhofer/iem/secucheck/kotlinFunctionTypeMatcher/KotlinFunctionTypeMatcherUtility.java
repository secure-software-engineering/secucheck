package de.fraunhofer.iem.secucheck.kotlinFunctionTypeMatcher;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility to transform the Kotlin's function type in a method signature to Kotlin/JVM platform data types
 *
 * @author Ranjith Krishnamurthy
 */
public class KotlinFunctionTypeMatcherUtility {
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
