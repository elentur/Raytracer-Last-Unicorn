package UI;

import geometries.Sphere;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import matVect.Point3;
import raytracer.ImageSaver;
import utils.Color;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewSphereStage extends Stage {

    final NumberTextField[] txtInputs;
    private final ColorPicker cpColorPicker;

    public NewSphereStage(){
        super();
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPercentWidth(25);
        center.getColumnConstraints().addAll(col1,col2,col3, col4);

        cpColorPicker = new ColorPicker(javafx.scene.paint.Color.LIGHTGRAY);
        final Label lblColorPicker = new Label("Color:");

        final Label lblInfo = new Label("Do you wish to create a new Sphere?");


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a->onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a->onCancel());
        if(ImageSaver.getWorld()==null){
            lblInfo.setText("No Scene Created!");
            lblInfo.setTextFill(javafx.scene.paint.Color.RED);
            btnOK.setDisable(true);
        }



        final Label lblTranslate = new Label("Translation");
        final Label lblRadius = new Label("Radius");
        final Label lblX = new Label("x");
        final Label lblY = new Label("y");
        final Label lblZ = new Label("z");
        txtInputs = new NumberTextField[4];
        for (int i = 0; i < 4 ; i++) {
            txtInputs[i] = new NumberTextField("0.0");
            center.add(txtInputs[i],(i%3)+1,(i/3)+2);
        }

        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK,btnCancel);
        center.add(lblColorPicker,0,0);
        center.add(cpColorPicker,1,0);
        center.add(lblX,1,1);
        center.add(lblY,2,1);
        center.add(lblZ,3,1);
        center.add(lblTranslate,0,2);
        center.add(lblRadius,0,3);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 600,300);
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("Create new Sphere?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void onCancel(){
        this.close();
    }
    private void onOK(){
        try{
            Point3 center = new Point3(
                    Double.parseDouble(txtInputs[0].getText()),
                    Double.parseDouble(txtInputs[1].getText()),
                    Double.parseDouble(txtInputs[2].getText()));
            double r = Double.parseDouble(txtInputs[3].getText());
            if(r <= 0.0) r=1.0;
            javafx.scene.paint.Color c = cpColorPicker.getValue();
            Sphere p = new Sphere(center,r,new Color(c.getRed(),c.getGreen(),c.getBlue()));
            ImageSaver.getWorld().geometries.add(p);

        }catch(NumberFormatException e){
            System.out.println("ZahlenFehler");
        }

        this.close();
    }
}
