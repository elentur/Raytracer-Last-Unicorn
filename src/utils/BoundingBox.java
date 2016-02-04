package utils;

import geometries.Geometry;
import matVect.Normal3;
import matVect.Point3;
import material.Material;
import material.SingleColorMaterial;
import texture.SingleColorTexture;
import texture.TexCoord2;

/**
 * Created by Marcus Baetz on 15.12.2015.
 *
 * @author Marcus Bätz
 */
public class BoundingBox extends Geometry {
    /**
     * The left bottom far corner of the Axis Aligned Box.
     */
    public final Point3 lbf;
    /**
     * The right top near corner of the Axis Aligned Box.
     */
    public final Point3 run;

    private final Plane[] planes;


    /**
     * Instantiates a new Axis Aligned Box Object.

     * @param lbf   of the Axis Aligned Box. Can't be null.
     * @param run   of the Axis Aligned Box. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public BoundingBox(final Point3 run, final Point3 lbf) {
        super(new SingleColorMaterial(new SingleColorTexture(new Color(0,0,0)),new SingleColorTexture(new Color(0,0,0)),0,false,2,16),true,true,true,true);
        if (lbf == null) {
            throw new IllegalArgumentException("The lbf cannot be null!");
        }
        if (run == null) {
            throw new IllegalArgumentException("The run cannot be null!");
        }

        this.lbf = lbf;
        this.run = run;

        planes = new Plane[6];

        planes[0] = new Plane( // front layer
                run,
                new Normal3(0, 0, 1)
        );
        planes[1] = new Plane( // back layer
                lbf,
                new Normal3(0, 0, -1)
        );
        planes[2] = new Plane( // left layer
                lbf,
                new Normal3(-1, 0, 0)
        );
        planes[3] = new Plane( // right layer
                run,
                new Normal3(1, 0, 0)
        );
        planes[4] = new Plane( // up layer
                run,
                new Normal3(0, 1, 0)

        );
        planes[5] = new Plane( // down layer
                lbf,
                new Normal3(0, -1, 0)
        );
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }



        Hit max = null;

        for (final Plane plane : planes) {
            // Finds all layers whose normals shows to the viewer.
            final double condition = r.o.sub(plane.a).dot(plane.n);

            if (condition > 0) {
                // calculates the ray that intersects the selected layers
                final double t = plane.a.sub(r.o).dot(plane.n) / r.d.dot(plane.n);
                if (max == null || t > max.t) {
                    max = new Hit(t,plane.n, r, this,new TexCoord2(0,0));
                }
            }
        }

        return comparison(max);
    }

    @Override
    public Geometry deepCopy() {
        return null;
    }

    @Override
    public Geometry deepCopy(final Material m) {
        return null;
    }

    /**
     * The method checks if the results lies within the Axis Aligned Box coordinates.
     *
     * @param hit a hit of the ray or null
     * @return Hit if the coordinates are within the Axis Aligned Box coordinates or null.
     */
    private Hit comparison(final Hit hit) {
        if (hit != null) {
            final Point3 p = hit.ray.at(hit.t);
            final double e = 0.00000000001;

            if ((lbf.x <= p.x + e && p.x <= run.x + e) &&
                    (lbf.y <= p.y + e && p.y <= run.y + e) &&
                    (lbf.z <= p.z + e && p.z <= run.z + e)
                    )
                return hit;
        }

        return null;
    }

    private class Plane extends Geometry {
        /**
         * A known Point.
         */
        public final Point3 a;
        /**
         * A Normal of the Plane.
         */
        public final Normal3 n;

        /**
         * Instantiates a new Plane Object.
         *
         * @param a     of the Plane. Can't be null.
         * @param n     of the Plane. Can't be null.
         * @throws IllegalArgumentException if one of the given arguments are null.
         */
        public Plane(final Point3 a, final Normal3 n) {
            super(new SingleColorMaterial(new SingleColorTexture(new Color(0,0,0)),new SingleColorTexture(new Color(0,0,0)),0,false,2,16),true,true,true,true);

            if (a == null) {
                throw new IllegalArgumentException("The a cannot be null!");
            }
            if (n == null) {
                throw new IllegalArgumentException("The n cannot be null!");
            }

            this.a = a;
            this.n = n;
        }

        @Override
        public Hit hit(final Ray r) {
            if (r == null) {
                throw new IllegalArgumentException("The r cannot be null!");
            }

            final double nenner = r.d.dot(n);

            if (nenner != 0) {
                final double t = n.dot(a.sub(r.o)) / nenner;
                if (t > 0) return new Hit(t, n, r, this, new TexCoord2(0,0));
            }
            return null;
        }

        @Override
        public Geometry deepCopy() {
            return null;
        }

        @Override
        public Geometry deepCopy(final Material m) {
            return null;
        }
    }
}
