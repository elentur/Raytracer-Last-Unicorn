package UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import raytracer.ImageSaver;
import utils.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class RenderSettingsStage extends Stage {

    private final CheckBox chkMultithreading;
    private final CheckBox chkHDRRendering;
    private final CheckBox chkKeepRatio;
    private final ChoiceBox<String> chbCores;
    private final ChoiceBox<String> chbPattern;
    private final ChoiceBox<String> chbResolution;
    private final NumberTextField txtWidth;
    private final NumberTextField txtHeight;
    private double aspectration = 0;
    private final ColorPicker cpColorPicker;
    private final ColorPicker cpAmbientColor;
    private final NumberTextField txtRecursion;
    private final NumberTextField txtIOR;

    public RenderSettingsStage() {
        super();
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPercentWidth(20);
        center.getColumnConstraints().addAll(col1, col2, col3, col4);

        final Label lblInfo = new Label("Render-Settings");

        cpColorPicker = new ColorPicker(javafx.scene.paint.Color.BLACK);
        final Label lblColorPicker = new Label("Background-Color:");
        final Label lblAmbient = new Label("Ambientlight-Color");
        cpAmbientColor = new ColorPicker(new javafx.scene.paint.Color(0.15, 0.15, 0.15, 1));

        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a -> onCancel());

        chkMultithreading = new CheckBox("Multithreading");
        chkHDRRendering = new CheckBox("HDR-Rendering");
        chkKeepRatio = new CheckBox("Keep Ratio");
        chbCores = new ChoiceBox<>();
        chbPattern = new ChoiceBox<>();
        chbResolution = new ChoiceBox<>();
        txtWidth = new NumberTextField("0.0");
        txtHeight = new NumberTextField("0.0");
        txtIOR = new NumberTextField("1.0003");
        Label lblIOR = new Label("Global IOR");
        final Label lblWidth = new Label("width");
        final Label lblHeight = new Label("height");
        final Label lblRecursion = new Label("Recursion Depth");
        txtRecursion = new NumberTextField("0.0");

        final Label lblRenderPattern = new Label("Render-Pattern");

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            chbCores.getItems().add((i + 1) + "");
        }
        chbPattern.getItems().addAll("Clockwise", "Random");
        chbCores.disableProperty().bind(chkMultithreading.selectedProperty().not());
        chbPattern.getSelectionModel().select(0);
        chbResolution.getItems().addAll("320x240", "640x480", "1024x768", "1280x720", "1920x1080");

        chkKeepRatio.setOnAction(a -> aspectration = Double.parseDouble(txtWidth.getText()) / Double.parseDouble(txtHeight.getText()));
        center.add(chkMultithreading, 0, 0, 2, 1);
        center.add(chkHDRRendering, 1, 0, 2, 1);
        center.add(chbCores, 0, 1, 2, 1);
        center.add(lblRenderPattern, 0, 2, 2, 1);
        center.add(chbPattern, 0, 3, 2, 1);
        center.add(lblColorPicker, 0, 4,1,1);
        center.add(cpColorPicker, 1, 4,2,1);
        center.add(lblAmbient, 0, 5,1,1);
        center.add(cpAmbientColor, 1, 5,2,1);
        center.add(chbResolution, 0, 6, 2, 1);
        center.add(lblWidth, 0, 7);
        center.add(lblHeight, 0, 8);
        center.add(txtWidth, 1, 7);
        center.add(txtHeight, 1, 8);
        center.add(chkKeepRatio, 2, 7, 2, 1);
        center.add(lblRecursion, 1, 1, 1, 1);
        center.add(txtRecursion, 1, 2, 1, 1);
        center.add(lblIOR, 2, 1, 1, 1);
        center.add(txtIOR, 2, 2, 1, 1);

        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK, btnCancel);

        chbResolution.setOnAction(a -> setResolution());
        txtWidth.setOnKeyReleased(a -> keepRation(txtWidth));
        txtHeight.setOnKeyReleased(a -> keepRation(txtHeight));
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane);
        this.minWidthProperty().bind(borderPane.widthProperty());
        this.minHeightProperty().bind(borderPane.heightProperty());
        scene.getStylesheets().add("css/rootStyle.css");
        loadConfig();
        this.setTitle("Render-Settings");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void setResolution() {
        String[] s = chbResolution.getSelectionModel().getSelectedItem().split("x");
        txtWidth.setText(s[0] + ".0");
        txtHeight.setText(s[1] + ".0");
    }

    private void keepRation(TextField txt) {
        if (chkKeepRatio.isSelected()) {
            if (txt.equals(txtWidth)) {
                txtHeight.setText((Double.parseDouble(txt.getText()) / aspectration) + "");
            } else {
                txtWidth.setText((Double.parseDouble(txt.getText()) * aspectration) + "");
            }
        }
    }

    private void loadConfig() {
        Map<String, String> input = IO.readFile("settings.cfg");
        if (input.size() > 0) {
            try {
                if (ImageSaver.raytracer.getWorld() == null) {
                    cpColorPicker.setValue(new Color(Double.parseDouble(input.get("backgroundColorRed")),
                            Double.parseDouble(input.get("backgroundColorGreen")),
                            Double.parseDouble(input.get("backgroundColorBlue")),
                            1));
                    cpAmbientColor.setValue(new Color(Double.parseDouble(input.get("ambientColorRed")),
                            Double.parseDouble(input.get("ambientColorGreen")),
                            Double.parseDouble(input.get("ambientColorBlue")),
                            1));
                }else{
                    cpColorPicker.setValue(new Color(ImageSaver.raytracer.getWorld().backgroundColor.r,
                            ImageSaver.raytracer.getWorld().backgroundColor.g,
                            ImageSaver.raytracer.getWorld().backgroundColor.b,
                            1));
                    cpAmbientColor.setValue(new Color(ImageSaver.raytracer.getWorld().ambientLight.r,
                            ImageSaver.raytracer.getWorld().ambientLight.g,
                            ImageSaver.raytracer.getWorld().ambientLight.b,
                            1));
                }
                chkMultithreading.setSelected(input.get("multithreading").equals("true"));
                chkHDRRendering.setSelected(input.get("hdr").equals("true"));
                chbCores.getSelectionModel().select(Integer.parseInt(input.get("cores")) - 1);
                chbPattern.getSelectionModel().select(Integer.parseInt(input.get("pattern")));
                txtWidth.setText(input.get("width"));
                txtHeight.setText(input.get("height"));
                txtRecursion.setText(input.get("recursion"));
                txtIOR.setText(input.get("ior"));
            } catch (Exception e) {
                System.out.println("ladefehler");
            }

        }
    }

    private void saveConfig() {
        Map<String, String> output = new HashMap<>();
        output.put("multithreading", chkMultithreading.isSelected() + "");
        output.put("hdr", chkHDRRendering.isSelected() + "");
        output.put("cores", (chbCores.getSelectionModel().getSelectedIndex() + 1) + "");
        output.put("pattern", chbPattern.getSelectionModel().getSelectedIndex() + "");
        output.put("backgroundColorRed", cpColorPicker.getValue().getRed()+"");
        output.put("backgroundColorGreen", cpColorPicker.getValue().getGreen()+"");
        output.put("backgroundColorBlue", cpColorPicker.getValue().getBlue()+"");
        output.put("ambientColorRed", cpAmbientColor.getValue().getRed()+"");
        output.put("ambientColorGreen", cpAmbientColor.getValue().getGreen()+"");
        output.put("ambientColorBlue", cpAmbientColor.getValue().getBlue()+"");
        output.put("width", txtWidth.getText());
        output.put("height", txtHeight.getText());
        output.put("recursion", txtRecursion.getText());
        output.put("ior", txtIOR.getText());
        IO.writeFile("settings.cfg", output);
    }

    private void onCancel() {
        this.close();
    }

    private void onOK() {
        saveConfig();
        int width = (int) Double.parseDouble(txtWidth.getText());
        int height = (int) Double.parseDouble(txtHeight.getText());
        if (width < 0) width = 0;
        if (height < 0) height = 0;
        ImageSaver.raytracer.imgWidth.set(width);
        ImageSaver.raytracer.imgHeight.set(height);
        if (chkMultithreading.isSelected()) {
            ImageSaver.raytracer.cores = chbCores.getSelectionModel().getSelectedIndex() + 1;
        } else {
            ImageSaver.raytracer.cores = 1;
        }
        ImageSaver.raytracer.hdr = chkHDRRendering.isSelected();
        ImageSaver.raytracer.pattern = chbPattern.getSelectionModel().getSelectedIndex();
        ImageSaver.raytracer.recursionDepth = (int) Double.parseDouble(txtRecursion.getText());
        ImageSaver.raytracer.iOR = Double.parseDouble(txtRecursion.getText());
        utils.Color back = new utils.Color(
                cpColorPicker.getValue().getRed(),
                cpColorPicker.getValue().getGreen(),
                cpColorPicker.getValue().getBlue());
        utils.Color ambient = new utils.Color(
                cpAmbientColor.getValue().getRed(),
                cpAmbientColor.getValue().getGreen(),
                cpAmbientColor.getValue().getBlue());
        World w = ImageSaver.raytracer.getWorld();
        ImageSaver.raytracer.setWorld(new World(back, ambient));
        ImageSaver.raytracer.getWorld().lights.addAll(w.lights);
        ImageSaver.raytracer.getWorld().geometries.addAll(w.geometries);
        this.close();
    }
}
