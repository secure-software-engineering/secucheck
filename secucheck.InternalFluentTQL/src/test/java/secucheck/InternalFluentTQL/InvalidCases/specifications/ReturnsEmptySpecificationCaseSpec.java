package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class ReturnsEmptySpecificationCaseSpec implements FluentTQLUserInterface {
    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        return new ArrayList<>();  //Fixme: This is the problem/error
    }
}
