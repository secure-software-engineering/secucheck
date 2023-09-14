package de.fraunhofer.iem.secucheck.sarif.runs.result;

import de.fraunhofer.iem.secucheck.sarif.runs.result.location.Location;
import de.fraunhofer.iem.secucheck.sarif.runs.result.message.Message;

import java.util.List;

/**
 * Result item in the SARIF
 *
 * @author Ranjith Krishnamurthy
 */
public class Result {
    /**
     * TaintFlowQuery Id
     */
    private String ruleId;

    /**
     * Report message
     */
    private Message message;

    /**
     * Level of taintflow: error
     */
    private String level;

    /**
     * Locations of the taintflow
     */
    private List<Location> locations;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
