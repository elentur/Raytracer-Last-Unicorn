package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import observables.AOElement;
import observables.materials.AOMaterial;
import observables.materials.DefaultMaterial;
import observables.textures.AOTexture;
import raytracer.Raytracer;

/**
 * Created by roberto on 05.01.16.
 * <p>
 * The Abstract ControllerClass to access all relevant Controller to the given attributes
 */
public abstract class AController implements Initializable {
    /**
     * The Raytracer that is used for regular renderprocesses
     */
    public final static Raytracer raytracer = new Raytracer(true);
    /**
     * The TreeItem that is actually selected in the TreeView
     */
    final static ObjectProperty<TreeItem<AOElement>> selectedTreeItem = new SimpleObjectProperty<>();
    /**
     * A Collection of Lists and tools to add, remove, group, ungroup or duplicate Elements in the TreeView
     */
    final ObservableElementLists elementLists = ObservableElementLists.getInstance();

    /**
     * The Material of the actually selected ONode if available
     */
    public final static ObjectProperty<AOMaterial> material = new SimpleObjectProperty<>();
    /**
     * The observable List of all Materials that are new created or in use and a function to create new Materials
     */
    public final static ObservableList<AOMaterial> materialList = FXCollections.observableArrayList(
            DefaultMaterial.getSingleColorMaterial(),
            DefaultMaterial.getLambert(),
            DefaultMaterial.getOrenNayar(),
            DefaultMaterial.getPhong(),
            DefaultMaterial.getReflectiveMaterial(),
            DefaultMaterial.getTransparentMaterial(),
            DefaultMaterial.getDefaultLambert()
    );
    /**
     * The observable List of all Textures that are new created or in use
     */
    public final static ObservableList<AOTexture> textureList = FXCollections.observableArrayList();
    /**
     * The TabPane
     */
    static TabPane masterTabPane;
    /**
     * The rootItem of the TreeView
     */
    final TreeItem<AOElement> rootItem = new TreeItem<>(new AOElement("Elements"));
}
