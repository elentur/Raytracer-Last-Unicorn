package UI;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import raytracer.ImageSaver;
import utils.World;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Path;

/**
 * Created by Marcus Baetz on 04.11.2015.
 *
 * @author Marcus Bätz
 */
public class IO {
    /**
     * This method saves the generated image in a png file, with a name and location, the user defined.
     *
     * @param stage The PrimaryStage of this program.
     */
    public static void saveImage(final Stage stage, final WritableImage writableImage) {
        if (stage == null) throw new IllegalArgumentException("Stage can't be null");
        if (writableImage == null) throw new IllegalArgumentException("WritableImage must not be null.");
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG"));
        final File file = fileChooser.showSaveDialog(stage);
        final RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try {
            if (file != null ) ImageIO.write(renderedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveScene(final Stage stage, final World world){
        if (world ==null){
            Dialog dlg = new Dialog("No Scene!");
            dlg.setNewText("No Scene Created. You must Create a Scene first before you can try to save it.");
            dlg.showAndWait();
            return;
        }
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("UCN files (*.ucn)", "*.ucn"));
        final File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Path path = file.toPath();
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toString()));
                out.writeObject(world);
                out.close();
            }catch (IOException e){
                System.out.println("IO Fehler");
            }


        }
    }
    public static void loadScene(final Stage stage){

        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("UCN files (*.ucn)", "*.ucn"));
        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Path path = file.toPath();
            World world = null;
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toString()));
                world = (World)in.readObject();
                in.close();
            }catch (IOException e){
                System.out.println("IO Fehler");
            } catch (ClassNotFoundException e) {
                Dialog dlg = new Dialog("File not Found!");
                dlg.setNewText("The selected File could not be found.");
                dlg.showAndWait();
                return;
            }catch (ClassCastException e){
                Dialog dlg = new Dialog("Wrong File-type!");
                dlg.setNewText("The File is not a Last-Unicorn Scene File.");
                dlg.showAndWait();
                return;
            }
            if(world!= null) ImageSaver.setWorld(world);


        }
    }
}
