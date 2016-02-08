package serializable.lights;

import observables.lights.ODirectionalLight;

/**
 * Wrapper class to serialize the ODirectionalLight object.
 * Created by roberto on 05.02.16.
 */
public class SDirectionalLight extends SLight {

    private static final long serialVersionUID = 1L;

    /**
     * Represents the direction of the Light
     */
    private final double dx;
    private final double dy;
    private final double dz;

    /**
     * Instantiates a new SDirectionalLight Object.
     *
     * @param red part of the color
     * @param green part of the color
     * @param blue part of the color
     * @param dx is the direction of the Light
     * @param dy is the direction of the Light
     * @param dz is the direction of the Light
     * @param castShadows if the light cast shadows
     * @param photons is the photons cast
     * @param size of the pattern
     * @param subdiv Square number of the Rays
     * @param name of the light
     */
    public SDirectionalLight(final double red, final double green, final double blue,
                             final double dx, final double dy, final double dz,
                             final boolean castShadows, final int photons,
                             final double size, final int subdiv, final String name) {
        super(red, green, blue, castShadows, photons, size, subdiv, name);
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }


    @Override
    public ODirectionalLight generate() {
        ODirectionalLight light = new ODirectionalLight();

        light.color.set(new javafx.scene.paint.Color(red, green, blue, 1));
        light.castShadow.set(castShadows);
        light.photons.set(photons);
        light.patternSize.set(size);
        light.patternSubdiv.set(subdiv);
        light.dx.set(dx);
        light.dy.set(dy);
        light.dz.set(dz);
        light.name.set(name);

        return light;
    }
}
