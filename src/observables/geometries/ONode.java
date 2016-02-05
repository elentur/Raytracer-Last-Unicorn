package observables.geometries;

import geometries.Node;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import matVect.Point3;
import serializable.geometries.SNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by
 * Robert Dziuba  on 02/02/16.
 */
public class ONode extends AOGeometry {

    public DoubleProperty translationx = new SimpleDoubleProperty(0.0);
    public DoubleProperty translationy = new SimpleDoubleProperty(0.0);
    public DoubleProperty translationz = new SimpleDoubleProperty(0.0);

    public DoubleProperty scalingx = new SimpleDoubleProperty(1.0);
    public DoubleProperty scalingy = new SimpleDoubleProperty(1.0);
    public DoubleProperty scalingz = new SimpleDoubleProperty(1.0);

    public DoubleProperty rotationx = new SimpleDoubleProperty(0.0);
    public DoubleProperty rotationy = new SimpleDoubleProperty(0.0);
    public DoubleProperty rotationz = new SimpleDoubleProperty(0.0);
    public ObservableList<AOGeometry> oGeos = FXCollections.observableArrayList();

    public ONode(String name,List<AOGeometry> oGeos) {
        this.name.set(name);
        this.oGeos.setAll(oGeos);
    }

    @Override
    public Node generate() {
        return new Node(
                new Point3(rotationx.get(),translationy.get(),translationz.get()),
                new Point3(scalingx.get(), scalingy.get(), scalingz.get()),
                new Point3(rotationx.get(), rotationy.get(), rotationz.get()),
                oGeos.stream().map(AOGeometry::generate).collect(Collectors.toList()),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public SNode serialize() {
        return new SNode(
                new Point3(rotationx.get(),translationy.get(),translationz.get()),
                new Point3(scalingx.get(), scalingy.get(), scalingz.get()),
                new Point3(rotationx.get(), rotationy.get(), rotationz.get()),
                oGeos.stream().map(AOGeometry::generate).collect(Collectors.toList()),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }
}
