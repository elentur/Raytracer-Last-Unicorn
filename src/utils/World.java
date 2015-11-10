package utils;

import geometries.Geometry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This Class represents the scene. In This class will be all objects and lights be available.
 *
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class World implements Serializable {
    private static final long serialVersionUID = 1L;
   /**
     *represents the background color of the scene.
     */
    public final Color backgroundColor;
    /**
     * A List of Geometries in the scene.
     */
    public final List<Geometry> geometries;

    /**
     *Generates a new world with predefined Background color
     * @param backgroundColor represents the background color of the scene. The color is from typ Color.
     */
    public World(final Color backgroundColor) {
        if(backgroundColor== null) throw new IllegalArgumentException("backgroundColor must not be null!");
        this.backgroundColor = backgroundColor;
        this.geometries=new ArrayList<>();
    }



    /**
     *  Checks every Geometry in the scene if it is hit by the given ray.
     * Returns the color for the nearest hit or the background color.
     * @param r the Ray that the scene have to check all geometries for a hit with it.
     * @return Color-object of the nearest Geometry that ist hit or of the background color.
     */
    public Color hit(final Ray r){
        if(r== null) throw new IllegalArgumentException("r must not be null!");
        Hit hit = null;
        for(Geometry g : geometries){
            final Hit h = g.hit(r);
            if(hit == null || (h != null && h.t < hit.t)) hit = h;
        }

        return hit!= null ? hit.geo.color : backgroundColor;
    }

    @Override
    public String toString() {
        return "World{" +
                "backgroundColor=" + backgroundColor +
                ", geometries=" + geometries +
                '}';
    }

    @Override

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final World world = (World) o;

        if (!backgroundColor.equals(world.backgroundColor)) return false;
        return geometries.equals(world.geometries);

    }

    @Override
    public int hashCode() {
        int result = backgroundColor.hashCode();
        result = 31 * result + geometries.hashCode();
        return result;
    }

}
