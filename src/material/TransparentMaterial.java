package material;

import controller.AController;
import light.Light;
import matVect.Normal3;
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
    private final double iOR;
    private final Texture specular;
    private final Texture reflection;
    private final int exponent;

    public TransparentMaterial(final Texture texture, final Texture specular, final Texture reflection,
                               final int exponent, double indexOfRefraction, final Texture bumpMap,
                               final double bumpScale, final Texture irradiance,
                               boolean ambientOcllusion, double ambientSize, int ambientSubdiv) {
        super(texture, bumpMap, bumpScale, irradiance, ambientOcllusion, ambientSize, ambientSubdiv);
        if (exponent <= 0) {
            throw new IllegalArgumentException("The exponent must be bigger than 0!");
        }

        this.specular = specular;
        this.reflection = reflection;
        this.exponent = exponent;
        this.iOR = indexOfRefraction;
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
        if (e.dot(hit.n) < 0) {
            ref = iOR / AController.raytracer.iOR;
            n = hit.n.mul(-1);
        } else {
            ref = AController.raytracer.iOR / iOR;
            n = hit.n;
        }
        double cosA1 = n.dot(e);
        double co = 1 - (ref * ref) * (1 - cosA1 * cosA1);
        double a = 1;
        if (co >= 0) {
            double cosA2 = Math.sqrt(co);
            final double a0 = Math.pow((AController.raytracer.iOR - iOR) / (AController.raytracer.iOR + iOR), 2);
            a = a0 + (1 - a0) * Math.pow(1 - cosA1, 5);
            final double b = 1 - a;
            Vector3 t = hit.ray.d.mul(ref).sub(n.mul(cosA2 - ref * cosA1));
            Ray refractionRay = new Ray(hit.ray.at(hit.t + 0.00001), t);
            Tracer tracer2 = new Tracer(tracer.recursionDepth);
            basicColor = basicColor.add(tracer2.reflection(refractionRay, world).mul(b));
            basicColor = basicColor.mul(
                    texture.getColor(hit.texCoord.u, hit.texCoord.v)
            );


        }
        Vector3 r = hit.ray.d.normalized().add(n.mul(2 * cosA1));
        final Point3 p = hit.ray.at(hit.t - 0.00001);
        Ray reflectionRay = new Ray(p, r);

        for (Light light : world.lights) {

            Vector3 l = light.directionFrom(p);
            Vector3 rl = l.reflectedOn(hit.n);
            synchronized (light.lightShadowPattern) {
                light.lightShadowPattern.generateSampling();

               // for (Point2 point : light.lightShadowPattern.generateSampling()) {
                //    if (light.illuminates(p, point, world, hit.geo)) {

                        basicColor = basicColor.add(
                                specular.getColor(hit.texCoord.u, hit.texCoord.v)
                                        .mul(light.color)
                                        .mul(Math.pow(
                                                Math.max(0,rl.dot( hit.ray.d.mul(-1).normalized())), exponent)
                                        )
                        );
                  //  }

                //}
                //basicColor = basicColor.mul(1.0 / light.lightShadowPattern.generateSampling().size());
            }


            basicColor = basicColor.add(reflection.getColor(hit.texCoord.u, hit.texCoord.v).mul(tracer.reflection(reflectionRay, world)).mul(a));
        }

        return basicColor;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TransparentMaterial)) return false;
        if (!super.equals(o)) return false;

        TransparentMaterial that = (TransparentMaterial) o;

        if (Double.compare(that.iOR, iOR) != 0) return false;
        if (exponent != that.exponent) return false;
        if (specular != null ? !specular.equals(that.specular) : that.specular != null) return false;
        return !(reflection != null ? !reflection.equals(that.reflection) : that.reflection != null);

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
