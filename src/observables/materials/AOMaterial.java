package observables.materials;

import controller.AController;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import material.Material;
import observables.AOElement;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOMaterial extends AOElement {

    public ObjectProperty<AOTexture> texture = new SimpleObjectProperty<>(new OSingleColorTexture(Color.GRAY));
    public ObjectProperty<AOTexture> bumpMap = new SimpleObjectProperty<>(new OSingleColorTexture(Color.BLACK));
    public DoubleProperty bumpScale = new SimpleDoubleProperty(0);
    public ObjectProperty<AOTexture> irradiance = new SimpleObjectProperty<>(new OSingleColorTexture(Color.WHITE));
    public BooleanProperty ambientOcclusion = new SimpleBooleanProperty(false);
    public DoubleProperty ambientSize = new SimpleDoubleProperty(2);
    public IntegerProperty ambientSubdiv = new SimpleIntegerProperty(16);

    public AOMaterial(){
        texture.addListener(a->refreshMaterial());
        bumpMap.addListener(a->refreshMaterial());
        bumpScale.addListener(a->refreshMaterial());
        irradiance.addListener(a->refreshMaterial());

    }
    public abstract Material generate();

    protected void refreshMaterial(){
        
        AOMaterial m = AController.material.get();
        AController.material.setValue(null);
        AController.material.setValue(m);
    }
}
