package observables.geometries;

import geometries.Geometry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import observables.AOElement;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOGeometry extends AOElement{

    public ObjectProperty<AOMaterial> material = new SimpleObjectProperty<>();
    public BooleanProperty reciveShadows = new SimpleBooleanProperty();
    public BooleanProperty castShadows  = new SimpleBooleanProperty();
    public BooleanProperty visibility  = new SimpleBooleanProperty();
    public BooleanProperty flipNormal = new SimpleBooleanProperty();

    public AOGeometry(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name);
        this.material.setValue(material);
        this.reciveShadows.setValue(reciveShadows);
        this.castShadows.setValue(castShadows);
        this.visibility.setValue( visibility);
        this.flipNormal.setValue(flipNormal);
    }

    public abstract Geometry generate();
}
