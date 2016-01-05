package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import raytracer.Raytracer;
import utils.Element;

/**
 * Created by roberto on 05.01.16.
 */
abstract class AController  implements Initializable {
    Raytracer raytracer = new Raytracer(true);
    ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();
}
