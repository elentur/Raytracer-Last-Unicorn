package material;

import controller.AController;
import light.Light;
import matVect.Normal3;
import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import texture.Texture;
import utils.*;

/**
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class TransparentMaterial extends Material {
    public final double iOR;
    public final Texture specular;
    public final  Texture reflection;
    public final  int exponent;
    public TransparentMaterial(final Texture texture, final  Texture specular, final  Texture reflection,
                               final  int exponent, double indexOfRefraction, final Texture bumpMap,
                               final double bumpScale, final Texture irradiance) {
        super(texture,bumpMap,bumpScale,irradiance);
        name="Transparent Material";
        this.specular=specular;
        this.reflection=reflection;
        this.exponent=exponent;
        this.iOR=indexOfRefraction;
    }

    public TransparentMaterial(final TransparentMaterial m) {
        super(m.texture,m.bumpMap,m.bumpScale,m.irradiance);
        name=m.name;
        this.specular=m.specular;
        this.reflection=m.reflection;
        this.exponent=m.exponent;
        this.iOR=m.iOR;
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
            ref = iOR/ AController.raytracer.iOR;
            n = hit.n.mul(-1);

        }else{
            ref = AController.raytracer.iOR/iOR;
            n = hit.n;
        }
        double cosA1 = n.dot(e);
        double co = 1 - (ref * ref) * (1 - cosA1 * cosA1);
        double a=1;
        if(co>=0) {
            double cosA2 = Math.sqrt(co);
            final double a0 = Math.pow((AController.raytracer.iOR - iOR) / (AController.raytracer.iOR + iOR), 2);
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
            if (light.illuminates(p,new Point2(0,0), world,hit.geo)) {
                basicColor = basicColor.add(
                        light.color.mul(texture.getColor(hit.texCoord.u,hit.texCoord.v))
                                .mul(Math.max(0, hit.n.dot(l.normalized()))
                                )
                ).add(
                        specular.getColor(hit.texCoord.u,hit.texCoord.v)
                                .mul(light.color)
                                .mul(Math.pow(
                                        Math.max(0, rl.dot(e)), exponent)
                                )
                );
            }
            basicColor = basicColor.add(reflection.getColor(hit.texCoord.u,hit.texCoord.v).mul(tracer.reflection(reflectionRay,world)).mul(a));
        }

        return basicColor;
    }

    /**
     * deepCopy Method
     *
     * @return a copied Object from Material;
     */
    @Override
    public Material deepCopy() {
        return new TransparentMaterial(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        return false;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(iOR);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (specular != null ? specular.hashCode() : 0);
        result = 31 * result + (reflection != null ? reflection.hashCode() : 0);
        result = 31 * result + exponent;
        return result;
    }

    @Override
    public String toString() {
        return "TransparentMaterial{" +
                "iOR=" + iOR +
                ", specular=" + specular +
                ", reflection=" + reflection +
                ", exponent=" + exponent +
                '}';
    }
}
