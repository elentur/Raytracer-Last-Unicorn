package serializable.geometries;

import geometries.Plane;
import material.Material;
import observables.geometries.OPlane;
import serializable.SElement;

/**
 * Created by roberto on 05.02.16.
 */
public class SPlane extends Plane implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SPlane(Material material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
    }

    @Override
    public OPlane generate(){

        OPlane geo = new OPlane();
        geo.name.set(name);
        geo.material.set(null);
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
