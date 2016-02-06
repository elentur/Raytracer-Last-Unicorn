package serializable.cameras;

import observables.cameras.ODOFCamera;

/**
 * Created by roberto on 05.02.16.
 *
 */
public class SPerspectiveCamera extends SCamera {
    private final double angle;
    public SPerspectiveCamera(final double ex, final double ey, final double ez,
                      final double gx, final double gy, final double gz,
                      final double tx, final double ty, final double tz,
                      final double angle,
                      final int subdiv, final String name) {
        super(name,ex,ey,ez,gx,gy,gz,tx,ty,tz,subdiv);
        this.angle=angle;
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


        return camera;
    }
}
