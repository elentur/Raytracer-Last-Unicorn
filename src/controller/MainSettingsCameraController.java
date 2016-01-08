package controller;

import UI.NumberTextField;
import camera.Camera;
import camera.DOFCamera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;

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
    private NumberTextField txtAperture;


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
                if (selectedElement.get() instanceof OrthographicCamera) {

                    v = loader.load(this.getClass().getResource("/fxml/mainSettingsOrthographicCameraView.fxml"));
                    cameraView.getChildren().add(v);
                } else if (selectedElement.get() instanceof PerspectiveCamera) {
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
            txtAperture = (NumberTextField) cameraView.lookup("#txtAperture");
            txtScaleFactor = (NumberTextField) cameraView.lookup("#txtScaleFactor");

            initializeFields();
            initialized = true;
        }
    }

    private void initializeFields() {
        Camera c = (Camera) selectedElement.get();
        txtPositionX.setNumber(c.e.x);
        txtPositionY.setNumber(c.e.y);
        txtPositionZ.setNumber(c.e.z);
        txtDirectionX.setNumber(c.g.x);
        txtDirectionY.setNumber(c.g.y);
        txtDirectionZ.setNumber(c.g.z);
        txtUpVectorX.setNumber(c.t.x);
        txtUpVectorY.setNumber(c.t.y);
        txtUpVectorZ.setNumber(c.t.z);
        txtSampling.setNumber(c.samplingPattern.size);
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
            txtFStop.setNumber(5.0);
            txtFStop.setOnAction(a -> handleUpdateCamera());
        }
        if (txtAperture != null) {
            txtAperture.setNumber(8.0);
            txtAperture.setOnAction(a -> handleUpdateCamera());
        }
    }

    @FXML
    private void handleUpdateCamera() {
        Camera camera = null;
        Point3 e = new Point3(txtPositionX.getDouble(), txtPositionY.getDouble(), txtPositionZ.getDouble());
        Vector3 g = new Vector3(txtDirectionX.getDouble(), txtDirectionY.getDouble(), txtDirectionZ.getDouble());
        Vector3 t = new Vector3(txtUpVectorX.getDouble(), txtUpVectorY.getDouble(), txtUpVectorZ.getDouble());
        SamplingPattern pattern = new SamplingPattern(txtSampling.getInteger());

        if (selectedElement.get() instanceof OrthographicCamera) {
            camera = new OrthographicCamera(e, g, t,
                    txtScaleFactor.getDouble(),
                    pattern
            );
        } else if (selectedElement.get() instanceof PerspectiveCamera) {
            camera = new PerspectiveCamera(e, g, t,
                    sldAngle.getValue() / (180 / Math.PI),
                    pattern
            );
        } else if (selectedElement.get() instanceof DOFCamera) {
            camera = new DOFCamera(e, g, t,
                    sldAngle.getValue() / (180 / Math.PI),
                    txtFStop.getDouble(),
                    txtAperture.getDouble() / (180 / Math.PI),
                    pattern
            );
        }
        if (camera != null) {
            camera.name = selectedElement.get().name;
            NodeTreeViewController.updateElement(camera);
        }
    }


}
