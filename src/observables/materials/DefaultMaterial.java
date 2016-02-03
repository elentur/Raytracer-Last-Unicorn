package observables.materials;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultMaterial {
    private final static AOMaterial defaultMaterial = getDefaultMaterial();
    public static AOMaterial getDefaultMaterial(){
        OLambertMaterial oLambertMaterial = new OLambertMaterial();
        oLambertMaterial.name.set("Default Material");
        return oLambertMaterial;
    }
    public static AOMaterial getLambert(){
        return new OLambertMaterial();
    }
    public static AOMaterial getSingleColorMaterial(){
        return new OSingleColorMaterial();
    }
    public static AOMaterial getOrenNayar(){
        return new OOrenNayarMaterial();
    }
    public static AOMaterial getPhong(){
        return new OPhongMaterial();
    }
    public static AOMaterial getReflectiveMaterial(){
        return new OReflectiveMaterial();
    }
    public static AOMaterial getTransparentMaterial(){
        return new OTransparentMaterial();    }

    public static AOMaterial getDefaultLambert() {
        return defaultMaterial;
    }
}
