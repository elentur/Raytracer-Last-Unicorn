package material;

import utils.Color;
import utils.Hit;
import utils.World;

/**
 * This class represents a SingleColorMaterial.
 *
 * @author Andreas Kiauka 17.11.2015
 */
public class SingleColorMaterial extends Material {


    /**
     * Instantiates a new SingleColorMaterial Object.
     *
     * @param color of the Material. Can't be null.
     */
    public SingleColorMaterial(final Color color) {
        super(color);
    }

    @Override
    public Color colorFor(final Hit hit, final World world) {
        if(hit == null){
            throw new IllegalArgumentException("hit must not be null");
        }
        if(world == null){
            throw new IllegalArgumentException("world must not be null");
        }
        return diffuse;
    }

    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "material=" + diffuse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return !(diffuse != null ? !diffuse.equals(that.diffuse) : that.diffuse != null);

    }

    @Override
    public int hashCode() {
        return diffuse != null ? diffuse.hashCode() : 0;
    }
}
