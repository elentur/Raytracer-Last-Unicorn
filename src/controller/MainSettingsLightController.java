package controller;

import UI.NumberTextField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import sampling.LightShadowPattern;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 07.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsLightController extends AController {
    @FXML
    private VBox lightView;
    @FXML
    private ColorPicker clpLightColor;
    @FXML
    private CheckBox chkCastShadows;
    @FXML
    private NumberTextField txtIrradiance;
    @FXML
    private NumberTextField txtLightSize;
    @FXML
    private NumberTextField txtLightSizeSubdiv;
    @FXML
    private NumberTextField txtDirectionX;
    @FXML
    private NumberTextField txtDirectionY;
    @FXML
    private NumberTextField txtDirectionZ;
    @FXML
    private NumberTextField txtPositionX;
    @FXML
    private NumberTextField txtPositionY;
    @FXML
    private NumberTextField txtPositionZ;
    @FXML
    private Slider sldAngle;
    @FXML
    private Label lblAngle;
    private boolean initialized = false;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        if (!initialized) {
            VBox v;
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            try {
                if (selectedTreeItem.get().getValue() instanceof DirectionalLight) {
                    v = loader.load(getClass().getResource("/fxml/mainSettingsDirectionalLightView.fxml"));
                    lightView.getChildren().add(v);

                } else if (selectedTreeItem.get().getValue() instanceof PointLight) {
                    v = loader.load(getClass().getResource("/fxml/mainSettingsPointLightView.fxml"));
                    lightView.getChildren().add(v);
                } else {
                    v = loader.load(getClass().getResource("/fxml/mainSettingsPointLightView.fxml"));
                    lightView.getChildren().add(v);
                    v = loader.load(getClass().getResource("/fxml/mainSettingsDirectionalLightView.fxml"));
                    lightView.getChildren().add(v);
                    v = loader.load(getClass().getResource("/fxml/mainSettingsSpotLightView.fxml"));
                    lightView.getChildren().add(v);
                }

                sldAngle = (Slider) lightView.lookup("#sldAngle");
                lblAngle = (Label) lightView.lookup("#lblAngle");
                txtPositionX = (NumberTextField) lightView.lookup("#txtPositionX");
                txtPositionY = (NumberTextField) lightView.lookup("#txtPositionY");
                txtPositionZ = (NumberTextField) lightView.lookup("#txtPositionZ");
                txtDirectionX = (NumberTextField) lightView.lookup("#txtDirectionX");
                txtDirectionY = (NumberTextField) lightView.lookup("#txtDirectionY");
                txtDirectionZ = (NumberTextField) lightView.lookup("#txtDirectionZ");
            } catch (
                    IOException e
                    )

            {
                e.printStackTrace();
            }

            initializeFields();

            initialized = true;
        }
    }

    private void initializeFields() {
        Light l = (Light) selectedTreeItem.get().getValue();


        if (txtPositionX != null) {
            Point3 pos = l instanceof PointLight ? ((PointLight) l).position : ((SpotLight) l).position;

            txtPositionX.setNumber((pos.x));
            txtPositionX.setOnAction(a -> handleUpdateLight());
            txtPositionY.setNumber(pos.y);
            txtPositionY.setOnAction(a -> handleUpdateLight());
            txtPositionZ.setNumber(pos.z);
            txtPositionZ.setOnAction(a -> handleUpdateLight());
        }
        if (txtDirectionX != null) {
            Vector3 dir = l instanceof DirectionalLight ? ((DirectionalLight) l).direction : ((SpotLight) l).direction;

            txtDirectionX.setNumber(dir.x);
            txtDirectionX.setOnAction(a -> handleUpdateLight());
            txtDirectionY.setNumber(dir.y);
            txtDirectionY.setOnAction(a -> handleUpdateLight());
            txtDirectionZ.setNumber(dir.z);
            txtDirectionZ.setOnAction(a -> handleUpdateLight());
        }

        if (sldAngle != null) {
            Double angle = ((SpotLight) l).halfAngle;
            angle = angle * (180 / Math.PI);
            lblAngle.textProperty().bind(Bindings.concat("Opening Angle: ").concat(Bindings.format("%.1f", sldAngle.valueProperty())).concat("°"));
            sldAngle.setMin(1);
            sldAngle.setMax(90);
            sldAngle.setValue(angle);
            sldAngle.setOnMouseReleased(a -> handleUpdateLight());
        }
        clpLightColor.setValue(new Color(l.color.r, l.color.g, l.color.b, 1));
        chkCastShadows.setSelected(l.castsShadow);
        txtIrradiance.setNumber(l.photons);
        txtLightSize.setNumber(l.lightShadowPattern.size);
        txtLightSizeSubdiv.setNumber(l.lightShadowPattern.subdiv);
    }

    @FXML
    private void handleUpdateLight() {
        if (selectedTreeItem.get().getValue() != null) {
            Light light = null;
            utils.Color color = new utils.Color(clpLightColor.getValue().getRed(), clpLightColor.getValue().getGreen(), clpLightColor.getValue().getBlue());
            boolean castShadows = chkCastShadows.isSelected();
            int irrad = txtIrradiance.getInteger();
            if (selectedTreeItem.get().getValue() instanceof DirectionalLight) {
                light = new DirectionalLight(color,
                        new Vector3(txtDirectionX.getDouble(), txtDirectionY.getDouble(), txtDirectionZ.getDouble()),
                        castShadows,
                        irrad,
                        new LightShadowPattern(txtLightSize.getDouble(),txtLightSizeSubdiv.getInteger())
                );
            } else if (selectedTreeItem.get().getValue() instanceof PointLight) {
                light = new PointLight(color,
                        new Point3(txtPositionX.getDouble(), txtPositionY.getDouble(), txtPositionZ.getDouble()),
                        castShadows,
                        irrad,
                        new LightShadowPattern(txtLightSize.getDouble(),txtLightSizeSubdiv.getInteger())
                );
            } else if (selectedTreeItem.get().getValue() instanceof SpotLight) {
                light = new SpotLight(color,
                        new Point3(txtPositionX.getDouble(), txtPositionY.getDouble(), txtPositionZ.getDouble()),
                        new Vector3(txtDirectionX.getDouble(), txtDirectionY.getDouble(), txtDirectionZ.getDouble()),
                        sldAngle.getValue() / (180 / Math.PI),
                        castShadows,
                        irrad,
                        new LightShadowPattern(txtLightSize.getDouble(),txtLightSizeSubdiv.getInteger())
                );
            }
            if (light != null) {
                light.name = selectedTreeItem.get().getValue().name;
                elementLists.updateElement(selectedTreeItem.get().getValue(), light);
                // NodeTreeViewController.updateElement(light);
            }
        }
    }
}
