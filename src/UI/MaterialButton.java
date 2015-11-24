package UI;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import material.Material;

/**
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class MaterialButton extends Button {
    public MaterialButton(final NewGeoStage st){

        st.material.addListener(a-> refresh(st.material.get()));
    }
    public void refresh(Material mat){
        utils.Color c =  mat.diffuse;
        this.setGraphic(new Rectangle(10,10, new Color(c.r,c.g,c.b,1)));
        this.setText( mat.getClass().getName().split("\\.")[1]);
    }
}
