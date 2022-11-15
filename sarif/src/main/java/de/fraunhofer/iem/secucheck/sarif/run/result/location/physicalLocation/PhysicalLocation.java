package de.fraunhofer.iem.secucheck.SecuCheckSARIFRepresentations.run.result.location.physicalLocation;

import de.fraunhofer.iem.secucheck.SecuCheckSARIFRepresentations.run.result.location.physicalLocation.fileLocation.FileLocation;

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
