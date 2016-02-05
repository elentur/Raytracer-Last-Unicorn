package observables.geometries;

import geometries.Triangle;
import serializable.geometries.STriangle;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OTriangle extends AOGeometry {

    public OTriangle() {
        name.set("Triangle");
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

    @Override
    public STriangle serialize() {
        return new STriangle(
                material.get().serialize(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }
}
