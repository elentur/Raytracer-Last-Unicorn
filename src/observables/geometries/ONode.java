package observables.geometries;

import geometries.Geometry;
import geometries.Node;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import matVect.Point3;
import observables.materials.DefaultMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                nodeFinder(oGeos),
                reciveShadows.get(),
                castShadows.get(),
                visibility.get(),
                flipNormal.get()
        );
    }

    private List<Geometry> nodeFinder(List<AOGeometry> obGeos){

        List<Geometry> list = new ArrayList<>();

        for(AOGeometry oGeo : obGeos){
            
            if(oGeo instanceof ONode) {

                if(((ONode) oGeo).oGeos.isEmpty()) throw new IllegalArgumentException("The oGeos cannot be null!");

                return new ArrayList<Geometry>(
                    Arrays.asList(
                        new Node(
                            new Point3(((ONode) oGeo).rotationx.get(),((ONode) oGeo).translationy.get(),((ONode) oGeo).translationz.get()),
                            new Point3(((ONode) oGeo).scalingx.get(), ((ONode) oGeo).scalingy.get(), ((ONode) oGeo).scalingz.get()),
                            new Point3(((ONode) oGeo).rotationx.get(), ((ONode) oGeo).rotationy.get(), ((ONode) oGeo).rotationz.get()),
                            nodeFinder( ((ONode) oGeo).oGeos),
                                ((ONode) oGeo).reciveShadows.get(),
                                ((ONode) oGeo).castShadows.get(),
                                ((ONode) oGeo).visibility.get(),
                                ((ONode) oGeo).flipNormal.get()
                        )
                    )
                );
            }

            list.add(oGeo.generate());
            
        }

        return list;
    }
}
