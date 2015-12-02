package material;

import light.Light;
import matVect.Point3;
import matVect.Vector3;
import utils.*;

/**
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class ReflectiveMaterial extends Material {
    public final Color specular;
    public final  Color reflection;
    public final  int exponent;

    public ReflectiveMaterial(final Color diffuse, final  Color specular, final  Color reflection, final  int exponent) {
        super(diffuse);
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
            if (light.illuminates(h, world)) {
                basicColor = basicColor.add(
                        light.color.mul(diffuse)
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

        return diffuse.mul(world.ambientLight).add(basicColor);
    }
}
