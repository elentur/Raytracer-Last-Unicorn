package texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import utils.Color;

import java.io.File;

/**
 * @author  Robert Dziuba on 03/12/15.
 */
public class ImageTexture extends Texture {

    public ImageTexture(final Image img) {
        this(img,1,1,0,0,0);
    }

    public ImageTexture(final Image img, final double scaleU, final double scaleV, final double offsetU, final double offsetV, final double rotate) {
        super(scaleU,scaleV,offsetU,offsetV,rotate,img);
        name = "Image Texture";
    }

    @Override
    public Color getColor(double u, double v) {

        double u1 = u*Math.cos(rotate) -v*Math.sin(rotate);
        v = u*Math.sin(rotate) +v*Math.cos(rotate);
        u=u1;
        u = (u/ scaleU + offsetU) % 1.0;
        v = (v/ scaleV + offsetV) % 1.0;

        if(u < 0) u += 1.0;
        if(v < 0) v += 1.0;

        int x = (int) (u*(image.getWidth()));
        int y = (int) (v*(image.getHeight()));

        PixelReader reader = image.getPixelReader();
        javafx.scene.paint.Color fxColor = reader.getColor(x,y);
        return new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        return false;

    }

    @Override
    public int hashCode() {
        return image != null ? image.hashCode() : 0;
    }
}
