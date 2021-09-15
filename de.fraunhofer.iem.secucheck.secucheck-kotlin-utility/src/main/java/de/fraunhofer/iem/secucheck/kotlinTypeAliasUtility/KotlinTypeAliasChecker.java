package de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility;

/**
 * Utility to check for the type aliases.
 *
 * @author Ranjith Krishnamurthy
 */
public class KotlinTypeAliasChecker {
    public static final int MAX_RECURSIVE_STAGE = 50;

    private String aliasTypeName;
    private AbstractTypeAliases typeAliases;

    /**
     * Returns the original type name for the given alias type name if any, otherwise returns the aliasTypeName as it is
     * This method recursively applies the type alias checker for {@link de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker#MAX_RECURSIVE_STAGE}
     * to resolve the chained type aliases
     *
     * @param aliasTypeName Alias type name
     * @param typeAliases   Type aliases
     * @return Returns the original type name if any otherwise returns the aliasTypeName as it is
     */
    public String getOriginalTypeName(String aliasTypeName, AbstractTypeAliases typeAliases) {
        this.aliasTypeName = aliasTypeName;
        this.typeAliases = typeAliases;

        return recursivelyCheckForTypeAlias(0);
    }

    private String recursivelyCheckForTypeAlias(int stage) {
        if (stage > MAX_RECURSIVE_STAGE)
            return null;

        String res = typeAliases.getOriginalTypeNameFrom(aliasTypeName);

        if (res.equals(aliasTypeName)) {
            return aliasTypeName;
        }

        this.aliasTypeName = res;
        return recursivelyCheckForTypeAlias(stage + 1);
    }
}
