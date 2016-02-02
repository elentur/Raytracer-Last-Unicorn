package observables.geometries;

import geometries.Geometry;
import geometries.Node;
import matVect.Point3;
import observables.materials.AOMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 02/02/16.
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

        List<Geometry> geos = new ArrayList<>();

        for(AOGeometry oGeo : oGeos){
            geos.add(oGeo.generate());
        }

        return new Node(
            new Point3(translationx,translationy,translationz),
            new Point3(scalingx,scalingy,scalingz),
            new Point3(rotationx,rotationy,rotationz),
            geos,
            reciveShadows,
            castShadows,
            visibility,
            flipNormal
        );
    }
}
