package material;

import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

/**
 * Created by Marcus Baetz on 01.12.2015.
 *
 * @author Marcus Bätz
 */
public class ReflectiveMaterial extends Material {
    public final Color specular;
    public final  Color reflection;
    public final  int exponent;

    public ReflectiveMaterial(final Color diffuse, final  Color specular, final  Color reflection, final  int exponent) {
        super(diffuse);
        this.specular=specular;
        this.reflection=reflection;
        this.exponent=exponent;
    }

    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {
        return diffuse;
    }
}
