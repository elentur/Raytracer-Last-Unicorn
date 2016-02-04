package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by roberto on 04.02.16.
 */
public class RenderViewController extends Stage {

    private ImageView image;

    protected Parent root;

    public RenderViewController() {
        super();
        root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/renderView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(
                root,
                AController.raytracer.imgWidth.get() + 4,
                AController.raytracer.imgHeight.get() + 38
        );

        initialize();

        this.setTitle("Render-View");
        this.setScene(scene);
        this.initModality(Modality.WINDOW_MODAL);
        this.show();
    }

    public ImageView getImageView(){
        return image;
    }

    private void initialize() {
        ScrollPane sp = (ScrollPane) root.lookup("#scrollPane");
        image = (ImageView) sp.getContent();

        VBox statusbar = (VBox) root.lookup("#statusbar");
        Label lblTime = (Label) statusbar.lookup("#lblTime");
        lblTime.setAlignment(Pos.BASELINE_CENTER);
        lblTime.prefWidthProperty().bind(this.widthProperty());
        ProgressBar progressBar = (ProgressBar) statusbar.lookup("#progressBar");
        progressBar.prefWidthProperty().bind(this.widthProperty());
    }
}
