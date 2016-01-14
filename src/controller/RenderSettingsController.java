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

    private CheckBox chkMultithreading;
    private CheckBox chkHDRRendering;
    private CheckBox chkKeepRatio;
    private ChoiceBox<String> chbCores;
    private ChoiceBox<String> chbPattern;
    private ChoiceBox<String> chbResolution;
    private NumberTextField txtWidth;
    private NumberTextField txtHeight;
    private double aspectration = 0;
    private ColorPicker cpColorPicker;
    private ColorPicker cpAmbientColor;
    private NumberTextField txtRecursion;
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

        Scene scene = new Scene(root,640,370);
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
