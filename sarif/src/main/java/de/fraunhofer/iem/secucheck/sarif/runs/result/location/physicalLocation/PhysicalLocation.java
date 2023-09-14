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
    private FileLocation fileLocation;

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(FileLocation fileLocation) {
        this.fileLocation = fileLocation;
    }
}
