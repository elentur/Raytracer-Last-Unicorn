package images;


import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * Created by roberto on 10/10/15.
 */
public class ImageRendering implements Runnable {

    private BorderPane root;

    public ImageRendering(BorderPane root) {
        this.root = root;
    }

    public void run(){
        Platform.runLater(new Runnable() {

            public void run() {

                ImageView imageView = new ImageView();

                WritableImage wImage = new WritableImage((int) root.getWidth(), (int) root.getHeight());

                PixelWriter pixelWriter = wImage.getPixelWriter();

                for (int readY = 0; readY < wImage.getHeight(); readY++){
                    for (int readX = 0; readX < wImage.getWidth(); readX++) {
                        pixelWriter.setColor(readX, readY, getColor(readX, readY));

                        imageView.setImage(wImage);
                        root.setCenter(imageView);
                    }
                }

                /*imageView.setImage(wImage);
                root.setCenter(imageView);*/
            }
        });
    }

    private Color getColor(int readX, int readY) {
        return (readX == readY) ? Color.RED : Color.BLACK;
    }
}
