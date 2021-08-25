""" This is the main script of the Evaluation of SecuCheck SC-1.1.0

    This script contains the evaluation settings parameters as shown below.

    1. SECUCHECK_JAR: Path of the SecuCheck-cmd jar file. Make sure to give the path of the jar-with-dependencies jar file.
    2. HYPOTHESIS_PROJECTS: It is a dictionary, that contains the number of hypothesis and the respective evaluation
        projects for each hypothesis.

        For example, if you want to run the evaluation for "Hypothesis1" with the evaluation project to only "catalog"
        and "Hypothesis2_SingleEntryPoint" with the evaluation project to "catalog", and "petclinic",
        then the HYPOTHESIS_PROJECTS settings parameter should be as shown below

        HYPOTHESIS_PROJECTS: dict = {
            "Hypothesis1": ["catalog"],
            "Hypothesis2_SingleEntryPoint": ["catalog", "petclinic"]
        }

        Note: Evaluation environment contains the folder for each of the hypothesis with the same name mentioned in the
        HYPOTHESIS_PROJECTS settings parameter. And each of these folder contains a folder for each of the
        evaluation project with the same name as given in the HYPOTHESIS_PROJECTS settings parameter. And the script
        look for the same name folders. Therefore, please do not change the name of the hypothesis and the evaluation
        project. Just configure the settings parameter to run for particular hypothesis and the evaluation project as you wish,
        but keep the names as it is.

    3. TOTAL_RUN: Total number of runs that the evaluation should be carried out.

        Note:
            1. For TOTAL_RUN = 1, and for all the hypotheses and the evaluation projects, in Windows 10 with 16 GB RAM,
        it took almost 1 hour to complete the evaluation.
            2.  For TOTAL_RUN = 10 (used for the actual paper), and for all the hypotheses and the evaluation projects,
        in Windows 10 with 16 GB RAM, it took almost 8.5 hour to complete the evaluation.

    Developer(s):
        Ranjith Krishnamurthy
"""

import time

import run_secucheck as runner
import run_visualization as visualizer

# Evaluation settings
SECUCHECK_JAR: str = '/home/secucheck/SecuCheck/secucheck/de.fraunhofer.iem.secucheck.secucheck-cmd/target/de.fraunhofer.iem.secucheck.secucheck-cmd-SC-1.1.0-jar-with-dependencies.jar'
HYPOTHESIS_PROJECTS: dict = {
    "Hypothesis1": ["catalog", "demo-project", "petclinic", "Webgoat"],
    "Hypothesis2_AllEntryPoint": ["catalog", "demo-project", "petclinic", "Webgoat"],
    "Hypothesis2_SingleEntryPoint": ["catalog", "demo-project", "petclinic", "Webgoat"],
    "Hypothesis3": ["catalog"]
}
TOTAL_RUN: int = 1

# Main
if __name__ == "__main__":
    # Record Start Time
    start_time = time.time()

    runner.run()
    visualizer.run(HYPOTHESIS_PROJECTS, TOTAL_RUN)

    # Record End Time
    end_time = time.time()
    print("*** Total time taken by the evaluation script = " + str((end_time - start_time)) + " ***")
