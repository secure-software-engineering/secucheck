package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias;

import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.AbstractTypeAliases;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows user to specify the Kotlin Type Aliases
 *
 * @author Ranjith Krishnamurthy
 */
public class TypeAliases implements AbstractTypeAliases {
    private final HashMap<String, String> typeAliases;

    public TypeAliases() {
        this.typeAliases = new HashMap<>();
    }

    /**
     * Returns the Type Aliases in a Map object
     *
     * @return Type aliases as Map object
     */
    public Map<String, String> getAsMap() {
        return typeAliases;
    }

    /**
     * Adds new Type Alias
     *
     * @param aliasTypeName    Alias type name
     * @param originalTypeName Original type name
     */
    public void add(String aliasTypeName, String originalTypeName) {
        typeAliases.put(aliasTypeName, originalTypeName);
    }

    /**
     * Adds all the aliases present in the given TypeAlias object
     *
     * @param typeAliases Type Alias
     */
    public void addAll(AbstractTypeAliases typeAliases) {
        this.typeAliases.putAll(typeAliases.getAsMap());
    }

    /**
     * Get the original type name for the given alias type name. If not found returns the same alias type name
     *
     * @param aliasTypeName Alias type name
     * @return Original type name if found otherwiese returns the aliasTypeName as it is
     */
    public String getOriginalTypeNameFrom(String aliasTypeName) {
        String res = this.typeAliases.get(aliasTypeName);

        if (res == null) {
            return aliasTypeName;
        }

        return res;
    }
}
