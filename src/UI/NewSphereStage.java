package UI;

import geometries.Geometry;
import geometries.Sphere;
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
import raytracer.ImageSaver;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewSphereStage extends NewGeoStage {

    private final NumberTextField[] txtInputs;
    private final TextField txtName;
    private Sphere s;

    public NewSphereStage(Sphere s) {
        super();
        this.s = s;
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

        final Button btnMaterial = new Button("new Material");
        btnMaterial.setOnAction(a-> new NewMaterialStage(this));
        final Label lblColorPicker = new Label("Material:");

        txtName = new TextField();

        final Label lblName = new Label("Name");

        final Label lblInfo = new Label("Do you wish to create a new Sphere?");


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


        final Label lblTranslate = new Label("Translation");
        final Label lblRadius = new Label("Radius");
        final Label lblX = new Label("x");
        final Label lblY = new Label("y");
        final Label lblZ = new Label("z");
        txtInputs = new NumberTextField[4];
        for (int i = 0; i < 4; i++) {
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
        center.add(lblTranslate, 0, 2);
        center.add(lblRadius, 0, 3);

        setValues();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 600, 300);
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("Create new Sphere?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void setValues() {
        if (s == null) {
            int index = 1;
            if (ImageSaver.raytracer.getWorld() != null) {
                for (Geometry g : ImageSaver.raytracer.getWorld().geometries)
                    if (g instanceof Sphere) index++;
            }
            txtName.setText("Sphere" + index);

            txtInputs[0].setText("0.0");
            txtInputs[1].setText("0.0");
            txtInputs[2].setText("-3.0");
            txtInputs[3].setText("0.5");
        } else {
            txtName.setText(s.name);
            txtInputs[0].setText(s.c.x + "");
            txtInputs[1].setText(s.c.y + "");
            txtInputs[2].setText(s.c.z + "");
            txtInputs[3].setText(s.r + "");
            material = s.material;
           // cpColorPicker.setValue(new javafx.scene.paint.Color(s.material.r, s.material.g, s.material.b, 1));
        }

    }

    private void onCancel() {
        this.close();
    }

    private void onOK() {
        try {
            if (s != null) ImageSaver.raytracer.getWorld().geometries.remove(s);
            Point3 center = new Point3(
                    Double.parseDouble(txtInputs[0].getText()),
                    Double.parseDouble(txtInputs[1].getText()),
                    Double.parseDouble(txtInputs[2].getText()));
            double r = Double.parseDouble(txtInputs[3].getText());
            if (r <= 0.0) r = 1.0;
            Sphere p = new Sphere(center, r, material);
            p.name = txtName.getText();
            int index = 1;
            boolean run = false;
            for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                if (g.name.equals(p.name)) run = true;
            }
            while (run) {
                int i = index;
                for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                    if (g.name == p.name + index) index++;
                }
                if (i == index) {
                    run = false;
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
