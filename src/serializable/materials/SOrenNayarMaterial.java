package serializable.materials;

import observables.materials.OOrenNayarMaterial;
import serializable.textures.STexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SOrenNayarMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    private final STexture irradiance;
    private final double roughness;

    public SOrenNayarMaterial(final STexture texture, final STexture bumpMap, final double bumpScale , final STexture irradiance,final double roughness, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv , final String name) {
        super(name,texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance=irradiance;
        this.roughness=roughness;
    }

    @Override
    public OOrenNayarMaterial generate() {
        OOrenNayarMaterial s =  new OOrenNayarMaterial();
        s.name.setValue(name);
        s.texture.setValue( texture.generate());
        s.bumpMap.setValue( bumpMap.generate());
        s.bumpScale.setValue( bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.roughness.setValue(roughness);
        s.ambientOcclusion.setValue( ambientOcllusion);
        s.ambientSize.setValue( ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
