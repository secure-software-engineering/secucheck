package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.EntryPoint.EntryPoint;
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
		this.classEntryPointName = classEntryPointName;
	}
	
}
