package observables.geometries;

import geometries.Geometry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import observables.AOElement;
import observables.materials.AOMaterial;
import observables.materials.DefaultMaterial;
import serializable.SElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOGeometry extends AOElement{

    public ObjectProperty<AOMaterial> material = new SimpleObjectProperty<>(DefaultMaterial.getDefaultLambert());
    public BooleanProperty reciveShadows = new SimpleBooleanProperty(true);
    public BooleanProperty castShadows  = new SimpleBooleanProperty(true);
    public BooleanProperty visibility  = new SimpleBooleanProperty(true);
    public BooleanProperty flipNormal = new SimpleBooleanProperty(false);

    public abstract Geometry generate();
    public abstract SElement serialize();
}
