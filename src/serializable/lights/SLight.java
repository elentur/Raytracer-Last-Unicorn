package serializable.lights;

import observables.lights.AOLight;
import serializable.SElement;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 06.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SLight implements SElement,Serializable {
    private static final long serialVersionUID = 1L;
    protected final double red;
    protected final double green;
    protected final double blue;
    protected final boolean castShadows;
    protected final int photons;
    protected final double size;
    protected final int subdiv;
    protected final String name;

    public SLight(final double red, final double green, final double blue,
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
