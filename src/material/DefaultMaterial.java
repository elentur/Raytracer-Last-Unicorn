package material;

import texture.SingleColorTexture;
import utils.Color;

/**
 * Created by roberto on 05.01.16.
 */
public class DefaultMaterial {
    public final static LambertMaterial MATERIAL = new LambertMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
}
