package msjfxuicomponents.cells;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

public abstract class DatePickerCell<T> extends TableCell<T, LocalDate> {

	private DatePicker datePicker;

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			createDatePicker();
			setText(null);
			setGraphic(datePicker);
		}
	}

	@Override
	public void commitEdit(LocalDate newValue) {
		super.commitEdit(newValue);

		T produit = this.getTableView().getItems().get(this.getTableRow().getIndex());

		this.onUpdateRequest(produit);
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		LocalDate date = getDate();

		if (date != null)
			setText(getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		else
			setText("");

		setGraphic(null);
	}

	@Override
	public void updateItem(LocalDate item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (datePicker != null) {
					datePicker.setValue(getDate());
				}
				setText(null);
				setGraphic(datePicker);
			} else {
				if (getDate() != null)
					setText(getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				else
					setText("");
				setGraphic(null);
			}
		}
	}

	private void createDatePicker() {
		datePicker = new DatePicker(getDate());
		datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		datePicker.setOnAction((e) -> {
			commitEdit(datePicker.getValue());
		});
	}

	private LocalDate getDate() {
		LocalDate value = this.getTableColumn().getCellData(this.getTableRow().getIndex());
		return (value == null ? null : value);
	}

	public abstract void onUpdateRequest(T entity);
}
