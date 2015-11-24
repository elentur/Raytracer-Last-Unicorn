package UI;

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

    public Material material = new LambertMaterial(new Color(0.5,0.5,0.5));
}
