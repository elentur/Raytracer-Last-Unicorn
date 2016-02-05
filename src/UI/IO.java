package UI;

import controller.ObservableElementLists;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import observables.AOElement;
import observables.geometries.AOGeometry;
import observables.lights.AOLight;
import serializable.SElement;
import utils.Scene;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static void saveImage(final Window stage, final Image writableImage) {
        if (stage == null) throw new IllegalArgumentException("Stage can't be null");
        if (writableImage == null) throw new IllegalArgumentException("WritableImage must not be null.");
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG"));
        final File file = fileChooser.showSaveDialog(stage);
        final RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try {
            if (file != null) ImageIO.write(renderedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveScene(final Stage stage, final TreeItem<AOElement> rootItem) {
        if (rootItem == null) {
            Dialog dlg = new Dialog("No Elements!");

            dlg.setNewText("No Elements created. You must Create a Element first before you can try to save it.");
            dlg.showAndWait();
            return;
        }
        ObservableElementLists list = ObservableElementLists.getInstance();
        SElement camera = null;
        if ( list.camera!=null)  camera = list.camera.serialize();
        List<SElement> lights = new ArrayList<>();
        for (AOLight light : list.lights){
            lights.add(light.serialize());
        }
        List<SElement> geometries = new ArrayList<>();
        for (AOGeometry geometry : list.geometries){
            geometries.add(geometry.serialize());
        }

        Scene scene = new Scene(geometries,lights,camera);

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("UCN files (*.ucn)", "*.ucn"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".ucn")) file = new File(file.getAbsolutePath() + ".ucn");
            Path path = file.toPath();
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toString()));
                out.writeObject(scene);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Fehler");
            }
        }
    }

    public static void loadScene(final Stage stage, final TreeItem<AOElement> rootItem) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("UCN files (*.ucn)", "*.ucn"));
        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Path path = file.toPath();
            Scene scene = null;
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toString()));
                scene = (Scene) in.readObject();
                in.close();
            } catch (IOException e) {
                System.out.println("IO Fehler");
            } catch (ClassNotFoundException e) {
                Dialog dlg = new Dialog("File not Found!");
                dlg.setNewText("The selected File could not be found.");
                dlg.showAndWait();
                return;
            } catch (ClassCastException e) {
                Dialog dlg = new Dialog("Wrong File-type!");
                dlg.setNewText("The File is not a Last-Unicorn Scene File.");
                dlg.showAndWait();
                return;
            }

            if (scene != null) {
                ObservableElementLists list = ObservableElementLists.getInstance();
                list.addElement(scene.getCamera());

                for(AOLight light : scene.getLights()){
                    list.addElement(light);
                }

                for(AOGeometry geometry : scene.getGeometries()){
                    list.addElement(geometry);
                }
            }


        }
    }

    public static Map<String, String> readFile(String source) {
        Path path = Paths.get(source);
        BufferedReader br;
        Map<String, String> input = new HashMap<>();
        try {
            br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(":");
                input.put(s[0], s[1]);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Lesefehler");
        }
        return input;
    }

    public static void writeFile(String source, Map<String, String> output) {
        Path path = Paths.get(source);
        BufferedWriter br;

        try {
            br = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            for (String key : output.keySet()) {
                br.write(key + ":" + output.get(key));
                br.newLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Schreibfehler");

        }
    }
}
