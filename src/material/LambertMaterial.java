package material;

import light.Light;
import matVect.Point2;
import matVect.Point3;
import texture.Texture;
import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

/**
 * A nearly perfect diffuse Material
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class LambertMaterial extends Material {


    /**
     * Generates a new Lambert material with the given color
     *
     * @param texture Represents the Color of the Lambert material
     */
    public LambertMaterial(final Texture texture, final Texture bumpMap, final double bumpScale, final Texture irradiance) {
        super(texture,bumpMap,bumpScale,irradiance);
        name="Lambert Material";
    }
    private LambertMaterial(LambertMaterial m){
        super(m.texture,m.bumpMap,m.bumpScale,m.irradiance);
        name = m.name;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {

        if (hit == null) throw new IllegalArgumentException("hit must not be null ");
        if (world == null) throw new IllegalArgumentException("world must not be null ");

        Color c = new Color(0, 0, 0);
        for (Light light : world.lights) {
            final Point3 p = hit.ray.at(hit.t);
          //  if (light.illuminates(p, world, hit.geo)) {
          //      c = c.add(light.color.mul(texture.getColor(hit.texCoord.u, hit.texCoord.v)).mul(Math.max(0, hit.n.dot(light.directionFrom(p)))));
          //  }else {
                synchronized (light.lightShadowPattern) {
                    light.lightShadowPattern.generateSampling();

                    for (Point2 point : light.lightShadowPattern.points) {

                        if (light.illuminates(p,point, world, hit.geo)) {
                            c = c.add(light.color.mul(texture.getColor(hit.texCoord.u, hit.texCoord.v)).mul(Math.max(0, hit.n.dot(light.directionFrom(p)))));
                        }
                    }
                    c = c.mul(1.0/light.lightShadowPattern.points.size());
                }
           // }

        }

        return texture.getColor(hit.texCoord.u,hit.texCoord.v).mul(world.ambientLight).add(c);
    }

    /**
     * deepCopy Method
     *
     * @return a copied Object from Material;
     */
    @Override
    public Material deepCopy() {
        return new LambertMaterial(this);
    }

    @Override
    public String toString() {
        return "LambertMaterial{" +
                "material=" + texture +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return false;

    }

    @Override
    public int hashCode() {
        return texture != null ? texture.hashCode() : 0;
    }
}
