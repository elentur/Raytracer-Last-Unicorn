package serializable.lights;

import observables.lights.OPointLight;

/**
 * Wrapper class to serialize the OPointLight object.
 * Created by roberto on 05.02.16.
 */
public class SPointLight extends SLight {

    private static final long serialVersionUID = 1L;

    /**
     * Represents the position of the Light
     */
    private final double px;
    private final double py;
    private final double pz;

    /**
     * Instantiates a new SOrenNayarMaterial Object.
     *
     * @param red part of the color
     * @param green part of the color
     * @param blue part of the color
     * @param px is the position of the Light
     * @param py is the position of the Light
     * @param pz is the position of the Light
     * @param castShadows if the light cast shadows
     * @param photons is the photons cast
     * @param size of the pattern
     * @param subdiv Square number of the Rays
     * @param name of the light
     */
    public SPointLight(final double red, final double green, final double blue,
                       final double px, final double py, final double pz,
                       final boolean castShadows, final int photons,
                       final double size, final int subdiv, final String name) {
        super(red, green, blue, castShadows, photons, size, subdiv, name);
        this.px = px;
        this.py = py;
        this.pz = pz;
    }


    @Override
    public OPointLight generate() {
        OPointLight light = new OPointLight();

        light.color.set(new javafx.scene.paint.Color(red, green, blue, 1));
        light.castShadow.set(castShadows);
        light.photons.set(photons);
        light.patternSize.set(size);
        light.patternSubdiv.set(subdiv);
        light.px.set(px);
        light.py.set(py);
        light.pz.set(pz);
        light.name.set(name);

        return light;
    }
}
