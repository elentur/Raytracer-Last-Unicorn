package raytracer;

import UI.IO;
import controller.AController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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


    /**
     * The Javafx start class.
     *
     * @param primaryStage The PrimaryStage of this program.
     * @see javafx.stage.Stage
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        primaryStage.setTitle("Unicorn RayTracer");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(900);
        primaryStage.setHeight(610);
        ScrollPane pane = (ScrollPane) scene.lookup("#scrollPaneSettings");
        VBox nodeTreeView = (VBox) scene.lookup("#nodeTreeView");
       // pane.minHeightProperty().bind(primaryStage.heightProperty());
       // nodeTreeView.minHeightProperty().bind(primaryStage.heightProperty());
        primaryStage.show();
        // TODO was macht der Befehl? // ImageView image  = (ImageView)scene.lookup("#image");

        MenuItem menuItem = ((MenuBar) scene.lookup("#menuBar")).getMenus().get(0).getItems().get(3);
        menuItem.disableProperty().bind(image.imageProperty().isNull());
        menuItem.setOnAction(a -> IO.saveImage(scene.getWindow(), image.getImage()));

        primaryStage.setOnCloseRequest(a -> AController.raytracer.stopRender());
        scene.setOnKeyPressed(a -> {
            if (a.getCode() == KeyCode.ESCAPE) AController.raytracer.stopRender();
        });
        primaryStage.setOnCloseRequest(
                a -> Platform.exit()
        );

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

