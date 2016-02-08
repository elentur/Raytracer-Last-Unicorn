package serializable.geometries;

import controller.AController;
import javafx.collections.ObservableList;
import observables.geometries.OAxisAlignedBox;
import observables.materials.AOMaterial;
import serializable.materials.SMaterial;

/**
 * Wrapper class to serialize the OAxisAlignedBox object.
 * Created by roberto on 05.02.16.
 */
public class SAxisAlignedBox extends SGeometry {

    /**
     * Instantiates a new SAxisAlignedBox Object.
     *
     * @param material current material of the Geometry.
     * @param reciveShadows if a geometry receives shadows.
     * @param castShadows if a geometry casts shadows.
     * @param visibility if a geometry is visible.
     * @param flipNormal if a geometry has its normal direction changed.
     * @param name of the geometry.
     */
    public SAxisAlignedBox(final SMaterial material, final boolean reciveShadows, final boolean castShadows,
                           final boolean visibility, final boolean flipNormal, final String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal, name);
    }


    @Override
    public OAxisAlignedBox generate() {

        OAxisAlignedBox geo = new OAxisAlignedBox();
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
