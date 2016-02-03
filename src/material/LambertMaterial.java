package material;

import geometries.Geometry;
import light.Light;
import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import texture.Texture;
import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                synchronized (light.lightShadowPattern) {
                    light.lightShadowPattern.generateSampling();

                    for (Point2 point : light.lightShadowPattern.generateSampling()) {
                        if (light.illuminates(p,point, world, hit.geo)) {
                            c = c.add(light.color.mul(texture.getColor(hit.texCoord.u, hit.texCoord.v)).mul(Math.max(0, hit.n.dot(light.directionFrom(p)))));
                        }
                    }
                    c = c.mul(1.0/light.lightShadowPattern.generateSampling().size());
                }

            ///ambient occlusion test
            List<Vector3> testDirections = new ArrayList<>();
            int numberOfRays = 16;
            int numOfHits=0;
            Random rnd = new Random();

            while(testDirections.size()<numberOfRays){
                Vector3 v = new Vector3(rnd.nextDouble()-0.5,rnd.nextDouble()-0.5,rnd.nextDouble()-0.5);
                double alpha = hit.n.dot(v.normalized());
                if(alpha<Math.PI/2 && alpha >0){
                    testDirections.add(v);
                }

            }
            double ambientOcclusion=0;

            for(Vector3 v : testDirections){
                Ray r = new Ray(p,v);

                for (Geometry g : world.geometries) {
                    if(!g.visibility) continue;
                    final Hit h = g.hit(r);
                    final double border = rnd.nextDouble()*3.0;
                    if (h != null && h.t >0.00001 && h.t<border ) {
                        ambientOcclusion += h.t/border;
                        numOfHits++;
                        break;
                    }

                }
            }
           if(numOfHits>0) c= c.mul(((ambientOcclusion)/(numOfHits*1.0)));

            ////
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
