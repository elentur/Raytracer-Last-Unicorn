package serializable.cameras;

import observables.cameras.ODOFCamera;

/**
 * Created by roberto on 05.02.16.
 */
public class SDOFCamera extends SCamera {

    private final double angle;
    private final int dofSubdiv;
    private final double fstop;
    private final double focalLength;

    public SDOFCamera(final double ex, final double ey, final double ez,
                      final double gx, final double gy, final double gz,
                      final double tx, final double ty, final double tz,
                      final double angle,
                      final int dofSubdiv, final double fstop, final double focalLength,
                      final int subdiv, final String name) {
        super(name, ex, ey, ez, gx, gy, gz, tx, ty, tz, subdiv);
        this.angle = angle;
        this.dofSubdiv = dofSubdiv;
        this.fstop = fstop;
        this.focalLength = focalLength;
    }

    @Override
    public ODOFCamera generate() {

        ODOFCamera camera = new ODOFCamera();
        camera.name.set(name);
        camera.ex.set(ex);
        camera.ey.set(ey);
        camera.ez.set(ez);

        camera.gx.set(gx);
        camera.gy.set(gy);
        camera.gz.set(gz);

        camera.tx.set(tx);
        camera.ty.set(ty);
        camera.tz.set(tz);

        camera.patternSubdiv.set(subdiv);

        camera.angle.set(angle);
        camera.dPatternSubdiv.set(dofSubdiv);
        camera.dPatternFStop.set(fstop);
        camera.focalLength.set(focalLength);

        return camera;
    }
}
