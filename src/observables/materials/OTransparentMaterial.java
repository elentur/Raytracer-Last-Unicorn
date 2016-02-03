package observables.materials;

import javafx.beans.property.*;
import material.TransparentMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OTransparentMaterial extends AOMaterial {
    public DoubleProperty indexOfRefraction = new SimpleDoubleProperty();
    public ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public ObjectProperty<AOTexture> reflection = new SimpleObjectProperty<>();
    public IntegerProperty exponent = new SimpleIntegerProperty();

    public OTransparentMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, double indexOfRefraction, AOTexture specular, AOTexture reflection, int exponent) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.indexOfRefraction.setValue(indexOfRefraction);
        this.specular.setValue(specular);
        this.reflection.setValue(reflection);
        this.exponent.setValue(exponent);
    }

    @Override
    public TransparentMaterial generate() {
        return new TransparentMaterial(
                texture.get().generate(),
                specular.get().generate(),
                reflection.get().generate(),
                exponent.get(),
                indexOfRefraction.get(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate()
        );
    }
}
