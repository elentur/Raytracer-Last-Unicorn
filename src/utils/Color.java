package utils;

import java.io.Serializable;

/**
 * This class represents a Color without any transparency.
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class Color implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * represents the Red Color value of this Color object. The Value have to be between 0.0 and 1.0
     */
    public final double r;
    /**
     * represents the Green Color value of this Color object. The Value have to be between 0.0 and 1.0
     */
    public final double g;
    /**
     * represents the Blue Color value of this Color object. The Value have to be between 0.0 and 1.0
     */
    public final double b;

    /**
     * Generates a Color object that represents a material
     *
     * @param r represents the Red Color value of this Color object. The Value have to be between 0.0 and 1.0
     * @param g represents the Green Color value of this Color object. The Value have to be between 0.0 and 1.0
     * @param b represents the Blue Color value of this Color object. The Value have to be between 0.0 and 1.0
     */
    public Color(final double r, final double g, final double b) {
        if (r < 0) throw new IllegalArgumentException("r have to be greater or equal 0!");
        if (g < 0) throw new IllegalArgumentException("g have to be greater or equal 0!");
        if (b < 0) throw new IllegalArgumentException("b have to be greater or equal 0!");
        this.r = r;
        this.g = g;
        this.b = b;
    }



    /**
     * Adds a material to this material.
     *
     * @param c The Color object that has to be added to this material.
     * @return the new Color that results in this addition.
     */
    public Color add(final Color c) {
        if (c == null) throw new IllegalArgumentException("c must not be null!");
        return new Color(r + c.r, g + c.g, b + c.b);
    }

    /**
     * Subtracts a material from this material.
     *
     * @param c The Color object that has to be subtracted from this material.
     * @return the new Color that results in this subtraction.
     */
    public Color sub(final Color c) {
        if (c == null) throw new IllegalArgumentException("c must not be null!");
        return new Color(
                r - c.r < 0.0 ? 0.0 : r - c.r,
                g - c.g < 0.0 ? 0.0 : g - c.g,
                b - c.b < 0.0 ? 0.0 : b - c.b);
    }

    /**
     * Multiplies a material to this material.
     *
     * @param c The Color object that has to be multiplied to this material.
     * @return the new Color that results in this multiplication.
     */
    public Color mul(final Color c) {
        if (c == null) throw new IllegalArgumentException("c must not be null!");
        return new Color(r * c.r, g * c.g, b * c.b);
    }

    /**
     * Multiplies a scalar to this material.
     *
     * @param v The scalar that has to be multiplied to this material.
     * @return the new Color that results in this multiplication.
     */
    public Color mul(final double v) {
        if (v < 0){throw new IllegalArgumentException("v have to be positive!");}
        return new Color(r * v, g * v, b * v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (Double.compare(color.r, r) != 0) return false;
        if (Double.compare(color.g, g) != 0) return false;
        return Double.compare(color.b, b) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(r);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(g);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
