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



    public SingleColorMaterial(Color color) {
        super(color);
    }

    @Override
    public Color colorFor(Hit hit, World world) {
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
