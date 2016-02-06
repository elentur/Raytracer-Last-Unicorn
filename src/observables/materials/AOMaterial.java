package observables.materials;

import controller.AController;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import material.Material;
import observables.AOElement;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;
import serializable.materials.SMaterial;

import java.util.UUID;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOMaterial extends AOElement {

    public final ObjectProperty<AOTexture> texture = new SimpleObjectProperty<>(new OSingleColorTexture(Color.GRAY));
    public final ObjectProperty<AOTexture> bumpMap = new SimpleObjectProperty<>(new OSingleColorTexture(Color.BLACK));
    public final DoubleProperty bumpScale = new SimpleDoubleProperty(0);
    public final ObjectProperty<AOTexture> irradiance = new SimpleObjectProperty<>(new OSingleColorTexture(Color.WHITE));
    public final BooleanProperty ambientOcclusion = new SimpleBooleanProperty(false);
    public final DoubleProperty ambientSize = new SimpleDoubleProperty(2);
    public final IntegerProperty ambientSubdiv = new SimpleIntegerProperty(16);
    public String uniqueID = UUID.randomUUID().toString();

    AOMaterial() {
        texture.addListener(a -> refreshMaterial());
        bumpMap.addListener(a -> refreshMaterial());
        bumpScale.addListener(a -> refreshMaterial());
        irradiance.addListener(a -> refreshMaterial());

    }

    public abstract Material generate();

    public abstract SMaterial serialize();

    void refreshMaterial() {

        AOMaterial m = AController.material.get();
        AController.material.setValue(null);
        AController.material.setValue(m);
    }
}
