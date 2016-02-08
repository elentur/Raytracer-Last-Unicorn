package observables.lights;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultLight {
    /**
     * @return a default ODirectionalLight
     */
    public static AOLight getDirectionalLight() {
        return new ODirectionalLight();
    }

    /**
     * @return a default OPointLight
     */
    public static AOLight getPointLight() {
        return new OPointLight();
    }

    /**
     * @return a default OSpotLight
     */
    public static AOLight getSpotLight() {
        return new OSpotLight();
    }
}
