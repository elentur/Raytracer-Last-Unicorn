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
 * This class represents a OrenNayarMaterial.
 * The lighting model of Oren-Nayar is suitable to describe rough or coarse materials, like fabric, sand or stone.
 * <p>
 * Created by Robert Dziuba on 24.11.15.
 */
public class OrenNayarMaterial extends Material {

    /**
     * The roughness of the material
     */
    public final double rough_sq;

    /**
     * Instantiates a new SpotLight Object.
     *
     * @param texture     of the Material. Can't be null.
     * @param roughness of the Material. Can't be under 0.0 and over 1.0.
     * @throws IllegalArgumentException if one of the given arguments are null or not in the value range.
     */
    public OrenNayarMaterial(final Texture texture, final double roughness,
                             final Texture bumpMap, final double bumpScale, final Texture irradiance,
                             boolean ambientOcllusion,double ambientSize, int ambientSubdiv) {
        super(texture,bumpMap,bumpScale,irradiance, ambientOcllusion,ambientSize,ambientSubdiv);
        name="Oren-Nayar Material";
        if (roughness < 0.0 && roughness > 1.0) {
            throw new IllegalArgumentException("The roughness muss be between 0.0 and 1.0!");
        }

        this.rough_sq = roughness * roughness;
    }

    public OrenNayarMaterial(final OrenNayarMaterial m) {
        super(m.texture,m.bumpMap,m.bumpScale,m.irradiance,m.ambientOcllusion,m.ambientSize,m.ambientSubdiv);
         name=m.name;
        this.rough_sq = m.rough_sq;
    }

    /*
     * This formula can be described as follows: θi is the angle between the normal and the light vector.
     * The angle around the normal vector between the viewer and the light vector is described with φ o - i φ.
     * The parameter ρ is the reflection properties of the surface. The E0 parameter express the energy of light.
     * The constant value σ describes the roughness of the surface and is suitable by the user to choose.
     *
     */

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null) {
            throw new IllegalArgumentException("The hit cannot be null!");
        }
        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        Color basicColor = new Color(0, 0, 0);

        final Point3 h = hit.ray.at(hit.t);
        final Vector3 v = hit.ray.o.sub(h).normalized();

        //x = v - n * dot( v, n )
        final Vector3 x = v.sub(hit.n).mul(hit.n.dot(v));

        final double c1 = 1.0 - 0.5 * (rough_sq / (rough_sq + 0.33));

        double c2 = 0.45f * (rough_sq / (rough_sq + 0.09));
        double c3 = (1.0 / 8.0) * (rough_sq / (rough_sq + 0.09));

        //Simple Variant
        /*
        final Point3 h = hit.ray.at(hit.t);
        final Vector3 v = hit.ray.o.sub(h).normalized();*/

        for (Light light : world.lights) {
            synchronized (light.lightShadowPattern) {
                light.lightShadowPattern.generateSampling();

                for (Point2 point : light.lightShadowPattern.generateSampling()) {
            if (light.illuminates(h,point, world,hit.geo)) {

                final Vector3 l = light.directionFrom(h).normalized();
                final double alpha = Math.max(Math.acos(hit.n.dot(v)), Math.acos(hit.n.dot(l)));
                final double beta = Math.min(Math.acos(hit.n.dot(v)), Math.acos(hit.n.dot(l)));

                //y = l - n * dot( l, n )
                final Vector3 y = l.sub(hit.n).mul(l.dot(hit.n));
                final double gamma = x.dot(y);

                if (gamma >= 0) {
                    c2 *= Math.sin(alpha);
                } else {
                    c2 *= (Math.sin(alpha) - Math.pow((2 * beta) / Math.PI, 3));
                }

                c3 *= Math.pow((4.0 * alpha * beta) / (Math.PI * Math.PI), 2);

                final double a = gamma * c2 * Math.tan(beta);
                final double b = (1 - Math.abs(gamma)) * c3 * Math.tan((alpha + beta) / 2.0);


                basicColor = basicColor.add(
                        texture.getColor(hit.texCoord.u,hit.texCoord.v).mul(Math.max(0.0, hit.n.dot(l)) * (c1 + a + b))
                );
                //simple variant
                /*
                final Vector3 l = light.directionFrom(h).normalized();
                final double alpha    = Math.max( Math.acos( hit.n.dot(v) ), Math.acos( hit.n.dot(l) ) );
                final double beta     = Math.min( Math.acos( hit.n.dot(v) ), Math.acos( hit.n.dot(l) ) );
                final double a = 1-0.5*(rough_sq/(rough_sq+0.57));
                final double b = 0.45*(rough_sq/(rough_sq+0.09));
                basicColor = basicColor.add(light.color.mul(diffuse).mul(Math.max(0, hit.n.dot(l))).mul(
                        a+b*Math.max( 0.0, hit.n.dot(l) )* Math.sin(alpha)*Math.tan(beta)
                ));*/

            }
                }
                basicColor = basicColor.mul(1.0/light.lightShadowPattern.generateSampling().size());
            }
        }

        return texture.getColor(hit.texCoord.u,hit.texCoord.v).mul(world.ambientLight).add(basicColor);
    }

    /**
     * deepCopy Method
     *
     * @return a copied Object from Material;
     */
    @Override
    public Material deepCopy() {
        return new OrenNayarMaterial(this);
    }

    @Override
    public String toString() {
        return "OrenNayarMaterial{" +
                "color=" + texture +
                ", rough_sq=" + rough_sq +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        return false;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(rough_sq);
        return (int) (temp ^ (temp >>> 32));
    }
}
