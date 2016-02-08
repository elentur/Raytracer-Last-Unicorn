package serializable.lights;

import observables.lights.OSpotLight;

/**
 * Wrapper class to serialize the OSpotLight object.
 * Created by roberto on 05.02.16.
 */
public class SSpotLight extends SLight {

    private static final long serialVersionUID = 1L;

    /**
     * Represents the position of the Light
     */
    private final double px;
    private final double py;
    private final double pz;

    /**
     * Represents the direction of the Light
     */
    private final double dx;
    private final double dy;
    private final double dz;

    /**
     * The angle of opening of the spotlight.
     */
    private final double angle;

    /**
     * Instantiates a new SSpotLight Object.
     *
     * @param red part of the color
     * @param green part of the color
     * @param blue part of the color
     * @param px is the position of the Light
     * @param py is the position of the Light
     * @param pz is the position of the Light
     * @param dx is the direction of the Light
     * @param dy is the direction of the Light
     * @param dz is the direction of the Light
     * @param angle of opening of the spotlight
     * @param castShadows if the light cast shadows
     * @param photons is the photons cast
     * @param size of the pattern
     * @param subdiv Square number of the Rays
     * @param name of the light
     */
    public SSpotLight(final double red, final double green, final double blue,
                      final double px, final double py, final double pz,
                      final double dx, final double dy, final double dz,
                      final double angle,
                      final boolean castShadows, final int photons,
                      final double size, final int subdiv, final String name) {
        super(red, green, blue, castShadows, photons, size, subdiv, name);
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.angle = angle;
    }


    @Override
    public OSpotLight generate() {
        OSpotLight light = new OSpotLight();

        light.color.set(new javafx.scene.paint.Color(red, green, blue, 1));
        light.castShadow.set(castShadows);
        light.photons.set(photons);
        light.patternSize.set(size);
        light.patternSubdiv.set(subdiv);
        light.px.set(px);
        light.py.set(py);
        light.pz.set(pz);
        light.dx.set(dx);
        light.dy.set(dy);
        light.dz.set(dz);
        light.halfAngle.set(angle);
        light.name.set(name);
        return light;
    }
}
