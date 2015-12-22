package UI;

import camera.PerspectiveCamera;
import geometries.Node;
import geometries.Sphere;
import javafx.scene.image.ImageView;
import light.PointLight;
import matVect.Point3;
import matVect.Transform;
import matVect.Vector3;
import material.SingleColorMaterial;
import raytracer.Raytracer;
import texture.InterpolatedImageTexture;

/**
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class MaterialView extends ImageView {
    private final Raytracer matTracer = new Raytracer(false);

    public MaterialView(final NewGeoStage st) {
        setUpTracer(st);
      //  st.material.addListener(a -> refresh(st.material.get()));

    }


    private void setUpTracer(NewGeoStage st) {
       // matTracer.setWorld(new World(new utils.Color(0, 0, 0), new utils.Color(0, 0, 0)));
        matTracer.setCamera(new PerspectiveCamera(new Point3(0, 0, 0), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4));
        matTracer.getWorld().lights.add(new PointLight(new utils.Color(1, 1, 1), new Point3(0.5, 0.5, 0),false,500));

        matTracer.getWorld().geometries.add(new Node(new Transform(),new Sphere(st.material.get(),true,true,true,true),true,true,true,true));
        matTracer.getWorld().geometries.add(new Node(new Transform().scale(500,500,500),new Sphere(
                new SingleColorMaterial(new CheckerTexture()),true,true,true,true),true,true,true,true));

        st.material.addListener(a -> {
            matTracer.getWorld().geometries.clear();
            matTracer.getWorld().geometries.add(new Sphere(st.material.get(),true,true,true,true));
            matTracer.render(this);
        });
        matTracer.render(this);

    }
}
