package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import observables.AOElement;
import observables.materials.AOMaterial;
import observables.textures.AOTexture;
import raytracer.Raytracer;

/**
 * Created by roberto on 05.01.16.
 */
public abstract class AController  implements Initializable {
    public final static Raytracer raytracer = new Raytracer(true);
   // public final static ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();
    public final static ObjectProperty<TreeItem<AOElement>> selectedTreeItem = new SimpleObjectProperty<>();
    protected ObservableElementLists elementLists = ObservableElementLists.getInstance();
    //TODO material löschen und schauen ob es über die liste geht
    public final static ObjectProperty<AOMaterial> material= new SimpleObjectProperty<>();
    protected final static ObservableList<AOMaterial> materialList = FXCollections.observableArrayList();
    protected final static ObservableList<AOTexture> textureList = FXCollections.observableArrayList();
    protected static TabPane masterTabPane;
    protected final TreeItem<AOElement> rootItem = new TreeItem<>(new AOElement("Elements"));
}
