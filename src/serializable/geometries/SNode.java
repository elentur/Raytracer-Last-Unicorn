package serializable.geometries;

import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import serializable.SElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 05.02.16.
 */
public class SNode extends SGeometry {


    private final double translationX;
    private final double translationY;
    private final double translationZ;
    private final double scalingX;
    private final double scalingY;
    private final double scalingZ;
    private final double rotationX;
    private final double rotationY;
    private final double rotationZ;

    private final List<SElement> sGeos;

    public SNode(final double translationX, final double translationY, final double translationZ,
                 final double scalingX, final double scalingY, final double scalingZ,
                 final double rotationX, final double rotationY, final double rotationZ,
                 final List<SElement> sGeos, final boolean reciveShadows, final boolean castShadows,
                 final boolean visibility, final boolean flipNormal, final String name) {
        super(null, reciveShadows, castShadows, visibility, flipNormal, name);
        this.translationX = translationX;
        this.translationY = translationY;
        this.translationZ = translationZ;
        this.scalingX = scalingX;
        this.scalingY = scalingY;
        this.scalingZ = scalingZ;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.sGeos = sGeos;
    }


    @Override
    public ONode generate() {

        ONode geo = new ONode(
                name,
                sGoes2aGeos());

        geo.translationx.set(translationX);
        geo.translationy.set(translationY);
        geo.translationz.set(translationZ);

        geo.scalingx.set(scalingX);
        geo.scalingy.set(scalingY);
        geo.scalingz.set(scalingZ);

        geo.rotationx.set(rotationX);
        geo.rotationy.set(rotationY);
        geo.rotationz.set(rotationZ);

        geo.material.set(null);
        geo.reciveShadows.set(reciveShadows);
        geo.castShadows.set(castShadows);
        geo.visibility.set(visibility);
        geo.flipNormal.set(flipNormal);

        return geo;
    }

    private List<AOGeometry> sGoes2aGeos() {
        List<AOGeometry> oGeos = new ArrayList<>();

        for (SElement sGeo : sGeos) {
            if (sGeo instanceof SNode) {
                oGeos.add(((SNode) sGeo).generate());
            } else {
                oGeos.add((AOGeometry) sGeo.generate());
            }
        }

        return oGeos;
    }
}
