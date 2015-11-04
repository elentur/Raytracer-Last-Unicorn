package raytracer;

import UI.*;
import camera.Camera;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.World;

/**
 * This program generates a black image witch a red diagonal line. This picture can be saved as a png.
 * To achieve this the program generates an empty image and fill it pixel by pixel with the right color.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class ImageSaver extends Application {
    /**
     * Represents the width in pixels of the image.
     */
    private int imgWidth = 640;
    /**
     * Represents the height in pixels of the image.
     */
    private int imgHeight = 480;
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

    public static World getWorld() {
        return world;
    }

    public static void setWorld(final World world) {
        ImageSaver.world = world;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static void setCamera(final Camera camera) {
        ImageSaver.camera = camera;
    }

    /**
     * The Javafx start class.
     *
     * @param primaryStage The PrimaryStage of this program.
     * @see javafx.stage.Stage
     */
    @Override
    public void start(final Stage primaryStage) {

        primaryStage.setTitle("Last-Unicorn Ray-Tracer");
        primaryStage.setScene(setScene(primaryStage));
        primaryStage.show();
        //render();
    }

    /**
     * Initialize the GuiElements and set all necessary listeners.
     *
     * @param stage The PrimaryStage of this program.
     * @return The Scene for this PrimaryStage.
     */
    private Scene setScene(final Stage stage) {
        if (stage == null) throw new IllegalArgumentException("Stage can't be null");
        final int elementsHeight = 25;
        final Menu btnFile = new Menu("File");
        final MenuItem btnSave = new MenuItem("Save Rendered Image");
        final MenuItem btnSaveScene = new MenuItem("Save Scene");
        final MenuItem btnLoadScene = new MenuItem("Load Scene");
        final Menu btnCreate = new Menu("Create");
        final MenuItem btnNewScene = new MenuItem("New Scene");
        final MenuItem btnNewCamera = new MenuItem("New Camera");
        final MenuItem btnNewPlane = new MenuItem("Add new Plane");
        final MenuItem btnNewSphere = new MenuItem("Add new Sphere");
        final MenuItem btnNewCube = new MenuItem("Add new Cube");
        final MenuItem btnNewTriangle = new MenuItem("Add new Triangle");
        final MenuItem btnNewOBJ = new MenuItem("Add new OBJ");
        final MenuBar menubar = new MenuBar();
        final VBox root = new VBox(menubar, image);
        final Scene scene = new Scene(root, imgWidth, imgHeight + elementsHeight);

        btnFile.getItems().addAll(btnSaveScene,btnLoadScene,btnSave);
        btnCreate.getItems().addAll(btnNewScene,btnNewCamera,
                btnNewPlane, btnNewSphere,btnNewCube,
                btnNewTriangle, btnNewOBJ);
        menubar.getMenus().addAll(btnFile,btnCreate);

        btnSave.setOnAction(a -> IO.saveImage(stage,writableImage));
        btnSaveScene.setOnAction(a -> IO.saveScene(stage,world));
        btnLoadScene.setOnAction(a -> IO.loadScene(stage));
        btnNewScene.setOnAction(a -> new NewWorldStage());
        btnNewCamera.setOnAction(a -> new NewCameraStage());
        btnNewPlane.setOnAction(a -> new NewPlaneStage());
        btnNewSphere.setOnAction(a -> new NewSphereStage());
        btnNewCube.setOnAction(a -> new NewCubeStage());
        btnNewTriangle.setOnAction(a -> new NewTriangleStage());
        btnNewOBJ.setOnAction(a -> new NewOBJStage());

        scene.widthProperty().addListener(a -> {
            imgWidth = ((int) scene.getWidth());
            render();
        });
        scene.heightProperty().addListener(a -> {
            imgHeight = ((int) (scene.getHeight() - elementsHeight));
            render();
        });
        return scene;
    }




    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Fills the image with the defined color for each pixel.
     */
    private void render() {
        writableImage = new WritableImage(imgWidth, imgHeight);
        image.setImage(writableImage);
        final PixelWriter myPixelWriter = writableImage.getPixelWriter();
        final double wh = (double) imgHeight / (double) imgWidth;
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                if (y == ((int) ((wh) * x))) myPixelWriter.setColor(x, y, Color.RED);
                else myPixelWriter.setColor(x, y, Color.BLACK);
            }
        }

    }


}

