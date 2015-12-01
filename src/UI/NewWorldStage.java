package UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import raytracer.ImageSaver;
import utils.Color;
import utils.World;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewWorldStage extends Stage {

    private final ColorPicker cpColorPicker;
    private final ColorPicker cpAmbientColor;

    public NewWorldStage() {
        super();
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final GridPane center = new GridPane();
        center.setPadding(new Insets(20));
        center.setVgap(20);
        center.setHgap(20);

        final Label lblInfo = new Label("Do you wish to create a new Scene?");


        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK());
        final Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(a -> onCancel());

        cpColorPicker = new ColorPicker(javafx.scene.paint.Color.BLACK);
        final Label lblColorPicker = new Label("Background-Color:");
        final Label lblAmbient = new Label("Ambientlight-Color");
        cpAmbientColor = new ColorPicker(new javafx.scene.paint.Color(0.15, 0.15, 0.15, 1));
        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnOK, btnCancel);
        center.add(lblColorPicker, 0, 0);
        center.add(cpColorPicker, 1, 0);
        center.add(lblAmbient, 0, 1);
        center.add(cpAmbientColor, 1, 1);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 400, 200);
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("Create new Scene?");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void onCancel() {
        this.close();
    }

    private void onOK() {
        final javafx.scene.paint.Color c = cpColorPicker.getValue();
        final javafx.scene.paint.Color a = cpAmbientColor.getValue();
        final World world = new World(new Color(c.getRed(), c.getGreen(), c.getBlue()), new Color(a.getRed(), a.getGreen(), a.getBlue()));
        ImageSaver.raytracer.setWorld(world);
        this.close();
    }
}
