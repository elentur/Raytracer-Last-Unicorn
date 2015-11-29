package utils;

/**
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class HDRFilter {
    private final int[] colorBuffer;
    private double  maxHDR=0;
    private final int width;
    private final int height;
    public HDRFilter(int width, int height){
        this.width=width;
        this.height=height;
       colorBuffer = new int[width*height*3];
    }
    public void filter(Color c,int x, int y) {
        colorBuffer[(y*width*3)+x*3 ]=(int)(c.r*255);
        colorBuffer[(y*width*3)+x*3 +1]=(int)(c.g*255);
        colorBuffer[(y*width*3)+x*3 +2]=(int)(c.b*255);
        if(c.r > maxHDR) maxHDR=c.r;
        if(c.g > maxHDR) maxHDR=c.g;
        if(c.b> maxHDR) maxHDR=c.b;
    }

    public void getImage(byte[] imageData){
        double hdr = 1.0/maxHDR;
        for (int i = 0; i < colorBuffer.length; i++) {
                imageData[i] = (byte)(colorBuffer[i]*hdr);
        }
    }



}
