package utils;

import geometries.Geometry;

/**
 * This class represents a Tracer.
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class Tracer {

    /**
     * The deep of the recursion
     */
    public int recursionDepth = 0;

    /**
     * Instantiates a new Tracer Object .
     * @param recursionDepth
     * @throws IllegalArgumentException if the given argument is smaller than 0.
     */
    public Tracer(int recursionDepth) {
        if (recursionDepth < 0) throw new IllegalArgumentException("The recursion depth List cannot be smaller than 0!");
        this.recursionDepth = recursionDepth;
    }

    /**
     * calculate the reflection Color according the recursion depth.
     * @param reflectionRay of the reflection
     * @param world of the Scene
     * @return the reflection Color or the world backgroundColor when recursion depth is 0.
     * @throws IllegalArgumentException if the given arguments are null.
     */
    public Color reflection(Ray reflectionRay, World world) {
        if (reflectionRay == null) throw new IllegalArgumentException("The Ray cannot not be null!");
        if (world == null) throw new IllegalArgumentException("The world cannot not be null!");
        if (recursionDepth > 0) {
            recursionDepth--;
            Hit hit = null;
            for (Geometry g : world.geometries) {
                if (!g.visibility) continue;
                final Hit h = g.hit(reflectionRay);
                if (hit == null || (h != null && h.t < hit.t && h.t > 0.0001)) hit = h;
            }

            if (hit != null && hit.t > 0.0001) {
                return hit.geo.material.colorFor(hit, world, this);
            }
        }

        return world.backgroundColor;
    }
}
