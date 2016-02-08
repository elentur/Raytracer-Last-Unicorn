package serializable.materials;

import controller.AController;
import javafx.collections.ObservableList;
import observables.materials.AOMaterial;
import serializable.SElement;
import serializable.textures.STexture;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SMaterial implements SElement, Serializable {
    protected static final long serialVersionUID = 1L;
    final String uniqueID;
    final String name;
    final STexture texture;
    final STexture bumpMap;
    final double bumpScale;
    final boolean ambientOcllusion;
    final double ambientSize;
    final int ambientSubdiv;

    SMaterial(final String uniqueID, final String name, final STexture texture, final STexture bumpMap, final double bumpScale, final boolean ambientOcllusion, final double ambientSize, final int ambientSubdiv) {
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

    void add2MaterialList(AOMaterial m) {

        ObservableList<AOMaterial> list = AController.materialList;

        if (!list.contains(m)) {
            list.add(m);
        } else {
            if (list.indexOf(m) == 6) {
                list.set(6, m);
            }
        }
    }
}
