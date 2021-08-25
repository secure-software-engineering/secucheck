# SecuCheck evaluation enviornment for IEEE-SCAM 2021

This project provides the evaluation environment for the evaluation of SecuCheck SC-1.1.0 for the IEEE-SCAM 2021
conference.

There are three scripts available on the root directory.

* ***run_secucheck_evaluation***: This is the main script, user can configure the evaluation settings parameter in this
  script and then, run this script to run the evaluation of SecuCheck SC-1.1.0
* ***run_secucheck***: This script is called internally by the *run_secucheck_evaluation* script. This script runs the
  SecuCheck based on the configured settings by the user
* ***run_visualization***: This script is called internally by the *run_secucheck_evaluation* script. This script runs
  visualization of the evaluation and generates the graphs and stores in JPG file

## Things that must be done before running the evaluation scripts

### Clone and Build the evaluation projects

For this evaluation, we consider 4 evaluation projects.

* ***catalog and demo-project***

Clone the SecuCheck-Catalog project. This project also contains the fluentTQL specifications for all the 4 evaluation
projects.

````shell
git clone https://github.com/secure-software-engineering/secucheck-catalog.git
````

change the branch to v1.1.0

````shell
git checkout v1.1.0
````

Build the project. This builds both catalog and demo-project, as well as all the fluentTQL specifications projects too.

````shell
# In the root directory of the secucheck-catalog project run the below command
mvn clean install -DskipTests
````

* ***petclinic***

Clone the project and build the project as mentioned in the github repository using the below link. Build should be
successful, otherwise SecuCheck can not analyze since there is no bytecode generated.

https://github.com/contrast-community/spring-petclinic.git

* ***WebGoat***

Clone the project and build the project as mentioned in the github repository using the below link. Build should be
successful at least for the webgoat-lessons/sql-injection project, otherwise SecuCheck can not analyze since there is no
bytecode generated. This evaluation considers only the webgoat-lessons/sql-injection project from the WebGoat

https://github.com/WebGoat/WebGoat.git

### Build the fluentTQL specifications for all the 4 evaluation projects.

When you cloned and built SecuCheck-Catalog project, this project also contains the fluentTQL specifications projects.
If the build is successful in that step, building the fluentTQL specifications projects also successful.

### Install dependencies for the evaluation python scripts

* xlsxwriter
* yaml
* pandas
* matplotlib
* numpy
* openpyxl

````shell
pip install xlsxwriter
pip install pyyaml
pip install pandas
pip install matplotlib
pip install numpy
pip install openpyxl
````

### Download the SecuCheck-cmd jar files

Download and save *secucheck-cmd.jar* in some location from the given below link

https://github.com/secure-software-engineering/secucheck/releases/tag/SC-1.1.0

## Configuring the SecuCheck settings file (YAML)

SecuCheck-cmd takes the settings for the analysis through the YAML file. These YAML settings files are already provided
in this evaluation environment in the respective hypothesis and the evaluation projects directory, under the directory
name
*settings_files*.

For example, settings files for the *Hypothesis1*, and the evaluation project *catalog* is stored in the below location

```shell
# This path is relative to the root directory of this project 
# where the 3 python scripts are stored
Hypothesis1/catalog/settings_files
```

An example of a settings file is shown below. These settings file takes the class path of the Java programs that needs
to be analyzed and the fluentTQL specifications path to run the analysis.

```shell
classPath: CATALOG_CLASS_PATH
entryPoints:
- de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.SQLInjection.CWE89.SimpleSQLInjection
specPath: CATALOG_FLUENTTQL_SPEC_PATH
selectedSpecs:
- SimpleSQLi
asSpecFile: 'false'
solver: Boomerang3
isPostProcessResult: 'false'
```

Since, we need to build the evaluation projects, therefore in place of *classPath* and *specPath*
we placed a placeholder as shown in the above example. Placeholders for different project is shown below.

* ***catalog*** :
    * Class path: CATALOG_CLASS_PATH
    * Spec path:  CATALOG_FLUENTTQL_SPEC_PATH
* ***demo-project***
    * Class path: DEMO_PROJECT_CLASS_PATH
    * Spec path:  DEMO_PROJECT_FLUENTTQL_SPEC_PATH
* ***petclinic***
    * Class path: PETCLINIC_CLASS_PATH
    * Spec path:  PETCLINIC_FLUENTTQL_SPEC_PATH
* ***Webgoat***
    * Class path: WEBGOAT_CLASS_PATH
    * Spec path:  WEBGOAT_FLUENTTQL_SPEC_PATH

Now, we need to replace these placeholders with the actual path. This can be done in IDE like IntelliJ.

For Linux users, we have provided the commands to replace the placeholders.

For example, lets consider the *catalog* project. Let's assume that, SecuCheck-catalog is cloned and built in the below
directory.

````shell
/home/secucheck/SecuCheck/secucheck-catalog
````

Then, the catalog project's class path is as shown below. Do not forget to include the ***classes*** directory at the
end

````shell
/home/secucheck/SecuCheck/secucheck-catalog/de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog/target/classes
````

The catalog's fluentTQL specifications path is as shown below. Do not forget to include the ***target*** directory at
the end

````shell
/home/secucheck/SecuCheck/secucheck-catalog/de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications/target
````

For replacing the placeholders, use the below command. (Use similarly)

````shell
# For replacing the class path of catalog project
# run this command in the root directory of this project that contains the 3 python scripts
# After the s/ give the place holder that you want to replace
# After the placeholder type / and place the actual path that you want replace to
# Remember to use the escape character \ to insert the / 
# For example, for /home/secucheck, you should type as \/home\/secucheck
find . -type f -exec sed -i 's/CATALOG_CLASS_PATH/\/home\/secucheck\/SecuCheck\/secucheck-catalog\/de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog\/target\/classes/g' {} +
````

Similarly, repalce all the placeholder before you run the evaluation scripts.