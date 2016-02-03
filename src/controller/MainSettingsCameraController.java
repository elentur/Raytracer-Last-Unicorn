package controller;

import UI.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import observables.cameras.AOCamera;
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

                    v = loader.load(this.getClass().getResource("/fxml/mainSettingsOrthographicCameraView.fxml"));
                    cameraView.getChildren().add(v);
                } else if (selectedTreeItem.get().getValue() instanceof OPerspectiveCamera) {
                    v = loader.load(this.getClass().getResource("/fxml/mainSettingsPerspectiveCameraView.fxml"));
                    cameraView.getChildren().add(v);
                } else {
                    v = loader.load(this.getClass().getResource("/fxml/mainSettingsDOFCameraView.fxml"));
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
        /*txtPositionX.setNumber(c.ex);
        txtPositionY.setNumber(c.e.y);
        txtPositionZ.setNumber(c.e.z);
        txtDirectionX.setNumber(c.g.x);
        txtDirectionY.setNumber(c.g.y);
        txtDirectionZ.setNumber(c.g.z);
        txtUpVectorX.setNumber(c.t.x);
        txtUpVectorY.setNumber(c.t.y);
        txtUpVectorZ.setNumber(c.t.z);
        txtSampling.setNumber(c.samplingPattern.subdiv);
        if (txtScaleFactor != null) {
            txtScaleFactor.setNumber(((OrthographicCamera) c).s);
            txtScaleFactor.setOnAction(a -> handleUpdateCamera());
        }
        if (sldAngle != null) {
            Double angle = c instanceof DOFCamera ? ((DOFCamera) c).angle : ((PerspectiveCamera) c).angle;
            angle = angle * (180 / Math.PI);
            lblAngle.textProperty().bind(Bindings.concat("Opening Angle: ").concat(Bindings.format("%.1f", sldAngle.valueProperty())).concat("°"));
            sldAngle.setMin(1);
            sldAngle.setMax(90);
            sldAngle.setValue(angle);
            sldAngle.setOnMouseReleased(a -> handleUpdateCamera());
        }
        if (txtFStop != null) {
            txtFStop.setNumber(((DOFCamera)c).dofPattern.size);
            txtFStop.setOnAction(a -> handleUpdateCamera());
        }
        if (txtFocalLength != null) {
            txtFocalLength.setNumber(((DOFCamera)c).focalLength);
            txtFocalLength.setOnAction(a -> handleUpdateCamera());
        }
        if (txtSubdiv != null) {
            txtSubdiv.setNumber(((DOFCamera)c).dofPattern.subdiv);
            txtSubdiv.setOnAction(a -> handleUpdateCamera());

        }*/
    }

    @FXML
    private void handleUpdateCamera() {

    }


}
