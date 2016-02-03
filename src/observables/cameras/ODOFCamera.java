package observables.cameras;

import camera.DOFCamera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import matVect.Point3;
import matVect.Vector3;
import sampling.DOFPattern;
import sampling.SamplingPattern;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class ODOFCamera extends AOCamera{
    public DoubleProperty angle = new SimpleDoubleProperty();
    public IntegerProperty dPatternSubdiv = new SimpleIntegerProperty();
    public DoubleProperty dPatternFStop  = new SimpleDoubleProperty();
    public DoubleProperty focalLength  = new SimpleDoubleProperty();

    public ODOFCamera(String name, double[] e, double[] g, double[] t, double angle, double[] pattern, double[] dPattern, double focalLength) {
        super(name, e, g, t, pattern);
        this.angle.setValue(angle);
        this.dPatternSubdiv.setValue(dPattern[0]);
        this.dPatternFStop.setValue(dPattern[1]);
        this.focalLength.setValue(focalLength);
    }

    @Override
    public DOFCamera generate() {
        return new DOFCamera(
                new Point3(ex.get(),ey.get(),ez.get()),
                new Vector3(gx.get(),gy.get(),gz.get()),
                new Vector3(tx.get(),ty.get(),tz.get()),
                this.angle.get(),
                new DOFPattern(dPatternSubdiv.get(),dPatternFStop.get()),
                focalLength.get(),
                new SamplingPattern(patternSubdiv.get())
        );
    }
}


