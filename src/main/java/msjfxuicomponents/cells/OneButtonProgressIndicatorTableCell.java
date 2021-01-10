package msjfxuicomponents.cells;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public abstract class OneButtonProgressIndicatorTableCell<T> extends OneButtonProgressTableCell<T> {

	protected ProgressIndicator progressIndicator;

	public OneButtonProgressIndicatorTableCell(Image icon, String caption, int waitingTime) {
		super(icon, caption, waitingTime);
	}

	@Override
	protected void constructProgressComponent() {
		this.progressIndicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		this.progressIndicator.getStyleClass().add("black-progress-indicator");
	}

	@Override
	protected Region getProgressComponent() {
		return this.progressIndicator;
	}

	@Override
	protected Double getProgressComponentPreferedWidth() {
		return 24.0;
	}

	@Override
	protected Double getProgressComponentPreferedHeight() {
		return 24.0;
	}
}
