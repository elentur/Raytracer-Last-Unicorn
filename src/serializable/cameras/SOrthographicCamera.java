package serializable.cameras;

import observables.cameras.OOrthographicCamera;

/**
 * Wrapper class to serialize the OOrthographicCamera object.
 * Created by Robert Dziuba on 05.02.16.
 */
public class SOrthographicCamera extends SCamera {
    /**
     * scale factor of the of image scene
     */
    private final int s;

    /**
     * Instantiates a new SOrthographicCamera Object.
     *
     * @param ex coordinates for the eye position.
     * @param ey coordinates for the eye position.
     * @param ez coordinates for the eye position.
     * @param gx Coordinates for the gaze vector .
     * @param gy Coordinates for the gaze vector.
     * @param gz Coordinates for the gaze vector.
     * @param tx Coordinates for the up vector.
     * @param ty Coordinates for the up vector.
     * @param tz Coordinates for the up vector.
     * @param s scale factor.
     * @param subdiv the Square number of the Rays for each pixel.
     * @param name of the camera.
     */
    public SOrthographicCamera(final double ex, final double ey, final double ez,
                               final double gx, final double gy, final double gz,
                               final double tx, final double ty, final double tz,
                               final int s, final int subdiv, final String name) {
        super(name, ex, ey, ez, gx, gy, gz, tx, ty, tz, subdiv);
        this.s = s;
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
