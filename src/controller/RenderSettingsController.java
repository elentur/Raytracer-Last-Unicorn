package controller;

import UI.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;;

/**
 * Created by roberto on 12.01.16.
 */
public class RenderSettingsController extends Stage {
    @FXML
    private CheckBox chkMultithreading;
    @FXML
    private CheckBox chkHDRRendering;
    @FXML
    private CheckBox chkKeepRatio;
    @FXML
    private ChoiceBox<String> chbCores;
    @FXML
    private ChoiceBox<String> chbPattern;
    @FXML
    private ChoiceBox<String> chbResolution;
    @FXML
    private NumberTextField txtWidth;
    @FXML
    private NumberTextField txtHeight;
    @FXML
    private double aspectration = 0;
    @FXML
    private ColorPicker cpColorPicker;
    @FXML
    private ColorPicker cpAmbientColor;
    @FXML
    private NumberTextField txtRecursion;
    @FXML
    private NumberTextField txtIOR;

    private Parent root;

    public RenderSettingsController() {
        super();
        root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/renderSettingsView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();

        Scene scene = new Scene(root);
        this.setTitle("Render-Settings");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void initialize() {
        chkMultithreading = (CheckBox) root.lookup("#chkMultithreading");
        chkHDRRendering = (CheckBox) root.lookup("#chkHDRRendering");
        chkKeepRatio = (CheckBox) root.lookup("#chkKeepRatio");
        chbCores = (ChoiceBox<String>) root.lookup("#chbCores");
        chbPattern = (ChoiceBox<String>) root.lookup("#chbPattern");
        chbResolution = (ChoiceBox<String>) root.lookup("#chbResolution");
        txtWidth = (NumberTextField) root.lookup("#txtWidth");
        txtHeight = (NumberTextField) root.lookup("#txtHeight");
        txtIOR = (NumberTextField) root.lookup("#txtIOR");

        chbPattern.getItems().addAll("Clockwise", "Random");
        chbCores.disableProperty().bind(chkMultithreading.selectedProperty().not());
        chbPattern.getSelectionModel().select(0);
        chbResolution.getItems().addAll("320x240", "640x480", "1024x768", "1280x720", "1920x1080");
    }
}
