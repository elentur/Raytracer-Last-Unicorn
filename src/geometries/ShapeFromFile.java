package geometries;


import UI.Dialog;
import matVect.Normal3;
import matVect.Point3;
import material.Material;
import texture.TexCoord2;
import utils.Hit;
import utils.Octree;
import utils.Ray;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcus Baetz on 22.10.2015.
 *
 * @author Marcus Baetz
 */
public class ShapeFromFile extends Geometry {
    public final File file;
    public final List<Geometry> triangles;
    private final List<Point3> v;
    private final List<Normal3> vn;
    private final List<TexCoord2> vt;
    private final List<String> f;
    private final Octree octree;

    public ShapeFromFile(final File path, final Material material, final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        super(material,reciveShadows,castShadows,visibility,castShadows);
        this.file = path;
        triangles = new ArrayList<>();
        v = new ArrayList<>();
        vn = new ArrayList<>();
        vt = new ArrayList<>();
        f = new ArrayList<>();
        loadFile();
        octree = new Octree(triangles);
    }

    protected void loadFile(){

        List<Point3> points = new ArrayList<>();
        if (readFile(file.getPath())) {
            try {
                for (String s : f) {
                    String[] fs = s.split("\\s+");
                    //F�r einfaches f
                    if (fs[0].matches("^\\d+(\\.\\d+)?") && fs.length == 3) {
                        final int p1 = Integer.parseInt(fs[0]) - 1;
                        final int p2 = Integer.parseInt(fs[1]) - 1;
                        final int p3 = Integer.parseInt(fs[2]) - 1;
                        Triangle tri = new Triangle(v.get(p1), v.get(p2), v.get(p3), material,new TexCoord2(1,1),new TexCoord2(1,1),new TexCoord2(1,1),reciveShadows,castShadows,visibility,castShadows);
                        triangles.add(tri);
                    } //F�r f / v/vt
                    else {
                        int[] p = new int[fs.length];
                        int[] t = new int[fs.length];
                        int[] n = new int[fs.length];
                        for (int i = 0; i < fs.length; i++) {
                            String[] ft = fs[i].split("/");
                            try {
                                p[i] = Integer.parseInt(ft[0]) - 1;
                                t[i] = !ft[1].equals("") ? Integer.parseInt(ft[1]) - 1 : -1;
                                n[i] = ft.length == 3 ? Integer.parseInt(ft[2]) - 1 : -1;
                            } catch (NumberFormatException e) {
                                System.out.println("Fehler");
                            }
                        }
                        if (n[0] != -1 && t[0] != -1) {
                            Triangle tri = new Triangle(v.get(p[0]), v.get(p[1]), v.get(p[2]),
                                    vn.get(n[0]), vn.get(n[1]), vn.get(n[2]),
                                    material,vt.get(t[0]),vt.get(t[1]),vt.get(t[2]),reciveShadows,castShadows,visibility,false);
                            triangles.add(tri);
                        } else if (n[0] != -1) {
                            Triangle tri = new Triangle(v.get(p[0]), v.get(p[1]), v.get(p[2]),
                                    material,new TexCoord2(0,1),new TexCoord2(1,1),new TexCoord2(1,0),reciveShadows,castShadows,visibility,false);
                            triangles.add(tri);
                        }else{
                            Normal3 normal = v.get(p[0]).sub(v.get(p[1])).x(v.get(p[2]).sub(v.get(p[1]))).normalized().asNormal().mul(-1);
                            Triangle tri = new Triangle(v.get(p[0]), v.get(p[1]), v.get(p[2]),
                                    normal, normal, normal,
                                    material,vt.get(t[0]),vt.get(t[1]),vt.get(t[2]),reciveShadows,castShadows,visibility,false);
                            triangles.add(tri);
                        }
                    }
                }

            } catch (Exception e) {
                Dialog dlg = new Dialog("Failure in File!");

                dlg.setNewText("The File is not a true Wavefront obj or corrupted.");
                dlg.showAndWait();
            }

        }

        // System.out.println(ImageSaver.fTriangle.size());
    }


    @Override
    public Hit hit(Ray r) {
        return octree.hit(r);
    }



    /**
     * Reads an Wavefront obj File and converts it into a group of triangles.
     *
     * @param source The path of the obj-File
     * @return returns true if the reading progress happened without any failure
     */
    private boolean readFile(String source) {
        Path path = Paths.get(source);
        BufferedReader br;
        try {
            br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;

            while ((line = br.readLine()) != null) {
                if (line.matches("v\\s+.*")) {
                    line = line.substring(1).trim();
                    String[] vs = line.split("\\s+");
                    final double x = Double.parseDouble(vs[0]);
                    final double y = Double.parseDouble(vs[1]);
                    final double z = Double.parseDouble(vs[2]);
                    v.add(new Point3(x, y, z));
                } else if (line.matches("vn\\s+.*")) {
                    line = line.substring(2).trim();
                    String[] vs = line.split("\\s+");
                    final double x = Double.parseDouble(vs[0]);
                    final double y = Double.parseDouble(vs[1]);
                    final double z = Double.parseDouble(vs[2]);
                    vn.add(new Normal3(x, y, z));
                } else if (line.matches("vt\\s+.*")) {
                    line = line.substring(2).trim();
                    String[] vs = line.split("\\s+");
                    final double x = Double.parseDouble(vs[0]);
                    final double y = Double.parseDouble(vs[1]);
                    vt.add(new TexCoord2(x, y));
                } else if (line.matches("f\\s+.*")) {
                    line = line.substring(1).trim();
                    f.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Fehler in der Datein");
            return false;
        }
        return true;
    }

}
