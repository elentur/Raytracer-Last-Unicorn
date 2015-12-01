package material;

import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

/**
 * Generates a material with no attributes representing only a color
 * Created by Andreas Kiauka on 21.11.2015.
 *
 * @author Andreas Kiauka
 */
public class SingleColorMaterial extends Material {


    /**
     * Generates a SinglColor Object with the given Color
     *
     * @param color
     */
    public SingleColorMaterial(final Color color) {
        super(color);
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null) throw new IllegalArgumentException("hit must not be null ");
        if (world == null) throw new IllegalArgumentException("world must not be null ");
        return diffuse;
    }

    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "material=" + diffuse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return !(diffuse != null ? !diffuse.equals(that.diffuse) : that.diffuse != null);

    }

    @Override
    public int hashCode() {
        return diffuse != null ? diffuse.hashCode() : 0;
    }
}
