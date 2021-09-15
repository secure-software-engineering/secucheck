## How to build SecuCheck manually

* First build the SecuCheck-Core analysis using the [link](https://github.com/secure-software-engineering/secucheck-core/tree/SCCK-1.0.0#how-to-build-secucheck-core-manually).

* Then build the SecuCheck
* Git clone the SecuCheck repository

````shell
git clone https://github.com/secure-software-engineering/secucheck.git
````

* Change the branch to SCK-1.0.0

````shell
git checkout SCK-1.0.0
````

* Build the SecuCheck using the below command

````shell
mvn clean install -DskipTests
````

* Then, in secucheck project, under the SecuCheck-Magpie module, use the generated jar-with-dependency jar (in target directory) to use SecuCheck in IDE.

* In secucheck project, under the secucheck-cmd module, use the generated jar-with-dependency jar (in target directory) to use SecuCheck through command line prompt.

## How to install SecuCheck in Eclipse

To install SecuCheck in Eclipse, use this [link](https://github.com/secure-software-engineering/secucheck/wiki/Use-SecuCheck-in-Eclipse-IDE.)

## How to use SecuCheck through command line

Use the secucheck-cmd jar from the [release](https://github.com/secure-software-engineering/secucheck/releases/tag/SC-1.1.0) or use the manually built jar-with-dependecy jar from the module **de.fraunhofer.iem.secucheck.secucheck-cmd** project in the target directory.

Below is the output of the help options from the SecuCheck-cmd

````shell
$ java -jar secucheck-cmd.jar -h
usage: secucheck-cmd
 -od,--out-dir <arg>             SecuCheck analysis result output
                                 directory
 -of,--out-file <arg>            SecuCheck analysis result output filename
                                 without the file extension
 -scp,--secu-config-file <arg>   SecuCheck configuration settings file
````

Usage example 
````shell
java -jar secucheck-cmd.jar -scp /home/secucheck/catalog/settings.yml -od /home/secucheck/catalog/output/ -of run1_output
````

Configuration settings for the analysis is provided through the options **-scp** and provides the settings YAML file. Below is the simple example of settings YAML file for the SecuCheck-cmd

````shell
classPath: 'D:\Work\Latest\SCK-1.0.0\secucheck-catalog\de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog\target\classes'
entryPoints:
specPath: 'D:\Work\Latest\SCK-1.0.0\secucheck-catalog\de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications\target'
selectedSpecs:
asSpecFile: false
solver: "Boomerang3"
isPostProcessResult: false
````

### Detailed information of the settings YAML file parameters
* **classPath** : Class path of the project that needs to be analyzed by the SecuCheck
* **entryPoints** : Entry points for the analysis. In the above example it is empty, that means, all the classes that available in the provided class path (**classPath**) are considered as enrty points for that analysis. To select the particular classes as entry points below is the example of settings YAML file.
* **specPath** : Path of the directory that contains the fluentTQL specification's compiled jar
* **selectedSpecs** : fluentTQL specification / taintflow queries that are considered for the analysis. In the above example it is empty, that means, all the fluentTQL specifications or taint flow queries that available in the provided fluentTQL specification path (**specPath**) are considered for the analysis and try to find the similar taintflow in the project. To select the particular specifications/taintflow queries as selected specifications. below is the example of settings YAML file.
* **asSpecFile** : Each fluentTQL specification file can have multiple taintflow queries. If you specify taint flow query ID in the selectedSpecs parameter, then you much provide fase to asSpecFile, so that SecuCheck-cmd will look for that taint flow query ID. If you specify true to asSpecFile parameter, then SecuCheck-cmd look for the specifications file name rather than the taint flow query ID.
* **solver** : It can be **Boomerang3** or **FlowDroid**.
* **isPostProcessResult** : If you specify true, then SecuCheck will post process the result and provide the result in an object. For running the SecuCheck-cmd it does not matter since we are not accessing the post-process result for now.

````shell
# If you specify using the taintflow query ID
classPath: 'D:\Work\Latest\SCK-1.0.0\secucheck-catalog\de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog\target\classes'
entryPoints:
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89.SimpleSQLInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.CommandInjection.CWE77.CommandInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LDAPInjection.CWE90.LdapInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LDAPInjection.CWE90.LDAPServerUtils
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LogInjectionAttack.CWE117.LogInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources
specPath: 'D:\Work\Latest\SCK-1.0.0\secucheck-catalog\de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications\target'
selectedSpecs:
- SimpleSQLi
- SQLiWithPreparedStmt
- NoSQLiWithMultipleSources
- StoredXSS
asSpecFile: 'false'
solver: "Boomerang3"
isPostProcessResult: false


# If you specify using the fluentTQL specification file name
classPath: 'D:\Work\Latest\SCK-1.0.0\secucheck-catalog\de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog\target\classes'
entryPoints:
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89.SimpleSQLInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.CommandInjection.CWE77.CommandInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LDAPInjection.CWE90.LdapInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LDAPInjection.CWE90.LDAPServerUtils
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.LogInjectionAttack.CWE117.LogInjection
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources
specPath: 'D:\Work\Latest\SCK-1.0.0\secucheck-catalog\de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications\target'
selectedSpecs:
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.SQLInjection.CWE89.SimpleSQLInjectionSpec
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.SQLInjection.CWE89.SQLiWithPreparedStatementsSpec
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.NoSQLInjection.CWE943.NoSQLInjectionWithMultipleSources
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.XSS.CWE79.StoredXSSSpec 
asSpecFile: 'true'
solver: "Boomerang3"
isPostProcessResult: 'false'
````
