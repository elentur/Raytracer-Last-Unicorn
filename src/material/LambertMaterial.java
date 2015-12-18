package material;

import light.Light;
import matVect.Point3;
import texture.Texture;
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
     * @param texture Represents the Color of the Lambert material
     */
    public LambertMaterial(final Texture texture) {
        super(texture);
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {

        if (hit == null) throw new IllegalArgumentException("hit must not be null ");
        if (world == null) throw new IllegalArgumentException("world must not be null ");

        Color c = new Color(0, 0, 0);
        for (Light light : world.lights) {
            final Point3 p = hit.ray.at(hit.t);
            if(light.illuminates(p,world,hit.geo)){
                c = c.add(light.color.mul(texture.getColor(hit.texCoord.u,hit.texCoord.v)).mul(Math.max(0, hit.n.dot(light.directionFrom(p)))));
            }
        }

        return texture.getColor(hit.texCoord.u,hit.texCoord.v).mul(world.ambientLight).add(c);
    }

    @Override
    public String toString() {
        return "LambertMaterial{" +
                "material=" + texture +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambertMaterial that = (LambertMaterial) o;

        return !(texture != null ? !texture.equals(that.texture) : that.texture != null);

    }

    @Override
    public int hashCode() {
        return texture != null ? texture.hashCode() : 0;
    }
}
