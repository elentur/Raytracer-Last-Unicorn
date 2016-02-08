package observables.geometries;

import javafx.collections.FXCollections;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultGeometries {
    /**
     * @return a default Plane
     */
    public static AOGeometry getPlane() {
        return new ONode(
                "Plane",
                FXCollections.observableArrayList(new OPlane())
        );

    }

    /**
     * @return a default Sphere
     */
    public static AOGeometry getSphere() {
        return new ONode(
                "Sphere",
                FXCollections.observableArrayList(new OSphere())
        );

    }

    /**
     * @return a default AxisAlignedBox
     */
    public static AOGeometry getAxisAlignedBox() {
        return new ONode(
                "Axis-Aligned-Box",
                FXCollections.observableArrayList(new OAxisAlignedBox())
        );

    }

    /**
     * @return a default Triangle
     */
    public static AOGeometry getTriangle() {
        return new ONode(
                "Triangle",
                FXCollections.observableArrayList(new OTriangle())
        );

    }
}
