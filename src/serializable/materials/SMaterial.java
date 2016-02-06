package serializable.materials;

import controller.AController;
import observables.materials.AOMaterial;
import serializable.SElement;
import serializable.textures.STexture;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SMaterial implements SElement ,Serializable {
    protected static final long serialVersionUID = 1L;
    protected final String uniqueID;
    protected final String name;
    protected final STexture texture;
    protected final STexture bumpMap;
    protected final double bumpScale;
    protected final boolean ambientOcllusion;
    protected final double ambientSize;
    protected final int ambientSubdiv;

    public SMaterial(final String uniqueID, final String name, final STexture texture, final STexture bumpMap, final double bumpScale, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.texture = texture;
        this.bumpMap = bumpMap;
        this.bumpScale = bumpScale;
        this.ambientOcllusion = ambientOcllusion;
        this.ambientSize = ambientSize;
        this.ambientSubdiv = ambientSubdiv;
    }
    @Override
    public abstract AOMaterial generate();

    protected void add2MaterialList(AOMaterial m){
        if(!AController.materialList.contains(m)) {
            AController.materialList.add(m);
        }else{
            if(AController.materialList.indexOf(m) > 5) {
                AController.materialList.set(AController.materialList.indexOf(m), m);
            }
        }
    }
}
