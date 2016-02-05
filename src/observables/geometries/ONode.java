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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ONode)) return false;

        ONode oNode = (ONode) o;

        if (!translationx.equals(oNode.translationx)) return false;
        if (!translationy.equals(oNode.translationy)) return false;
        if (!translationz.equals(oNode.translationz)) return false;
        if (!scalingx.equals(oNode.scalingx)) return false;
        if (!scalingy.equals(oNode.scalingy)) return false;
        if (!scalingz.equals(oNode.scalingz)) return false;
        if (!rotationx.equals(oNode.rotationx)) return false;
        if (!rotationy.equals(oNode.rotationy)) return false;
        if (!rotationz.equals(oNode.rotationz)) return false;
        return !(oGeos != null ? !oGeos.equals(oNode.oGeos) : oNode.oGeos != null);

    }

    @Override
    public int hashCode() {
        int result = translationx.hashCode();
        result = 31 * result + translationy.hashCode();
        result = 31 * result + translationz.hashCode();
        result = 31 * result + scalingx.hashCode();
        result = 31 * result + scalingy.hashCode();
        result = 31 * result + scalingz.hashCode();
        result = 31 * result + rotationx.hashCode();
        result = 31 * result + rotationy.hashCode();
        result = 31 * result + rotationz.hashCode();
        result = 31 * result + (oGeos != null ? oGeos.hashCode() : 0);
        return result;
    }


}
