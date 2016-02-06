package serializable.cameras;

import observables.cameras.AOCamera;
import serializable.SElement;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 06.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SCamera implements SElement,Serializable {

    private static final long serialVersionUID = 1L;
    protected final String name;
    protected final double ex;
    protected final double ey;
    protected final double ez;
    protected final double gx;
    protected final double gy;
    protected final double gz;
    protected final double tx;
    protected final double ty;
    protected final double tz;
    protected final int subdiv;

    public SCamera(final String name, final double ex, final double ey, final double ez,
                   final double gx, final double gy, final double gz,
                   final double tx, final double ty, final double tz,
                   final int subdiv) {
        this.name = name;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.subdiv=subdiv;
    }

    @Override
    public abstract AOCamera generate();
}