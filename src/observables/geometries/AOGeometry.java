package observables.geometries;

import geometries.Geometry;
import observables.AOElement;
import observables.materials.AOMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOGeometry extends AOElement{

    public AOMaterial material;
    public boolean reciveShadows;
    public boolean castShadows;
    public boolean visibility;
    public boolean flipNormal;

    public AOGeometry(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal) {
        super(name);
        this.material = material;
        this.reciveShadows = reciveShadows;
        this.castShadows = castShadows;
        this.visibility = visibility;
        this.flipNormal = flipNormal;
    }

    public abstract Geometry generate();
}
