package serializable.materials;


import observables.materials.OLambertMaterial;
import serializable.textures.STexture;

/**
 * Wrapper class to serialize the OLambertMaterial object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SLambertMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    /**
     * represents the irradiance Color and intensity of the material.
     */
    private final STexture irradiance;

    /**
     * Instantiates a new SLambertMaterial Object.
     *
     * @param uniqueID to indentify the material.
     * @param name of the material.
     * @param texture of the material.
     * @param bumpMap normalMap of the Material.
     * @param bumpScale amount of the normalMap displacement.
     * @param irradiance Color and intensity of the material.
     * @param ambientOcllusion allows ambient occlusion.
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions.
     */
    public SLambertMaterial(final String uniqueID, final STexture texture, final STexture bumpMap, final double bumpScale, final STexture irradiance, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv, final String name) {
        super(uniqueID, name, texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance = irradiance;
    }

    @Override
    public OLambertMaterial generate() {
        OLambertMaterial s = new OLambertMaterial();
        s.uniqueID = uniqueID;
        s.name.setValue(name);
        s.texture.setValue(texture.generate());
        s.bumpMap.setValue(bumpMap.generate());
        s.bumpScale.setValue(bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.ambientOcclusion.setValue(ambientOcllusion);
        s.ambientSize.setValue(ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
