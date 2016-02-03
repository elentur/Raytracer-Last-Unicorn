package observables.materials;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import material.PhongMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPhongMaterial extends AOMaterial{

    public ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public IntegerProperty exponent = new SimpleIntegerProperty();

    public OPhongMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, AOTexture specular, int exponent) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.specular.setValue(specular);
        this.exponent.setValue(exponent);
    }

    @Override
    public PhongMaterial generate() {
        return new PhongMaterial(
                texture.get().generate(),
                specular.get().generate(),
                exponent.get(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate()
        );
    }
}
