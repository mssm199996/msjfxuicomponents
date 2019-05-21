package msjfxuicomponents.rows;

import javafx.scene.control.TableRow;

public abstract class ColoredTableRow<T> extends TableRow<T> {

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			this.setStyle("-fx-background-color: " + this.getHexColor(item) + ";");
		}
	}
	
	public abstract String getHexColor(T item);
}
