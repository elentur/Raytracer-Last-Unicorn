package raytracer;
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

public class ImageViewer extends Application {

    /**
     * The Javafx start class.
     * @see javafx.stage.Stage
     * @param primaryStage The PrimaryStage of this program.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        final Image image = load(primaryStage);
        final Scene scene = new Scene(new Group(),image.getWidth(),image.getHeight());
        scene.setFill(new ImagePattern(image));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Viewer");
        primaryStage.show();
    }
    /** This method load an image from file and shows it in its native resolution
     * @param stage The PrimaryStage of this program.
     */
    private Image load(Stage stage){
        final String file;
        Image image=null;
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        try{
            file = fileChooser.showOpenDialog(stage).toURI().toString();
            image = new Image(file);
        }catch(Exception e){

            System.exit(0);
        }
        return image;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
