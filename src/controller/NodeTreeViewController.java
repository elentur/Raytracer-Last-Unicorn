package controller;

import camera.Camera;
import camera.DOFCamera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import geometries.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import material.DefaultMaterial;
import sampling.SamplingPattern;
import utils.Color;
import utils.Element;

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
    private ComboBox<Element> cmbNewElement;

    @FXML
    private TreeView<Element> elementsTreeView;
    private static TreeView<Element> elementTreeViewStatic;
    private static NodeTreeViewController controllerStatic;

    @FXML
    private TreeItem<Element> nodesRootTree;

    @FXML
    private TreeItem<Element> lightsRootTree;

    @FXML
    private TreeItem<Element> camerasRootTree;

    private final static ObjectProperty<Element> element = new SimpleObjectProperty<>();

    public NodeTreeViewController() {
        this.controllerStatic = this;
    }

    public void initialize(URL url, ResourceBundle resource) {
        initializeComobox();
        initializeTreeView();

        element.addListener(new ChangeListener<Element>() {
            @Override
            public void changed(final ObservableValue<? extends Element> observable, final Element oldValue, final Element newValue) {
                if (newValue != null) {
                    addNewElement();
                    element.setValue(null);

                }
            }
        });
    }

    private void initializeTreeView() {

        // nodesRootTree
        elementTreeViewStatic = elementsTreeView;
        elementsTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nodesRootTree = new TreeItem<>();
        Element nodes = new Element() {
        };
        nodes.name = "Nodes";
        nodesRootTree.setValue(nodes);

        nodesRootTree.getChildren().addListener((ListChangeListener<TreeItem<Element>>) c -> {

            List<Geometry> geos = raytracer.getWorld().geometries;

            geos.clear();

            for (TreeItem<Element> item : nodesRootTree.getChildren()) {
                geos.add((Geometry) item.getValue());
            }
        });

        // lightsRootTree

        lightsRootTree = new TreeItem<>();
        Element light = new Element() {
        };
        light.name = "Lights";
        lightsRootTree.setValue(light);

        lightsRootTree.getChildren().addListener((ListChangeListener<TreeItem<Element>>) c -> {

            List<Light> lights = raytracer.getWorld().lights;

            lights.clear();

            for (TreeItem<Element> item : lightsRootTree.getChildren()) {
                lights.add((Light) item.getValue());
            }
        });

        // camerasRootTree

        camerasRootTree = new TreeItem<>();
        Element camera = new Element() {
        };
        camera.name = "Cameras";
        camerasRootTree.setValue(camera);

        camerasRootTree.getChildren().addListener((ListChangeListener<TreeItem<Element>>) c -> {
            if (camerasRootTree.getChildren().size() > 1) camerasRootTree.getChildren().remove(1);
            else if (camerasRootTree.getChildren().size() == 1)
                raytracer.setCamera((Camera) camerasRootTree.getChildren().get(0).getValue());
        });

        // rootTreeItem

        TreeItem<Element> root = new TreeItem<>();
        root.setExpanded(true);
        Element element = new Element() {
        };
        element.name = "Elements";
        root.setValue(element);


        root.getChildren().addAll(nodesRootTree, lightsRootTree, camerasRootTree);

        elementsTreeView.setRoot(root);

        // TODO in eigene Klasse auslagern
        elementsTreeView.setCellFactory(new Callback<TreeView<Element>, TreeCell<Element>>() {
            @Override
            public TreeCell<Element> call(TreeView<Element> p) {
                return new TreeCell<Element>() {
                    @Override
                    protected void updateItem(Element item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null || !empty) {
                            setText(item.name);
                            if (item instanceof Geometry) {
                                setGraphic(new ImageView(new Image("file:icons/mesh.png")));

                            } else if (item instanceof Light) {
                                setGraphic(new ImageView(new Image("file:icons/light.png")));

                            } else if (item instanceof Camera) {
                                setGraphic(new ImageView(new Image("file:icons/camera.png")));

                            } else {
                                setGraphic(null);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // wenn element ausgewählt
        elementsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {
                if (newValue.getValue() instanceof Geometry || newValue.getValue() instanceof Light || newValue.getValue() instanceof Camera) {
                    selectedElement.set(newValue.getValue());
                    return;
                }
            }
            selectedElement.set(null);
        });

    }

    private void initializeComobox() {

        // TODO in eigene Klasse auslagern
        cmbNewElement.setCellFactory(new Callback<ListView<Element>, ListCell<Element>>() {
            @Override
            public ListCell<Element> call(ListView<Element> c) {

                return new ListCell<Element>() {
                    @Override
                    protected void updateItem(Element item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.name);
                        }
                    }
                };
            }
        });

        cmbNewElement.setButtonCell(new ListCell<Element>() {
            @Override
            protected void updateItem(Element item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.name);
                }
            }
        });

        ObservableList<Element> lstNewElement = cmbNewElement.getItems();

        lstNewElement.add(new Plane(DefaultMaterial.MATERIAL, true, true, true, false));
        lstNewElement.add(new Sphere(DefaultMaterial.MATERIAL, true, true, true, false));
        lstNewElement.add(new AxisAlignedBox(DefaultMaterial.MATERIAL, true, true, true, false));
        lstNewElement.add(new Triangle(DefaultMaterial.MATERIAL, true, true, true, false));

        lstNewElement.add(new DirectionalLight(new Color(1, 1, 1), new Vector3(1, 1, 1), true, 500));
        lstNewElement.add(new PointLight(new Color(1, 1, 1), new Point3(0, 0, 0), true, 500));
        lstNewElement.add(new SpotLight(new Color(1, 1, 1), new Point3(0, 0, 0), new Vector3(1, 1, 1), Math.PI / 14, true, 500));

        lstNewElement.add(new OrthographicCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 3, new SamplingPattern(1)));
        lstNewElement.add(new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4, new SamplingPattern(1)));
        lstNewElement.add(new DOFCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4, 1, 1, new SamplingPattern(1)));

    }

    public void handleGroupAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItems().size() > 0) {
            ObservableList<TreeItem<Element>> selectedItems = elementsTreeView.getSelectionModel().getSelectedItems();
            ObservableList<TreeItem<Element>> newItems = FXCollections.observableArrayList();
            List<Geometry> geos = new ArrayList<>();
            for (TreeItem<Element> item : selectedItems) {
                if (!nodesRootTree.equals(item.getParent())) return;
                geos.add((Geometry) item.getValue());
                if (item.getChildren().isEmpty()) newItems.add(new TreeItem<>(item.getValue()));
                else {
                    TreeItem<Element> t = new TreeItem<>(item.getValue());
                    t.getChildren().addAll(item.getChildren());
                    newItems.add(t);
                }
            }
            raytracer.getWorld().geometries.removeAll(geos);
            Node node = new Node(new Point3(0,0,0),new Point3(1,1,1),new Point3(0,0,0), geos, true, true, true, false);
            raytracer.getWorld().geometries.add(node);
            node.name = "group";
            TreeItem<Element> t = new TreeItem<>(node);
            t.getChildren().addAll(newItems);
            nodesRootTree.getChildren().add(t);
            nodesRootTree.getChildren().removeAll(selectedItems);
            elementsTreeView.getSelectionModel().clearSelection();
            elementsTreeView.getSelectionModel().select(t);
        }
    }

    public void handleUngroupAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {
            TreeItem<Element> selectedItem = elementsTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem.getChildren().size() > 0 && (selectedItem.getChildren().get(0).getValue() instanceof Node)) {
                ObservableList<TreeItem<Element>> selectedItems = selectedItem.getChildren();
                TreeItem<Element> parent = selectedItem.getParent();
                parent.getChildren().remove(selectedItem);
                raytracer.getWorld().geometries.remove(selectedItem.getValue());
                for (TreeItem<Element> item : selectedItems) {
                    raytracer.getWorld().geometries.add((Geometry) item.getValue());
                    TreeItem<Element> t = new TreeItem(item.getValue());
                    if (!item.getChildren().isEmpty()) t.getChildren().addAll(item.getChildren());
                    parent.getChildren().add(t);
                }

            }
        }
    }

    public void handleDeleteAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Element> selectedItem = elementsTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem.getValue() instanceof Geometry || selectedItem.getValue() instanceof Light || selectedItem.getValue() instanceof Camera) {
                if (selectedItem.getValue() instanceof Geometry) {
                    Node n = (Node) selectedItem.getValue();
                    raytracer.getWorld().geometries.remove(n);
                }
                if (selectedItem.getValue() instanceof Light) {
                    Light l = (Light) selectedItem.getValue();
                    raytracer.getWorld().lights.remove(l);
                }
                if (selectedItem.getValue() instanceof Camera) {
                    raytracer.setCamera(null);
                }
                selectedItem.getParent().getChildren().remove(selectedItem);
            }
        }
    }

    public void handleNewElementAction() {

        if (cmbNewElement.getSelectionModel().getSelectedItem() != null && cmbNewElement.getSelectionModel().getSelectedItem() instanceof Element) {

            newElement(cmbNewElement.getSelectionModel().getSelectedItem());


        }
    }

    private void addNewElement() {
        TreeItem<Element> t=null;
        if (element.getValue() instanceof Light) {
           t =new TreeItem<>(((Light) element.getValue()).deepCopy());
            lightsRootTree.getChildren().add(t);
        } else if (element.getValue() instanceof Camera) {
            if (camerasRootTree.getChildren().isEmpty())
                t=new TreeItem<>(((Camera) element.getValue()).deepCopy());
                camerasRootTree.getChildren().add(t);
        } else if(element.getValue() instanceof Node){
            Node n = ((Node)element.getValue());
            if(n.geos.get(0) instanceof Geometry) {
                t = new TreeItem<>(((Node) element.getValue()).deepCopy());
                nodesRootTree.getChildren().add(t);
            }
        }else if (element.getValue() instanceof Geometry) {
            Node node = new Node(new Point3(5,3,2),new Point3(1,3,1),new Point3(1,1,1), ((Geometry) element.getValue()).deepCopy(), true, true, true, false);
            node.name = element.getValue().name;
            t=new TreeItem<>(node);
            nodesRootTree.getChildren().add(t);
        }
        if(t!=null){
            elementTreeViewStatic.getSelectionModel().clearSelection();
            elementTreeViewStatic.getSelectionModel().select(t);
        }
    }

    public static void newElement(Element e) {
        element.setValue(e);
    }

    public static void updateElement(Element e) {
        controllerStatic.handleDeleteAction();
        element.setValue(e);


    }

    public static void refresh() {
        elementTreeViewStatic.refresh();
    }
}
