package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal.SecuCheckMapieBridgeResult;
import magpiebridge.core.AnalysisResult;

import java.io.*;
import java.util.*;

public class SerializeResult {
    private List<String> fileList = new ArrayList<String>(
            Arrays.asList("CWE20_ImproperInputValidation",
                    "CWE22_PathTraversal",
                    "CWE311_MissingEncryption",
                    "CWE601_OpenRedirect_TaskController1",
                    "CWE78_OsCommandInjection_NewTaskController1",
                    "CWE78_OsCommandInjection_NewTaskController2",
                    "CWE79_CrossSiteScripting",
                    "CWE89_SqlInjection_DatabaseController1",
                    "CWE89_SqlInjection_DatabaseController2")
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
            System.out.println(specFile);
            System.out.println(entryPoints);
            System.out.println(fileToResultMap.keySet());
            if ("CWE20_ImproperInputValidation.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.LoginController")) {
                    partialResults.add(fileToResultMap.get("CWE20_ImproperInputValidation"));
                }
            }

            if ("CWE22_PathTraversal.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.NewTaskController")) {
                    partialResults.add(fileToResultMap.get("CWE22_PathTraversal"));
                }
            }

            if ("CWE311_MissingEncryption.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.NewTaskController")) {
                    partialResults.add(fileToResultMap.get("CWE311_MissingEncryption"));
                }
            }

            if ("CWE601_OpenRedirect.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.TaskController")) {
                    partialResults.add(fileToResultMap.get("CWE601_OpenRedirect_TaskController1"));
                }
            }

            if ("CWE78_OsCommandInjection.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.NewTaskController")) {
                    partialResults.add(fileToResultMap.get("CWE78_OsCommandInjection_NewTaskController1"));
                    partialResults.add(fileToResultMap.get("CWE78_OsCommandInjection_NewTaskController2"));
                }
            }

            if ("CWE79_CrossSiteScripting.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.LoginController")) {
                    partialResults.add(fileToResultMap.get("CWE79_CrossSiteScripting"));
                }
            }

            if ("CWE89_SqlInjection.java".equals(specFile)) {
                if (entryPoints.contains("de.fraunhofer.iem.secucheck.todolist.controllers.DatabaseController")) {
                    partialResults.add(fileToResultMap.get("CWE89_SqlInjection_DatabaseController1"));
                    partialResults.add(fileToResultMap.get("CWE89_SqlInjection_DatabaseController2"));
                }
            }
        }

        System.out.println("\n\n\n\nVery Imp = " + partialResults.size());
        return partialResults;
    }

    public void serializeToJson(Collection<AnalysisResult> results) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        mapper.enableDefaultTyping(
                ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);

        int count = 0;
        try {
            // Serialize Java object info JSON file.
            for (AnalysisResult result : results) {
                File file = new File("C:\\Users\\Ranjith\\SecucheckResults\\result" + ++count + ".json");
                SecuCheckMapieBridgeResult resultFinal = (SecuCheckMapieBridgeResult) result;
                mapper.writeValue(file, resultFinal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeFromJson() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        mapper.enableDefaultTyping(
                ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);

        completeResults.clear();

        for (String fileName : fileList) {
            File file = new File("C:\\Users\\Ranjith\\SecucheckResults\\" + fileName + ".json");

            try {
                // Deserialize JSON file into Java object.
                SecuCheckMapieBridgeResult analysisResult = mapper.readValue(file, SecuCheckMapieBridgeResult.class);
                completeResults.add(analysisResult);
                fileToResultMap.put(fileName, analysisResult);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
