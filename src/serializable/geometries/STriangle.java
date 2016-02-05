package serializable.geometries;

import geometries.Triangle;
import observables.geometries.OTriangle;
import observables.materials.DefaultMaterial;
import serializable.SElement;
import serializable.materials.SMaterial;

/**
 * Created by roberto on 05.02.16.
 */
public class STriangle extends Triangle implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final SMaterial material;

    public STriangle(SMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(DefaultMaterial.getSingleColorMaterial().generate(), reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
        this.material=material;
    }

    @Override
    public OTriangle generate(){
        OTriangle geo = new OTriangle();
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
