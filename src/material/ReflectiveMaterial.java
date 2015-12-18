package material;

import light.Light;
import matVect.Point3;
import matVect.Vector3;
import texture.Texture;
import utils.*;

/**
 * Created by Robert Dziuba on 01.12.2015.
 *
 * @author Robert Dziuba
 */
public class ReflectiveMaterial extends Material {
    /**
     * Represents the Specular color
     */
    public final Color specular;
    /**
     * represents the reflection amount and color
     */
    public final  Color reflection;
    /**
     * amount of the light spot
     */
    public final  int exponent;

    /**
     * A Material what represents any reflectiv materials
     * @param texture  of the Material. Can't be null.
     * @param specular of the Material. Can't be null.
     * @param reflection of the Material. Can't be null.
     * @param exponent of the Material. Muss be bigger zero.
     */
    public ReflectiveMaterial(final Texture texture, final  Color specular, final  Color reflection, final  int exponent) {
        super(texture);
        this.specular=specular;
        this.reflection=reflection;
        this.exponent=exponent;
    }

    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {

        if (hit == null) {
            throw new IllegalArgumentException("The hit cannot be null!");
        }
        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        Color basicColor = new Color(0, 0, 0);
        final Vector3 e = hit.ray.d.mul(-1).normalized();
        final Point3 h = hit.ray.at(hit.t);
        final Ray refRay = new Ray(h,hit.ray.d.normalized().mul(-1).reflectedOn(hit.n));

        for (Light light : world.lights) {

            Vector3 l = light.directionFrom(h);
            Vector3 rl = l.reflectedOn(hit.n);
            if (light.illuminates(h, world,hit.geo)) {
                basicColor = basicColor.add(
                        light.color.mul(texture.getColor(hit.texCoord.u,hit.texCoord.v))
                                .mul(Math.max(0, hit.n.dot(l.normalized()))
                                )
                ).add(
                        specular
                                .mul(light.color)
                                .mul(Math.pow(
                                        Math.max(0, rl.dot(e)), exponent)
                                )
                );
            }
            basicColor = basicColor.add(reflection.mul(tracer.reflection(refRay,world)));
        }

        return texture.getColor(hit.texCoord.u, hit.texCoord.v).mul(world.ambientLight).add(basicColor);
    }
}
