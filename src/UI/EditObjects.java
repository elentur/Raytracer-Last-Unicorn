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
import raytracer.ImageSaver;
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
        this.setTitle("Create new Camera?");
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
                    Plane p = new Plane(p1.a, p1.n, p1.material);
                    p.name = p1.name;
                    p = (Plane) nameTest(p);

                    ImageSaver.getWorld().geometries.add(p);
                    //  l.getItems().add(p);
                } else if (e instanceof Sphere) {
                    Sphere s1 = (Sphere) e;
                    Sphere s = new Sphere(s1.c, s1.r, s1.material);
                    s.name = s1.name;
                    s = (Sphere) nameTest(s);
                    ImageSaver.getWorld().geometries.add(s);
                    //  l.getItems().add(s);
                } else if (e instanceof AxisAlignedBox) {
                    AxisAlignedBox b1 = (AxisAlignedBox) e;
                    AxisAlignedBox b = new AxisAlignedBox(b1.run, b1.lbf, b1.material);
                    b.name = b1.name;
                    b = (AxisAlignedBox) nameTest(b);
                    ImageSaver.getWorld().geometries.add(b);
                    //  l.getItems().add(b);
                } else if (e instanceof Triangle) {
                    Triangle t1 = (Triangle) e;
                    Triangle t = new Triangle(t1.a, t1.b, t1.c, t1.material);
                    t.name = t1.name;
                    t = (Triangle) nameTest(t);
                    ImageSaver.getWorld().geometries.add(t);
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
        ol.addAll(ImageSaver.getWorld().geometries);
        ol.add(ImageSaver.getCamera());
        l.setItems(ol);
        for (int i = 0; i < l.getItems().size(); i++) {
            l.getSelectionModel().select(i);
        }
        l.getSelectionModel().clearSelection();

    }

    private Geometry nameTest(Geometry p) {
        int index = 1;
        boolean run = false;
        Geometry g1 = p;
        for (Geometry g : ImageSaver.getWorld().geometries) {
            if (g.name.equals(p.name)) run = true;
        }
        while (run) {
            int i = index;
            for (Geometry g : ImageSaver.getWorld().geometries) {
                if (g.name == p.name + index) index++;
            }
            if (i == index) {
                run = false;
                g1.name = p.name + index;
            }
        }
        return g1;
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
                            if (this.getItem() instanceof Geometry) name = ((Geometry) this.getItem()).name;
                            else name = ((Camera) this.getItem()).name;
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
                                if (this.getItem() instanceof Geometry) name = ((Geometry) this.getItem()).name;
                                else name = ((Camera) this.getItem()).name;
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
        if (ImageSaver.getWorld() != null) {
            list.addAll(ImageSaver.getWorld().geometries);
        }
        if (ImageSaver.getCamera() != null) list.add(ImageSaver.getCamera());
    }

    private void onDelete(ListView<Element> t) {
        ObservableList<Element> sel = t.getSelectionModel().getSelectedItems();
        for (Element e : sel) {
            if (e instanceof Camera) ImageSaver.setCamera(null);
            else ImageSaver.getWorld().geometries.remove(e);
            //  t.getItems().remove(e);
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
        refreshList(t);


    }
}
