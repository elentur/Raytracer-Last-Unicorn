package camera;

import matVect.Point3;
import matVect.Vector3;
import utils.Ray;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class PerspectiveCamera extends Camera {
    /**
     * the opening angle
     */
    public final double angle;
    /**
     * Constructor initializes e. g and t.
     *
     * @param e
     *            eye position
     * @param g
     *            gaze vector (Blickrichtung)
     * @param t
     *            up vector
     * @param angle
     *            the opening angle
     */
    public PerspectiveCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle) {
        super(e, g, t);
        this.angle = angle;
    }

    @Override
    public Ray rayFor(final int w,final int h,final int x,final int y) {
        final Vector3 summand1 = this.w.mul(-1).mul( (h/2) / Math.tan(angle/2)  );
        final Vector3 summand2 = this.u.mul( x - ((w -1)/2)    );
        final Vector3 summand3 = this.v.mul(y -((h-1)/2));
        final Vector3 r = summand1.add(summand2).add(summand3);

        return  new Ray(this.e, r.normalized() );
    }
}
