package msjfxuicomponents.cells;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;

public abstract class DeleteListCell<T extends ICategorizer> extends ListCell<T> {
	private JFXButton deleteButton;
	private HBox container;
	private Label label;

	public DeleteListCell() { 
		this.constructContainer();
		this.deleteButton.setOnAction(event -> {
			this.doAfterDelete(getListView().getItems().remove(getIndex()));
		});
	}

	public DeleteListCell(String alertTitle, Window mother) {
		this.constructContainer();
		this.deleteButton.setOnAction(event -> {
			if (this.confirmBeforeDelete(alertTitle, mother))
				this.doAfterDelete(getListView().getItems().remove(getIndex()));
		});
	}

	public void constructContainer() {
		this.deleteButton = new JFXButton();
		this.deleteButton.setGraphic(new ImageView(MSLightweightJFXUIComponentsHolder.LITTLE_MINUS_ICON));

		this.label = new Label();

		this.container = new HBox();
		this.container.setAlignment(Pos.CENTER_LEFT);
		this.container.setPadding(new Insets(2.0));
		this.container.setSpacing(5.0);
		this.container.getChildren().addAll(this.deleteButton, this.label);
	}

	public boolean confirmBeforeDelete(String alertTitle, Window mother) {
		return MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(alertTitle,
				"Confirmation de suppression", "Veuillez confirmer pour proceder à la suppression", mother);
	}

	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			this.setText("");
			this.setGraphic(null);
		} else {
			this.setText(null);
			this.setGraphic(this.container);
			this.label.setText(item.getDesignation());
		}
	}

	public abstract void doAfterDelete(T object);

}
