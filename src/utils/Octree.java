package utils;

import geometries.Geometry;
import geometries.Triangle;
import matVect.Point3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a Octree.
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class Octree {
    /**
     * The box contains the BoundingBox object.
     */
    private BoundingBox box;

    /**
     * The g contains a List of all Geometry which will be boxed.
     */
    private final List<Geometry> g;

    /**
     * The subtrees contains a array of suboctrees.
     */
    private Octree[] subtrees;

    /**
     * The octreeList contains a ArrayList of Geometries.
     */
    private List<Geometry>[] octreeList;

    /**
     * Variables to find the biggest group of geometries which have to be boxed
     */
    private double lbfX = 999999.0;
    private double lbfY = 999999.0;
    private double lbfZ = 999999.0;
    private double runX = -999999.0;
    private double runY = -999999.0;
    private double runZ = -999999.0;

    /**
     * Instantiates a new Octree Object .
     *
     * @param g is a list of geometries
     */
    public Octree(List<Geometry> g) {
        if (g == null) throw new IllegalArgumentException("The Geometry List cannot be null!");
        this.g = g;
        for (Geometry geo : g) {
            Triangle t = (Triangle) geo;

            if (t.a.x < lbfX) lbfX = t.a.x;
            if (t.a.y < lbfY) lbfY = t.a.y;
            if (t.a.z < lbfZ) lbfZ = t.a.z;

            if (t.b.x < lbfX) lbfX = t.b.x;
            if (t.b.y < lbfY) lbfY = t.b.y;
            if (t.b.z < lbfZ) lbfZ = t.b.z;

            if (t.c.x < lbfX) lbfX = t.c.x;
            if (t.c.y < lbfY) lbfY = t.c.y;
            if (t.c.z < lbfZ) lbfZ = t.c.z;

            if (t.a.x > runX) runX = t.a.x;
            if (t.a.y > runY) runY = t.a.y;
            if (t.a.z > runZ) runZ = t.a.z;

            if (t.b.x > runX) runX = t.b.x;
            if (t.b.y > runY) runY = t.b.y;
            if (t.b.z > runZ) runZ = t.b.z;

            if (t.c.x > runX) runX = t.c.x;
            if (t.c.y > runY) runY = t.c.y;
            if (t.c.z > runZ) runZ = t.c.z;
        }
        generateOctrees();
    }

    /**
     * Instantiates a new Octree Object which given attributes.
     *
     * @param g    is a list of geometries
     * @param runX The right top near corner of the Axis Aligned Box.
     * @param runY The right top near corner of the Axis Aligned Box.
     * @param runZ The right top near corner of the Axis Aligned Box.
     * @param lbfX The left bottom far corner of the Axis Aligned Box.
     * @param lbfY The left bottom far corner of the Axis Aligned Box.
     * @param lbfZ The left bottom far corner of the Axis Aligned Box.
     */
    private Octree(List<Geometry> g, double runX, double runY, double runZ, double lbfX, double lbfY, double lbfZ) {
        if (g == null) throw new IllegalArgumentException("The Geometry List cannot be null!");
        this.g = g;
        this.runX = runX;
        this.runY = runY;
        this.runZ = runZ;
        this.lbfX = lbfX;
        this.lbfY = lbfY;
        this.lbfZ = lbfZ;
        generateOctrees();
    }

    /**
     * generate the subtrees List when the size of elements is bigger than 500 and the geometries are triangles.
     */
    private void generateOctrees() {
        box = new BoundingBox(new Point3(runX, runY, runZ), new Point3(lbfX, lbfY, lbfZ));

        final double e = 0.0000001;
        if (g.size() > 500) {
            subtrees = new Octree[8];
            octreeList = new ArrayList[8];
            for (int i = 0; i < 8; i++) {
                octreeList[i] = new ArrayList<>();
                double newRunX, newRunY, newRunZ, newLbfX, newLbfY, newLbfZ;

                if (i == 0 || i == 1 || i == 3 || i == 5) {
                    newRunX = runX - ((runX - lbfX) / 2.0);
                    newLbfX = lbfX;
                } else {
                    newRunX = runX;
                    newLbfX = lbfX + ((runX - lbfX) / 2.0);
                }
                if (i == 0 || i == 1 || i == 2 || i == 4) {
                    newRunY = runY - ((runY - lbfY) / 2.0);
                    newLbfY = lbfY;
                } else {
                    newRunY = runY;
                    newLbfY = lbfY + ((runY - lbfY) / 2.0);
                }
                if (i == 0 || i == 2 || i == 3 || i == 6) {
                    newRunZ = runZ - ((runZ - lbfZ) / 2.0);
                    newLbfZ = lbfZ;
                } else {
                    newRunZ = runZ;
                    newLbfZ = lbfZ + ((runZ - lbfZ) / 2.0);
                }
                for (Iterator<Geometry> geo = g.iterator(); geo.hasNext(); ) {
                    Geometry g = geo.next();
                    if (g instanceof Triangle) {
                        Triangle t = (Triangle) g;
                        if ((newLbfX <= t.a.x && t.a.x <= newRunX + e) &&
                                (newLbfY <= t.a.y + e && t.a.y <= newRunY + e) &&
                                (newLbfZ <= t.a.z + e && t.a.z <= newRunZ + e) &&
                                (newLbfX <= t.b.x + e && t.b.x <= newRunX + e) &&
                                (newLbfY <= t.b.y + e && t.b.y <= newRunY + e) &&
                                (newLbfZ <= t.b.z + e && t.b.z <= newRunZ + e) &&
                                (newLbfX <= t.c.x + e && t.c.x <= newRunX + e) &&
                                (newLbfY <= t.c.y + e && t.c.y <= newRunY + e) &&
                                (newLbfZ <= t.c.z + e && t.c.z <= newRunZ + e)) {
                            geo.remove();
                            octreeList[i].add(t);
                        }
                    }
                }
                subtrees[i] = new Octree(octreeList[i], newRunX, newRunY, newRunZ, newLbfX, newLbfY, newLbfZ);
            }

        }
    }

    /**
     * generate a hit object if the ray hits a BoundingBox or one of the BoundingBoxes in the subtrees.
     *
     * @param r the ray
     * @return a Hit or null
     * @throws IllegalArgumentException if one of the given argument is null.
     */
    public Hit hit(Ray r) {
        if (r == null) throw new IllegalArgumentException("The Ray cannot be null!");

        Hit h = null;

        if (box.hit(r) == null) {
            if (r.o.x + 0.00001 <= box.run.x && r.o.x - 0.00001 >= box.lbf.x &&
                    r.o.y + 0.00001 <= box.run.y && r.o.y - 0.00001 >= box.lbf.y &&
                    r.o.z + 0.00001 <= box.run.z && r.o.z - 0.00001 >= box.lbf.z) {
            } else {
                return null;
            }
        }

        if (subtrees == null) {
            for (Geometry t : g) {
                Hit hit = t.hit(r);
                if (h == null || (hit != null && h.t > hit.t)) h = hit;
            }
        } else {
            for (Octree sub : subtrees) {
                Hit hit = sub.hit(r);
                if (h == null || (hit != null && h.t > hit.t)) h = hit;
            }
            for (Geometry t : g) {
                Hit hit = t.hit(r);
                if (h == null || (hit != null && h.t > hit.t)) h = hit;
            }
        }

        return h;
    }
}
