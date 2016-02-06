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
 */
public abstract class AOTexture extends AOElement {
    public DoubleProperty scaleU = new SimpleDoubleProperty(1);
    public DoubleProperty scaleV = new SimpleDoubleProperty(1);
    public DoubleProperty offsetU = new SimpleDoubleProperty(0);
    public DoubleProperty offsetV = new SimpleDoubleProperty(0);
    public DoubleProperty rotate = new SimpleDoubleProperty(0);
    public final StringProperty path = new SimpleStringProperty();
    public final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.GRAY);
    public final ObjectProperty<Image> img = new SimpleObjectProperty<>();

    public abstract Texture generate();

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

    private void refreshMaterial() {
        AOMaterial m = AController.material.get();
        AController.material.setValue(null);
        AController.material.setValue(m);
    }

}
