package serializable.textures;

import controller.AController;
import observables.textures.OImageTexture;

/**
 * Wrapper class to serialize the OImageTexture object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SImageTexture extends STexture {

    private static final long serialVersionUID = 1L;

    /**
     * The path of the images as String
     */
    private final String path;

    /**
     * Instantiates a new SImageTexture Object.
     * @param path of the image.
     * @param name name of the texture.
     */
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
