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
import serializable.cameras.SDOFCamera;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class ODOFCamera extends AOCamera {
    public final DoubleProperty angle = new SimpleDoubleProperty();
    public final IntegerProperty dPatternSubdiv = new SimpleIntegerProperty();
    public final DoubleProperty dPatternFStop = new SimpleDoubleProperty();
    public final DoubleProperty focalLength = new SimpleDoubleProperty();

    public ODOFCamera() {
        name.set("DOF Camera");
        angle.setValue(40);
        dPatternSubdiv.setValue(3);
        dPatternFStop.setValue(8);
        focalLength.setValue(5);

        patternSubdiv.addListener(a->{
            if(patternSubdiv.get() < 1) patternSubdiv.set(1);
        });

        dPatternSubdiv.addListener(a -> {
            if(dPatternSubdiv.get() < 1) dPatternSubdiv.set(1);
        });

        dPatternFStop.addListener(a->{
            if(dPatternFStop.get() < 0) dPatternFStop.set(0);
        });

        angle.addListener(a ->{
            if(angle.get() < 1 || angle.get() > 90)
                angle.set(1);
        });


    }

    @Override
    public DOFCamera generate() {
        return new DOFCamera(
                new Point3(ex.get(), ey.get(), ez.get()),
                new Vector3(gx.get(), gy.get(), gz.get()),
                new Vector3(tx.get(), ty.get(), tz.get()),
                this.angle.get() * (Math.PI / 180),
                new DOFPattern(dPatternSubdiv.get(), dPatternFStop.get()),
                focalLength.get(),
                new SamplingPattern(patternSubdiv.get())
        );
    }

    @Override
    public SDOFCamera serialize() {
        return new SDOFCamera(
                ex.get(), ey.get(), ez.get(),
                gx.get(), gy.get(), gz.get(),
                tx.get(), ty.get(), tz.get(),
                this.angle.get(),
                dPatternSubdiv.get(),
                dPatternFStop.get(),
                focalLength.get(),
                patternSubdiv.get(),
                name.get()
        );
    }
}


