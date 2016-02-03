package controller;

import UI.IO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import observables.geometries.OShapeFromFile;
import observables.materials.DefaultMaterial;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by roberto on 05.01.16.
 */
public class MenuController extends AController{

    @FXML
    Parent embeddedView;

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mnuSaveImage;

    /**
     * Handle action related to "About" menu item.
     *
     * @param event Event on "About" menu item.
     */
    @FXML
    private void handleSettingsAction(final ActionEvent event)
    {
        new RenderSettingsController();
    }

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }

    /**
     * Perform functionality associated with "About" menu selection or CTRL-A.
     */
    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }

    public void handleExitAction(ActionEvent actionEvent) {

        System.out.println(actionEvent);

        Stage stage = (Stage) menuBar.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void handleLoadModel(ActionEvent actionEvent) {
        FileChooser dlg = new FileChooser();
        dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront obj File. (*.obj)", "*.obj"));
        File file = dlg.showOpenDialog(menuBar.getScene().getWindow());
        if(file!= null){
            AOGeometry e = new OShapeFromFile(file.getName().split(".")[0],file.toString(), DefaultMaterial.getDefaultLambert(), true, true, true, false);
           ONode n= new ONode(
                   e.name.get(),
                   FXCollections.observableArrayList(e)
           );
            n.name=e.name;
            if(e!= null) ObservableElementLists.getInstance().addElement(n);
        }

    }

    public void handleSaveAction(ActionEvent actionEvent) {
        IO.saveScene((Stage)menuBar.getScene().getWindow(), raytracer.getWorld(), raytracer.getCamera());
    }

    public void handleLoadAction(ActionEvent actionEvent) {
        IO.loadScene((Stage)menuBar.getScene().getWindow());
    }


    public void handleRenderAction(ActionEvent actionEvent) {
        ImageView image  = (ImageView)menuBar.getParent().getParent().lookup("#image");
        if(image!=null)raytracer.render(image);
    }

    public void handleStopRenderAction(ActionEvent actionEvent) {
        raytracer.stopRender();
    }
}
