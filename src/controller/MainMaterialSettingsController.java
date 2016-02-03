package controller;

import UI.MaterialView;
import UI.NumberTextField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import material.*;
import texture.ImageTexture;
import texture.InterpolatedImageTexture;
import texture.Texture;

import java.io.File;
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
    private TextField txtMaterialName;
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
    private ColorPicker clpIrradiance;
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

    private VBox diffuseTextureView;
    private VBox normalTextureView;
    private VBox irradianceTextureView;
    private VBox specularTextureView;
    private VBox reflectionTextureView;



    private final Callback<ListView<Texture>, ListCell<Texture>> cell=  new Callback<ListView<Texture>, ListCell<Texture>>() {
        @Override
        public ListCell<Texture> call(ListView<Texture> c) {

            return new ListCell<Texture>() {
                @Override
                protected void updateItem(Texture item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.name);
                    }
                }
            };
        }};


    private boolean initialized = false;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
       // material.setValue(((Node)selectedTreeItem.get().getValue()).geos.get(0).material);
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
            loadTextureTabs();
            initialized=true;

        }

    }
    private void initializeFields() {
        Material m = material.get();
        txtMaterialName.setText(m.name);
        txtMaterialName.setOnAction(a -> handleUpdateMaterial());
        clpDiffuse.setValue(new Color(m.texture.getColor(0,0).r,m.texture.getColor(0,0).g,m.texture.getColor(0,0).b,1));
        clpDiffuse.setOnAction(a -> handleUpdateMaterial());
        cmbDiffuse.setItems(textureList);
        if(cmbDiffuse.getItems().contains(m.texture))cmbDiffuse.getSelectionModel().select(m.texture);
        cmbDiffuse.setCellFactory(cell);
        cmbDiffuse.setButtonCell(new ButtonCell());
        txtNormalScale.setNumber(m.bumpScale);
        txtNormalScale.setOnAction(a -> handleUpdateMaterial());
        cmbNormal.setItems(textureList);
        if(cmbNormal.getItems().contains(m.bumpMap))cmbNormal.getSelectionModel().select(m.bumpMap);
        cmbNormal.setCellFactory(cell);
        cmbNormal.setButtonCell(new ButtonCell());
        cmbIrradiance.setItems(textureList);
        if(cmbIrradiance.getItems().contains(m.irradiance)) cmbIrradiance.getSelectionModel().select(m.irradiance);
        cmbIrradiance.setCellFactory(cell);
        cmbIrradiance.setButtonCell(new ButtonCell());

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
            cmbSpecular.setButtonCell(new ButtonCell());
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
            cmbReflection.setButtonCell(new ButtonCell());
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
        materialView.lookup("#btnNewDiffuse").setOnMouseClicked(a->newTexture(a,cmbDiffuse));
        materialView.lookup("#btnNewNormal").setOnMouseClicked(a->newTexture(a,cmbNormal));
        materialView.lookup("#btnNewIrradiance").setOnMouseClicked(a->newTexture(a,cmbIrradiance));
        if(materialView.lookup("#btnNewSpecular")!=null)materialView.lookup("#btnNewSpecular").setOnMouseClicked(a->newTexture(a,cmbSpecular));
        if(materialView.lookup("#btnNewReflection")!=null)materialView.lookup("#btnNewReflection").setOnMouseClicked(a->newTexture(a,cmbReflection));
        materialView.lookup("#btnClearDiffuse").setOnMouseClicked(a->clearTexture(a,cmbDiffuse));
        materialView.lookup("#btnClearNormal").setOnMouseClicked(a->clearTexture(a,cmbNormal));
        materialView.lookup("#btnClearIrradiance").setOnMouseClicked(a->clearTexture(a,cmbIrradiance));
        if(materialView.lookup("#btnClearSpecular")!=null)materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->clearTexture(a,cmbSpecular));
        if(materialView.lookup("#btnClearReflection")!=null)materialView.lookup("#btnClearReflection").setOnMouseClicked(a->clearTexture(a,cmbReflection));

        cmbDiffuse.setOnAction(a->selectTexture(cmbDiffuse));
        cmbNormal.setOnAction(a-> selectTexture(cmbNormal));
        cmbIrradiance.setOnAction(a-> selectTexture(cmbIrradiance));
        if(cmbSpecular!=null)cmbSpecular.setOnAction(a-> selectTexture(cmbSpecular));
        if(cmbReflection!=null)cmbReflection.setOnAction(a-> selectTexture(cmbReflection));

    }

    private void newTexture(final MouseEvent a, final ComboBox<Texture> comboBox) {
            FileChooser dlg = new FileChooser();
            dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpeg  (*.jpg)", "*.jpg"));
            dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG. (*.png)", "*.png"));
            File file = dlg.showOpenDialog(materialView.getScene().getWindow());
            if (file != null) {
                Texture newTex = new ImageTexture(
                        file.getPath()
                );
                comboBox.getItems().add(newTex);
                comboBox.setValue(newTex);
            }

    }

    private void handleUpdateMaterial() {

    }


    private void loadTextureTabs() {
       TabPane tabPane = masterTabPane;
        for(int i =2 ; i <tabPane.getTabs().size();i++){
            tabPane.getTabs().remove(i);
        }
        FXMLLoader loader  = new FXMLLoader();
        loader.setController(this);
        try {
            if(cmbDiffuse.getValue()!=null){
                Tab tab = new Tab();
                tab.setText("Texture");
                tab.setId(material.get().texture.hashCode()+"");
                for(Tab t : tabPane.getTabs()){
                    if(t.getId() != null && t.getId().equals(material.get().texture.hashCode()+""))tab=t;
                }
                diffuseTextureView = loader.load(this.getClass().getResource("/fxml/mainSettingsTextureView.fxml"));
                tab.setContent(diffuseTextureView);
                initializeTexture(diffuseTextureView, material.get().texture);
                if(!tabPane.getTabs().contains(tab))tabPane.getTabs().add(tab);
            }
            if(cmbNormal.getValue()!=null){
                Tab tab = new Tab();
                tab.setText("Texture");
                tab.setId(material.get().bumpMap.hashCode()+"");
                for(Tab t : tabPane.getTabs()){
                    if(t.getId() != null && t.getId().equals(material.get().bumpMap.hashCode()+""))tab=t;
                }
                normalTextureView = loader.load(this.getClass().getResource("/fxml/mainSettingsTextureView.fxml"));
                tab.setContent(normalTextureView);
                initializeTexture(normalTextureView, material.get().bumpMap);
                if(!tabPane.getTabs().contains(tab))tabPane.getTabs().add(tab);
            }
            if(cmbIrradiance.getValue()!=null){
                Tab tab = new Tab();
                tab.setText("Texture");
                tab.setId(material.get().irradiance.hashCode()+"");
                for(Tab t : tabPane.getTabs()){
                    if(t.getId() != null && t.getId().equals(material.get().irradiance.hashCode()+""))tab=t;
                }
                irradianceTextureView = loader.load(this.getClass().getResource("/fxml/mainSettingsTextureView.fxml"));
                tab.setContent(irradianceTextureView);
                initializeTexture(irradianceTextureView, material.get().irradiance);
                if(!tabPane.getTabs().contains(tab))tabPane.getTabs().add(tab);
            }
            if(cmbSpecular != null && cmbSpecular.getValue()!=null){
                Texture tex= null;
                if(material.get() instanceof PhongMaterial)tex= ((PhongMaterial) material.get()).specular;
                else if(material.get() instanceof ReflectiveMaterial)tex=((ReflectiveMaterial) material.get()).specular;
                else tex=((TransparentMaterial) material.get()).specular;

                Tab tab = new Tab();
                tab.setText("Texture");
                tab.setId(tex.hashCode()+"");
                for(Tab t : tabPane.getTabs()){
                    if(t.getId() != null && t.getId().equals(material.get().texture.hashCode()+""))tab=t;
                }
                specularTextureView = loader.load(this.getClass().getResource("/fxml/mainSettingsTextureView.fxml"));
                tab.setContent(specularTextureView);
                initializeTexture(specularTextureView,tex);
                if(!tabPane.getTabs().contains(tab))tabPane.getTabs().add(tab);
            }
            if(cmbReflection != null && cmbReflection.getValue()!=null){
                Texture tex= null;
                if(material.get() instanceof ReflectiveMaterial)tex=((ReflectiveMaterial) material.get()).reflection;
                else tex=((TransparentMaterial) material.get()).reflection;
                Tab tab = new Tab();
                tab.setText("Texture");
                tab.setId(tex.hashCode()+"");
                for(Tab t : tabPane.getTabs()){
                    if(t.getId() != null && t.getId().equals(tex.hashCode()+""))tab=t;
                }
                reflectionTextureView = loader.load(this.getClass().getResource("/fxml/mainSettingsTextureView.fxml"));
                tab.setContent(reflectionTextureView);
                initializeTexture(reflectionTextureView,tex);

                if(!tabPane.getTabs().contains(tab))tabPane.getTabs().add(tab);
            }
        } catch (IOException e) {
        }
    }

    private void initializeTexture(final VBox v, final Texture texture) {
        ((TextField) v.lookup("#txtTextureName")).setText(texture.name);
        ((TextField) v.lookup("#txtTextureName")).setOnAction(a->handleUpdateTexture(v,texture));
        ((TextField) v.lookup("#txtPath")).setText(texture.path);
        ((TextField) v.lookup("#txtPath")).setOnAction(a->handleUpdateTexture(v,texture));
        ((Button) v.lookup("#btnNewPath")).setOnAction(a->handleUpdateTexture(v,texture));
        ((CheckBox) v.lookup("#chkBilinearFilter")).setSelected(texture instanceof InterpolatedImageTexture);
        ((CheckBox) v.lookup("#chkBilinearFilter")).setOnAction(a->handleUpdateTexture(v,texture));
        ((ImageView) v.lookup("#imgTexture")).setImage(new Image(new File(texture.path).toURI().toString()));
        ((NumberTextField) v.lookup("#txtOffsetU")).setNumber(texture.offsetU);
        ((NumberTextField) v.lookup("#txtOffsetU")).setOnAction(a->handleUpdateTexture(v,texture));
        ((NumberTextField) v.lookup("#txtOffsetV")).setNumber(texture.offsetV);
        ((NumberTextField) v.lookup("#txtOffsetV")).setOnAction(a->handleUpdateTexture(v,texture));
        ((NumberTextField) v.lookup("#txtScalingU")).setNumber(texture.scaleU);
        ((NumberTextField) v.lookup("#txtScalingU")).setOnAction(a->handleUpdateTexture(v,texture));
        ((NumberTextField) v.lookup("#txtScalingV")).setNumber(texture.scaleV);
        ((NumberTextField) v.lookup("#txtScalingV")).setOnAction(a->handleUpdateTexture(v,texture));
        ((NumberTextField) v.lookup("#txtRotation")).setNumber(texture.rotate);
        ((NumberTextField) v.lookup("#txtRotation")).setOnAction(a->handleUpdateTexture(v,texture));



    }

    private void handleUpdateTexture(final VBox v, final Texture texture) {
            Texture tex = ((CheckBox)v.lookup("#chkBilinearFilter")).isSelected()?
                    new InterpolatedImageTexture(
                            ((TextField) v.lookup("#txtPath")).getText(),
                            ((NumberTextField) v.lookup("#txtScalingU")).getDouble(),
                            ((NumberTextField) v.lookup("#txtScalingV")).getDouble(),
                            ((NumberTextField) v.lookup("#txtOffsetU")).getDouble(),
                            ((NumberTextField) v.lookup("#txtOffsetV")).getDouble(),
                            ((NumberTextField) v.lookup("#txtRotation")).getDouble()

                    ):
                    new ImageTexture(((TextField) v.lookup("#txtPath")).getText(),
                            ((NumberTextField) v.lookup("#txtScalingU")).getDouble(),
                            ((NumberTextField) v.lookup("#txtScalingV")).getDouble(),
                            ((NumberTextField) v.lookup("#txtOffsetU")).getDouble(),
                            ((NumberTextField) v.lookup("#txtOffsetV")).getDouble(),
                            ((NumberTextField) v.lookup("#txtRotation")).getDouble()

                    );
        tex.name = ((TextField) v.lookup("#txtTextureName")).getText();
        textureList.add(tex);
        if(cmbDiffuse.getValue()!=null && cmbDiffuse.getSelectionModel().getSelectedItem().equals(texture))cmbDiffuse.getSelectionModel().select(tex);
        if(cmbNormal.getValue()!=null && cmbNormal.getSelectionModel().getSelectedItem().equals(texture))cmbNormal.getSelectionModel().select(tex);
        if(cmbIrradiance.getValue()!=null && cmbIrradiance.getSelectionModel().getSelectedItem().equals(texture))cmbIrradiance.getSelectionModel().select(tex);
        if(cmbSpecular != null && cmbSpecular.getValue()!=null && cmbSpecular.getSelectionModel().getSelectedItem().equals(texture))cmbSpecular.getSelectionModel().select(tex);
        if(cmbReflection !=null && cmbReflection.getValue()!=null &&  cmbReflection.getSelectionModel().getSelectedItem().equals(texture))cmbReflection.getSelectionModel().select(tex);
        textureList.remove(texture);
        handleUpdateMaterial();
    }

    private void clearTexture(final MouseEvent a, final ComboBox<Texture> comboBox) {
            comboBox.getSelectionModel().clearSelection();
            handleUpdateMaterial();
    }
    private void selectTexture( final ComboBox<Texture> comboBox) {
        handleUpdateMaterial();
    }

    private class ButtonCell extends ListCell<Texture> {
        @Override
        protected void updateItem(Texture item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText("");
            } else {
                setText(item.name);
            }
        }
    }

}
