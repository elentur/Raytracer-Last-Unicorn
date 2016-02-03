package controller;

import UI.MaterialView;
import UI.NumberTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import observables.geometries.ONode;
import observables.materials.*;
import observables.textures.AOTexture;
import observables.textures.OImageTexture;
import observables.textures.OInterpolatedImageTexture;
import observables.textures.OSingleColorTexture;

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
    private ComboBox<AOTexture> cmbDiffuse;
    @FXML
    private NumberTextField txtNormalScale;
    @FXML
    private ComboBox<AOTexture> cmbNormal;
    @FXML
    private ColorPicker clpIrradiance;
    @FXML
    private ComboBox<AOTexture> cmbIrradiance;
    @FXML
    private Slider sldRoughness;
    @FXML
    private Label lblRoughness;
    @FXML
    private ColorPicker clpSpecular;
    @FXML
    private ComboBox <AOTexture>cmbSpecular;
    @FXML
    private NumberTextField txtExponent;
    @FXML
    private ColorPicker clpReflection;
    @FXML
    private ComboBox<AOTexture> cmbReflection;
    @FXML
    private Label lblIOR;
    @FXML
    private Slider sldIOR;

    private VBox diffuseTextureView;
    private VBox normalTextureView;
    private VBox irradianceTextureView;
    private VBox specularTextureView;
    private VBox reflectionTextureView;



    private final Callback<ListView<AOTexture>, ListCell<AOTexture>> cell=  new Callback<ListView<AOTexture>, ListCell<AOTexture>>() {
        @Override
        public ListCell<AOTexture> call(ListView<AOTexture> c) {

            return new ListCell<AOTexture>() {
                @Override
                protected void updateItem(AOTexture item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.name.getValue());
                    }
                }
            };
        }};


    private boolean initialized = false;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        material.setValue(((ONode)selectedTreeItem.get().getValue()).oGeos.get(0).material.getValue());
        materialRenderView.setUpTracer(material);
        if(!initialized) {
            VBox v;
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            try {
                if (material.get() instanceof OOrenNayarMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainOrenNayarMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                } else if (material.get() instanceof OPhongMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                }else if (material.get() instanceof OReflectiveMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainReflectiveMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                } else  if (material.get() instanceof OTransparentMaterial){
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
        AOMaterial m = material.get();
        txtMaterialName.textProperty().bindBidirectional(m.name);
        clpDiffuse.valueProperty().bindBidirectional(m.texture.get().color);
        cmbDiffuse.setItems(textureList);
        if(cmbDiffuse.getItems().contains(m.texture.get()))cmbDiffuse.getSelectionModel().select(m.texture.get());
        cmbDiffuse.setCellFactory(cell);
        cmbDiffuse.setButtonCell(new ButtonCell());
        txtNormalScale.doubleProperty.bindBidirectional(m.bumpScale);
        cmbNormal.setItems(textureList);
        if(cmbNormal.getItems().contains(m.bumpMap.get()))cmbNormal.getSelectionModel().select(m.bumpMap.get());
        cmbNormal.setCellFactory(cell);
        cmbNormal.setButtonCell(new ButtonCell());
        cmbIrradiance.setItems(textureList);
        if(cmbIrradiance.getItems().contains(m.irradiance.get())) cmbIrradiance.getSelectionModel().select(m.irradiance.get());
        cmbIrradiance.setCellFactory(cell);
        cmbIrradiance.setButtonCell(new ButtonCell());

        if (sldRoughness != null) {
            lblRoughness.textProperty().bind(Bindings.concat("Roughness: ").concat(Bindings.format("%.1f", sldRoughness.valueProperty())));
            sldRoughness.setMin(0);
            sldRoughness.setMax(1);
            sldRoughness.valueProperty().bindBidirectional(((OOrenNayarMaterial)m).roughness);
        }
        if(clpSpecular !=null){
            ObjectProperty<Color> c;
            IntegerProperty exp;
            cmbSpecular.setItems(textureList);
            cmbSpecular.setCellFactory(cell);
            cmbSpecular.setButtonCell(new ButtonCell());
            if(m instanceof OPhongMaterial){
                c = ((OPhongMaterial)m).specular.getValue().color;
                exp = ((OPhongMaterial)m).exponent;
                if(cmbSpecular.getItems().contains(((OPhongMaterial) m).specular.get())) cmbSpecular.getSelectionModel().select(((OPhongMaterial) m).specular.get());
            }else if(m instanceof OReflectiveMaterial){
                c = ((OReflectiveMaterial)m).specular.getValue().color;
                exp = ((OReflectiveMaterial)m).exponent;
                if(cmbSpecular.getItems().contains(((OReflectiveMaterial) m).specular.get()))  cmbSpecular.getSelectionModel().select(((OReflectiveMaterial) m).specular.get());
            }else{
                c = ((OTransparentMaterial)m).specular.getValue().color;
                exp = ((OTransparentMaterial)m).exponent;
                if(cmbSpecular.getItems().contains(((OTransparentMaterial) m).specular.get()))  cmbSpecular.getSelectionModel().select(((OTransparentMaterial) m).specular.get());
            }

            clpSpecular.valueProperty().bindBidirectional(c);

            txtExponent.doubleProperty.bindBidirectional(exp);
        }
        if(clpReflection !=null){
            ObjectProperty<Color> c;
            cmbReflection.setItems(textureList);
            cmbReflection.setCellFactory(cell);
            cmbReflection.setButtonCell(new ButtonCell());
            if(m instanceof OReflectiveMaterial){
                c = ((OReflectiveMaterial)m).reflection.get().color;
                if(cmbReflection.getItems().contains(((OReflectiveMaterial) m).reflection.get()))  cmbReflection.getSelectionModel().select(((OReflectiveMaterial) m).reflection.get());
            }else{
                c = ((OTransparentMaterial)m).reflection.get().color;
                if(cmbReflection.getItems().contains(((OTransparentMaterial) m).reflection.get()))cmbReflection.getSelectionModel().select(((OTransparentMaterial) m).reflection.get());
            }

            clpReflection.valueProperty().bindBidirectional(c);
        }
        if (sldIOR!= null) {
            lblIOR.textProperty().bind(Bindings.concat("Index of Refraction: ").concat(Bindings.format("%.3f", sldIOR.valueProperty())));
            sldIOR.setMin(0);
            sldIOR.setMax(2);
            sldIOR.valueProperty().bindBidirectional(((OTransparentMaterial)m).indexOfRefraction);
        }
        materialView.lookup("#btnNewDiffuse").setOnMouseClicked(a->newTexture(a,cmbDiffuse));
        materialView.lookup("#btnNewNormal").setOnMouseClicked(a->newTexture(a,cmbNormal));
        materialView.lookup("#btnNewIrradiance").setOnMouseClicked(a->newTexture(a,cmbIrradiance));
        if(materialView.lookup("#btnNewSpecular")!=null)materialView.lookup("#btnNewSpecular").setOnMouseClicked(a->newTexture(a,cmbSpecular));
        if(materialView.lookup("#btnNewReflection")!=null)materialView.lookup("#btnNewReflection").setOnMouseClicked(a->newTexture(a,cmbReflection));

        materialView.lookup("#btnClearDiffuse").setOnMouseClicked(a->{
            m.texture.setValue(new OSingleColorTexture(Color.GRAY));
            cmbDiffuse.getSelectionModel().clearSelection();
        });
        materialView.lookup("#btnClearNormal").setOnMouseClicked(a->{
            m.bumpMap.setValue(new OSingleColorTexture(Color.BLACK));
            m.bumpScale.setValue(0);
            cmbNormal.getSelectionModel().clearSelection();
        });
        materialView.lookup("#btnClearIrradiance").setOnMouseClicked(a->{
            m.irradiance.setValue(new OSingleColorTexture(Color.WHITE));
            cmbIrradiance.getSelectionModel().clearSelection();
        });
        if(materialView.lookup("#btnClearSpecular")!=null){
            if(m instanceof OPhongMaterial) materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->((OPhongMaterial)m).specular.setValue(new OSingleColorTexture(Color.WHITE)));
            if(m instanceof OReflectiveMaterial) materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->((OReflectiveMaterial)m).specular.setValue(new OSingleColorTexture(Color.WHITE)));
            if(m instanceof OTransparentMaterial)materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->((OTransparentMaterial)m).specular.setValue(new OSingleColorTexture(Color.WHITE)));
            cmbSpecular.getSelectionModel().clearSelection();
        }
        if(materialView.lookup("#btnClearReflection")!=null){
            if(m instanceof OReflectiveMaterial) materialView.lookup("#btnClearReflection").setOnMouseClicked(a->((OReflectiveMaterial)m).reflection.setValue(new OSingleColorTexture(Color.GRAY)));
            if(m instanceof OTransparentMaterial)materialView.lookup("#btnClearReflection").setOnMouseClicked(a->((OTransparentMaterial)m).reflection.setValue(new OSingleColorTexture(Color.GRAY)));
            cmbReflection.getSelectionModel().clearSelection();
        }


        cmbDiffuse.setOnAction(a-> setTexture(m.texture,cmbDiffuse));
        cmbNormal.setOnAction(a-> setTexture(m.bumpMap,cmbNormal));
        cmbIrradiance.setOnAction(a-> setTexture(m.irradiance,cmbIrradiance));
        if(cmbSpecular!=null){
            if(m instanceof OPhongMaterial)cmbSpecular.setOnAction(a-> setTexture(((OPhongMaterial)m).specular,cmbSpecular));
            if(m instanceof OReflectiveMaterial)cmbSpecular.setOnAction(a-> setTexture(((OReflectiveMaterial)m).specular,cmbSpecular));
            if(m instanceof OTransparentMaterial)cmbSpecular.setOnAction(a-> setTexture(((OTransparentMaterial)m).specular,cmbSpecular));
        }
        if(cmbReflection!=null){
            if(m instanceof OReflectiveMaterial)cmbReflection.setOnAction(a-> setTexture(((OReflectiveMaterial)m).reflection,cmbReflection));
            if(m instanceof OTransparentMaterial)cmbReflection.setOnAction(a-> setTexture(((OTransparentMaterial)m).reflection,cmbReflection));
        }

    }

    private void setTexture(final ObjectProperty<AOTexture> texture, final ComboBox<AOTexture> cmbTexture) {
       if(!cmbTexture.getSelectionModel().isEmpty()) texture.setValue(cmbTexture.getSelectionModel().getSelectedItem());
    }

    private void newTexture(final MouseEvent a, final ComboBox<AOTexture> comboBox) {
            FileChooser dlg = new FileChooser();
            dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpeg  (*.jpg)", "*.jpg"));
            dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG. (*.png)", "*.png"));
            File file = dlg.showOpenDialog(materialView.getScene().getWindow());
            if (file != null) {
                AOTexture newTex = new OImageTexture(
                        file.getPath()
                );
                comboBox.getItems().add(newTex);
                comboBox.setValue(newTex);
                loadTextureTabs();
            }


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
                initializeTexture(diffuseTextureView, material.get().texture.get());
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
                initializeTexture(normalTextureView, material.get().bumpMap.get());
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
                initializeTexture(irradianceTextureView, material.get().irradiance.get());
                if(!tabPane.getTabs().contains(tab))tabPane.getTabs().add(tab);
            }
            if(cmbSpecular != null && cmbSpecular.getValue()!=null){
                AOTexture tex= null;
                if(material.get() instanceof OPhongMaterial)tex= ((OPhongMaterial) material.get()).specular.get();
                else if(material.get() instanceof OReflectiveMaterial)tex=((OReflectiveMaterial) material.get()).specular.get();
                else tex=((OTransparentMaterial) material.get()).specular.get();

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
                AOTexture tex= null;
                if(material.get() instanceof OReflectiveMaterial)tex=((OReflectiveMaterial) material.get()).reflection.get();
                else tex=((OTransparentMaterial) material.get()).reflection.get();
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

    private void initializeTexture(final VBox v, final AOTexture texture) {
        ((TextField) v.lookup("#txtTextureName")).textProperty().bindBidirectional(texture.name);
        ((TextField) v.lookup("#txtPath")).textProperty().bindBidirectional(texture.path);
        //((Button) v.lookup("#btnNewPath")).setOnAction(a->handleUpdateTexture(v,texture));
        ((CheckBox) v.lookup("#chkBilinearFilter")).selectedProperty().bindBidirectional(new SimpleBooleanProperty(texture instanceof OInterpolatedImageTexture));
        ((ImageView) v.lookup("#imgTexture")).setImage(new Image(new File(texture.path.get()).toURI().toString()));
        ((NumberTextField) v.lookup("#txtOffsetU")).doubleProperty.bindBidirectional(texture.offsetU);
        ((NumberTextField) v.lookup("#txtOffsetV")).doubleProperty.bindBidirectional(texture.offsetV);
        ((NumberTextField) v.lookup("#txtScalingU")).doubleProperty.bindBidirectional(texture.scaleU);
        ((NumberTextField) v.lookup("#txtScalingV")).doubleProperty.bindBidirectional(texture.scaleV);
        ((NumberTextField) v.lookup("#txtRotation")).doubleProperty.bindBidirectional(texture.rotate);



    }


    private class ButtonCell extends ListCell<AOTexture> {
        @Override
        protected void updateItem(AOTexture item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {textProperty().unbind();
                setText("");
            } else {
                textProperty().bind(item.name);
            }
        }
    }

}
