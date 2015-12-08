package geometries;


import UI.Dialog;
import matVect.Normal3;
import matVect.Point3;
import material.Material;
import raytracer.ImageSaver;
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
    private final List<Normal3> vt;
    private final List<String> f;
    private final Octree octree;
    private int rekDeep = 10;

    public ShapeFromFile(final File path, final Material material) {
        super(material);
        this.file = path;
        name = nameTest(path.getName().split("\\.")[0]);
        triangles = new ArrayList<>();
        v = new ArrayList<>();
        vn = new ArrayList<>();
        vt = new ArrayList<>();
        f = new ArrayList<>();
        List<Point3> points = new ArrayList<>();
        if (readFile(path.toString())) {
            try {
                for (String s : f) {
                    String[] fs = s.split("\\s+");
                    //F�r einfaches f
                    if (fs[0].matches("^\\d+(\\.\\d+)?") && fs.length == 3) {
                        final int p1 = Integer.parseInt(fs[0]) - 1;
                        final int p2 = Integer.parseInt(fs[1]) - 1;
                        final int p3 = Integer.parseInt(fs[2]) - 1;
                        Triangle tri = new Triangle(v.get(p1), v.get(p2), v.get(p3), material,0,0,0);
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
                        if (n[0] != -1) {
                            Triangle tri = new Triangle(v.get(p[0]), v.get(p[1]), v.get(p[2]),
                                    vn.get(n[0]), vn.get(n[1]), vn.get(n[2]),
                                    material,0,0,0);
                            triangles.add(tri);
                        } else {
                            Triangle tri = new Triangle(v.get(p[0]), v.get(p[1]), v.get(p[2]),
                                    material,0,0,0);
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
        octree = new Octree(triangles);
        // System.out.println(ImageSaver.fTriangle.size());

    }

    @Override
    public Hit hit(Ray r) {
       /* Hit h = null;
        if(octree !=null){
            if(octree.box.hit(r) == null) return null;
        }
        for (Geometry t : triangles) {
            Hit hit = t.hit(r);
            if (h == null || (hit != null && h.t > hit.t)) h = hit;
        }
        return h;*/
        return octree.hit(r);
    }

    private String nameTest(String n) {
        int index = 1;
        boolean run = false;
        for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
            if (g.name.equals(n)) run = true;
        }
        while (run) {
            int i = index;
            for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                if (g.name == n + index) index++;
            }
            if (i == index) {
                run = false;
                return n + index;
            }
        }

        return n;
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
                    final double z;
                    if (vs.length == 3) z = Double.parseDouble(vs[2]);
                    else z = 0.0;
                    vt.add(new Normal3(x, y, z));
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
