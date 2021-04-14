package de.fraunhofer.iem.secucheck.fluentTQL2English

import java.util.List
import de.fraunhofer.iem.secucheck.analysis.query.TaintFlow
import de.fraunhofer.iem.secucheck.analysis.query.MethodImpl
import de.fraunhofer.iem.secucheck.analysis.query.Method
import de.fraunhofer.iem.secucheck.analysis.query.SecucheckTaintFlowQuery
import de.fraunhofer.iem.secucheck.analysis.query.TaintFlowImpl
import de.fraunhofer.iem.secucheck.analysis.query.ReportSite

class BriefFluentTQL2Eng {
	def String multipleTaintFlows(List<TaintFlowImpl> taintFlow) {
		var String multipleTaintFlowInfo = ""
		
		multipleTaintFlowInfo = "Below " + taintFlow.size() + 
			" TaintFlow should be present in the program to successfully report the error message.\n"
			
		for (i : 0 ..< taintFlow.size()) {
			
			multipleTaintFlowInfo += (i + 1) + ". TaintFlow:\n" + singleTaintFlows(taintFlow.get(i)) + "\n"
		}
		
		return multipleTaintFlowInfo
	}
	
	def String singleTaintFlows(TaintFlow taintFlow) {
		var List<MethodImpl> source = taintFlow.getFrom()
		var List<MethodImpl> sink = taintFlow.getTo()
		var List<MethodImpl> through = taintFlow.getThrough()
		var List<MethodImpl> notThrough = taintFlow.getNotThrough()
		
		var String taintFlowInEnglish = "A Data flow: "
		
		if(source instanceof Method)
			taintFlowInEnglish += "from the source Method: " + (source as Method).getSignature()
		else 
			taintFlowInEnglish += "one of the source in the MethodSet"
			
		if(through.size > 0) {
			if(through.get(0) instanceof Method)
			taintFlowInEnglish += " and does not go through the sanitizer Method: " + (through.get(0) as Method).getSignature()
		else
			taintFlowInEnglish += " and does not go through one of the sanitizers in the MethodSet: "
		}
			
		for (i : 0 ..< notThrough.size()) {
			if(notThrough.get(i) instanceof Method)
				taintFlowInEnglish += " and goes through the required propogator Method: " + (notThrough.get(0) as Method).getSignature()
			else
				taintFlowInEnglish += " and goes through one of the required propogator in the MethodSet: "
		}
		
		if(sink instanceof Method)
			taintFlowInEnglish += " and finally reaches the sink Method: " + (sink as Method).getSignature()

		return taintFlowInEnglish
	}
	
	def String translate(SecucheckTaintFlowQuery taintFlowQuery) {
		val List<TaintFlowImpl> taintFlows = taintFlowQuery.getTaintFlows();
		var String fluentTQL2E = ""
		
		if(taintFlows.size() == 1) {
			fluentTQL2E += singleTaintFlows(taintFlows.get(0))
			fluentTQL2E += " then report the error message "
		}
		else if(taintFlows.size() == 0) {
			fluentTQL2E += "No Taint Flow found in this TaintFlowQuery"
		}
		else {
			fluentTQL2E += multipleTaintFlows(taintFlows)
			fluentTQL2E += "If, above all the TaintFlows are present in the given program then report the error message "
		}
			
		fluentTQL2E += '''"''' + taintFlowQuery.getReportMessage() + '''"''' + " at "
		
		
		
		var ReportSite reportLocation = taintFlowQuery.getReportLocation()
		
		if(reportLocation == ReportSite.Source)
			fluentTQL2E += "the source location in the IDE."
		else if(reportLocation == ReportSite.Sink)
			fluentTQL2E += "the sink location in the IDE."
		else if(reportLocation == ReportSite.SourceAndSink)
			fluentTQL2E += "both the source and the sink location in the IDE.\n\n\t\t"
		
		return fluentTQL2E
  }
}