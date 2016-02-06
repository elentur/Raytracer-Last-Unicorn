package observables.lights;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultLight {

    public static AOLight getDirectionalLight() {
        return new ODirectionalLight();
    }

    public static AOLight getPointLight() {
        return new OPointLight();
    }

    public static AOLight getSpotLight() {
        return new OSpotLight();
    }
}
