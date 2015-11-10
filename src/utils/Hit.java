package utils;

import geometries.Geometry;

import java.io.Serializable;

/**
 * This class represents a intersection between a given Ray and a given Geometry.
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class Hit implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * represents the intersect of the geometry of this hit with the ray of this Hit object.
     */
    public final double t;
    /**
     * represents the Ray tha has hit the Geometry of this Hit object.
     */
    public final Ray ray;
    /**
     * represents the Geometry that has a hit with this Ray.
     */
    public final Geometry geo;

    /**
     * Generates a Hit object that represents the hit Geometry, the Ray that hit the Geometry and the smallest
     * intersect between Ray and Geometry.
     *
     * @param t   the smallest intersect between Ray and Geometry.
     * @param ray the Ray that hit this Geometry.
     * @param geo the Geometry that is hit by that Ray.
     */
    public Hit(final double t, final Ray ray, final Geometry geo) {
        if (ray == null) throw new IllegalArgumentException("ray must not be null!");
        if (geo == null) throw new IllegalArgumentException("geo must not be null!");
        this.t = t;
        this.ray = ray;
        this.geo = geo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hit hit = (Hit) o;

        if (Double.compare(hit.t, t) != 0) return false;
        if (ray != null ? !ray.equals(hit.ray) : hit.ray != null) return false;
        return !(geo != null ? !geo.equals(hit.geo) : hit.geo != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(t);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ray != null ? ray.hashCode() : 0);
        result = 31 * result + (geo != null ? geo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "t=" + t +
                ", ray=" + ray +
                ", geo=" + geo +
                '}';
    }

}