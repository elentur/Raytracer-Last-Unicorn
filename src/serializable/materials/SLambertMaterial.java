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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SLambertMaterial that = (SLambertMaterial) o;

        if (Double.compare(that.bumpScale, bumpScale) != 0) return false;
        if (ambientOcllusion != that.ambientOcllusion) return false;
        if (Double.compare(that.ambientSize, ambientSize) != 0) return false;
        if (ambientSubdiv != that.ambientSubdiv) return false;
        if (!name.equals(that.name)) return false;
        if (!texture.equals(that.texture)) return false;
        if (!irradiance.equals(that.irradiance)) return false;
        return bumpMap.equals(that.bumpMap);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + texture.hashCode();
        result = 31 * result + bumpMap.hashCode();
        temp = Double.doubleToLongBits(bumpScale);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ambientOcllusion ? 1 : 0);
        temp = Double.doubleToLongBits(ambientSize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ambientSubdiv;
        result = 31 * result + irradiance.hashCode();
        return result;
    }
}
