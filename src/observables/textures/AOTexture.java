package observables.textures;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import observables.AOElement;
import texture.Texture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOTexture extends AOElement {
    public DoubleProperty scaleU = new SimpleDoubleProperty(1);
    public DoubleProperty scaleV = new SimpleDoubleProperty(1);
    public DoubleProperty offsetU = new SimpleDoubleProperty(0);
    public DoubleProperty offsetV = new SimpleDoubleProperty(0);
    public DoubleProperty rotate = new SimpleDoubleProperty(0);
    public StringProperty path  = new SimpleStringProperty();

    public abstract Texture generate();
}
