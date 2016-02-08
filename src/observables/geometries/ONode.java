package observables.geometries;

import geometries.Node;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import matVect.Transform;
import serializable.geometries.SNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by
 * Robert Dziuba  on 02/02/16.
 * <p>
 * Creates a new ONode
 */
public class ONode extends AOGeometry {
    /**
     * represents the translation x value
     */
    public final DoubleProperty translationx = new SimpleDoubleProperty(0.0);
    /**
     * represents the translation y value
     */
    public final DoubleProperty translationy = new SimpleDoubleProperty(0.0);
    /**
     * represents the translation z value
     */
    public final DoubleProperty translationz = new SimpleDoubleProperty(0.0);

    /**
     * represents the scaling x value
     */
    public final DoubleProperty scalingx = new SimpleDoubleProperty(1.0);
    /**
     * represents the scaling y value
     */
    public final DoubleProperty scalingy = new SimpleDoubleProperty(1.0);
    /**
     * represents the scaling z value
     */
    public final DoubleProperty scalingz = new SimpleDoubleProperty(1.0);

    /**
     * represents the rotation x value
     */
    public final DoubleProperty rotationx = new SimpleDoubleProperty(0.0);
    /**
     * represents the rotation y value
     */
    public final DoubleProperty rotationy = new SimpleDoubleProperty(0.0);
    /**
     * represents the rotation z value
     */
    public final DoubleProperty rotationz = new SimpleDoubleProperty(0.0);
    /**
     * represents a list of subGeometries
     */
    public final ObservableList<AOGeometry> oGeos = FXCollections.observableArrayList();


    /**
     * @param name  name of the node
     * @param oGeos list of subGeometries
     */
    public ONode(String name, List<AOGeometry> oGeos) {
        this.name.set(name);
        this.oGeos.setAll(oGeos);
        if (!this.oGeos.isEmpty() && !(this.oGeos.get(0) instanceof ONode)) {
            this.castShadows.addListener(a -> syncAttributes());
            this.receiveShadows.addListener(a -> syncAttributes());
            this.visibility.addListener(a -> syncAttributes());
            this.flipNormal.addListener(a -> syncAttributes());
        }

    }

    /**
     * gives the values to their sub Geometries if they are not ONodes
     */
    private void syncAttributes() {
        for (AOGeometry g : this.oGeos) {
            g.castShadows.set(this.castShadows.get());
            g.receiveShadows.set(this.receiveShadows.get());
            g.visibility.set(this.visibility.get());
            g.flipNormal.set(this.flipNormal.get());
        }
    }

    /**
     * Creates a Copy of this Object
     */
    public ONode getInstance() {
        SNode s = this.serialize();
        return s.generate();
    }

    @Override
    public Node generate() {
        return new Node(
                new Transform().translate(
                        translationx.get(),
                        translationy.get(),
                        translationz.get()).
                        scale(
                                scalingx.get(),
                                scalingy.get(),
                                scalingz.get()).
                        rotateX(rotationx.get() * Math.PI / 180).
                        rotateY(rotationy.get() * Math.PI / 180).
                        rotateZ(rotationz.get() * Math.PI / 180),
                oGeos.stream().map(AOGeometry::generate).collect(Collectors.toList()),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    @Override
    public SNode serialize() {
        return new SNode(
                translationx.get(), translationy.get(), translationz.get(),
                scalingx.get(), scalingy.get(), scalingz.get(),
                rotationx.get(), rotationy.get(), rotationz.get(),
                oGeos.stream().map(AOGeometry::serialize).collect(Collectors.toList()),
                receiveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get(),
                name.get()
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
