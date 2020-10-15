package de.fraunhofer.iem.secucheck.FluentTQLClassLoader;

import org.apache.commons.lang3.StringUtils;

public class PrintUtils {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

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
