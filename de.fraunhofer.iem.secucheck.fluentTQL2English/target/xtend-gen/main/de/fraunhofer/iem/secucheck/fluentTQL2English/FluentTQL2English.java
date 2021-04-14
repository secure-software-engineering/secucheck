package de.fraunhofer.iem.secucheck.fluentTQL2English;

import com.google.common.base.Objects;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import java.util.List;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class FluentTQL2English {
  public String printSingleMethod(final Method method) {
    String _signature = method.getSignature();
    return ("\n\t\t" + _signature);
  }
  
  public String sourceInfo(final FlowParticipant source) {
    String sourceInfo = "";
    if ((source instanceof Method)) {
      String _sourceInfo = sourceInfo;
      String _printSingleMethod = this.printSingleMethod(((Method) source));
      String _plus = ("\n\tfrom the below source: \n" + _printSingleMethod);
      sourceInfo = (_sourceInfo + _plus);
    } else {
      MethodSet sourceMethodSet = ((MethodSet) source);
      List<Method> methods = sourceMethodSet.getMethods();
      String _sourceInfo_1 = sourceInfo;
      sourceInfo = (_sourceInfo_1 + "\n\tfrom any of the below sources: \n");
      int _size = methods.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        int _size_1 = methods.size();
        int _minus = (_size_1 - 1);
        boolean _equals = ((i).intValue() == _minus);
        if (_equals) {
          String _sourceInfo_2 = sourceInfo;
          String _printSingleMethod_1 = this.printSingleMethod(methods.get((i).intValue()));
          sourceInfo = (_sourceInfo_2 + _printSingleMethod_1);
        } else {
          String _sourceInfo_3 = sourceInfo;
          String _printSingleMethod_2 = this.printSingleMethod(methods.get((i).intValue()));
          sourceInfo = (_sourceInfo_3 + _printSingleMethod_2);
        }
      }
    }
    return sourceInfo;
  }
  
  public String sinkInfo(final FlowParticipant sink) {
    String sinkInfo = "";
    if ((sink instanceof Method)) {
      String _sinkInfo = sinkInfo;
      String _printSingleMethod = this.printSingleMethod(((Method) sink));
      String _plus = ("\n\tand finally reaches the sink: \n" + _printSingleMethod);
      sinkInfo = (_sinkInfo + _plus);
    } else {
      MethodSet sourceMethodSet = ((MethodSet) sink);
      List<Method> methods = sourceMethodSet.getMethods();
      String _sinkInfo_1 = sinkInfo;
      sinkInfo = (_sinkInfo_1 + "\n\tand finally reaches any of the sink: \n");
      int _size = methods.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        int _size_1 = methods.size();
        int _minus = (_size_1 - 1);
        boolean _equals = ((i).intValue() == _minus);
        if (_equals) {
          String _sinkInfo_2 = sinkInfo;
          String _printSingleMethod_1 = this.printSingleMethod(methods.get((i).intValue()));
          sinkInfo = (_sinkInfo_2 + _printSingleMethod_1);
        } else {
          String _sinkInfo_3 = sinkInfo;
          String _printSingleMethod_2 = this.printSingleMethod(methods.get((i).intValue()));
          sinkInfo = (_sinkInfo_3 + _printSingleMethod_2);
        }
      }
    }
    return sinkInfo;
  }
  
  public String sanitizerInfo(final TaintFlow taintFlow) {
    List<FlowParticipant> sanitizer = taintFlow.getNotThrough();
    String sanitizerInfo = "";
    int count = 0;
    String tempResult = "";
    int _size = sanitizer.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        FlowParticipant _get = sanitizer.get((i).intValue());
        if ((_get instanceof Method)) {
          String _sanitizerInfo = sanitizerInfo;
          sanitizerInfo = (_sanitizerInfo + "\n\n\tthat does not go through the below sanitizer: \n");
          String _sanitizerInfo_1 = sanitizerInfo;
          FlowParticipant _get_1 = sanitizer.get((i).intValue());
          String _printSingleMethod = this.printSingleMethod(((Method) _get_1));
          sanitizerInfo = (_sanitizerInfo_1 + _printSingleMethod);
        } else {
          FlowParticipant _get_2 = sanitizer.get((i).intValue());
          if ((_get_2 instanceof MethodSet)) {
            FlowParticipant _get_3 = sanitizer.get((i).intValue());
            List<Method> methods = ((MethodSet) _get_3).getMethods();
            int _size_1 = methods.size();
            ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
            for (final Integer j : _doubleDotLessThan_1) {
              {
                count++;
                String _tempResult = tempResult;
                String _printSingleMethod_1 = this.printSingleMethod(methods.get((j).intValue()));
                tempResult = (_tempResult + _printSingleMethod_1);
              }
            }
            if ((count == 0)) {
              String _sanitizerInfo_2 = sanitizerInfo;
              sanitizerInfo = (_sanitizerInfo_2 + "");
            } else {
              if ((count == 1)) {
                String _sanitizerInfo_3 = sanitizerInfo;
                sanitizerInfo = (_sanitizerInfo_3 + "\n\n\tthat does not go through the below sanitizer: \n");
              } else {
                String _sanitizerInfo_4 = sanitizerInfo;
                sanitizerInfo = (_sanitizerInfo_4 + "\n\n\tthat does not go through any of the below sanitizers: \n");
              }
            }
          }
        }
        count = 0;
        String _sanitizerInfo_5 = sanitizerInfo;
        sanitizerInfo = (_sanitizerInfo_5 + tempResult);
        tempResult = "";
      }
    }
    return sanitizerInfo;
  }
  
  public String deSanitizerInfo(final TaintFlow taintFlow) {
    List<FlowParticipant> deSanitizer = taintFlow.getThrough();
    String sanitizerInfo = "";
    int count = 0;
    String tempResult = "";
    int _size = deSanitizer.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        FlowParticipant _get = deSanitizer.get((i).intValue());
        if ((_get instanceof Method)) {
          String _sanitizerInfo = sanitizerInfo;
          sanitizerInfo = (_sanitizerInfo + "\n\n\tand goes through the below de-sanitizer/required-propogator: \n");
          String _sanitizerInfo_1 = sanitizerInfo;
          FlowParticipant _get_1 = deSanitizer.get((i).intValue());
          String _printSingleMethod = this.printSingleMethod(((Method) _get_1));
          sanitizerInfo = (_sanitizerInfo_1 + _printSingleMethod);
        } else {
          FlowParticipant _get_2 = deSanitizer.get((i).intValue());
          if ((_get_2 instanceof MethodSet)) {
            FlowParticipant _get_3 = deSanitizer.get((i).intValue());
            List<Method> methods = ((MethodSet) _get_3).getMethods();
            int _size_1 = methods.size();
            ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
            for (final Integer j : _doubleDotLessThan_1) {
              {
                count++;
                String _tempResult = tempResult;
                String _printSingleMethod_1 = this.printSingleMethod(methods.get((j).intValue()));
                tempResult = (_tempResult + _printSingleMethod_1);
              }
            }
            if ((count == 0)) {
              String _sanitizerInfo_2 = sanitizerInfo;
              sanitizerInfo = (_sanitizerInfo_2 + "");
            } else {
              if ((count == 1)) {
                String _sanitizerInfo_3 = sanitizerInfo;
                sanitizerInfo = (_sanitizerInfo_3 + "\n\n\ttand goes through the below de-sanitizer/propogator: \n");
              } else {
                String _sanitizerInfo_4 = sanitizerInfo;
                sanitizerInfo = (_sanitizerInfo_4 + "\n\n\tand goes through any of the below de-sanitizers/propogators: \n");
              }
            }
          }
        }
        count = 0;
        String _sanitizerInfo_5 = sanitizerInfo;
        sanitizerInfo = (_sanitizerInfo_5 + tempResult);
        tempResult = "";
      }
    }
    return (sanitizerInfo + "\n");
  }
  
  public String multipleTaintFlows(final List<TaintFlow> taintFlow) {
    String multipleTaintFlowInfo = "";
    int _size = taintFlow.size();
    String _plus = ("Below " + Integer.valueOf(_size));
    String _plus_1 = (_plus + 
      " TaintFlow should be present in the program to successfully report the error message.\n\n");
    multipleTaintFlowInfo = _plus_1;
    int _size_1 = taintFlow.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_1, true);
    for (final Integer i : _doubleDotLessThan) {
      String _multipleTaintFlowInfo = multipleTaintFlowInfo;
      String _plus_2 = (Integer.valueOf(((i).intValue() + 1)) + ". TaintFlow:\n");
      String _singleTaintFlows = this.singleTaintFlows(taintFlow.get((i).intValue()));
      String _plus_3 = (_plus_2 + _singleTaintFlows);
      String _plus_4 = (_plus_3 + "\n\n");
      multipleTaintFlowInfo = (_multipleTaintFlowInfo + _plus_4);
    }
    return multipleTaintFlowInfo;
  }
  
  public String singleTaintFlows(final TaintFlow taintFlow) {
    FlowParticipant source = taintFlow.getFrom();
    FlowParticipant sink = taintFlow.getTo();
    List<FlowParticipant> through = taintFlow.getThrough();
    List<FlowParticipant> notThrough = taintFlow.getNotThrough();
    String _sourceInfo = this.sourceInfo(source);
    String taintFlowInEnglish = ("A Data flow: " + _sourceInfo);
    int _size = notThrough.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      String _taintFlowInEnglish = taintFlowInEnglish;
      String _sanitizerInfo = this.sanitizerInfo(taintFlow);
      taintFlowInEnglish = (_taintFlowInEnglish + _sanitizerInfo);
    }
    int _size_1 = through.size();
    boolean _greaterThan_1 = (_size_1 > 0);
    if (_greaterThan_1) {
      String _taintFlowInEnglish_1 = taintFlowInEnglish;
      String _deSanitizerInfo = this.deSanitizerInfo(taintFlow);
      taintFlowInEnglish = (_taintFlowInEnglish_1 + _deSanitizerInfo);
    }
    String _taintFlowInEnglish_2 = taintFlowInEnglish;
    String _sinkInfo = this.sinkInfo(sink);
    taintFlowInEnglish = (_taintFlowInEnglish_2 + _sinkInfo);
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
      fluentTQL2E = (_fluentTQL2E_1 + "\n\n\tthen report the below error message at ");
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
        fluentTQL2E = (_fluentTQL2E_4 + "\n\nIf, above all the TaintFlows are present in the given program then report the below error message at ");
      }
    }
    String reportMessage = taintFlowQuery.getReportMessage();
    LOCATION reportLocation = taintFlowQuery.getReportLocation();
    boolean _equals_2 = Objects.equal(reportLocation, LOCATION.SOURCE);
    if (_equals_2) {
      String _fluentTQL2E_5 = fluentTQL2E;
      fluentTQL2E = (_fluentTQL2E_5 + "the source location in the IDE.");
    } else {
      boolean _equals_3 = Objects.equal(reportLocation, LOCATION.SINK);
      if (_equals_3) {
        String _fluentTQL2E_6 = fluentTQL2E;
        fluentTQL2E = (_fluentTQL2E_6 + "the sink location in the IDE.");
      } else {
        boolean _equals_4 = Objects.equal(reportLocation, LOCATION.SOURCEANDSINK);
        if (_equals_4) {
          String _fluentTQL2E_7 = fluentTQL2E;
          fluentTQL2E = (_fluentTQL2E_7 + "both the source and the sink location in the IDE.\n\n\t\t");
        }
      }
    }
    return (fluentTQL2E + reportMessage);
  }
}
