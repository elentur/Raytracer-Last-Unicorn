package texture;

import utils.Color;

/**
 * Created by roberto on 03/12/15.
 */
public class CheckerTexture extends Texture {

    final Color color;

    public CheckerTexture(final Color color) {
        super(1,1,1,1,0,"");
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        this.color = color;
    }
    public CheckerTexture(final Color color, final double ScaleU, final double ScaleV, final double OffsetU, final double OffsetV, final double rotate) {
        super(ScaleU,ScaleV,OffsetU,OffsetV,rotate,"");
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        this.color = color;
    }

    @Override
    public Color getColor(double u, double v) {

        double u1 = u*Math.cos(rotate) -v*Math.sin(rotate);
        v = u*Math.sin(rotate) +v*Math.cos(rotate);
        u=u1;
        u= (u* scaleU + offsetU)%1.0;
        v= (v* scaleV + offsetV)%1.0;
        if(u<0)u+=1.0;
        if(v<0)v+=1.0;
        Color c;
        c = new Color(1,1,1);
        if((u <0.5 && v<0.5) ||(u>=0.5 &&v>=0.5)){
            c = color;
        }
        return c;
    }
}
