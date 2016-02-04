package controller;

import geometries.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import observables.AOElement;
import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import observables.lights.AOLight;
import raytracer.Raytracer;
import utils.Element;

import java.util.List;

/**
 * Created by Marcus Baetz on 11.01.2016.
 *
 * @author Marcus Bätz
 */
public class ObservableElementLists {
    private final Raytracer r = AController.raytracer;
    private static ObservableElementLists ourInstance = new ObservableElementLists();
    private TreeView<AOElement> treeView;
    private TreeItem<AOElement> nodeTree;
    private TreeItem<AOElement> lightTree;
    private TreeItem<AOElement> cameraTree;
    public ObservableList<AOGeometry> geometries = FXCollections.observableArrayList();
    public ObservableList<AOLight> lights = FXCollections.observableArrayList();
    public AOCamera camera=null;

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

    public void updateElement(AOElement oldElement, AOElement newElement) {
        if (newElement instanceof AOCamera) {
            updateCamera((AOCamera) newElement);
        } else if (newElement instanceof AOLight) {
            updateLight((AOLight) oldElement, (AOLight) newElement);
        } else if (newElement instanceof ONode) {
            updateNode((ONode) oldElement, (ONode) newElement);
        } else {
            throw new IllegalArgumentException("Wrong typ of Object. Must be Camera, Light or Node");
        }
        //AController.selectedTreeItem.get().setValue(newElement);
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
        camera=c;
    }

    private void updateCamera(AOCamera c) {
     /*   r.setCamera(c);
        treeView.getSelectionModel().getSelectedItem().setValue(c);*/
    }

    private void removeCamera() {

        cameraTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
        camera=null;
    }

    private void addLight(AOLight l) {
        TreeItem<AOElement> treeItem = new TreeItem<>(l);
        lightTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        lights.addAll(l);

    }
    private void updateLight(AOLight oldLight, AOLight newLight) {
      /*
        r.getWorld().lights.set(r.getWorld().lights.indexOf(oldLight), newLight);
        treeView.getSelectionModel().getSelectedItem().setValue(newLight);*/
    }

    private void removeLight(AOLight l) {
        lightTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
        lights.remove(l);
    }

    private void addNode(ONode n) {
        TreeItem<AOElement> treeItem = new TreeItem<>(n);
        nodeTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        geometries.add(n);
    }


    private void updateNode(ONode oldNode, ONode newNode) {
       /* if (r.getWorld().geometries.contains(oldNode)) {
       // nodes.set(nodes.indexOf(oldNode), newNode);
            r.getWorld().geometries.set(r.getWorld().geometries.indexOf(oldNode), newNode);
        }else {
            Node n = getParentNode(r.getWorld().geometries,oldNode);
            n.geos.set(n.geos.indexOf(oldNode), newNode);
        }
        if(oldNode.geos.size()== 1 && !(oldNode.geos.get(0) instanceof Node)){
            geometries.remove(oldNode);
            geometries.add(newNode);
        }
        if(treeView.getSelectionModel().getSelectedItem().getValue().equals(oldNode)){
            treeView.getSelectionModel().getSelectedItem().setValue(newNode);
        }else{
            TreeItem<Element> n = getTreeItem(nodeTree,oldNode);
            System.out.println(n);

        }
*/
    }
    private TreeItem<Element> getTreeItem(TreeItem<Element> root, Node n){
      /*  for(TreeItem<Element> child: root.getChildren()){
            if(child.getValue().equals(n)){
               return(child);
            } else {
                return getTreeItem(child,n);
            }
        }*/
        return null;
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
                    while (parentItem!= nodeTree){
                        parentParent = parent;
                        parent = getParentNode(geometries, parent);
                        parentParentItem = parentItem;
                        parentItem= parentItem.getParent();
                        if(parentItem.getChildren().size()>1){
                            break;
                        }

                    }
                    treeView.getSelectionModel().clearSelection();
                    parentItem.getChildren().remove(parentParentItem);
                    if(parent!=null)parent.oGeos.remove(parentParent);
                    else geometries.remove(parentParent);
                }
            }
        }
    }

    private ONode getParentNode(ObservableList<AOGeometry> nodes, ONode n) {
        ONode node = null;
        for (AOGeometry p : nodes) {
            if(p instanceof ONode ) {
                if (((ONode) p).oGeos.contains(n)) return (ONode) p;
                node =  getParentNode(((ONode) p).oGeos, n);
            }
        }
        return node;
    }

    public void groupNodes(List<AOGeometry> nodes){
        ONode parent = getParentNode(geometries,(ONode)nodes.get(0));
        ONode n = new ONode(
                "Group",
                nodes
        );
        TreeItem<AOElement> t = new TreeItem<>(n);
        if(parent==null){
            for(TreeItem<AOElement> tItem : treeView.getSelectionModel().getSelectedItems()){
                TreeItem<AOElement> treeItem = new TreeItem<>(tItem.getValue());
                treeItem.getChildren().addAll(tItem.getChildren());
                t.getChildren().addAll(treeItem);
                geometries.remove(tItem.getValue());
            }
            nodeTree.getChildren().removeAll(treeView.getSelectionModel().getSelectedItems());
            nodeTree.getChildren().add(t);
        }else{

            parent.oGeos.add(n);
            for(TreeItem<AOElement> tItem : treeView.getSelectionModel().getSelectedItems()){
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
        TreeItem<AOElement> selectedItem =  treeView.getSelectionModel().getSelectedItem();
        parent.getChildren().remove(selectedItem);
        ONode parentNode = getParentNode(geometries,(ONode)selectedItem.getValue());
        if(parentNode == null)geometries.remove(selectedItem.getValue());
        else  parentNode.oGeos.remove(selectedItem.getValue());
        for (TreeItem<AOElement> item : children) {
            if(parentNode == null)geometries.add((AOGeometry) item.getValue());
            else parentNode.oGeos.add((AOGeometry) item.getValue());
            TreeItem<AOElement> t = new TreeItem(item.getValue());
            if (!item.getChildren().isEmpty()) t.getChildren().addAll(item.getChildren());
            parent.getChildren().add(t);
        }

    }
}
