package sample;
/**
 * Created by Marcus Baetz on 07.10.2015.
 *
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImageViewer extends Application {
private Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception{

        final String file;
        final Image image;
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        file = fileChooser.showOpenDialog(primaryStage).toURI().toString();
        image = new Image(file);
        scene = new Scene(new Group(),image.getWidth(),image.getHeight());

        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.getScene().setFill(new ImagePattern(image));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
