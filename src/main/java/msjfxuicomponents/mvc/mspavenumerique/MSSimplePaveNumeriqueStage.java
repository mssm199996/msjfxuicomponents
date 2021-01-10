package msjfxuicomponents.mvc.mspavenumerique;

import javafx.stage.Window;

public class MSSimplePaveNumeriqueStage extends MSPaveNumeriqueStage<MSPaveNumeriqueController> {

	public MSSimplePaveNumeriqueStage(Window mother) {
		super(mother, "Pavé numérique", "/msjfxuicomponents/mvc/mspavenumerique/MSPaveNumerique.fxml");
	}
}
