package utils;

import camera.Camera;
import com.sun.corba.se.impl.orbutil.ObjectWriter;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import observables.AOElement;

import java.io.Serializable;

/**
 * Combines a World object and a Camera object for saving and loading as a File.
 * Created by Marcus Baetz on 08.11.2015.
 *
 * @author Marcus Bätz
 */
public class Scene implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Represents the AOElments Treeview object of this Scene.
     */
    private final TreeItem<AOElement> tree;

    /**
     * Generates a new Scene object.
     *
     * @param tree  Represents the treeview object of this Scene.
     */
    public Scene(TreeItem<AOElement> tree) {
        this.tree = tree;
    }

    /**
     * @return the TreeView object of this Scene.
     */
    public TreeItem<AOElement> getTreeView() {
        return tree;
    }
}
