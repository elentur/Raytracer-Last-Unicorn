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
 * Created by roberto on 02/02/16.
 */
public abstract class TranslateObservableElements {

    private ObservableList<AOLight> lights;
    private AOCamera camera;
    private ObservableList<AOGeometry> geos;

    public void setLights(ObservableList<AOLight> lights) {
        this.lights = lights;
    }

    public void setCamera(AOCamera camera) {
        this.camera = camera;
    }


    public void setGeos(ObservableList<AOGeometry> geos) {
        this.geos = geos;
    }

    public List<Light> translateLights(){

        List<Light> ls = new ArrayList<>();

        for(AOLight l : lights){
            ls.add(l.generate());
        }

        return ls;
    }

    public Camera translateCamera(){
        return camera.generate();
    }

    public List<Geometry> translateGeometries(){

        List<Geometry> gs = new ArrayList<>();

        for(AOGeometry g : geos){
            gs.add(g.generate());
        }

        return gs;

    }

    private List<Geometry> geometryFinder(List<AOGeometry> geos){

        List<Geometry> gs = new ArrayList<>();

        for(AOGeometry g : geos){

            if(g instanceof ONode) {

            }else {
                gs.add(g.generate());
            }
        }

        return gs;
    }

}
