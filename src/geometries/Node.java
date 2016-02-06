package geometries;

import matVect.Transform;
import material.SingleColorMaterial;
import texture.SingleColorTexture;
import utils.Color;
import utils.Hit;
import utils.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a Node Object.
 *
 * @author Robert Dziuba on 15/12/15.
 */
public class Node extends Geometry {
    /**
     * A object with all transform operations
     */
    public final Transform t;
    /**
     * A list with all geometries which will be rendered
     */
    public final List<Geometry> geos;


    /**
     * Instantiates a new Geometry.
     * @param geos is a List of containing geometries.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Node(final Transform transform, final List<Geometry> geos, final boolean reciveShadows, final boolean castShadows, final boolean visibility, final boolean flipNormal) {
        super(new SingleColorMaterial(new SingleColorTexture(new Color(0,0,0)),
                new SingleColorTexture(new Color(0,0,0)),0,false,2,16),reciveShadows,castShadows,
                visibility,flipNormal);

        if (geos == null) throw new IllegalArgumentException("The geos cannot be null!");
        this.t = transform;
        this.geos = geos;
    }

    /**
     * Instantiates a new Geometry.
     * @param geo is a geometry.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Node(final Transform transform, Geometry geo,final boolean reciveShadows,
                final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        this(transform, new ArrayList<Geometry>(Arrays.asList(geo)),reciveShadows,castShadows,visibility,flipNormal);
    }


    @Override
    public Hit hit(final Ray r) {
        final Ray tr = t.mul(r);

        Hit hit = null;

        for (final Geometry g : geos) {
            if(!g.visibility) continue;
            final Hit h = g.hit(tr);
            if (hit == null || (h != null && h.t < hit.t)) hit = h;
        }

        return (hit == null) ? hit : new Hit(hit.t, t.mul(hit.n),r,hit.geo,hit.texCoord);
    }


    @Override
    public String toString() {
        return "Node{" +
                "t=" + t +
                ", geos=" + geos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (t != null ? !t.equals(node.t) : node.t != null) return false;
        return geos != null ? geos.equals(node.geos) : node.geos == null;

    }

    @Override
    public int hashCode() {
        int result = t != null ? t.hashCode() : 0;
        result = 31 * result + (geos != null ? geos.hashCode() : 0);
        return result;
    }
}
