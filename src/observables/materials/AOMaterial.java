package observables.materials;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import material.Material;
import observables.AOElement;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOMaterial extends AOElement {

    public ObjectProperty<AOTexture> texture = new SimpleObjectProperty<>(new OSingleColorTexture(Color.GRAY));
    public ObjectProperty<AOTexture> bumpMap = new SimpleObjectProperty<>(new OSingleColorTexture(Color.BLACK));
    public DoubleProperty bumpScale = new SimpleDoubleProperty(500);
    public ObjectProperty<AOTexture> irradiance = new SimpleObjectProperty<>(new OSingleColorTexture(Color.WHITE));

    public abstract Material generate();
}
