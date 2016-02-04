package UI;

import camera.OrthographicCamera;
import geometries.Node;
import geometries.Sphere;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.ImageView;
import light.PointLight;
import matVect.Point3;
import matVect.Vector3;
import material.SingleColorMaterial;
import observables.materials.AOMaterial;
import raytracer.Raytracer;
import sampling.LightShadowPattern;
import sampling.SamplingPattern;
import texture.CheckerTexture;
import texture.SingleColorTexture;
import utils.Color;

/**
 * Created by Marcus Baetz on 24.11.2015.
 *
 * @author Marcus Bätz
 */
public class MaterialView extends ImageView {
    private static MaterialView materialView = new MaterialView();
    private final Raytracer matTracer = new Raytracer(false);
    private MaterialView() {

    }
    public static MaterialView getInstance(){return materialView;}

    public void setUpTracer(ObjectProperty<AOMaterial> material) {
        MaterialView that = this;
        matTracer.getWorld().lights.clear();
        matTracer.getWorld().geometries.clear();

        matTracer.setCamera(new OrthographicCamera(new Point3(0, 0, 4), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 2.2, new SamplingPattern(1)));
        matTracer.getWorld().lights.add(new PointLight(new Color(1, 1, 1), new Point3(4, 4, 4), false, 500, new LightShadowPattern(0, 1)));
        matTracer.getWorld().geometries.add(new Node(new Point3(0, 0, 0), new Point3(1, 1, 1), new Point3(0, 0, 0), new Sphere(material.get().generate(), true, true, true, false), true, true, true, false));
        matTracer.getWorld().geometries.add(new Node(new Point3(0, 0, 0), new Point3(10, 10, 10), new Point3(0, 0, 0), new Sphere(
                new SingleColorMaterial(new CheckerTexture(new Color(0, 0, 0), 10, 5, 0, 0, 0), new SingleColorTexture(new Color(0, 0, 0)), 0,false,2,16), true, true, true, false), true, true, true, false));

        material.addListener(a -> {
            if (material.getValue() != null) {
                matTracer.stopRender();
                matTracer.getWorld().geometries.clear();
                matTracer.getWorld().geometries.add(new Node(new Point3(0, 0, 0), new Point3(1, 1, 1), new Point3(0, 0, 0), new Sphere(material.get().generate(), true, true, true, false), true, true, true, false));
                matTracer.getWorld().geometries.add(new Node(new Point3(0, 0, 0), new Point3(10, 10, 10), new Point3(0, 0, 0), new Sphere(
                        new SingleColorMaterial(new CheckerTexture(new Color(0, 0, 0), 10, 5, 0, 0, 0), new SingleColorTexture(new Color(0, 0, 0)), 0,false,2,16), true, true, true, false), true, true, true, false));

                matTracer.render(that);
            }
        });
        matTracer.render(that);
    }
}
