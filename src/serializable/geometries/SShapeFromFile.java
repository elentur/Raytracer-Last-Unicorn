package serializable.geometries;

import geometries.ShapeFromFile;
import observables.geometries.OShapeFromFile;
import observables.materials.DefaultMaterial;
import serializable.SElement;
import serializable.materials.SMaterial;

import java.io.File;

/**
 * Created by roberto on 05.02.16.
 */
public class SShapeFromFile extends ShapeFromFile implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final SMaterial material;

    public SShapeFromFile(String path, SMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(new File(path), DefaultMaterial.getSingleColorMaterial().generate(), reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
        this.material=material;
    }

    @Override
    protected void loadFile() {

    }

    @Override
    public OShapeFromFile generate(){
        OShapeFromFile geo = new OShapeFromFile(
        file.getPath());
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
