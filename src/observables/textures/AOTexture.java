package observables.textures;

import controller.AController;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import observables.AOElement;
import observables.materials.AOMaterial;
import serializable.textures.STexture;
import texture.Texture;

import java.io.File;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new AOTexture Object
 */
public abstract class AOTexture extends AOElement {
    /**
     * represents the scale value of the u axis
     */
    public DoubleProperty scaleU = new SimpleDoubleProperty(1);
    /**
     * represents the scale value of the v axis
     */
    public DoubleProperty scaleV = new SimpleDoubleProperty(1);
    /**
     * represents the offset value of the u axis
     */
    public DoubleProperty offsetU = new SimpleDoubleProperty(0);
    /**
     * represents the offset value of the v axis
     */
    public DoubleProperty offsetV = new SimpleDoubleProperty(0);
    /**
     * represents the rotation value
     */
    public DoubleProperty rotate = new SimpleDoubleProperty(0);
    /**
     * represents the path of the texture
     */
    public final StringProperty path = new SimpleStringProperty();
    /**
     * represents the color of the texture
     */
    public final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.GRAY);
    /**
     * represents the image of the texture
     */
    public final ObjectProperty<Image> img = new SimpleObjectProperty<>();

    /**
     * Creates a new Texture for rendering
     *
     * @return a Texture-Object
     */
    public abstract Texture generate();

    /**
     * Creates a new STexture for saving
     *
     * @return a STexture-Object
     */
    public abstract STexture serialize();

    AOTexture() {
        this.color.addListener(a -> refreshMaterial());
        this.scaleU.addListener(a -> refreshMaterial());
        this.scaleV.addListener(a -> refreshMaterial());
        this.offsetU.addListener(a -> refreshMaterial());
        this.offsetV.addListener(a -> refreshMaterial());
        this.rotate.addListener(a -> refreshMaterial());
        this.path.addListener(a -> {
            if (!path.get().equals("")) img.set(new Image(new File(path.get()).toURI().toString()));
            refreshMaterial();
        });
    }

    /**
     * refreshes the AController.material to force the MaterialView to rerender
     */

    private void refreshMaterial() {
        AOMaterial m = AController.material.get();
        AController.material.setValue(null);
        AController.material.setValue(m);
    }

}
