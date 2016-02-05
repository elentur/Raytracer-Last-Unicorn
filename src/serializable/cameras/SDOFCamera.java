package serializable.cameras;

import camera.DOFCamera;
import matVect.Point3;
import matVect.Vector3;
import observables.cameras.ODOFCamera;
import sampling.DOFPattern;
import sampling.SamplingPattern;
import serializable.SElement;

/**
 * Created by roberto on 05.02.16.
 */
public class SDOFCamera extends DOFCamera implements SElement{

    private static final long serialVersionUID = 1L;

    private final String name;

    public SDOFCamera(Point3 e, Vector3 g, Vector3 t, double angle, DOFPattern dofPattern, double focalLength, SamplingPattern samplingPattern, String name) {
        super(e, g, t, angle*Math.PI/180, dofPattern, focalLength, samplingPattern);
        this.name = name;
    }

    @Override
    public ODOFCamera generate() {

        ODOFCamera camera = new ODOFCamera();
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
        camera.dPatternSubdiv.set(dofPattern.subdiv);
        camera.dPatternFStop.set(dofPattern.size);
        camera.focalLength.set(focalLength);

        return camera;
    }
}
