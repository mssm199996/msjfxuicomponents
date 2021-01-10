package msjfxuicomponents.cells;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public abstract class OneButtonProgressBarTableCell<T> extends OneButtonProgressTableCell<T>
		implements IProgressBarTableCell {

	protected ProgressBar progressBar;
	private Text text;
	private StackPane container;

	public OneButtonProgressBarTableCell(Image icon, String caption, int waitingTime) {
		super(icon, caption, waitingTime);
	}

	@Override
	protected void constructProgressComponent() {
		this.progressBar = new ProgressBar();
		this.progressBar.setMaxWidth(Double.MAX_VALUE);

		this.text = new Text();

		this.container = new StackPane();
		this.container.getChildren().addAll(this.progressBar, this.text);
		this.container.setAlignment(Pos.CENTER);
		this.container.setPadding(new Insets(0.0, 2.0, 0.0, 2.0));

		HBox.setHgrow(this.progressBar, Priority.ALWAYS);
	}

	@Override
	protected Region getProgressComponent() {
		return this.container;
	}

	@Override
	public ProgressBar getProgressBar() {
		return this.progressBar;
	}

	@Override
	public Text getTextComponent() {
		return this.text;
	}

	@Override
	protected Double getProgressComponentPreferedWidth() {
		return null;
	}

	@Override
	protected Double getProgressComponentPreferedHeight() {
		return 24.0;
	}
}
