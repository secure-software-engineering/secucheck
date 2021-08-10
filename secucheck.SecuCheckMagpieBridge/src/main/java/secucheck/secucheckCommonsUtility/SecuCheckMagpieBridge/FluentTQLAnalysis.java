package secucheck.secucheckCommonsUtility.SecuCheckMagpieBridge;

import com.ibm.wala.classLoader.Module;
import magpiebridge.core.AnalysisConsumer;
import magpiebridge.core.AnalysisResult;
import magpiebridge.core.ServerAnalysis;
import magpiebridge.core.ToolAnalysis;
import magpiebridge.core.analysis.configuration.ConfigurationAction;
import magpiebridge.core.analysis.configuration.ConfigurationOption;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class is the FluentTQL Taint analysis. This implements the configuration options for configuration pages.
 * And uses these configuration options set by the user and set the FluentTQL analysis.
 *
 */
public class FluentTQLAnalysis implements ToolAnalysis, ServerAnalysis {
    /**
     * Returns the source name for the Magpie bridge required for configuration page.
     *
     * @return Source name
     */
    public String source() {
        return FluentTQLAnalysisConfigurator.getSource();
    }

    /**
     * This method runs the analysis
     *
     * @param files  Files
     * @param server Analysis Server
     * @param rerun  Is rerun
     */
    public void analyze(Collection<? extends Module> files, AnalysisConsumer server, boolean rerun) {
        if (rerun) {
            FluentTQLAnalysisConfigurator.runAnalysis();
        }
    }

    /**
     * This method returns the configuration options to the Magpie bridge for configuration page.
     *
     * @return List of configuration options
     */
    public List<ConfigurationOption> getConfigurationOptions() {
        return Collections.emptyList();
    }

    /**
     * This method returns the configuration actions to the Magpie bridge for configuration page.
     *
     * @return List of configuration actions
     */
    public List<ConfigurationAction> getConfiguredActions() {
        return Collections.emptyList();
    }

    public String[] getCommand() {
        return new String[0];
    }

    public Collection<AnalysisResult> convertToolOutput() {
        return null;
    }
}
