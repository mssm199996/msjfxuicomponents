package msjfxuicomponents.cells;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public abstract class OneButtonCell<T> extends TableCell<T, Boolean> {
	protected JFXButton button = new JFXButton();
	protected boolean buttonDisabled = false;

	public OneButtonCell(Image image, String text) {
		HBox hbox = new HBox();
		hbox.getChildren().addAll(this.button);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(5.0);

		this.initButton(image, text);
	}

	private void initButton(Image image, String text) {
		if (image != null) {
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(17.0);
			imageView.setFitWidth(17.0);

			this.button.setGraphic(imageView);
			this.button.setDisable(this.buttonDisabled);
		}

		this.button.setText(text);
		this.button.setOnAction(event -> {
			T row = (T) this.getTableView().getItems().get(this.getIndex());

			if (!this.buttonDisabled) {
				this.onClicked(row);

				(new Thread(() -> {
					this.onAfterClicked(row);
				})).start();
			}
		});
	}

	@Override
	public void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);

		if (item == null || empty) {
			setGraphic(null);
			setText("");
		} else {
			this.button.setDisable(!item || this.buttonDisabled);
			this.setGraphic(this.button.getParent());
			this.setText("");
		}
	}

	public abstract void onClicked(T row);

	public abstract void onAfterClicked(T entity);

	public void disableButton() {
		this.button.setDisable(true);
		this.buttonDisabled = true;
	}

	public void enableButton() {
		this.button.setDisable(false);
		this.buttonDisabled = false;
	}
}
