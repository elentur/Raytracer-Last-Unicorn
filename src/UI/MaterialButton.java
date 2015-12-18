package UI;

import camera.PerspectiveCamera;
import geometries.Sphere;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import light.PointLight;
import matVect.Point3;
import matVect.Vector3;
import material.Material;
import raytracer.Raytracer;

/**
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class MaterialButton extends Button {
    private final ImageView img;
    private final Raytracer matTracer = new Raytracer(false);

    public MaterialButton(final NewGeoStage st) {
        img = new ImageView();
        setUpTracer(st);
        st.material.addListener(a -> refresh(st.material.get()));

    }

    public void refresh(Material mat) {
        this.setGraphic(img);
        this.setText(mat.getClass().getName().split("\\.")[1]);
        this.setContentDisplay(ContentDisplay.TOP);
    }

    private void setUpTracer(NewGeoStage st) {
       // matTracer.setWorld(new World(new utils.Color(0, 0, 0), new utils.Color(0, 0, 0)));
        matTracer.setCamera(new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4));
        matTracer.getWorld().lights.add(new PointLight(new utils.Color(1, 1, 1), new Point3(0.5, 0.5, 0),false));
        matTracer.getWorld().backImg = new Image("img/matBack.png", 80, 80, false, false, false);
        matTracer.getWorld().geometries.add(new Sphere(st.material.get(),true,true));
        st.material.addListener(a -> {
            matTracer.getWorld().geometries.clear();
            matTracer.getWorld().geometries.add(new Sphere(st.material.get(),true,true));
            matTracer.render(img);
        });
        matTracer.render(img);

    }
}
