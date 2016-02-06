package serializable.geometries;

import observables.geometries.AOGeometry;
import serializable.SElement;
import serializable.materials.SMaterial;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 06.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SGeometry implements SElement, Serializable {

    private static final long serialVersionUID = 1L;
    protected final SMaterial material;
    protected final boolean reciveShadows;
    protected final boolean castShadows;
    protected final boolean visibility;
    protected final boolean flipNormal;
    protected final String name;

    public SGeometry(final SMaterial material, final boolean reciveShadows, final boolean castShadows,
                     final boolean visibility, final boolean flipNormal, final String name) {
        this.material = material;
        this.reciveShadows = reciveShadows;
        this.castShadows = castShadows;
        this.visibility = visibility;
        this.flipNormal = flipNormal;
        this.name = name;
    }
    @Override
    public abstract AOGeometry generate();
}