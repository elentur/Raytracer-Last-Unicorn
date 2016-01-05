package raytracer;

import UI.*;
import camera.Camera;
import camera.PerspectiveCamera;
import geometries.AxisAlignedBox;
import geometries.Node;
import geometries.Plane;
import geometries.Sphere;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import light.Light;
import light.PointLight;
import matVect.Point3;
import matVect.Transform;
import matVect.Vector3;
import material.LambertMaterial;
import material.ReflectiveMaterial;
import sampling.LightShadowPattern;
import sampling.SamplingPattern;
import texture.SingleColorTexture;
import utils.Color;
import utils.World;


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
    public final static Raytracer raytracer = new Raytracer(true);


    private void testScene() {

        World world = new World(new Color(0, 0, 0), new Color(0.0, 0.0, 0.0));
        raytracer.setWorld(world);


        Light light1 = new PointLight(new Color(1,1,1),new Point3(0,8,-10), true,500, new LightShadowPattern(1,1));
        light1.name = "Pointlight1";
        world.lights.add(light1);

       // Camera camera = new DOFCamera(new Point3(0,0,5),new Vector3(0,0,-1), new Vector3(0,1,0), Math.PI/4, new DOFPattern(5,5.6),4, new SamplingPattern(4));
        Camera camera = new PerspectiveCamera(new Point3(0,5,14),new Vector3(0,0,-1), new Vector3(0,1,0), Math.PI/6, new SamplingPattern(5));

        raytracer.setCamera(camera);

        Sphere sphere1 = new Sphere(
                new LambertMaterial(new SingleColorTexture(new Color(1,1,1)),
                        new SingleColorTexture(new Color(1,1,1)),
                        0,
                        new SingleColorTexture(new Color(1,1,1))
                        ),
                true,
                true,
                true,
                false
        );

        Sphere sphere2 = new Sphere(
                new ReflectiveMaterial(new SingleColorTexture(new Color(0.5,0.5,0.5)),
                        new SingleColorTexture(new Color(1,1,1)),
                        new SingleColorTexture(new Color(1,1,1)),
                        64,
                        new SingleColorTexture(new Color(1,1,1)),
                        0,
                        new SingleColorTexture(new Color(1,1,1))
                ),
                true,
                true,
                true,
                false
        );
        Plane plane1 = new Plane(
                new LambertMaterial(new SingleColorTexture(new Color(1,1,1)),
                        new SingleColorTexture(new Color(1,1,1)),
                        0,
                        new SingleColorTexture(new Color(1,1,1))
                ),
                true,
                true,
                true,
                false
        );
        Plane plane2 = new Plane(
                new LambertMaterial(new SingleColorTexture(new Color(0,1,0)),
                        new SingleColorTexture(new Color(1,1,1)),
                        0,
                        new SingleColorTexture(new Color(1,1,1))
                ),
                true,
                true,
                true,
                false
        );
        Plane plane3 = new Plane(
                new LambertMaterial(new SingleColorTexture(new Color(1,0,0)),
                        new SingleColorTexture(new Color(1,1,1)),
                        0,
                        new SingleColorTexture(new Color(1,1,1))
                ),
                true,
                true,
                true,
                false
        );
        AxisAlignedBox box = new AxisAlignedBox(
                new LambertMaterial(new SingleColorTexture(new Color(1,0,0)),
                        new SingleColorTexture(new Color(1,1,1)),
                        0,
                        new SingleColorTexture(new Color(1,1,1))
                ),
                true,
                true,
                true,
                false
        );



        world.geometries.add(new Node(new Transform().translate(-2.7,4.225,-11.39).scale(1.7,1.7,1.7),sphere1,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(3,4.225,-7).scale(1.7,1.7,1.7),sphere1,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(0.571,3.1,-12.532).scale(0.7,0.7,0.7),sphere2,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(0,10,0).rotateX(Math.PI),plane1,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(0,0,-16).rotateX(Math.PI/2),plane1,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(0,0,15).rotateX(-Math.PI/2),plane1,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(6,0,0).rotateZ(Math.PI/2),plane2,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(-6,0,0).rotateZ(-Math.PI/2),plane3,true,true,true,false));
        world.geometries.add(new Node(new Transform().translate(0,2.5,0),plane1,true,true,true,false));
    }

    /**
     * The Javafx start class.
     *
     * @param primaryStage The PrimaryStage of this program.
     * @see javafx.stage.Stage
     */
    @Override
    public void start(final Stage primaryStage) {


        testScene();

        primaryStage.setScene(setScene(primaryStage));

        primaryStage.setOnCloseRequest(a -> raytracer.stopRender());
        primaryStage.show();
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

        final Scene scene = new Scene(root, raytracer.imgWidth.get() + 10, raytracer.imgHeight.get() + elementsHeight);

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
        btnSaveScene.setOnAction(a -> IO.saveScene(stage, raytracer.getWorld(), raytracer.getCamera()));
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
        btnRender.setOnAction(a -> raytracer.render(image));
        btnStopRender.setOnAction(a -> raytracer.stopRender());
        btnSettings.setOnAction(a -> new RenderSettingsStage());
        raytracer.progress.addListener(a -> lblTime.setText(raytracer.getStatus()));
        progressBar.progressProperty().bind(raytracer.progress);
        progressBar.prefWidthProperty().bind(scene.widthProperty());

        scrollPane.minViewportHeightProperty().bind(image.fitHeightProperty());
        scrollPane.minViewportWidthProperty().bind(image.fitWidthProperty());

        resolution.bind(Bindings.concat("Last-Unicorn Ray-Tracer   Resolution: ", raytracer.imgWidth, " x ", raytracer.imgHeight));

        stage.titleProperty().bind(resolution);
        scene.setOnKeyPressed(a -> {
            if (a.getCode() == KeyCode.ESCAPE) raytracer.stopRender();
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

