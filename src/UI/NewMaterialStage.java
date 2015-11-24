package UI;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import material.*;

/**
 * Created by Marcus Baetz on 23.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewMaterialStage extends Stage {

    private final ColorPicker cpColorPicker;
    private final ColorPicker cpSpec;
    private final ChoiceBox<String> chbMaterial;
    private final Slider sldExp;
    public NewMaterialStage(NewGeoStage st) {
        super();
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);

        final Label lblInfo = new Label("Select a Material");
        cpColorPicker = new ColorPicker(javafx.scene.paint.Color.GRAY);
        final Label lblColorPicker = new Label("Difuse:");
        cpSpec = new ColorPicker(javafx.scene.paint.Color.WHITE);
        final Label lblSpec = new Label("Specular:");

         chbMaterial = new ChoiceBox<>();
        final Label lblMaterial = new Label("Choose Material:");
        chbMaterial.getItems().addAll("SingleColor-Material", "Lambert-Material","Oren-Nayar", "Phong-Material");
        chbMaterial.getSelectionModel().select(1);
        sldExp = new Slider();
        sldExp.setMin(1);
        sldExp.setMax(256);
        sldExp.setValue(64);



        sldExp.disableProperty().bind(chbMaterial.getSelectionModel().selectedIndexProperty().isEqualTo(3).or(
                chbMaterial.getSelectionModel().selectedIndexProperty().isEqualTo(2)
        ).not());
        cpSpec.disableProperty().bind(chbMaterial.getSelectionModel().selectedIndexProperty().isEqualTo(3).not());
        final Label lblExp = new Label();
        lblExp.textProperty().bind(Bindings.concat("Exponent: ").concat(Bindings.format("%.0f",sldExp.valueProperty())));

        chbMaterial.setOnAction(a->{
            if(chbMaterial.getSelectionModel().getSelectedIndex() == 2){
                lblExp.textProperty().bind(Bindings.concat("Roughness: ").concat(Bindings.format("%.2f",sldExp.valueProperty())));
                sldExp.setMin(0);
                sldExp.setMax(1);
                sldExp.setValue(0.5);
            }else{
                lblExp.textProperty().bind(Bindings.concat("Exponent: ").concat(Bindings.format("%.0f",sldExp.valueProperty())));
                sldExp.setMin(1);
                sldExp.setMax(256);
                sldExp.setValue(64);
            }
        });


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK( st, cpColorPicker.getValue(),cpSpec.getValue(),chbMaterial.getSelectionModel().getSelectedIndex(),(int)sldExp.getValue()));
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a -> onCancel());



        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK, btnCancel);
        center.add(lblMaterial, 0, 0);
        center.add(chbMaterial, 1, 0);
        center.add(lblColorPicker, 0, 1);
        center.add(cpColorPicker, 1, 1);
        center.add(lblSpec, 0, 2);
        center.add(cpSpec, 1, 2);
        center.add(lblExp, 0, 3);
        center.add(sldExp, 1, 3);

        setValues(st);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 400, 300);
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("Create new Material");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    public void setValues(NewGeoStage st) {
    if(st.material!= null){
        if(st.material.get() instanceof SingleColorMaterial){
            SingleColorMaterial m = (SingleColorMaterial)st.material.get();
            chbMaterial.getSelectionModel().select(0);

        }if(st.material.get() instanceof LambertMaterial){
            LambertMaterial m = (LambertMaterial)st.material.get();
            chbMaterial.getSelectionModel().select(1);
        }if(st.material.get() instanceof OrenNayarMaterial){
            OrenNayarMaterial m = (OrenNayarMaterial)st.material.get();
            chbMaterial.getSelectionModel().select(2);
            sldExp.setValue(Math.sqrt(m.rough_sq));
        }if(st.material.get() instanceof PhongMaterial){
            PhongMaterial m = (PhongMaterial)st.material.get();
            chbMaterial.getSelectionModel().select(3);
            cpSpec.setValue( new Color( m.specular.r,m.specular.g,m.specular.b,1));
            sldExp.setValue(m.exponent);
        }
        cpColorPicker.setValue( new Color( st.material.get().diffuse.r,st.material.get().diffuse.g,st.material.get().diffuse.b,1));
    }

    }
    private void onCancel() {
        this.close();
    }
    private void onOK(NewGeoStage stage,Color c, Color s, int typ, int exp ) {
        Material material=null;
        if(typ == 0){
            material = new SingleColorMaterial(new utils.Color(c.getRed(),c.getGreen(),c.getBlue()));
        }else if(typ == 1){
            material = new LambertMaterial(new utils.Color(c.getRed(),c.getGreen(),c.getBlue()));
        }else if(typ == 2){
            material = new OrenNayarMaterial(new utils.Color(c.getRed(),c.getGreen(),c.getBlue()),exp);
        }else if(typ == 3){
            material = new PhongMaterial(new utils.Color(c.getRed(),c.getGreen(),c.getBlue()),new utils.Color(s.getRed(),s.getGreen(),s.getBlue()),exp);
        }
        if(material!=null)stage.material.set(material);

        this.close();
    }
}
