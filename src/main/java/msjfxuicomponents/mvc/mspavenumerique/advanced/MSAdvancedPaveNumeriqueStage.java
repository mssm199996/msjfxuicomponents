package msjfxuicomponents.mvc.mspavenumerique.advanced;

import javafx.stage.Window;
import msjfxuicomponents.mvc.mspavenumerique.MSPaveNumeriqueStage;

public class MSAdvancedPaveNumeriqueStage extends MSPaveNumeriqueStage<MSAdvancedPaveNumeriqueController> {

	public MSAdvancedPaveNumeriqueStage(Window mother) {
		super(mother, "Pavé numérique", "/msjfxuicomponents/mvc/mspavenumerique/advanced/MSAdvancedPaveNumerique.fxml");
	}

	public void setTarget(Double target) {
		this.getController().setTarget(target);
	}
}
