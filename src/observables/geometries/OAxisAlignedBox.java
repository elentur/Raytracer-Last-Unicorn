package observables.geometries;

import geometries.AxisAlignedBox;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OAxisAlignedBox extends AOGeometry {

    public OAxisAlignedBox(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);
    }

    @Override
    public AxisAlignedBox generate() {
        return new AxisAlignedBox(
                material.generate(),
                reciveShadows,
                castShadows,
                visibility,
                flipNormal
        );
    }
}
