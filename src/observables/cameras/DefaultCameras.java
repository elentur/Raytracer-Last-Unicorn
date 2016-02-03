package observables.cameras;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultCameras {
    public static AOCamera getPerspectiveCamera(){
        return new OPerspectiveCamera();
    }

    public static AOCamera getOrthographicCamera(){
        return new OOrthographicCamera();
    }

    public static AOCamera getDOFCamera(){
        return new ODOFCamera();
    }
}
