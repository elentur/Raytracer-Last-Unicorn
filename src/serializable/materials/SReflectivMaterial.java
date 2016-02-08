package serializable.materials;

import observables.materials.OReflectiveMaterial;
import serializable.textures.STexture;

/**
 * Wrapper class to serialize the OReflectivMaterial object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SReflectivMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    /**
     * represents the irradiance Color and intensity of the material.
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
     * The value to change the size of the highlight.
     * The larger exponent, the smaller will be the highlight.
     */
    private final int exponent;

    /**
     * Instantiates a new SReflectivMaterial Object.
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
     * @param ambientOcllusion allows ambient occlusion.
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions.
     */
    public SReflectivMaterial(final String uniqueID, final STexture texture, final STexture bumpMap, final double bumpScale,
                              final STexture irradiance, final STexture specular, final int exponent,
                              final STexture reflection, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv, final String name) {
        super(uniqueID, name, texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance = irradiance;
        this.exponent = exponent;
        this.specular = specular;
        this.reflection = reflection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SSingleColorMaterial material = (SSingleColorMaterial) o;

        if (Double.compare(material.bumpScale, bumpScale) != 0) return false;
        if (ambientOcllusion != material.ambientOcllusion) return false;
        if (Double.compare(material.ambientSize, ambientSize) != 0) return false;
        if (ambientSubdiv != material.ambientSubdiv) return false;
        if (name != null ? !name.equals(material.name) : material.name != null) return false;
        if (texture != null ? !texture.equals(material.texture) : material.texture != null) return false;
        return bumpMap != null ? bumpMap.equals(material.bumpMap) : material.bumpMap == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (texture != null ? texture.hashCode() : 0);
        result = 31 * result + (bumpMap != null ? bumpMap.hashCode() : 0);
        temp = Double.doubleToLongBits(bumpScale);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ambientOcllusion ? 1 : 0);
        temp = Double.doubleToLongBits(ambientSize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ambientSubdiv;
        return result;
    }

    @Override
    public OReflectiveMaterial generate() {
        OReflectiveMaterial s = new OReflectiveMaterial();
        s.uniqueID = uniqueID;
        s.name.setValue(name);
        s.texture.setValue(texture.generate());
        s.bumpMap.setValue(bumpMap.generate());
        s.bumpScale.setValue(bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.specular.setValue(specular.generate());
        s.exponent.setValue(exponent);
        s.reflection.setValue(reflection.generate());
        s.ambientOcclusion.setValue(ambientOcllusion);
        s.ambientSize.setValue(ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
