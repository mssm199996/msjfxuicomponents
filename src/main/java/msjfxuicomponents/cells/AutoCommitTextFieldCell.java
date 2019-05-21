package msjfxuicomponents.cells;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public abstract class AutoCommitTextFieldCell<T, S> extends TextFieldTableCell<T, S> {

	private boolean isListenerAdded = false;
	private boolean isLastPressedKeyEscape = false;
	private boolean isLastPressedKeyEnter = false;
	private boolean isLastPressedKeyTab = false;

	public AutoCommitTextFieldCell(StringConverter<S> converter) {
		super(converter);
	}

	@Override
	public void updateItem(S item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			this.setText("");
			this.setGraphic(null);
		}
	}

	@Override
	public void startEdit() {
		if (this.getItem() == null)
			return;

		super.startEdit();

		if (!this.isListenerAdded) {
			TextField textField = (TextField) this.getGraphic();
			textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent t) {
					isLastPressedKeyEscape = false;
					isLastPressedKeyEnter = false;
					isLastPressedKeyTab = false;

					if (t.getCode() == KeyCode.ENTER) {
						isLastPressedKeyEnter = true;
					} else if (t.getCode() == KeyCode.ESCAPE) {
						isLastPressedKeyEscape = true;

						cancelEdit();
					} else if (t.getCode() == KeyCode.TAB) {
						isLastPressedKeyTab = true;

						commitEdit(fromStringToS(textField.getText()));

						TableColumn<T, ?> nextColumn = getNextColumn(!t.isShiftDown());

						if (nextColumn != null) {
							getTableView().edit(getTableRow().getIndex(), nextColumn);
						}
					}
				}
			});

			textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue && !isLastPressedKeyEscape && !isLastPressedKeyEnter && !isLastPressedKeyTab) {
						commitEdit(fromStringToS(textField.getText()));
					}

					isLastPressedKeyEnter = false;
					isLastPressedKeyEscape = false;
					isLastPressedKeyTab = false;
				}
			});

			this.isListenerAdded = true;
		}

	}

	public abstract S fromStringToS(String entityRepresentation);

	public abstract void setNewValueToEntityAttribute(ObservableValue<S> observableValue, S newValue);

	@Override
	public void commitEdit(S newValue) {
		TableColumn<T, S> column = getTableColumn();
		ObservableValue<S> observableValue = column.getCellObservableValue(this.getIndex());

		this.setNewValueToEntityAttribute(observableValue, newValue);
		this.onEditCommit(newValue);

		super.commitEdit(newValue);
	}

	public abstract void onEditCommit(S newValue);

	private TableColumn<T, ?> getNextColumn(boolean forward) {
		List<TableColumn<T, ?>> columns = new ArrayList<>();

		for (TableColumn<T, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		if (columns.size() < 2) {
			return null;
		}
		int currentIndex = columns.indexOf(getTableColumn());
		int nextIndex = currentIndex;
		if (forward) {
			nextIndex++;
			if (nextIndex > columns.size() - 1) {
				nextIndex = 0;
			}
		} else {
			nextIndex--;
			if (nextIndex < 0) {
				nextIndex = columns.size() - 1;
			}
		}
		return columns.get(nextIndex);
	}

	private List<TableColumn<T, ?>> getLeaves(TableColumn<T, ?> root) {
		List<TableColumn<T, ?>> columns = new ArrayList<>();
		if (root.getColumns().isEmpty()) {
			if (root.isEditable()) {
				columns.add(root);
			}
			return columns;
		} else {
			for (TableColumn<T, ?> column : root.getColumns()) {
				columns.addAll(getLeaves(column));
			}
			return columns;
		}
	}

	public T getEntity() {
		return this.getTableView().getItems().get(this.getTableRow().getIndex());
	}
}
