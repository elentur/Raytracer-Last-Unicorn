package observables.materials;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import material.PhongMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPhongMaterial extends AOMaterial{

    public ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public IntegerProperty exponent = new SimpleIntegerProperty();

    public OPhongMaterial() {
        name.set("Phong Material");
        specular.set(new OSingleColorTexture(Color.WHITE));
        exponent.set(64);
        specular.addListener(a->refreshMaterial());
        exponent.addListener(a->refreshMaterial());
    }

    @Override
    public PhongMaterial generate() {
        return new PhongMaterial(
                texture.get().generate(),
                specular.get().generate(),
                exponent.get(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }
}
