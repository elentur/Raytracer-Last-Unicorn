package UI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 *
 * A clas for short messages for usage with the interface
 */
public class Dialog extends Stage {
    /**
     * represents the Dialog Title
     */
    private final StringProperty title = new SimpleStringProperty("Info Dialog");
    /**
     * represents the Dialog InfoText
     */
    private final StringProperty text = new SimpleStringProperty("Info Dialog");


    private void setNewTitle(String title) {
        this.title.set(title);
    }

    public void setNewText(String text) {
        this.text.set(text);
    }

    /**
     *
     * @param s Title text
     */
    public Dialog(String s) {
        super();
        setNewTitle(s);
        VBox root = new VBox();
        Group group = new Group();
        Label lblInfo = new Label();
        lblInfo.setWrapText(true);
        lblInfo.textProperty().bind(text);

        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a -> onOK());

        group.getChildren().addAll(lblInfo, btnOK);
        root.getChildren().add(group);

        Scene scene = new Scene(root, 400, 150);
        btnOK.translateXProperty().bind(scene.widthProperty().subtract(btnOK.widthProperty()).subtract(20));
        btnOK.translateYProperty().bind(scene.heightProperty().subtract(btnOK.heightProperty()).subtract(20));
        lblInfo.prefWidthProperty().bind(scene.widthProperty().divide(1.5));
        lblInfo.prefHeightProperty().bind(scene.heightProperty().divide(1.5));
        lblInfo.translateXProperty().bind(scene.widthProperty().subtract(lblInfo.widthProperty()).divide(2));
        lblInfo.translateYProperty().bind(scene.heightProperty().subtract(lblInfo.heightProperty()).divide(2));
        scene.getStylesheets().add("css/rootStyle.css");
        this.titleProperty().bind(title);
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);

    }


    private void onOK() {
        this.close();
    }
}
