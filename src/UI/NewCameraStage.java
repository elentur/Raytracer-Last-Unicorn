package UI;

import camera.Camera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import matVect.Point3;
import matVect.Vector3;
import raytracer.ImageSaver;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewCameraStage extends Stage {
    final RadioButton rbOrthographic;
    final RadioButton rbPerspective;
    final NumberTextField txtParam;
    final NumberTextField[] txtInputs;

    public NewCameraStage(){
        super();
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPercentWidth(20);
        center.getColumnConstraints().addAll(col1,col2,col3, col4);


        final Label lblInfo = new Label("Do you wish to create a new Camera?");


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a->onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a->onCancel());

        final ToggleGroup group = new ToggleGroup();
        rbPerspective = new RadioButton("Perspective Camera");
        rbPerspective.setToggleGroup(group);
        rbPerspective.setSelected(true);
        rbOrthographic = new RadioButton("Orthographic Camera");
        rbOrthographic.setToggleGroup(group);


        final Label lblTranslate = new Label("Translation");
        final Label lblDirection = new Label("Direction");
        final Label lblOrientation = new Label("Orientation");
        final Label lblX = new Label("x");
        final Label lblY = new Label("y");
        final Label lblZ = new Label("z");
        final Label lblParam = new Label("Opening Angel in Degree");
        rbPerspective.selectedProperty().addListener(a->{
            if(rbPerspective.isSelected()){
                lblParam.setText("Opening Angel in Degree");
            }else{
                lblParam.setText("Scaling Factor");
            }
        });
        lblParam.setWrapText(true);
        txtParam = new NumberTextField("0.0");
        txtInputs = new NumberTextField[9];
        center.add(txtParam,1,5);
        for (int i = 0; i < 9 ; i++) {
            txtInputs[i] = new NumberTextField("0.0");
            center.add(txtInputs[i],(i%3)+1,(i/3)+2);
        }

        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK,btnCancel);
        center.add(rbPerspective,0,0,2,1);
        center.add(rbOrthographic,2,0,2,1);

        center.add(lblX,1,1);
        center.add(lblY,2,1);
        center.add(lblZ,3,1);
        center.add(lblTranslate,0,2);
        center.add(lblDirection,0,3);
        center.add(lblOrientation,0,4);
        center.add(lblParam,0,5);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 600,400);

        this.setTitle("Create new Camera?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void onCancel(){
        this.close();
    }
    private void onOK(){
        try{
        Camera cam = null;
            Point3 e = new Point3(
                    Double.parseDouble(txtInputs[0].getText()),
                    Double.parseDouble(txtInputs[1].getText()),
                    Double.parseDouble(txtInputs[2].getText()));
            Vector3 g = new Vector3(
                    Double.parseDouble(txtInputs[3].getText()),
                    Double.parseDouble(txtInputs[4].getText()),
                    Double.parseDouble(txtInputs[5].getText()));
            Vector3 t = new Vector3(
                    Double.parseDouble(txtInputs[6].getText()),
                    Double.parseDouble(txtInputs[7].getText()),
                    Double.parseDouble(txtInputs[8].getText()));
            double s = Double.parseDouble(txtParam.getText());
            if (rbPerspective.isSelected()){
                if (s>90.0) s=90.0;
                if (s<5.0) s= 5.0;
                double a = s*(180/Math.PI);
                cam = new PerspectiveCamera(e,g,t,a);
            }else{
                if(s < 1.0) s= 1.0;
                cam = new OrthographicCamera(e,g,t,s);
            }
            ImageSaver.setCamera(cam);

        }catch(NumberFormatException e){
            System.out.println("ZahlenFehler");
        }

        this.close();
    }
}
