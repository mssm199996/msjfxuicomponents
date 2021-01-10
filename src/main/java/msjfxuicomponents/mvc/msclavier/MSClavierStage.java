package msjfxuicomponents.mvc.msclavier;

import javafx.stage.Modality;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSClavierStage extends SimpleStageType<MSClavierController> {
	public MSClavierStage() {
		super("", "/msjfxuicomponents/mvc/msclavier/MSClavier.fxml");

		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
