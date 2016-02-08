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
 *
 * Creates a new AOMaterial Object
 */
public abstract class AOMaterial extends AOElement {

    /**
     * represents the diffuse Texture of this material
     */
    public final ObjectProperty<AOTexture> texture = new SimpleObjectProperty<>(new OSingleColorTexture(Color.GRAY));
    /**
     * represents the normal Texture of this material
     */
    public final ObjectProperty<AOTexture> bumpMap = new SimpleObjectProperty<>(new OSingleColorTexture(Color.BLACK));
    /**
     * represents the normal scale value of this material
     */
    public final DoubleProperty bumpScale = new SimpleDoubleProperty(0);
    /**
     * represents the irradience Texture of this material(not implemented)
     */
    public final ObjectProperty<AOTexture> irradiance = new SimpleObjectProperty<>(new OSingleColorTexture(Color.WHITE));
    /**
     * represents whether or not this material light allows ambient Occlusion
     */
    public final BooleanProperty ambientOcclusion = new SimpleBooleanProperty(false);
    /**
     * represents the size of ambient occlusion
     */
    public final DoubleProperty ambientSize = new SimpleDoubleProperty(2);
    /**
     * represents the number of rays to calculate the ambient occlusion
     */
    public final IntegerProperty ambientSubdiv = new SimpleIntegerProperty(16);
    public String uniqueID = UUID.randomUUID().toString();

    AOMaterial() {
        texture.addListener(a -> refreshMaterial());
        bumpMap.addListener(a -> refreshMaterial());
        bumpScale.addListener(a -> refreshMaterial());
        irradiance.addListener(a -> refreshMaterial());

        ambientSize.addListener(a->{
            if(ambientSize.get() < 0) ambientSize.set(0);
            refreshMaterial();
        });

        ambientSubdiv.addListener(a->{
            if(ambientSubdiv.get() < 1) ambientSubdiv.set(1);
            refreshMaterial();
        });

    }

     /**
     * Creates a new Material for rendering
     * @return a Material-Object
     */
    public abstract Material generate();
    /**
     * Creates a new SMaterial for saving
     * @return a SMaterial-Object
     */
    public abstract SMaterial serialize();

    /**
     * refreshes the AController.material to force the MaterialView to rerender
     */

    void refreshMaterial() {

        AOMaterial m = AController.material.get();
        AController.material.setValue(null);
        AController.material.setValue(m);
    }
}
