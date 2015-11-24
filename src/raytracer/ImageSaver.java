package raytracer;

import UI.Dialog;
import UI.*;
import camera.Camera;
import camera.PerspectiveCamera;
import geometries.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import light.Light;
import light.PointLight;
import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import material.LambertMaterial;
import material.OrenNayarMaterial;
import material.PhongMaterial;
import material.SingleColorMaterial;
import utils.*;
import utils.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * This program generates a black image witch a red diagonal line. This picture can be saved as a png.
 * To achieve this the program generates an empty image and fill it pixel by pixel with the right material.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class ImageSaver extends Application {
    /**
     * threadBreak exits all Threads.
     */
    private boolean threadBreak = false;
    /**
     * represents the width of the image.
     */
    public final static IntegerProperty imgWidth = new SimpleIntegerProperty(640);
    /**
     * represents the height of the image.
     */
    public final static IntegerProperty imgHeight = new SimpleIntegerProperty(480);

    /**
     * Represents the Pattern of the Renderprocess.
     */
    public static int pattern = 0;
    /**
     * Represents the number of Cores.
     */
    public static int cores = 1;
    /**
     * The Image in that the program draw.
     */
    private WritableImage writableImage;


    /**
     * The ImageView where the image is shown.
     */
    private final ImageView image = new ImageView();

    /**
     * represents the Scene with all its lights and geometries.
     */
    private static World world;

    /**
     * Represents the Camera of this scene.
     */
    private static Camera camera;
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
    private final DoubleProperty progress = new SimpleDoubleProperty(0);
    /**
     * represents the start time of the render process.
     */
    private long startTime = 0;

    public static World getWorld() {
        return world;
    }

    public static void setWorld(final World world) {
        ImageSaver.world = world;
    }

    public static void setCamera(final Camera camera) {
        ImageSaver.camera = camera;
    }

    public static Camera getCamera() {
        return camera;
    }

    /*private void testScene(){
        world = new World(new Color(0,0,0),new Color(0.1,0.1,0.1));
        camera = new PerspectiveCamera(new Point3(0,0,0),new Vector3(0,0,-1), new Vector3(0,1,0), Math.PI/4);
        Geometry obj = new Sphere(new Point3(0,0,-3),0.5, new LambertMaterial(new Color(1,0,0)));
        Light light = new PointLight(new Color(1,1,1), new Point3(5,5,5));
        world.geometries.add(obj);
        world.lights.add(light);
    }*/

    private void testScene(){
        world = new World(new Color(0,0,0),new Color(0.0,0.0,0.0));
        camera = new PerspectiveCamera(new Point3(0,0,2),new Vector3(0,0,-1), new Vector3(0,1,0), Math.PI/4);
        Light light = new PointLight(new Color(1,1,1), new Point3(0,0,10));
        world.geometries.add( new Sphere(
                new Point3(-1.5,0,-3),
                0.5,
                new LambertMaterial(new Color(1,0,0))
        ));
        world.geometries.add( new Sphere(
                new Point3(0,0,-3),
                0.5,
                new OrenNayarMaterial(new Color(1,0,0),0.3)
        ));
        world.geometries.add( new Sphere(
                new Point3(1.5,0,-3),
                0.5,
                new OrenNayarMaterial(new Color(1,0,0),0.6)
        ));
        world.lights.add(light);
    }

    /**
     * The Javafx start class.
     *
     * @param primaryStage The PrimaryStage of this program.
     * @see javafx.stage.Stage
     */
    @Override
    public void start(final Stage primaryStage) {

        loadConfig();
        testScene();

        primaryStage.setScene(setScene(primaryStage));
        primaryStage.show();
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
     * Initialize the GuiElements and set all necessary listeners.
     *
     * @param stage The PrimaryStage of this program.
     * @return The Scene for this PrimaryStage.
     */
    private Scene setScene(final Stage stage) {
        if (stage == null) throw new IllegalArgumentException("Stage can't be null");
        final int elementsHeight = 80;
        final Menu btnFile = new Menu("File");
        final MenuItem btnSave = new MenuItem("Save Rendered Image");
        final MenuItem btnSaveScene = new MenuItem("Save Scene");
        final MenuItem btnLoadScene = new MenuItem("Load Scene");
        final Menu btnEdit = new Menu("Edit");
        final MenuItem btnObjects = new MenuItem("Objects");
        final Menu btnCreate = new Menu("Create");
        final MenuItem btnNewScene = new MenuItem("New Scene");
        final MenuItem btnNewCamera = new MenuItem("New Camera");
        final MenuItem btnNewPlane = new MenuItem("Add new Plane");
        final MenuItem btnNewSphere = new MenuItem("Add new Sphere");
        final MenuItem btnNewCube = new MenuItem("Add new Cube");
        final MenuItem btnNewTriangle = new MenuItem("Add new Triangle");
        final MenuItem btnNewOBJ = new MenuItem("Add new OBJ");
        final Menu btnRendering = new Menu("Rendering");
        final MenuItem btnRender = new MenuItem("Render");
        final MenuItem btnStopRender = new MenuItem("Stop Render");
        final MenuItem btnSettings = new MenuItem("Rendersettings");
        final MenuBar menubar = new MenuBar();
        final Label lblTime = new Label();

        final ScrollPane scrollPane = new ScrollPane(image);
        final ProgressBar progressBar = new ProgressBar(0);
        final StringProperty resolution = new SimpleStringProperty();
        final VBox statusPane = new VBox(progressBar, lblTime);
        final BorderPane root = new BorderPane();
        root.setTop(menubar);
        root.setCenter(scrollPane);
        root.setBottom(statusPane);

        final Scene scene = new Scene(root, imgWidth.get() + 10, imgHeight.get() + elementsHeight);

        scene.getStylesheets().add("css/rootStyle.css");

        btnFile.getItems().addAll(btnSaveScene, btnLoadScene, btnSave);
        btnEdit.getItems().addAll(btnObjects);
        btnCreate.getItems().addAll(btnNewScene, btnNewCamera,
                btnNewPlane, btnNewSphere, btnNewCube,
                btnNewTriangle, btnNewOBJ);
        btnRendering.getItems().addAll(btnRender, btnStopRender, btnSettings);
        menubar.getMenus().addAll(btnFile, btnEdit, btnCreate, btnRendering);

        btnSave.setOnAction(a -> IO.saveImage(stage, image.getImage()));
        btnSaveScene.setOnAction(a -> IO.saveScene(stage, world, camera));
        btnLoadScene.setOnAction(a -> IO.loadScene(stage));
        btnObjects.setOnAction(a -> new EditObjects());
        btnNewScene.setOnAction(a -> new NewWorldStage());
        btnNewCamera.setOnAction(a -> new NewCameraStage(null));
        btnNewPlane.setOnAction(a -> new NewPlaneStage(null));
        btnNewSphere.setOnAction(a -> new NewSphereStage(null));
        btnNewCube.setOnAction(a -> new NewCubeStage(null));
        btnNewTriangle.setOnAction(a -> new NewTriangleStage(null));
        btnNewOBJ.setOnAction(a -> new NewOBJStage());
        btnRender.setOnAction(a -> render());
        btnStopRender.setOnAction(a -> stopRender());
        btnSettings.setOnAction(a -> new RenderSettingsStage());
        actualProgress.addListener(a -> lblTime.setText(setStatus()));

        //Bindings
        maxProgress.bind(imgHeight.multiply(imgWidth));
        maxProgress.addListener(a -> {
            actualProgress.set(0);

        });
        progress.bind(actualProgress.divide(maxProgress));
        progressBar.progressProperty().bind(progress);
        progressBar.prefWidthProperty().bind(scene.widthProperty());

        scrollPane.minViewportHeightProperty().bind(image.fitHeightProperty());
        scrollPane.minViewportWidthProperty().bind(image.fitWidthProperty());

        resolution.bind(Bindings.concat("Last-Unicorn Ray-Tracer   Resolution: ", imgWidth, " x ", imgHeight));

        stage.titleProperty().bind(resolution);
        scene.setOnKeyPressed(a -> {
            if (a.getCode() == KeyCode.ESCAPE) stopRender();
        });
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Generates as String that gives information about the render process:
     * Percentage done: the actually rendered part in percent.
     * Time Elapsed: the actually elapsed time.
     * Estimated Time: the estimated time.
     *
     * @return returns the String with all information.
     */
    private String setStatus() {
        if (actualProgress.get() > 0) {
            final int timeElapsed = (int) ((System.currentTimeMillis() - startTime) / 1000);
            final int estimatedTime = (int) ((timeElapsed / actualProgress.get()) * maxProgress.get());
            if (actualProgress.get() < maxProgress.get()) {
                return (
                        "Percentage done: " + (int) (100 * progress.get()) + "%   " +
                                "Time Elapsed: " + timeElapsed + " sec" +
                                "    Estimated Time: " + (estimatedTime - timeElapsed) + "sec");
            } else {
                return (
                        "Percentage done: " + (100) + "%   " +
                                "Total Time: " + timeElapsed + " sec");
            }

        }
        return "";
    }

    /**
     * set a breaking variable to stop all render-threads.
     */
    private void stopRender() {
        threadBreak = true;
    }

    /**
     * Begins the render process
     */
    private void render() {
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
        writableImage = new WritableImage(imgWidth.get(), imgHeight.get());

        final PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int x = 0; x < imgWidth.get(); x++) {
            for (int y = 0; y < imgHeight.get(); y++) {
                pixelWriter.setColor(x, y, javafx.scene.paint.Color.MIDNIGHTBLUE);
            }
        }
        image.setImage(writableImage);

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
                        draw(x, y, writableImage.getPixelWriter());
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

