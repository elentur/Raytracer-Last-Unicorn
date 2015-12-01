package utils;

import geometries.Geometry;
import matVect.Point3;
import matVect.Vector3;
import raytracer.ImageSaver;

/**
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class Tracer {

    private int recursionDepth = 0;

    public Tracer(int recursionDepth) {
        this.recursionDepth = recursionDepth;
    }

    public Color reflection(Ray reflectionRay,World world){

        if(recursionDepth > 0) {
            recursionDepth--;
            Hit hit = null;
            for (Geometry g : world.geometries) {
                final Hit h = g.hit(reflectionRay);
                if (hit == null || (h != null && h.t < hit.t && h.t > 0.0001)) hit = h;
            }

            if(hit != null && hit.t > 0.0001){
                return hit.geo.material.colorFor(hit, world,this);
            }
        }

        return world.backgroundColor;
    }
}
