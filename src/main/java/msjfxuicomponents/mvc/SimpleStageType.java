package msjfxuicomponents.mvc;

import javafx.fxml.Initializable;

public abstract class SimpleStageType<C extends Initializable> extends StageType<C> {

	private C controller;

	public SimpleStageType(String title, String fxmlFilePath) {
		super(title, fxmlFilePath, null);
	}

	public SimpleStageType(String title, String fxmlFilePath, C controller) {
		super(title, fxmlFilePath, controller);
	}

	public C getController() {
		return controller;
	}

	public void setController(C controller) {
		this.controller = controller;
	}
}
