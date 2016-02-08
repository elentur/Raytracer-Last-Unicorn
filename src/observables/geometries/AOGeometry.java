package observables.geometries;

import geometries.Geometry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import observables.AOElement;
import observables.materials.AOMaterial;
import observables.materials.DefaultMaterial;
import serializable.geometries.SGeometry;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 *
 * Creates a new AOGeometry Object
 */
public abstract class AOGeometry extends AOElement {

    /**
     * represents the material of the geometry
     */
    public final ObjectProperty<AOMaterial> material = new SimpleObjectProperty<>(DefaultMaterial.getDefaultLambert());
    /**
     * represents whether or not a node receives shadows
     */
    public final BooleanProperty receiveShadows = new SimpleBooleanProperty(true);
    /**
     * represents whether or not a node casts shadows
     */
    public final BooleanProperty castShadows = new SimpleBooleanProperty(true);
    /**
     * represents whether or not a node is visible
     */
    public final BooleanProperty visibility = new SimpleBooleanProperty(true);
    /**
     * represents whether or not a node has flipped normals
     */
    public final BooleanProperty flipNormal = new SimpleBooleanProperty(false);

    /**
     * Creates a new Geometry for rendering
     * @return a Geometry-Object
     */
    public abstract Geometry generate();
    /**
     * Creates a new SGeometry for saving
     * @return a SGeometry-Object
     */
    public abstract SGeometry serialize();
}
