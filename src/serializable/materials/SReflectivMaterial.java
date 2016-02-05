package serializable.materials;

import observables.materials.OReflectiveMaterial;
import serializable.textures.STexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SReflectivMaterial  extends SMaterial {

    private static final long serialVersionUID = 1L;

    private final STexture irradiance;
    private final STexture specular;
    private final STexture reflection;
    private final int exponent;

    public SReflectivMaterial(final STexture texture, final STexture bumpMap, final double bumpScale ,
                              final STexture irradiance,final STexture specular,final int exponent,
                              final STexture reflection, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv , final String name) {
        super(name,texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);
        this.irradiance=irradiance;
        this.exponent=exponent;
        this.specular=specular;
        this.reflection=reflection;
    }

    @Override
    public OReflectiveMaterial generate() {
        OReflectiveMaterial s =  new OReflectiveMaterial();
        s.name.setValue(name);
        s.texture.setValue( texture.generate());
        s.bumpMap.setValue( bumpMap.generate());
        s.bumpScale.setValue( bumpScale);
        s.irradiance.setValue(irradiance.generate());
        s.specular.setValue(specular.generate());
        s.exponent.setValue(exponent);
        s.reflection.setValue(reflection.generate());
        s.ambientOcclusion.setValue( ambientOcllusion);
        s.ambientSize.setValue( ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        return s;
    }
}
