package de.fraunhofer.iem.secucheck.sarif.runs.result.location;

import de.fraunhofer.iem.secucheck.sarif.runs.result.location.physicalLocation.PhysicalLocation;

/**
 * Location item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Location {
    /**
     * Physical location
     */
    private PhysicalLocation physicalLocation;

    public PhysicalLocation getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(PhysicalLocation physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

}
