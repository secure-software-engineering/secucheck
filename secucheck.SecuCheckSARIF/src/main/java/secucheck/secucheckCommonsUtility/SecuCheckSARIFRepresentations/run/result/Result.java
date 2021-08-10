package secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.result;

import secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.result.location.Location;
import secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.result.message.Message;

import java.util.List;

/**
 * Result item in the SARIF
 *
 */
public class Result {
    /**
     * TaintFlowQuery Id
     */
    private String ruleId;

    /**
     * Message ID: Location with the BriefFluentTQL2English
     */
    private String ruleMessageId;

    /**
     * RichMessage ID: Location with the FluentTQL2English
     */
    private String richMessageId;

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

    public String getRuleMessageId() {
        return ruleMessageId;
    }

    public void setRuleMessageId(String ruleMessageId) {
        this.ruleMessageId = ruleMessageId;
    }

    public String getRichMessageId() {
        return richMessageId;
    }

    public void setRichMessageId(String richMessageId) {
        this.richMessageId = richMessageId;
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
