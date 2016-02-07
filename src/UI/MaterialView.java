package UI;

import camera.OrthographicCamera;
import geometries.Node;
import geometries.Sphere;
import javafx.scene.image.ImageView;
import light.PointLight;
import matVect.Point3;
import matVect.Transform;
import matVect.Vector3;
import material.SingleColorMaterial;
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
    private static final MaterialView materialView1 = new MaterialView();
    private static final MaterialView materialView2 = new MaterialView();
    private final static Raytracer matTracer = new Raytracer(false);

    private MaterialView() {

    }

    public static MaterialView getInstance1() {
        return materialView1;
    }

    public static MaterialView getInstance2() {
        return materialView2;
    }

    public void setUpTracer() {
        MaterialView that = this;
        matTracer.getWorld().lights.clear();
        matTracer.getWorld().geometries.clear();

        matTracer.setCamera(new OrthographicCamera(new Point3(0, 0, 4), new Vector3(0, 0, -1), new Vector3(0, 1, 0), 2.2, new SamplingPattern(1)));
        matTracer.getWorld().lights.add(new PointLight(new Color(1, 1, 1), new Point3(4, 4, 4), false, 500, new LightShadowPattern(0, 1)));
        matTracer.getWorld().geometries.add(new Node(new Transform(), new Sphere(controller.AController.material.get().generate(), true, true, true, false), true, true, true, false));
        matTracer.getWorld().geometries.add(new Node(new Transform().scale(10, 10, 10), new Sphere(
                new SingleColorMaterial(new CheckerTexture(new Color(0, 0, 0)), new SingleColorTexture(new Color(0, 0, 0)), 0, false, 2, 16), true, true, true, false), true, true, true, false));

        controller.AController.material.addListener(a -> {
            if (controller.AController.material.getValue() != null) {
                matTracer.stopRender();
                matTracer.getWorld().geometries.clear();
                matTracer.getWorld().geometries.add(new Node(new Transform(), new Sphere(controller.AController.material.get().generate(), true, true, true, false), true, true, true, false));
                matTracer.getWorld().geometries.add(new Node(new Transform().scale(10, 10, 10), new Sphere(
                        new SingleColorMaterial(new CheckerTexture(new Color(0, 0, 0)), new SingleColorTexture(new Color(0, 0, 0)), 0, false, 2, 16), true, true, true, false), true, true, true, false));

                matTracer.render(that);
            }
        });
        matTracer.render(that);
    }
}
