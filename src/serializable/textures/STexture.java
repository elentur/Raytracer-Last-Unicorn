package serializable.textures;

import observables.textures.AOTexture;
import serializable.SElement;

import java.io.Serializable;

/**
 * Abstract wrapper class for all serializable texture objects.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class STexture implements SElement, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the texture
     */
    final String name;

    /**
     *
     * @param name of the texture
     */
    STexture(final String name) {
        this.name = name;
    }

    @Override
    public abstract AOTexture generate();
}
