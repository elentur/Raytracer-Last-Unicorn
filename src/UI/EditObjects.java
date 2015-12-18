package UI;

import camera.Camera;
import geometries.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import light.DirectionalLight;
import light.Light;
import light.PointLight;
import light.SpotLight;
import raytracer.ImageSaver;
import texture.TexCoord2;
import utils.Element;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class EditObjects extends Stage {

    public EditObjects() {
        super();
        final HBox bottom = new HBox(20);
        final HBox top = new HBox(20);
        final ListView<Element> center = new ListView<>();
        fillCenter(center);

        final Label lblInfo = new Label("Objects");

        final Button btnDublicate = new Button("Dublicate");
        btnDublicate.setPrefWidth(70);
        btnDublicate.setOnAction(a -> onDublicate(center));
        btnDublicate.disableProperty().bind(center.getSelectionModel().selectedIndexProperty().lessThan(0));
        final Button btnEdit = new Button("Edit");
        btnEdit.setPrefWidth(70);
        btnEdit.setOnAction(a -> onEdit(center));
        btnEdit.disableProperty().bind(center.getSelectionModel().selectedIndexProperty().lessThan(0));
        final Button btnDelete = new Button("Delete");
        btnDelete.setPrefWidth(70);
        btnDelete.setOnAction(a -> onDelete(center));
        btnDelete.disableProperty().bind(center.getSelectionModel().selectedIndexProperty().lessThan(0));
        final Button btnClose = new Button("Close");
        btnClose.setPrefWidth(70);
        btnClose.setOnAction(a -> onClose());

        top.getChildren().addAll(lblInfo);
        bottom.getChildren().addAll(btnDublicate, btnEdit, btnDelete, btnClose);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        borderPane.setPadding(new Insets(20));
        Scene scene = new Scene(borderPane, 380, 400);
        scene.getStylesheets().add("css/rootStyle.css");
        this.setTitle("ObjectFinder?");
        this.setScene(scene);
        this.initModality(Modality.WINDOW_MODAL);
        this.show();
    }

    private void onDublicate(ListView<Element> l) {
        ObservableList<Element> sel = l.getSelectionModel().getSelectedItems();
        for (Element e : sel) {
            if (!(e instanceof Camera)) {
                if (e instanceof Plane) {
                    Plane p1 = (Plane) e;
                    Plane p = new Plane(p1.material,true,true);
                    p.name = p1.name;
                    p = (Plane) nameTest(p);

                    ImageSaver.raytracer.getWorld().geometries.add(p);
                    //  l.getItems().add(p);
                } else if (e instanceof Sphere) {
                    Sphere s1 = (Sphere) e;
                    Sphere s = new Sphere(s1.material,true,true);
                    s.name = s1.name;
                    s = (Sphere) nameTest(s);
                    ImageSaver.raytracer.getWorld().geometries.add(s);
                    //  l.getItems().add(s);
                } else if (e instanceof AxisAlignedBox) {
                    AxisAlignedBox b1 = (AxisAlignedBox) e;
                    AxisAlignedBox b = new AxisAlignedBox(b1.material,true,true);
                    b.name = b1.name;
                    b = (AxisAlignedBox) nameTest(b);
                    ImageSaver.raytracer.getWorld().geometries.add(b);
                    //  l.getItems().add(b);
                } else if (e instanceof Triangle) {
                    Triangle t1 = (Triangle) e;
                    Triangle t = new Triangle(t1.a, t1.b, t1.c, t1.material,new TexCoord2(1,1),new TexCoord2(1,1),new TexCoord2(1,1),true,true);
                    t.name = t1.name;
                    t = (Triangle) nameTest(t);
                    ImageSaver.raytracer.getWorld().geometries.add(t);
                    // l.getItems().add(t);
                } else if (e instanceof PointLight) {
                    PointLight pl = (PointLight) e;
                    PointLight p = new PointLight(pl.color, pl.position,pl.castsShadow);
                    p.name = pl.name;
                    p = (PointLight) nameTest(p);
                    ImageSaver.raytracer.getWorld().lights.add(p);
                    // l.getItems().add(t);
                } else if (e instanceof DirectionalLight) {
                    DirectionalLight pl = (DirectionalLight) e;
                    DirectionalLight p = new DirectionalLight(pl.color, pl.direction,pl.castsShadow);
                    p.name = pl.name;
                    p = (DirectionalLight) nameTest(p);
                    ImageSaver.raytracer.getWorld().lights.add(p);
                    // l.getItems().add(t);
                } else if (e instanceof SpotLight) {
                    SpotLight pl = (SpotLight) e;
                    SpotLight p = new SpotLight(pl.color, pl.position, pl.direction, pl.halfAngle,pl.castsShadow);
                    p.name = pl.name;
                    p = (SpotLight) nameTest(p);
                    ImageSaver.raytracer.getWorld().lights.add(p);
                    // l.getItems().add(t);
                }
            }

        }
        refreshList(l);
    }

    private void refreshList(ListView<Element> l) {
        ObservableList<Element> ol = l.getItems();
        l.setItems(null);
        ol.clear();
        ol.addAll(ImageSaver.raytracer.getWorld().geometries);
        ol.add(ImageSaver.raytracer.getCamera());
        ol.addAll(ImageSaver.raytracer.getWorld().lights);
        l.setItems(ol);
        for (int i = 0; i < l.getItems().size(); i++) {
            l.getSelectionModel().select(i);
        }
        l.getSelectionModel().clearSelection();

    }

    private Element nameTest(Element p) {
        int index = 1;
        boolean run = false;
        if (p instanceof Geometry) {
            Geometry g1 = (Geometry) p;
            for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                if (g.name.equals(p.name)) run = true;
            }
            while (run) {
                int i = index;
                for (Geometry g : ImageSaver.raytracer.getWorld().geometries) {
                    if (g.name == p.name + index) index++;
                }
                if (i == index) {
                    run = false;
                    g1.name = p.name + index;
                }
            }
            return g1;
        } else {
            Light l1 = (Light) p;
            for (Light l : ImageSaver.raytracer.getWorld().lights) {
                if (l.name.equals(p.name)) run = true;
            }
            while (run) {
                int i = index;
                for (Light l : ImageSaver.raytracer.getWorld().lights) {
                    if (l.name == p.name + index) index++;
                }
                if (i == index) {
                    run = false;
                    l1.name = p.name + index;
                }
            }
            return l1;
        }

    }

    private void fillCenter(ListView<Element> t) {
        ObservableList<Element> list = FXCollections.observableArrayList();
        t.setItems(list);
        t.setCellFactory(new Callback<ListView<Element>, ListCell<Element>>() {
            @Override
            public ListCell<Element> call(ListView<Element> param) {
                return new ListCell<Element>() {
                    String text = "";

                    @Override
                    public void updateSelected(boolean selected) {
                        super.updateSelected(selected);

                        if (this.getItem() != null) {
                            String name = "";
                            name = this.getItem().name;
                            if (selected) {
                                text = (name + "\n\t" + this.getItem().toString());
                            } else {
                                text = (name);
                            }
                        }
                        updateItem(this.getItem(), false);
                    }

                    @Override
                    protected void updateItem(Element item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (text == "") {
                                String name = "";
                                name = this.getItem().name;
                                text = name;
                            }
                            setText(text);
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        if (ImageSaver.raytracer.getWorld() != null) {
            list.addAll(ImageSaver.raytracer.getWorld().geometries);
        }
        if (ImageSaver.raytracer.getCamera() != null) list.add(ImageSaver.raytracer.getCamera());
        if (ImageSaver.raytracer.getWorld() != null) {
            list.addAll(ImageSaver.raytracer.getWorld().lights);
        }
    }

    private void onDelete(ListView<Element> t) {
        ObservableList<Element> sel = t.getSelectionModel().getSelectedItems();
        for (Element e : sel) {
            if (e instanceof Camera) ImageSaver.raytracer.setCamera(null);
            else if (e instanceof Geometry) ImageSaver.raytracer.getWorld().geometries.remove(e);
            else ImageSaver.raytracer.getWorld().lights.remove(e);
        }
        refreshList(t);

    }


    private void onClose() {
        this.close();
    }

    private void onEdit(ListView<Element> t) {
        ObservableList<Element> sel = t.getSelectionModel().getSelectedItems();
        Element e = sel.get(0);
        if (e instanceof Camera) new NewCameraStage((Camera) e);
        else if (e instanceof Sphere) new NewSphereStage((Sphere) e);
        else if (e instanceof Plane) new NewPlaneStage((Plane) e);
        else if (e instanceof AxisAlignedBox) new NewCubeStage((AxisAlignedBox) e);
        else if (e instanceof Triangle) new NewTriangleStage((Triangle) e);
        else if (e instanceof ShapeFromFile) new NewOBJStage((ShapeFromFile) e);
        else if (e instanceof Light) new NewLightStage((Light) e);
        refreshList(t);


    }
}
