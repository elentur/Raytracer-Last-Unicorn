package utils;

import javafx.scene.image.PixelWriter;

/**
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class HDRFilter {
    private final Color[] colorBuffer;
    private double  maxHDR=0;
    private final int width;
    private final int height;
    public HDRFilter(int width, int height){
        this.width=width;
        this.height=height;
       colorBuffer = new Color[width*height];
    }
    public void filter(Color c,int x, int y) {
        colorBuffer[(y*width)+x]=c;
        if(c.r > maxHDR) maxHDR=c.r;
        if(c.g > maxHDR) maxHDR=c.g;
        if(c.b> maxHDR) maxHDR=c.b;
    }

    public PixelWriter getImage(PixelWriter p){
        double hdr = 1.0/maxHDR;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = colorBuffer[(y*width)+x];
                p.setColor(x,y, new javafx.scene.paint.Color(c.r*hdr,c.g*hdr,c.b*hdr,1.0));
            }

        }
        return p;
    }



}
