package images;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Created by roberto on 16.10.15.
 */
public class Render {

    private WritableImage wImage;
    private PixelWriter pixelWriter;

    public Render(WritableImage wImage) {
        this.wImage = wImage;
        this.pixelWriter =  wImage.getPixelWriter();
    }

    public void start(){
        for (double readY = 0; readY < wImage.getHeight(); readY++) {
            for (double readX = 0; readX < wImage.getWidth(); readX++) {
                pixelWriter.setColor((int) readX, (int) readY, getColor(readX, readY));
            }
        }
    }

    private Color getColor(double readX, double readY) {
        return (readX == readY) ? javafx.scene.paint.Color.RED : javafx.scene.paint.Color.BLACK;
    }
}
