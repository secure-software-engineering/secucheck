package de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.SecucheckHttpServer.utility;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the utils for Freemarker java template engine to create an actual file from the template.
 *
 * @author Ranjith Krishnamurthy
 */
public class FreeMarkerUtility {
    public static String setFirstPageFile(String projectName) {

        String resolvedProjectName = "null";

        if (projectName != null && !"".equals(projectName))
            resolvedProjectName = projectName;

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

        try {
            //Set up the free marker configuration, with template loading class and path.
            cfg.setClassForTemplateLoading(FreeMarkerUtility.class, "/");
            cfg.setDefaultEncoding("UTF-8");

            //Get the Iguana configuration template.
            Template template = cfg.getTemplate("firstpage.ftl");

            //Port number and query file to insert into benchmark template
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("projectName", resolvedProjectName);

            StringWriter out = new StringWriter();
            template.process(templateData, out);
            out.flush();

            return out.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    public static String setSecondPageFile(String projectName, String specDiv, String entryPointDiv) {

        String resolvedProjectName = "null";
        String resolvedSpecDiv = "null";
        String resolvedEntryPointDiv = "null";

        if (projectName != null && !"".equals(projectName))
            resolvedProjectName = projectName;

        if (specDiv != null && !"".equals(specDiv))
            resolvedSpecDiv = specDiv;

        if (entryPointDiv != null && !"".equals(entryPointDiv))
            resolvedEntryPointDiv = entryPointDiv;


        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

        try {
            //Set up the free marker configuration, with template loading class and path.
            cfg.setClassForTemplateLoading(FreeMarkerUtility.class, "/");
            cfg.setDefaultEncoding("UTF-8");

            //Get the Iguana configuration template.
            Template template = cfg.getTemplate("secondpage.ftl");

            //Port number and query file to insert into benchmark template
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("projectName", resolvedProjectName);
            templateData.put("specDiv", resolvedSpecDiv);
            templateData.put("entryPointDiv", resolvedEntryPointDiv);

            StringWriter out = new StringWriter();
            template.process(templateData, out);
            out.flush();

            return out.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}