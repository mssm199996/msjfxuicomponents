/**
 * 
 */
package msjfxuicomponents.mvc.msjrviewer;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;

import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import msjfxuicomponents.mvc.msjrviewer.MSJRViewerStage.MSJRViewerFxMode;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.print.JRPrinterAWT;

@SuppressWarnings("deprecation")
public class MSJRViewerController implements Initializable {

	private MSJRViewerFxMode printMode;
	private ChangeListener<Number> zoomListener;
	private JasperPrint jasperPrint;

	@FXML
	private ImageView resultViewer;

	@FXML
	private StackPane resultHolder;

	@FXML
	private ScrollPane resultScroller;

	@FXML
	private JFXComboBox<Integer> pageList;

	@FXML
	private JFXSlider zoomLevel;

	private FileChooser fileChooser = new FileChooser();
	private Double zoomFactor;
	private double imageHeight;
	private double imageWidth;
	private boolean isFirstAppearance = true;

	public void show() {
		if (this.isFirstAppearance) {
			this.resultHolder.minWidthProperty().bind(this.resultScroller.widthProperty());
			this.resultHolder.minHeightProperty().bind(this.resultScroller.heightProperty());

			this.isFirstAppearance = false;
		}

		if (this.printMode == MSJRViewerFxMode.REPORT_VIEW) {
			this.zoomFactor = 1.5;
			this.zoomLevel.setValue(150);

			this.imageHeight = this.jasperPrint.getPageHeight();
			this.imageWidth = this.jasperPrint.getPageWidth();

			List<Integer> pagesIndexes = new ArrayList<Integer>(this.jasperPrint.getPages().size());

			for (int i = 0; i < this.jasperPrint.getPages().size(); i++)
				pagesIndexes.add(i);

			this.pageList.getItems().clear();
			this.pageList.getItems().addAll(pagesIndexes);
			this.pageList.getSelectionModel().clearAndSelect(0);
		} else if (this.printMode == MSJRViewerFxMode.REPORT_PRINT) {
			this.print();
		}
	}

	@FXML
	public void save() {
		LocalDateTime now = LocalDateTime.now();
		Stage window = (Stage) this.resultViewer.getScene().getWindow();
		String reportFileName = now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh-mm-ss", Locale.FRANCE));
		reportFileName = "Rapport du " + reportFileName;

		this.fileChooser.setInitialFileName(reportFileName);
		this.fileChooser.setTitle(this.getWindow().getTitle());

		File file = this.fileChooser.showSaveDialog(window);

		if (this.fileChooser.getSelectedExtensionFilter() != null
				&& this.fileChooser.getSelectedExtensionFilter().getExtensions() != null) {
			List<String> selectedExtensions = this.fileChooser.getSelectedExtensionFilter().getExtensions();

			try {
				if (selectedExtensions.contains("*.pdf")) {
					JRPdfExporter exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
					exporter.exportReport();
				} else if (selectedExtensions.contains("*.docx")) {
					JRDocxExporter exporter = new JRDocxExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
					exporter.exportReport();
				} else if (selectedExtensions.contains("*.xlsx")) {
					JRXlsxExporter exporter = new JRXlsxExporter();
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, this.jasperPrint);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
					exporter.exportReport();
				}
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void openWithMSWord() throws JRException {
		File file = new File("temp.docx");

		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
		exporter.exportReport();

		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();

			this.showErrorAlert("Ouverture avec Microsoft Word",
					"Microsoft word n'est pas installé dans cette machine !");
		}
	}

	@FXML
	public void openWithPdfReader() throws JRException {
		File file = new File("temp.pdf");

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
		exporter.exportReport();

		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();

			this.showErrorAlert("Ouverture en PDF",
					"Aucune application de lecture d'un fichier pdf n'est disponible dans cette machine !");
		}
	}

	@FXML
	public void openWithMSExcel() throws JRException {
		File file = new File("temp.xlsx");

		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, this.jasperPrint);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
		exporter.exportReport();

		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();

			this.showErrorAlert("Ouverture avec Microsoft Excel",
					"Microsoft excel n'est pas installé dans cette machine !");
		}
	}

	@FXML
	private void print() {
		try {
			JasperPrintManager.printReport(this.jasperPrint, true);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		this.initSliders();
		this.initComboBoxes();
		this.initFileChoosers();
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public MSJRViewerFxMode getPrintMode() {
		return printMode;
	}

	public void setPrintMode(MSJRViewerFxMode printMode) {
		this.printMode = printMode;
	}

	private void viewPage(int pageNumber) throws JRException, IOException {
		this.resultViewer.setFitHeight(this.imageHeight * this.zoomFactor);
		this.resultViewer.setFitWidth(this.imageWidth * this.zoomFactor);
		this.resultViewer.setImage(this.getImage(pageNumber));
	}

	private Image getImage(int pageNumber) throws JRException, IOException {
		return SwingFXUtils.toFXImage((BufferedImage) JRPrinterAWT.printPageToImage(this.jasperPrint, pageNumber, 2),
				null);
	}

	// ---------------------------------------------------------------

	private void initComboBoxes() {
		this.pageList.setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer object) {
				return "Page n° " + object;
			}

			@Override
			public Integer fromString(String string) {
				return pageList.getValue();
			}
		});

		this.pageList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() != -1)
				try {
					this.viewPage(newValue.intValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}

	private void initSliders() {
		this.zoomListener = (observable, oldValue, newValue) -> {
			this.zoomFactor = newValue.doubleValue() / 100;

			this.resultViewer.setFitHeight(this.imageHeight * this.zoomFactor);
			this.resultViewer.setFitWidth(this.imageWidth * this.zoomFactor);
		};

		this.zoomLevel.valueProperty().addListener(this.zoomListener);
	}

	private void initFileChoosers() {
		this.fileChooser.getExtensionFilters()
				.add(new ExtensionFilter("PDF Document", Arrays.asList("*.pdf", "*.PDF")));
		this.fileChooser.getExtensionFilters()
				.add(new ExtensionFilter("DOCX Document", Arrays.asList("*.docx", "*.DOCX")));
		this.fileChooser.getExtensionFilters()
				.add(new ExtensionFilter("XLSX Document", Arrays.asList("*.xlsx", "*.XLSX")));
	}

	private Stage getWindow() {
		return (Stage) this.resultViewer.getScene().getWindow();
	}

	private void showErrorAlert(String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(this.getWindow().getTitle());
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.initOwner(this.getWindow());
		alert.showAndWait();
	}
}
