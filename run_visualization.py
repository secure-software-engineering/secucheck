""" This scripts runs the visualization for all the hypothesis and the evaluation projected as configured in run_secucheck_evaluation script.

    1. Recursively, run the visualization for all the given hypotheses and the evaluation project for each hypothesis
        configured in the run_secucheck_evaluation script. This script is run after the run_secucheck script that generates
        the results
    2. It also stores the graph for each hypothesis with each evaluation project.

        For example, for the first run, and for the "Hypothesis1" and the evaluation project "catalog", it stores the
        SecuCheck-cmd result in the below folder (relative path to this script).

        Hypothesis1/catalog/run1_output/
    3. It also stores the graph for average of TOTAL_RUN (for example, in the below path)
        Hypothesis1/catalog/
    4. It also stores a graph (for example, in the below path), that compares the multiple projects results in one graph. (This is not valid for
        Hypothesis3, since Hypothesis3 is only possible on catalog project)
        Hypothesis1/

    Developer(s):
        Ranjith Krishnamurthy
"""

import os

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

from matplotlib.font_manager import FontProperties

# Font for the graph
fontP = FontProperties()
fontP.set_size('xx-small')

# Markers for the projects
markers = {"catalog": ".", "demo-project": "+", "Webgoat": "v", "petclinic": "x"}


# Plots the graph for each run result into the respective folder for the given project and the hypothesis
def plot_each_run_graph(filename, jpg_filename, title, x_label, hypo, proj):
    df1 = pd.read_excel(filename)

    y_max = 0
    y_key = ""
    if (hypo == 'Hypothesis2_AllEntryPoint') | (hypo == 'Hypothesis2_SingleEntryPoint') | (hypo == 'Hypothesis2'):
        y_max = df1['Specifications Count'].max() + 1
        y_key = 'Specifications Count'

    if hypo == 'Hypothesis1':
        y_max = df1['Entry points count'].max() + 1
        y_key = 'Entry points count'

    axes = plt.gca()
    axes.set_ylim([0, df1['Total time in sec'].max() + 1])
    axes.set_xlim([0, y_max + 1])

    plt.xticks(np.arange(0, df1[y_key].max() + 1, 2))
    plt.title(title)
    plt.xlabel(x_label)
    plt.ylabel('Analysis runtime (seconds)')

    plt.plot(df1[y_key], df1['Total time in sec'], markers[proj])

    plt.savefig(jpg_filename, dpi=150, bbox_inches='tight')
    plt.clf()


# Plots the graph for each run result of hypothesis 3, into the respective folder for the given project
def plot_each_run_graph_for_hypo_3(filename, jpg_filename, title, x_label, proj):
    df1 = pd.read_excel(filename)

    axes = plt.gca()
    axes.set_ylim([0, df1['Total time in sec'].max() + 1])
    # axes.set_xlim([0, 5])
    axes.set_xlim([0, len(df1['Total time in sec']) + 1])

    plt.xticks(np.arange(0, len(df1['Total time in sec']) + 1, 1))
    plt.title(title)
    plt.xlabel(x_label)
    plt.ylabel('Analysis runtime (seconds)')

    plt.plot(np.arange(1, len(df1['Total time in sec']) + 1, 1), df1['Total time in sec'], markers[proj])

    plt.savefig(jpg_filename, dpi=150, bbox_inches='tight')
    plt.clf()


