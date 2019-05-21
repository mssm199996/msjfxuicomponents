package msjfxuicomponents.mvc.msjrxmleditor;

import java.util.Collection;

import javafx.stage.Modality;
import msjfxuicomponents.mvc.SimpleStageType;
import msjfxuicomponents.others.MSJRXMLDocument;

public class MSJRXMLEditorStage extends SimpleStageType<MSJRXMLEditorController> {

	public MSJRXMLEditorStage(String stageTitle, Collection<MSJRXMLDocument> documents) {
		super(stageTitle, "/msjfxuicomponents/mvc/msjrxmleditor/MSJRXMLEditor.fxml");

		this.initModality(Modality.APPLICATION_MODAL);
		this.getController().setDocuments(documents);
		this.setMaximized(true);
	}
}
