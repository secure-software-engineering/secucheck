package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

public enum CWE {
    // Related to command injection
    CWE77("CWE77", "Command Injection: Improper Neutralization of Special Elements used in a Command"),
    CWE78("CWE78", "Command Injection: Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')"),


    CWE90("CWE90", "LDAP Injection: Improper Neutralization of Special Elements used in an LDAP Query"),
    CWE117("CWE117", "Log Injection: Improper Output Neutralization for Logs"),
    CWE943("CWE943", "NoSql Injection, etc.: Improper Neutralization of Special Elements in Data Query Logic"),
    CWE601("CWE601", "Open Redirect: URL Redirection to Untrusted Site"),
    CWE22("CWE22", "Path Traversal: Improper Limitation of a Pathname to a Restricted Directory"),
    CWE89("CWE89", "SQL InjectionImproper Neutralization of Special Elements used in an SQL Command"),
    CWE501("CWE501", "Trust Boundary Violation"),
    CWE643("CWE643", "XPath Injection: Improper Neutralization of Data within XPath Expressions"),
    CWE79("CWE79", "Cross-site Scripting: Improper Neutralization of Input During Web Page Generation"),
    CWE200("CWE200", "Information leak: Exposure of Sensitive Information to an Unauthorized Actor"),
    CWE311("CWE311", "Missing Encryption of Sensitive Data"),
    CWE20("CWE20", "Improper Input Validation"),

    // Related to XML Injection
    CWE91("CWE91", "XML Injection: aka Blind XPath Injection"),
    CWE116("CWE116", "XML Injection: Improper Encoding or Escaping of Output"),
    CWE611("CWE611", "XML Injection: Improper Restriction of XML External Entity Reference ('XXE')"),
    CWE776("CWE776", "XML Injection: Improper Restriction of Recursive Entity References in DTDs ('XML Entity Expansion')"),

    ATTACK_SURFACE("ATTACK_SURFACE", "This indicates the sources such as user input");

    private final String category;

    private final String categoryName;

    CWE(String category, String categoryName) {
        this.category = category;
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
