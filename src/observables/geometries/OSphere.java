package observables.geometries;

import geometries.Sphere;
import serializable.geometries.SSphere;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 *
 * Creates a new OSphere
 */
public class OSphere extends AOGeometry {

    public OSphere() {
        name.set("Sphere");
    }

    @Override
    public Sphere generate() {
        return new Sphere(
                material.get().generate(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public SSphere serialize() {
        return new SSphere(
                material.get().serialize(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }
}
