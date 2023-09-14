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

    /**
     * Region
     */
    private Region region;

    public PhysicalLocation getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(PhysicalLocation physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
