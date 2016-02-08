package serializable.textures;

import controller.AController;
import observables.textures.OImageTexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SImageTexture extends STexture {

    private static final long serialVersionUID = 1L;

    private final String path;

    public SImageTexture(final String path, final String name, final double scaleU, final double scaleV,
                         final double offsetU, final double offsetV, final double rotate) {
        super(name, scaleU, scaleV, offsetU, offsetV, rotate);
        this.path = path;
    }

    @Override
    public OImageTexture generate() {
        OImageTexture t = new OImageTexture(path);
        t.name.setValue(name);
        t.scaleV.setValue(scaleV);
        t.scaleU.setValue(scaleU);
        t.offsetU.setValue(offsetU);
        t.offsetV.setValue(offsetV);
        t.rotate.setValue(rotate);
        AController.textureList.add(t);
        return t;
    }
}
