package serializable.textures;

import controller.AController;
import observables.textures.OInterpolatedImageTexture;

/**
 * Wrapper class to serialize the OInterpolatedImageTexture object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SInterpolatedImageTexture extends STexture {

    private static final long serialVersionUID = 1L;

    /**
     * The path of the images as String
     */
    private final String path;

    /**
     * Instantiates a new SInterpolatedImageTexture Object.
     * @param path of the image.
     * @param name name of the texture.
     */
    public SInterpolatedImageTexture(final String path, final String name) {
        super(name);
        this.path = path;
    }

    @Override
    public OInterpolatedImageTexture generate() {
        OInterpolatedImageTexture t = new OInterpolatedImageTexture(path);
        t.name.setValue(name);
        AController.textureList.add(t);
        return t;
    }
}
