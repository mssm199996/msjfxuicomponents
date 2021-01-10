package msjfxuicomponents.uicomponents;

import javafx.scene.Node;

public class MSCounterBootstrapCard extends MSBootstrapCard {

	private MSCounter counter;

	@Override
	public Node getBottomRightMoseComponent() {
		if (this.counter == null) {
			this.counter = new MSCounter();
			this.counter.getPlus().setOnAction(e -> {
				if (this.getOnCardActionTriggered() != null)
					this.getOnCardActionTriggered().run();
			});
			this.counter.getMinus().setOnAction(e -> {
				if (this.getOnCardActionTriggered() != null)
					this.getOnCardActionTriggered().run();
			});
		}

		return this.counter;
	}

	@Override
	protected boolean isAvailable() {
		return this.counter.getCount() != 0;
	}

	public Integer getCount() {
		return this.counter.getCount();
	}

	public MSCounter getCounter() {
		return this.counter;
	}
}
