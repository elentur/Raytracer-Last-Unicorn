package observables.textures;

import controller.AController;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import observables.AOElement;
import observables.materials.AOMaterial;
import texture.Texture;

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
    public StringProperty path  = new SimpleStringProperty();
    public ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.GRAY);
    public abstract Texture generate();

    public AOTexture(){
        this.color.addListener(a->refreshMaterial());
        this.scaleU.addListener(a->refreshMaterial());
        this.scaleV.addListener(a->refreshMaterial());
        this.offsetU.addListener(a->refreshMaterial());
        this.offsetV.addListener(a->refreshMaterial());
        this.rotate.addListener(a->refreshMaterial());
        this.path.addListener(a->refreshMaterial());
    }

    protected void refreshMaterial(){
        AOMaterial m = AController.material.get();
        AController.material.setValue(null);
        AController.material.setValue(m);
    }
}
