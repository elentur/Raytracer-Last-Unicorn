package images;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImageChooser extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        StackPane root = new StackPane();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        // Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterPNG);
        File file = fileChooser.showOpenDialog(primaryStage);

        Image image = new Image(file.toURI().toString());

        final double width = image.getWidth();
        final double height = image.getHeight();

        ImageView iv = new ImageView();

        iv.setImage(image);

        root.getChildren().add(iv);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
