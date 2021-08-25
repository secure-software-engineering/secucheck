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
