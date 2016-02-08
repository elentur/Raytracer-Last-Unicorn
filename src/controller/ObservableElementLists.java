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
 *
 * A Collection of Lists and tools to add, remove, group, ungroup or duplicate Elements in the TreeView
 */
public class ObservableElementLists {
    private static final ObservableElementLists ourInstance = new ObservableElementLists();
    /**
     * represents the TreeView
     */
    private TreeView<AOElement> treeView;
    /**
     * represents the TreeItem root for AOGeometry
     */
    private TreeItem<AOElement> nodeTree;
    /**
     * represents the TreeItem root for AOLight
     */
    private TreeItem<AOElement> lightTree;
    /**
     * represents the TreeItem root for AOCamera
     */
    private TreeItem<AOElement> cameraTree;
    /**
     * represents a list of all nodes equal to the geometry list in world
     */
    public final ObservableList<AOGeometry> geometries = FXCollections.observableArrayList();
    /**
     * represents a list of all lights equal to the light list in world
     */
    public final ObservableList<AOLight> lights = FXCollections.observableArrayList();
    /**
     * represents the camera equal to the camera of the raytracer
     */
    public AOCamera camera = null;

    /**
     *
     * @return the only instance of this Class
     */
    public static ObservableElementLists getInstance() {
        return ourInstance;
    }

    /**
     * sets the TreeView to treeView
     * @param t reference to TreeView
     */
    public void setTreeview(TreeView<AOElement> t) {
        treeView = t;
        nodeTree = t.getRoot().getChildren().get(0);
        lightTree = t.getRoot().getChildren().get(1);
        cameraTree = t.getRoot().getChildren().get(2);
    }

    private ObservableElementLists() {
    }

    /**
     * this Method delegates all addCalls to the relevant Typ Method
     * @param e the Element that have to be added
     */
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

    /**
     * this Method delegates all removeCalls to the relevant Typ Method
     * @param e the Element that vae to be removed
     */
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
    /**
     * this Method adds a new Camera
     * @param c the camera that have to be added
     */
    private void addCamera(AOCamera c) {

        cameraTree.getChildren().clear();
        TreeItem<AOElement> treeItem = new TreeItem<>(c);
        cameraTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        camera = c;
    }

    /**
     * this Method removes the selected Camera
     */
    private void removeCamera() {

        cameraTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
        camera = null;
    }
    /**
     * this Method adds a new light
     * @param l the light that have to be added
     */
    private void addLight(AOLight l) {
        TreeItem<AOElement> treeItem = new TreeItem<>(l);
        lightTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        lights.addAll(l);

    }

    /**
     * this Method removes the selected light
     */
    private void removeLight(AOLight l) {
        lightTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
        lights.remove(l);
    }
    /**
     * this Method adds a new Node
     * @param n the node that have to be added
     */
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

    /**
     * a recursiv addNode call for nodes that have subNodes
     * @param n the node that have to be adde
     * @return the TreeItem including all necessary subTreeItems
     */
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

    /**
     * this Method removes the selected node
     */
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

    /**
     * returns the parent node of a node or null if the node has no parent
     * @param nodes list of all nodes o the same level an with the same parent
     * @param n node that have to be checked for parent
     * @return the parent node of a node or null if the node has no parent
     */
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

    /**
     * groups multiple nodes that are part of the same parentNode
     * @param nodes list of nodes that have to be grouped to a new node
     */
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

    /**
     * ungroup a node and put all subNodes in the parentNode
     * @param children List of all subNodes
     * @param parent the ParentNode
     */
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
    /**
     * this Method duplicates Nodes and lights
     * @param e
     */
    public void duplicateElement(AOElement e){

        if(e instanceof ONode) {

            ONode parent = getParentNode(geometries, (ONode) e);
            ONode newNode = ((ONode) e).getInstance();

            if (parent != null) {
                parent.oGeos.add(newNode);
                TreeItem<AOElement> tiParent = treeView.getSelectionModel().getSelectedItem().getParent();
                treeView.getSelectionModel().clearSelection();
                treeView.getSelectionModel().select(tiParent);
                TreeItem<AOElement> recTreeItem = addNodeRecursive(newNode);
                tiParent.getChildren().add(recTreeItem);
                treeView.getSelectionModel().clearSelection();
                treeView.getSelectionModel().select(recTreeItem);
            } else {
                treeView.getSelectionModel().clearSelection();
                addNode(newNode);
            }
        }else if(e instanceof AOLight){
            AOLight newLight = ((AOLight) e).getInstance();
            treeView.getSelectionModel().clearSelection();
            addLight(newLight);
        }
    }

    /**
     * Clear all list for a new setup after loading a new scene
     */
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
