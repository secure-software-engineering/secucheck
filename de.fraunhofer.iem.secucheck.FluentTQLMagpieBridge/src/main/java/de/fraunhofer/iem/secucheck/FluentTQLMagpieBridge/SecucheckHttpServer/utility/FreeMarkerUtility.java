package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.utility;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the utils for Freemarker java template engine to create an actual file from the template.
 *
 * @author Ranjith Krishnamurthy
 * @author Rahul sethi
 */
public class FreeMarkerUtility {
    public static String setFirstPageFile(String projectName) {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

        try {
            //Set up the free marker configuration, with template loading class and path.
            cfg.setClassForTemplateLoading(FreeMarkerUtility.class, "/");
            cfg.setDefaultEncoding("UTF-8");

            //Get the Iguana configuration template.
            Template template = cfg.getTemplate("firstpage.ftl");

            //Port number and query file to insert into benchmark template
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("projectName", projectName);

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

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

        try {
            //Set up the free marker configuration, with template loading class and path.
            cfg.setClassForTemplateLoading(FreeMarkerUtility.class, "/");
            cfg.setDefaultEncoding("UTF-8");

            //Get the Iguana configuration template.
            Template template = cfg.getTemplate("secondpage.ftl");

            //Port number and query file to insert into benchmark template
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("projectName", projectName);
            templateData.put("specDiv", specDiv);
            templateData.put("entryPointDiv", entryPointDiv);

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