package serializable.cameras;

import observables.cameras.ODOFCamera;

/**
 * Wrapper class to serialize the ODOFCamera object.
 * Created by Robert Dziuba on 05.02.16.
 */
public class SDOFCamera extends SCamera {

    /**
     * the opening angle
     */
    private final double angle;
    /**
     * Square number of the Rays for each pixel for the DOFPattern
     */
    private final int dofSubdiv;
    /**
     * size of the pattern
     */
    private final double fstop;
    /**
     * represents the range between the camera and the focused point
     */
    private final double focalLength;

    /**
     * Instantiates a new SDOFCamera Object.
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
     * @param dofSubdiv Square number of the Rays.
     * @param fstop size of the pattern.
     * @param focalLength focused point.
     * @param subdiv the Square number of the Rays for each pixel.
     * @param name of the camera.
     */
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
