package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import observables.AOElement;
import observables.cameras.AOCamera;
import observables.cameras.DefaultCameras;
import observables.geometries.AOGeometry;
import observables.geometries.DefaultGeometries;
import observables.geometries.ONode;
import observables.lights.AOLight;
import observables.lights.DefaultLight;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by roberto on 05.01.16.
 *
 * @author Robert Dziuba
 */
public class NodeTreeViewController extends AController {

    @FXML
    private ComboBox<AOElement> cmbNewElement;

    @FXML
    private TreeView<AOElement> elementsTreeView;

    @FXML
    private TreeItem<AOElement> nodesRootTree;

    @FXML
    private TreeItem<AOElement> lightsRootTree;

    @FXML
    private TreeItem<AOElement> camerasRootTree;


    public void initialize(URL url, ResourceBundle resource) {
        initializeComobox();
        initializeTreeView();

    }

    private void initializeTreeView() {

        elementsTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nodesRootTree = new TreeItem<>();
        nodesRootTree.setValue(new AOElement("Nodes"));


        lightsRootTree = new TreeItem<>();
        lightsRootTree.setValue(new AOElement("Lights"));


        camerasRootTree = new TreeItem<>();
        camerasRootTree.setValue(new AOElement("Camera"));



        TreeItem<AOElement> root = new TreeItem<>();
        root.setExpanded(true);
        root.setValue(new AOElement("Elements"));


        root.getChildren().addAll(nodesRootTree, lightsRootTree, camerasRootTree);

        elementsTreeView.setRoot(root);
        elementLists.setTreeview(elementsTreeView);

        // TODO in eigene Klasse auslagern
        elementsTreeView.setCellFactory(new ElementTreeCellFactory());

        // wenn element ausgewählt
        elementsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {

                if (newValue != null && newValue.getValue() instanceof AOGeometry || newValue.getValue() instanceof AOLight || newValue.getValue() instanceof AOCamera) {

                    selectedTreeItem.set(newValue);
                    return;
                }else{
                    selectedTreeItem.set(null);
                }
            }else{
                selectedTreeItem.set(null);
            }

        });

    }

    private void initializeComobox() {

        // TODO in eigene Klasse auslagern
        cmbNewElement.setCellFactory(new Callback<ListView<AOElement>, ListCell<AOElement>>() {
            @Override
            public ListCell<AOElement> call(ListView<AOElement> c) {

                return new ListCell<AOElement>() {
                    @Override
                    protected void updateItem(AOElement item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.name.getValue());
                        }
                    }
                };
            }
        });

        cmbNewElement.setButtonCell(new ListCell<AOElement>() {
            @Override
            protected void updateItem(AOElement item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.name.getValue());
                }
            }
        });

        ObservableList<AOElement> lstNewElement = cmbNewElement.getItems();

        lstNewElement.add(DefaultGeometries.getPlane());
        lstNewElement.add(DefaultGeometries.getSphere());
        lstNewElement.add(DefaultGeometries.getAxisAlignedBox());
        lstNewElement.add(DefaultGeometries.getTriangle());

        lstNewElement.add(DefaultLight.getDirectionalLight());
        lstNewElement.add(DefaultLight.getPointLight());
        lstNewElement.add(DefaultLight.getSpotLight());

        lstNewElement.add(DefaultCameras.getOrthographicCamera());
        lstNewElement.add(DefaultCameras.getPerspectiveCamera());
        lstNewElement.add(DefaultCameras.getDOFCamera());

    }

    public void handleGroupAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItems().size() > 0) {
            ObservableList<TreeItem<AOElement>> selectedItems = elementsTreeView.getSelectionModel().getSelectedItems();
            List<AOGeometry> nodes = new ArrayList<>();
            TreeItem<AOElement> p =selectedItems.get(0).getParent();
            for(TreeItem<AOElement> t: selectedItems){
                if(!(t.getValue()instanceof ONode) ||!t.getParent().equals(p))return;
                nodes.add((ONode)t.getValue());
            }
            elementLists.groupNodes(nodes);
        }
    }

    public void handleUngroupAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {
            TreeItem<AOElement> selectedItem = elementsTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem.getChildren().size() > 0 && (selectedItem.getChildren().get(0).getValue() instanceof ONode)) {
                ObservableList<TreeItem<AOElement>> selectedItems = selectedItem.getChildren();
                TreeItem<AOElement> parent = selectedItem.getParent();
                elementLists.ungroupNodes(selectedItems,parent);
            }
        }
    }
    public void handleDeleteAction() {
        elementLists.removeElement(selectedTreeItem.get().getValue());
    }

    @FXML
    private void handleNewElementAction() {
        if (cmbNewElement.getSelectionModel().getSelectedItem() != null && cmbNewElement.getSelectionModel().getSelectedItem() instanceof AOElement) {
          AOElement e = cmbNewElement.getSelectionModel().getSelectedItem();
            if(e instanceof AOLight || e instanceof AOCamera || e instanceof ONode){
                elementLists.addElement( e);
            }


        }
    }


}
