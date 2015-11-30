package utils;

import geometries.AxisAlignedBox;
import geometries.Geometry;
import geometries.Triangle;
import matVect.Point3;
import material.SingleColorMaterial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class Octree implements Serializable {
    private static final long serialVersionUID = 1L;
    public AxisAlignedBox box;

    private List<Geometry> g;

    public Octree[] subtrees;

    public List<Geometry>[] octreeList;

    double lbfX = 999999.0;
    double lbfY = 999999.0;
    double lbfZ = 999999.0;
    double runX = -999999.0;
    double runY = -999999.0;
    double runZ = -999999.0;

    public Octree(List<Geometry> g) {
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

      /*  double e = 0;
        runX += e;
        runY += e;
        runZ += e;
        lbfX -= e;
        lbfY -= e;
        lbfZ -= e;*/
        generateOctrees();
    }

    public Octree(List<Geometry> g, double runX, double runY, double runZ, double lbfX, double lbfY, double lbfZ) {
        this.g = g;
        this.runX = runX;
        this.runY = runY;
        this.runZ = runZ;
        this.lbfX = lbfX;
        this.lbfY = lbfY;
        this.lbfZ = lbfZ;
        generateOctrees();
    }

    private void generateOctrees() {
        box = new AxisAlignedBox(new Point3(runX, runY, runZ), new Point3(lbfX, lbfY, lbfZ), new SingleColorMaterial(new Color(0, 0, 0)));

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
                    Triangle t = (Triangle) geo.next();
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
                subtrees[i] = new Octree(octreeList[i], newRunX, newRunY, newRunZ, newLbfX, newLbfY, newLbfZ);


            }

        }
    }

    public Hit hit(Ray r) {
        Hit h = null;
        if (box.hit(r) == null) return null;

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
