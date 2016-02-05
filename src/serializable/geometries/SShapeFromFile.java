package serializable.geometries;

import geometries.ShapeFromFile;
import material.Material;
import observables.geometries.OShapeFromFile;
import serializable.SElement;

import java.io.File;

/**
 * Created by roberto on 05.02.16.
 */
public class SShapeFromFile extends ShapeFromFile implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SShapeFromFile(String path, Material material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(new File(path), material, reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
    }

    @Override
    protected void loadFile() {

    }

    @Override
    public OShapeFromFile generate(){
        OShapeFromFile geo = new OShapeFromFile(
        file.toURI().toString());
        geo.name.set(name);
        geo.material.set(null);
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
