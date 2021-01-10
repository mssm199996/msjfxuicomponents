package msjfxuicomponents.cells;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import msjfxuicomponents.rows.ColoredRow;

public abstract class OneButtonProgressTableCell<T> extends OneButtonCell<T> implements ColoredRow {

	private Thread task;
	private int waitingTime;

	public OneButtonProgressTableCell(Image icon, String caption, int waitingTime) {
		super(icon, caption);

		this.constructProgressComponent();
		this.waitingTime = waitingTime;
	}

	@Override
	public void onClicked(T row) {
		this.disableButton();

		HBox parent = HBox.class.cast(this.button.getParent());
		parent.getChildren().remove(this.button);
		parent.getChildren().add(this.getProgressComponent());

		this.getProgressComponent().setPadding(new Insets(2, 2, 2, 2));

		if (this.getProgressComponentPreferedWidth() != null) {
			this.getProgressComponent().setMinWidth(this.getProgressComponentPreferedWidth());
			this.getProgressComponent().setMaxWidth(this.getProgressComponentPreferedWidth());
		}

		if (this.getProgressComponentPreferedHeight() != null) {
			this.getProgressComponent().setMinHeight(this.getProgressComponentPreferedHeight());
			this.getProgressComponent().setMaxHeight(this.getProgressComponentPreferedHeight());
		}

		this.addColorfullClass("whiteTableRow", this.getTableRow());

		this.task = (new Thread(() -> {
			if (this.onPerformTask(row)) {
				Platform.runLater(() -> {
					this.addColorfullClass("greenTableRow", this.getTableRow());
				});
			} else {
				Platform.runLater(() -> {
					this.addColorfullClass("redTableRow", this.getTableRow());
				});
			}

			Platform.runLater(() -> {
				parent.getChildren().remove(this.getProgressComponent());
				parent.getChildren().add(this.button);
			});

			synchronized (this.task) {
				try {
					this.task.wait(this.waitingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Platform.runLater(() -> {
				this.enableButton();
			});
		}));

		this.task.start();
	}

	@Override
	public void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);

		if (item != null && !empty && this.getProgressComponent().getParent() != null) {
			this.setGraphic(this.getProgressComponent().getParent());
			this.setText("");
		}

		this.addColorfullClass("whiteTableRow", this.getTableRow());
	}

	@Override
	public String[] getPossibleClasses() {
		return new String[] { "redTableRow", "greenTableRow", "whiteTableRow" };
	}

	protected abstract Double getProgressComponentPreferedWidth();

	protected abstract Double getProgressComponentPreferedHeight();

	protected abstract void constructProgressComponent();

	protected abstract Region getProgressComponent();

	public abstract boolean onPerformTask(T row);
}
