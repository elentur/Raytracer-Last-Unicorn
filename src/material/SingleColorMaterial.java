package material;

import texture.SingleColorTexture;
import texture.Texture;
import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

/**
 * Generates a material with no attributes representing only a color
 * Created by Andreas Kiauka on 21.11.2015.
 *
 * @author Andreas Kiauka
 */
public class SingleColorMaterial extends Material {


    /**
     * Generates a SinglColor Object with the given texture
     *@param texture Represents the diffuse Color property of the material
     * @param bumpMap represents the normalMap of the Material
     * @param bumpScale represents the amount of the normalMap displacement
     * @param ambientOcclusion represents if the material allows ambientOcclusion or not
     * @param ambientSize represent the pattern size
     * @param ambientSubdiv represent the ambient occlusion Subdivisions
     * @param texture The Diffuse Color Texture Material
     */

    public SingleColorMaterial(final Texture texture, final Texture bumpMap, final double bumpScale,
                               boolean ambientOcclusion, double ambientSize, int ambientSubdiv) {
        super(texture, bumpMap, bumpScale, new SingleColorTexture(new Color(0, 0, 0)), ambientOcclusion, ambientSize, ambientSubdiv);
    }


    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null) throw new IllegalArgumentException("hit must not be null ");
        if (world == null) throw new IllegalArgumentException("world must not be null ");

        return texture.getColor(hit.texCoord.u, hit.texCoord.v);
    }


    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "material=" + texture +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleColorMaterial)) return false;
        if (!super.equals(o)) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return !(texture != null ? !texture.equals(that.texture) : that.texture != null);

    }

    @Override
    public int hashCode() {
        return texture != null ? texture.hashCode() : 0;
    }
}
