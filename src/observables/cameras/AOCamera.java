package observables.cameras;

import camera.Camera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import observables.AOElement;
import serializable.SElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOCamera extends AOElement {

    public final DoubleProperty ex = new SimpleDoubleProperty(0);
    public final DoubleProperty ey = new SimpleDoubleProperty(0);
    public final DoubleProperty ez = new SimpleDoubleProperty(5);

    public final DoubleProperty gx = new SimpleDoubleProperty(0);
    public final DoubleProperty gy = new SimpleDoubleProperty(0);
    public final DoubleProperty gz = new SimpleDoubleProperty(-1);

    public final DoubleProperty tx = new SimpleDoubleProperty(0);
    public final DoubleProperty ty = new SimpleDoubleProperty(1);
    public final DoubleProperty tz = new SimpleDoubleProperty(0);

    public AOCamera(){
        tx.addListener(a->{
            checkT();
        });

        ty.addListener(a->{
            checkT();
        });

        tz.addListener(a->{
            checkT();
        });

        gx.addListener(a->{
            checkG();
        });

        gy.addListener(a->{
            checkG();
        });

        gz.addListener(a->{
            checkG();
        });

        ex.addListener(a->{
            checkE();
        });

        ey.addListener(a->{
            checkE();
        });

        ez.addListener(a->{
            checkE();
        });

    }

    private void checkT(){
        if(tx.get() == 0 && ty.get() == 0 && tz.get() == 0)
            ty.set(1);
    }

    private void checkG(){
        if(gx.get() == 0 && gy.get() == 0 && gz.get() == 0)
            gy.set(1);
    }

    private void checkE(){
        if(ex.get() == 0 && ey.get() == 0 && ez.get() == 0)
            ey.set(1);
    }

    public final IntegerProperty patternSubdiv = new SimpleIntegerProperty(1);

    public abstract Camera generate();

    public abstract SElement serialize();
}
