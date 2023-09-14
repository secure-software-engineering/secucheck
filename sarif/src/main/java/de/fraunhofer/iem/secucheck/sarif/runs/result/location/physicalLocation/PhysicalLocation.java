package de.fraunhofer.iem.secucheck.sarif.runs.result.location.physicalLocation;

import de.fraunhofer.iem.secucheck.sarif.runs.result.location.physicalLocation.fileLocation.ArtifactLocation;
import de.fraunhofer.iem.secucheck.sarif.runs.result.location.region.Region;

/**
 * Physical location item in the SARIF
 *
 * @author Ranjith Krishnamurthy
 */
public class PhysicalLocation {
    /**
     * File location
     */
    private ArtifactLocation artifactLocation;

    /**
     * Region
     */
    private Region region;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ArtifactLocation getArtifactLocation() {
        return artifactLocation;
    }

    public void setArtifactLocation(ArtifactLocation artifactLocation) {
        this.artifactLocation = artifactLocation;
    }
}
