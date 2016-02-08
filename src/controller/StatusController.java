package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by roberto on 05.01.16.
 * The Controller for the progressView
 */
public class StatusController extends AController {
    @FXML
    private Label lblTime;
    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        raytracer.progress.addListener(a -> lblTime.setText(raytracer.getStatus()));
        progressBar.progressProperty().bind(raytracer.progress);
    }
}
