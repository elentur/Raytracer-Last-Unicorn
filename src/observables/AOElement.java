package observables;

import java.util.Observable;

/**
 * Created by roberto on 02/02/16.
 */
public class AOElement extends Observable{

    private String name = null;

    public AOElement(String name) {
        this.name = name;
    }
}
