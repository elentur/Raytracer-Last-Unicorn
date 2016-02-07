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
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import utils.Color;
import utils.HDRFilter;
import utils.Ray;
import utils.World;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marcus Baetz on 25.11.2015.
 *
 * @author Marcus Bätz
 */
public class Raytracer {

    public boolean rendering = false;
    private HDRFilter hdrFilter;
    /**
     * threadBreak exits all Threads.
     */
    private boolean threadBreak = false;
    /**
     * represents the width of the image.
     */
    public final IntegerProperty imgWidth = new SimpleIntegerProperty(640);
    /**
     * represents the height of the image.
     */
    public final IntegerProperty imgHeight = new SimpleIntegerProperty(480);

    /**
     * Represents the Pattern of the Renderprocess.
     */
    public int pattern = 0;
    /**
     * Represents the number of Cores.
     */
    public int cores = 1;
    /**
     * Represents if hdr Render.
     */
    public boolean hdr = false;
    /**
     * represents the Scene with all its lights and geometries.
     */
    private World world;

    /**
     * Represents the Camera of this scene.
     */
    private Camera camera;

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
    private Integer quadrantCounter;

    /**
     * represent the maximum pixels that have to be rendered.
     */
    private final DoubleProperty maxProgress = new SimpleDoubleProperty(imgHeight.get() * imgWidth.get());

    /**
     * represents the render progress in a range between 0.0 and 1.0.
     */
    public DoubleProperty progress = new SimpleDoubleProperty(0);

    /**
     * represent the recursion depth of the reflection.
     */
    public int recursionDepth = 0;
    /**
     * represents the index of Refraction of the empty room of the scene
     */
    public double iOR = 1.0003;

    private boolean ambientOcclusion = false;

    /**
     * represents the start time of the render process.
     */
    private long startTime = 0;
    private boolean def = false;
    private WritableImage img;
    private int running;


    public Raytracer(boolean loadConfig) {
        world = new World(new Color(0, 0, 0), new Color(0.2, 0.2, 0.2), ambientOcclusion);
        if (loadConfig) loadConfig();
        else setDefault();
        maxProgress.bind(imgHeight.multiply(imgWidth));
    }

    private void setDefault() {
        cores = 1;
        pattern = 0;
        imgWidth.set(80);
        imgHeight.set(80);
        hdr = false;
        def = true;
        ambientOcclusion = false;
    }


    public World getWorld() {
        return world;
    }

    public void setWorld(final World world) {
        this.world = world;
    }

    public void setCamera(final Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }


