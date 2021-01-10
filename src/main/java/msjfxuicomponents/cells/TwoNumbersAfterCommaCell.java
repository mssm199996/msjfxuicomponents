package msjfxuicomponents.cells;

import javafx.scene.control.TableCell;

public class TwoNumbersAfterCommaCell<T> extends TableCell<T, Double>{
	@Override
	public void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} 
		else {
			setText(String.format("%.2f", item));
		}
	}
}
