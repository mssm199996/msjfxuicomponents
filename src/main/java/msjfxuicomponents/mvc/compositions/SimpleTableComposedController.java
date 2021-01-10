package msjfxuicomponents.mvc.compositions;

import javafx.scene.control.TableView;

public interface SimpleTableComposedController<T> extends ComposedController {

	public abstract void setTableView(TableView<T> tableView);
}
