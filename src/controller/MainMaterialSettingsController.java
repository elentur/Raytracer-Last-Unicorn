package controller;

import UI.MaterialView;
import UI.NumberTextField;
import geometries.Geometry;
import geometries.Node;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import material.*;
import texture.SingleColorTexture;
import texture.Texture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainMaterialSettingsController extends AController {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @FXML
    private MaterialView materialRenderView;
    @FXML
    private VBox materialView;
    @FXML
    private ColorPicker clpDiffuse;
    @FXML
    private ComboBox<Texture> cmbDiffuse;
    @FXML
    private NumberTextField txtNormalScale;
    @FXML
    private ComboBox<Texture> cmbNormal;
    @FXML
    private ComboBox<Texture> cmbIrradiance;
    @FXML
    private Slider sldRoughness;
    @FXML
    private Label lblRoughness;
    @FXML
    private ColorPicker clpSpecular;
    @FXML
    private ComboBox <Texture>cmbSpecular;
    @FXML
    private NumberTextField txtExponent;
    @FXML
    private ColorPicker clpReflection;
    @FXML
    private ComboBox<Texture> cmbReflection;
    @FXML
    private Label lblIOR;
    @FXML
    private Slider sldIOR;

    private final Callback<ListView<Texture>, ListCell<Texture>> cell=  new Callback<ListView<Texture>, ListCell<Texture>>() {
        @Override
        public ListCell<Texture> call(ListView<Texture> c) {

            return new ListCell<Texture>() {
                @Override
                protected void updateItem(Texture item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        String prefix="";
                        if(this.getIndex()<1)prefix="New";
                        setText(prefix+" " +item.name);
                    }
                }
            };
        }};
    private final ListCell<Texture> buttonCell = new ListCell<Texture>() {
        @Override
        protected void updateItem(Texture item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText("");
            } else {
                String prefix="";
                if(this.getIndex()<1)prefix="New";
                setText(prefix+" " +item.name);
            }
        }
    };

    private boolean initialized = false;

    public MainMaterialSettingsController() {
        if (textureList.isEmpty()) textureList.addAll(
                DefaultMaterial.IMAGE_TEXTURE
        );
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        material.setValue(((Node)selectedTreeItem.get().getValue()).geos.get(0).material);
        materialRenderView.setUpTracer(material);
        if(!initialized) {
            VBox v;
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            try {
                if (material.get() instanceof OrenNayarMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainOrenNayarMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                } else if (material.get() instanceof PhongMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                }else if (material.get() instanceof ReflectiveMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainReflectiveMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                } else  if (material.get() instanceof TransparentMaterial){
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainReflectiveMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainTransparentMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            sldRoughness = (Slider) materialView.lookup("#sldRoughness");
            lblRoughness = (Label) materialView.lookup("#lblRoughness");
            clpSpecular = (ColorPicker) materialView.lookup("#clpSpecular");
            cmbSpecular = (ComboBox) materialView.lookup("#cmbSpecular");
            txtExponent = (NumberTextField) materialView.lookup("#txtExponent");
            clpReflection = (ColorPicker) materialView.lookup("#clpReflection");
            cmbReflection = (ComboBox) materialView.lookup("#cmbReflection");
            sldIOR = (Slider) materialView.lookup("#sldIOR");
            lblIOR = (Label) materialView.lookup("#lblIOR");

            initializeFields();
            initialized=true;
        }

    }
    private void initializeFields() {
        Material m = material.get();
        clpDiffuse.setValue(new Color(m.texture.getColor(0,0).r,m.texture.getColor(0,0).g,m.texture.getColor(0,0).b,1));
        clpDiffuse.setOnAction(a -> handleUpdateMaterial());
        cmbDiffuse.setItems(textureList);
        if(cmbDiffuse.getItems().contains(m.texture))cmbDiffuse.getSelectionModel().select(m.texture);
        cmbDiffuse.setCellFactory(cell);
        cmbDiffuse.setButtonCell(buttonCell);
        txtNormalScale.setNumber(m.bumpScale);
        txtNormalScale.setOnAction(a -> handleUpdateMaterial());
        cmbNormal.setItems(textureList);
        if(cmbNormal.getItems().contains(m.bumpMap))cmbNormal.getSelectionModel().select(m.bumpMap);
        cmbNormal.setCellFactory(cell);
        cmbNormal.setButtonCell(buttonCell);
        cmbIrradiance.setItems(textureList);
        if(cmbIrradiance.getItems().contains(m.irradiance)) cmbIrradiance.getSelectionModel().select(m.irradiance);
        cmbIrradiance.setCellFactory(cell);
        cmbIrradiance.setButtonCell(buttonCell);

        if (sldRoughness != null) {
            lblRoughness.textProperty().bind(Bindings.concat("Roughness: ").concat(Bindings.format("%.1f", sldRoughness.valueProperty())));
            sldRoughness.setMin(0);
            sldRoughness.setMax(1);
            sldRoughness.setValue(Math.sqrt(((OrenNayarMaterial)m).rough_sq));
            sldRoughness.setOnMouseReleased(a -> handleUpdateMaterial());
        }
        if(clpSpecular !=null){
            utils.Color c;
            int exp=0;
            cmbSpecular.setItems(textureList);
            cmbSpecular.setCellFactory(cell);
            cmbSpecular.setButtonCell(buttonCell);
            if(m instanceof PhongMaterial){
                c = ((PhongMaterial)m).specular.getColor(0,0);
                exp = ((PhongMaterial)m).exponent;
                if(cmbSpecular.getItems().contains(((PhongMaterial) m).specular)) cmbSpecular.getSelectionModel().select(((PhongMaterial) m).specular);
            }else if(m instanceof ReflectiveMaterial){
                c = ((ReflectiveMaterial)m).specular.getColor(0,0);
                exp = ((ReflectiveMaterial)m).exponent;
                if(cmbSpecular.getItems().contains(((ReflectiveMaterial) m).specular))  cmbSpecular.getSelectionModel().select(((ReflectiveMaterial) m).specular);
            }else{
                c = ((TransparentMaterial)m).specular.getColor(0,0);
                exp = ((TransparentMaterial)m).exponent;
                if(cmbSpecular.getItems().contains(((TransparentMaterial) m).specular))  cmbSpecular.getSelectionModel().select(((TransparentMaterial) m).specular);
            }

            clpSpecular.setValue(new Color(c.r,c.g,c.b,1));
            clpSpecular.setOnAction(a -> handleUpdateMaterial());

            txtExponent.setNumber(exp);
            txtExponent.setOnAction(a -> handleUpdateMaterial());
        }
        if(clpReflection !=null){
            utils.Color c;
            cmbReflection.setItems(textureList);
            cmbReflection.setCellFactory(cell);
            cmbReflection.setButtonCell(buttonCell);
            if(m instanceof ReflectiveMaterial){
                c = ((ReflectiveMaterial)m).reflection.getColor(0,0);
                if(cmbReflection.getItems().contains(((ReflectiveMaterial) m).reflection))  cmbReflection.getSelectionModel().select(((ReflectiveMaterial) m).reflection);
            }else{
                c = ((TransparentMaterial)m).reflection.getColor(0,0);
                if(cmbReflection.getItems().contains(((TransparentMaterial) m).reflection))cmbReflection.getSelectionModel().select(((TransparentMaterial) m).reflection);
            }

            clpReflection.setValue(new Color(c.r,c.g,c.b,1));
            clpReflection.setOnAction(a -> handleUpdateMaterial());
        }
        if (sldIOR!= null) {
            lblIOR.textProperty().bind(Bindings.concat("Index of Refraction: ").concat(Bindings.format("%.3f", sldIOR.valueProperty())));
            sldIOR.setMin(0);
            sldIOR.setMax(2);
            sldIOR.setValue(Math.sqrt(((TransparentMaterial)m).iOR));
            sldIOR.setOnMouseReleased(a -> handleUpdateMaterial());
        }
    }

    private void handleUpdateMaterial() {
        Material m = null;
        Texture diffuse = cmbDiffuse.getSelectionModel().getSelectedItem() != null ?
                cmbDiffuse.getSelectionModel().getSelectedItem() :
                new SingleColorTexture(new utils.Color(clpDiffuse.getValue().getRed(), clpDiffuse.getValue().getGreen(), clpDiffuse.getValue().getBlue()));
        Texture normal = cmbNormal.getSelectionModel().getSelectedItem()!= null ?
                cmbNormal.getSelectionModel().getSelectedItem():
                material.get().bumpMap;
        double normalScaling = txtNormalScale.getDouble();
        Texture irradiance = cmbIrradiance.getSelectionModel().getSelectedItem()!= null ?
                cmbIrradiance.getSelectionModel().getSelectedItem():
                material.get().irradiance;
        if (material.get() instanceof SingleColorMaterial) {
            m = new SingleColorMaterial(
                    diffuse,
                    normal,
                    normalScaling
            );
        } else if (material.get() instanceof LambertMaterial) {
            m = new LambertMaterial(
                    diffuse,
                    normal,
                    normalScaling,
                    irradiance
            );
        } else if (material.get() instanceof OrenNayarMaterial) {
            m = new OrenNayarMaterial(
                    diffuse,
                    sldRoughness.getValue(),
                    normal,
                    normalScaling,
                    irradiance
            );
        } else {
            Texture specular = cmbSpecular.getSelectionModel().getSelectedItem()!=null?
                    cmbSpecular.getSelectionModel().getSelectedItem():
                    new SingleColorTexture(new utils.Color(clpSpecular.getValue().getRed(),clpSpecular.getValue().getGreen(),clpSpecular.getValue().getBlue()));

            if (material.get() instanceof PhongMaterial) {

                m = new PhongMaterial(
                        diffuse,
                        specular,
                        txtExponent.getInteger(),
                        normal,
                        normalScaling,
                        irradiance
                );
            }else{
                Texture reflection = cmbReflection.getSelectionModel().getSelectedItem()!=null?
                        cmbReflection.getSelectionModel().getSelectedItem():
                        new SingleColorTexture(new utils.Color(clpReflection.getValue().getRed(),clpReflection.getValue().getGreen(),clpReflection.getValue().getBlue()));

                if (material.get() instanceof ReflectiveMaterial) {

                    m = new ReflectiveMaterial(
                            diffuse,
                            specular,
                            reflection,
                            txtExponent.getInteger(),
                            normal,
                            normalScaling,
                            irradiance
                    );
                }else if (material.get() instanceof TransparentMaterial) {

                    m = new TransparentMaterial(
                            diffuse,
                            specular,
                            reflection,
                            txtExponent.getInteger(),
                            sldIOR.getValue(),
                            normal,
                            normalScaling,
                            irradiance
                    );
                }
            }
        }

        if (m != null) {
            Node n =(Node) selectedTreeItem.get().getValue();
            m.name = n.geos.get(0).material.name;
            Geometry g = n.geos.get(0).deepCopy(m);
            Node node = new Node(
                    n.translation,
                    n.scaling,
                    n.rotation,
                    g,
                    n.reciveShadows,
                    n.castShadows,
                    n.visibility,
                    n.flipNormal);
            node.name = n.name;
            material.setValue(m);
            materialList.set(materialList.indexOf(n.geos.get(0).material),m);
            elementLists.updateElement(selectedTreeItem.get().getValue(), node);

        }
    }



}
