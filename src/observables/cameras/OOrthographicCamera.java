package observables.cameras;

import camera.OrthographicCamera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;
import serializable.cameras.SOrthographicCamera;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OOrthographicCamera extends AOCamera{

    public DoubleProperty s = new SimpleDoubleProperty();

    public OOrthographicCamera() {
        name.set("Orthographic Camera");
        s.setValue(3);
    }

    @Override
    public OrthographicCamera generate(){
        return new OrthographicCamera(
                new Point3(ex.get(),ey.get(),ez.get()),
                new Vector3(gx.get(),gy.get(),gz.get()),
                new Vector3(tx.get(),ty.get(),tz.get()),
                this.s.get(),
                new SamplingPattern(patternSubdiv.get())
        );
    }

    @Override
    public SOrthographicCamera serialize(){
        return new SOrthographicCamera(
                new Point3(ex.get(),ey.get(),ez.get()),
                new Vector3(gx.get(),gy.get(),gz.get()),
                new Vector3(tx.get(),ty.get(),tz.get()),
                this.s.get(),
                new SamplingPattern(patternSubdiv.get()),
                name.get()
        );
    }
}
