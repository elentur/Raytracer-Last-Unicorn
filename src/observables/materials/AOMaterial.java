package observables.materials;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import material.Material;
import observables.AOElement;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOMaterial extends AOElement {

    public ObjectProperty<AOTexture> texture = new SimpleObjectProperty<>();
    public ObjectProperty<AOTexture> bumpMap = new SimpleObjectProperty<>();
    public DoubleProperty bumpScale = new SimpleDoubleProperty();
    public ObjectProperty<AOTexture> irradiance = new SimpleObjectProperty<>();

    public AOMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance) {
        super(name);
        this.texture.setValue(texture);
        this.bumpMap.setValue(bumpMap);
        this.bumpScale.setValue(bumpScale);
        this.irradiance.setValue(irradiance);
    }

    public abstract Material generate();
}
