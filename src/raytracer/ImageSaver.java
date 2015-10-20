package raytracer;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

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
     * The Javafx start class.
     * @see javafx.stage.Stage
     * @param primaryStage The PrimaryStage of this program.
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Image Saver");
        primaryStage.setScene(setScene(primaryStage));
        primaryStage.show();


    }

    /**
     * Initialize the GuiElements and set all necessary listeners.
     * @param stage The PrimaryStage of this program.
     * @return The Scene for this PrimaryStage.
     */
    private Scene setScene(Stage stage){
        if(stage==null) throw new IllegalArgumentException("Stage can't be null");
        final int elementsHeight =25;
        final Menu btnFile = new Menu("File");
        final MenuItem btnSave = new MenuItem("Save");
        final MenuBar menubar = new MenuBar();
        final VBox root = new VBox(menubar, image);
        final Scene scene = new Scene(root, imgWidth, imgHeight + elementsHeight);

        btnFile.getItems().addAll(btnSave);
        menubar.getMenus().addAll(btnFile);

        btnSave.setOnAction(a->save(stage));

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

    /** This method saves the generated image in a png file, with a name and location, the user defined.
     * @param stage The PrimaryStage of this program.
     */
    private void save(Stage stage) {
        if(stage==null) throw new IllegalArgumentException("Stage can't be null");
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG"));
        final File file = fileChooser.showSaveDialog(stage);
        final RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try {  if(file != null && file.getName().contains("png")) ImageIO.write(renderedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

