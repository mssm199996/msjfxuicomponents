package msjfxuicomponents.cells;

import javafx.scene.control.TableCell;

public class IndexCell<T> extends TableCell<T, Integer> {
	@Override
	public void updateItem(Integer item, boolean empty) {
		if(empty) {
			setText("");
			setGraphic(null);
		}
		else {
			setText((this.getTableRow().getIndex() + 1) + "");
			setGraphic(null);
		}
	}
}
