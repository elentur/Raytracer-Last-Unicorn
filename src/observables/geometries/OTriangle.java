package observables.geometries;

import geometries.Geometry;
import geometries.Triangle;
import matVect.Normal3;
import matVect.Point3;
import observables.materials.AOMaterial;

/**
 * Created by roberto on 02/02/16.
 */
public class OTriangle extends AOGeometry {

    public OTriangle(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);
    }

    @Override
    public Triangle generate() {
        return new Triangle(
            material.generate(),
            reciveShadows,
            castShadows,
            visibility,
            flipNormal
        );
    }
}
