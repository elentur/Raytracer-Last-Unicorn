package serializable.materials;

import observables.materials.OOrenNayarMaterial;
import serializable.textures.STexture;

/**
 * Wrapper class to serialize the OOrenNayarMaterial object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SOrenNayarMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    /**
     *  represents the irradiance Color and intensity of the material.
     */
    private final STexture irradiance;

    /**
     * The roughness of the material.
     */
    private final double roughness;

    /**
     * Instantiates a new SOrenNayarMaterial Object.
     *
     * @param uniqueID to indentify the material.
     * @param name of the material.
     * @param texture of the material.
     * @param bumpMap normalMap of the Material.
     * @param bumpScale amount of the normalMap displacement.
     * @param irradiance Color and intensity of the material.
     * @param roughness of the material.
     * @param ambientOcllusion allows ambient occlusion.
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions.
     */
    public SOrenNayarMaterial(final String uniqueID, final STexture texture, final STexture bumpMap, final double bumpScale, final STexture irradiance, final double roughness, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv, final String name) {
        super(uniqueID, name, texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance = irradiance;
        this.roughness = roughness;
    }

    @Override
    public OOrenNayarMaterial generate() {
        OOrenNayarMaterial s = new OOrenNayarMaterial();
        s.uniqueID = uniqueID;
        s.name.setValue(name);
        s.texture.setValue(texture.generate());
        s.bumpMap.setValue(bumpMap.generate());
        s.bumpScale.setValue(bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.roughness.setValue(roughness);
        s.ambientOcclusion.setValue(ambientOcllusion);
        s.ambientSize.setValue(ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
