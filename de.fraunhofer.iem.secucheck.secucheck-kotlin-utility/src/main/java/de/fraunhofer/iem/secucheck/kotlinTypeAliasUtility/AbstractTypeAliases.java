package de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility;

import java.util.Map;

/**
 * Abstract Type aliases for organizing the modules
 *
 * @author Ranjith Krishnamurthy
 */
public interface AbstractTypeAliases {
    public void add(String aliasTypeName, String originalTypeName);

    public void addAll(AbstractTypeAliases typeAliases);

    public Map<String, String> getAsMap();

    public String getOriginalTypeNameFrom(String aliasTypeName);
}
