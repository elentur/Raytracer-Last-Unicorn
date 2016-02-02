package observables.geometries;

import geometries.Geometry;
import geometries.Sphere;
import observables.materials.AOMaterial;

/**
 * Created by roberto on 02/02/16.
 */
public class OSphere extends AOGeometry {

    public OSphere(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);
    }

    @Override
    public Sphere generate() {
        return new Sphere(
                material.generate(),
                reciveShadows,
                castShadows,
                visibility,
                flipNormal
        );
    }
}
