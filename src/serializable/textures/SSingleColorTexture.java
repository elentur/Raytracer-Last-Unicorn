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
    private final double red;
    private final double green;
    private final double blue;

    public SSingleColorTexture(final double red, final double green, final double blue, final String name,
                               final double scaleU, final double scaleV,
                               final double offsetU, final double offsetV, final double rotate) {
        super("",scaleU,scaleV,offsetU,offsetV,rotate);
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.name = name;
    }


    @Override
    public OSingleColorTexture generate() {
        OSingleColorTexture t = new OSingleColorTexture(new Color(red, green, blue, 1));
        t.name.setValue(name);
        t.scaleV.setValue(scaleV);
        t.scaleU.setValue(scaleU);
        t.offsetU.setValue(offsetU);
        t.offsetV.setValue(offsetV);
        t.rotate.setValue(rotate);
        return t;
    }
}
