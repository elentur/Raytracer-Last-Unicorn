package controller;

import UI.MaterialView;
import UI.NumberTextField;
import geometries.Geometry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import material.DefaultMaterial;
import observables.geometries.ONode;
import observables.geometries.OShapeFromFile;
import observables.materials.AOMaterial;

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
    private MaterialView materialView;
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
                DefaultMaterial.SINGLE_COLOR_MATERIAL,
                DefaultMaterial.LAMBERT_MATERIAL,
                DefaultMaterial.OREN_NAYAR_MATERIAL,
                DefaultMaterial.PHONG_MATERIAL,
                DefaultMaterial.REFLECTIVE_MATERIAL,
                DefaultMaterial.TRANSPARENT_MATERIAL,
                DefaultMaterial.MATERIAL);
        DefaultMaterial.MATERIAL.name="Default Lambert Material";
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
        /*txtTranslationX.setNumber(n.translation.x);
        txtTranslationY.setNumber(n.translation.y);
        txtTranslationZ.setNumber(n.translation.z);
        txtScalingX.setNumber(n.scaling.x);
        txtScalingY.setNumber(n.scaling.y);
        txtScalingZ.setNumber(n.scaling.z);
        txtRotationX.setNumber(n.rotation.x * (180 / Math.PI));
        txtRotationY.setNumber(n.rotation.y * (180 / Math.PI));
        txtRotationZ.setNumber(n.rotation.z * (180 / Math.PI));
        material.setValue(((Node) selectedTreeItem.get().getValue()).geos.get(0).material);
        materialView.setUpTracer(material);
        cmbMaterial.setItems(materialList);
        chkCastShadows.setSelected(n.castShadows);
        chkReceiveShadows.setSelected(n.reciveShadows);
        chkVisible.setSelected(n.visibility);
        chkFlipNormals.setSelected(n.flipNormal);
        if(!(n.geos.size()==1 && !(n.geos.get(0)instanceof Node))){
            System.out.println(chkFlipNormals.getParent().getParent());
            nodeView.getChildren().removeAll(
                    chkFlipNormals.getParent(),
                    chkCastShadows.getParent(),
                    chkReceiveShadows.getParent()

            );

        }
        if (txtPath != null) {
            txtPath.setText(((ShapeFromFile) n.geos.get(0)).file.getPath());
        }
        if (btnNewPath != null) {
            btnNewPath.setOnAction(a -> newPathLoad());
        }
        setMaterialComboBox();*/
    }

    private void newPathLoad() {
       /* FileChooser dlg = new FileChooser();
        dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront obj File. (*.obj)", "*.obj"));
        File file = dlg.showOpenDialog(materialView.getScene().getWindow());
        if (file != null) {
            ShapeFromFile s = (ShapeFromFile) ((Node) selectedTreeItem.get().getValue()).geos.get(0);
            ShapeFromFile e = new ShapeFromFile(file,
                    s.material,
                    s.reciveShadows,
                    s.castShadows,
                    s.visibility,
                    s.flipNormal);
            List<Geometry> geos = new ArrayList<>();
            geos.add(e);
            if (e != null) updateNode(geos);
            ;
        }*/
    }

    private void setMaterialComboBox() {
        if (((ONode) selectedTreeItem.get().getValue()).oGeos.get(0) instanceof ONode) {
            ((HBox) cmbMaterial.getParent()).getChildren().remove(cmbMaterial);
            ((HBox) materialView.getParent()).getChildren().remove(materialView);
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
                            setText(prefix + " " + item.name);
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
                    setText("");
                } else {
                    String prefix = "";
                    if (this.getIndex() < 6) prefix = "New";
                    setText(prefix + " " + item.name);
                }
            }
        });
       // cmbMaterial.getSelectionModel().select(material.get());


    }

    @FXML
    private void handleUpdateNode(Event a) {
        updateNode(null);
    }

    private void updateNode(List<Geometry> geos) {

   /*     if (selectedTreeItem.get().getValue() != null){
            if (geos == null) geos = ((Node) selectedTreeItem.get().getValue()).geos;

            if(geos.size()==1 && !(geos.get(0) instanceof Node)) {
                Geometry g = geos.get(0);
                if (g instanceof Triangle || g instanceof Plane || g instanceof Sphere || g instanceof AxisAlignedBox || g instanceof ShapeFromFile) {
                    g.flipNormal = chkFlipNormals.isSelected();
                    g.visibility = chkVisible.isSelected();
                    g.castShadows = chkCastShadows.isSelected();
                    g.reciveShadows = chkReceiveShadows.isSelected();
                }
            }
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
                node.name = selectedTreeItem.get().getValue().name;
                elementLists.updateElement(selectedTreeItem.get().getValue(), node);
                //   NodeTreeViewController.updateElement(node);
            }
        }
    }
    public void handleComboBoxMaterialAction(ActionEvent actionEvent) {
        Material m = cmbMaterial.getSelectionModel().getSelectedItem();
        if (cmbMaterial.getSelectionModel().getSelectedIndex() < 6) {
            m = cmbMaterial.getSelectionModel().getSelectedItem().deepCopy();
            materialList.add(m);
        }
        material.setValue(m);
        Geometry g = ((Node) selectedTreeItem.get().getValue()).geos.get(0);
        List<Geometry> geos = new ArrayList<>();
        geos.add(g.deepCopy(m));
        updateNode(geos);
        TreeItem<Element> t = selectedTreeItem.get();
        selectedTreeItem.setValue(null);
        selectedTreeItem.setValue(t);*/
    }
}
