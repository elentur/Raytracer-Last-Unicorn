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

    final Texture texture;

    /**
     * Generates a SinglColor Object with the given texture
     *
     * @param texture
     */

    public SingleColorMaterial(final Texture texture) {
        super(texture);
        if(texture == null){
            throw new IllegalArgumentException("texture must not be null");
        }
        this.texture = texture;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (hit == null) throw new IllegalArgumentException("hit must not be null ");
        if (world == null) throw new IllegalArgumentException("world must not be null ");

        return texture.getColor(hit.texCoord.u,hit.texCoord.v);
    }

    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "material=" + texture +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return !(texture != null ? !texture.equals(that.texture) : that.texture != null);

    }

    @Override
    public int hashCode() {
        return texture != null ? texture.hashCode() : 0;
    }
}
