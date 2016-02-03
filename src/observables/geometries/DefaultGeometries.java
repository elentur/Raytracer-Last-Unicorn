package observables.geometries;

import javafx.collections.FXCollections;
import observables.materials.DefaultMaterial;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultGeometries {
    public static AOGeometry getPlane(){
        return new ONode(
                "Plane",
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OPlane())
        );

    }
    public static AOGeometry getSphere(){
        return new ONode(
                "Sphere",
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OSphere())
        );

    }
    public static AOGeometry getAxisAlignedBox(){
        return new ONode(
                "Axis-Aligned-Box",
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OAxisAlignedBox())
        );

    }
    public static AOGeometry getTriangle(){
        return new ONode(
                "Triangle",
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OTriangle())
        );

    }
}
