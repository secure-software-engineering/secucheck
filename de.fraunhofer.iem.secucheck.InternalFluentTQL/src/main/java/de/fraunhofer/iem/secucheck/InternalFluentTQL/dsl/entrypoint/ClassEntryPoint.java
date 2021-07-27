package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.ImproperEntryPointNameException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

/**
 * This class represents an entry point as a Java class that can be added to the entry point list in {@link TaintFlowQuery}
 * 
 * @author Enri Ozuni
 */
public class ClassEntryPoint implements EntryPoint {
	
	private String classEntryPointName;

	public ClassEntryPoint(String classEntryPointName) {
		Objects.requireNonNull(classEntryPointName, "Argument of ClassEntryPoint's contructor is null.");
		setClassEntryPointName(classEntryPointName);
	}

	public String getClassEntryPointName() {
		return classEntryPointName;
	}

	public void setClassEntryPointName(String classEntryPointName) {
		Objects.requireNonNull(classEntryPointName, "Argument of setClassEntryPointName() method is null.");
		
		try {
			this.classEntryPointName = parseClassEntryPointName(classEntryPointName);
		} catch (ImproperEntryPointNameException e) {
			e.printStackTrace();
		}
	}
	
	
	private String parseClassEntryPointName(String classEntryPointName) throws ImproperEntryPointNameException {
		if(classEntryPointName.trim().split("\\s+").length > 1) {
			throw new ImproperEntryPointNameException("Entry point name "+classEntryPointName+
					" that was given as class, is not a proper Java class name.");
		}
		return classEntryPointName.trim().replaceAll(":", "");
	}
	
}
