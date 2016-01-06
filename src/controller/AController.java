package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import raytracer.Raytracer;
import utils.Element;

/**
 * Created by roberto on 05.01.16.
 */
public abstract class AController  implements Initializable {
    public final static Raytracer raytracer = new Raytracer(true);
    public final static ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();
}
