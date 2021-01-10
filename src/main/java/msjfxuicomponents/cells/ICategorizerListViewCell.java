package msjfxuicomponents.cells;

import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import msdatabaseutils.ICategorizer;

public class ICategorizerListViewCell<T extends ICategorizer> extends TextFieldListCell<T> {

	public ICategorizerListViewCell(ListView<T> listview) { 
		this.setConverter(new StringConverter<T>() {

			@Override
			public String toString(T object) {
				return object.getDesignation();
			}

			@Override
			public T fromString(String string) {
				return listview.getSelectionModel().getSelectedItem();
			}
		});
	}
}
