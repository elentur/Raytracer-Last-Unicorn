package observables.geometries;

import geometries.AxisAlignedBox;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OAxisAlignedBox extends AOGeometry {

    public OAxisAlignedBox() {
        name.set("Axis Aligned Box");
    }

    @Override
    public AxisAlignedBox generate() {
        return new AxisAlignedBox(
                material.get().generate(),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }
}
