package observables.geometries;

import javafx.collections.FXCollections;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultGeometries {
    public static AOGeometry getPlane() {
        return new ONode(
                "Plane",
                FXCollections.observableArrayList(new OPlane())
        );

    }

    public static AOGeometry getSphere() {
        return new ONode(
                "Sphere",
                FXCollections.observableArrayList(new OSphere())
        );

    }

    public static AOGeometry getAxisAlignedBox() {
        return new ONode(
                "Axis-Aligned-Box",
                FXCollections.observableArrayList(new OAxisAlignedBox())
        );

    }

    public static AOGeometry getTriangle() {
        return new ONode(
                "Triangle",
                FXCollections.observableArrayList(new OTriangle())
        );

    }
}
