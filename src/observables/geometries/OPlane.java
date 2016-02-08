package observables.geometries;

import geometries.Plane;
import serializable.geometries.SPlane;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OPlane Object
 */
public class OPlane extends AOGeometry {

    public OPlane() {
        name.set("Plane");
    }

    @Override
    public Plane generate() {
        return new Plane(
                material.get().generate(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public SPlane serialize() {
        return new SPlane(
                material.get().serialize(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }

}
