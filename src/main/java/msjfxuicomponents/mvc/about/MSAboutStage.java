package msjfxuicomponents.mvc.about;

import javafx.fxml.Initializable;
import javafx.stage.Modality;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSAboutStage extends SimpleStageType<Initializable> {

	public MSAboutStage() {
		super("À propos", "/msjfxuicomponents/mvc/about/MSAbout.fxml");

		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
