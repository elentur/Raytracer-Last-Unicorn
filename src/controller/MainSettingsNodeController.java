package controller;

import UI.MaterialView;
import UI.NumberTextField;
import geometries.Geometry;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import observables.AOElement;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import observables.geometries.OShapeFromFile;
import observables.materials.AOMaterial;
import observables.materials.DefaultMaterial;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 07.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsNodeController extends AController {
    @FXML
    private VBox nodeView;
    @FXML
    private NumberTextField txtTranslationX;
    @FXML
    private NumberTextField txtTranslationY;
    @FXML
    private NumberTextField txtTranslationZ;
    @FXML
    private NumberTextField txtScalingX;
    @FXML
    private NumberTextField txtScalingY;
    @FXML
    private NumberTextField txtScalingZ;
    @FXML
    private NumberTextField txtRotationX;
    @FXML
    private NumberTextField txtRotationY;
    @FXML
    private NumberTextField txtRotationZ;
    @FXML
    private HBox materialViewHBox;
    @FXML
    private ComboBox<AOMaterial> cmbMaterial;
    @FXML
    private CheckBox chkCastShadows;
    @FXML
    private CheckBox chkReceiveShadows;
    @FXML
    private CheckBox chkVisible;
    @FXML
    private CheckBox chkFlipNormals;
    @FXML
    private TextField txtPath;
    @FXML
    private Button btnNewPath;

    private boolean initialized = false;

    public MainSettingsNodeController() {
        if (materialList.isEmpty()) materialList.addAll(
                DefaultMaterial.getSingleColorMaterial(),
                DefaultMaterial.getLambert(),
                DefaultMaterial.getOrenNayar(),
                DefaultMaterial.getPhong(),
                DefaultMaterial.getReflectiveMaterial(),
                DefaultMaterial.getTransparentMaterial(),
                DefaultMaterial.getDefaultLambert()
        );
    }

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
                if (((ONode) selectedTreeItem.get().getValue()).oGeos.get(0) instanceof OShapeFromFile) {
                    v = loader.load(getClass().getResource("/fxml/mainSettingsShapeFromFileView.fxml"));
                    nodeView.getChildren().add(0, v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            txtPath = (TextField) nodeView.lookup("#txtPath");
            btnNewPath = (Button) nodeView.lookup("#btnNewPath");

            initializeFields();
            initialized = true;
        }
    }

    private void initializeFields() {
        TabPane t = null;
        ONode n = (ONode) selectedTreeItem.get().getValue();
        txtTranslationX.doubleProperty.bindBidirectional(n.translationx);
        txtTranslationY.doubleProperty.bindBidirectional(n.translationy);
        txtTranslationZ.doubleProperty.bindBidirectional(n.translationz);
        txtScalingX.doubleProperty.bindBidirectional(n.scalingx);
        txtScalingY.doubleProperty.bindBidirectional(n.scalingy);
        txtScalingZ.doubleProperty.bindBidirectional(n.scalingz);
        txtRotationX.doubleProperty.bindBidirectional(n.rotationx);
        txtRotationY.doubleProperty.bindBidirectional(n.rotationy);
        txtRotationZ.doubleProperty.bindBidirectional(n.rotationz);
        //material.bindBidirectional(((ONode) selectedTreeItem.get().getValue()).oGeos.get(0).material);
        material.setValue(((ONode) selectedTreeItem.get().getValue()).oGeos.get(0).material.getValue());

        cmbMaterial.setItems(materialList);
        chkCastShadows.selectedProperty().bindBidirectional(n.castShadows);
        chkReceiveShadows.selectedProperty().bindBidirectional(n.reciveShadows);
        chkVisible.selectedProperty().bindBidirectional(n.visibility);
        chkFlipNormals.selectedProperty().bindBidirectional(n.flipNormal);
        if (!(n.oGeos.size() == 1 && !(n.oGeos.get(0) instanceof ONode))) {
            nodeView.getChildren().removeAll(
                    chkFlipNormals.getParent(),
                    chkCastShadows.getParent(),
                    chkReceiveShadows.getParent()

            );

        }
        if (txtPath != null) {
            txtPath.textProperty().bindBidirectional(((OShapeFromFile) n.oGeos.get(0)).path);
        }
        if (btnNewPath != null) {
            btnNewPath.setOnAction(a -> newPathLoad());
        }
        setMaterialComboBox();
        MaterialView materialView = MaterialView.getInstance();
        materialView.setUpTracer(material);
        materialViewHBox.getChildren().add(materialView);
    }

    private void newPathLoad() {
        FileChooser dlg = new FileChooser();
        dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront obj File. (*.obj)", "*.obj"));
        File file = dlg.showOpenDialog(materialViewHBox.getScene().getWindow());
        if (file != null) {
            txtPath.setText(file.getPath());
        }
    }

    private void setMaterialComboBox() {
        if (((ONode) selectedTreeItem.get().getValue()).oGeos.get(0) instanceof ONode) {
            ((HBox) cmbMaterial.getParent()).getChildren().remove(cmbMaterial);
           // ((HBox) materialView.getParent()).getChildren().remove(materialView);
        }
        cmbMaterial.setCellFactory(new Callback<ListView<AOMaterial>, ListCell<AOMaterial>>() {
            @Override
            public ListCell<AOMaterial> call(ListView<AOMaterial> c) {

                return new ListCell<AOMaterial>() {
                    @Override
                    protected void updateItem(AOMaterial item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String prefix = "";
                            if (this.getIndex() < 6) prefix = "New";
                            setText(prefix + " " + item.name.get());
                        }
                    }
                };
            }
        });

        cmbMaterial.setButtonCell(new ListCell<AOMaterial>() {
            @Override
            protected void updateItem(AOMaterial item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    textProperty().unbind();
                    setText("");
                } else {
                   // String prefix = "";
                   //if (this.getIndex() < 6) prefix = "New";
                  //  setText(prefix + " " + item.name.get());
                    textProperty().bind(item.name);
                }
            }
        });
        cmbMaterial.getSelectionModel().select(material.get());


    }

    @FXML
    private void handleUpdateNode(Event a) {
        updateNode(null);
    }

    private void updateNode(List<Geometry> geos) {

    }
    public void handleComboBoxMaterialAction(ActionEvent actionEvent) {

        AOMaterial m = cmbMaterial.getSelectionModel().getSelectedItem();
        if (cmbMaterial.getSelectionModel().getSelectedIndex() < 6) {
            try {
                m =  cmbMaterial.getSelectionModel().getSelectedItem().getClass().newInstance();
                materialList.add(m);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }

        }
        material.setValue(m);
        AOGeometry g = ((ONode) selectedTreeItem.get().getValue()).oGeos.get(0);
        g.material.set(m);
        TreeItem<AOElement> treeItem = selectedTreeItem.getValue();
        selectedTreeItem.setValue(null);
        selectedTreeItem.setValue(treeItem);

    }
}
