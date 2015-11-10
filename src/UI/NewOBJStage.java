package UI;

import geometries.ShapeFromFile;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import raytracer.ImageSaver;
import utils.Color;

import java.io.File;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewOBJStage extends Stage {


    private final ColorPicker cpColorPicker;
    private File file;

    public NewOBJStage(){
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

        final Label lblInfo = new Label("Do you wish to load and add a .Obj file?");


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a->onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a->onCancel());
        final Button btnFile = new Button("Choose File");
        btnFile.setPrefWidth(100);
        btnFile.setOnAction(a->onLoad(btnOK));
        btnOK.setDisable(true);

        if(ImageSaver.getWorld()==null){
            lblInfo.setText("No Scene Created!");
            lblInfo.setTextFill(javafx.scene.paint.Color.RED);
        }
        Label lblLoad = new Label("Choose a obj File to load.");
        lblLoad.setWrapText(true);


        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK,btnCancel);
        center.add(lblColorPicker,0,0);
        center.add(cpColorPicker,1,0);
        center.add(lblLoad,0,1);
        center.add(btnFile,1,1);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 600,300);
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("Import new .obj?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void onLoad(Button btnOK) {
        if(btnOK==null)throw new IllegalArgumentException("btnOk must be not null.");
        FileChooser dlg = new FileChooser();
        dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront obj File. (*.obj)","*.obj"));
        file = dlg.showOpenDialog(this);
        if(ImageSaver.getWorld()!=null && file != null){
            btnOK.setDisable(false);
        }
    }

    private void onCancel(){
        this.close();
    }
    private void onOK(){
        try{

            javafx.scene.paint.Color c = cpColorPicker.getValue();
            ShapeFromFile p = new ShapeFromFile(file.toString(),new Color(c.getRed(),c.getGreen(),c.getBlue()));
            ImageSaver.getWorld().geometries.add(p);

        }catch(NumberFormatException e){
            System.out.println("ZahlenFehler");
        }

        this.close();
    }
}
