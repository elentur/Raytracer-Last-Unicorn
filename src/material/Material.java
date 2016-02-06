package material;

import texture.Texture;
import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Material {


    final Texture texture;
    public final Texture bumpMap;
    public final double bumpScale;
    private final Texture irradiance;
    final boolean ambientOcllusion;
    final double ambientSize;
    final int ambientSubdiv;

    Material(final Texture texture, final Texture bumpMap, final double bumpScale, final Texture irradiance,
             boolean ambientOcllusion, double ambientSize, int ambientSubdiv) {
        if (texture == null) {
            throw new IllegalArgumentException("Texture cannot be null!");
        }
        this.texture = texture;
        this.bumpMap = bumpMap;
        this.bumpScale = bumpScale;
        this.irradiance = irradiance;
        this.ambientOcllusion = ambientOcllusion;
        this.ambientSize = ambientSize;
        this.ambientSubdiv = ambientSubdiv;
    }

    /**
     * Returns the right illuminated color for the hit point
     *
     * @param hit    The Hit-Object for the hit Point
     * @param world  The WorldObject for this scene
     * @param tracer calculates the color
     * @return the Color-Object for the hit point
     */
    public abstract Color colorFor(final Hit hit, final World world, final Tracer tracer);

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;

        Material material = (Material) o;

        if (Double.compare(material.bumpScale, bumpScale) != 0) return false;
        if (ambientOcllusion != material.ambientOcllusion) return false;
        if (Double.compare(material.ambientSize, ambientSize) != 0) return false;
        if (ambientSubdiv != material.ambientSubdiv) return false;
        if (!texture.equals(material.texture)) return false;
        if (bumpMap != null ? !bumpMap.equals(material.bumpMap) : material.bumpMap != null) return false;
        return !(irradiance != null ? !irradiance.equals(material.irradiance) : material.irradiance != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = texture.hashCode();
        result = 31 * result + (bumpMap != null ? bumpMap.hashCode() : 0);
        temp = Double.doubleToLongBits(bumpScale);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (irradiance != null ? irradiance.hashCode() : 0);
        result = 31 * result + (ambientOcllusion ? 1 : 0);
        temp = Double.doubleToLongBits(ambientSize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ambientSubdiv;
        return result;
    }
}
