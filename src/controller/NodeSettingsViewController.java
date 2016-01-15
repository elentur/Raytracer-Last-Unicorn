package controller;

import camera.Camera;
import geometries.Geometry;
import geometries.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import light.Light;
import utils.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class NodeSettingsViewController  extends AController{

    @FXML
     private  TabPane tabPane;
    private TreeItem<Element> selTreeItem=null;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        selectedTreeItem.addListener(a-> handleSelectedElement());
        masterTabPane=tabPane;
    }

    private void handleSelectedElement() {
        Element e = null;
       if(selectedTreeItem.get()!=null) e = selectedTreeItem.get().getValue();


       if(e==null)tabPane.getTabs().clear();
        try {
            if(e !=null){

                Tab t = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsView.fxml"));
                if(tabPane.getTabs().isEmpty() || !selectedTreeItem.getValue().equals(selTreeItem)){
                   tabPane.getTabs().clear();
                   tabPane.getTabs().add(t);
                    selTreeItem = selectedTreeItem.getValue();
                   if(e instanceof Node)t.setText("Node");
                   else if(e instanceof Light)t.setText("Light");
                   else if(e instanceof Camera)t.setText("Camera");
               }else {
                      tabPane.getTabs().get(0).setContent(t.getContent());
                      if(e instanceof Node) tabPane.getTabs().get(0).setText("Node");
                      else if(e instanceof Light) tabPane.getTabs().get(0).setText("Light");
                      else if(e instanceof Camera) tabPane.getTabs().get(0).setText("Camera");
               }
                if(e instanceof Geometry){
                    if(!((Node)e).geos.isEmpty()&&!(((Node)e).geos.get(0) instanceof Node)){
                        t = FXMLLoader.load(getClass().getResource("/fxml/mainMaterialSettingsView.fxml"));
                        t.setText("Material");
                        if(tabPane.getTabs().size()<2){
                            tabPane.getTabs().add(t);
                        }else {
                            tabPane.getTabs().get(1).setContent(t.getContent());
                        }
                    }
                }else{
                    if(tabPane.getTabs().size()>1){
                        Tab tab = tabPane.getTabs().get(0);
                        tabPane.getTabs().clear();
                        tabPane.getTabs().add(tab);
                    }
                }
               // tabPane.getSelectionModel().select(index);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}