package serializable.geometries;

import observables.geometries.OShapeFromFile;
import serializable.materials.SMaterial;

/**
 * Created by roberto on 05.02.16.
 *
 */
public class SShapeFromFile extends SGeometry{

    private final String path;
    public SShapeFromFile( final String path, final SMaterial material, final boolean reciveShadows, final boolean castShadows,
                          final boolean visibility, final boolean flipNormal, final String name) {
        super(material,reciveShadows,castShadows,visibility,flipNormal,name);
        this.path=path;
    }

    @Override
    public OShapeFromFile generate(){
        OShapeFromFile geo = new OShapeFromFile(
        path);
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
