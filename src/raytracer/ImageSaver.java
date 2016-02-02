package raytracer;

import UI.*;
import controller.AController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * This program generates a black image witch a red diagonal line. This picture can be saved as a png.
 * To achieve this the program generates an empty image and fill it pixel by pixel with the right material.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class ImageSaver extends Application {

    /**
     * The ImageView where the image is shown.
     */
    public static final ImageView image = new ImageView();
   // public final static Raytracer raytracer = new Raytracer(true);


    private void testScene() {

    }

    /**
     * The Javafx start class.
     *
     * @param primaryStage The PrimaryStage of this program.
     * @see javafx.stage.Stage
     */
    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        primaryStage.setTitle("Unicorn RayTracer");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        ImageView image  = (ImageView)scene.lookup("#image");
        MenuItem menuItem = (MenuItem) ((MenuBar)scene.lookup("#menuBar")).getMenus().get(0).getItems().get(3);
        menuItem.disableProperty().bind(image.imageProperty().isNull());
        menuItem.setOnAction(a -> IO.saveImage(scene.getWindow(), image.getImage()));
        //testScene();
        primaryStage.setOnCloseRequest(a -> AController.raytracer.stopRender());

        /*primaryStage.setScene(setScene(primaryStage));

        primaryStage.setOnCloseRequest(a -> raytracer.stopRender());
        primaryStage.show();*/
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
        //final MenuItem btnNewScene = new MenuItem("New Scene");
        final MenuItem btnNewCamera = new MenuItem("New Camera");
        final MenuItem btnNewLight = new MenuItem("New Light");
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

        final Scene scene = new Scene(root, AController.raytracer.imgWidth.get() + 10, AController.raytracer.imgHeight.get() + elementsHeight);

        scene.getStylesheets().add("css/rootStyle.css");

        btnFile.getItems().addAll(btnSaveScene, btnLoadScene, btnSave);
        btnEdit.getItems().addAll(btnObjects);
        btnCreate.getItems().addAll( btnNewCamera, btnNewLight,
                btnNewPlane, btnNewSphere, btnNewCube,
                btnNewTriangle, btnNewOBJ);
        btnRendering.getItems().addAll(btnRender, btnStopRender, btnSettings);
        menubar.getMenus().addAll(btnFile, btnEdit, btnCreate, btnRendering);

        btnSave.disableProperty().bind(image.imageProperty().isNull());

        btnSave.setOnAction(a -> IO.saveImage(stage, image.getImage()));
        btnSaveScene.setOnAction(a -> IO.saveScene(stage, AController.raytracer.getWorld(), AController.raytracer.getCamera()));
        btnLoadScene.setOnAction(a -> IO.loadScene(stage));
        btnObjects.setOnAction(a -> new EditObjects());
      //  btnNewScene.setOnAction(a -> new NewWorldStage());
        btnNewCamera.setOnAction(a -> new NewCameraStage(null));
        btnNewLight.setOnAction(a -> new NewLightStage(null));
        btnNewPlane.setOnAction(a -> new NewPlaneStage(null));
        btnNewSphere.setOnAction(a -> new NewSphereStage(null));
        btnNewCube.setOnAction(a -> new NewCubeStage(null));
        btnNewTriangle.setOnAction(a -> new NewTriangleStage(null));
        btnNewOBJ.setOnAction(a -> new NewOBJStage(null));
        btnRender.setOnAction(a -> AController.raytracer.render(image));
        btnStopRender.setOnAction(a -> AController.raytracer.stopRender());
        btnSettings.setOnAction(a -> new RenderSettingsStage());
        AController.raytracer.progress.addListener(a -> lblTime.setText(AController.raytracer.getStatus()));
        progressBar.progressProperty().bind(AController.raytracer.progress);
        progressBar.prefWidthProperty().bind(scene.widthProperty());

        scrollPane.minViewportHeightProperty().bind(image.fitHeightProperty());
        scrollPane.minViewportWidthProperty().bind(image.fitWidthProperty());

        resolution.bind(Bindings.concat("Last-Unicorn Ray-Tracer   Resolution: ", AController.raytracer.imgWidth, " x ", AController.raytracer.imgHeight));

        stage.titleProperty().bind(resolution);
        scene.setOnKeyPressed(a -> {
            if (a.getCode() == KeyCode.ESCAPE) AController.raytracer.stopRender();
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

}

