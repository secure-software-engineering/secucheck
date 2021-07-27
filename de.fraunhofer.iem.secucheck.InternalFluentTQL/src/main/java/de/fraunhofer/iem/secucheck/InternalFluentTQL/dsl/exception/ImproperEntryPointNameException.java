package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.ClassEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.MethodEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.PackageEntryPoint;

/**
 * This exception indicates that the given entry point through the classes of 
 * {@link MethodEntryPoint}, {@link ClassEntryPoint}, and {@link PackageEntryPoint}
 * is improper or incomplete because the qualified name of such entry point is
 * not a method, class, or package.
 *
 * @author Enri Ozuni
 */
public class ImproperEntryPointNameException extends FluentTQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImproperEntryPointNameException(String message) {
		super(message);
	}

}