# Plots, combining all the projects results into a single graph for each hypothesis
def plot_all_proj_in_each_hypo(HYPOTHESIS_PROJECTS, hypo, TOTAL_RUN):
    x_max: int = 0
    y_max: int = 0
    x_label = ""

    y_key = ""
    if (hypo == 'Hypothesis2_AllEntryPoint') | (hypo == 'Hypothesis2_SingleEntryPoint') | (hypo == 'Hypothesis2'):
        y_key = 'Specifications Count'
        x_label = 'Number of specifications'

    if hypo == 'Hypothesis1':
        y_key = 'Entry points count'
        x_label = 'Number of entry points'

    for project in HYPOTHESIS_PROJECTS.get(hypo):
        core_location: str = hypo + os.path.sep + project + os.path.sep

        df1 = pd.read_excel(core_location + "average_of_" + str(TOTAL_RUN) + "_runs.xlsx")

        axes = plt.gca()

        if y_max < np.max(df1['Total time in sec']):
            axes.set_ylim([0, np.max(df1['Total time in sec']) + 1])
            y_max = np.max(df1['Total time in sec'])

        if x_max < np.max(df1[y_key]):
            axes.set_xlim([0, np.max(df1[y_key]) + 1])
            x_max = np.max(df1[y_key])

        plt.plot(df1[y_key], df1['Total time in sec'], markers[project], label=project)

    plt.xticks(np.arange(0, x_max + 1, 2))
    plt.xlabel(x_label)
    plt.ylabel('Analysis runtime (seconds)')
    plt.legend(bbox_to_anchor=(1, 1), loc='upper left', prop=fontP)

    plt.savefig(hypo + os.path.sep + "all_proj_comparison.jpg", dpi=150, bbox_inches='tight')
    plt.clf()


# Process all the results stored in excel sheet and stores the visualizazion (jpg image)
def run(HYPOTHESIS_PROJECTS, TOTAL_RUN):
    # For each Hypothesis
    for hypo in HYPOTHESIS_PROJECTS.keys():
        # For each evaluation project
        for project in HYPOTHESIS_PROJECTS.get(hypo):
            core_location: str = hypo + os.path.sep + project + os.path.sep
            x_label = ''
            hypo_name: str = ''

            # For each run
            for run_count in range(1, TOTAL_RUN + 1):
                stats_filename: str = 'run' + str(run_count) + '_stats'
                output_directory_name: str = 'run' + str(run_count) + '_output' + os.path.sep

                if hypo == 'Hypothesis1':
                    x_label = 'Number of entry points'

                if hypo == 'Hypothesis3':
                    x_label = 'Specification\'s complexity level'

                if (hypo == 'Hypothesis2_AllEntryPoint') | (hypo == 'Hypothesis2_SingleEntryPoint'):
                    x_label = 'Number of specifications'

                # plot the graph for each run
                if (hypo == 'Hypothesis2_AllEntryPoint') | (hypo == 'Hypothesis2_SingleEntryPoint'):
                    hypo_name = 'Hypothesis2'
                else:
                    hypo_name = hypo

                if hypo == "Hypothesis3":
                    plot_each_run_graph_for_hypo_3(core_location + output_directory_name + stats_filename + '.xlsx',
                                                   core_location + output_directory_name + 'run' + str(
                                                       run_count) + "_graph.jpg",
                                                   project + '(' + hypo + ')' + ' - Run ' + str(run_count),
                                                   x_label,
                                                   project)
                else:
                    plot_each_run_graph(core_location + output_directory_name + stats_filename + '.xlsx',
                                        core_location + output_directory_name + 'run' + str(run_count) + "_graph.jpg",
                                        project + '(' + hypo + ')' + ' - Run ' + str(run_count),
                                        x_label,
                                        hypo_name,
                                        project)

            # plot average
            if hypo == "Hypothesis3":
                plot_each_run_graph_for_hypo_3(core_location + "average_of_" + str(TOTAL_RUN) + "_runs.xlsx",
                                               core_location + "average_of_" + str(TOTAL_RUN) + "_runs.jpg",
                                               project + '(' + hypo + ')' + ' - Average of 10 runs',
                                               x_label,
                                               project)
            else:
                plot_each_run_graph(core_location + "average_of_" + str(TOTAL_RUN) + "_runs.xlsx",
                                    core_location + "average_of_" + str(TOTAL_RUN) + "_runs.jpg",
                                    project + '(' + hypo + ')' + ' - Average of ' + str(TOTAL_RUN) + ' runs',
                                    x_label,
                                    hypo_name,
                                    project)

            # plot the graph by combining all the evaluation project for each hypothesis into single graph
            # Since Hypothesis 3 is only for one project, therefore do not run this for Hypothesis 3
            if not hypo == "Hypothesis3":
                plot_all_proj_in_each_hypo(HYPOTHESIS_PROJECTS, hypo, TOTAL_RUN)
