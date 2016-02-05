package serializable.geometries;

import geometries.AxisAlignedBox;
import observables.geometries.OAxisAlignedBox;
import observables.materials.DefaultMaterial;
import serializable.SElement;
import serializable.materials.SMaterial;

/**
 * Created by roberto on 05.02.16.
 */
public class SAxisAlignedBox extends AxisAlignedBox implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final SMaterial material;

    public SAxisAlignedBox(SMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(DefaultMaterial.getSingleColorMaterial().generate(), reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
        this.material=material;
    }

    @Override
    public OAxisAlignedBox generate() {

        OAxisAlignedBox geo = new OAxisAlignedBox();
        geo.name.set(name);
        geo.material.set(material.generate());
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }
}
