package images;
/**
 * Created by roberto on 06.10.15.
 */

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaver extends Application {

    private Scene scene;
    private BorderPane root;
    protected static int counter = 0;
    protected static long startTime;
    private WritableImage wImage = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();

        scene = new Scene(root, 640, 530);

        setScene(primaryStage);

        primaryStage.setTitle("Save Image");

        primaryStage.setScene(scene);

        primaryStage.show();

        setScene(primaryStage);
    }

    private void setScene(Stage primaryStage){

        MenuItem save = new MenuItem("save");

        save.setOnAction(e -> saveImage(primaryStage));

        Menu fileMenu = new Menu("File");

        fileMenu.getItems().add(save);



        MenuItem render = new MenuItem("normal render");

        render.setOnAction(e -> drawImage("normal"));

        MenuItem mtrender = new MenuItem("multithread render");

        mtrender.setOnAction(e -> drawImage("multithread"));

        Menu renderMenu = new Menu("Render");

        renderMenu.getItems().addAll(render, mtrender);


        MenuBar menuBar = new MenuBar(fileMenu,renderMenu);

        root.setTop(menuBar);
    }

    private void drawImage(String type) {

        ImageView imageView = new ImageView();

        wImage = new WritableImage((int)root.getWidth(), (int)root.getHeight() - 50);

        imageView.setImage(wImage);

        root.setCenter(imageView);

        if(type.contains("normal")){
            renderImage();
        }else{
            renderMultiThreadImage();
        }
    }

    private void renderImage(){
        new Render(wImage).start();
        root.setBottom(null);
    }

    private void renderMultiThreadImage(){

        int cores = Runtime.getRuntime().availableProcessors();

        MultiThreadRender task = null;

        startTime = System.currentTimeMillis();

        for(int core = 1; core <= cores;core++) {

            task = new MultiThreadRender(wImage, cores, core);

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        }

        generateFooter(task);
    }

    private void generateFooter(MultiThreadRender task){

        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(task.progressProperty());

        Label label = new Label();
        label.textProperty().bind(task.messageProperty());

        root.setBottom(new HBox(bar,label));
    }

    private void saveImage(final Stage primaryStage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);

        if(file != null){
            saveFile(file);
        }
    }

    private void saveFile(final File file) {

        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());

        BufferedImage imageRGB = null;

        if(fileExtension == "png") {
            imageRGB = SwingFXUtils.fromFXImage(wImage, null);
        }else{ // workaround for javafx jpg bug
            BufferedImage image = SwingFXUtils.fromFXImage(wImage, null); // Get buffered image.
            imageRGB = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
            Graphics2D graphics = imageRGB.createGraphics();
            graphics.drawImage(image, 0, 0, null);
        }

        try {
            ImageIO.write(imageRGB, fileExtension, file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Konnte Datei nicht speichern! Error: " + e.getMessage());
        }
    }
}
