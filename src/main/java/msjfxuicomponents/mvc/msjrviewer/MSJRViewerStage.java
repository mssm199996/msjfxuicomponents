package msjfxuicomponents.mvc.msjrviewer;

import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;
import net.sf.jasperreports.engine.JasperPrint;

public class MSJRViewerStage extends SimpleStageType<MSJRViewerController> {

	public MSJRViewerStage(String stageTitle) {
		super(stageTitle, "/msjfxuicomponents/mvc/msjrviewer/MSJRViewer.fxml");

		this.setMaximized(true);

		MSJFXUIComponentsHolder.MS_JR_VIEWER_STAGE = this;
	}

	public void previewDocument(JasperPrint jasperPrint, MSJRViewerFxMode printMode) {
		this.getController().setJasperPrint(jasperPrint);
		this.getController().setPrintMode(printMode);
		this.getController().show();

		this.show();
		this.toFront();
	}

	public static enum MSJRViewerFxMode {
		REPORT_PRINT, REPORT_VIEW
	}
}
