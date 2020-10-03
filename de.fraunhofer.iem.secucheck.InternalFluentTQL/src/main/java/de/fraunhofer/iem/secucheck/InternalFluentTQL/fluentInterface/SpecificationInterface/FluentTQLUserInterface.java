package de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;

import java.util.List;

/**
 * This is the interface between the user and FluentTQL. User must implement this interface in their FluentTQL
 * specification.
 *
 * @author Ranjith Krishnamurthy
 */
public interface FluentTQLUserInterface {
    /**
     * This returns the FluentTQL specification specified in that class.
     *
     * @return FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification();
}
