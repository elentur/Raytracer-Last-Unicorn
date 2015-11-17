package material;

import light.Light;
import matVect.Point3;
import utils.Color;
import utils.Hit;
import utils.World;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class LambertMaterial extends Material {

    public final Color color;

    public LambertMaterial(Color color) {
        this.color = color;
    }

    @Override
    public Color colorFor(Hit hit, World world) {

        Color basicColor = new Color(0, 0, 0);

        for (Light light : world.lights) {
            Point3 h = hit.ray.at(hit.t);
            if (light.illuminates(h)) {
                basicColor = basicColor.add(
                        light.color.mul(color)
                                .mul(Math.max(0, hit.n.dot(light.directionFrom(h).normalized()))
                                )
                );
            }
        }

        return color.mul(world.ambientLight).add(basicColor);
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
