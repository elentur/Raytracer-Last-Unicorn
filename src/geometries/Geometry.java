package geometries;

import utils.Color;
import utils.Element;
import utils.Hit;
import utils.Ray;

import java.io.Serializable;

/**
 * This abstract class provides a parent for all Geometry Objects
 *
 * @author Robert Dziuba on 25/10/15.
 */
public abstract class Geometry implements Serializable, Element {
    private static final long serialVersionUID = 1L;
    /**
     * The current color of the Geometry child class.
     */
    public final Color color;

    /**
     * Represents the name of the object.
     */
    public String name;


    /**
     * Instantiates a new Geometry.
     *
     * @param color of child class. Can't be null.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Geometry(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("The Color cannot be null!");
        }
        this.color = color;
    }

    /**
     * abstract hit method
     *
     * @param r the ray
     * @return a Hit
     */
    public abstract Hit hit(final Ray r);

    @Override
    public String toString() {
        return "Geometry{" +
                "color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geometry geometry = (Geometry) o;

        return !(color != null ? !color.equals(geometry.color) : geometry.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
