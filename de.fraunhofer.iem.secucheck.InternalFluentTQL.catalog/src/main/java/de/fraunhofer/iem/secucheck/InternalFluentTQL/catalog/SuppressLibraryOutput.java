package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This class suppresses the library output.
 *
 * @author Ranjith Krishnamurthy
 */
public class SuppressLibraryOutput {

    static PrintStream backUP;
    static PrintStream backUPErr;
    static OutputStream output;
    static PrintStream printOut;
    static OutputStream outErr;
    static PrintStream printErr;

    /**
     * This method suppresses the library output.
     *
     * @throws FileNotFoundException If it fails to redirect the library output to a
     *                               temporary file.
     */
    public static void SupressOutput() throws FileNotFoundException {
        backUP = System.out;
        output = new FileOutputStream("temp");
        printOut = new PrintStream(output);

        backUPErr = System.err;
        outErr = new FileOutputStream("tempErr");
        printErr = new PrintStream(output);

        System.setOut(printOut);
        System.setErr(printErr);
    }

    /**
     * This method restores the standard output.
     *
     * @throws FileNotFoundException If it fails to delete the temporary file.
     */
    public static void Restore() throws IOException {
        output.close();
        printOut.close();
        outErr.close();
        printErr.close();
        new File("temp").delete();
        new File("tempErr").delete();
        System.setOut(backUP);
        System.setErr(backUPErr);
    }
}
