package observables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by roberto on 02/02/16.
 * <p>
 * Creates a new AOElement Object
 */
public class AOElement {
    /**
     * represents the name of the AOElement
     */
    public StringProperty name = new SimpleStringProperty();

    public AOElement() {
    }

    public AOElement(String name) {
        this.name.set(name);
    }
}
