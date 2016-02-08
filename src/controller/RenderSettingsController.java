package controller;

import UI.IO;
import UI.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roberto on 12.01.16.
 *
 * The Controller for the RenderSettingsWindow
 */
class RenderSettingsController extends Stage {
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
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private CheckBox chkAmbientOcclusion;

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
    /**
     * setup all FieldValues and binds them to the related Object. And sets all necessary actions
     */
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
        txtRecursion = (NumberTextField) root.lookup("#txtRecursion");
        cpAmbientColor = (ColorPicker) root.lookup("#cpAmbientColor");
        cpColorPicker = (ColorPicker) root.lookup("#cpColorPicker");
        btnCancel = (Button) root.lookup("#btnCancel");
        btnOK = (Button) root.lookup("#btnOK");
        chkAmbientOcclusion = (CheckBox) root.lookup("#chkAmbientOcclusion");

        chbPattern.getItems().addAll("Clockwise", "Random");
        chbCores.disableProperty().bind(chkMultithreading.selectedProperty().not());
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            chbCores.getItems().add((i + 1) + "");
        }
        chbPattern.getSelectionModel().select(0);
        chbResolution.getItems().addAll("320x240", "640x480", "1024x768", "1280x720", "1920x1080");
        chbResolution.setOnAction(a -> setResolution());
        txtWidth.setOnKeyReleased(a -> keepRation(txtWidth));
        txtHeight.setOnKeyReleased(a -> keepRation(txtHeight));
        chkKeepRatio.setOnAction(a -> aspectration = txtWidth.getDouble() / txtHeight.getDouble());
        btnCancel.setOnAction(a -> onCancel());
        btnOK.setOnAction(a -> onOK());
        loadConfig();
    }

    /**
     * sets the selected resolution to the txtWidth and txtHeight NumberTextField
     */
    private void setResolution() {
        String[] s = chbResolution.getSelectionModel().getSelectedItem().split("x");
        txtWidth.setNumber(s[0]);
        txtHeight.setNumber(s[1]);
    }

    /**
     * if this is selected and width or height is changed, the other attribute is
     * simultaneously changed to keep the ratio
     * @param txt NumberTextField that is changed
     */
    private void keepRation(NumberTextField txt) {
        if (chkKeepRatio.isSelected()) {
            if (txt.equals(txtWidth)) {
                txtHeight.setNumber(txtWidth.getInteger() / aspectration);

            } else {
                txtWidth.setNumber(txtHeight.getInteger() * aspectration);
            }
        }
    }

    /**
     * loads saved settings
     */
    private void loadConfig() {
        Map<String, String> input = IO.readFile();
        if (input.size() > 0) {
            try {
                if (AController.raytracer.getWorld() == null) {
                    cpColorPicker.setValue(new Color(Double.parseDouble(input.get("backgroundColorRed")),
                            Double.parseDouble(input.get("backgroundColorGreen")),
                            Double.parseDouble(input.get("backgroundColorBlue")),
                            1));
                    cpAmbientColor.setValue(new Color(Double.parseDouble(input.get("ambientColorRed")),
                            Double.parseDouble(input.get("ambientColorGreen")),
                            Double.parseDouble(input.get("ambientColorBlue")),
                            1));
                } else {
                    cpColorPicker.setValue(new Color(AController.raytracer.getWorld().backgroundColor.r,
                            AController.raytracer.getWorld().backgroundColor.g,
                            AController.raytracer.getWorld().backgroundColor.b,
                            1));
                    cpAmbientColor.setValue(new Color(AController.raytracer.getWorld().ambientLight.r,
                            AController.raytracer.getWorld().ambientLight.g,
                            AController.raytracer.getWorld().ambientLight.b,
                            1));
                }
                chkMultithreading.setSelected(input.get("multithreading").equals("true"));
                chkHDRRendering.setSelected(input.get("hdr").equals("true"));
                chbCores.getSelectionModel().select(Integer.parseInt(input.get("cores")) - 1);
                chbPattern.getSelectionModel().select(Integer.parseInt(input.get("pattern")));
                txtWidth.setNumber(input.get("width"));
                txtHeight.setNumber(input.get("height"));
                txtRecursion.setNumber(input.get("recursion"));
                txtIOR.setNumber(input.get("ior"));
                chkAmbientOcclusion.setSelected(input.get("ambient").equals("true"));
            } catch (Exception e) {
                System.out.println("ladefehler");
            }

        }
    }

    /**
     * save new settings
     */
    private void saveConfig() {
        Map<String, String> output = new HashMap<>();
        output.put("multithreading", chkMultithreading.isSelected() + "");
        output.put("hdr", chkHDRRendering.isSelected() + "");
        output.put("cores", (chbCores.getSelectionModel().getSelectedIndex() + 1) + "");
        output.put("pattern", chbPattern.getSelectionModel().getSelectedIndex() + "");
        output.put("backgroundColorRed", cpColorPicker.getValue().getRed() + "");
        output.put("backgroundColorGreen", cpColorPicker.getValue().getGreen() + "");
        output.put("backgroundColorBlue", cpColorPicker.getValue().getBlue() + "");
        output.put("ambientColorRed", cpAmbientColor.getValue().getRed() + "");
        output.put("ambientColorGreen", cpAmbientColor.getValue().getGreen() + "");
        output.put("ambientColorBlue", cpAmbientColor.getValue().getBlue() + "");
        output.put("width", txtWidth.getInteger() + "");
        output.put("height", txtHeight.getInteger() + "");
        output.put("recursion", txtRecursion.getDouble() + "");
        output.put("ior", txtIOR.getDouble() + "");
        output.put("ambient", chkAmbientOcclusion.isSelected() + "");
        IO.writeFile(output);
    }

    /**
     * quit without saving
     */
    private void onCancel() {
        this.close();
    }

    /**
     * sets the new Values to the raytracer
     */
    private void onOK() {
        saveConfig();
        int width = txtWidth.getInteger();
        int height = txtHeight.getInteger();
        if (width < 0) width = 0;
        if (height < 0) height = 0;
        AController.raytracer.imgWidth.set(width);
        AController.raytracer.imgHeight.set(height);
        if (chkMultithreading.isSelected()) {
            AController.raytracer.cores = chbCores.getSelectionModel().getSelectedIndex() + 1;
        } else {
            AController.raytracer.cores = 1;
        }
        AController.raytracer.hdr = chkHDRRendering.isSelected();
        AController.raytracer.pattern = chbPattern.getSelectionModel().getSelectedIndex();
        AController.raytracer.recursionDepth = txtRecursion.getInteger();
        AController.raytracer.iOR = txtIOR.getDouble();
        utils.Color back = new utils.Color(
                cpColorPicker.getValue().getRed(),
                cpColorPicker.getValue().getGreen(),
                cpColorPicker.getValue().getBlue());
        utils.Color ambient = new utils.Color(
                cpAmbientColor.getValue().getRed(),
                cpAmbientColor.getValue().getGreen(),
                cpAmbientColor.getValue().getBlue());
        World w = AController.raytracer.getWorld();
        AController.raytracer.setWorld(new World(back, ambient, chkAmbientOcclusion.isSelected()));
        AController.raytracer.getWorld().lights.addAll(w.lights);
        AController.raytracer.getWorld().geometries.addAll(w.geometries);
        this.close();
    }
}
