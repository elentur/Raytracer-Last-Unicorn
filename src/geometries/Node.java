package geometries;

import matVect.Transform;
import material.Material;
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
     * @param t is the transform object.
     * @param geos is a List of containing geometries.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Node(final Transform t, final List<Geometry> geos,final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        super(new SingleColorMaterial(new SingleColorTexture(new Color(0,0,0)),
                new SingleColorTexture(new Color(0,0,0)),0),reciveShadows,castShadows,
                visibility,flipNormal);

        if (t == null) throw new IllegalArgumentException("The t cannot be null!");
        if (geos == null) throw new IllegalArgumentException("The geos cannot be null!");

        this.t = t;
        this.geos = geos;
    }

    /**
     * Copy Constructor
     *
     * @param node
     */
    public Node(Node node) {
        super(node.material, node.reciveShadows, node.castShadows, node.visibility, node.flipNormal);
        this.t = node.t;
        this.geos = node.geos;
    }

    /**
     * Instantiates a new Geometry.
     * @param t is the transform object.
     * @param geo is a geometry.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Node(final Transform t, Geometry geo,final boolean reciveShadows,
                final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        this(t, new ArrayList<Geometry>(Arrays.asList(geo)),reciveShadows,castShadows,visibility,flipNormal);
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
    public Node deepCopy() {
        return new Node(this);
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
