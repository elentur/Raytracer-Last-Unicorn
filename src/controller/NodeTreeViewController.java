package controller;

import camera.Camera;
import camera.DOFCamera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import geometries.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import material.DefaultMaterial;
import sampling.DOFPattern;
import sampling.LightShadowPattern;
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

        elementsTreeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nodesRootTree = new TreeItem<>();
        Element nodes = new Element() {
            @Override
            public Element deepCopy() {
                return null;
            }
        };
        nodes.name = "Nodes";
        nodesRootTree.setValue(nodes);


        lightsRootTree = new TreeItem<>();
        Element light = new Element() {
            @Override
            public Element deepCopy() {
                return null;
            }
        };
        light.name = "Lights";
        lightsRootTree.setValue(light);


        camerasRootTree = new TreeItem<>();
        Element camera = new Element() {
            @Override
            public Element deepCopy() {
                return null;
            }
        };
        camera.name = "Cameras";
        camerasRootTree.setValue(camera);



        TreeItem<Element> root = new TreeItem<>();
        root.setExpanded(true);
        Element element = new Element() {
            @Override
            public Element deepCopy() {
                return null;
            }
        };
        element.name = "Elements";
        root.setValue(element);


        root.getChildren().addAll(nodesRootTree, lightsRootTree, camerasRootTree);

        elementsTreeView.setRoot(root);
        elementLists.setTreeview(elementsTreeView);

        // TODO in eigene Klasse auslagern
        elementsTreeView.setCellFactory(new ElementTreeCellFactory());

        // wenn element ausgewählt
        elementsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {

                if (newValue != null && newValue.getValue() instanceof Geometry || newValue.getValue() instanceof Light || newValue.getValue() instanceof Camera) {

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

        lstNewElement.add(new DirectionalLight(new Color(1, 1, 1), new Vector3(1, 1, 1), true, 500,new LightShadowPattern(0,1)));
        lstNewElement.add(new PointLight(new Color(1, 1, 1), new Point3(5, 5, 5), true, 500,new LightShadowPattern(0,1)));
        lstNewElement.add(new SpotLight(new Color(1, 1, 1), new Point3(0, 0, 0), new Vector3(1, 1, 1), Math.PI / 14, true, 500,new LightShadowPattern(0,1)));

        lstNewElement.add(new OrthographicCamera(new Point3(0, 0, 5), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 3, new SamplingPattern(1)));
        lstNewElement.add(new PerspectiveCamera(new Point3(0, 0, 5), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4, new SamplingPattern(1)));
        lstNewElement.add(new DOFCamera(new Point3(0, 0, 5), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4, new DOFPattern(3,8), 5, new SamplingPattern(1)));

    }

    public void handleGroupAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItems().size() > 0) {
            ObservableList<TreeItem<Element>> selectedItems = elementsTreeView.getSelectionModel().getSelectedItems();
            List<Geometry> nodes = new ArrayList<>();
            TreeItem<Element> p =selectedItems.get(0).getParent();
            for(TreeItem<Element> t: selectedItems){
                if(!(t.getValue()instanceof Node) ||!t.getParent().equals(p))return;
                nodes.add((Node)t.getValue());
            }
            elementLists.groupNodes(nodes);
        }
    }

    public void handleUngroupAction() {
        if (elementsTreeView.getSelectionModel().getSelectedItems().size() == 1) {
            TreeItem<Element> selectedItem = elementsTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem.getChildren().size() > 0 && (selectedItem.getChildren().get(0).getValue() instanceof Node)) {
                ObservableList<TreeItem<Element>> selectedItems = selectedItem.getChildren();
                TreeItem<Element> parent = selectedItem.getParent();
                elementLists.ungroupNodes(selectedItems,parent);
            }
        }
    }
    public void handleDeleteAction() {
        elementLists.removeElement(selectedTreeItem.get().getValue());
    }
    @FXML
    private void handleNewElementAction() {
        if (cmbNewElement.getSelectionModel().getSelectedItem() != null && cmbNewElement.getSelectionModel().getSelectedItem() instanceof Element) {
          Element e = cmbNewElement.getSelectionModel().getSelectedItem();
            if(e instanceof Light){
                elementLists.addElement(((Light) e).deepCopy());
            }else  if(e instanceof Camera){
                elementLists.addElement(((Camera) e).deepCopy());
            }else  if(e instanceof Geometry){
                Node node = new Node(new Point3(0,0,0),new Point3(1,1,1),new Point3(0,0,0), ((Geometry) e).deepCopy(), true, true, true, false);
                node.name = e.name;
                elementLists.addElement(node);
            }


        }
    }


}
