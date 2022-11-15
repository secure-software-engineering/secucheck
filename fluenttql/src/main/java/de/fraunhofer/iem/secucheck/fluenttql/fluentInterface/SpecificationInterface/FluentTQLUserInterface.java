package de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;

import java.util.List;

/**
 * This is the interface between the user and FluentTQL. User must implement this interface in their FluentTQL
 * specification.
 *
 * @author Ranjith Krishnamurthy
 */
public interface FluentTQLUserInterface {
    public List<FluentTQLSpecification> getFluentTQLSpecification();
}
