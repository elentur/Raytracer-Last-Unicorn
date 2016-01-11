package controller;

/**
 * Created by Marcus Baetz on 11.01.2016.
 *
 * @author Marcus Bätz
 */
public class ObservableElementLists {
    private static ObservableElementLists ourInstance = new ObservableElementLists();

    public static ObservableElementLists getInstance() {
        return ourInstance;
    }

    private ObservableElementLists() {
    }
}
