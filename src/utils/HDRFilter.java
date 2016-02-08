package utils;

/**
 * This class represents a HDRFilter witch equalize all pixel according to the highest HDR.
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class HDRFilter {
    /**
     * The Array
     */
    private final int[] colorBuffer;
    /**
     * The highest HDR value
     */
    private double maxHDR = 0;
    /**
     * The width of the the Image
     */
    private final int width;

    /**
     * Instantiates a new HDRFilter Object.
     *
     * @param width  is the width of the image. it has to be bigger than 0.
     * @param height is the height of the image. it has to be bigger than 0.
     * @throws IllegalArgumentException if one of the given arguments are smaller or equals 0.
     */
    public HDRFilter(int width, int height) {
        if (width <= 0) throw new IllegalArgumentException("The width have to be bigger than 0!");
        if (height <= 0) throw new IllegalArgumentException("The height have to be bigger than 0!");
        this.width = width;
        colorBuffer = new int[width * height * 3];
    }

    /**
     * Filters the highest HDR value of the Pixel
     * @param c is the color of the pixel
     * @param x is the x coordinate of the pixel
     * @param y is the y coordinate of the pixel
     */
    public void filter(Color c, int x, int y) {
        colorBuffer[(y * width * 3) + x * 3] = (int) (c.r * 255);
        colorBuffer[(y * width * 3) + x * 3 + 1] = (int) (c.g * 255);
        colorBuffer[(y * width * 3) + x * 3 + 2] = (int) (c.b * 255);
        if (c.r > maxHDR) maxHDR = c.r;
        if (c.g > maxHDR) maxHDR = c.g;
        if (c.b > maxHDR) maxHDR = c.b;
    }

    /**
     * Equalize all pixel according to the maxHDR
     * @param imageData is the image in binary data
     */
    public void getImage(byte[] imageData) {
        double hdr = 1.0 / maxHDR;
        if (hdr < 255) {
            for (int i = 0; i < colorBuffer.length; i++) {
                imageData[i] = (byte) (colorBuffer[i] * hdr);
            }
        }
    }


}
