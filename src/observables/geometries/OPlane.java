package observables.geometries;

import geometries.Plane;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPlane extends AOGeometry {

    public OPlane(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);
    }

    @Override
    public Plane generate() {
        return new Plane(
                material.get().generate(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }
}
