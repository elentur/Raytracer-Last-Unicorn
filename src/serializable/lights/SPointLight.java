package serializable.lights;

import observables.lights.OPointLight;

/**
 * Created by roberto on 05.02.16.
 */
public class SPointLight extends SLight {

    private static final long serialVersionUID = 1L;
    private final double px;
    private final double py;
    private final double pz;
    public SPointLight(final double red, final double green, final double blue,
                             final double px, final double py, final double pz,
                             final boolean castShadows, final int photons,
                             final double size, final int subdiv, final String name) {
        super(red,green,blue,castShadows,photons,size,subdiv,name);
        this.px =px;
        this.py =py;
        this.pz =pz;
    }


    @Override
    public OPointLight generate(){
        OPointLight light = new OPointLight();

        light.color.set(new javafx.scene.paint.Color(red,green,blue,1));
        light.castShadow.set(castShadows);
        light.photons.set(photons);
        light.patternSize.set(size);
        light.patternSubdiv.set(subdiv);
        light.px.set(px);
        light.py.set(py);
        light.pz.set(pz);
        light.name.set(name);

        return light;
    }
}
