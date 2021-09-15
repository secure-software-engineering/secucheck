package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException;

import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

/**
 * This exception indicates that the fluentTQL reached the maximum level of chained type aliases. Reason may be there is a
 * cyclic type alias or type alias chained level is more than it is currently supported by fluentTQL.
 *
 * @author Ranjith Krishnamurthy
 */
public class CyclicTypeAliasException extends FluentTQLRuntimeException {
    /**
     * Constructs the FluentTQLRuntimeException with the given message.
     *
     * @param initialTypeAlias Error message.
     */
    public CyclicTypeAliasException(String initialTypeAlias) {
        super("Type Alias chain is more than " + KotlinTypeAliasChecker.MAX_RECURSIVE_STAGE +
                "\nThere may be a chained Type aliases, Please remove chained type alias if any.\n" +
                "Currently fluentTQL supports maximum upto the level of " + KotlinTypeAliasChecker.MAX_RECURSIVE_STAGE +
                " chained type aliases");
    }
}
