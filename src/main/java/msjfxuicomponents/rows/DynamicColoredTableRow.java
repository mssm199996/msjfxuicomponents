package msjfxuicomponents.rows;

import javafx.scene.control.TableView;
import msjfxuicomponents.others.IColoredEntity;

public class DynamicColoredTableRow<T extends IColoredEntity> extends ColoredTableRow<T> {
	
	public DynamicColoredTableRow(TableView<T> tableView) {
	}
	
	@Override
	public String getHexColor(T item) {
		if (item != null && item.getHexColor() != null && !item.getHexColor().equals(""))
			return item.getHexColor();

		return this.getDefaultHexColor();
	}

	public String getDefaultHexColor() {
		return "rgb(255, 255, 255)";
	}
}
