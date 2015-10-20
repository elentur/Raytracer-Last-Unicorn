package images;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.WritableRenderedImage;

/**
 * @author roberto
 * @
 */
public class MultiThreadRender extends Task {

    private PixelWriter pixelWriter;
    private WritableImage wImage;
    private int cores;
    private int core;

    public MultiThreadRender(WritableImage wImage, int cores, int core) {
        this.wImage = wImage;
        this.pixelWriter =  wImage.getPixelWriter();
        this.cores = cores;
        this.core = core;
    }

    @Override
    protected Object call() throws Exception {

        final double img_height = wImage.getHeight();
        final double img_width = wImage.getWidth();
        final double img_size = img_height * img_width;

        final double max_height = img_height / cores;

        for (double readY = ( max_height * core) - max_height; readY < max_height * core; readY++) {

            for (double readX = 0; readX < img_width; readX++) {

                final double finalReadX = readX, finalReadY = readY;

                Platform.runLater(new Runnable() {
                    public void run() {

                        pixelWriter.setColor((int) finalReadX, (int) finalReadY, getColor(finalReadX, finalReadY));

                        updateProgress(ImageSaver.counter++, img_size);
                        updateTime(img_size);
                    }
                });

                if (isCancelled()) {
                    break;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException interrupted) {
                    System.out.println("Abbruch");
                }
            }

        }
        return null;
    }

    private void updateTime(double img_size) {

        long elapsedTime = (System.currentTimeMillis() - ImageSaver.startTime) / 1000;

        // counter / elapsedTime = maxCounter / estimatedTime

        long estimatedTime = (long)((double)elapsedTime / ImageSaver.counter * (img_size));

        updateMessage("elapsed Time: " + elapsedTime + "sec. estimated Time: " + estimatedTime + " sec.");
    }

    private Color getColor(double readX, double readY) {
        return (readX == readY) ? Color.RED : Color.BLACK;
    }
}
