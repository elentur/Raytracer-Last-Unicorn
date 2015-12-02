package material;

import light.Light;
import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import raytracer.ImageSaver;
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
    public TransparentMaterial(final Color diffuse, final  Color specular, final  Color reflection, final  int exponent, double indexOfRefraction) {
        super(diffuse);
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
        final Vector3 e = hit.ray.d.mul(-1);
        final Normal3 normal;
        final double ref;
        if (e.dot(hit.n)<0){
            normal= hit.n.mul(-1);
            ref = iOR/ ImageSaver.raytracer.iOR;
        }else{
            normal= hit.n;
            ref = ImageSaver.raytracer.iOR/iOR;
        }
        final double rho1 =e.dot(normal);
        final  double g = 1-(ref*ref)   *(1-rho1*rho1);
        final double rho2 = Math.sqrt(g);
        final Vector3 rd = e.reflectedOn(hit.n);
        final Vector3 rt = e.mul(-ref).sub(normal.mul(rho2-ref*rho1));
        final double r0= Math.pow((ImageSaver.raytracer.iOR-iOR)/(ImageSaver.raytracer.iOR + iOR),2);
        final double r = r0 +(1-r0)*Math.pow((1-rho1),5);
        final double t = 1-r;
        final Point3 p = hit.ray.at(hit.t);
        final Ray ref1Ray = new Ray(p,rd);
        final Ray ref2Ray = new Ray(p,rt);
        for (Light light : world.lights) {

            Vector3 l = light.directionFrom(p);
            Vector3 rl = l.reflectedOn(hit.n);

                if (g < 0){
                     basicColor = basicColor.add(tracer.reflection(ref1Ray, world).mul(r));
                }else {
                    basicColor = basicColor.add(tracer.reflection(ref1Ray, world).mul(r)).add(tracer.reflection(ref2Ray, world).mul(t));
                }
            if(!(diffuse.r ==0 && diffuse.g==0 && diffuse.b ==0))basicColor =  basicColor.mul(diffuse);
            if (light.illuminates(p, world)) {
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
            basicColor = basicColor.add(reflection.mul(tracer.reflection(ref1Ray,world)));
        }

        return basicColor;
    }
}
