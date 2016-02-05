package serializable.geometries;

import geometries.Plane;
import observables.geometries.OPlane;
import observables.materials.DefaultMaterial;
import serializable.SElement;
import serializable.materials.SMaterial;

/**
 * Created by roberto on 05.02.16.
 */
public class SPlane extends Plane implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final SMaterial material;

    public SPlane(SMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(DefaultMaterial.getSingleColorMaterial().generate(), reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
        this.material=material;
    }

    @Override
    public OPlane generate(){

        OPlane geo = new OPlane();
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