    /**
     * on start old configuration will be loaded.
     */
    private void loadConfig() {
        Map<String, String> input = IO.readFile();
        if (input.size() > 0) {
            try {
                hdr = input.get("hdr").equals("true");
                ambientOcclusion = input.get("ambient").equals("true");
                cores = Integer.parseInt(input.get("cores"));
                pattern = Integer.parseInt(input.get("pattern"));
                imgWidth.set((int) (Double.parseDouble(input.get("width"))));
                imgHeight.set((int) (Double.parseDouble(input.get("height"))));
                recursionDepth = (int) (Double.parseDouble(input.get("recursion")));
                iOR = (Double.parseDouble(input.get("ior")));
                Color back = new Color(Double.parseDouble(input.get("backgroundColorRed")),
                        Double.parseDouble(input.get("backgroundColorGreen")),
                        Double.parseDouble(input.get("backgroundColorBlue")));
                Color ambient = new Color(Double.parseDouble(input.get("ambientColorRed")),
                        Double.parseDouble(input.get("ambientColorGreen")),
                        Double.parseDouble(input.get("ambientColorBlue")));
                World w = world;
                world = new World(back, ambient, ambientOcclusion);
                world.lights.addAll(w.lights);
                world.geometries.addAll(w.geometries);
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
        if (progress.get() > 0) {
            final int timeElapsed = (int) ((System.currentTimeMillis() - startTime) / 1000);
            final int estimatedTime = (int) (timeElapsed / progress.get());
            if (progress.get() < 1) {
                return (
                        "Percentage done: " + (int) (100 * progress.get()) + "%   " +
                                "Time Elapsed: " + timeElapsed / 60 + ":" + timeElapsed % 60 + " min" +
                                "    Estimated Time: " + (estimatedTime - timeElapsed) / 60 + ":" + (estimatedTime - timeElapsed) % 60 + "min");
            } else {
                return (
                        "Percentage done: " + (100) + "%   " +
                                "Total Time: " + timeElapsed / 60 + ":" + timeElapsed % 60 + " min");
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
     *
     * @param image The image the raytracer will render into
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
            prepare();
            image.setImage(img);
            threadBreak = false;
            quadrantCounter = 0;
            final Point[] quadrant = newQuadrants(pattern);
            startTime = System.currentTimeMillis();
            if (hdr) hdrFilter = new HDRFilter(imgWidth.get(), imgHeight.get());
            Task rt;
            if (def) {
                rt = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        rendering=true;
                        final PixelWriter pixelWriter = img.getPixelWriter();
                        final PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
                        final byte[] imageData = new byte[imgWidth.get() * imgHeight.get() * 3];
                        for (int y = 0; y < imgHeight.get(); y++) {
                            for (int x = 0; x < imgWidth.get(); x++) {
                                final byte[] b = draw(x, y);
                                for (int i = 0; i < 3; i++) {
                                    imageData[y * imgWidth.get() * 3 + x * 3 + i] = b[i];
                                }
                            }
                        }
                        pixelWriter.setPixels(0, 0, imgWidth.get(), imgHeight.get(), pixelFormat, imageData, 0,
                                imgWidth.get() * 3);
                        rendering=false;
                        return null;
                    }
                };


            } else {
                rt = new RenderTask(quadrant);
                progress.bind(rt.progressProperty());
            }
            Thread mainRenderThread = new Thread(rt, 0 + "");
            mainRenderThread.setDaemon(true);
            mainRenderThread.start();
        }
    }

    /**
     * Generates a render pattern
     *
     * @param pattern typ of pattern
     * @return paatern array of the renderer
     */
    private Point[] newQuadrants(final int pattern) {
        Point[] q;

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
        } else {
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
        WritableImage oldImg = img;
        img = new WritableImage(imgWidth.get(), imgHeight.get());
        final PixelWriter pixelWriter = img.getPixelWriter();
        if (!def) {

            for (int x = 0; x < imgWidth.get(); x++) {
                for (int y = 0; y < imgHeight.get(); y++) {
                    pixelWriter.setColor(x, y, javafx.scene.paint.Color.MIDNIGHTBLUE);
                }
            }
        } else {
            if (oldImg != null) img = oldImg;
        }

    }

    /**
     * Generates for the pixel the specific material.
     *
     * @param x represents the x coordinate
     * @param y represents the y coordinate
     */
    private byte[] draw(final int x, final int y) {
        if (x < 0 || x > imgWidth.get() - 1) throw new IllegalArgumentException("x has to be between 0 and width -1.");
        if (y < 0 || y > imgHeight.get() - 1)
            throw new IllegalArgumentException("y has to be between 0 and height -1.");


        utils.Color c = new Color(0, 0, 0);
        Set<Ray> rays;
        synchronized (camera) {
            rays = camera.rayFor(imgWidth.get(), imgHeight.get(), x, y);
        }
        for (Ray r : rays) {
            c = c.add(world.hit(r));
        }

        c = c.mul(1.0 / rays.size());

        if (hdr) hdrFilter.filter(c, x, y);
        if (c.r > 1) c = new utils.Color(1.0, c.g, c.b);
        if (c.g > 1) c = new utils.Color(c.r, 1.0, c.b);
        if (c.b > 1) c = new utils.Color(c.r, c.g, 1.0);
        byte[] data = new byte[3];
        data[0] = (byte) (c.r * 255);
        data[1] = (byte) (c.g * 255);
        data[2] = (byte) (c.b * 255);

        return data;
    }

    /**
     * The Task-class for the render-threads.
     *
     * @param <Empty>
     */
    private class RenderTask<Empty> extends Task<Empty> {
        private final Point[] quadrant;

        public RenderTask(final Point[] quadrant) {
            this.quadrant = quadrant;
        }

        @Override
        protected Empty call() throws Exception {
            PixelWriter pixelWriter = img.getPixelWriter();
            PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
            byte[] imageData = new byte[imgWidth.get() * imgHeight.get() * 3];
            final int[] threadProgress = new int[cores];
            renderThread(imageData, threadProgress, quadrant);
            while (running > 0) {
                if (threadBreak) return null;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pixelWriter.setPixels(0, 0, imgWidth.get(), imgHeight.get(), pixelFormat, imageData, 0,
                        imgWidth.get() * 3);
                int p = 0;
                for (int i : threadProgress) {
                    p += i;

                }
                this.updateProgress(p, maxProgress.get());
                if (progress.get() == 1) break;
            }
            if (threadBreak) return null;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (hdr) hdrFilter.getImage(imageData);
            pixelWriter.setPixels(0, 0, imgWidth.get(), imgHeight.get(), pixelFormat, imageData, 0,
                    imgWidth.get() * 3);
            return null;
        }
    }

    private void renderThread(byte[] imageData, final int[] threadProgress, final Point[] quadrant) {
        for (int i = 0; i < imageData.length; i++) {

            if (i % 3 == 0) imageData[i] = (byte) (javafx.scene.paint.Color.MIDNIGHTBLUE.getRed() * 255);
            if (i % 3 == 1) imageData[i] = (byte) (javafx.scene.paint.Color.MIDNIGHTBLUE.getGreen() * 255);
            if (i % 3 == 2) imageData[i] = (byte) (javafx.scene.paint.Color.MIDNIGHTBLUE.getBlue() * 255);
        }
        for (int i = 0; i < cores; i++) {
            threadProgress[i] = 0;
            final int num = i;
            Thread t = new Thread(() -> {
                while (quadrantCounter < quadrant.length) {
                    int bHeight = imgHeight.get() / tileY;
                    int bWidth = imgWidth.get() / tileX;
                    if (bWidth == 0) bWidth = 1;
                    if (bHeight == 0) bHeight = 1;
                    int quadrantX = 0;
                    int quadrantY = 0;
                    synchronized (quadrantCounter) {
                        if (quadrantCounter < quadrant.length) {
                            quadrantX = quadrant[quadrantCounter].x;
                            quadrantY = quadrant[quadrantCounter].y;
                        }
                        quadrantCounter++;
                    }
                    final int fromX = quadrantX * bWidth;
                    int toX = (quadrantX + 1) * bWidth;
                    final int fromY = quadrantY * bHeight;
                    int toY = (quadrantY + 1) * bHeight;
                    if (toY > imgHeight.get()) toY = imgHeight.get();
                    if (toX > imgWidth.get()) toX = imgWidth.get();
                    if (toY < imgHeight.get() && quadrantY == tileY - 1) toY = imgHeight.get();
                    if (toX < imgWidth.get() && quadrantX == tileX - 1) toX = imgWidth.get();


                    byte[] a = new byte[(toX - fromX) * (toY - fromY) * 3];
                    int count = 0;
                    for (int y = fromY; y < toY; y++) {
                        for (int x = fromX; x < toX; x++) {
                            if (threadBreak) break;
                            threadProgress[num]++;
                            byte[] b = draw(x, y);
                            System.arraycopy(b, 0, a, count, 3);
                            count += 3;
                        }
                        if (threadBreak) break;
                    }
                    if (threadBreak) break;
                    for (int j = 0; j < toY - fromY; j++) {
                        System.arraycopy(a, (toX - fromX) * j * 3, imageData, (fromY + j) * imgWidth.get() * 3 + fromX * 3, a.length / (toY - fromY));
                    }
                }
                running--;
            }, "tracingThread " + i);
            t.setDaemon(true);
            t.start();
            running++;
        }

    }
}
