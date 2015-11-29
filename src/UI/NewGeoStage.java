package UI;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import material.LambertMaterial;
import material.Material;
import utils.Color;

/**
 * Created by Marcus Baetz on 23.11.2015.
 *
 * @author Marcus Bätz
 */
public class NewGeoStage extends Stage {

    public ObjectProperty<Material> material =new SimpleObjectProperty<>(this, "materialProperty",new LambertMaterial(new Color(0.5, 0.5,0.5)));

}
