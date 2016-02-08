package serializable.materials;

import observables.materials.OPhongMaterial;
import serializable.textures.STexture;

/**
 * Wrapper class to serialize the OPhongMaterial object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SPhongMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    /**
     * represents the irradiance Color and intensity of the material.
     */
    private final STexture irradiance;

    /**
     *  Represents the Specular color.
     */
    private final STexture specular;

    /**
     * The value to change the size of the highlight.
     * The larger exponent, the smaller will be the highlight.
     */
    private final int exponent;

    /**
     * Instantiates a new SOrenNayarMaterial Object.
     *
     * @param uniqueID to indentify the material.
     * @param name of the material.
     * @param texture of the material.
     * @param bumpMap normalMap of the Material.
     * @param bumpScale amount of the normalMap displacement.
     * @param irradiance Color and intensity of the material.
     * @param specular material of our Specular.
     * @param exponent is the value size of the highlight.
     * @param ambientOcllusion allows ambient occlusion.
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions.
     */
    public SPhongMaterial(final String uniqueID, final STexture texture, final STexture bumpMap, final double bumpScale, final STexture irradiance, final STexture specular, final int exponent, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv, final String name) {
        super(uniqueID, name, texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance = irradiance;
        this.exponent = exponent;
        this.specular = specular;
    }

    @Override
    public OPhongMaterial generate() {
        OPhongMaterial s = new OPhongMaterial();
        s.uniqueID = uniqueID;
        s.name.setValue(name);
        s.texture.setValue(texture.generate());
        s.bumpMap.setValue(bumpMap.generate());
        s.bumpScale.setValue(bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.specular.setValue(specular.generate());
        s.exponent.setValue(exponent);
        s.ambientOcclusion.setValue(ambientOcllusion);
        s.ambientSize.setValue(ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
