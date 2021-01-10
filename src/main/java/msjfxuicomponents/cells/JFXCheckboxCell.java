package msjfxuicomponents.cells;

import com.jfoenix.controls.JFXCheckBox;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public abstract class JFXCheckboxCell<T> extends TableCell<T, Boolean> {
	protected JFXCheckBox isChecked = new JFXCheckBox();

	public JFXCheckboxCell() {
		HBox hbox = new HBox();
		hbox.getChildren().addAll(this.isChecked);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(5.0);

		this.initCheckBoxes();
	}

	private void initCheckBoxes() {
		this.isChecked.setOnAction(event -> {
			boolean newValue = this.isChecked.isSelected();

			T row = (T) this.getTableView().getItems().get(this.getIndex());

			this.onBeforeUpdateRow(row, newValue);
			this.onUpdateRow(row, newValue);

			(new Thread(() -> {
				this.onUpdateEntity(row);
			})).start();
		});
	}

	@Override
	public void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);

		if (item == null || empty) {
			setGraphic(null);
			setText("");
		} else {
			this.isChecked.setSelected(this.getItem());
			this.setGraphic(this.isChecked.getParent());
			this.setText("");
		}
	}

	public abstract void onUpdateRow(T row, Boolean newValue);

	public abstract void onUpdateEntity(T entity);

	public void onBeforeUpdateRow(T row, Boolean newValue) {

	}
}
