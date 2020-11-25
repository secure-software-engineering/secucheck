package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import magpiebridge.core.AnalysisResult;

import java.io.*;
import java.util.*;

public class SerializeResult {
    private List<String> fileList = new ArrayList<String>(
            Arrays.asList("CWE20_LoginController_Sink.ser",
                    "CWE20_LoginController_Source.ser",
                    "CWE22_TaskController_Sink.ser",
                    "CWE22_TaskController_Source.ser",
                    "CWE311_TaskController_Sink.ser",
                    "CWE311_TaskController_Source.ser",
                    "CWE601_TaskController_Sink.ser",
                    "CWE601_TaskController_Source.ser",
                    "CWE78_TaskController_Sink.ser",
                    "CWE78_TaskController_Source.ser",
                    "CWE79_LoginController_Sink.ser",
                    "CWE79_LoginController_Source.ser",
                    "CWE89_SimpleSQLInjection_Sink.ser",
                    "CWE89_SimpleSQLInjection_Source.ser",
                    "CWE89_TaskController_Sink.ser",
                    "CWE89_TaskController_Source.ser")
    );

    private final Collection<AnalysisResult> completeResults = new ArrayList<AnalysisResult>();
    private final Collection<AnalysisResult> partialResults = new ArrayList<AnalysisResult>();
    private final HashMap<String, AnalysisResult> fileToResultMap = new HashMap<String, AnalysisResult>();

    public Collection<AnalysisResult> getCompleteResults() {
        return completeResults;
    }

    public Collection<AnalysisResult> getPartialResults(List<String> specificationFiles, List<String> entryPoints) {
        partialResults.clear();

        for (String specFile : specificationFiles) {
            if ("CWE22.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController")) {
                    partialResults.add(fileToResultMap.get("CWE22_TaskController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE22_TaskController_Source.ser"));
                }
            }

            if ("CWE20.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.LoginController")) {
                    partialResults.add(fileToResultMap.get("CWE20_LoginController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE20_LoginController_Source.ser"));
                }
            }

            if ("CWE78.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController")) {
                    partialResults.add(fileToResultMap.get("CWE78_TaskController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE78_TaskController_Source.ser"));
                }
            }

            if ("CWE311.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController")) {
                    partialResults.add(fileToResultMap.get("CWE311_TaskController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE311_TaskController_Source.ser"));
                }
            }

            if ("CWE89.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController")) {
                    partialResults.add(fileToResultMap.get("CWE89_TaskController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE89_TaskController_Source.ser"));
                }
            }

            if ("CWE79.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.LoginController")) {
                    partialResults.add(fileToResultMap.get("CWE79_LoginController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE79_LoginController_Source.ser"));
                }
            }

            if ("CWE601.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController")) {
                    partialResults.add(fileToResultMap.get("CWE601_TaskController_Sink.ser"));
                    partialResults.add(fileToResultMap.get("CWE601_TaskController_Source.ser"));
                }
            }
        }

        System.out.println("\n\n\n\nVery Imp = " + partialResults.size());
        return partialResults;
    }

    public void serializeResult(Collection<AnalysisResult> results) {
        int count = 0;
        for (AnalysisResult result : results) {
            // Serialization
            try {
                System.out.println("Separator = " + System.getProperty("file.separator"));
                System.out.println(result.position().getURL().toString());

                String[] splitFileName = result.position().getURL().toString().split("/");
                String message = result.toString()
                        .replaceAll(" ", "_")
                        .replaceAll(":", "")
                        .replaceAll("-", "")
                        .replaceAll("'", "")
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", "")
                        .replaceAll("\\.", "");
                int line = result.position().getFirstLine();

                //Saving of object in a file
                System.out.println("Last element = " + splitFileName[splitFileName.length - 1]);
                System.out.println("Replacement = " + splitFileName[splitFileName.length - 1].replaceAll(".java", ""));

                System.out.println("----->>>>> C:\\Users\\Ranjith\\SecucheckResults\\"
                        + splitFileName[splitFileName.length - 1].replaceAll(".java", "")
                        + ".ser");
                FileOutputStream file = new FileOutputStream(
                        "C:\\Users\\Ranjith\\SecucheckResults\\"
                                + splitFileName[splitFileName.length - 1].replaceAll(".java", "")
                                + message
                                + "_"
                                + line
                                + ".ser"
                );

                ObjectOutputStream out = new ObjectOutputStream(file);

                // Method for serialization of object
                out.writeObject(result);

                out.close();
                file.close();

                System.out.println("Object has been serialized");

            } catch (IOException ex) {
                System.out.println("IOException is caught: " + ex.getMessage());
            }
        }
    }

    public void deserializeResult() {
        int count = 0;
        Collection<AnalysisResult> results = new ArrayList<AnalysisResult>();

        completeResults.clear();
        fileToResultMap.clear();

        for (String fileName : fileList) {
            try {
                // Reading the object from a file
                FileInputStream file = new FileInputStream("C:\\Users\\Ranjith\\SecucheckResults\\" + fileName);
                ObjectInputStream in = new ObjectInputStream(file);

                // Method for deserialization of object
                AnalysisResult analysisResult = ((AnalysisResult) in.readObject());
                completeResults.add(analysisResult);
                fileToResultMap.put(fileName, analysisResult);

                in.close();
                file.close();
            } catch (IOException ex) {
                System.out.println("IOException is caught");
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException is caught");
            }
        }
    }
}
