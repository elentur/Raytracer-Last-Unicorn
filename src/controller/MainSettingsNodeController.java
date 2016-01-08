package controller;

import UI.MaterialView;
import UI.NumberTextField;
import geometries.Geometry;
import geometries.Node;
import geometries.ShapeFromFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import matVect.Point3;
import material.DefaultMaterial;
import material.Material;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private MaterialView materialView;
    @FXML
    private ComboBox<Material> cmbMaterial;
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
        if(materialList.isEmpty())materialList.addAll(
                DefaultMaterial.SINGLECOLORMATERIAL,
                DefaultMaterial.LAMBERTMATERIAL,
                DefaultMaterial.ORENNAYARMATERIAL,
                DefaultMaterial.PHONGMATERIAL,
                DefaultMaterial.REFLECTIVEMATERIAL,
                DefaultMaterial.TRANSPARENTMATERIAL,
                DefaultMaterial.MATERIAL);
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
                if (((Node) selectedElement.get()).geos.get(0) instanceof ShapeFromFile) {
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
        Node n = (Node) selectedElement.get();
        txtTranslationX.setNumber(n.translation.x);
        txtTranslationY.setNumber(n.translation.y);
        txtTranslationZ.setNumber(n.translation.z);
        txtScalingX.setNumber(n.scaling.x);
        txtScalingY.setNumber(n.scaling.y);
        txtScalingZ.setNumber(n.scaling.z);
        txtRotationX.setNumber(n.rotation.x * (180 / Math.PI));
        txtRotationY.setNumber(n.rotation.y * (180 / Math.PI));
        txtRotationZ.setNumber(n.rotation.z * (180 / Math.PI));
        material.setValue(((Node) selectedElement.get()).geos.get(0).material);
        materialView.setUpTracer(material);
        cmbMaterial.setItems(materialList);
        chkCastShadows.setSelected(n.castShadows);
        chkReceiveShadows.setSelected(n.reciveShadows);
        chkVisible.setSelected(n.visibility);
        chkFlipNormals.setSelected(n.flipNormal);
        if (txtPath != null) {
            txtPath.setText(((ShapeFromFile) n.geos.get(0)).file.getPath());
        }
        if (btnNewPath != null) {
            btnNewPath.setOnAction(a -> {
            });
        }
        setMaterialComboBox();
    }

    private void setMaterialComboBox() {
        if(((Node) selectedElement.get()).geos.get(0) instanceof Node){
            ((HBox)cmbMaterial.getParent()).getChildren().remove(cmbMaterial);
            ((HBox)materialView.getParent()).getChildren().remove(materialView);
        }
        cmbMaterial.setCellFactory(new Callback<ListView<Material>, ListCell<Material>>() {
            @Override
            public ListCell<Material> call(ListView<Material> c) {

                return new ListCell<Material>() {
                    @Override
                    protected void updateItem(Material item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String prefix="";
                            if(this.getIndex()<6)prefix="New";
                            if(this.getIndex()==6)prefix="Default";
                            setText(prefix+" " +item.name);
                        }
                    }
                };
            }
        });

        cmbMaterial.setButtonCell(new ListCell<Material>() {
            @Override
            protected void updateItem(Material item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    String prefix="";
                    if(this.getIndex()<6)prefix="New";
                    if(this.getIndex()==6)prefix="Default";
                    setText(prefix+" " +item.name);
                }
            }
        });
        cmbMaterial.getSelectionModel().select(material.get());


    }

    @FXML
    private void handleUpdateNode() {
        updateNode(null);
    }
    private void updateNode(List<Geometry> geos){
        if(geos == null) geos =((Node) selectedElement.get()).geos;
        Node node = new Node(
                new Point3(txtTranslationX.getDouble(), txtTranslationY.getDouble(), txtTranslationZ.getDouble()),
                new Point3(txtScalingX.getDouble(), txtScalingY.getDouble(), txtScalingZ.getDouble()),
                new Point3(txtRotationX.getDouble() / (180 / Math.PI), txtRotationY.getDouble() / (180 / Math.PI), txtRotationZ.getDouble() / (180 / Math.PI)),
                geos,
                chkReceiveShadows.isSelected(),
                chkCastShadows.isSelected(),
                chkVisible.isSelected(),
                chkFlipNormals.isSelected());
        if (node != null) {
            node.name = selectedElement.get().name;
            NodeTreeViewController.updateElement(node);
        }
    }

    public void handleComboBoxMaterialAction(ActionEvent actionEvent) {
        Material m = cmbMaterial.getSelectionModel().getSelectedItem();
        if(cmbMaterial.getSelectionModel().getSelectedIndex()<6){
            m=cmbMaterial.getSelectionModel().getSelectedItem().deepCopy();
            materialList.add(m);
            for(Material t: materialList ){
                if(t.equals(m)) System.out.println("true");
            }
        }
        Geometry g = ((Node) selectedElement.get()).geos.get(0);
        List<Geometry> geos = new ArrayList<>();
        geos.add(g.deepCopy(m));
        updateNode(geos);
    }
}
