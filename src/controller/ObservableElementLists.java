package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import observables.AOElement;
import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import observables.lights.AOLight;
import observables.materials.AOMaterial;
import observables.materials.DefaultMaterial;

import java.util.List;

/**
 * Created by Marcus Baetz on 11.01.2016.
 *
 * @author Marcus Bätz
 */
public class ObservableElementLists {
    private static final ObservableElementLists ourInstance = new ObservableElementLists();
    private TreeView<AOElement> treeView;
    private TreeItem<AOElement> nodeTree;
    private TreeItem<AOElement> lightTree;
    private TreeItem<AOElement> cameraTree;
    public final ObservableList<AOGeometry> geometries = FXCollections.observableArrayList();
    public final ObservableList<AOLight> lights = FXCollections.observableArrayList();
    public AOCamera camera = null;

    public static ObservableElementLists getInstance() {
        return ourInstance;
    }

    public void setTreeview(TreeView<AOElement> t) {
        treeView = t;
        nodeTree = t.getRoot().getChildren().get(0);
        lightTree = t.getRoot().getChildren().get(1);
        cameraTree = t.getRoot().getChildren().get(2);
    }

    private ObservableElementLists() {
    }

    public void addElement(AOElement e) {
        treeView.getSelectionModel().clearSelection();
        if (e instanceof AOCamera) {
            addCamera((AOCamera) e);
        } else if (e instanceof AOLight) {
            addLight((AOLight) e);
        } else if (e instanceof ONode) {
            addNode((ONode) e);
        } else {
            throw new IllegalArgumentException("Wrong typ of Object. Must be Camera, Light or Node");
        }
    }

    public void duplicateElement(ONode e){

        ONode parent = getParentNode(geometries,e);
        ONode newNode = e.getInstance();

        if(parent != null) {
            parent.oGeos.add(newNode);
            TreeItem<AOElement> tiParent = treeView.getSelectionModel().getSelectedItem().getParent();
            treeView.getSelectionModel().clearSelection();
            treeView.getSelectionModel().select(tiParent);
            TreeItem<AOElement> recTreeItem = addNodeRecursive(newNode);
            tiParent.getChildren().add(recTreeItem);
            treeView.getSelectionModel().clearSelection();
            treeView.getSelectionModel().select(recTreeItem);
        }else{
            treeView.getSelectionModel().clearSelection();
            addNode(newNode);
        }
    }

    public void removeElement(AOElement e) {
        if (e instanceof AOCamera) {
            removeCamera();
        } else if (e instanceof AOLight) {
            removeLight((AOLight) e);
        } else if (e instanceof ONode) {
            removeNode((ONode) e);
        } else {
            throw new IllegalArgumentException("Wrong typ of Object. Must be Camera, Light or Node");
        }
        treeView.getSelectionModel().clearSelection();
    }

    private void addCamera(AOCamera c) {

        cameraTree.getChildren().clear();
        TreeItem<AOElement> treeItem = new TreeItem<>(c);
        cameraTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        camera = c;
    }


    private void removeCamera() {

        cameraTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
        camera = null;
    }

    private void addLight(AOLight l) {
        TreeItem<AOElement> treeItem = new TreeItem<>(l);
        lightTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        lights.addAll(l);

    }


    private void removeLight(AOLight l) {
        lightTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
        lights.remove(l);
    }

    private void addNode(ONode n) {
        if (!(n.oGeos.get(0) instanceof ONode)) {
            TreeItem<AOElement> treeItem = new TreeItem<>(n);
            nodeTree.getChildren().add(treeItem);
            treeView.getSelectionModel().select(treeItem);
            geometries.add(n);
        } else {
            TreeItem<AOElement> treeItem = addNodeRecursive(n);
            nodeTree.getChildren().add(treeItem);
            treeView.getSelectionModel().select(treeItem);
            geometries.add(n);
        }
    }

    private TreeItem<AOElement> addNodeRecursive(ONode n) {
        if (!(n.oGeos.get(0) instanceof ONode)) {
            return new TreeItem<>(n);
        } else {
            TreeItem<AOElement> treeItem = new TreeItem<>(n);
            for (AOGeometry node : n.oGeos) {
                treeItem.getChildren().add(addNodeRecursive((ONode) node));
            }
            return treeItem;
        }
    }


