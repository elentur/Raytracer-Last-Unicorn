package serializable.materials;

import observables.materials.OSingleColorMaterial;
import serializable.textures.STexture;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public class SSingleColorMaterial  extends SMaterial {

    private static final long serialVersionUID = 1L;


    public SSingleColorMaterial(final STexture texture, final STexture bumpMap, final double bumpScale, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv , final String name) {
        super(name,texture, bumpMap, bumpScale, ambientOcllusion, ambientSize, ambientSubdiv);

    }

    @Override
    public OSingleColorMaterial generate() {
        OSingleColorMaterial s =  new OSingleColorMaterial();
        s.name.setValue(name);
        s.texture.setValue( texture.generate());
        s.bumpMap.setValue( bumpMap.generate());
        s.bumpScale.setValue( bumpScale);
        s.ambientOcclusion.setValue( ambientOcllusion);
        s.ambientSize.setValue( ambientSize);
        s.ambientSubdiv.setValue(ambientSubdiv);
        add2MaterialList(s);
        return s;
    }
}
