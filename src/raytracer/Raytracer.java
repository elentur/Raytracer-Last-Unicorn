package raytracer;

import UI.Dialog;
import UI.IO;
import camera.Camera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import utils.Ray;
import utils.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Marcus Baetz on 25.11.2015.
 *
 * @author Marcus Bätz
 */
public class Raytracer {
    /**
     * threadBreak exits all Threads.
     */
    private boolean threadBreak = false;
    /**
     * represents the width of the image.
     */
    public final  IntegerProperty imgWidth = new SimpleIntegerProperty(640);
    /**
     * represents the height of the image.
     */
    public final  IntegerProperty imgHeight = new SimpleIntegerProperty(480);

    /**
     * Represents the Pattern of the Renderprocess.
     */
    public  int pattern = 0;
    /**
     * Represents the number of Cores.
     */
    public  int cores = 1;
    /**
     * represents the Scene with all its lights and geometries.
     */
    private  World world;

    /**
     * Represents the Camera of this scene.
     */
    private  Camera camera;
    /**
     * represents the render pattern.
     */
    private Point[] quadrant;

    /**
     * size of the area that is rendered from one core at a time;
     */
    private int tileX = 10;
    /**
     * size of the area that is rendered from one core at a time;
     */
    private int tileY = 10;
    /**
     * represent the last quadrant that is actually rendered.
     */
    private int quadrantCounter;

    /**
     * represent the maximum pixels that have to be rendered.
     */
    private final DoubleProperty maxProgress = new SimpleDoubleProperty(imgHeight.get() * imgWidth.get());
    /**
     * represents the actual rendered pixel number.
     */
    private final DoubleProperty actualProgress = new SimpleDoubleProperty(0);
    /**
     * represents the render progress in a range between 0.0 and 1.0.
     */
    public final DoubleProperty progress = new SimpleDoubleProperty(0);
    /**
     * represents the start time of the render process.
     */
    private long startTime = 0;

    private WritableImage img;





    public Raytracer( boolean loadConfig ){
        if(loadConfig)loadConfig();
        else setDefault();



        //Bindings
        maxProgress.bind(imgHeight.multiply(imgWidth));
        maxProgress.addListener(a -> {
            actualProgress.set(0);

        });
        progress.bind(actualProgress.divide(maxProgress));

    }

    private void setDefault() {
    }


    public  World getWorld() {
        return world;
    }

    public  void setWorld(final World world) {
        this.world = world;
    }

    public  void setCamera(final Camera camera) {
        this.camera = camera;
    }

    public  Camera getCamera() {
        return camera;
    }


    /**
     * on start old configuration will be loaded.
     */
    private void loadConfig() {
        Map<String, String> input = IO.readFile("settings.cfg");
        if (input.size() > 0) {
            try {

                cores = Integer.parseInt(input.get("cores"));
                pattern = Integer.parseInt(input.get("pattern"));
                imgWidth.set((int) (Double.parseDouble(input.get("width"))));
                imgHeight.set((int) (Double.parseDouble(input.get("height"))));
            } catch (Exception e) {
                System.out.println("ladefehler");
            }

        }
    }
    /**
     * Generates as String that gives information about the render process:
     * Percentage done: the actually rendered part in percent.
     * Time Elapsed: the actually elapsed time.
     * Estimated Time: the estimated time.
     *
     * @return returns the String with all information.
     */
    public String getStatus() {
        if (actualProgress.get() > 0) {
            final int timeElapsed = (int) ((System.currentTimeMillis() - startTime) / 1000);
            final int estimatedTime = (int) ((timeElapsed / actualProgress.get()) * maxProgress.get());
            if (actualProgress.get() < maxProgress.get()) {
                return (
                        "Percentage done: " + (int) (100 * progress.get()) + "%   " +
                                "Time Elapsed: " + timeElapsed/60 +":" + timeElapsed%60 + " sec" +
                                "    Estimated Time: " + (estimatedTime - timeElapsed)/60 +":" + (estimatedTime - timeElapsed)%60 + "sec");
            } else {
                return (
                        "Percentage done: " + (100) + "%   " +
                                "Total Time: " + timeElapsed/60 +":" + timeElapsed%60  + " sec");
            }

        }
        return "";
    }

    /**
     * set a breaking variable to stop all render-threads.
     */
    public void stopRender() {
        threadBreak = true;
    }

    /**
     * Begins the render process
     * @param image
     */
    public void render(ImageView image) {
        {

            stopRender();
            if (world == null) {
                Dialog dlg = new Dialog("No Scene created.");
                dlg.setNewText("There is no Scene to be rendered.");
                dlg.showAndWait();
                return;
            }
            if (camera == null) {
                Dialog dlg = new Dialog("No Camera created.");
                dlg.setNewText("There is no Camera in this scene.");
                dlg.showAndWait();
                return;
            }
            startTime = System.currentTimeMillis();
            prepare();
            image.setImage(img);
            actualProgress.set(0);
            threadBreak = false;
            quadrantCounter = 0;
            quadrant = newQuadrants(pattern);

            for (int i = 0; i < cores; i++) {
                new Thread(new RenderTask()).run();

            }
        }
    }

