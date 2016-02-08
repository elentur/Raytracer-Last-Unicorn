package serializable.lights;

import observables.lights.AOLight;
import serializable.SElement;

import java.io.Serializable;

/**
 * Abstract wrapper class for all serializable light objects.
 * Created by Marcus Baetz on 06.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SLight implements SElement, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Represents the Color of the Light
     */
    final double red;
    final double green;
    final double blue;

    /**
     *  Represents if a light cast shadows or not
     */
    final boolean castShadows;

    /**
     * Represents the photons cast by the light(not implemented)
     */
    final int photons;

    /**
     *  The size of the pattern
     */
    final double size;

    /**
     *  The Square number of the Rays for each pixel
     */
    final int subdiv;

    /**
     * the name of the light.
     */
    final String name;

    /**
     *
     * @param red part of the color
     * @param green part of the color
     * @param blue part of the color
     * @param castShadows if the light cast shadows
     * @param photons is the photons cast
     * @param size of the pattern
     * @param subdiv Square number of the Rays
     * @param name of the light
     */
    SLight(final double red, final double green, final double blue,
           final boolean castShadows, final int photons,
           final double size, final int subdiv, final String name) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.castShadows = castShadows;
        this.photons = photons;
        this.size = size;
        this.subdiv = subdiv;
        this.name = name;
    }

    @Override
    public abstract AOLight generate();
}
