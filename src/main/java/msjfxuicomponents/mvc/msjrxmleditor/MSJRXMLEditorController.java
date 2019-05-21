package msjfxuicomponents.mvc.msjrxmleditor;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.cells.OneButtonCell;
import msjfxuicomponents.others.MSJRXMLDocument;

public class MSJRXMLEditorController implements Initializable {
	public static Image MODIFY_ICON, RECOVER_ICON;

	@FXML
	private TableView<MSJRXMLDocument> documentsTable;

	@FXML
	private TableColumn<MSJRXMLDocument, Integer> indexColumn;

	@FXML
	private TableColumn<MSJRXMLDocument, String> nameColumn;

	@FXML
	private TableColumn<MSJRXMLDocument, String> pathColumn;

	@FXML
	private TableColumn<MSJRXMLDocument, Boolean> modifyColumn;

	@FXML
	private TableColumn<MSJRXMLDocument, Boolean> recoverColumn;

	private Collection<MSJRXMLDocument> documents;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initVariables();
		this.initTables();
	}

	private void initVariables() {
		MSJRXMLEditorController.MODIFY_ICON = new Image(
				getClass().getResource("/msjfxuicomponents/mvc/icons/modifier.png").toString());
		MSJRXMLEditorController.RECOVER_ICON = new Image(
				getClass().getResource("/msjfxuicomponents/mvc/icons/reset.png").toString());

		Platform.runLater(() -> {
			this.documentsTable.getItems().addAll(this.documents);
		});
	}

	private void initTables() {
		this.indexColumn.setCellFactory(call -> {
			return new IndexCell<>();
		});

		this.nameColumn.setCellValueFactory(call -> {
			return call.getValue().nameProperty();
		});

		this.pathColumn.setCellValueFactory(call -> {
			return call.getValue().printablePathProperty();
		});

		this.modifyColumn.setCellValueFactory(call -> {
			return new SimpleBooleanProperty(true);
		});
		this.modifyColumn.setCellFactory(call -> {
			return new OneButtonCell<MSJRXMLDocument>(MSJRXMLEditorController.MODIFY_ICON, "Modifier") {
				@Override
				public void onClicked(MSJRXMLDocument row) {
					try {
						Desktop.getDesktop().open(new File(row.getPath()));
					} catch (IOException e) {
						e.printStackTrace();

						Alert alert = new Alert(AlertType.ERROR);
						alert.initOwner(getWindow());
						alert.setTitle(getWindow().getTitle());
						alert.setHeaderText("Modification du document: " + row.getName());
						alert.setContentText(
								"Aucune application n'est installé pour la modification de fichier .jrxml, .jasper ... veuillez d'abords installer Jaspersoft studio ou iReport !");
						alert.showAndWait();
					}
				}

				@Override
				public void onAfterClicked(MSJRXMLDocument entity) {
				}
			};
		});
		this.recoverColumn.setCellValueFactory(call -> {
			return new SimpleBooleanProperty(true);
		});
		this.recoverColumn.setCellFactory(call -> {
			return new OneButtonCell<MSJRXMLDocument>(MSJRXMLEditorController.RECOVER_ICON, "Rétablir") {

				@Override
				public void onClicked(MSJRXMLDocument row) {
					try {
						Files.copy(Paths.get(row.getRecoveryPath()), Files.newOutputStream(Paths.get(row.getPath())));
					} catch (AccessDeniedException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.initOwner(getWindow());
						alert.setTitle(getWindow().getTitle());
						alert.setHeaderText("Rétablissement du document: " + row.getName());
						alert.setContentText(
								"Le document est ouvert par une autre application. Veuillez d'abords fermer l'autre application et réessayer !");
						alert.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onAfterClicked(MSJRXMLDocument entity) {
				}
			};
		});
	}

	public void setDocuments(Collection<MSJRXMLDocument> documents) {
		this.documents = documents;
	}

	public Stage getWindow() {
		return (Stage) this.documentsTable.getScene().getWindow();
	}
}
