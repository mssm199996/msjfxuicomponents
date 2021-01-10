package msjfxuicomponents.mvc;

import javafx.fxml.Initializable;

public abstract class SimpleStageType<C extends Initializable> extends StageType<C> {
	
	public SimpleStageType(String title, String fxmlFilePath) {
		super(title, fxmlFilePath, null);
	}

	public SimpleStageType(String title, String fxmlFilePath, C controller) {
		super(title, fxmlFilePath, controller);
	}
}
