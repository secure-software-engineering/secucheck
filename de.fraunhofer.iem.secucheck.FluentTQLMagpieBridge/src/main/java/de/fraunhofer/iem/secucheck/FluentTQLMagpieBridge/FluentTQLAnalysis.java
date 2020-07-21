package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import com.ibm.wala.classLoader.Module;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import magpiebridge.core.AnalysisConsumer;
import magpiebridge.core.AnalysisResult;
import magpiebridge.core.ServerAnalysis;
import magpiebridge.core.ToolAnalysis;
import magpiebridge.core.analysis.configuration.ConfigurationAction;
import magpiebridge.core.analysis.configuration.ConfigurationOption;
import magpiebridge.core.analysis.configuration.OptionType;
import magpiebridge.core.analysis.configuration.htmlElement.CheckBox;

import java.io.File;
import java.util.*;

/**
 * This class is the FluentTQL Taint analysis. This implements the configuration options for configuration pages.
 * And uses these configuration options set by the user and set the FluentTQL analysis.
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLAnalysis implements ToolAnalysis, ServerAnalysis {

    private List<ConfigurationOption> options = new ArrayList<ConfigurationOption>();
    private String fluentTQLSpecPath = "";
    private List<File> fluentTQLSpecList = new ArrayList<File>();
    private static HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    private static List<TaintFlowQuery> listOfConfiguredTaintFlowQueries = new ArrayList<>();

    /**
     * Constructor sets the initial configuration option for the configuration page.
     */
    public FluentTQLAnalysis() {
        ConfigurationOption pathRequesting = new ConfigurationOption("FluentTQL Specification's path", OptionType.text);
        options.add(pathRequesting);
    }

    /**
     * Returns the source name for the Magpie bridge required for configuration page.
     *
     * @return Source name
     */
    public String source() {
        return "FluentTQLAnalysis";
    }

    /**
     * This method runs the analysis
     *
     * @param files
     * @param server
     * @param rerun
     */
    public void analyze(Collection<? extends Module> files, AnalysisConsumer server, boolean rerun) {
        //Todo: Add running analysis code here.
        System.out.println("Analysis is on progress");
        System.out.println(listOfConfiguredTaintFlowQueries.size());
    }

    /**
     * This method returns the configuration options to the Magpie bridge for configuration page.
     *
     * @return List of configuration options
     */
    public List<ConfigurationOption> getConfigurationOptions() {
        return options;
    }

    /**
     * This method returns the configuration actions to the Magpie bridge for configuration page.
     *
     * @return List of configuration actions
     */
    public List<ConfigurationAction> getConfiguredActions() {
        return Collections.emptyList();
    }

    /**
     * This method configures the analysis or configuration page based on the user input given in the previous
     * configuration page.
     *
     * @param configuration List of configuration options from the Magpie bridge
     */
    public void configure(List<ConfigurationOption> configuration) {
        for (ConfigurationOption configOption : configuration) {
            if ("FluentTQL Specification's path".equals(configOption.getName())) {
                String specPath = configOption.getValue();

                if (specPath == null || "".equals(specPath)) {
                    System.err.println("Invalid path");
                    return;
                }

                File file = new File(specPath);

                if (file.exists()) {
                    if (file.isDirectory()) {
                        fluentTQLSpecPath = specPath;

                        fluentTQLSpecs = InternalFluentTQLIntegration.getSpecs(file.getAbsolutePath());

                        if (fluentTQLSpecs.size() > 0)
                            setConfig(fluentTQLSpecs);
                        else
                            System.err.println("No FluentTQL specifications present in the given path");
                    } else {
                        System.err.println("Given path is not a directory. Please give valid directory name.");
                        return;
                    }
                } else {
                    System.err.println("Path does not exist");
                    return;
                }
            } else if ("FluentTQL Specification files".equals(configOption.getName())) {
                listOfConfiguredTaintFlowQueries.clear();

                for (ConfigurationOption configurationOption : configOption.getChildren()) {
                    if (configurationOption.getValueAsBoolean()) {
                        System.out.println(configurationOption.getName());
                        // Get the list of FluentTQLSpecification
                        List<FluentTQLSpecification> fluentTQLSpecificationList = fluentTQLSpecs.get(configurationOption.getName()).getFluentTQLSpecification();

                        // Get the TaintFLowQuery object.
                        for (FluentTQLSpecification fluentTQLSpecification : fluentTQLSpecificationList) {
                            if (fluentTQLSpecification instanceof TaintFlowQuery) {
                                TaintFlowQuery taintFlowQuery = (TaintFlowQuery) fluentTQLSpecification;

                                listOfConfiguredTaintFlowQueries.add(taintFlowQuery);
                            } else if (fluentTQLSpecification instanceof QueriesSet) {
                                QueriesSet queriesSet = (QueriesSet) fluentTQLSpecification;

                                listOfConfiguredTaintFlowQueries.addAll(queriesSet.getTaintFlowQueries());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method sets the new configuration option for new configuration page.
     *
     * @param fluentSpecs FluentTQL specifications
     */
    private void setConfig(HashMap<String, FluentTQLUserInterface> fluentSpecs) {
        ConfigurationOption initialOption = new CheckBox(
                "FluentTQL Specification files",
                true);
        initialOption.setValue("on");

        Set<String> keys = fluentSpecs.keySet();

        for (String key : keys) {
            ConfigurationOption myOption = new CheckBox(
                    key,
                    true
            );
            myOption.setValue("on");
            initialOption.addChild(myOption);
        }

        options.clear();
        options.add(initialOption);
    }

    public String[] getCommand() {
        return new String[0];
    }

    public Collection<AnalysisResult> convertToolOutput() {
        return null;
    }
}
