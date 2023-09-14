package de.fraunhofer.iem.secucheck.fluenttql.english

import java.util.List
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.TaintFlowPackage.TaintFlow
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.TaintFlowPackage.FlowParticipant
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method
import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSet
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery
import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION

class BriefFluentTQL2Eng {
	def String multipleTaintFlows(List<TaintFlow> taintFlow) {
		var String multipleTaintFlowInfo = ""
		
		multipleTaintFlowInfo = "Below " + taintFlow.size() + 
			" TaintFlows should be present in the program to successfully report the error message.\n"
			
		for (i : 0 ..< taintFlow.size()) {
			
			multipleTaintFlowInfo += "\n" + (i + 1) + ". " + singleTaintFlows(taintFlow.get(i)) + "\n"
		}
		
		return multipleTaintFlowInfo
	}
	
	def String singleTaintFlows(TaintFlow taintFlow) {
		var FlowParticipant source = taintFlow.getFrom()
		var FlowParticipant sink = taintFlow.getTo()
		var List<FlowParticipant> through = taintFlow.getThrough()
		var List<FlowParticipant> notThrough = taintFlow.getNotThrough()
		
		var String taintFlowInEnglish = "A Taintflow: \n\t"
		
		if(source instanceof Method)
			taintFlowInEnglish += "from the source Method: " + (source as Method).getSignature() + "\n\t"
		else 
			taintFlowInEnglish += "one of the source in the MethodSet: " + (source as MethodSet).getName() + "\n\t"
			
		if(notThrough.size > 0) {
			if(notThrough.get(0) instanceof Method)
			taintFlowInEnglish += "and does not go through the sanitizer Method: " + (notThrough.get(0) as Method).getSignature() + "\n\t"
		else
			taintFlowInEnglish += "and does not go through one of the sanitizers in the MethodSet: " + (notThrough.get(0) as MethodSet).getName() + "\n\t"
		}
			
		for (i : 0 ..< through.size()) {
			if(through.get(i) instanceof Method)
				taintFlowInEnglish += "and goes through the required propogator Method: " + (through.get(0) as Method).getSignature() + "\n\t"
			else
				taintFlowInEnglish += "and goes through one of the required propogator in the MethodSet: " + (through.get(0) as MethodSet).getName() + "\n\t"
		}
		
		if(sink instanceof Method)
			taintFlowInEnglish += "and finally reaches the sink Method: " + (sink as Method).getSignature()
		else 
			taintFlowInEnglish += "and finally reaches one of sinks in the MethodSet: " + (sink as MethodSet).getName()

		return taintFlowInEnglish
	}
	
	def String translate(TaintFlowQuery taintFlowQuery) {
		val List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();
		var String fluentTQL2E = "TaintFlowQuery ID = " + taintFlowQuery.getId() + "\n";
		
		if(taintFlows.size() == 1) {
			fluentTQL2E += singleTaintFlows(taintFlows.get(0))
			fluentTQL2E += " then report the error message "
		}
		else if(taintFlows.size() == 0) {
			fluentTQL2E += "No Taint Flow found in this TaintFlowQuery"
		}
		else {
			fluentTQL2E += multipleTaintFlows(taintFlows)
			fluentTQL2E += "\nIf, above all the TaintFlows are present in the given program then report the error message\n"
		}
			
		fluentTQL2E += '''"''' + taintFlowQuery.getReportMessage() + '''"''' + " at "
		
		
		
		var LOCATION reportLocation = taintFlowQuery.getReportLocation()
		
		if(reportLocation == LOCATION.SOURCE)
			fluentTQL2E += "the source location in the IDE."
		else if(reportLocation == LOCATION.SINK)
			fluentTQL2E += "the sink location in the IDE."
		else if(reportLocation == LOCATION.SOURCEANDSINK)
			fluentTQL2E += "both the source and the sink location in the IDE.\n\n\t\t"
		
		return fluentTQL2E
  }
}