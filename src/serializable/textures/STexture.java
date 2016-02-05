package serializable.textures;

import observables.textures.AOTexture;
import serializable.SElement;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class STexture implements SElement {

    private static final long serialVersionUID = 1L;
    protected final String name;

    public STexture(final String name){
        this.name=name;
    }

    @Override
    public abstract AOTexture generate();
}
