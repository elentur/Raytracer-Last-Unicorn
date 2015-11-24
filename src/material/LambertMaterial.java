package material;

import light.Light;
import matVect.Point3;
import utils.Color;
import utils.Hit;
import utils.World;

/**
 * A nearly perfect diffuse Material
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class LambertMaterial extends Material {
    /**
     *  Represents the Color of the Lambert material
     */
    public final Color color;

    /**
     * Generates a new Lambert material with the given color
     * @param color Represents the Color of the Lambert material
     */
    public LambertMaterial(Color color) {
        this.color = color;
    }

    @Override
    public Color colorFor(Hit hit, World world) {
        Color c = new Color(0,0,0);
        for(Light light: world.lights){
            final Point3 p = hit.ray.at(hit.t);
            if(light.illuminates(p)){
                c= c.add(light.color.mul(color).mul(Math.max(0, hit.n.dot(light.directionFrom(p)))));
            }
        }

        return color.mul(world.ambientLight).add(c);
    }

    @Override
    public String toString() {
        return "LambertMaterial{" +
                "material=" + color +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambertMaterial that = (LambertMaterial) o;

        return !(color != null ? !color.equals(that.color) : that.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
