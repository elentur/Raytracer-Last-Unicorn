package serializable.materials;

import observables.materials.OTransparentMaterial;
import serializable.textures.STexture;

/**
 * Wrapper class to serialize the OTransparentMaterial object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class STransparentMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    /**
     *  represents the irradiance Color and intensity of the material.
     */
    private final STexture irradiance;

    /**
     * Represents the Specular color
     */
    private final STexture specular;

    /**
     * The color of our reflection.
     */
    private final STexture reflection;

    /**
     * represents the index of refraction of the material
     */
    private final double indexOfRefraction;

    /**
     * represents the intensity of the specular
     */
    private final int exponent;

    /**
     * Instantiates a new STransparentMaterial Object.
     *
     * @param uniqueID to indentify the material.
     * @param name of the material.
     * @param texture of the material.
     * @param bumpMap normalMap of the Material.
     * @param bumpScale amount of the normalMap displacement.
     * @param irradiance Color and intensity of the material.
     * @param specular is the Specular color.
     * @param exponent is the value size of the highlight.
     * @param reflection is the size of the highlight.
     * @param indexOfRefraction is the index of refraction of the material.
     * @param ambientOcllusion allows ambient occlusion.
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions.
     */
    public STransparentMaterial(final String uniqueID, final STexture texture, final STexture bumpMap, final double bumpScale,
                                final STexture irradiance, final STexture specular, final int exponent,
                                final STexture reflection, final double indexOfRefraction, final boolean ambientOcllusion, final double ambientSize,
                                final int ambientSubdiv, final String name) {
        super(uniqueID, name, texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance = irradiance;
        this.exponent = exponent;
        this.specular = specular;
        this.reflection = reflection;
        this.indexOfRefraction = indexOfRefraction;
    }

    @Override
    public OTransparentMaterial generate() {
        OTransparentMaterial s = new OTransparentMaterial();
        s.uniqueID = uniqueID;
        s.name.setValue(name);
        s.texture.setValue(texture.generate());
        s.bumpMap.setValue(bumpMap.generate());
        s.bumpScale.setValue(bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.specular.setValue(specular.generate());
        s.exponent.setValue(exponent);
        s.reflection.setValue(reflection.generate());
        s.indexOfRefraction.setValue(indexOfRefraction);
        s.ambientOcclusion.setValue(ambientOcllusion);
        s.ambientSize.setValue(ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
