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
    public DoubleProperty scaleU = new SimpleDoubleProperty();
    public DoubleProperty scaleV = new SimpleDoubleProperty();
    public DoubleProperty offsetU = new SimpleDoubleProperty();
    public DoubleProperty offsetV = new SimpleDoubleProperty();
    public DoubleProperty rotate = new SimpleDoubleProperty();
    public StringProperty path  = new SimpleStringProperty();

    public AOTexture(String name, double scaleU, double scaleV, double offsetU, double offsetV, double rotate, String path) {
        super(name);
        this.scaleU.setValue(scaleU);
        this.scaleV.setValue(scaleV);
        this.offsetU.setValue(offsetU);
        this.offsetV.setValue(offsetV);
        this.rotate.setValue(rotate);
        this.path.setValue(path);
    }

    public abstract Texture generate();
}
