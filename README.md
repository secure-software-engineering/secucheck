## How to build SecuCheck manually

* First build the SecuCheck-Core analysis using the [link](https://github.com/secure-software-engineering/secucheck-core/tree/SCC-1.1.0#how-to-build-secucheck-core-manually).

* Then build the SecuCheck
* Git clone the SecuCheck repository

````shell
git clone https://github.com/secure-software-engineering/secucheck.git
````

* Change the branch to SC-1.1.0

````shell
git checkout SC-1.1.0
````

* Build the SecuCheck using the below command

````shell
mvn clean install -DskipTests
````

* Then, in secucheck project, under the SecuCheck-Magpie module, use the generated jar-with-dependency jar (in target directory) to use SecuCheck in IDE.

* In secucheck project, under the secucheck-cmd module, use the generated jar-with-dependency jar (in target directory) to use SecuCheck through command line prompt.
