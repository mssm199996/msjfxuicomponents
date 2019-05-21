package msjfxuicomponents.mvc.msactivation;

import MainPackage.CopyrightHandler;
import javafx.stage.Modality;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSActivationStage extends SimpleStageType<MSActivationController> {

	public MSActivationStage(String title, CopyrightHandler copyrightHandler) {
		super(title, "/msjfxuicomponents/mvc/msactivation/MSActivation.fxml");

		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.getController().setCopyrightHandler(copyrightHandler);

		MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE = this;
	}
}
