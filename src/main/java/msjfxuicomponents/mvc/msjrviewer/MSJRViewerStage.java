package msjfxuicomponents.mvc.msjrviewer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.IPrintableDocument;
import msjfxuicomponents.mvc.SimpleStageType;
import msjfxuicomponents.mvc.mslogin.MSLoginController;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("deprecation")
public class MSJRViewerStage extends SimpleStageType<MSJRViewerController> {

	public MSJRViewerStage(String stageTitle) {
		super(stageTitle, "/msjfxuicomponents/mvc/msjrviewer/MSJRViewer.fxml");

		this.setMaximized(true);

		MSJFXUIComponentsHolder.MS_JR_VIEWER_STAGE = this;
	}

	public void previewDocument(JasperPrint jasperPrint, MSJRViewerFxMode printMode) {
		if (MSLoginController.MAIN_CONFIGURATION.isAdvancedPrinting() != null
				&& MSLoginController.MAIN_CONFIGURATION.isAdvancedPrinting()) {
			this.getController().setJasperPrint(jasperPrint);
			this.getController().setPrintMode(printMode);
			this.getController().show();

			this.show();
			this.toFront();
		} else {
			JasperViewer jasperViewer = new JasperViewer(DefaultJasperReportsContext.getInstance(), jasperPrint, false,
					Locale.FRANCE, null);
			jasperViewer.setVisible(true);
			jasperViewer.toFront();
		}
	}

	public <PrintDoc extends IPrintableDocument> void generatePdfWithMultipleDocuments(Collection<PrintDoc> documents,
			Path documentTypePath, Consumer<SimpleEntry<PrintDoc, Map<String, Object>>> fillCallback, Path outputFile) {

		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>(documents.size());

		for (PrintDoc printableDocument : documents) {
			Map<String, Object> params = new HashMap<>();

			fillCallback.accept(new SimpleEntry<>(printableDocument, params));

			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(documentTypePath.toAbsolutePath().toString(),
						params, new JREmptyDataSource());

				jasperPrintList.add(jasperPrint);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}

		try {
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Files.newOutputStream(outputFile));
			exporter.exportReport();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	public <PrintDoc extends IPrintableDocument> void printWithMultipleDocuments(Collection<PrintDoc> documents,
			Path documentTypePath, Consumer<SimpleEntry<PrintDoc, Map<String, Object>>> fillCallback,
			MSJRViewerFxMode jrFxMode) {

		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>(documents.size());

		for (PrintDoc printableDocument : documents) {
			Map<String, Object> params = new HashMap<>();

			fillCallback.accept(new SimpleEntry<>(printableDocument, params));

			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(documentTypePath.toAbsolutePath().toString(),
						params, new JREmptyDataSource());

				jasperPrintList.add(jasperPrint);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}

		if (!jasperPrintList.isEmpty()) {
			JasperPrint firstReport = jasperPrintList.get(0);

			for (int i = 1; i < jasperPrintList.size(); i++) {
				JasperPrint nextReport = jasperPrintList.get(i);

				firstReport.getPages().addAll(nextReport.getPages());
			}

			this.previewDocument(firstReport, jrFxMode);
		}
	}

	public static enum MSJRViewerFxMode {
		REPORT_PRINT, REPORT_VIEW
	}
}
