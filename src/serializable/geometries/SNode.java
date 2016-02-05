package serializable.geometries;

import geometries.Geometry;
import geometries.Node;
import matVect.Point3;
import observables.AOElement;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import serializable.SElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by roberto on 05.02.16.
 */
public class SNode extends Node implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    private final  List<SElement> sGeos;

    public SNode(Point3 translation, Point3 scaling, Point3 rotation, List<SElement> sGeos, boolean reciveShadows, boolean castShadows, boolean visibility, boolean flipNormal, String name) {
        super(translation, scaling, rotation, new ArrayList<Geometry>(), reciveShadows, castShadows, visibility, flipNormal);
        this.name = name;
        this.sGeos = sGeos;
    }

    @Override
    public ONode generate(){

        ONode geo = new ONode(
                name,
                sGoes2aGeos());

        geo.translationx.set(translation.x);
        geo.translationy.set(translation.y);
        geo.translationz.set(translation.z);

        geo.scalingx.set(scaling.x);
        geo.scalingy.set(scaling.y);
        geo.scalingz.set(scaling.z);

        geo.rotationx.set(rotation.x);
        geo.rotationy.set(rotation.y);
        geo.rotationz.set(rotation.z);

        geo.material.set(null);
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }

    private List<AOGeometry> sGoes2aGeos(List<SElement> sGeos){
        List<AOGeometry> oGeos = new ArrayList<>();

        for(SElement sGeo : sGeos){
            if(sGeo instanceof SNode){
                oGeos.add(sGoes2aGeos(((SNode) sGeo).sGeos));
            }else {
                oGeos.add(sGeo.serialize());
            }
        }

        return oGeos;
    }
}
