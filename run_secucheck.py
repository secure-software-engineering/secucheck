import subprocess
import os
import xlsxwriter
import yaml
import shutil

import pandas as pd

from run_secucheck_evaluation import TOTAL_RUN
from run_secucheck_evaluation import SECUCHECK_JAR
from run_secucheck_evaluation import HYPOTHESIS_PROJECTS


# Parse the SecuCheck output file to get the information like run time and so on.
def parse_output_yaml(out_dir, output_file_name):
    with open(out_dir + output_file_name + "_additional.yml") as stream:
        try:
            return yaml.safe_load(stream)
        except yaml.YAMLError as exc:
            print(exc)


# Parse the SecuCheck settings file to get the information like entry points and so on.
def parse_settings_yaml(settings_file):
    with open(settings_file) as stream:
        try:
            output_yaml = yaml.safe_load(stream)
            spec_count = 0
            entry_points_count = 0

            for key, value in output_yaml.items():
                if key == 'entryPoints':
                    entry_points_count = value.__len__()

                if key == 'selectedSpecs':
                    spec_count = value.__len__()

            return spec_count, entry_points_count
        except yaml.YAMLError as exc:
            print(exc)


# Adds the given run result into the statistics file
def add_stat(row, entry_points_count, spec_count, file_location, outputYaml, worksheet):
    col = 0
    worksheet.write(row, col, entry_points_count)
    col += 1
    worksheet.write(row, col, spec_count)
    col += 1

    total_taintflow_found = 0

    for res in outputYaml['result']:
        for key, value in res.items():
            for item in value:
                for k, v in item.items():
                    if k == 'taintFlowFoundCount':
                        total_taintflow_found += v

    worksheet.write(row, col, total_taintflow_found)
    col += 1

    worksheet.write(row, col, outputYaml["totalSeedCount"])
    col += 1

    worksheet.write(row, col, outputYaml["totalTimeInSec"])
    col += 1

    worksheet.write(row, col, outputYaml["totalTimeInMilli"])
    col += 1

    worksheet.write(row, col, file_location)
    col += 1


# Runs the SecuCheck for the given run, hypothesis and the project
def run_secucheck(core_location, secucheck_jar, output_directory_name, output_file_name, worksheet):
    # Get the settings file
    settings_file_count: int = 0
    for root, dirs, files in os.walk(core_location + 'settings_files/'):
        for file in files:
            if (file.startswith('settings')) & file.endswith('.yml'):
                settings_file_count += 1

    for index in range(1, settings_file_count + 1):
        settings_file: str = core_location + 'settings_files' + os.path.sep + 'settings' + str(index) + '.yml'
        out_dir: str = core_location + output_directory_name + 'output' + os.path.sep + 'settings' + str(
            index) + os.path.sep

        spec_count, entry_points_count = parse_settings_yaml(settings_file)

        if not os.path.exists(out_dir):
            os.makedirs(out_dir)
        else:
            shutil.rmtree(out_dir)
            os.makedirs(out_dir)

        # Run the SecuCheck for the current settings
        subprocess.call(['java',
                         '-jar',
                         secucheck_jar,
                         '-scp',
                         settings_file,
                         '-od',
                         out_dir,
                         '-of',
                         output_file_name])

        # Adds the current run statistics to the excel sheet
        add_stat(index, entry_points_count, spec_count, os.path.abspath(out_dir),
                 parse_output_yaml(out_dir, output_file_name), worksheet)


# Runs the SecuCheck for the given hypothesis and the projects for the given total number of runs
# This generates the SecuCheck results and the statistics file of each run and the average of the total number of runs
def run():
    # For each Hypothesis
    for hypo in HYPOTHESIS_PROJECTS.keys():
        # For each evaluation project
        for project in HYPOTHESIS_PROJECTS.get(hypo):
            average_df = None
            average_run = 0

            # For each run
            for run_count in range(1, TOTAL_RUN + 1):
                core_location: str = hypo + os.path.sep + project + os.path.sep
                stats_filename: str = 'run' + str(run_count) + '_stats'
                output_directory_name: str = 'run' + str(run_count) + '_output' + os.path.sep
                output_file_name: str = 'run' + str(run_count) + '_out'

                # Create a workbook and add a worksheet.
                workbook = xlsxwriter.Workbook(core_location + output_directory_name + stats_filename + '.xlsx')
                worksheet = workbook.add_worksheet()

                # Add titles
                row = 0
                col = 0
                worksheet.write(row, col, 'Entry points count')
                col += 1
                worksheet.write(row, col, 'Specifications Count')
                col += 1
                worksheet.write(row, col, 'Total Taintflow found')
                col += 1
                worksheet.write(row, col, 'Total Seed count')
                col += 1
                worksheet.write(row, col, 'Total time in sec')
                col += 1
                worksheet.write(row, col, 'Total time in Milli')
                col += 1
                worksheet.write(row, col, 'SecuCheck Output Location')
                col += 1

                print("**************************************************************************")
                print("Core Location = " + core_location + "\n" + "Output Directory Name = " + output_directory_name +
                      "\n" + "Output File Name = " + output_file_name)
                print("**************************************************************************")

                # Call the evaluation (i.e. running SecuCheck)
                run_secucheck(core_location, SECUCHECK_JAR, output_directory_name, output_file_name, worksheet)

                # Close the current workbook
                workbook.close()

                # Adds the times, to find the average later
                df1 = pd.read_excel(core_location + output_directory_name + stats_filename + '.xlsx')

                if average_df is None:
                    average_df = df1
                    average_run += 1
                else:
                    average_run += 1
                    for i in range(0, len(df1['Total time in sec'])):
                        average_df.at[i, 'Total time in sec'] += df1.at[i, 'Total time in sec']
                        average_df.at[i, 'Total time in Milli'] += df1.at[i, 'Total time in Milli']

            # Find the average, divide by total number of runs
            for i in range(0, len(average_df['Total time in sec'])):
                average_df.at[i, 'Total time in sec'] /= average_run
                average_df.at[i, 'Total time in Milli'] /= average_run

            # Deletes the  average file already exist
            if os.path.exists(hypo + os.path.sep + project + os.path.sep + "average_of_" + str(TOTAL_RUN) + "_runs.xlsx"):
                os.remove(hypo + os.path.sep + project + os.path.sep + "average_of_" + str(TOTAL_RUN) + "_runs.xlsx")

            # Deletes the 'SecuCheck Output Location' tab and stores the average of TOTAL_NUMBER_OF_RUNS
            average_df.__delitem__('SecuCheck Output Location')
            average_df.to_excel(hypo + os.path.sep + project + os.path.sep + "average_of_" + str(TOTAL_RUN) + "_runs.xlsx",
                                encoding='utf-8', index=False)
