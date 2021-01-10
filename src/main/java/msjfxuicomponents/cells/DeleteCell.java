package msjfxuicomponents.cells;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;

public abstract class DeleteCell<T> extends TableCell<T, Boolean> {

	private JFXButton deleteButton;

	public DeleteCell() {
		this.deleteButton = new JFXButton();
		this.deleteButton.setGraphic(new ImageView(MSLightweightJFXUIComponentsHolder.LITTLE_MINUS_ICON));
		this.deleteButton.setOnAction(event -> {
			this.doAfterDelete(getTableView().getItems().remove(getIndex()));
		});
	}

	public DeleteCell(String alertTitle, Window mother) {
		this.deleteButton = new JFXButton();
		this.deleteButton.setGraphic(new ImageView(MSLightweightJFXUIComponentsHolder.LITTLE_MINUS_ICON));
		this.deleteButton.setOnAction(event -> {
			if (this.confirmBeforeDelete(alertTitle, mother))
				this.doAfterDelete(getTableView().getItems().remove(getIndex()));
		});
	}

	public abstract void doAfterDelete(T object);

	public boolean confirmBeforeDelete(String alertTitle, Window mother) {
		return MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(alertTitle,
				"Confirmation de suppression", "Veuillez confirmer pour proceder à la suppression", mother);
	}

	@Override
	public void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText("");
			setGraphic(null);
		} else {
			this.deleteButton.setDisable(!item);

			setText("");
			setGraphic(this.deleteButton);
		}
	}
}
