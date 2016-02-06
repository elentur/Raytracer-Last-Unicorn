package controller;

import UI.NumberTextField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import observables.cameras.AOCamera;
import observables.cameras.ODOFCamera;
import observables.cameras.OOrthographicCamera;
import observables.cameras.OPerspectiveCamera;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsCameraController extends AController {
    @FXML
    private VBox cameraView;

    @FXML
    private NumberTextField txtPositionX;
    @FXML
    private NumberTextField txtPositionY;
    @FXML
    private NumberTextField txtPositionZ;
    @FXML
    private NumberTextField txtDirectionX;
    @FXML
    private NumberTextField txtDirectionY;
    @FXML
    private NumberTextField txtDirectionZ;
    @FXML
    private NumberTextField txtUpVectorX;
    @FXML
    private NumberTextField txtUpVectorY;
    @FXML
    private NumberTextField txtUpVectorZ;
    @FXML
    private NumberTextField txtSampling;
    @FXML
    private NumberTextField txtScaleFactor;
    @FXML
    private Slider sldAngle;
    @FXML
    private Label lblAngle;
    @FXML
    private NumberTextField txtFStop;
    @FXML
    private NumberTextField txtFocalLength;
    private NumberTextField txtSubdiv;


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
                if (selectedTreeItem.get().getValue() instanceof OOrthographicCamera) {

                    v = FXMLLoader.load(this.getClass().getResource("/fxml/mainSettingsOrthographicCameraView.fxml"));
                    cameraView.getChildren().add(v);
                } else if (selectedTreeItem.get().getValue() instanceof OPerspectiveCamera) {
                    v = FXMLLoader.load(this.getClass().getResource("/fxml/mainSettingsPerspectiveCameraView.fxml"));
                    cameraView.getChildren().add(v);
                } else {
                    v = FXMLLoader.load(this.getClass().getResource("/fxml/mainSettingsDOFCameraView.fxml"));
                    cameraView.getChildren().add(v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            sldAngle = (Slider) cameraView.lookup("#sldAngle");
            lblAngle = (Label) cameraView.lookup("#lblAngle");
            txtFStop = (NumberTextField) cameraView.lookup("#txtFStop");
            txtFocalLength = (NumberTextField) cameraView.lookup("#txtFocalLength");
            txtSubdiv = (NumberTextField) cameraView.lookup("#txtSubdiv");
            txtScaleFactor = (NumberTextField) cameraView.lookup("#txtScaleFactor");

            initializeFields();
            initialized = true;
        }
    }

    private void initializeFields() {
        AOCamera c = (AOCamera) selectedTreeItem.get().getValue();
        txtPositionX.doubleProperty.bindBidirectional(c.ex);
        txtPositionY.doubleProperty.bindBidirectional(c.ey);
        txtPositionZ.doubleProperty.bindBidirectional(c.ez);
        txtDirectionX.doubleProperty.bindBidirectional(c.gx);
        txtDirectionY.doubleProperty.bindBidirectional(c.gy);
        txtDirectionZ.doubleProperty.bindBidirectional(c.gz);
        txtUpVectorX.doubleProperty.bindBidirectional(c.tx);
        txtUpVectorY.doubleProperty.bindBidirectional(c.ty);
        txtUpVectorZ.doubleProperty.bindBidirectional(c.tz);
        txtSampling.doubleProperty.bindBidirectional(c.patternSubdiv);
        if (txtScaleFactor != null) {
            txtScaleFactor.doubleProperty.bindBidirectional(((OOrthographicCamera) c).s);
        }
        if (sldAngle != null) {

            lblAngle.textProperty().bind(Bindings.concat("Opening Angle: ").concat(Bindings.format("%.1f", sldAngle.valueProperty())).concat("°"));
            sldAngle.setMin(1);
            sldAngle.setMax(90);
            if (c instanceof OPerspectiveCamera) {
                sldAngle.valueProperty().bindBidirectional(((OPerspectiveCamera) c).angle);
            } else {
                sldAngle.valueProperty().bindBidirectional(((ODOFCamera) c).angle);
            }
        }
        if (txtFStop != null) {
            txtFStop.doubleProperty.bindBidirectional(((ODOFCamera) c).dPatternFStop);
        }
        if (txtFocalLength != null) {
            txtFocalLength.doubleProperty.bindBidirectional(((ODOFCamera) c).focalLength);
        }
        if (txtSubdiv != null) {
            txtSubdiv.doubleProperty.bindBidirectional(((ODOFCamera) c).dPatternSubdiv);

        }
    }


}
