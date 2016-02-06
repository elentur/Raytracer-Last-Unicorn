package controller;

import UI.Dialog;
import UI.IO;
import javafx.beans.property.SimpleDoubleProperty;
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
import observables.lights.AOLight;

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

            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S)
            {
                System.out.println("ctr + s");
                saveAction();
            }

            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.L)
            {
                System.out.println("ctr + s");
                loadAction();
            }
        }
    }

    public void handleExitAction(ActionEvent actionEvent) {

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
            AOGeometry e = new OShapeFromFile(file.toString());
           ONode n= new ONode(
                   e.name.get(),
                   FXCollections.observableArrayList(e)
           );
            n.name=e.name;
            if(e!= null) ObservableElementLists.getInstance().addElement(n);
        }

    }

    public void handleSaveAction(ActionEvent actionEvent) {
        saveAction();
    }

    private void saveAction(){
        IO.saveScene((Stage)menuBar.getScene().getWindow(), rootItem);
    }

    public void handleLoadAction(ActionEvent actionEvent) {
        loadAction();
    }

    public void loadAction(){
        IO.loadScene((Stage)menuBar.getScene().getWindow(),rootItem);
    }


    public void handleRenderAction(ActionEvent actionEvent) {
        ObservableElementLists list = ObservableElementLists.getInstance();
        if(list.camera!=null){
            raytracer.setCamera(list.camera.generate());
        }else{
            Dialog dlg = new Dialog("No Camera created.");
            dlg.setNewText("There is no Camera in this scene.");
            dlg.showAndWait();
            return;
        }
        raytracer.getWorld().lights.clear();
        raytracer.getWorld().geometries.clear();
        for(AOLight light : list.lights){
            raytracer.getWorld().lights.add(light.generate());
        }
        for(AOGeometry geo : list.geometries){
            raytracer.getWorld().geometries.add(geo.generate());
        }
        if(list.camera!=null) raytracer.setCamera(list.camera.generate());
        raytracer.progress = new SimpleDoubleProperty(0);
        RenderViewController rvc = new RenderViewController();
        ImageView image  = rvc.getImageView();
        if(image!=null)raytracer.render(image);
    }

    public void handleStopRenderAction(ActionEvent actionEvent) {
        raytracer.stopRender();
    }
}
