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
import texture.CheckerTexture;
import texture.SingleColorTexture;
import utils.Color;

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
        matTracer.setCamera(new PerspectiveCamera(new Point3(0, 0, 4), new Vector3(0, 0, -1), new Vector3(0, 1, 0), Math.PI / 4));
        matTracer.getWorld().lights.add(new PointLight(new utils.Color(1,1, 1), new Point3(4, 4, 4),false,500));
        matTracer.getWorld().geometries.add(new Node(new Transform(),new Sphere(st.material.get(),true,true,true,false),true,true,true,false));
        matTracer.getWorld().geometries.add(new Node(new Transform().scale(500,500,500),new Sphere(
                new SingleColorMaterial(new CheckerTexture(new Color(0,0,0),20,10,0,0), new SingleColorTexture(new Color(0,0,0)),0),true,true,true,false),true,true,true,false));

        st.material.addListener(a -> {
            matTracer.getWorld().geometries.clear();
            matTracer.getWorld().geometries.add(new Node(new Transform(),new Sphere(st.material.get(),true,true,true,false),true,true,true,false));
            matTracer.getWorld().geometries.add(new Node(new Transform().scale(500,500,500),new Sphere(
                    new SingleColorMaterial(new CheckerTexture(new Color(0,0,0),20,10,0,0), new SingleColorTexture(new Color(0,0,0)),0),true,true,true,false),true,true,true,false));

            matTracer.render(this);
        });
        matTracer.render(this);

    }
}
