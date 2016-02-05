package serializable.materials;

import observables.materials.OTransparentMaterial;
import serializable.textures.STexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class STransparentMaterial extends SMaterial {

    private static final long serialVersionUID = 1L;

    private final STexture irradiance;
    private final STexture specular;
    private final STexture reflection;
    private final double indexOfRefraction;
    private final int exponent;

    public STransparentMaterial(final STexture texture, final STexture bumpMap, final double bumpScale ,
                              final STexture irradiance,final STexture specular,final int exponent,
                              final STexture reflection, final double indexOfRefraction, final boolean ambientOcllusion, final double ambientSize,
                                final int ambientSubdiv , final String name) {
        super(name,texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance=irradiance;
        this.exponent=exponent;
        this.specular=specular;
        this.reflection=reflection;
        this.indexOfRefraction=indexOfRefraction;
    }

    @Override
    public OTransparentMaterial generate() {
        OTransparentMaterial s =  new OTransparentMaterial();
        s.name.setValue(name);
        s.texture.setValue( texture.generate());
        s.bumpMap.setValue( bumpMap.generate());
        s.bumpScale.setValue( bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.specular.setValue(specular.generate());
        s.exponent.setValue(exponent);
        s.reflection.setValue(reflection.generate());
        s.indexOfRefraction.setValue(indexOfRefraction);
        s.ambientOcclusion.setValue( ambientOcllusion);
        s.ambientSize.setValue( ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        return s;
    }
}
