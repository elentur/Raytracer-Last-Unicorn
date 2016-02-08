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
import observables.lights.AOLight;
import observables.lights.ODirectionalLight;
import observables.lights.OPointLight;
import observables.lights.OSpotLight;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 07.01.2016.
 *
 * @author Marcus Bätz
 *
 * The Controller for the Light settings
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
                if (selectedTreeItem.get().getValue() instanceof ODirectionalLight) {
                    v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsDirectionalLightView.fxml"));
                    lightView.getChildren().add(v);
                    lightView.getChildren().removeAll(
                            txtLightSize.getParent(),
                            txtLightSizeSubdiv.getParent()

                    );
                } else if (selectedTreeItem.get().getValue() instanceof OPointLight) {
                    v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsPointLightView.fxml"));
                    lightView.getChildren().add(v);
                } else {
                    v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsPointLightView.fxml"));
                    lightView.getChildren().add(v);
                    v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsDirectionalLightView.fxml"));
                    lightView.getChildren().add(v);
                    v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsSpotLightView.fxml"));
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
    /**
     * setup all FieldValues and binds them to the related Object. And sets all necessary actions
     */
    private void initializeFields() {
        final AOLight l = (AOLight) selectedTreeItem.get().getValue();
        clpLightColor.valueProperty().bindBidirectional(l.color);
        if (txtPositionX != null) {
            if (l instanceof OPointLight) {
                OPointLight p = (OPointLight) l;
                txtPositionX.doubleProperty.bindBidirectional(p.px);
                txtPositionY.doubleProperty.bindBidirectional(p.py);
                txtPositionZ.doubleProperty.bindBidirectional(p.pz);
            } else {
                OSpotLight p = (OSpotLight) l;
                txtPositionX.doubleProperty.bindBidirectional(p.px);
                txtPositionY.doubleProperty.bindBidirectional(p.py);
                txtPositionZ.doubleProperty.bindBidirectional(p.pz);
            }


        }
        if (txtDirectionX != null) {
            if (l instanceof ODirectionalLight) {
                ODirectionalLight d = (ODirectionalLight) l;
                txtDirectionX.doubleProperty.bindBidirectional(d.dx);
                txtDirectionY.doubleProperty.bindBidirectional(d.dy);
                txtDirectionZ.doubleProperty.bindBidirectional(d.dz);
            } else {
                OSpotLight d = (OSpotLight) l;
                txtDirectionX.doubleProperty.bindBidirectional(d.dx);
                txtDirectionY.doubleProperty.bindBidirectional(d.dy);
                txtDirectionZ.doubleProperty.bindBidirectional(d.dz);
            }

        }

        if (sldAngle != null) {
            lblAngle.textProperty().bind(Bindings.concat("Opening Angle: ").concat(Bindings.format("%.1f", sldAngle.valueProperty())).concat("°"));
            sldAngle.setMin(1);
            sldAngle.setMax(90);
            sldAngle.valueProperty().bindBidirectional(((OSpotLight) l).halfAngle);
        }
        chkCastShadows.selectedProperty().bindBidirectional(l.castShadow);
        txtIrradiance.doubleProperty.bindBidirectional(l.photons);
        txtLightSize.doubleProperty.bindBidirectional(l.patternSize);
        txtLightSizeSubdiv.doubleProperty.bindBidirectional(l.patternSubdiv);
    }

}
