package UI;

import geometries.Geometry;
import geometries.Triangle;
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
import texture.SingleColorTexture;
import texture.TexCoord2;
import utils.Color;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewTriangleStage extends NewGeoStage {

    private final NumberTextField[] txtInputs;
    private Triangle t;
    private final TextField txtName;

    public NewTriangleStage(Triangle t) {
        super();
        this.t = t;
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
        final Label lblInfo = new Label("Do you wish to create a new Triangle?");


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


        final Label lblEdgeA = new Label("Edge A");
        final Label lblEdgeB = new Label("Edge B");
        final Label lblEdgeC = new Label("Edge C");
        final Label lblX = new Label("x");
        final Label lblY = new Label("y");
        final Label lblZ = new Label("z");
        txtInputs = new NumberTextField[9];
        for (int i = 0; i < 9; i++) {
            txtInputs[i] = new NumberTextField(0.0);
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
        center.add(lblEdgeA, 0, 2);
        center.add(lblEdgeB, 0, 3);
        center.add(lblEdgeC, 0, 4);

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
        this.setTitle("Create new Triangle?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void setValues() {
        if (t == null) {
            int index = 1;
            if (ImageSaver.raytracer.getWorld() != null) {
                for (Geometry g : ImageSaver.raytracer.getWorld().geometries)
                    if (g instanceof Triangle) index++;
            }
            txtName.setText("Triangle" + index);
            txtInputs[0].setNumber(-0.5);
            txtInputs[1].setNumber(0.5);
            txtInputs[2].setNumber(-3.0);
            txtInputs[3].setNumber(0.5);
            txtInputs[4].setNumber(0.5);
            txtInputs[5].setNumber(-3.0);
            txtInputs[6].setNumber(0.5);
            txtInputs[7].setNumber(-0.5);
            txtInputs[8].setNumber(-3.0);
            material.set(new LambertMaterial(new SingleColorTexture(new Color(0.5, 0.5, 0.5))));
        } else {
            txtName.setText(t.name);
            txtInputs[0].setNumber(t.a.x);
            txtInputs[1].setNumber(t.a.y);
            txtInputs[2].setNumber(t.a.z);
            txtInputs[3].setNumber(t.b.x);
            txtInputs[4].setNumber(t.b.y);
            txtInputs[5].setNumber(t.b.z);
            txtInputs[6].setNumber(t.c.x);
            txtInputs[7].setNumber(t.c.y);
            txtInputs[8].setNumber(t.c.z);
            material.set(t.material);
            // cpColorPicker.setValue(new javafx.scene.paint.Color(t.material.r, t.material.g, t.material.b, 1));
        }
    }

    private void onCancel() {
        this.close();
    }

    private void onOK() {
        try {
            if (t != null) ImageSaver.raytracer.getWorld().geometries.remove(t);
            Point3 edgeA = new Point3(
                    txtInputs[0].getDouble(),
                    txtInputs[1].getDouble(),
                    txtInputs[2].getDouble());
            Point3 edgeB = new Point3(
                    txtInputs[3].getDouble(),
                    txtInputs[4].getDouble(),
                    txtInputs[5].getDouble());
            Point3 edgeC = new Point3(
                    txtInputs[6].getDouble(),
                    txtInputs[7].getDouble(),
                    txtInputs[8].getDouble());


            Triangle p = new Triangle(edgeA, edgeB, edgeC, material.get(),new TexCoord2(1,1),new TexCoord2(1,1),new TexCoord2(1,1),true,true);
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
