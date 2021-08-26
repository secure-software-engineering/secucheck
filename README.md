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

There are total of 4 hypothesis.
* ***Hyothesis1*** : Figure 3 in submiited paper to IEEE SCAM 2021
* ***Hyothesis2_AllEntryPoint*** : Figure 5 in submiited paper to IEEE SCAM 2021
* ***Hyothesis2_SingleEntryPoint*** Figure 4 in submiited paper to IEEE SCAM 2021
* ***Hypothesis3*** : It is mentioed in the paper as text at page 5 (section IV.b) at the last paragraph.

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

When you cloned and built SecuCheck-Catalog project in the [step](#clone-and-build-the-evaluation-projects), this
project also contains the fluentTQL specifications projects. If the build of SecuCheck-Catalog project is successful in
that [step](#clone-and-build-the-evaluation-projects), then building the fluentTQL specifications projects also
successful.

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

## Configuring the evaluation settings parameters

***run_secucheck_evaluation*** contains the evaluation parameters as shown below

````shell
# Evaluation settings
SECUCHECK_JAR: str = 'SECUCHECK-CMD-SC-1.1.0-JAR_WITH_DEPENDENCIES_JAR_PATH'

HYPOTHESIS_PROJECTS: dict = {
    "Hypothesis1": ["catalog", "demo-project", "petclinic", "Webgoat"],
    "Hypothesis2_AllEntryPoint": ["catalog", "demo-project", "petclinic", "Webgoat"],
    "Hypothesis2_SingleEntryPoint": ["catalog", "demo-project", "petclinic", "Webgoat"],
    "Hypothesis3": ["catalog"]
}

TOTAL_RUN: int = 1
````

* ***SECUCHECK_JAR*** : This is the executable jar file of SecuCheck-cmd, to run the SecuCheck through command line.
  Give the full absolute path of the downloaded secucheck-jar file in the [step](#download-the-secucheck-cmd-jar-files).

````shell
# for example,
SECUCHECK_JAR: str = '/home/secucheck/Downloads/secucheck-cmd.jar'
````

* ***HYPOTHESIS_PROJECTS*** : It is a dictionary, that contains the number of hypothesis and the respective evaluation
  projects for each hypothesis.

For example, if you want to run the evaluation for ***Hypothesis1*** with the evaluation project to only ***catalog***
and ***Hypothesis2_SingleEntryPoint*** with the evaluation project to ***catalog***, and ***petclinic***, then the ***
HYPOTHESIS_PROJECTS*** settings parameter should be as shown below

````shell
HYPOTHESIS_PROJECTS: dict = {
  "Hypothesis1": ["catalog"],
  "Hypothesis2_SingleEntryPoint": ["catalog", "petclinic"]
}
````

**Note**: Evaluation environment contains the folder for each of the hypothesis with the same name mentioned in the
***HYPOTHESIS_PROJECTS*** settings parameter. And each of these folder contains a folder for each of the evaluation
project with the same name as given in the ***HYPOTHESIS_PROJECTS*** settings parameter. And the script look for the
same name folders. Therefore, please do not change the name of the hypothesis and the evaluation project. Just configure
the settings parameter to run for particular hypothesis and the evaluation project as you wish, but keep the names as it
is.

* ***TOTAL_RUN*** : Total number of runs that the evaluation should be carried out.

**Note**:

1. For **TOTAL_RUN = 1**, and for all the hypotheses and the evaluation projects, in Windows 10 with 16 GB RAM, it took
   almost **1 hour** to complete the evaluation.
2. For **TOTAL_RUN = 10** (used for the actual paper), and for all the hypotheses and the evaluation projects, in
   Windows 10 with 16 GB RAM, it took almost **8.5 hour** to complete the evaluation.

## Run the evaluation

Finally, run the ***run_secucheck_evaluation*** script to run the evaluation.

## Important directories

Note:
All the below given directories are the relative path to the root of this project

* ***Hypothesis1***

There are 4 evaluation projects and their respective path

````shell
# catalog
Hypothesis1/catalog/
# demo-project 
Hypothesis1/demo-project/
# petclinic 
Hypothesis1/petclinic/
# Webgoat 
Hypothesis1/Webgoat/
````

SecuCheck settings files (YAML) will be stored in

````shell
# catalog
Hypothesis1/catalog/settings_files/
# demo-project 
Hypothesis1/demo-project/settings_files/
# petclinic 
Hypothesis1/petclinic/settings_files/
# Webgoat 
Hypothesis1/Webgoat/settings_files/
````

SecuCheck analysis result and the evaluation result (Excel file) for each run will be stored in

````shell
# catalog eg. Hypothesis1/catalog/run1_output/
Hypothesis1/catalog/run[0-9]_output/
# demo-project 
Hypothesis1/demo-project/run[0-9]_output/
# petclinic 
Hypothesis1/petclinic/run[0-9]_output/
# Webgoat 
Hypothesis1/Webgoat/run[0-9]_output/
````

SecuCheck evaluation result (Excel file: average of TOTAL_RUN) for each project will be stored in the below directories.
These directories also contains the visualization of the Excel file (average result), stored in JPG file.

````shell
# catalog eg. Hypothesis1/catalog/average_of_10_runs.jpg
Hypothesis1/catalog/
# demo-project 
Hypothesis1/demo-project/
# petclinic 
Hypothesis1/petclinic/
# Webgoat 
Hypothesis1/Webgoat/
````

Visualization of the evaluation result of all the projects in a single graph for each hypothesis will be stored in

**Note**:
This is not valid for Hypothesis3, since Hypothesis3 is only possible on catalog project. Therefore, no comparion graph
for Hypothesis3

````shell
Hypothesis1/
````

* ***Hypothesis2_AllEntryPoint***

Follows the same format as **Hypothesis1**, the only difference is the base directory is as shown below
````shell
# base directory
Hypothesis2_AllEntryPoint/

# There are 4 evaluation projects in Hypothesis2_AllEntryPoint

# catalog 
Hypothesis2_AllEntryPoint/catalog/
# demo-project 
Hypothesis2_AllEntryPoint/demo-project/
# petclinic 
Hypothesis2_AllEntryPoint/petclinic/
# Webgoat 
Hypothesis2_AllEntryPoint/Webgoat/
````

* ***Hypothesis2_SingleEntryPoint***

Follows the same format as **Hypothesis1**, the only difference is the base directory is as shown below
````shell
# base directory
Hypothesis2_SingleEntryPoint/

# There are 4 evaluation projects in Hypothesis2_SingleEntryPoint

# catalog 
Hypothesis2_SingleEntryPoint/catalog/
# demo-project 
Hypothesis2_SingleEntryPoint/demo-project/
# petclinic 
Hypothesis2_SingleEntryPoint/petclinic/
# Webgoat 
Hypothesis2_SingleEntryPoint/Webgoat/
````

* ***Hypothesis3***

Follows the same format as **Hypothesis1**, the only difference is the base directory is as shown below.
And, **Hypothesis3** does not have comparison visualization between multiple projects, since **Hypothesis3**
runs only for **catalog** evaluation project.
````shell
# base directory
Hypothesis3/

# There is only 1 evaluation project in Hypothesis3

# catalog 
Hypothesis3/catalog/
````
