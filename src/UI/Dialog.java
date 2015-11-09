package UI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class Dialog extends Stage {

    private StringProperty title = new SimpleStringProperty("Info Dialog");
    private StringProperty text = new SimpleStringProperty("Info Dialog");



    public void setNewTitle(String title) {
        this.title.set(title);
    }

    public void setNewText(String text) {
        this.text.set(text);
    }

    public Dialog(String s){
        super();
        setNewTitle(s);
        Group root = new Group();
        Label lblInfo = new Label();
        lblInfo.setWrapText(true);
        lblInfo.textProperty().bind(text);

        final Button btnOK = new Button("OK");
        btnOK.setPrefWidth(100);
        btnOK.setOnAction(a->onOK());

        root.getChildren().addAll(lblInfo,btnOK);

        Scene scene = new Scene(root, 400,150);
        btnOK.translateXProperty().bind(scene.widthProperty().subtract(btnOK.widthProperty()).subtract(20));
        btnOK.translateYProperty().bind(scene.heightProperty().subtract(btnOK.heightProperty()).subtract(20));
        lblInfo.prefWidthProperty().bind(scene.widthProperty().divide(1.5));
        lblInfo.prefHeightProperty().bind(scene.heightProperty().divide(1.5));
        lblInfo.translateXProperty().bind(scene.widthProperty().subtract(lblInfo.widthProperty()).divide(2));
        lblInfo.translateYProperty().bind(scene.heightProperty().subtract(lblInfo.heightProperty()).divide(2));
        this.titleProperty().bind(title);
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);

    }


    private void onOK(){
        this.close();
    }
}
