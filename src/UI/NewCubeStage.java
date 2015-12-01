package UI;

import geometries.AxisAlignedBox;
import geometries.Geometry;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import matVect.Point3;
import material.LambertMaterial;
import raytracer.ImageSaver;
import utils.Color;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewCubeStage extends NewGeoStage {

    private final NumberTextField[] txtInputs;
    private AxisAlignedBox b;
    private final TextField txtName;

    public NewCubeStage(AxisAlignedBox b) {
        super();
        this.b = b;
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPercentWidth(25);
        center.getColumnConstraints().addAll(col1, col2, col3, col4);

        final Button btnMaterial = new MaterialButton(this);
        btnMaterial.setOnAction(a -> new NewMaterialStage(this));
        final Label lblColorPicker = new Label("Material:");

        txtName = new TextField();

        final Label lblName = new Label("Name");
        final Label lblInfo = new Label("Do you wish to create a new Plane?");


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a -> onCancel());
        if (ImageSaver.raytracer.getWorld() == null) {
            lblInfo.setText("No Scene Created!");
            lblInfo.setTextFill(javafx.scene.paint.Color.RED);
            btnOK.setDisable(true);
        }


        final Label lblRun = new Label("rechte vordere obere Ecke");
        lblRun.setWrapText(true);
        final Label lblLbf = new Label("linke hintere untere Ecke");
        lblLbf.setWrapText(true);
        final Label lblX = new Label("x");
        final Label lblY = new Label("y");
        final Label lblZ = new Label("z");
        txtInputs = new NumberTextField[6];
        for (int i = 0; i < 6; i++) {
            txtInputs[i] = new NumberTextField("0.0");
            center.add(txtInputs[i], (i % 3) + 1, (i / 3) + 2);
        }

        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK, btnCancel);
        center.add(lblColorPicker, 0, 0);
        center.add(btnMaterial, 1, 0);
        center.add(lblName, 2, 0);
        center.add(txtName, 3, 0);
        center.add(lblX, 1, 1);
        center.add(lblY, 2, 1);
        center.add(lblZ, 3, 1);
        center.add(lblRun, 0, 2);
        center.add(lblLbf, 0, 3);

        setValues();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane);
        this.minWidthProperty().bind(borderPane.widthProperty());
        this.minHeightProperty().bind(borderPane.heightProperty());
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("Create new Cube?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void setValues() {
        if (b == null) {
            int index = 1;
            if (ImageSaver.raytracer.getWorld() != null) {
                for (Geometry g : ImageSaver.raytracer.getWorld().geometries)
                    if (g instanceof AxisAlignedBox) index++;
            }
            txtName.setText("Axis Aligned Box" + index);
            txtInputs[0].setText("0.5");
            txtInputs[1].setText("1.0");
            txtInputs[2].setText("0.5");
            txtInputs[3].setText("-0.5");
            txtInputs[4].setText("0.0");
            txtInputs[5].setText("-0.5");
            material.set(new LambertMaterial(new Color(0.5, 0.5, 0.5)));
        } else {
            txtName.setText(b.name);
            txtInputs[0].setText(b.run.x + "");
            txtInputs[1].setText(b.run.y + "");
            txtInputs[2].setText(b.run.z + "");
            txtInputs[3].setText(b.lbf.x + "");
            txtInputs[4].setText(b.lbf.y + "");
            txtInputs[5].setText(b.lbf.z + "");
            material.set(b.material);
            // cpColorPicker.setValue(new javafx.scene.paint.Color(b.material.r, b.material.g, b.material.b, 1));
        }
    }

    private void onCancel() {
        this.close();
    }

    private void onOK() {
        try {
            if (b != null) ImageSaver.raytracer.getWorld().geometries.remove(b);
            Point3 run = new Point3(
                    Double.parseDouble(txtInputs[0].getText()),
                    Double.parseDouble(txtInputs[1].getText()),
                    Double.parseDouble(txtInputs[2].getText()));
            Point3 lbf = new Point3(
                    Double.parseDouble(txtInputs[3].getText()),
                    Double.parseDouble(txtInputs[4].getText()),
                    Double.parseDouble(txtInputs[5].getText()));
            if (run.x < lbf.x) run = new Point3(lbf.x + 1.0, run.y, run.z);
            if (run.y < lbf.y) run = new Point3(run.x, lbf.y + 1.0, run.z);
            if (run.z < lbf.z) run = new Point3(run.x, run.y, lbf.z + 1.0);

            AxisAlignedBox p = new AxisAlignedBox(run, lbf, material.get());
            p.name = txtName.getText();
            int index = 1;
            boolean run1 = false;
            for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                if (g.name.equals(p.name)) run1 = true;
            }
            while (run1) {
                int i = index;
                for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                    if (g.name == p.name + index) index++;
                }
                if (i == index) {
                    run1 = false;
                    p.name = p.name + index;
                }
            }
            ImageSaver.raytracer.getWorld().geometries.add(p);

        } catch (NumberFormatException e) {
            System.out.println("ZahlenFehler");
        }

        this.close();
    }
}
