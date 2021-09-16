package de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility;

import java.util.HashMap;

/**
 * Kotlin's data types transformer, Utility to transform the Kotlin's data type to Kotlin/JVM platform data types
 *
 * @author Ranjith Krishnamurthy
 */
public class KotlinDataTypeTransformer {
    private static final HashMap<String, String> typeMatcher = new HashMap<>();

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
}