package observables.textures;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import texture.SingleColorTexture;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSingleColorTexture extends AOTexture {

    public DoubleProperty colorR = new SimpleDoubleProperty();
    public DoubleProperty colorG = new SimpleDoubleProperty();
    public DoubleProperty colorB = new SimpleDoubleProperty();

    public OSingleColorTexture(double[] color) {
        super("",1,1,1,1,0,"");
        this.colorR.setValue(color[0] >= 0 && color[0] <= 1 ? color[0] : 0.5);
        this.colorG.setValue(color[1] >= 0 && color[1] <= 1 ? color[1] : 0.5);
        this.colorB.setValue(color[2] >= 0 && color[2] <= 1 ? color[2] : 0.5);
    }

    @Override
    public SingleColorTexture generate() {
        return new SingleColorTexture(new Color(colorR.get(),colorG.get(),colorB.get()));
    }
}
