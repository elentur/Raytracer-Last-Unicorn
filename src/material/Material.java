package material;

import utils.Color;
import utils.Hit;
import utils.World;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Material {

    public abstract Color colorFor(Hit hit, World world);
}
