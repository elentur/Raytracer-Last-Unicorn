package serializable.textures;

import observables.textures.AOTexture;
import serializable.SElement;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class STexture implements SElement, Serializable {

    private static final long serialVersionUID = 1L;
    final String name;
    /**
     * represents the scale value of the u axis
     */
    final double scaleU;
    /**
     * represents the scale value of the v axis
     */
    final double scaleV;
    /**
     * represents the offset value of the u axis
     */
    final double offsetU;
    /**
     * represents the offset value of the v axis
     */
    final double offsetV;
    /**
     * represents the rotation value
     */
    final double rotate;

    public STexture(final String name, final double scaleU, final double scaleV,
                    final double offsetU, final double offsetV, final double rotate) {
        this.name = name;
        this.scaleU = scaleU;
        this.scaleV = scaleV;
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.rotate = rotate;
    }

    @Override
    public abstract AOTexture generate();
}
