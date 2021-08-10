package secucheck.FluentTQLClassLoader;

import org.apache.commons.lang3.StringUtils;

/**
 * This class provides the feature of printing the status of a single class
 *
 */
public class PrintUtils {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * This method prints the status of a single class
     *
     * @param className Class name
     * @param status    Status
     * @param isError   Is it a error or success, if its error then it prints in red color.
     */
    public void printClassStatus(String className, String status, boolean isError) {
        String statusColor;

        if (isError)
            statusColor = ANSI_RED;
        else
            statusColor = ANSI_GREEN;

        System.out.println(
                "* " + ANSI_BLUE + className + ANSI_RESET + StringUtils.repeat(" ", 80 - className.length()) +
                        statusColor + status + ANSI_RESET
        );
    }
}
