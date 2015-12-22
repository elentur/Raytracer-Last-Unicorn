package raytracer;

import UI.*;
import camera.Camera;
import camera.PerspectiveCamera;
import geometries.*;
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
import material.PhongMaterial;
import material.SingleColorMaterial;
import texture.InterpolatedImageTexture;
import texture.SingleColorTexture;
import utils.Color;
import utils.World;

import java.io.File;


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
      /*  raytracer.setWorld(new World(new Color(0, 0, 0), new Color(0.0, 0.0, 0.0)));
        // world.lights.add(new PointLight(new Color(1,1,1),new Point3(0,5,100)));
        Light light = new PointLight(new Color(1, 1, 1), new Point3(4, 4, 4));
        light.name = "Pointlight1";
        //camera = new PerspectiveCamera(new Point3(0,5,100),new Vector3(0,0,-1), new Vector3(0,1,0), Math.PI/4);
        raytracer.setCamera(new PerspectiveCamera(new Point3(4, 4, 4), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4));
        //camera = new PerspectiveCamera(new Point3(3,3,3),new Vector3(-3,-3,-3), new Vector3(0,1,0), Math.PI/4);
        // Geometry obj = new Sphere(new Point3(0,0,-3),0.5, new LambertMaterial(new Color(1,0,0)));
        //Geometry obj = new AxisAlignedBox(new Point3(0.5,1,0.5),new Point3(-0.5,0,-0.5), new LambertMaterial(new Color(0,0,1)));
        //Geometry obj = new Plane(new Point3(0,-1,0), new Normal3(0,1,0), new LambertMaterial(new Color(0,1,0)));
        //Geometry obj = new Triangle(new Point3(-0.5,0.5,-3),new Point3(0.5,0.5,-3),new Point3(0.5,-0.5,-3), new LambertMaterial(new Color(1,0,1)));
        //Geometry obj = new ShapeFromFile(new File("C:\\Users\\marcu_000\\IdeaProjects\\CG1\\src\\obj\\cube.obj"), new LambertMaterial(new Color(0.5,0.5,0.5)));
        //Geometry obj = new ShapeFromFile(new File("C:\\Users\\marcu_000\\karren.obj"), new LambertMaterial(new Color(0.5,0.5,0.5)));
        raytracer.getWorld().lights.add(light);
        // world.geometries.add(obj);
        raytracer.getWorld().geometries.add(new geometries.Sphere(new Point3(1, 1, 1), 0.5, new LambertMaterial(new Color(0, 1, 0))));
    */

        World world = new World(new Color(0, 0, 0), new Color(0.3, 0.3, 0.3));
        raytracer.setWorld(world);


        Light light1 = new PointLight(new Color(1,1,1),new Point3(10,0,30), true);
        light1.name = "Pointlight1";
        world.lights.add(light1);

        Camera camera = new PerspectiveCamera(new Point3(0,0,4),new Vector3(0,0,-1), new Vector3(0,1,0), Math.PI/4);
        raytracer.setCamera(camera);

        /*Geometry sphere  = new Sphere(new Point3(0,0,0), 1, new SingleColorMaterial(new ImageTexture("/home/roberto/Documents/CG/RayTracer-Last-Unicorn/texture/world.jpg")));
        world.geometries.add(sphere);*/

        /*Geometry sphere  = new Sphere(new Point3(1.5,0,0), 1, new ReflectiveMaterial(
                new InterpolatedImageTexture("texture/world.jpg"),
                new Color(1,1,1),
                new Color(0.5,0.5,0.5),
                64
        ));
        world.geometries.add(sphere);

        Geometry sphere2  = new Sphere(new Point3(-1.5,0,0), 1, new ReflectiveMaterial(
                new ImageTexture("texture/world.jpg"),
                new Color(1,1,1),
                new Color(0.5,0.5,0.5),
                64
        ));
        world.geometries.add(sphere2);*/


        /*Geometry triangle  = new Triangle(
                new Point3(0,0,0),
                new Point3(1,0,0),
                new Point3(0,1,0),
                new ReflectiveMaterial(
                        new ImageTexture("/home/roberto/Documents/Uni/beuth/WS15/CG/RayTracer-Last-Unicorn/texture/world.jpg"),
                        new Color(1,1,1),
                        new Color(0.5,0.5,0.5),
                        64),
                new TexCoord2(1,1),
                new TexCoord2(0,1),
                new TexCoord2(1,0)
                );
        world.geometries.add(triangle);*/

       /* Geometry geo = new AxisAlignedBox(
                new ReflectiveMaterial(
                        new ImageTexture("texture/world.jpg"),
                    new Color(1,1,1),
                    new Color(0.5,0.5,0.5),
                    64
                )
        );*/
        Geometry geo = new ShapeFromFile(new File("C:/Users/marcu_000/Desktop/bunny.obj"),
                new PhongMaterial(
                        new SingleColorTexture(new Color(0.5,0.5,0.0)),
                        new Color(1,1,1),
                        64,
                        new SingleColorTexture(new Color(0,0,0)),0
                ),true,true
        );

        Transform t = new Transform();

        t = t.rotateY(Math.PI/4);
        t=t.scale(1,1,1);

        Geometry n = new Node(t,geo);
        Geometry sphere = new Sphere(
                new SingleColorMaterial(
                        new InterpolatedImageTexture("texture/Environment.jpg"),new SingleColorTexture(new Color(0,0,0)),0
                ),true,true
        );
      //  world.geometries.add(new Node(new Transform().scale(500,500,500),sphere));
        Geometry sphere1 = new Sphere(
                new LambertMaterial(
                        new InterpolatedImageTexture("texture/world.jpg",1,1,0,0),new SingleColorTexture(new Color(0,0,0)),0//new SingleColorTexture(new Color(0.5,0.5,0.5))
                ),true,true
        );
        Geometry sphere2 = new Sphere(
                new LambertMaterial(
                        new InterpolatedImageTexture("texture/world.jpg",1,1,0,0),new InterpolatedImageTexture("texture/earthnormal.jpg"),1//new SingleColorTexture(new Color(0.5,0.5,0.5))
                ),true,true
        );
        Geometry plane = new Plane(
                new LambertMaterial(
                       new SingleColorTexture(new Color(0.5,0.5,0.5)),new InterpolatedImageTexture("texture/pillownormal.png",10,10,0,0),1
                ),true,true
        );
        sphere2.name="1";
       /// world.geometries.add(n);
        world.geometries.add(new Node(new Transform().translate(2,0,0),sphere1));
        world.geometries.add(new Node(new Transform().translate(-2,0,0),sphere2));
        world.geometries.add(new Node(new Transform().translate(0,0,-10).rotateX(Math.PI/2),plane));
        //world.geometries.add(n);



       /*Geometry plane  = new Plane(new Point3(0,-1,0),new Normal3(0,1,0), new OrenNayarMaterial(
                new InterpolatedImageTexture("texture/sterne.jpg"),
                0.6
        ));

        world.geometries.add(plane);*/
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

