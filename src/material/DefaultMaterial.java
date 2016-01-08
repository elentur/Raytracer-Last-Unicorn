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
    public final static SingleColorMaterial SINGLECOLORMATERIAL = new SingleColorMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            0
    );
    public final static LambertMaterial LAMBERTMATERIAL = new LambertMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static OrenNayarMaterial ORENNAYARMATERIAL = new OrenNayarMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),0.5,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static PhongMaterial PHONGMATERIAL = new PhongMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            64,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static ReflectiveMaterial REFLECTIVEMATERIAL = new ReflectiveMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            64,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static TransparentMaterial TRANSPARENTMATERIAL = new TransparentMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            64,
            1,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
}
