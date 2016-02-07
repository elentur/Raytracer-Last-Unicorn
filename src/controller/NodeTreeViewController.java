package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import observables.AOElement;
import observables.cameras.*;
import observables.geometries.*;
import observables.lights.*;

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

        rootItem.getChildren().addAll(nodesRootTree, lightsRootTree, camerasRootTree);

        elementsTreeView.setRoot(rootItem);
        elementLists.setTreeview(elementsTreeView);

        elementsTreeView.setCellFactory(new ElementTreeCellFactory());

        // wenn element ausgewählt
        elementsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // wenn nur ein Elmenet ausgewählt wurde
            if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {
                selectedTreeItem.set(newValue);
            } else {
                selectedTreeItem.set(new TreeItem<>(new AOElement()));
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
        if (elementsTreeView != null && elementsTreeView.getSelectionModel().getSelectedItems().size() > 0) {
            ObservableList<TreeItem<AOElement>> selectedItems = elementsTreeView.getSelectionModel().getSelectedItems();
            List<AOGeometry> nodes = new ArrayList<>();
            TreeItem<AOElement> p = selectedItems.get(0).getParent();
            for (TreeItem<AOElement> t : selectedItems) {
                if (!(t.getValue() instanceof ONode) || !t.getParent().equals(p)) return;
                nodes.add((ONode) t.getValue());
            }
            elementLists.groupNodes(nodes);
        }
    }

    public void handleUngroupAction() {
        if (elementsTreeView != null && elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {
            TreeItem<AOElement> selectedItem = elementsTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem.getChildren().size() > 0 && (selectedItem.getChildren().get(0).getValue() instanceof ONode)) {
                ObservableList<TreeItem<AOElement>> selectedItems = selectedItem.getChildren();
                TreeItem<AOElement> parent = selectedItem.getParent();
                elementLists.ungroupNodes(selectedItems, parent);
            }
        }
    }

    public void handleDeleteAction() {
        if (selectedTreeItem.get() != null && selectedTreeItem.get().getValue() != null &&
                (selectedTreeItem.get().getValue() instanceof AOGeometry ||
                        selectedTreeItem.get().getValue() instanceof AOCamera ||
                        selectedTreeItem.get().getValue() instanceof AOLight)) {
            elementLists.removeElement(selectedTreeItem.get().getValue());
        }
    }

    @FXML
    private void handleNewElementAction() {
        if (cmbNewElement != null && cmbNewElement.getSelectionModel().getSelectedItem() != null && cmbNewElement.getSelectionModel().getSelectedItem() instanceof AOElement) {
            AOElement e = cmbNewElement.getSelectionModel().getSelectedItem();
            if (e instanceof AOLight || e instanceof AOCamera || e instanceof ONode) {
                if (e instanceof ONode) {
                    AOGeometry g = ((ONode) e).oGeos.get(0);
                    if (g instanceof OPlane) e = DefaultGeometries.getPlane();
                    else if (g instanceof OSphere) e = DefaultGeometries.getSphere();
                    else if (g instanceof OAxisAlignedBox) e = DefaultGeometries.getAxisAlignedBox();
                    else if (g instanceof OTriangle) e = DefaultGeometries.getTriangle();
                } else {
                    if (e instanceof ODirectionalLight) e = DefaultLight.getDirectionalLight();
                    else if (e instanceof OPointLight) e = DefaultLight.getPointLight();
                    else if (e instanceof OSpotLight) e = DefaultLight.getSpotLight();
                    else if (e instanceof OOrthographicCamera) e = DefaultCameras.getOrthographicCamera();
                    else if (e instanceof OPerspectiveCamera) e = DefaultCameras.getPerspectiveCamera();
                    else if (e instanceof ODOFCamera) e = DefaultCameras.getDOFCamera();
                }
                elementLists.addElement(e);


            }
        }
    }

    @FXML
    private void handleDuplicateAction(){
        if (selectedTreeItem.get() != null && selectedTreeItem.get().getValue() != null && selectedTreeItem.get().getValue() instanceof ONode)
        elementLists.duplicateElement(selectedTreeItem.get().getValue());
    }


}
