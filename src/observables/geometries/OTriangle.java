package observables.geometries;

import geometries.Triangle;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OTriangle extends AOGeometry {

    public OTriangle(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);
    }

    @Override
    public Triangle generate() {
        return new Triangle(
                material.get().generate(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }
}
