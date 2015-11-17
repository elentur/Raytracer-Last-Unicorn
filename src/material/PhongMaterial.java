package material;

import light.Light;
import matVect.Point3;
import matVect.Vector3;
import utils.Color;
import utils.Hit;
import utils.World;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class PhongMaterial extends Material {

    public final Color diffuse;

    public final Color specular;

    public final int exponent;

    public PhongMaterial(Color diffuse, Color specular, int exponent) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
    }

    @Override
    public Color colorFor(Hit hit, World world) {

        Color basicColor = new Color(0, 0, 0);
        Vector3 e = hit.ray.d.mul(-1).normalized();

        for (Light light : world.lights) {
            Point3 h = hit.ray.at(hit.t);
            Vector3 l = light.directionFrom(h);
            Vector3 rl = l.reflectedOn(hit.n);
            if (light.illuminates(h)) {
                basicColor = basicColor.add(
                        light.color.mul(diffuse)
                                .mul(Math.max(0, hit.n.dot(l.normalized()))
                                )
                ).add(
                        specular
                                .mul(light.color)
                                .mul(Math.pow(
                                        Math.max(0,rl.dot(e)),exponent)
                                )
                );
            }
        }

        return diffuse.mul(world.ambientLight).add(basicColor);
    }

    @Override
    public String toString() {
        return "PhongMaterial{" +
                "diffuse=" + diffuse +
                ", specular=" + specular +
                ", exponent=" + exponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhongMaterial that = (PhongMaterial) o;

        if (exponent != that.exponent) return false;
        if (diffuse != null ? !diffuse.equals(that.diffuse) : that.diffuse != null) return false;
        return !(specular != null ? !specular.equals(that.specular) : that.specular != null);

    }

    @Override
    public int hashCode() {
        int result = diffuse != null ? diffuse.hashCode() : 0;
        result = 31 * result + (specular != null ? specular.hashCode() : 0);
        result = 31 * result + exponent;
        return result;
    }
}
