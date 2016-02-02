package observables.textures;

import texture.CheckerTexture;
import texture.Texture;
import utils.Color;

/**
 * Created by roberto on 02/02/16.
 */
public class OCheckerTexture extends AOTexture{

    public double colorR;
    public double colorG;
    public double colorB;

    public OCheckerTexture(int[] color) {
        super("",1,1,1,1,0,"");
        this.colorR = color[0] >= 0 && color[0] <= 1 ? color[0] : 0.5;
        this.colorG = color[1] >= 0 && color[1] <= 1 ? color[1] : 0.5;
        this.colorB = color[2] >= 0 && color[2] <= 1 ? color[2] : 0.5;
    }

    @Override
    public CheckerTexture generate() {
        return new CheckerTexture(new Color(colorR,colorG,colorB));
    }
}
