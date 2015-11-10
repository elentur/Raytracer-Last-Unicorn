package UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import raytracer.ImageSaver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class RenderSettingsStage extends Stage {

    final CheckBox chkMultithreading;
    final CheckBox chkKeepRatio;
    final ChoiceBox<String> chbCores;
    final ChoiceBox<String> chbPattern;
    final ChoiceBox<String> chbResolution;
    final NumberTextField txtWidth;
    final NumberTextField txtHeight;
    double aspectration = 0;


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


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a -> onCancel());

        chkMultithreading = new CheckBox("Multithreading");
        chkKeepRatio = new CheckBox("Keep Ratio");
        chbCores = new ChoiceBox<>();
        chbPattern = new ChoiceBox<>();
        chbResolution = new ChoiceBox<>();
        txtWidth = new NumberTextField("0.0");
        txtHeight = new NumberTextField("0.0");
        final Label lblWidth = new Label("width");
        final Label lblHeight = new Label("height");
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
        center.add(chbCores, 0, 1, 2, 1);
        center.add(lblRenderPattern, 0, 2, 2, 1);
        center.add(chbPattern, 0, 3, 2, 1);
        center.add(chbResolution, 0, 4, 2, 1);
        center.add(lblWidth, 0, 5, 1, 1);
        center.add(lblHeight, 0, 6, 1, 1);
        center.add(txtWidth, 1, 5);
        center.add(txtHeight, 1, 6);
        center.add(chkKeepRatio, 2, 5, 2, 1);

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
        Scene scene = new Scene(borderPane, 300, 400);
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
                chkMultithreading.setSelected(input.get("multithreading").equals("true"));
                chbCores.getSelectionModel().select(Integer.parseInt(input.get("cores")));
                chbPattern.getSelectionModel().select(Integer.parseInt(input.get("pattern")));
                txtWidth.setText(input.get("width"));
                txtHeight.setText(input.get("height"));
            } catch (Exception e) {
                System.out.println("ladefehler");
            }

        }
    }

    private void saveConfig() {
        Map<String, String> output = new HashMap<>();
        output.put("multithreading", chkMultithreading.isSelected() + "");
        output.put("cores", chbCores.getSelectionModel().getSelectedIndex() + "");
        output.put("pattern", chbPattern.getSelectionModel().getSelectedIndex() + "");
        output.put("width", txtWidth.getText());
        output.put("height", txtHeight.getText());
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
        ImageSaver.imgWidth.set(width);
        ImageSaver.imgHeight.set(height);
        if (chkMultithreading.isSelected()) {
            ImageSaver.cores = chbCores.getSelectionModel().getSelectedIndex() + 1;
        } else {
            ImageSaver.cores = 1;
        }
        ImageSaver.pattern = chbPattern.getSelectionModel().getSelectedIndex();
        this.close();
    }
}
