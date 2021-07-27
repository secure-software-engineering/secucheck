package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSignatureConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.ClassEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.MethodEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint.PackageEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This is a test class for the {@link MethodEntryPoint}, {@link ClassEntryPoint}, and {@link PackageEntryPoint}
 *
 * @author Enri Ozuni
 */
public class EntryPointTest {
	
	@Test
	public void correctMethodEntryPointTest() {
		String ep = "java.lang.String:    java.lang.String   toString  ( java.lang.String )";
		MethodEntryPoint entryPoint = new MethodEntryPoint(ep);
		String expected = "java.lang.String: java.lang.String toString(java.lang.String)";
		assertEquals(expected, entryPoint.getMethodEntryPointName());
	}
	
	@Test
	public void correctMethodEntryPointTest2() {
		MethodSignature ms = new MethodSignatureConfigurator()
				.atClass("java.lang.String ")
				.returns(" java.lang.String ")
				.named("toString")
				.accepts(" java.lang.String ")
				.configure();
		MethodEntryPoint entryPoint = new MethodEntryPoint(ms);
		String expected = "java.lang.String: java.lang.String toString(java.lang.String)";
		assertEquals(expected, entryPoint.getMethodEntryPointName());
	}
	
	@Test
	public void incorrectMethodEntryPointTest() {
		String ep = "java.lang.String    java.lang.String   toString  ( java.lang.String )";
		MethodEntryPoint entryPoint = new MethodEntryPoint(ep);
		assertEquals(null, entryPoint.getMethodEntryPointName());
	}
	
	@Test
	public void incorrectMethodEntryPointTest2() {
		MethodSignature ms = new MethodSignatureConfigurator()
				.atClass("java.lang.String ")
				.returns("  ")
				.named("toString")
				.accepts(" java.lang.String ")
				.configure();
		MethodEntryPoint entryPoint = new MethodEntryPoint(ms);
		assertEquals(null, entryPoint.getMethodEntryPointName());
	}
	
	@Test
	public void correctClassEntryPointTest() {
		String ep = "java.lang.String:  ";
		ClassEntryPoint entryPoint = new ClassEntryPoint(ep);
		String expected = "java.lang.String";
		assertEquals(expected, entryPoint.getClassEntryPointName());
	}
	
	@Test
	public void incorrectClassEntryPointTest() {
		String ep = "java.lang.String:    java.lang.String   toString  ( java.lang.String )";
		ClassEntryPoint entryPoint = new ClassEntryPoint(ep);
		assertEquals(null, entryPoint.getClassEntryPointName());
	}
	
	@Test
	public void correctPackageEntryPointTest() {
		String ep = "java.lang  ";
		PackageEntryPoint entryPoint = new PackageEntryPoint(ep);
		String expected = "java.lang";
		assertEquals(expected, entryPoint.getPackageEntryPointName());
	}
	
	@Test
	public void incorrectPackageEntryPointTest() {
		String ep = "java.lang    java.lang.String   toString  ( java.lang.String )";
		PackageEntryPoint entryPoint = new PackageEntryPoint(ep);
		assertEquals(null, entryPoint.getPackageEntryPointName());
	}
	
}
