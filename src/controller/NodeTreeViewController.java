package controller;

import camera.Camera;
import camera.DOFCamera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import geometries.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import matVect.Point3;
import matVect.Transform;
import matVect.Vector3;
import material.DefaultMaterial;
import sampling.SamplingPattern;
import utils.Color;
import utils.Element;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by roberto on 05.01.16.
 * @author Robert Dziuba
 */
public class NodeTreeViewController extends AController {

    @FXML
    private ComboBox<Element> cmbNewElement;

    @FXML
    private TreeView<Element> elementsTreeView;

    @FXML
    private TreeItem<Element> nodesRootTree;

    @FXML
    private TreeItem<Element> lightsRootTree;

    @FXML
    private TreeItem<Element> camerasRootTree;



    public void initialize(URL url, ResourceBundle resource) {
        initializeComobox();
        initializeTreeView();
    }

    private void initializeTreeView() {

        // nodesRootTree

        nodesRootTree = new TreeItem<>();
        Element nodes = new Element() {};
        nodes.name="Nodes";
        nodesRootTree.setValue(nodes);

        nodesRootTree.getChildren().addListener((ListChangeListener<TreeItem<Element>>) c -> {

            List<Geometry> geos = raytracer.getWorld().geometries;

            geos.clear();

            for(TreeItem<Element> item : nodesRootTree.getChildren()){
                geos.add((Geometry) item.getValue());
            }
        });

        // lightsRootTree

        lightsRootTree = new TreeItem<>();
        Element light = new Element() {};
        light.name="Lights";
        lightsRootTree.setValue(light);

        lightsRootTree.getChildren().addListener((ListChangeListener<TreeItem<Element>>) c -> {

            List<Light> lights = raytracer.getWorld().lights;

            lights.clear();

            for(TreeItem<Element> item : lightsRootTree.getChildren()){
                lights.add((Light) item.getValue());
            }
        });

        // camerasRootTree

        camerasRootTree = new TreeItem<>();
        Element camera = new Element() {};
        camera.name="Cameras";
        camerasRootTree.setValue(camera);

        camerasRootTree.getChildren().addListener((ListChangeListener<TreeItem<Element>>) c -> {
            for(TreeItem<Element> item : camerasRootTree.getChildren()){
                raytracer.setCamera((Camera) item.getValue());
            }
        });

        // rootTreeItem

        TreeItem<Element> root = new TreeItem<>();
        root.setExpanded(true);
        Element element = new Element() {};
        element.name="Elements";
        root.setValue(element);


        root.getChildren().addAll(nodesRootTree,lightsRootTree,camerasRootTree);

        elementsTreeView.setRoot(root);

        // TODO in eigene Klasse auslagern
        elementsTreeView.setCellFactory(new Callback<TreeView<Element>,TreeCell<Element>>(){
            @Override
            public TreeCell<Element> call(TreeView<Element> p) {
                return new TreeCell<Element>() {
                    @Override
                    protected void updateItem(Element item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            if (item != null) {
                                setText(item.name);
                            }else{
                                setText(null);
                            }
                        }else{
                            setText(null);
                        }
                    }
                };
            }
        });

        // wenn element ausgewählt
        elementsTreeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<Element> selectedItem = (TreeItem<Element>) newValue;
                TreeItem<Element> selectedItem = (TreeItem<Element>) newValue;
                if(selectedItem.getValue() instanceof Geometry || selectedItem.getValue() instanceof Light || selectedItem.getValue() instanceof Camera)
                    selectedElement.set(selectedItem.getValue());
            }

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
                        }else{
                            setText("");
                        }
                    }
                };
            }
        });

        cmbNewElement.setButtonCell(new ListCell<Element>(){
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
    }

    public void handleUngroupAction() {
    }

    public void handleDeleteAction() {
        if(elementsTreeView.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Element> selectedItem = elementsTreeView.getSelectionModel().getSelectedItem();
            if(selectedItem.getValue() instanceof Geometry || selectedItem.getValue() instanceof Light || selectedItem.getValue() instanceof Camera)
                selectedItem.getParent().getChildren().remove(selectedItem);
        }
    }

    public void handleNewElementAction() {

        if(cmbNewElement.getSelectionModel().getSelectedItem() != null){

            Element element = cmbNewElement.getSelectionModel().getSelectedItem();

            if (element instanceof Light) {
                lightsRootTree.getChildren().add(new TreeItem<>(((Light) element).deepCopy()));
            } else if (element instanceof Camera) {
                if(camerasRootTree.getChildren().isEmpty())
                    camerasRootTree.getChildren().add(new TreeItem<>(((Camera) element).deepCopy()));
            } else if (element instanceof Geometry) {
                Node node = new Node(new Transform(),((Geometry) element).deepCopy(),true,true,true,false);
                node.name = element.name;
                nodesRootTree.getChildren().add(new TreeItem<>(node));
            }
        }
    }
}
