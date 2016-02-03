package observables.materials;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import material.ReflectiveMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OReflectiveMaterial extends AOMaterial{
    public ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public ObjectProperty<AOTexture> reflection = new SimpleObjectProperty<>();
    public IntegerProperty exponent = new SimpleIntegerProperty();

    public OReflectiveMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, AOTexture specular, AOTexture reflection, int exponent) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.specular.setValue(specular);
        this.reflection.setValue(reflection);
        this.exponent.setValue(exponent);
    }

    @Override
    public ReflectiveMaterial generate() {
        return new ReflectiveMaterial(
                texture.get().generate(),
                specular.get().generate(),
                reflection.get().generate(),
                exponent.get(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate()
        );
    }
}
