package serializable.geometries;

import controller.AController;
import javafx.collections.ObservableList;
import observables.geometries.OSphere;
import observables.materials.AOMaterial;
import serializable.materials.SMaterial;

/**
 * Wrapper class to serialize the OSphere object.
 * Created by roberto on 05.02.16.
 */
public class SSphere extends SGeometry {

    /**
     * Instantiates a new SSphere Object.
     *
     * @param material current material of the Geometry.
     * @param reciveShadows if a geometry receives shadows.
     * @param castShadows if a geometry casts shadows.
     * @param visibility if a geometry is visible.
     * @param flipNormal if a geometry has its normal direction changed.
     * @param name of the geometry.
     */
    public SSphere(final SMaterial material, final boolean reciveShadows, final boolean castShadows,
                   final boolean visibility, final boolean flipNormal, final String name) {
        super(material, reciveShadows, castShadows, visibility, flipNormal, name);
    }

    @Override
    public OSphere generate() {
        OSphere geo = new OSphere();
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
