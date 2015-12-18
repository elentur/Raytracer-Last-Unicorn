package material;

import light.Light;
import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import raytracer.ImageSaver;
import texture.Texture;
import utils.*;

/**
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class TransparentMaterial extends Material {
    public final double iOR;
    public final Color specular;
    public final  Color reflection;
    public final  int exponent;
    public TransparentMaterial(final Texture texture, final  Color specular, final  Color reflection, final  int exponent, double indexOfRefraction) {
        super(texture);
        this.specular=specular;
        this.reflection=reflection;
        this.exponent=exponent;
        this.iOR=indexOfRefraction;
    }

    /**
     * Returns the right illuminated color for the hit point
     *
     * @param hit    The Hit-Object for the hit Point
     * @param world  The WorldObject for this scene
     * @param tracer calculates the color
     * @return the Color-Object for the hit point
     */
    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {
        if (hit == null) {
            throw new IllegalArgumentException("The hit cannot be null!");
        }
        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        Color basicColor = new Color(0, 0, 0);

        double ref;
        Normal3 n;
        final Vector3 e = hit.ray.d.mul(-1).reflectedOn(hit.n);
        if(e.dot(hit.n) < 0){
            ref = iOR/ImageSaver.raytracer.iOR;
            n = hit.n.mul(-1);

        }else{
            ref = ImageSaver.raytracer.iOR/iOR;
            n = hit.n;
        }
        double cosA1 = n.dot(e);
        double co = 1 - (ref * ref) * (1 - cosA1 * cosA1);
        double a=1;
        if(co>=0) {
            double cosA2 = Math.sqrt(co);
            final double a0 = Math.pow((ImageSaver.raytracer.iOR - iOR) / (ImageSaver.raytracer.iOR + iOR), 2);
             a = a0 + (1 - a0) * Math.pow(1 - cosA1, 5);
            final double b = 1 - a;
            Vector3 t = hit.ray.d.mul(ref).sub(n.mul(cosA2-ref*cosA1));
            Ray refractionRay = new Ray(hit.ray.at(hit.t+0.00001),t);
            Tracer tracer2 = new Tracer(tracer.recursionDepth);
            basicColor = basicColor.add(tracer2.reflection(refractionRay,world).mul(b));
        }
        Vector3 r =  hit.ray.d.normalized().add(n.mul(2*cosA1));
        final Point3 p = hit.ray.at(hit.t-0.00001);
        Ray reflectionRay = new Ray(p,r);

        for (Light light : world.lights) {

            Vector3 l = light.directionFrom(p);
            Vector3 rl = l.reflectedOn(hit.n);
            if (light.illuminates(p, world,hit.geo)) {
                basicColor = basicColor.add(
                        light.color.mul(texture.getColor(0,0))
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
            basicColor = basicColor.add(reflection.mul(tracer.reflection(reflectionRay,world)).mul(a));
        }

        return basicColor;
    }

}
