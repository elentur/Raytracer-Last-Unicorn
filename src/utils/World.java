package utils;

import geometries.Geometry;
import javafx.scene.image.Image;
import light.Light;
import raytracer.ImageSaver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the scene. In This class will be all objects and lights be available.
 * <p>
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class World implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * represents the background color of the scene.
     */
    public final Color backgroundColor;

    /**
     * represents the ambientLight color of the scene.
     */
    public final Color ambientLight;
    /**
     * A List of Geometries in the scene.
     */
    public final List<Geometry> geometries;

    /**
     * A List of Lights in the scene.
     */
    public final List<Light> lights;

    public Image backImg;

    /**
     * Generates a new world with predefined Background material
     *
     * @param backgroundColor represents the background material of the scene. The material is from typ Color.
     */
    public World(final Color backgroundColor, final Color ambientLight) {
        if (backgroundColor == null) throw new IllegalArgumentException("backgroundColor must not be null!");
        this.backgroundColor = backgroundColor;
        this.geometries = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.ambientLight = ambientLight;
    }


    /**
     * Checks every Geometry in the scene if it is hit by the given ray.
     * Returns the material for the nearest hit or the background material.
     *
     * @param r the Ray that the scene have to check all geometries for a hit with it.
     * @return Color-object of the nearest Geometry that ist hit or of the background material.
     */
    public Color hit(final Ray r, int x, int y) {
        if (r == null) throw new IllegalArgumentException("r must not be null!");
        Hit hit = null;

        for (Geometry g : geometries) {
            final Hit h = g.hit(r);
            if (hit == null || (h != null && h.t < hit.t)) hit = h;
        }
        Color back;
        if (backImg != null) {
            javafx.scene.paint.Color c = backImg.getPixelReader().getColor(x, y);
            back = new Color(c.getRed(), c.getGreen(), c.getBlue());
        } else {
            back = backgroundColor;
        }

        return hit != null ? hit.geo.material.colorFor(hit, this,new Tracer(ImageSaver.raytracer.recursionDepth)) : back;
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
