package observables.cameras;

import camera.OrthographicCamera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;
import serializable.cameras.SOrthographicCamera;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OOrthographicCamera extends AOCamera {
    /**
     * represents the scale value
     */
    public final IntegerProperty s = new SimpleIntegerProperty();

    public OOrthographicCamera() {
        name.set("Orthographic Camera");
        s.setValue(3);

        patternSubdiv.addListener(a -> {
            if (patternSubdiv.get() < 1) patternSubdiv.set(1);
        });

        s.addListener(a -> {
            if (s.get() < 1) s.set(1);
        });
    }

    @Override
    public OrthographicCamera generate() {
        return new OrthographicCamera(
                new Point3(ex.get(), ey.get(), ez.get()),
                new Vector3(gx.get(), gy.get(), gz.get()),
                new Vector3(tx.get(), ty.get(), tz.get()),
                this.s.get(),
                new SamplingPattern(patternSubdiv.get())
        );
    }

    @Override
    public SOrthographicCamera serialize() {
        return new SOrthographicCamera(
                ex.get(), ey.get(), ez.get(),
                gx.get(), gy.get(), gz.get(),
                tx.get(), ty.get(), tz.get(),
                this.s.get(),
                patternSubdiv.get(),
                name.get()
        );
    }
}
