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

       /* double r  ;
        Normal3 normal ;
        if(hit.ray.d.normalized().dot(hit.n)>0.0001){
            r = iOR/ImageSaver.raytracer.iOR;
            normal = hit.n.mul(1);
        } else{
            normal = hit.n.mul(-1);
            r = ImageSaver.raytracer.iOR/ iOR;
        }
        double w =  hit.ray.d.normalized().dot(hit.n)*(-r);
        double k = 1 +(w-r)*(w+r);
        if(k>0) {
            Vector3 t = hit.ray.d.normalized().mul(r).add(hit.n.mul(w - Math.sqrt(k)));
            Ray ray = new Ray(hit.ray.at(hit.t+0.0001),t);
            basicColor = basicColor.add(tracer.reflection(ray, world));

        }*/
      /*  final Vector3 e = hit.ray.d.mul(-1).normalized();
        final Vector3 rd = e.reflectedOn(hit.n);
        final Normal3 normal;
        final double ref;
       // if(tracer.recursionDepth == ImageSaver.raytracer.recursionDepth) tracer.in = true;
        if (tracer.in){
            normal= hit.n;
            ref = ImageSaver.raytracer.iOR/iOR;
        }else{
            normal= hit.n.mul(-1);
            ref = iOR/ ImageSaver.raytracer.iOR;
        }
        final double rho1 =e.dot(normal);
        final double g = 1-(ref*ref)   *(1-rho1*rho1);
        final double rho2 = Math.sqrt(g);
        final Vector3 rt = hit.ray.d.mul(ref).sub(normal.mul(rho2-rho1*ref));
        final double r0= Math.pow((ImageSaver.raytracer.iOR-iOR)/(ImageSaver.raytracer.iOR + iOR),2);
        final double r = r0 +(1-r0)*Math.pow((1-rho1),5);
        final double t = 1-r;
        final Point3 p = hit.ray.at(hit.t-0.00001);
        final Ray ref1Ray = new Ray(p,rd);
        final Ray ref2Ray = new Ray(p,rt);
        for (Light light : world.lights) {

            Vector3 l = light.directionFrom(p);
            Vector3 rl = l.reflectedOn(normal);
            if (g < 0){
                basicColor = basicColor.add(reflection.mul(tracer.reflection(ref1Ray,world).mul(r)));
            }else {
                basicColor = basicColor.add(reflection.mul(tracer.reflection(ref1Ray,world).mul(r))).add(tracer.refraction(ref2Ray, world,tracer.in).mul(t));
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



        }
        return basicColor;*/


        /*double ref;
        Normal3 normal;
        if (tracer.in){
            normal= hit.n.mul(1);
            ref = ImageSaver.raytracer.iOR/iOR;
        }else{
            normal= hit.n.mul(-1);
            ref =  iOR/ ImageSaver.raytracer.iOR;
        }

        double oi = hit.ray.d.normalized().mul(-1).dot(normal);
        double ot = Math.pow((ref),2)*(1-(oi*oi));
        Vector3 t = hit.ray.d.normalized().mul(ref).add(normal.mul(ref*oi - Math.sqrt(1-ot)));
        Vector3 r =  hit.ray.d.normalized().add(normal.mul(2*oi));
        final Point3 p = hit.ray.at(hit.t-0.00001);
        final Ray ref1Ray = new Ray(p,r);
        final Ray ref2Ray = new Ray(p,t);
        final double r0= Math.pow((ImageSaver.raytracer.iOR-iOR)/(ImageSaver.raytracer.iOR + iOR),2);
        double a = r0 +(1-r0)*Math.pow((1-oi),5);
        if (a<0) a=0;
        if(a >1)a =1;
        final double b = 1-a;
        basicColor = basicColor.add(reflection.mul(tracer.reflection(ref1Ray,world).mul(a))).add(tracer.refraction(ref2Ray, world,tracer.in).mul(b));

        return basicColor;*/
        double ref;
        Normal3 n;
        if(tracer.in){
            ref = ImageSaver.raytracer.iOR/iOR;
            n = hit.n;
        }else{
            ref = iOR/ImageSaver.raytracer.iOR;
            n = hit.n.mul(-1);
        }
        double cosA1 = hit.ray.d.normalized().dot(hit.n)*-1;
        double cosA2 = Math.sqrt(1-(ref*ref)*(1-cosA1*cosA1));
        Vector3 t = hit.ray.d.mul(ref).sub(hit.n.mul(cosA2-ref*cosA1));
        Vector3 r =  hit.ray.d.normalized().add(n.mul(2*cosA1));
        final Point3 p = hit.ray.at(hit.t-0.00001);
        Ray refractionRay = new Ray(p,t);
        Ray reflectionRay = new Ray(p,r);
        final double r0= Math.pow((ImageSaver.raytracer.iOR-iOR)/(ImageSaver.raytracer.iOR + iOR),2);
        double a = r0 +(1-r0)*Math.pow((1+cosA1),5);
        if(a>1) System.out.println(cosA1);
        //if(a >1)a =1;
        final double b = 1-a;
       // if()
        basicColor = basicColor.add(tracer.refraction(refractionRay,world,tracer.in).mul(1));

        for (Light light : world.lights) {

            Vector3 l = light.directionFrom(p);
            Vector3 rl = l.reflectedOn(hit.n);
            final Vector3 e = hit.ray.d.mul(-1).normalized();
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
            basicColor = basicColor.add(reflection.mul(tracer.reflection(reflectionRay,world)));
        }

        return basicColor;
    }

}
