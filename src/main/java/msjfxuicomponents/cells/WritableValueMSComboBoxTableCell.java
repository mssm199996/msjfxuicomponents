package msjfxuicomponents.cells;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;

public abstract class WritableValueMSComboBoxTableCell<T, S> extends MSComboBoxTableCell<T, S> {

	public WritableValueMSComboBoxTableCell(ObservableList<S> datasource) {
		super(datasource);		
	}

	@Override
	public void setNewValueToEntityAttribute(ObservableValue<S> observableValue, S newValue) {
		if (observableValue instanceof WritableValue) {
			@SuppressWarnings("unchecked")
			WritableValue<S> property = (WritableValue<S>) observableValue;
			property.setValue(newValue);
		}
	}
}
