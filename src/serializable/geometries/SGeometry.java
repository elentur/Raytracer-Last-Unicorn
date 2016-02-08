package serializable.geometries;

import observables.geometries.AOGeometry;
import serializable.SElement;
import serializable.materials.SMaterial;

import java.io.Serializable;

/**
 * Abstract wrapper class for all serializable geometry objects.
 * Created by Marcus Baetz on 06.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SGeometry implements SElement, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The current material of the Geometry child class.
     */
    final SMaterial material;

    /**
     * represents if a geometry receives shadows
     */
    final boolean reciveShadows;

    /**
     * represents if a geometry casts shadows
     */
    final boolean castShadows;

    /**
     * represents if a geometry is visible
     */
    final boolean visibility;

    /**
     * represents if a geometry has its normal direction changed
     */
    final boolean flipNormal;

    /**
     * The name of the geometry
     */
    final String name;

    /**
     *
     * @param material current material of the Geometry.
     * @param reciveShadows if a geometry receives shadows.
     * @param castShadows if a geometry casts shadows.
     * @param visibility if a geometry is visible.
     * @param flipNormal if a geometry has its normal direction changed.
     * @param name of the geometry.
     */
    SGeometry(final SMaterial material, final boolean reciveShadows, final boolean castShadows,
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
