package observables.textures;

import javafx.scene.paint.Color;
import serializable.textures.SSingleColorTexture;
import texture.SingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * Creates a new OSingleColorTexture Object
 */
public class OSingleColorTexture extends AOTexture {


    public OSingleColorTexture(Color color) {
        name.set("Single Color Texture");
        path.set("");
        this.color.setValue(color);
    }

    public void setColor(final utils.Color color) {
        this.color.setValue(new Color(color.r, color.g, color.b, 1));
    }

    @Override
    public SingleColorTexture generate() {
        return new SingleColorTexture(new utils.Color(color.get().getRed(), color.get().getGreen(), color.get().getBlue()));
    }

    @Override
    public SSingleColorTexture serialize() {
        return new SSingleColorTexture(
                color.get().getRed(),
                color.get().getGreen(),
                color.get().getBlue(),
                name.get()
        );
    }
}
