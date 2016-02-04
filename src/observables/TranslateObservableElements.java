package observables;

import camera.Camera;
import geometries.Geometry;
import javafx.collections.ObservableList;
import light.Light;
import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
import observables.geometries.ONode;
import observables.lights.AOLight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class TranslateObservableElements {

    private static ObservableList<AOLight> lights;
    private static AOCamera camera;
    private static ObservableList<AOGeometry> geos;

    public void setLights(ObservableList<AOLight> lights) {
        this.lights = lights;
    }

    public void setCamera(AOCamera camera) {
        this.camera = camera;
    }

    public void setGeos(ObservableList<AOGeometry> geos) {
        this.geos = geos;
    }

    public static List<Light> translateLights(){

        List<Light> ls = new ArrayList<>();

        for(AOLight l : lights){
            ls.add(l.generate());
        }

        return ls;
    }

    public static Camera translateCamera(){
        return camera.generate();
    }

    public static List<Geometry> translateGeometries(){

        List<Geometry> gs = new ArrayList<>();

        for(AOGeometry g : geos){
            gs.add(g.generate());
        }

        return gs;

    }

}
