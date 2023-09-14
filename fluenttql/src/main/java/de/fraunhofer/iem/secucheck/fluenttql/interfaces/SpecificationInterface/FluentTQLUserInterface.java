package de.fraunhofer.iem.secucheck.fluenttql.interfaces.SpecificationInterface;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.FluentTQLSpecification;

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
