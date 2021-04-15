package de.fraunhofer.iem.secucheck.fluentTQL2English

import java.util.List
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery

class FluentTQL2English {
	def String printSingleMethod(Method method) {
		return "\n\t\t" + method.getSignature()
	}

	def String sourceInfo(FlowParticipant source) {
		var String sourceInfo = ""

		if(source instanceof Method) {
			sourceInfo += "\n\tfrom the below source: \n" +
			printSingleMethod(source as Method)
		}
		else {
			var MethodSet sourceMethodSet = source as MethodSet
			var List<Method> methods =  sourceMethodSet.getMethods()
			sourceInfo += "\n\tfrom any of the below sources: \n"
			for (i : 0 ..< methods.size()) {
				if(i == methods.size() - 1)
					sourceInfo += printSingleMethod(methods.get(i))
				else
					sourceInfo += printSingleMethod(methods.get(i))
			}
		}

		return sourceInfo
	}

	def String sinkInfo(FlowParticipant sink) {
		var String sinkInfo = ""

		if(sink instanceof Method) {
			sinkInfo += "\n\tand finally reaches the sink: \n" +
			printSingleMethod(sink as Method)
		}
		else {
			var MethodSet sourceMethodSet = sink as MethodSet
			var List<Method> methods =  sourceMethodSet.getMethods()
			sinkInfo += "\n\tand finally reaches any of the sink: \n"
			for (i : 0 ..< methods.size()) {
				if(i == methods.size() - 1)
					sinkInfo += printSingleMethod(methods.get(i))
				else
					sinkInfo += printSingleMethod(methods.get(i))
			}
		}

		return sinkInfo
	}

	def String sanitizerInfo(TaintFlow taintFlow) {
		var List<FlowParticipant> sanitizer = taintFlow.getNotThrough()
		var sanitizerInfo = ""
		var int count = 0
		var tempResult = ""

		for (i : 0 ..< sanitizer.size()) {
			if(sanitizer.get(i) instanceof Method) {
				sanitizerInfo += "\n\n\tthat does not go through the below sanitizer: \n"
				sanitizerInfo += printSingleMethod(sanitizer.get(i) as Method)
			} else if(sanitizer.get(i) instanceof MethodSet) {
				var List<Method> methods = (sanitizer.get(i) as MethodSet).getMethods()

				for (j : 0 ..< methods.size()) {
					count++
					tempResult += printSingleMethod(methods.get(j))
				}

				if(count == 0)
					sanitizerInfo += ""
				else if(count == 1)
					sanitizerInfo += "\n\n\tthat does not go through the below sanitizer: \n"
				else
					sanitizerInfo += "\n\n\tthat does not go through any of the below sanitizers: \n"
			}

			count = 0
			sanitizerInfo += tempResult
			tempResult = ""
		}

		return sanitizerInfo
	}

	def String deSanitizerInfo(TaintFlow taintFlow) {
		var List<FlowParticipant> deSanitizer = taintFlow.getThrough()
		var sanitizerInfo = ""
		var int count = 0
		var tempResult = ""

		for (i : 0 ..< deSanitizer.size()) {
			if(deSanitizer.get(i) instanceof Method) {
				sanitizerInfo += "\n\n\tand goes through the below de-sanitizer/required-propogator: \n"
				sanitizerInfo += printSingleMethod(deSanitizer.get(i) as Method)
			} else if(deSanitizer.get(i) instanceof MethodSet) {
				var List<Method> methods = (deSanitizer.get(i) as MethodSet).getMethods()

				for (j : 0 ..< methods.size()) {
					count++
					tempResult += printSingleMethod(methods.get(j))
				}

				if(count == 0)
					sanitizerInfo += ""
				else if(count == 1)
					sanitizerInfo += "\n\n\ttand goes through the below de-sanitizer/propogator: \n"
				else
					sanitizerInfo += "\n\n\tand goes through any of the below de-sanitizers/propogators: \n"
			}

			count = 0
			sanitizerInfo += tempResult
			tempResult = ""
		}



		return sanitizerInfo + "\n"
	}

	def String multipleTaintFlows(List<TaintFlow> taintFlow) {
		var String multipleTaintFlowInfo = ""

		multipleTaintFlowInfo = "Below " + taintFlow.size() +
			" TaintFlow should be present in the program to successfully report the error message.\n\n"

		for (i : 0 ..< taintFlow.size()) {

			multipleTaintFlowInfo += (i + 1) + ". " + singleTaintFlows(taintFlow.get(i)) + "\n\n"
		}

		return multipleTaintFlowInfo
	}

	def String singleTaintFlows(TaintFlow taintFlow) {
		var FlowParticipant source = taintFlow.getFrom()
		var FlowParticipant sink = taintFlow.getTo()
		var List<FlowParticipant> through = taintFlow.getThrough()
		var List<FlowParticipant> notThrough = taintFlow.getNotThrough()

		var String taintFlowInEnglish = "A Taintflow: " + sourceInfo(source)


		if(notThrough.size() > 0) {
			taintFlowInEnglish += sanitizerInfo(taintFlow)
		}

		if(through.size() > 0) {
			taintFlowInEnglish += deSanitizerInfo(taintFlow)
		}

		taintFlowInEnglish += sinkInfo(sink)

		return taintFlowInEnglish
	}

	def String translate(TaintFlowQuery taintFlowQuery) {
		val List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();
		var String fluentTQL2E = ""

		if(taintFlows.size() == 1) {
			fluentTQL2E += singleTaintFlows(taintFlows.get(0))
			fluentTQL2E += "\n\n\tthen report the below error message at "
		}
		else if(taintFlows.size() == 0) {
			fluentTQL2E += "No Taint Flow found in this TaintFlowQuery"
		}
		else {
			fluentTQL2E += multipleTaintFlows(taintFlows)
			fluentTQL2E += "\n\nIf, above all the TaintFlows are present in the given program then report the below error message at "
		}

		var String reportMessage = taintFlowQuery.getReportMessage()



		var LOCATION reportLocation = taintFlowQuery.getReportLocation()

		if(reportLocation == LOCATION.SOURCE)
			fluentTQL2E += "the source location in the IDE."
		else if(reportLocation == LOCATION.SINK)
			fluentTQL2E += "the sink location in the IDE."
		else if(reportLocation == LOCATION.SOURCEANDSINK)
			fluentTQL2E += "both the source and the sink location in the IDE.\n\n\t\t"

		return fluentTQL2E + reportMessage
  }
}