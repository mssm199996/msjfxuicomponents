package msjfxuicomponents.mvc.mscalculatrice;

import javafx.stage.Modality;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSCalculatriceStage extends SimpleStageType<MSCalculatriceController> {

	public MSCalculatriceStage() {
		super("", "/msjfxuicomponents/mvc/mscalculatrice/MSCalculatrice.fxml");

		this.initModality(Modality.APPLICATION_MODAL);
		this.setMaxWidth(240);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
