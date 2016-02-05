package observables.materials;

import material.LambertMaterial;
import serializable.materials.SLambertMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OLambertMaterial extends AOMaterial{

    public OLambertMaterial() {
        name.set("Lambert Material");
    }

    @Override
    public LambertMaterial generate() {
        return new LambertMaterial(
                texture.get().generate(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }

    @Override
    public SLambertMaterial serialize() {
        return new SLambertMaterial(
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                irradiance.get().serialize(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get(),
                name.get()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AOMaterial that = (AOMaterial) o;

        if (texture != null ? !texture.equals(that.texture) : that.texture != null) return false;
        if (bumpMap != null ? !bumpMap.equals(that.bumpMap) : that.bumpMap != null) return false;
        if (bumpScale != null ? !bumpScale.equals(that.bumpScale) : that.bumpScale != null) return false;
        if (irradiance != null ? !irradiance.equals(that.irradiance) : that.irradiance != null) return false;
        if (ambientOcclusion != null ? !ambientOcclusion.equals(that.ambientOcclusion) : that.ambientOcclusion != null)
            return false;
        if (ambientSize != null ? !ambientSize.equals(that.ambientSize) : that.ambientSize != null) return false;
        return ambientSubdiv != null ? ambientSubdiv.equals(that.ambientSubdiv) : that.ambientSubdiv == null;

    }

    @Override
    public int hashCode() {
        int result = texture != null ? texture.hashCode() : 0;
        result = 31 * result + (bumpMap != null ? bumpMap.hashCode() : 0);
        result = 31 * result + (bumpScale != null ? bumpScale.hashCode() : 0);
        result = 31 * result + (irradiance != null ? irradiance.hashCode() : 0);
        result = 31 * result + (ambientOcclusion != null ? ambientOcclusion.hashCode() : 0);
        result = 31 * result + (ambientSize != null ? ambientSize.hashCode() : 0);
        result = 31 * result + (ambientSubdiv != null ? ambientSubdiv.hashCode() : 0);
        return result;
    }


}
