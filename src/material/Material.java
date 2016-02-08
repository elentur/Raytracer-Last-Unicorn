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

    /**
     * Represents the diffuse Color property of the material
     */
    final Texture texture;
    /**
     * represents the normalMap of the Material
     */
    public final Texture bumpMap;
    /**
     * represents the amount of the normalMap displacement
     */
    public final double bumpScale;
    /**
     * represents the irradiance Color and intensity of the material(not implemented)
     */
    private final Texture irradiance;
    /**
     * represents if the material allows ambientOcclusion or not
     */
    final boolean ambientOcclusion;
    /**
     * represent the pattern size
     */
    final double ambientSize;
    /**
     * represent the ambient occlusion Subdivisions
     */
    final int ambientSubdiv;

    /**
     * Creates a new Material
     *
     * @param texture          Represents the diffuse Color property of the material
     * @param bumpMap          represents the normalMap of the Material
     * @param bumpScale        represents the amount of the normalMap displacement
     * @param irradiance       represents the irradiance Color and intensity of the material(not implemented)
     * @param ambientOcclusion represents if the material allows ambientOcclusion or not
     * @param ambientSize      represent the pattern size
     * @param ambientSubdiv    represent the ambient occlusion Subdivisions
     */
    Material(final Texture texture, final Texture bumpMap, final double bumpScale, final Texture irradiance,
             boolean ambientOcclusion, double ambientSize, int ambientSubdiv) {
        if (texture == null) {
            throw new IllegalArgumentException("Texture cannot be null!");
        }
        this.texture = texture;
        this.bumpMap = bumpMap;
        this.bumpScale = bumpScale;
        this.irradiance = irradiance;
        this.ambientOcclusion = ambientOcclusion;
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
        if (ambientOcclusion != material.ambientOcclusion) return false;
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
        result = 31 * result + (ambientOcclusion ? 1 : 0);
        temp = Double.doubleToLongBits(ambientSize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ambientSubdiv;
        return result;
    }
}
