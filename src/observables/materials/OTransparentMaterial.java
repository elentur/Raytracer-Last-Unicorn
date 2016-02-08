package observables.materials;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import material.TransparentMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;
import serializable.materials.STransparentMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OTransparentMaterial Object
 */
public class OTransparentMaterial extends AOMaterial {
    /**
     * represents the index of refraction value of this material
     */
    public final DoubleProperty indexOfRefraction = new SimpleDoubleProperty();
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

    public OTransparentMaterial() {
        name.set("Transparent Material");
        texture.set(new OSingleColorTexture(Color.WHITE));
        indexOfRefraction.set(1.0);
        specular.set(new OSingleColorTexture(Color.WHITE));
        reflection.set(new OSingleColorTexture(Color.GRAY));
        exponent.set(64);

        specular.addListener(a -> {
            refreshMaterial();
        });
        reflection.addListener(a -> {
            refreshMaterial();
        });

        indexOfRefraction.addListener(a -> {
            if (indexOfRefraction.get() < 0.0 && indexOfRefraction.get() > 2.0) indexOfRefraction.set(0.0);
            refreshMaterial();
        });

        exponent.addListener(a -> {
            if (exponent.get() < 1) exponent.set(1);
            refreshMaterial();
        });
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
                irradiance.get().generate(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }


    @Override
    public STransparentMaterial serialize() {
        return new STransparentMaterial(
                uniqueID,
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                irradiance.get().serialize(),
                specular.get().serialize(),
                exponent.get(),
                reflection.get().serialize(),
                indexOfRefraction.get(),
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
