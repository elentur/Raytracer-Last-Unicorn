package serializable.materials;

import controller.AController;
import observables.materials.OLambertMaterial;
import serializable.textures.STexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SLambertMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    private final STexture irradiance;

    public SLambertMaterial(final STexture texture, final STexture bumpMap, final double bumpScale , final STexture irradiance, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv , final String name) {
        super(name,texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance=irradiance;
    }

    @Override
    public OLambertMaterial generate() {
        OLambertMaterial s =  new OLambertMaterial();
        s.name.setValue(name);
        s.texture.setValue( texture.generate());
        s.bumpMap.setValue( bumpMap.generate());
        s.bumpScale.setValue( bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.ambientOcclusion.setValue( ambientOcllusion);
        s.ambientSize.setValue( ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
