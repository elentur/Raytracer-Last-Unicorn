package observables.geometries;

import geometries.Triangle;
import serializable.geometries.STriangle;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OTriangle
 */
public class OTriangle extends AOGeometry {

    public OTriangle() {
        name.set("Triangle");
    }

    @Override
    public Triangle generate() {
        return new Triangle(
                material.get().generate(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public STriangle serialize() {
        return new STriangle(
                material.get().serialize(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }
}
