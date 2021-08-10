package secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface;

import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;

import java.util.List;

/**
 * This is the interface between the user and FluentTQL. User must implement this interface in their FluentTQL
 * specification.
 *
 */
public interface FluentTQLUserInterface {
    public List<FluentTQLSpecification> getFluentTQLSpecification();
}
