package secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.result.location.physicalLocation;

import secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.result.location.physicalLocation.fileLocation.FileLocation;

/**
 * Physical location item in the SARIF
 *
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
