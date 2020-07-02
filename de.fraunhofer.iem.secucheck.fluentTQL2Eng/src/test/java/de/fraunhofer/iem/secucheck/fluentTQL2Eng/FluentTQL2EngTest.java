package de.fraunhofer.iem.secucheck.fluentTQL2Eng;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.fraunhofer.iem.secucheck.query.InputDeclaration;
import de.fraunhofer.iem.secucheck.query.Method;
import de.fraunhofer.iem.secucheck.query.OutputDeclaration;
import de.fraunhofer.iem.secucheck.query.Parameter;
import de.fraunhofer.iem.secucheck.query.QueryFactory;
import de.fraunhofer.iem.secucheck.query.TaintFlow;
import de.fraunhofer.iem.secucheck.query.TaintFlowQuery;

public class FluentTQL2EngTest {
	@Test
	public void blackboxTest() {
		Method m1 = QueryFactory.eINSTANCE.createMethod();
		m1.setSignature("Test: int getSecret()");
		m1.setName("m1");
		Method m2 = QueryFactory.eINSTANCE.createMethod();
		
		OutputDeclaration outDecl = QueryFactory.eINSTANCE.createOutputDeclaration();
		outDecl.getOutputs().add(QueryFactory.eINSTANCE.createReturnValue());
		m1.setOutputDeclaration(outDecl);
		
		InputDeclaration inDecl = QueryFactory.eINSTANCE.createInputDeclaration();
		Parameter param = QueryFactory.eINSTANCE.createParameter();
		param.setNumber(0);
		inDecl.getInputs().add(param);
		m2.setInputDeclaration(inDecl);
		
		
		m2.setSignature("Test: void publish(int)");
		m2.setName("m2");
		TaintFlowQuery flow = QueryFactory.eINSTANCE.createTaintFlowQuery();
		TaintFlow partialFlow = QueryFactory.eINSTANCE.createTaintFlow();
		TaintFlow partialFlow1 = QueryFactory.eINSTANCE.createTaintFlow();
		flow.getTaintFlows().add(partialFlow);
		flow.getTaintFlows().add(partialFlow1);
		partialFlow.getFrom().add(m1);
		partialFlow.getTo().add(m2);
		partialFlow1.getFrom().add(m2);
		partialFlow1.getTo().add(m1);
		
		flow.setReportMessage("Invalid Information Flow");
		flow.setReportLocation(3);
		
		List<TaintFlowQuery> taintFlows = new ArrayList<TaintFlowQuery>();
		taintFlows.add(flow);
		
		System.out.println(new BriefFluentTQL2Eng().translate(taintFlows.get(0)));
	}
}
