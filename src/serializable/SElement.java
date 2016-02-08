package serializable;

import observables.AOElement;

/**
 * Interface for all serializable Wrapper classes.
 * Created by Robert Dziuba on 05.02.16.
 */
public interface SElement {
    /**
     * Method to transform a AOElement Object from a SElement object
     * @return AOElement.
     */
    AOElement generate();
}