    /**
     * Generates a render pattern
     *
     * @param pattern typ of pattern
     * @return paatern array of the renderer
     */
    private Point[] newQuadrants(final int pattern) {
        Point[] q = null;
        if (pattern == 0) {
            tileX = 10;
            tileY = 10;
            int bSize = tileX / 2;
            int aSize = tileY / 2;
            q = new Point[tileX * tileY];

            int count = 0;
            for (int y = 0; y < tileY; y++) {
                for (int x = 0; x < bSize; x++) {
                    q[count] = new Point(x, y);
                    count++;
                }
            }

            for (int x = bSize; x < tileX; x++) {
                for (int y = aSize; y < tileY; y++) {
                    q[count] = new Point(x, y);
                    count++;
                }
            }
            for (int y = aSize - 1; y > -1; y--) {
                for (int x = bSize; x < tileX; x++) {
                    q[count] = new Point(x, y);
                    count++;
                }
            }
        } else {//if (pattern == 1) {
            tileY = imgHeight.get();
            tileX = imgWidth.get();


            q = new Point[tileX * tileY];
            java.util.List<Point> lp = new ArrayList<>();

            for (int y = 0; y < tileY; y++) {
                for (int x = 0; x < tileX; x++) {
                    lp.add(new Point(x, y));
                }
            }
            Collections.shuffle(lp);
            for (int i = 0; i < lp.size(); i++) {
                q[i] = lp.get(i);
            }
        }
        return q;
    }

    /**
     * Prepares the writableImage for rendering and sets its start-material.
     */
    private void prepare() {
        img = new WritableImage(imgWidth.get(), imgHeight.get());

        final PixelWriter pixelWriter = img.getPixelWriter();
        for (int x = 0; x < imgWidth.get(); x++) {
            for (int y = 0; y < imgHeight.get(); y++) {

                pixelWriter.setColor(x, y, javafx.scene.paint.Color.MIDNIGHTBLUE);
            }
        }
    }

    /**
     * Generates for the pixel the specific material.
     *
     * @param x           represents the x coordinate
     * @param y           represents the y coordinate
     * @param pixelWriter represents the pixelwriter of the writableimage
     */
    private void draw(final int x, final int y, PixelWriter pixelWriter) {
        if (pixelWriter == null) throw new IllegalArgumentException("pixelWriter must not be null.");
        if (x < 0 || x > imgWidth.get() - 1) throw new IllegalArgumentException("x has to be between 0 and width -1.");
        if (y < 0 || y > imgHeight.get() - 1)
            throw new IllegalArgumentException("y has to be between 0 and height -1.");

        Ray r = camera.rayFor(imgWidth.get(), imgHeight.get(), x, y);
        utils.Color c = world.hit(r);

        if (c.r > 1) c = new utils.Color(1.0, c.g, c.b);
        if (c.g > 1) c = new utils.Color(c.r, 1.0, c.b);
        if (c.b > 1) c = new utils.Color(c.r, c.g, 1.0);

        pixelWriter.setColor(x, y, new javafx.scene.paint.Color(c.r, c.g, c.b, 1.0));
    }

    /**
     * The Task-class for the render-threads.
     *
     * @param <Void>
     */
    private class RenderTask<Void> extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            int repeate = 1;
            if (pattern == 1) repeate = 300;
            for (int i = 0; i < repeate; i++) {


                int bHeight = imgHeight.get() / tileY;
                int bWidth = imgWidth.get() / tileX;
                if (bWidth == 0) bWidth = 1;
                if (bHeight == 0) bHeight = 1;
                final int quadrantX = quadrant[quadrantCounter].x;
                final int quadrantY = quadrant[quadrantCounter].y;
                quadrantCounter++;


                final int fromX = quadrantX * bWidth;
                int toX = (quadrantX + 1) * bWidth;
                final int fromY = quadrantY * bHeight;
                int toY = (quadrantY + 1) * bHeight;
                if (toY > imgHeight.get()) toY = imgHeight.get();
                if (toX > imgWidth.get()) toX = imgWidth.get();
                if (toY < imgHeight.get() && quadrantY == tileY - 1) toY = imgHeight.get();
                if (toX < imgWidth.get() && quadrantX == tileX - 1) toX = imgWidth.get();

                for (int y = fromY; y < toY; y++) {
                    for (int x = fromX; x < toX; x++) {
                        if (threadBreak) break;
                        actualProgress.set(actualProgress.get() + 1);
                        draw(x, y, img.getPixelWriter());
                    }
                }
            }
            return null;
        }

        @Override
        protected void succeeded() {
            if (quadrantCounter < quadrant.length) {
                new Thread(new RenderTask()).run();
            }
        }
    }
}
