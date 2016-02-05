package serializable.cameras;

import camera.OrthographicCamera;
import matVect.Point3;
import matVect.Vector3;
import observables.cameras.OOrthographicCamera;
import observables.cameras.OPerspectiveCamera;
import sampling.SamplingPattern;
import serializable.SElement;

/**
 * Created by roberto on 05.02.16.
 */
public class SOrthographicCamera extends OrthographicCamera implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SOrthographicCamera(Point3 e, Vector3 g, Vector3 t, double s, SamplingPattern samplingPattern, String name) {
        super(e, g, t, s, samplingPattern);
        this.name = name;
    }

    @Override
    public OOrthographicCamera generate() {
        OOrthographicCamera camera = new OOrthographicCamera();
        camera.name.set(name);
        camera.ex.set(e.x);
        camera.ey.set(e.y);
        camera.ez.set(e.z);

        camera.gx.set(g.x);
        camera.gy.set(g.y);
        camera.gz.set(g.z);

        camera.tx.set(t.x);
        camera.ty.set(t.y);
        camera.tz.set(t.z);

        camera.patternSubdiv.set(samplingPattern.subdiv);

        camera.s.set(s);

        return camera;
    }
}
