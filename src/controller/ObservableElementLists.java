package controller;

import geometries.Node;
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

import java.util.ArrayList;
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
    private List<Node> geometries = new ArrayList<>();


    public static ObservableElementLists getInstance() {
        return ourInstance;
    }

    public List<Node> getGeometries(){
        return geometries;
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
       /* if (r.getCamera() == null) {
           // camera.setValue(c);
            r.setCamera(c);
            TreeItem<AOElement> treeItem = new TreeItem<AOElement>(c);
            cameraTree.getChildren().add(treeItem);
            treeView.getSelectionModel().select(treeItem);
        }*/
    }

    private void updateCamera(AOCamera c) {
     /*   r.setCamera(c);
        treeView.getSelectionModel().getSelectedItem().setValue(c);*/
    }

    private void removeCamera() {
        /*r.setCamera(null);
        cameraTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();*/
    }

    private void addLight(AOLight l) {
        TreeItem<AOElement> treeItem = new TreeItem<>(l);
        lightTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);

    }
    private void updateLight(AOLight oldLight, AOLight newLight) {
      /*
        r.getWorld().lights.set(r.getWorld().lights.indexOf(oldLight), newLight);
        treeView.getSelectionModel().getSelectedItem().setValue(newLight);*/
    }

    private void removeLight(AOLight l) {
       /*
        r.getWorld().lights.remove(l);
        lightTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();*/
    }

    private void addNode(ONode n) {
    /*
        r.getWorld().geometries.add(n);
        TreeItem<Element> treeItem = new TreeItem<>(n);
        nodeTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
        if(n.geos.size()== 1 && !(n.geos.get(0) instanceof Node)){
            geometries.add(n);
        }
        */
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
        /*if (r.getWorld().geometries.contains(n)) {
            r.getWorld().geometries.remove(n);
            nodeTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        } else {
            Node parent = getParentNode(r.getWorld().geometries, n);
            if (parent != null) {
                parent.geos.remove(n);
                TreeItem<Element> parentItem = treeView.getSelectionModel().getSelectedItem().getParent();
                parentItem.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
                if (parent.geos.isEmpty()) {
                    Node parentParent = null;
                    TreeItem<Element> parentParentItem = null;
                    while (parentItem!= nodeTree){
                        parentParent = parent;
                        parent = getParentNode(r.getWorld().geometries, parent);
                        parentParentItem = parentItem;
                        parentItem= parentItem.getParent();
                        if(parentItem.getChildren().size()>1){
                            break;
                        }

                    }
                    treeView.getSelectionModel().clearSelection();
                    parentItem.getChildren().remove(parentParentItem);
                    if(parent!=null)parent.geos.remove(parentParent);
                    else r.getWorld().geometries.remove(parentParent);
                }
            }
        }
        if(n.geos.size()== 1 && !(n.geos.get(0) instanceof Node)){
            geometries.remove(n);
        }*/
    }

    private ONode getParentNode(List<AOGeometry> nodes, ONode n) {
     /*   Node node = null;
        for (Geometry p : nodes) {
            if(p instanceof Node ) {
                if (((Node) p).geos.contains(n)) return (Node) p;
                node =  getParentNode(((Node) p).geos, n);
            }
        }
        return node;*/
        return null;
    }

    public void groupNodes(List<AOGeometry> nodes){
        /*Node parent = getParentNode(r.getWorld().geometries,(Node)nodes.get(0));
        Node n = new Node(
                new Point3(0,0,0),
                new Point3(1,1,1),
                new Point3(0,0,0),
                nodes,
                true,true,true,false
        );
        n.name="group";
        TreeItem<Element> t = new TreeItem<>(n);
        if(parent==null){
            r.getWorld().geometries.add(n);
            for(TreeItem<Element> tItem : treeView.getSelectionModel().getSelectedItems()){
                TreeItem<Element> treeItem = new TreeItem<>(tItem.getValue());
                treeItem.getChildren().addAll(tItem.getChildren());
                t.getChildren().addAll(treeItem);
                r.getWorld().geometries.remove(tItem.getValue());
            }
            nodeTree.getChildren().removeAll(treeView.getSelectionModel().getSelectedItems());
            nodeTree.getChildren().add(t);
        }else{

            parent.geos.add(n);
            for(TreeItem<Element> tItem : treeView.getSelectionModel().getSelectedItems()){
                TreeItem<Element> treeItem = new TreeItem<>(tItem.getValue());
                treeItem.getChildren().addAll(tItem.getChildren());
                t.getChildren().addAll(treeItem);
                parent.geos.remove(tItem.getValue());
            }
            TreeItem<Element> treeItem = treeView.getSelectionModel().getSelectedItem().getParent();
            treeView.getSelectionModel().getSelectedItem().getParent().getChildren().removeAll(treeView.getSelectionModel().getSelectedItems());
            treeItem.getChildren().add(t);
        }
        treeView.getSelectionModel().clearSelection();
        treeView.getSelectionModel().select(t);
*/
    }


    public void ungroupNodes(final ObservableList<TreeItem<AOElement>> children, final TreeItem<AOElement> parent) {
      /*  TreeItem<Element> selectedItem =  treeView.getSelectionModel().getSelectedItem();
        parent.getChildren().remove(selectedItem);
        Node parentNode = getParentNode(r.getWorld().geometries,(Node)selectedItem.getValue());
        if(parentNode == null)r.getWorld().geometries.remove(selectedItem.getValue());
        else  parentNode.geos.remove(selectedItem.getValue());
        for (TreeItem<Element> item : children) {
            if(parentNode == null)r.getWorld().geometries.add((Geometry) item.getValue());
            else parentNode.geos.add((Geometry) item.getValue());
            TreeItem<Element> t = new TreeItem(item.getValue());
            if (!item.getChildren().isEmpty()) t.getChildren().addAll(item.getChildren());
            parent.getChildren().add(t);
        }
        */
    }
}
