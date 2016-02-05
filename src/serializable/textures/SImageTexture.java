package serializable.textures;

import controller.AController;
import observables.AOElement;
import observables.textures.OImageTexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SImageTexture extends STexture {

    private static final long serialVersionUID = 1L;

    private final String path;

    public SImageTexture(final String path, final String name) {
        super(name);
        this.path = path;
    }

    @Override
    public OImageTexture generate() {
        OImageTexture t = new OImageTexture(path);
        t.name.setValue(name);
        AController.textureList.add(t);
        return t;
    }
}
