package observables.geometries;

import geometries.ShapeFromFile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import observables.materials.AOMaterial;

import java.io.File;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OShapeFromFile extends AOGeometry {

    public StringProperty path = new SimpleStringProperty();

    public OShapeFromFile(String path) {
        name.set("Shape From File");
        this.path.setValue(path);
    }

    @Override
    public ShapeFromFile generate() {
        return new ShapeFromFile(
                new File(path.get()),
                material.get().generate(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }
}
