package observables.materials;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import material.TransparentMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OTransparentMaterial extends AOMaterial {
    public DoubleProperty indexOfRefraction = new SimpleDoubleProperty();
    public ObjectProperty<AOTexture> specular = new SimpleObjectProperty<>();
    public ObjectProperty<AOTexture> reflection = new SimpleObjectProperty<>();
    public IntegerProperty exponent = new SimpleIntegerProperty();

    public OTransparentMaterial() {
        name.set("Transparent Material");
        texture.set(new OSingleColorTexture(Color.BLACK));
        indexOfRefraction.set(1.0);
        specular.set(new OSingleColorTexture(Color.WHITE));
        reflection.set(new OSingleColorTexture(Color.GRAY));
        exponent.set(64);
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
