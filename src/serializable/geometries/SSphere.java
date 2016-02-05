package serializable.geometries;

import geometries.Sphere;
import material.Material;
import observables.geometries.OSphere;
import serializable.SElement;

/**
 * Created by roberto on 05.02.16.
 */
public class SSphere extends Sphere implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SSphere(Material material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
    }

    @Override
    public OSphere generate(){
        OSphere geo = new OSphere();
        geo.name.set(name);
        geo.material.set(null);
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}