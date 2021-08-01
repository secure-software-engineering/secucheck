package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.EntryPoint.EntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

/**
 * This class represents an entry point as a Java package that can be added to the entry point list in {@link TaintFlowQuery}
 * 
 * @author Enri Ozuni
 */
public class PackageEntryPoint implements EntryPoint {

	private String packageEntryPointName;

	public PackageEntryPoint(String packageEntryPointName) {
		Objects.requireNonNull(packageEntryPointName, "Argument of PackageEntryPoint's contructor is null.");
		setPackageEntryPointName(packageEntryPointName);
	}

	public String getPackageEntryPointName() {
		return packageEntryPointName;
	}

	public void setPackageEntryPointName(String packageEntryPointName) {
		Objects.requireNonNull(packageEntryPointName, "Argument of setPackageEntryPointName() method is null.");
		this.packageEntryPointName = packageEntryPointName;	
	}
		
}
