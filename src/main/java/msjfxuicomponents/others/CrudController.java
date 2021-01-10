package msjfxuicomponents.others;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.AutoCommitStringTextFieldCell;
import msjfxuicomponents.cells.IndexCell;
import mssoftutils.others.SearchController;

public abstract class CrudController<T> extends SearchController<T> implements Initializable {

	public CrudController() {
		super();
	}

	public CrudController(ObservableList<T> datasource) {
		super(datasource);
	}

	@FXML
	protected TableView<T> tablePrincipale;

	@FXML
	protected TableColumn<T, Integer> indexColumn;

	protected Callback<TableColumn<T, String>, TableCell<T, String>> textCellUpdater = new Callback<TableColumn<T, String>, TableCell<T, String>>() {

		@Override
		public TableCell<T, String> call(TableColumn<T, String> param) {
			return new AutoCommitStringTextFieldCell<T>() {
				@Override
				public void onEditCommit(String newValue) {
					T entity = tablePrincipale.getItems().get(getIndex());

					if (entity != null) {
						onUpdateCallback(entity);
					}
				}
			};
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTables();
		this.search();
	}

	protected void initTables() {
		if (!this.isCustomTable()) {
			this.tablePrincipale.setItems(this.getDatasource());

			this.indexColumn.setCellFactory(call -> {
				return new IndexCell<>();
			});
		}
	}

	@FXML
	protected void addEntity() {
		if (this.onBeforeInsertCallback()) {
			this.getInsertStage().showAndWait();

			T entity = this.getLastInsertedEntity();

			if (entity != null) {
				this.tablePrincipale.getItems().add(entity);
				this.tablePrincipale.getSelectionModel().selectLast();
			}
		}
	}

	@FXML
	private void deleteEntity() {
		T entity = this.tablePrincipale.getSelectionModel().getSelectedItem();

		if (entity != null) {
			boolean confirmed = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(
					this.getNomGestion(), this.getDeleteHeader(), "Voulez vous vraiment procéder à la suppression ?",
					this.getWindow());

			if (confirmed) {

				(new Thread(() -> {
					this.onDeleteCallback(entity);
				})).start();

				this.tablePrincipale.getItems().remove(entity);
			}
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getDeleteHeader(),
					"Veuillez réessayer en spécifiant un élement du tableau !", this.getWindow());
	}

	@FXML
	private void updateEntity() {
		T entity = this.tablePrincipale.getSelectionModel().getSelectedItem();

		if (entity != null) {
			this.onBeforeUpdateCallback(entity);

			(new Thread(() -> {
				this.onUpdateCallback(entity);
			})).start();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
					"Tentative de mise à jour", "Veuillez réessayer en spécifiant un élement du tableau !",
					this.getWindow());
	}

	public abstract Stage getInsertStage();

	public abstract T getLastInsertedEntity();

	public abstract String getNomGestion();

	public abstract String getDeleteHeader();

	public abstract void onDeleteCallback(T entity);

	public abstract void onBeforeUpdateCallback(T entity);

	public abstract void onUpdateCallback(T entity);

	public Window getWindow() {
		return this.tablePrincipale.getScene().getWindow();
	}

	public boolean isCustomTable() {
		return false;
	}

	public void setTableView(TableView<T> tableView) {
		this.tablePrincipale = tableView;
		this.tablePrincipale.setItems(this.getDatasource());
	}

	public boolean onBeforeInsertCallback() {
		return true;
	}
}
