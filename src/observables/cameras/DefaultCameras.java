package observables.cameras;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultCameras {
    /**
     *
     * @return a default perspective camera
     */
    public static AOCamera getPerspectiveCamera() {
        return new OPerspectiveCamera();
    }
    /**
     *
     * @return a default orthographic camera
     */
    public static AOCamera getOrthographicCamera() {
        return new OOrthographicCamera();
    }
    /**
     *
     * @return a default dept of field  camera
     */
    public static AOCamera getDOFCamera() {
        return new ODOFCamera();
    }
}
