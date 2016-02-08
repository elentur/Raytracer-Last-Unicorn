package observables.materials;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import material.ReflectiveMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;
import serializable.materials.SReflectivMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OReflectiveMaterial
 */
public class OReflectiveMaterial extends AOMaterial {
    /**
     * represents the specular Texture of this material
     */
    public final ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    /**
     * represents the reflection Texture of this material
     */
    public final ObjectProperty<AOTexture> reflection = new SimpleObjectProperty<>();
    /**
     * represents the specular intensity of this material
     */
    public final IntegerProperty exponent = new SimpleIntegerProperty();

    public OReflectiveMaterial() {
        name.set("Reflective Material");
        specular.set(new OSingleColorTexture(Color.WHITE));
        reflection.set(new OSingleColorTexture(Color.GRAY));
        exponent.set(64);
        specular.addListener(a -> refreshMaterial());
        reflection.addListener(a -> refreshMaterial());

        exponent.addListener(a -> {
            System.out.println(exponent.get());
            if (exponent.get() < 1) exponent.set(1);
            refreshMaterial();
        });
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
                irradiance.get().generate(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }

    @Override
    public SReflectivMaterial serialize() {
        return new SReflectivMaterial(
                uniqueID,
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                irradiance.get().serialize(),
                specular.get().serialize(),
                exponent.get(),
                reflection.get().serialize(),
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
