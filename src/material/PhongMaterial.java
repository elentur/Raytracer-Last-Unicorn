package material;

import light.Light;
import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import texture.Texture;
import utils.Color;
import utils.Hit;
import utils.Tracer;
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
    private final Texture specular;

    /**
     * The value to change the size of the highlight.
     * The larger exponent, the smaller will be the highlight.
     */
    private final int exponent;

    /**
     * Instantiates a new PhongMaterial Object.
     *
     * @param texture  of the Material. Can't be null.
     * @param specular of the Material. Can't be null.
     * @param exponent of the Material. Muss be bigger zero.
     * @throws IllegalArgumentException if one of the given arguments are null or not in the value range.
     */
    public PhongMaterial(final Texture texture, final Texture specular, final int exponent,
                         final Texture bumpMap, final double bumpScale, final Texture irradiance,
                         boolean ambientOcllusion, double ambientSize, int ambientSubdiv) {
        super(texture, bumpMap, bumpScale, irradiance, ambientOcllusion, ambientSize, ambientSubdiv);
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
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
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
            synchronized (light.lightShadowPattern) {
                light.lightShadowPattern.generateSampling();

                for (Point2 point : light.lightShadowPattern.generateSampling()) {
                    if (light.illuminates(h, point, world, hit.geo)) {
                        basicColor = basicColor.add(
                                light.color.mul(texture.getColor(hit.texCoord.u, hit.texCoord.v))
                                        .mul(Math.max(0, hit.n.dot(l.normalized()))
                                        )
                        ).add(
                                specular.getColor(hit.texCoord.u, hit.texCoord.v)
                                        .mul(light.color)
                                        .mul(Math.pow(
                                                Math.max(0, rl.dot(e)), exponent)
                                        )
                        );
                    }
                }
                basicColor = basicColor.mul(1.0 / light.lightShadowPattern.generateSampling().size());
            }
        }

        return texture.getColor(hit.texCoord.u, hit.texCoord.v).mul(world.ambientLight).add(basicColor);
    }


    @Override
    public String toString() {
        return "PhongMaterial{" +
                "texture=" + texture +
                ", specular=" + specular +
                ", exponent=" + exponent +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PhongMaterial)) return false;
        if (!super.equals(o)) return false;

        PhongMaterial that = (PhongMaterial) o;

        if (exponent != that.exponent) return false;
        return !(specular != null ? !specular.equals(that.specular) : that.specular != null);

    }

    @Override
    public int hashCode() {
        int result = texture.hashCode();
        result = 31 * result + specular.hashCode();
        result = 31 * result + exponent;
        return result;
    }
}
