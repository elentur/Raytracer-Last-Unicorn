package utils;

import geometries.Geometry;

/**
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class Tracer {

    public int recursionDepth = 0;
    public boolean in;

    public Tracer(int recursionDepth) {
        this.recursionDepth = recursionDepth;
        this.in = true;
    }

    public Color reflection(Ray reflectionRay,World world){

        if(recursionDepth > 0) {
            recursionDepth--;
            Hit hit = null;
            for (Geometry g : world.geometries) {
                if(!g.visibility) continue;
                final Hit h = g.hit(reflectionRay);
                if (hit == null || (h != null && h.t < hit.t && h.t > 0.0001)) hit = h;
            }

            if(hit != null && hit.t > 0.0001){
                return hit.geo.material.colorFor(hit, world,this);
            }
        }


        return world.backgroundColor;
    }
/*
    public Color refraction(Ray reflectionRay,World world, boolean in){
        this.in = !in;
        if(recursionDepth > 0) {
            recursionDepth--;
            Hit hit =  world.hit(reflectionRay,0,0);

            if(hit != null && hit.t > 0.0001){
                return hit.geo.material.colorFor(hit, world,this);
            }
        }


        return world.backgroundColor;
    }*/
}
