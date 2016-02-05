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

}
