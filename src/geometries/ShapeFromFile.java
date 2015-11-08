package geometries;


import matVect.Normal3;
import matVect.Point3;
import utils.Color;
import utils.Hit;
import utils.Ray;

import java.io.BufferedReader;
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

    private final List<Geometry> triangles;
    private final List<Point3> v;
    private final List<Normal3> vn;
    private final List<Normal3> vt;
    private final List<String> f;
    public ShapeFromFile(final String path, final Color color) {
        super(color);
        triangles= new ArrayList<>();
        v= new ArrayList<>();
        vn= new ArrayList<>();
        vt= new ArrayList<>();
        f= new ArrayList<>();
        List<Point3> points = new ArrayList<>();
        if(readFile(path)){
            try {
                for(String s: f){
                        String[] fs = s.split("\\s+");
                    //F�r einfaches f
                    if(fs[0].matches("^\\d+(\\.\\d+)?") && fs.length ==3) {
                        final int p1 = Integer.parseInt(fs[0]) - 1;
                        final int p2 = Integer.parseInt(fs[1]) - 1;
                        final int p3 = Integer.parseInt(fs[2]) - 1;
                        Triangle tri = new Triangle(v.get(p1), v.get(p2), v.get(p3),color);
                        triangles.add(tri);
                    }

                }

            }catch(Exception e){
                System.out.println("Fehler in der Datein" + points.size());
            }
        }




    }

    @Override
    public Hit hit(Ray r) {
        Hit h =null;
        for (Geometry t : triangles){
            Hit hit = t.hit(r);
            if(h == null ||( hit != null &&  h.t > hit.t)) h = hit;
        }
        return h;
    }

    /**
     * Reads an Wavefront obj File and converts it into a group of triangles.
     * @param source The path of the obj-File
     * @return returns true if the reading progress happened without any failure
     */
    private boolean readFile(String source) {
        Path path = Paths.get(source);
        BufferedReader br;
        try {
            br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;

            while ((line=br.readLine()) != null) {
                if(line.matches("v\\s+.*")) {
                    line = line.substring(1).trim();
                    String[] vs = line.split("\\s+");
                    final double x = Double.parseDouble(vs[0]);
                    final double y = Double.parseDouble(vs[1]);
                    final double z = Double.parseDouble(vs[2]);
                    v.add(new Point3(x, y, z));
                }else if(line.matches("vn\\s+.*")) {
                    line = line.substring(2).trim();
                    String[] vs = line.split("\\s+");
                    final double x = Double.parseDouble(vs[0]);
                    final double y = Double.parseDouble(vs[1]);
                    final double z = Double.parseDouble(vs[2]);
                    vn.add(new Normal3(x, y, z));
                }else if(line.matches("vt\\s+.*")) {
                    line = line.substring(2).trim();
                    String[] vs = line.split("\\s+");
                    final double x = Double.parseDouble(vs[0]);
                    final double y = Double.parseDouble(vs[1]);
                    final double z;
                    if(vs.length==3) z = Double.parseDouble(vs[2]);
                    else z=0.0;
                    vt.add(new Normal3(x, y, z));
                }else if(line.matches("f\\s+.*")) {
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
