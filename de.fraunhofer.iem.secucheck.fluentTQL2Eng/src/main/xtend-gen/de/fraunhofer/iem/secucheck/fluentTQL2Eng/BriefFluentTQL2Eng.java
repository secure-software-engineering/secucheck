package de.fraunhofer.iem.secucheck.fluentTQL2Eng;

import de.fraunhofer.iem.secucheck.query.FlowParticipant;
import de.fraunhofer.iem.secucheck.query.Method;
import de.fraunhofer.iem.secucheck.query.MethodSet;
import de.fraunhofer.iem.secucheck.query.TaintFlow;
import de.fraunhofer.iem.secucheck.query.TaintFlowQuery;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class BriefFluentTQL2Eng {
  public String multipleTaintFlows(final List<TaintFlow> taintFlow) {
    String multipleTaintFlowInfo = "";
    int _size = taintFlow.size();
    String _plus = ("Below " + Integer.valueOf(_size));
    String _plus_1 = (_plus + 
      " TaintFlow should be present in the program to successfully report the error message.\n");
    multipleTaintFlowInfo = _plus_1;
    int _size_1 = taintFlow.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_1, true);
    for (final Integer i : _doubleDotLessThan) {
      String _multipleTaintFlowInfo = multipleTaintFlowInfo;
      String _plus_2 = (Integer.valueOf(((i).intValue() + 1)) + ". TaintFlow:\n");
      String _singleTaintFlows = this.singleTaintFlows(taintFlow.get((i).intValue()));
      String _plus_3 = (_plus_2 + _singleTaintFlows);
      String _plus_4 = (_plus_3 + "\n");
      multipleTaintFlowInfo = (_multipleTaintFlowInfo + _plus_4);
    }
    return multipleTaintFlowInfo;
  }
  
  public String singleTaintFlows(final TaintFlow taintFlow) {
    FlowParticipant source = taintFlow.getFrom().get(0);
    FlowParticipant sink = taintFlow.getTo().get(0);
    List<FlowParticipant> through = taintFlow.getThrough();
    List<FlowParticipant> notThrough = taintFlow.getNotThrough();
    String taintFlowInEnglish = "A Data flow: ";
    if ((source instanceof Method)) {
      String _taintFlowInEnglish = taintFlowInEnglish;
      String _name = ((Method) source).getName();
      String _plus = ("from the source Method: " + _name);
      taintFlowInEnglish = (_taintFlowInEnglish + _plus);
    } else {
      String _taintFlowInEnglish_1 = taintFlowInEnglish;
      String _name_1 = ((MethodSet) source).getName();
      String _plus_1 = ("one of the source in the MethodSet: " + _name_1);
      taintFlowInEnglish = (_taintFlowInEnglish_1 + _plus_1);
    }
    int _size = through.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      FlowParticipant _get = through.get(0);
      if ((_get instanceof Method)) {
        String _taintFlowInEnglish_2 = taintFlowInEnglish;
        FlowParticipant _get_1 = through.get(0);
        String _name_2 = ((Method) _get_1).getName();
        String _plus_2 = (" and does not go through the sanitizer Method: " + _name_2);
        taintFlowInEnglish = (_taintFlowInEnglish_2 + _plus_2);
      } else {
        String _taintFlowInEnglish_3 = taintFlowInEnglish;
        FlowParticipant _get_2 = through.get(0);
        String _name_3 = ((MethodSet) _get_2).getName();
        String _plus_3 = (" and does not go through one of the sanitizers in the MethodSet: " + _name_3);
        taintFlowInEnglish = (_taintFlowInEnglish_3 + _plus_3);
      }
    }
    int _size_1 = notThrough.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_1, true);
    for (final Integer i : _doubleDotLessThan) {
      FlowParticipant _get_3 = notThrough.get((i).intValue());
      if ((_get_3 instanceof Method)) {
        String _taintFlowInEnglish_4 = taintFlowInEnglish;
        FlowParticipant _get_4 = notThrough.get(0);
        String _name_4 = ((Method) _get_4).getName();
        String _plus_4 = (" and goes through the required propogator Method: " + _name_4);
        taintFlowInEnglish = (_taintFlowInEnglish_4 + _plus_4);
      } else {
        String _taintFlowInEnglish_5 = taintFlowInEnglish;
        FlowParticipant _get_5 = notThrough.get(0);
        String _name_5 = ((MethodSet) _get_5).getName();
        String _plus_5 = (" and goes through one of the required propogator in the MethodSet: " + _name_5);
        taintFlowInEnglish = (_taintFlowInEnglish_5 + _plus_5);
      }
    }
    if ((sink instanceof Method)) {
      String _taintFlowInEnglish_6 = taintFlowInEnglish;
      String _name_6 = ((Method) sink).getName();
      String _plus_6 = (" and finally reaches the sink Method: " + _name_6);
      taintFlowInEnglish = (_taintFlowInEnglish_6 + _plus_6);
    } else {
      String _taintFlowInEnglish_7 = taintFlowInEnglish;
      String _name_7 = ((MethodSet) sink).getName();
      String _plus_7 = (" and finally reaches one of sinks in the MethodSet: " + _name_7);
      taintFlowInEnglish = (_taintFlowInEnglish_7 + _plus_7);
    }
    return taintFlowInEnglish;
  }
  
  public String translate(final TaintFlowQuery taintFlowQuery) {
    final List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();
    String fluentTQL2E = "";
    int _size = taintFlows.size();
    boolean _equals = (_size == 1);
    if (_equals) {
      String _fluentTQL2E = fluentTQL2E;
      String _singleTaintFlows = this.singleTaintFlows(taintFlows.get(0));
      fluentTQL2E = (_fluentTQL2E + _singleTaintFlows);
      String _fluentTQL2E_1 = fluentTQL2E;
      fluentTQL2E = (_fluentTQL2E_1 + " then report the error message ");
    } else {
      int _size_1 = taintFlows.size();
      boolean _equals_1 = (_size_1 == 0);
      if (_equals_1) {
        String _fluentTQL2E_2 = fluentTQL2E;
        fluentTQL2E = (_fluentTQL2E_2 + "No Taint Flow found in this TaintFlowQuery");
      } else {
        String _fluentTQL2E_3 = fluentTQL2E;
        String _multipleTaintFlows = this.multipleTaintFlows(taintFlows);
        fluentTQL2E = (_fluentTQL2E_3 + _multipleTaintFlows);
        String _fluentTQL2E_4 = fluentTQL2E;
        fluentTQL2E = (_fluentTQL2E_4 + "If, above all the TaintFlows are present in the given program then report the error message ");
      }
    }
    String _fluentTQL2E_5 = fluentTQL2E;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _reportMessage = taintFlowQuery.getReportMessage();
    String _plus = (_builder.toString() + _reportMessage);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("\"");
    String _plus_1 = (_plus + _builder_1);
    String _plus_2 = (_plus_1 + " at ");
    fluentTQL2E = (_fluentTQL2E_5 + _plus_2);
    int reportLocation = (taintFlowQuery.getReportLocation()).intValue();
    if ((reportLocation == 1)) {
      String _fluentTQL2E_6 = fluentTQL2E;
      fluentTQL2E = (_fluentTQL2E_6 + "the source location in the IDE.");
    } else {
      if ((reportLocation == 2)) {
        String _fluentTQL2E_7 = fluentTQL2E;
        fluentTQL2E = (_fluentTQL2E_7 + "the sink location in the IDE.");
      } else {
        if ((reportLocation == 3)) {
          String _fluentTQL2E_8 = fluentTQL2E;
          fluentTQL2E = (_fluentTQL2E_8 + "both the source and the sink location in the IDE.\n\n\t\t");
        }
      }
    }
    return fluentTQL2E;
  }
}
