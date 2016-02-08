package serializable.materials;

import observables.materials.OSingleColorMaterial;
import serializable.textures.STexture;

/**
 * Wrapper class to serialize the OSingleColorMaterial object.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SSingleColorMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new SSingleColorMaterial Object.
     *
     * @param uniqueID to indentify the material.
     * @param name of the material.
     * @param texture of the material.
     * @param bumpMap normalMap of the Material.
     * @param bumpScale amount of the normalMap displacement.
     * @param ambientOcllusion allows ambient occlusion
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions
     */
    public SSingleColorMaterial(final String uniqueID, final STexture texture, final STexture bumpMap, final double bumpScale, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv, final String name) {
        super(uniqueID, name, texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);

    }

    @Override
    public OSingleColorMaterial generate() {
        OSingleColorMaterial s = new OSingleColorMaterial();
        s.uniqueID = uniqueID;
        s.name.setValue(name);
        s.texture.setValue(texture.generate());
        s.bumpMap.setValue(bumpMap.generate());
        s.bumpScale.setValue(bumpScale);
        s.ambientOcclusion.setValue(ambientOcllusion);
        s.ambientSize.setValue(ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
