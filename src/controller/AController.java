package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import material.Material;
import raytracer.Raytracer;
import texture.Texture;
import utils.Element;

/**
 * Created by roberto on 05.01.16.
 */
public abstract class AController  implements Initializable {
    public final static Raytracer raytracer = new Raytracer(true);
    public final static ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();
    protected ObservableElementLists elementLists = ObservableElementLists.getInstance();
    //TODO material löschen und schauen ob es über die liste geht
    protected final static ObjectProperty<Material> material= new SimpleObjectProperty<>();
    protected final static ObservableList<Material> materialList = FXCollections.observableArrayList();
    protected final static ObservableList<Texture> textureList = FXCollections.observableArrayList();
}
