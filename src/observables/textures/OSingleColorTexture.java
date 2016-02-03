package observables.textures;

import controller.AController;
import javafx.scene.paint.Color;
import observables.materials.AOMaterial;
import texture.SingleColorTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSingleColorTexture extends AOTexture {



    public OSingleColorTexture(Color color) {
        name.set("Single Color Texture");
        path.set("");
        this.color.setValue(color);

    }

    @Override
    public SingleColorTexture generate() {
        return new SingleColorTexture(new utils.Color(color.get().getRed(),color.get().getGreen(),color.get().getBlue()));
    }
}
