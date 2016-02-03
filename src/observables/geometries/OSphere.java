package observables.geometries;

import geometries.Sphere;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSphere extends AOGeometry {

    public OSphere(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);
    }

    @Override
    public Sphere generate() {
        return new Sphere(
                material.get().generate(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }
}
