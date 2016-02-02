package observables.textures;

import texture.TexCoord2;

import java.util.Observable;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OTexCoord2 extends Observable {

    public double u;
    public double v;

    public OTexCoord2(double u, double v) {
        this.u = u;
        this.v = v;
    }

    public TexCoord2 generate(){
        return new TexCoord2(u,v);
    }
}
