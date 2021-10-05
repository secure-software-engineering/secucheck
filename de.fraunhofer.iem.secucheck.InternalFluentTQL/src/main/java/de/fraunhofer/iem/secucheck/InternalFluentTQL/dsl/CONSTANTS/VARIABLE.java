package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

/**
 * Enumeration that can be given as source only for HARDCODED or NULL variables
 *
 * @author Enri Ozuni
 */
public enum VARIABLE implements FlowParticipant{
	HARDCODED,
	NULL
}
