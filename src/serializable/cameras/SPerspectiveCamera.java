package serializable.cameras;

import camera.DOFCamera;
import camera.PerspectiveCamera;
import matVect.Point3;
import matVect.Vector3;
import observables.cameras.OPerspectiveCamera;
import sampling.SamplingPattern;
import serializable.SElement;

/**
 * Created by roberto on 05.02.16.
 */
public class SPerspectiveCamera extends PerspectiveCamera implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SPerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle, SamplingPattern samplingPattern, String name) {
        super(e, g, t, angle*Math.PI/180, samplingPattern);
        this.name = name;
    }

    @Override
    public OPerspectiveCamera generate() {
        OPerspectiveCamera camera = new OPerspectiveCamera();
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

        camera.angle.set(angle*180/Math.PI);

        return camera;
    }
}
