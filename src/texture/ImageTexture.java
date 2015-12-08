package texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import utils.Color;

import java.io.File;

/**
 * Created by roberto on 03/12/15.
 */
public class ImageTexture implements Texture {

    public Image image;

    public ImageTexture(final String path) {
        this.image = new Image(new File(path).toURI().toString());
    }

    @Override
    public Color getColor(double u, double v) {

        int x = (int)(u*image.getWidth());
        int y = (int)(v*image.getHeight());

        PixelReader reader = image.getPixelReader();
        javafx.scene.paint.Color fxColor = reader.getColor(x,y);
        return new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());
    }
}
