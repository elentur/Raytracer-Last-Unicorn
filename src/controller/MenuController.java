package controller;

import UI.Dialog;
import UI.IO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import observables.geometries.OShapeFromFile;
import observables.lights.AOLight;
import raytracer.ImageSaver;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by roberto on 05.01.16.
 * <p>
 * The Controller for the Menu-Bar
 */
public class MenuController extends AController {


    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu runMenu;

    /**
     * Handle action related to  menu item.
     */
    @FXML
    private void handleSettingsAction() {
        new RenderSettingsController();
    }


    /**
     * Handler for Exit the Program
     */
    public void handleExitAction() {

        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileMenu.setOnShowing(e -> {
            Object obj = menuBar.getScene().getFocusOwner();
            menuBar.requestFocus();
            setFocusBack(obj);
        });
        runMenu.setOnShowing(e -> {
            Object obj = menuBar.getScene().getFocusOwner();
            menuBar.requestFocus();
            setFocusBack(obj);
        });
    }

    /**
     * Reset the Focus to the Element selected before clicking the MenuBar
     *
     * @param element
     */
    private void setFocusBack(Object element) {

        if (element instanceof Node) {
            ((Node) element).requestFocus();
        } else {
            Scene scene = menuBar.getScene();
            Node node = scene.lookup("#elementsTreeView");
            node.requestFocus();
        }
    }

    /**
     * Handler for Loading a new obj Model
     */
    public void handleLoadModel() {
        FileChooser dlg = new FileChooser();
        dlg.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront obj File. (*.obj)", "*.obj"));
        File file = dlg.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            AOGeometry e = new OShapeFromFile(file.toString());
            ONode n = new ONode(
                    e.name.get(),
                    FXCollections.observableArrayList(e)
            );
            n.name = e.name;
            ObservableElementLists.getInstance().addElement(n);
        }

    }

    /**
     * handler for saving a scene
     */
    public void handleSaveAction() {
        IO.saveScene((Stage) menuBar.getScene().getWindow(), rootItem);
    }

    /**
     * handler for loading a scene
     */
    public void handleLoadAction() {
        IO.loadScene((Stage) menuBar.getScene().getWindow());
    }


    /**
     * Handler to start new rendering
     */
    public void handleRenderAction() {
        ObservableElementLists list = ObservableElementLists.getInstance();
        if (list.camera != null) {
            raytracer.setCamera(list.camera.generate());
        } else {
            Dialog dlg = new Dialog("No Camera created.");
            dlg.setNewText("There is no Camera in this scene.");
            dlg.showAndWait();
            return;
        }
        raytracer.getWorld().lights.clear();
        raytracer.getWorld().geometries.clear();
        raytracer.getWorld().lights.addAll(list.lights.stream().map(AOLight::generate).collect(Collectors.toList()));
        raytracer.getWorld().geometries.addAll(list.geometries.stream().map(AOGeometry::generate).collect(Collectors.toList()));
        if (list.camera != null) raytracer.setCamera(list.camera.generate());
        raytracer.progress = new SimpleDoubleProperty(0);
        RenderViewController rvc = new RenderViewController();
        ImageView image = rvc.getImageView();
        if (image != null) {
            raytracer.render(image);
            ImageSaver.image.setImage(image.getImage());
        }
    }

    /**
     * handler for stop rendering
     */
    public void handleStopRenderAction() {
        raytracer.stopRender();
    }
}
