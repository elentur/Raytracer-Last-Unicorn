package observables.geometries;

import geometries.ShapeFromFile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import serializable.geometries.SShapeFromFile;

import java.io.File;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 *
 * Creates a new OShapeFromFile Object
 */
public class OShapeFromFile extends AOGeometry {
    /**
     * represents the path of the obj file
     */
    public final StringProperty path = new SimpleStringProperty();

    /**
     *
     * @param path the path of the obj file
     */
    public OShapeFromFile(String path) {
        name.set(new File(path).getName().split("\\.")[0]);
        this.path.set(path);
    }

    @Override
    public ShapeFromFile generate() {
        return new ShapeFromFile(
                new File(path.get()),
                material.get().generate(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public SShapeFromFile serialize() {
        return new SShapeFromFile(
                path.get(),
                material.get().serialize(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }
}
