package msjfxuicomponents.mvc;

import javafx.fxml.Initializable;
import msjfxuicomponents.MSJFXUIComponentsHolder;

public class MainApplicationStage<C extends Initializable> extends SimpleStageType<C> {

	public MainApplicationStage(String title, String fxmlFilePath) {
		super(title, fxmlFilePath);

		MSJFXUIComponentsHolder.MAIN_APPLICATION_STAGE = this;
	}
}
