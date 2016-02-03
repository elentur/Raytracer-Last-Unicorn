package observables.materials;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import material.ReflectiveMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OReflectiveMaterial extends AOMaterial{
    public ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public ObjectProperty<AOTexture> reflection = new SimpleObjectProperty<>();
    public IntegerProperty exponent = new SimpleIntegerProperty();

    public OReflectiveMaterial(){
        specular.set(new OSingleColorTexture(new double[]{0.5,0.5,0.5}));
        reflection.set(new OSingleColorTexture(new double[]{0.5,0.5,0.5}));
        exponent.set(64);
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
