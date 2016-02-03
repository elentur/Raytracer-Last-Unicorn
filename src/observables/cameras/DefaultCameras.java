package observables.cameras;

/**
 * Created by Marcus Baetz on 03.02.2016.
 *
 * @author Marcus Bätz
 */
public class DefaultCameras {
    public static AOCamera getPerspectiveCamera(){
        return new OPerspectiveCamera(
                "Perspective Camera",
                new double[]{0,0,5},
                new double[]{0,0,-1},
                new double[]{0,1,0},
                Math.PI/4,
                new double[]{0,1}
        );
    }

    public static AOCamera getOrthographicCamera(){
        return new OOrthographicCamera(
                "Orthographic Camera",
                new double[]{0,0,5},
                new double[]{0,0,-1},
                new double[]{0,1,0},
                3,
                new double[]{0,1}
        );
    }

    public static AOCamera getDOFCamera(){
        return new ODOFCamera(
                "DOF Camera",
                new double[]{0,0,5},
                new double[]{0,0,-1},
                new double[]{0,1,0},
                Math.PI/4,
                new double[]{0,1},
                new double[]{3,8},
                5
        );
    }
}
