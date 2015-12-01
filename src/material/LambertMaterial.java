package material;

import light.Light;
import matVect.Point3;
import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

/**
 * A nearly perfect diffuse Material
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class LambertMaterial extends Material {


    /**
     * Generates a new Lambert material with the given color
     *
     * @param color Represents the Color of the Lambert material
     */
    public LambertMaterial(final Color color) {
        super(color);
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null) throw new IllegalArgumentException("hit must not be null ");
        if (world == null) throw new IllegalArgumentException("world must not be null ");
        Color c = new Color(0, 0, 0);
        for (Light light : world.lights) {
            final Point3 p = hit.ray.at(hit.t);
            if(light.illuminates(p,world)){
                c= c.add(light.color.mul(diffuse).mul(Math.max(0, hit.n.dot(light.directionFrom(p)))));
            }
        }

        return diffuse.mul(world.ambientLight).add(c);
    }

    @Override
    public String toString() {
        return "LambertMaterial{" +
                "material=" + diffuse +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambertMaterial that = (LambertMaterial) o;

        return !(diffuse != null ? !diffuse.equals(that.diffuse) : that.diffuse != null);

    }

    @Override
    public int hashCode() {
        return diffuse != null ? diffuse.hashCode() : 0;
    }
}
