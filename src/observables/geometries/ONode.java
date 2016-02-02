package observables.geometries;

import geometries.Geometry;
import geometries.Node;
import matVect.Point3;
import observables.materials.AOMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by
 * Robert Dziuba  on 02/02/16.
 */
public class ONode extends AOGeometry {

    public double translationx;
    public double translationy;
    public double translationz;

    public double scalingx;
    public double scalingy;
    public double scalingz;

    public double rotationx;
    public double rotationy;
    public double rotationz;
    public List<AOGeometry> oGeos;

    public ONode(String name, AOMaterial material, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, double[] scaling, double[] translation, double[] rotation, List<AOGeometry> oGeos) {
        super(name, material, reciveShadows, castShadows, visibility, flipNormal);

        this.scalingx = scaling[0];
        this.scalingy = scaling[1];
        this.scalingz = scaling[2];

        this.translationx = translation[0];
        this.translationy = translation[1];
        this.translationz = translation[2];

        this.rotationx = rotation[0];
        this.rotationy = rotation[1];
        this.rotationz = rotation[2];

        this.oGeos = oGeos;
    }

    @Override
    public Node generate() {

        return new Node(
                new Point3(rotationx,translationy,translationz),
                new Point3(scalingx, scalingy, scalingz),
                new Point3(rotationx, rotationy, rotationz),
                nodeFinder(oGeos),
                reciveShadows,
                castShadows,
                visibility,
                flipNormal
        );
    }

    private List<Geometry> nodeFinder(List<AOGeometry> obGeos){

        List<Geometry> list = new ArrayList<>();

        for(AOGeometry oGeo : obGeos){
            
            if(oGeo instanceof ONode) {

                return new ArrayList<Geometry>(
                    Arrays.asList(
                        new Node(
                            new Point3(((ONode) oGeo).rotationx,((ONode) oGeo).translationy,((ONode) oGeo).translationz),
                            new Point3(((ONode) oGeo).scalingx, ((ONode) oGeo).scalingy, ((ONode) oGeo).scalingz),
                            new Point3(((ONode) oGeo).rotationx, ((ONode) oGeo).rotationy, ((ONode) oGeo).rotationz),
                            nodeFinder( ((ONode) oGeo).oGeos),
                                ((ONode) oGeo).reciveShadows,
                                ((ONode) oGeo).castShadows,
                                ((ONode) oGeo).visibility,
                                ((ONode) oGeo).flipNormal
                        )
                    )
                );
            }

            list.add(oGeo.generate());
            
        }

        return list;
    }
}
