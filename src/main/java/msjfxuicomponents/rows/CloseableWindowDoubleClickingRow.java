package msjfxuicomponents.rows;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CloseableWindowDoubleClickingRow<T> extends DoubleClickingRow<T>{
	
	private SimpleBooleanProperty openedForSelection;
	private SimpleObjectProperty<T> lastSelectedElementHolder;
	
	public CloseableWindowDoubleClickingRow(SimpleBooleanProperty closeController, SimpleObjectProperty<T> lastSelectedElementHolder) {
		this.openedForSelection = closeController;
		this.lastSelectedElementHolder = lastSelectedElementHolder;
	}
	
	@Override
	public void onMouseDoubleClicked(T object) {
		if(this.openedForSelection.getValue()) {			
			this.lastSelectedElementHolder.setValue(object);;
			this.getTableView().getScene().getWindow().hide();
		
			this.openedForSelection.setValue(false);			
		}
	}
}