    private void removeNode(ONode n) {
        if (nodeTree.getChildren().contains(treeView.getSelectionModel().getSelectedItem())) {
            nodeTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
            geometries.remove(n);
        } else {
            ONode parent = getParentNode(geometries, n);
            if (parent != null) {
                parent.oGeos.remove(n);
                TreeItem<AOElement> parentItem = treeView.getSelectionModel().getSelectedItem().getParent();
                parentItem.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
                if (parent.oGeos.isEmpty()) {
                    ONode parentParent = null;
                    TreeItem<AOElement> parentParentItem = null;
                    while (parentItem != nodeTree) {
                        parentParent = parent;
                        parent = getParentNode(geometries, parent);
                        parentParentItem = parentItem;
                        parentItem = parentItem.getParent();
                        if (parentItem.getChildren().size() > 1) {
                            break;
                        }

                    }
                    treeView.getSelectionModel().clearSelection();
                    parentItem.getChildren().remove(parentParentItem);
                    if (parent != null) parent.oGeos.remove(parentParent);
                    else geometries.remove(parentParent);
                }
            }
        }
    }

    private ONode getParentNode(ObservableList<AOGeometry> nodes, ONode n) {
        ONode node = null;
        for (AOGeometry p : nodes) {
            if (p instanceof ONode) {
                if (((ONode) p).oGeos.contains(n)) return (ONode) p;
                node = getParentNode(((ONode) p).oGeos, n);
            }
        }
        return node;
    }

    public void groupNodes(List<AOGeometry> nodes) {
        ONode parent = getParentNode(geometries, (ONode) nodes.get(0));
        ONode n = new ONode(
                "Group",
                nodes
        );
        TreeItem<AOElement> t = new TreeItem<>(n);
        if (parent == null) {
            for (TreeItem<AOElement> tItem : treeView.getSelectionModel().getSelectedItems()) {
                TreeItem<AOElement> treeItem = new TreeItem<>(tItem.getValue());
                treeItem.getChildren().addAll(tItem.getChildren());
                t.getChildren().addAll(treeItem);
                geometries.remove(tItem.getValue());
            }
            nodeTree.getChildren().removeAll(treeView.getSelectionModel().getSelectedItems());
            nodeTree.getChildren().add(t);
        } else {

            parent.oGeos.add(n);
            for (TreeItem<AOElement> tItem : treeView.getSelectionModel().getSelectedItems()) {
                TreeItem<AOElement> treeItem = new TreeItem<>(tItem.getValue());
                treeItem.getChildren().addAll(tItem.getChildren());
                t.getChildren().addAll(treeItem);
                parent.oGeos.remove(tItem.getValue());
                geometries.remove(tItem.getValue());
            }
            TreeItem<AOElement> treeItem = treeView.getSelectionModel().getSelectedItem().getParent();
            treeView.getSelectionModel().getSelectedItem().getParent().getChildren().removeAll(treeView.getSelectionModel().getSelectedItems());
            treeItem.getChildren().add(t);
        }
        treeView.getSelectionModel().clearSelection();
        treeView.getSelectionModel().select(t);
        geometries.add(n);

    }


    public void ungroupNodes(final ObservableList<TreeItem<AOElement>> children, final TreeItem<AOElement> parent) {
        TreeItem<AOElement> selectedItem = treeView.getSelectionModel().getSelectedItem();
        parent.getChildren().remove(selectedItem);
        ONode parentNode = getParentNode(geometries, (ONode) selectedItem.getValue());
        if (parentNode == null) geometries.remove(selectedItem.getValue());
        else parentNode.oGeos.remove(selectedItem.getValue());
        for (TreeItem<AOElement> item : children) {
            if (parentNode == null) geometries.add((AOGeometry) item.getValue());
            else parentNode.oGeos.add((AOGeometry) item.getValue());
            TreeItem<AOElement> t = new TreeItem(item.getValue());
            if (!item.getChildren().isEmpty()) t.getChildren().addAll(item.getChildren());
            parent.getChildren().add(t);
        }

    }

    public void clearAll() {
        ObservableList<AOMaterial> list = AController.materialList;
        list.remove(7, list.size());
        treeView.getSelectionModel().clearSelection();
        nodeTree.getChildren().clear();
        lightTree.getChildren().clear();
        cameraTree.getChildren().clear();
        lights.clear();
        geometries.clear();
        camera = null;
        AController.textureList.clear();
        AController.materialList.set(6, DefaultMaterial.getDefaultLambert());
    }
}
