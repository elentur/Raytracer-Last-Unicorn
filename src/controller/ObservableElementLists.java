package controller;

import camera.Camera;
import geometries.Geometry;
import geometries.Node;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import light.Light;
import matVect.Point3;
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
    private TreeView<Element> treeView;
    private TreeItem<Element> nodeTree;
    private TreeItem<Element> lightTree;
    private TreeItem<Element> cameraTree;


    public static ObservableElementLists getInstance() {
        return ourInstance;
    }

    public void setTreeview(TreeView<Element> t) {
        treeView = t;
        nodeTree = t.getRoot().getChildren().get(0);
        lightTree = t.getRoot().getChildren().get(1);
        cameraTree = t.getRoot().getChildren().get(2);
    }

    private ObservableElementLists() {
    }

    public void addElement(Element e) {
        treeView.getSelectionModel().clearSelection();
        if (e instanceof Camera) {
            addCamera((Camera) e);
        } else if (e instanceof Light) {
            addLight((Light) e);
        } else if (e instanceof Node) {
            addNode((Node) e);
        } else {
            throw new IllegalArgumentException("Wrong typ of Object. Must be Camera, Light or Node");
        }
    }

    public void updateElement(Element oldElement, Element newElement) {
        if (newElement instanceof Camera) {
            updateCamera((Camera) newElement);
        } else if (newElement instanceof Light) {
            updateLight((Light) oldElement, (Light) newElement);
        } else if (newElement instanceof Node) {
            updateNode((Node) oldElement, (Node) newElement);
        } else {
            throw new IllegalArgumentException("Wrong typ of Object. Must be Camera, Light or Node");
        }
        //AController.selectedTreeItem.get().setValue(newElement);
    }

    public void removeElement(Element e) {
        if (e instanceof Camera) {
            removeCamera();
        } else if (e instanceof Light) {
            removeLight((Light) e);
        } else if (e instanceof Node) {
            removeNode((Node) e);
        } else {
            throw new IllegalArgumentException("Wrong typ of Object. Must be Camera, Light or Node");
        }
        treeView.getSelectionModel().clearSelection();
    }

    private void addCamera(Camera c) {
        if (r.getCamera() == null) {
           // camera.setValue(c);
            r.setCamera(c);
            TreeItem<Element> treeItem = new TreeItem<Element>(c);
            cameraTree.getChildren().add(treeItem);
            treeView.getSelectionModel().select(treeItem);
        }
    }

    private void updateCamera(Camera c) {
       // camera.setValue(c);
        r.setCamera(c);
        treeView.getSelectionModel().getSelectedItem().setValue(c);
    }

    private void removeCamera() {
        //camera.setValue(null);
        r.setCamera(null);
        cameraTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
    }

    private void addLight(Light l) {
        //lights.add(l);
        r.getWorld().lights.add(l);
        TreeItem<Element> treeItem = new TreeItem<Element>(l);
        lightTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);

    }
    private void updateLight(Light oldLight, Light newLight) {
       // lights.set(lights.indexOf(oldLight), newLight);
        r.getWorld().lights.set(r.getWorld().lights.indexOf(oldLight), newLight);
        treeView.getSelectionModel().getSelectedItem().setValue(newLight);
    }

    private void removeLight(Light l) {
       // lights.remove(l);
        r.getWorld().lights.remove(l);
        lightTree.getChildren().remove(treeView.getSelectionModel().getSelectedItem());
        treeView.getSelectionModel().clearSelection();
    }

    private void addNode(Node n) {
       // nodes.add(n);
        r.getWorld().geometries.add(n);
        TreeItem<Element> treeItem = new TreeItem<Element>(n);
        nodeTree.getChildren().add(treeItem);
        treeView.getSelectionModel().select(treeItem);
    }


    private void updateNode(Node oldNode, Node newNode) {
        if (r.getWorld().geometries.contains(oldNode)) {
       // nodes.set(nodes.indexOf(oldNode), newNode);
            r.getWorld().geometries.set(r.getWorld().geometries.indexOf(oldNode), newNode);
        }else {
            Node n = getParentNode(r.getWorld().geometries,oldNode);
            n.geos.set(n.geos.indexOf(oldNode), newNode);
        }
        treeView.getSelectionModel().getSelectedItem().setValue(newNode);

    }

    private void removeNode(Node n) {
        if (r.getWorld().geometries.contains(n)) {
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
    }

    private Node getParentNode(List<Geometry> nodes, Node n) {
        Node node = null;
        for (Geometry p : nodes) {
            if(p instanceof Node ) {
                if (((Node) p).geos.contains(n)) return (Node) p;
                node =  getParentNode(((Node) p).geos, n);
            }
        }
        return node;
    }

    public void groupNodes(List<Geometry> nodes){
        Node parent = getParentNode(r.getWorld().geometries,(Node)nodes.get(0));
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

    }


    public void ungroupNodes(final ObservableList<TreeItem<Element>> children, final TreeItem<Element> parent) {
        TreeItem<Element> selectedItem =  treeView.getSelectionModel().getSelectedItem();
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
    }
}
