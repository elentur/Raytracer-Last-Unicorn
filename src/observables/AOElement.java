package observables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by roberto on 02/02/16.
 */
public class AOElement{

    public StringProperty name = new SimpleStringProperty();

    public AOElement(String name) {
        this.name.setValue(name);
    }
}
