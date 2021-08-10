package secucheck.InternalFluentTQL.fluentInterface.InputOutput;

/**
 * Interface for Parameter
 *
 */
public interface Parameter extends Output, Input {
    /**
     * Returns the Parameter id
     *
     * @return parameter id
     */
    int getParameterId();
}
