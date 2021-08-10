package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.List;

@FluentTQLSpecificationClass
public class ReturnsNullSpecificationCaseSpec implements FluentTQLUserInterface {
    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        return null;   //Fixme: This is the problem/error
    }
}
