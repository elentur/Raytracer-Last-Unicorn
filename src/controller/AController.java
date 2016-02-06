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
 */
public abstract class AController implements Initializable {
    public final static Raytracer raytracer = new Raytracer(true);
    // public final static ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();
    final static ObjectProperty<TreeItem<AOElement>> selectedTreeItem = new SimpleObjectProperty<>();
    final ObservableElementLists elementLists = ObservableElementLists.getInstance();
    public final static ObjectProperty<AOMaterial> material = new SimpleObjectProperty<>();
    public final static ObservableList<AOMaterial> materialList = FXCollections.observableArrayList(
            DefaultMaterial.getSingleColorMaterial(),
            DefaultMaterial.getLambert(),
            DefaultMaterial.getOrenNayar(),
            DefaultMaterial.getPhong(),
            DefaultMaterial.getReflectiveMaterial(),
            DefaultMaterial.getTransparentMaterial(),
            DefaultMaterial.getDefaultLambert()
    );
    public final static ObservableList<AOTexture> textureList = FXCollections.observableArrayList();
    static TabPane masterTabPane;
    final TreeItem<AOElement> rootItem = new TreeItem<>(new AOElement("Elements"));
}
