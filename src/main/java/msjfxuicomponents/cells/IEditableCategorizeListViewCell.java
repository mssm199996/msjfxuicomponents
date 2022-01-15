package msjfxuicomponents.cells;

import javafx.scene.control.ListView;
import javafx.util.StringConverter;
import msdatabaseutils.ICategorizer;

public abstract class IEditableCategorizeListViewCell<T extends ICategorizer> extends ICategorizerListViewCell<T> {

    public IEditableCategorizeListViewCell(ListView<T> listview) {
        super(listview);

        this.setEditable(true);
        this.setConverter(new StringConverter<T>() {

            @Override
            public String toString(T object) {
                return object.getDesignation();
            }

            @Override
            public T fromString(String string) {
                T element = listview.getSelectionModel().getSelectedItem();
                element.setDesignation(string);
                onUpdate(element);

                return element;
            }
        });
    }

    public abstract void onUpdate(T oldValue);
}
