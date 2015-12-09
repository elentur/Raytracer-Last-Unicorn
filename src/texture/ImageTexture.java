package texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import utils.Color;

import java.io.File;

/**
 * Created by roberto on 03/12/15.
 */
public class ImageTexture extends Texture {

    public Image image;

    public ImageTexture(final String path) {
        this(path,1,1,1,1);
    }

    public ImageTexture(final String path, final double scaleU, final double scaleV, final double offsetU, final double offsetV) {
        super(scaleU,scaleV,offsetU,offsetV);
        this.image = new Image(new File(path).toURI().toString());
    }

    @Override
    public Color getColor(double u, double v) {

        u = (u/ScaleU + OffsetU) % 1.0;
        v = (v/ScaleV + OffsetV) % 1.0;

        if(u < 0) u += 1.0;
        if(v < 0) v += 1.0;

        int x = (int) (u*(image.getWidth()));
        int y = (int) (v*(image.getHeight()));

        PixelReader reader = image.getPixelReader();
        javafx.scene.paint.Color fxColor = reader.getColor(x,y);
        return new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());
    }
}
