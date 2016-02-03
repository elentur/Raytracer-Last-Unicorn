package observables.materials;

import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultMaterial {
    private final static AOMaterial defaultMaterial = getLambert();
    public static AOMaterial getLambert(){
        return new OLambertMaterial(
                "Lambert",
                getSingleColorTexture(),
                getSingleColorTexture(),
                500,
                getSingleColorTexture());
    }
    public static AOMaterial getSingleColorMaterial(){
        return new OSingleColorMaterial(
                "Single-Color",
                getSingleColorTexture(),
                getSingleColorTexture(),
                500);
    }
    public static AOMaterial getOrenNayar(){
        return new OOrenNayarMaterial(
                "Oren-Nayar",
                getSingleColorTexture(),
                getSingleColorTexture(),
                500,
                getSingleColorTexture(),
                0.5);
    }
    public static AOMaterial getPhong(){
        return new OPhongMaterial(
                "Phong",
                getSingleColorTexture(),
                getSingleColorTexture(),
                500,
                getSingleColorTexture(),
                getSingleColorTexture(),
                64);
    }
    public static AOMaterial getReflectiveMaterial(){
        return new OReflectiveMaterial(
                "Reflective",
                getSingleColorTexture(),
                getSingleColorTexture(),
                500,
                getSingleColorTexture(),
                getSingleColorTexture(),
                getSingleColorTexture(),
                64
        );
    }
    public static AOMaterial getTransparentMaterial(){
        return new OTransparentMaterial(
                "Transparent",
                getSingleColorTexture(),
                getSingleColorTexture(),
                500,
                getSingleColorTexture(),
                1.0,
                getSingleColorTexture(),
                getSingleColorTexture(),
                64
        );    }


    public static AOTexture getSingleColorTexture(){
        return  new OSingleColorTexture(new double[]{0.5,0.5,0.5});
    }

    public static AOMaterial getDefaultLambert() {
        return defaultMaterial;
    }
}
