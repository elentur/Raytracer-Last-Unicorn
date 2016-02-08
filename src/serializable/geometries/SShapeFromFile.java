package serializable.geometries;

import controller.AController;
import javafx.collections.ObservableList;
import observables.geometries.OShapeFromFile;
import observables.materials.AOMaterial;
import serializable.materials.SMaterial;

/**
 * Wrapper class to serialize the OShapeFromFile object.
 * Created by roberto on 05.02.16.
 */
public class SShapeFromFile extends SGeometry {

    /**
     * represents the path of the image as string
     */
    private final String path;

    /**
     * Instantiates a new STriangle Object.
     *
     * @param path of the image as string
     * @param material current material of the Geometry.
     * @param reciveShadows if a geometry receives shadows.
     * @param castShadows if a geometry casts shadows.
     * @param visibility if a geometry is visible.
     * @param flipNormal if a geometry has its normal direction changed.
     * @param name of the geometry.
     */
    public SShapeFromFile(final String path, final SMaterial material, final boolean reciveShadows, final boolean castShadows,
                          final boolean visibility, final boolean flipNormal, final String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal, name);
        this.path = path;
    }

    @Override
    public OShapeFromFile generate() {
        OShapeFromFile geo = new OShapeFromFile(
                path);
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.receiveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);
        ObservableList<AOMaterial> list = AController.materialList;
        if (list.contains(geo.material.get())) {
            geo.material.set(list.get(list.indexOf(geo.material.get())));
        }
        return geo;
    }
}
