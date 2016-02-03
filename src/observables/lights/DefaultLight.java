package observables.lights;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultLight {

    public static AOLight getDirectionalLight(){
        return new ODirectionalLight(
                "Directional Light",
                new double[]{1.0,1.0,1.0},
                true,
                500,
                new double[]{0.0,1.0},
                new double[]{0.0,0.0,-1.0}
        );
    }
    public static AOLight getPointLight(){
        return new OPointLight(
                "Point Light",
                new double[]{1.0,1.0,1.0},
                new double[]{5.0,5.0,5.0},
                true,
                500,
                new double[]{0.0,1.0}
        );
    }
    public static AOLight getSpotLight(){
        return new OSpotLight(
                "Spot Light",
                new double[]{1.0,1.0,1.0},
                true,
                500,
                new double[]{0.0,1.0},
                new double[]{5.0,5.0,5.0},
                new double[]{-1.0,-1.0,-1.0},
                Math.PI/14
        );
    }
}
