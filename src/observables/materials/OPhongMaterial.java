package observables.materials;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import material.PhongMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;
import serializable.materials.SPhongMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPhongMaterial extends AOMaterial {

    public final ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public final IntegerProperty exponent = new SimpleIntegerProperty();

    public OPhongMaterial() {
        name.set("Phong Material");
        specular.set(new OSingleColorTexture(Color.WHITE));
        exponent.set(64);
        specular.addListener(a -> refreshMaterial());
        exponent.addListener(a -> refreshMaterial());

        exponent.addListener(a -> {
            if(exponent.get() < 1) exponent.set(1);
            refreshMaterial();
        });
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

    @Override
    public SPhongMaterial serialize() {
        return new SPhongMaterial(
                uniqueID,
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                irradiance.get().serialize(),
                specular.get().serialize(),
                exponent.get(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get(),
                name.get()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AOMaterial that = (AOMaterial) o;
        return uniqueID.equals(that.uniqueID);
    }
}
