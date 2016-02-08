package serializable.textures;

import javafx.scene.paint.Color;
import observables.textures.OSingleColorTexture;
import serializable.SElement;

/**
 * Wrapper class to serialize the OSingleColorTexture object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SSingleColorTexture extends STexture implements SElement {

    private static final long serialVersionUID = 1L;

    /**
     * The value for the red part of the color
     */
    private final double red;
    /**
     * The value for the green part of the color
     */
    private final double green;
    /**
     * The value for the blue part of the color
     */
    private final double blue;

    /**
     * Instantiates a new SSingleColorTexture Object.
     *
     * @param red light of the color.
     * @param green light of the color.
     * @param blue light of the color.
     * @param name of the texture
     */
    public SSingleColorTexture(final double red, final double green, final double blue, final String name) {
        super(name);
        this.red = red;
        this.blue = blue;
        this.green = green;
    }


    @Override
    public OSingleColorTexture generate() {
        OSingleColorTexture t = new OSingleColorTexture(new Color(red, green, blue, 1));
        t.name.setValue(name);
        return t;
    }
}
