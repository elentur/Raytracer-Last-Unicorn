package observables.cameras;

import camera.PerspectiveCamera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPerspectiveCamera extends AOCamera {

    public DoubleProperty angle = new SimpleDoubleProperty();

    public OPerspectiveCamera(String name, double[] e, double[] g, double[] t, double angle, double[] pattern) {
        super(name, e, g, t, pattern);
        this.angle.setValue(angle);
    }

    @Override
    public PerspectiveCamera generate(){
        return new PerspectiveCamera(
                new Point3(ex.get(),ey.get(),ez.get()),
                new Vector3(gx.get(),gy.get(),gz.get()),
                new Vector3(tx.get(),ty.get(),tz.get()),
                this.angle.get(),
                new SamplingPattern(patternSubdiv.get())
        );
    }
}
