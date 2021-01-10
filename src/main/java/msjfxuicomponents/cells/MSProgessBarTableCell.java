package msjfxuicomponents.cells;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MSProgessBarTableCell<T> extends TableCell<T, Double> implements IProgressBarTableCell {

	private ProgressBar progressBar;
	private Text text;

	public MSProgessBarTableCell() {
		this.progressBar = new ProgressBar();
		this.progressBar.setMaxWidth(Double.MAX_VALUE);

		this.text = new Text();

		StackPane container = new StackPane();
		container.getChildren().addAll(this.progressBar, this.text);
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(0.0, 2.0, 0.0, 2.0));
	}

	@Override
	protected void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);

		this.onDrawProgress(item, empty);
	}

	@Override
	public ProgressBar getProgressBar() {
		return this.progressBar;
	}

	@Override
	public Text getTextComponent() {
		return this.text;
	}
}