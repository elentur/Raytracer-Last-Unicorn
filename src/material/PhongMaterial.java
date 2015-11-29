package material;

import light.Light;
import matVect.Point3;
import matVect.Vector3;
import utils.Color;
import utils.Hit;
import utils.World;

/**
 * This class represents a PhongMaterial.
 *
 * @author Robert Dziuba 17/11/2015
 */
public class PhongMaterial extends Material {



    /**
     * The color of our reflection.
     */
    public final Color specular;

    /**
     * The value to change the size of the highlight.
     * The larger exponent, the smaller will be the highlight.
     */
    public final int exponent;

    /**
     * Instantiates a new PhongMaterial Object.
     *
     * @param diffuse of the Material. Can't be null.
     * @param specular of the Material. Can't be null.
     * @param exponent of the Material. Muss be bigger zero.
     * @throws IllegalArgumentException if one of the given arguments are null or not in the value range.
     */
    public PhongMaterial(final Color diffuse, final Color specular, final int exponent) {
        super(diffuse);
        if (specular == null) {
            throw new IllegalArgumentException("The specular cannot be null!");
        }
        if (exponent <= 0) {
            throw new IllegalArgumentException("The exponent must be bigger than 0!");
        }
        this.specular = specular;
        this.exponent = exponent;
    }

    @Override
    public Color colorFor(final Hit hit, final World world) {
        if (hit == null) {
            throw new IllegalArgumentException("The hit cannot be null!");
        }
        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        Color basicColor = new Color(0, 0, 0);
        final Vector3 e = hit.ray.d.mul(-1).normalized();
        final Point3 h = hit.ray.at(hit.t);

        for (Light light : world.lights) {

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
        if (!diffuse.equals(that.diffuse)) return false;
        return specular.equals(that.specular);

    }

    @Override
    public int hashCode() {
        int result = diffuse.hashCode();
        result = 31 * result + specular.hashCode();
        result = 31 * result + exponent;
        return result;
    }
}
