package serializable.geometries;

import observables.geometries.OSphere;
import serializable.materials.SMaterial;

/**
 * Created by roberto on 05.02.16.
 */
public class SSphere extends SGeometry {

    public SSphere(final SMaterial material, final boolean reciveShadows, final boolean castShadows,
                   final boolean visibility, final boolean flipNormal, final String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal, name);
    }

    @Override
    public OSphere generate() {
        OSphere geo = new OSphere();
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
