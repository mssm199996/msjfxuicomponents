package msjfxuicomponents.rows;

import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

public abstract class DoubleClickingRow<T> extends TableRow<T> {

	public DoubleClickingRow() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					double x1 = 0;
					double y1 = 0;

					double x2 = x1 + getWidth();
					double y2 = x2 + getHeight();

					double x = event.getX();
					double y = event.getY();

					if (x <= x2 && x >= x && y < y2 && y > y1){
						T value = getTableView().getItems().get(getIndex());

						onMouseDoubleClicked(value);
					}
				}
			}
		});
	}

	@Override
	protected void updateItem(T object, boolean empty) {
		super.updateItem(object, empty);
	}

	public abstract void onMouseDoubleClicked(T object);
}
