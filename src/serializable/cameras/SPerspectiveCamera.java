package serializable.cameras;

import observables.cameras.OPerspectiveCamera;

/**
 * Wrapper class to serialize the OPerspectiveCamera object.
 * Created by roberto on 05.02.16.
 */
public class SPerspectiveCamera extends SCamera {
    /**
     * the opening angle
     */
    private final double angle;

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
     * @param angle the opening angle.
     * @param subdiv the Square number of the Rays for each pixel.
     * @param name of the camera.
     */
    public SPerspectiveCamera(final double ex, final double ey, final double ez,
                              final double gx, final double gy, final double gz,
                              final double tx, final double ty, final double tz,
                              final double angle,
                              final int subdiv, final String name) {
        super(name, ex, ey, ez, gx, gy, gz, tx, ty, tz, subdiv);
        this.angle = angle;
    }

    @Override
    public OPerspectiveCamera generate() {

        OPerspectiveCamera camera = new OPerspectiveCamera();
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
