package serializable.textures;

import javafx.scene.paint.Color;
import observables.textures.OSingleColorTexture;
import serializable.SElement;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SSingleColorTexture extends STexture implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final utils.Color color;

    public SSingleColorTexture(final utils.Color color, final String name) {
        super("");
        this.color=color;
        this.name = name;
    }


    @Override
    public OSingleColorTexture generate() {
        OSingleColorTexture t = new OSingleColorTexture(new Color(color.r,color.g,color.b,1));
        t.name.setValue(name);
        return t;
    }
}
