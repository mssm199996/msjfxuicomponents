package msjfxuicomponents.cells;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;

public abstract class MSComboBoxTableCell<T, S> extends ComboBoxTableCell<T, S> {

	public MSComboBoxTableCell(ObservableList<S> datasource) {
		super(datasource);
	}

	@Override
	public void commitEdit(S newValue) {
		TableColumn<T, S> column = getTableColumn();
		ObservableValue<S> observableValue = column.getCellObservableValue(this.getIndex());

		T element = column.getTableView().getItems().get(this.getIndex());

		this.setNewValueToEntityAttribute(observableValue, newValue);
		this.onElementChanged(element, newValue);

		super.commitEdit(newValue);
	}

	@Override
	public void updateItem(S item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			this.setText("");
			this.setGraphic(null);
		}
	}

	public abstract void onElementChanged(T row, S newValue);

	public abstract void setNewValueToEntityAttribute(ObservableValue<S> observableValue, S newValue);
}
