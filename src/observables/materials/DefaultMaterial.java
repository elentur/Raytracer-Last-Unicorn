package observables.materials;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultMaterial {
    /**
     * represents a default material set to every new Geometry
     */
    private final static AOMaterial defaultMaterial = getDefaultMaterial();

    /**
     * Creates a single Default Material
     *
     * @return OLambertMaterial
     */
    private static AOMaterial getDefaultMaterial() {
        OLambertMaterial oLambertMaterial = new OLambertMaterial();
        oLambertMaterial.name.set("Default Material");
        oLambertMaterial.uniqueID = "1";
        return oLambertMaterial;
    }

    /**
     * @return return a default LambertMaterial
     */
    public static AOMaterial getLambert() {
        return new OLambertMaterial();
    }

    /**
     * @return return a default SingleColorMaterial
     */
    public static AOMaterial getSingleColorMaterial() {
        return new OSingleColorMaterial();
    }

    /**
     * @return return a default OrenNayar
     */
    public static AOMaterial getOrenNayar() {
        return new OOrenNayarMaterial();
    }

    /**
     * @return return a default Phong
     */
    public static AOMaterial getPhong() {
        return new OPhongMaterial();
    }

    /**
     * @return return a default ReflectiveMaterial
     */
    public static AOMaterial getReflectiveMaterial() {
        return new OReflectiveMaterial();
    }

    /**
     * @return return a default TransparentMaterial
     */
    public static AOMaterial getTransparentMaterial() {
        return new OTransparentMaterial();
    }

    /**
     * @return return a defaultMaterial
     */
    public static AOMaterial getDefaultLambert() {
        return defaultMaterial;
    }
}
