package serializable.geometries;

import geometries.Triangle;
import matVect.Normal3;
import matVect.Point3;
import material.Material;
import observables.geometries.OTriangle;
import serializable.SElement;
import texture.TexCoord2;

/**
 * Created by roberto on 05.02.16.
 */
public class STriangle extends Triangle implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public STriangle(Material material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
    }

    @Override
    public OTriangle generate(){
        OTriangle geo = new OTriangle();
        geo.name.set(name);
        geo.material.set(null);
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
