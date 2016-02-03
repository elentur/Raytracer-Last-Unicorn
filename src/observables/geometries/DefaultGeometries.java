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
                true,
                true,
                true,
                false,
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OPlane(
                        "Plane",
                        DefaultMaterial.getDefaultLambert(),
                        true,
                        true,
                        true,
                        false
                ))
        );

    }
    public static AOGeometry getSphere(){
        return new ONode(
                "Sphere",
                true,
                true,
                true,
                false,
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OSphere(
                "Sphere",
                DefaultMaterial.getDefaultLambert(),
                true,
                true,
                true,
                false))
        );

    }
    public static AOGeometry getAxisAlignedBox(){
        return new ONode(
                "Axis-Aligned-Box",
                true,
                true,
                true,
                false,
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OAxisAlignedBox(
                "Axis-Aligned-Box",
                DefaultMaterial.getDefaultLambert(),
                true,
                true,
                true,
                false))
        );

    }
    public static AOGeometry getTriangle(){
        return new ONode(
                "Triangle",
                true,
                true,
                true,
                false,
                new double[]{0.0,0.0,0.0},
                new double[]{1.0,1.0,1.0},
                new double[]{0.0,0.0,0.0},
                FXCollections.observableArrayList(new OTriangle(
                "Triangle",
                DefaultMaterial.getDefaultLambert(),
                true,
                true,
                true,
                false))
        );

    }
}
