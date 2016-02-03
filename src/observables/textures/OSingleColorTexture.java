package observables.textures;

import javafx.scene.paint.Color;
import texture.SingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSingleColorTexture extends AOTexture {

    public Color color;

    public OSingleColorTexture(Color color) {
        name.set("Single Color Texture");
        path.set("");
        this.color = color;
    }

    @Override
    public SingleColorTexture generate() {
        return new SingleColorTexture(new utils.Color(color.getRed(),color.getGreen(),color.getBlue()));
    }
}
