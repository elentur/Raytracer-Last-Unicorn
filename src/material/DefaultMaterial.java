package material;

import texture.ImageTexture;
import texture.InterpolatedImageTexture;
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
    public final static SingleColorMaterial SINGLE_COLOR_MATERIAL = new SingleColorMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            0
    );

    public final static LambertMaterial LAMBERT_MATERIAL = new LambertMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static OrenNayarMaterial OREN_NAYAR_MATERIAL = new OrenNayarMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),0.5,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static PhongMaterial PHONG_MATERIAL = new PhongMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            64,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static ReflectiveMaterial REFLECTIVE_MATERIAL = new ReflectiveMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            64,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static TransparentMaterial TRANSPARENT_MATERIAL = new TransparentMaterial(
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            new SingleColorTexture( new Color(1,1,1)),
            new SingleColorTexture( new Color(0.5,0.5,0.5)),
            64,
            1,
            new SingleColorTexture( new Color(1,1,1)),
            0,
            new SingleColorTexture( new Color(1,1,1))
    );
    public final static SingleColorTexture SINGLE_COLOR_TEXTURE = new SingleColorTexture(
        new Color(1,1,1)
    );
    public final static ImageTexture IMAGE_TEXTURE = new ImageTexture(
            "",1,1,0,0
    );
    public final static InterpolatedImageTexture INTERPOLATED_IMAGE_TEXTURE = new InterpolatedImageTexture(
            "",1,1,0,0
    );
}
