package material;

import utils.Color;
import utils.Hit;
import utils.World;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class SingleColorMaterial extends Material {

    public final Color color;

    public SingleColorMaterial(Color color) {
        this.color = color;
    }

    @Override
    public Color colorFor(Hit hit, World world) {
        return null;
    }

    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "material=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return !(color != null ? !color.equals(that.color) : that.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
