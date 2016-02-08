package serializable.materials;

import controller.AController;
import javafx.collections.ObservableList;
import observables.materials.AOMaterial;
import serializable.SElement;
import serializable.textures.STexture;

import java.io.Serializable;

/**
 * Abstract wrapper class for all serializable material objects.
 * Created by Marcus Baetz on 05.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SMaterial implements SElement, Serializable {
    protected static final long serialVersionUID = 1L;
    /**
     * The unique ID to identify the material after loading from file.
     */
    final String uniqueID;
    /**
     * The name of the material.
     */
    final String name;
    /**
     * The texture of the material.
     */
    final STexture texture;
    /**
     * represents the normalMap of the Material
     */
    final STexture bumpMap;
    /**
     * represents the amount of the normalMap displacement
     */
    final double bumpScale;
    /**
     * represents if the material allows ambientOcclusion or not
     */
    final boolean ambientOcllusion;
    /**
     * represent the pattern size
     */
    final double ambientSize;
    /**
     * represent the ambient occlusion Subdivisions
     */
    final int ambientSubdiv;

    /**
     *
     * @param uniqueID to indentify the material.
     * @param name of the material.
     * @param texture of the material.
     * @param bumpMap normalMap of the Material.
     * @param bumpScale amount of the normalMap displacement.
     * @param ambientOcllusion allows ambient occlusion
     * @param ambientSize pattern size.
     * @param ambientSubdiv ambient occlusion Subdivisions
     */
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

    /**
     * add new or overwrite existing materials to the material list after after loading from file.
     * @param m the material which have to to be added.
     */
    void add2MaterialList(AOMaterial m) {

        ObservableList<AOMaterial> list = AController.materialList;

        if (!list.contains(m)) {
            list.add(m);
        } else {
            if (list.indexOf(m)==6) {
                list.set(6, m);
            }
        }
    }
}
