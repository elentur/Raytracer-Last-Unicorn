package serializable.cameras;

import observables.cameras.OOrthographicCamera;

/**
 * Created by roberto on 05.02.16.
 */
public class SOrthographicCamera extends SCamera{
    private final int s;

    public SOrthographicCamera(final double ex, final double ey, final double ez,
                               final double gx, final double gy, final double gz,
                               final double tx, final double ty, final double tz,
                               final int s, final int subdiv, final String name) {
        super(name,ex,ey,ez,gx,gy,gz,tx,ty,tz,subdiv);
        this.s=s;
    }

    @Override
    public OOrthographicCamera generate() {
        OOrthographicCamera camera = new OOrthographicCamera();
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

        camera.s.set(s);

        return camera;
    }
}
