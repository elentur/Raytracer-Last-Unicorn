package observables.geometries;

import geometries.Sphere;
import serializable.geometries.SSphere;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSphere extends AOGeometry {

    public OSphere() {
       name.set("Sphere");
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

    @Override
    public SSphere serialize() {
        return new SSphere(
                material.get().serialize(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }
}
