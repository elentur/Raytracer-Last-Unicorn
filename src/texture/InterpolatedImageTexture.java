package texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import utils.Color;

import java.io.File;

/**
 * @author  Robert Dziuba on 03/12/15.
 */
public class InterpolatedImageTexture extends Texture {

    public Image image;

    public InterpolatedImageTexture(final String path) {
        this(path,1,1,1,1);
    }

    public InterpolatedImageTexture(final String path, final double scaleU, final double scaleV, final double offsetU, final double offsetV) {
        super(scaleU,scaleV,offsetU,offsetV);
        File file = new File(path);

        if(!file.exists()) throw new IllegalArgumentException("Image "+path+" not found.");

        this.image = new Image(file.toURI().toString());
    }

    @Override
    public Color getColor(double u, double v) {

        u = (u/ScaleU + OffsetU) % 1.0;
        v = (v/ScaleV + OffsetV) % 1.0;

        if(u < 0) u += 1.0;
        if(v < 0) v += 1.0;


        u = u * (image.getWidth() - 1);
        v = v * (image.getHeight() - 1);

        int x = (int) Math.floor(u);
        int y = (int) Math.floor(v);

        double u_ratio = u - x;
        double v_ratio = v - y;

        double u_opposite = 1 - u_ratio;
        double v_opposite = 1 - v_ratio;

        PixelReader reader = image.getPixelReader();
        javafx.scene.paint.Color fxColor;

        fxColor = reader.getColor(x, y);
        Color color1 = new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());

        fxColor = reader.getColor(x + 1, y);
        Color color2 = new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());

        fxColor = reader.getColor(x, y + 1);
        Color color3 = new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());

        fxColor = reader.getColor(x + 1, y + 1);
        Color color4 = new Color(fxColor.getRed(), fxColor.getGreen(), fxColor.getBlue());

        // (tex[x][y] * u_opposite  + tex[x+1][y]   * u_ratio) * v_opposite
        // +
        // (tex[x][y+1] * u_opposite  + tex[x+1][y+1] * u_ratio) * v_ratio;

        Color a = color1.mul(u_opposite).add(color2.mul(u_ratio));
        Color b = a.mul(v_opposite);
        Color c = color3.mul(u_opposite).add(color4.mul(u_ratio));
        Color d = c.mul(v_ratio);

        Color e = b.add(d);

        return e;

    }
}
