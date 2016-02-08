package serializable.lights;

import observables.lights.ODirectionalLight;

/**
 * Created by roberto on 05.02.16.
 */
public class SDirectionalLight extends SLight {

    private static final long serialVersionUID = 1L;
    private final double dx;
    private final double dy;
    private final double dz;

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
