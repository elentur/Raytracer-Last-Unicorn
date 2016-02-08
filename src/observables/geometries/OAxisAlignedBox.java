package observables.geometries;

import geometries.AxisAlignedBox;
import serializable.geometries.SAxisAlignedBox;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OAxisAlignedBox
 */
public class OAxisAlignedBox extends AOGeometry {

    public OAxisAlignedBox() {
        name.set("Axis Aligned Box");
    }

    @Override
    public AxisAlignedBox generate() {
        return new AxisAlignedBox(
                material.get().generate(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public SAxisAlignedBox serialize() {
        return new SAxisAlignedBox(
                material.get().serialize(),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
        );
    }
}
