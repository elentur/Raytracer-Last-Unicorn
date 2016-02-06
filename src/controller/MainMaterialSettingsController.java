package controller;

import UI.MaterialView;
import UI.NumberTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainMaterialSettingsController extends AController {
    @FXML
    private HBox materialRenderViewHBox;
    @FXML
    private TextField txtMaterialName;
    @FXML
    private VBox materialView;
    @FXML
    private CheckBox chkAmbientOcclusion;
    @FXML
    private NumberTextField txtAmbientSize;
    @FXML
    private NumberTextField txtAmbientSubdive;
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


    private final Callback<ListView<AOTexture>, ListCell<AOTexture>> cell=  new Callback<ListView<AOTexture>, ListCell<AOTexture>>() {
        @Override
        public ListCell<AOTexture> call(ListView<AOTexture> c) {

            return new ListCell<AOTexture>() {
                @Override
                protected void updateItem(AOTexture item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                       textProperty().bind(item.name);
                    }
                }
            };
        }};


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
        material.setValue(((ONode)selectedTreeItem.get().getValue()).oGeos.get(0).material.getValue());

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
            MaterialView materialRenderView = MaterialView.getInstance1();
            materialRenderView.setUpTracer(material);
            materialRenderViewHBox.getChildren().add(materialRenderView);

        }

    }
    private void initializeFields() {
        AOMaterial m = material.get();
        txtMaterialName.textProperty().bindBidirectional(m.name);
        chkAmbientOcclusion.selectedProperty().bindBidirectional(m.ambientOcclusion);
        txtAmbientSize.doubleProperty.bindBidirectional(m.ambientSize);
        txtAmbientSubdive.doubleProperty.bindBidirectional(m.ambientSubdiv);
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
            }else {
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
        materialView.lookup("#btnNewDiffuse").setOnMouseClicked(a->newTexture(cmbDiffuse));
        materialView.lookup("#btnNewNormal").setOnMouseClicked(a->newTexture(cmbNormal));
        materialView.lookup("#btnNewIrradiance").setOnMouseClicked(a->newTexture(cmbIrradiance));
        if(materialView.lookup("#btnNewSpecular")!=null)materialView.lookup("#btnNewSpecular").setOnMouseClicked(a->newTexture(cmbSpecular));
        if(materialView.lookup("#btnNewReflection")!=null)materialView.lookup("#btnNewReflection").setOnMouseClicked(a->newTexture(cmbReflection));


        materialView.lookup("#btnClearDiffuse").setOnMouseClicked(a->clearTexture(m.texture,cmbDiffuse,clpDiffuse));
        materialView.lookup("#btnClearNormal").setOnMouseClicked(a->clearTexture(m.bumpMap,cmbNormal,null));
        materialView.lookup("#btnClearIrradiance").setOnMouseClicked(a->clearTexture(m.irradiance,cmbIrradiance,clpIrradiance));
        if(materialView.lookup("#btnClearSpecular")!=null){
            if(m instanceof OPhongMaterial) materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->clearTexture(((OPhongMaterial)m).specular,cmbSpecular,clpSpecular));
            if(m instanceof OReflectiveMaterial) materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->clearTexture(((OReflectiveMaterial)m).specular,cmbSpecular,clpSpecular));
            if(m instanceof OTransparentMaterial)materialView.lookup("#btnClearSpecular").setOnMouseClicked(a->clearTexture(((OTransparentMaterial)m).specular,cmbSpecular,clpSpecular));
            cmbSpecular.getSelectionModel().clearSelection();
        }
        if(materialView.lookup("#btnClearReflection")!=null){
            if(m instanceof OReflectiveMaterial) materialView.lookup("#btnClearReflection").setOnMouseClicked(a->clearTexture(((OReflectiveMaterial)m).reflection,cmbReflection,clpReflection));
            if(m instanceof OTransparentMaterial)materialView.lookup("#btnClearReflection").setOnMouseClicked(a->clearTexture(((OTransparentMaterial)m).reflection,cmbReflection,clpReflection));
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

    private void clearTexture(final ObjectProperty<AOTexture> texture, final ComboBox<AOTexture> cmbTexture, final ColorPicker clpColorPicker) {
        cmbTexture.getSelectionModel().clearSelection();
        if(clpColorPicker != null) {
            texture.setValue( new OSingleColorTexture(clpColorPicker.getValue()));
            clpColorPicker.valueProperty().bindBidirectional(texture.get().color);
        }else{
            material.get().bumpScale.setValue(0.0);
        }
        masterTabPane.getSelectionModel().select(1);
    }

    private void setTexture(final ObjectProperty<AOTexture> texture, final ComboBox<AOTexture> cmbTexture) {
       if(!cmbTexture.getSelectionModel().isEmpty()) texture.setValue(cmbTexture.getSelectionModel().getSelectedItem());
        loadTextureTabs();
    }

    private void newTexture( final ComboBox<AOTexture> comboBox) {
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
            }


    }



    private void loadTextureTabs() {
       TabPane tabPane = masterTabPane;

        FXMLLoader loader  = new FXMLLoader();
        loader.setController(this);
        Set<AOTexture> textures = new HashSet<>();
        if(!cmbDiffuse.getSelectionModel().isEmpty())
            textures.add(cmbDiffuse.getSelectionModel().getSelectedItem());
        if(!cmbNormal.getSelectionModel().isEmpty())
            textures.add(cmbNormal.getSelectionModel().getSelectedItem());
        if(!cmbIrradiance.getSelectionModel().isEmpty())
            textures.add(cmbIrradiance.getSelectionModel().getSelectedItem());
        if(cmbSpecular!= null && !cmbSpecular.getSelectionModel().isEmpty())
            textures.add(cmbSpecular.getSelectionModel().getSelectedItem());
        if(cmbReflection!= null && !cmbReflection.getSelectionModel().isEmpty())
            textures.add(cmbReflection.getSelectionModel().getSelectedItem());

        for (Iterator<Tab> tab = tabPane.getTabs().iterator(); tab.hasNext(); ) {
            Tab t =  tab.next();
            if(t.getText().equals("Texture"))tab.remove();
        }
        try {
            for(AOTexture tex : textures){
                Tab tab = new Tab();
                tab.setClosable(false);
                tab.setText("Texture");

                VBox v = loader.load(this.getClass().getResource("/fxml/mainSettingsTextureView.fxml"));
                tab.setContent(v);
                initializeTexture(v, tex,tab);
                masterTabPane.getTabs().add(tab);
            }
        } catch (IOException e) {
            //TODO schöne Ausgabe
            System.out.println("Fehler beim laden");
        }

    }

    private void initializeTexture(final VBox v, final AOTexture texture, final Tab tab) {
        ((TextField) v.lookup("#txtTextureName")).textProperty().bindBidirectional(texture.name);
        ((TextField) v.lookup("#txtPath")).textProperty().bindBidirectional(texture.path);
        ((Button) v.lookup("#btnNewPath")).setOnAction(a->{
            FileChooser dlg = new FileChooser();
            dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jpeg  (*.jpg)", "*.jpg"));
            dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG. (*.png)", "*.png"));
            File file = dlg.showOpenDialog(materialView.getScene().getWindow());
            if (file != null) {
               texture.path.setValue(file.getPath());
                initializeTexture(v,texture,tab);
            }
        });
        ((CheckBox) v.lookup("#chkBilinearFilter")).setSelected(texture instanceof OInterpolatedImageTexture);
        ((CheckBox) v.lookup("#chkBilinearFilter")).setOnAction(a->{
            AOTexture newTex = null;
           if(texture instanceof OImageTexture) {
               newTex = new OInterpolatedImageTexture(texture.path.get());
           }else{
               newTex = new OImageTexture(texture.path.get());
           }
            newTex.offsetU=texture.offsetU;
            newTex.offsetV=texture.offsetV;
            newTex.scaleU=texture.scaleU;
            newTex.scaleV=texture.scaleV;
            newTex.rotate=texture.rotate;
            newTex.name=texture.name;
            textureList.set(textureList.indexOf(texture),newTex);
            initializeTexture(v,newTex,tab);
            if(cmbDiffuse.getValue()!=null && cmbDiffuse.getValue().equals(texture))cmbDiffuse.setValue(newTex);
            if(cmbIrradiance.getValue()!=null && cmbIrradiance.getValue().equals(texture))cmbIrradiance.setValue(newTex);
            if(cmbNormal.getValue()!=null && cmbNormal.getValue().equals(texture))cmbNormal.setValue(newTex);

            if(cmbSpecular != null && cmbSpecular.getValue()!=null && cmbSpecular.getValue().equals(texture))cmbSpecular.setValue(newTex);
            if(cmbReflection != null && cmbReflection.getValue()!=null &&  cmbReflection.getValue().equals(texture))cmbReflection.setValue(newTex);
        });
        if(texture.img.get() != null) ((ImageView) v.lookup("#imgTexture")).setImage(texture.img.get());
        ((NumberTextField) v.lookup("#txtOffsetU")).doubleProperty.bindBidirectional(texture.offsetU);
        ((NumberTextField) v.lookup("#txtOffsetV")).doubleProperty.bindBidirectional(texture.offsetV);
        ((NumberTextField) v.lookup("#txtScalingU")).doubleProperty.bindBidirectional(texture.scaleU);
        ((NumberTextField) v.lookup("#txtScalingV")).doubleProperty.bindBidirectional(texture.scaleV);
        ((NumberTextField) v.lookup("#txtRotation")).doubleProperty.bindBidirectional(texture.rotate);
        masterTabPane.getSelectionModel().select(tab);


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
