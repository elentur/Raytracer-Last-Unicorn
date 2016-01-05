package UI;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import raytracer.ImageSaver;
import sampling.LightShadowPattern;

/**
 * Created by Marcus Baetz on 23.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewLightStage extends Stage {
    private final ColorPicker cpColorPicker;
    private final NumberTextField[] txtInputs;
    private final Light light;
    private final TextField txtName;
    private final ChoiceBox<String> chbLight;
    private final Slider sldAngle;
    private final CheckBox chkShadows;

    public NewLightStage(Light l) {
        super();
        this.light = l;
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);

        final Label lblInfo = new Label("Select a Light");

        txtName = new TextField();
        final Label lblName = new Label("Name");
        cpColorPicker = new ColorPicker(javafx.scene.paint.Color.WHITE);
        final Label lblColorPicker = new Label("Color:");
        chbLight = new ChoiceBox<>();
        final Label lblMaterial = new Label("Choose Light:");
        chbLight.getItems().addAll("Directional Light", "Pointlight", "Spotlight");
        chbLight.getSelectionModel().select(1);
        sldAngle = new Slider();
        sldAngle.setMin(1);
        sldAngle.setMax(90);
        sldAngle.setValue(45);
        chkShadows = new CheckBox("Cast Shadows");

        sldAngle.disableProperty().bind(chbLight.getSelectionModel().selectedIndexProperty().isEqualTo(2).not());

        final Label lblExp = new Label();
        lblExp.textProperty().bind(Bindings.concat("Angle: ").concat(Bindings.format("%.0f", sldAngle.valueProperty()).concat("°")));


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK(
                cpColorPicker.getValue(),
                chbLight.getSelectionModel().getSelectedIndex(),
                (int) sldAngle.getValue()));
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a -> onCancel());
        if (ImageSaver.raytracer.getWorld() == null) {
            lblInfo.setText("No Scene Created!");
            lblInfo.setTextFill(javafx.scene.paint.Color.RED);
            btnOK.setDisable(true);
        }
        final Label lblPosition = new Label("Position");
        final Label lblDirection = new Label("Direction");
        final Label lblX = new Label("x");
        final Label lblY = new Label("y");
        final Label lblZ = new Label("z");
        txtInputs = new NumberTextField[6];
        for (int i = 0; i < 6; i++) {
            txtInputs[i] = new NumberTextField(0.0);
            center.add(txtInputs[i], (i % 3) + 1, (i / 3) + 3);
        }


        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK, btnCancel);
        center.add(lblMaterial, 0, 0);
        center.add(chbLight, 1, 0);
        center.add(lblName, 2, 0);
        center.add(txtName, 3, 0);
        center.add(lblColorPicker, 0, 1);
        center.add(cpColorPicker, 1, 1);
        center.add(lblX, 1, 2);
        center.add(lblY, 2, 2);
        center.add(lblZ, 3, 2);
        center.add(lblPosition, 0, 3);
        center.add(lblDirection, 0, 4);
        center.add(lblExp, 0, 5);
        center.add(sldAngle, 1, 5);
        center.add(chkShadows,0,6);

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
        this.setTitle("Create new Material");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void setValues() {
        if (light == null) {
            int index = 1;
            if (ImageSaver.raytracer.getWorld() != null) {
                for (Light l : ImageSaver.raytracer.getWorld().lights)
                    if (l instanceof PointLight) index++;
            }
            txtName.setText("Pointlight" + index);


            txtInputs[1].setNumber(5.0);
            txtInputs[2].setNumber(10.0);
            txtInputs[4].setNumber(-5.0);
            txtInputs[4].setNumber(-10.0);
        } else {
            if (light instanceof PointLight) {
                PointLight l = (PointLight) light;
                chbLight.getSelectionModel().select(1);
                txtInputs[0].setNumber(l.position.x);
                txtInputs[1].setNumber(l.position.y);
                txtInputs[2].setNumber(l.position.z);
                chkShadows.setSelected(l.castsShadow);
            } else if (light instanceof DirectionalLight) {
                DirectionalLight l = (DirectionalLight) light;
                chbLight.getSelectionModel().select(0);
                txtInputs[3].setNumber(l.direction.x);
                txtInputs[4].setNumber(l.direction.y);
                txtInputs[5].setNumber(l.direction.z);
                chkShadows.setSelected(l.castsShadow);
            } else {
                SpotLight l = (SpotLight) light;
                chbLight.getSelectionModel().select(2);
                txtInputs[0].setNumber(l.position.x);
                txtInputs[1].setNumber(l.position.y);
                txtInputs[2].setNumber(l.position.z);
                txtInputs[3].setNumber(l.direction.x);
                txtInputs[4].setNumber(l.direction.y);
                txtInputs[5].setNumber(l.direction.z);
                chkShadows.setSelected(l.castsShadow);
                sldAngle.setValue((int) (l.halfAngle * (180 / Math.PI)));

            }
            Color c = new Color(light.color.r, light.color.g, light.color.b, 1);
            cpColorPicker.setValue(c);
            txtName.setText(light.name);
        }

    }

    private void onCancel() {
        this.close();
    }

    private void onOK(Color c, int typ, int angle) {
        try {
            if (light != null) ImageSaver.raytracer.getWorld().lights.remove(light);
            Point3 pos = new Point3(
                    txtInputs[0].getDouble(),
                    txtInputs[1].getDouble(),
                    txtInputs[2].getDouble());
            Vector3 dir = new Vector3(
                    txtInputs[3].getDouble(),
                    txtInputs[4].getDouble(),
                    txtInputs[5].getDouble());
            Light l = null;
            if (typ == 0) {
                l = new DirectionalLight(new utils.Color(c.getRed(), c.getGreen(), c.getBlue()), dir,chkShadows.isSelected(),500, new LightShadowPattern(0,1));
            } else if (typ == 2) {
                double a = sldAngle.getValue() / (180 / Math.PI);
                l = new SpotLight(new utils.Color(c.getRed(), c.getGreen(), c.getBlue()), pos, dir, a,chkShadows.isSelected(),500, new LightShadowPattern(0,1));
            } else {
                l = new PointLight(new utils.Color(c.getRed(), c.getGreen(), c.getBlue()), pos,chkShadows.isSelected(),500, new LightShadowPattern(0,1));
            }
            l.name = txtName.getText();
            int index = 1;
            boolean run = false;
            for (Light li : ImageSaver.raytracer.getWorld().lights) {
                if (li.name.equals(l.name)) run = true;
            }
            while (run) {
                int i = index;
                for (Light li : ImageSaver.raytracer.getWorld().lights) {
                    if (li.name == li.name + index) index++;
                }
                if (i == index) {
                    run = false;
                    l.name = l.name + index;
                }
            }

            ImageSaver.raytracer.getWorld().lights.add(l);

        } catch (NumberFormatException e) {
            System.out.println("ZahlenFehler");
        }
        this.close();
    }
}

