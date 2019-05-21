package msjfxuicomponents.mvc.listecategorizers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.AutoCommitStringTextFieldCell;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.mvc.KeyStageEvent;
import msjfxuicomponents.mvc.ResetController;
import mssoftutils.others.SearchController;

public abstract class ListeCategorizersController<C extends ICategorizer> extends SearchController<C>
		implements Initializable, ResetController, KeyStageEvent {

	public ListeCategorizersController(ObservableList<C> dataSource) {
		super(dataSource);
	}

	@FXML
	private JFXTextField nomCategorizer;

	@FXML
	private TableView<C> tableCategorizers;

	@FXML
	private JFXButton addButton;

	@FXML
	private JFXButton deleteButton;

	@FXML
	private TableColumn<C, Integer> indexColumn;

	@FXML
	private TableColumn<C, String> nomColumn;

	@FXML
	@Override
	public void search() {
		super.search();
	}

	@FXML
	void deleteCategorizer() {
		C categorizer = this.tableCategorizers.getSelectionModel().getSelectedItem();

		if (categorizer != null) {
			if (MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
					this.getHeaderSuppression(), "Veuillez confirmer pour proceder à la suppression",
					this.getWindow())) {

				(new Thread(() -> {
					this.onDeleteRequest(categorizer);
				})).start();

				this.getDatasource().remove(categorizer);
			}
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
					this.getHeaderSuppression(), this.getElementNotSelectedErrorMessage(), this.getWindow());
	}

	@FXML
	void addCategorizer() {
		this.callCategorizerAdder();
	}

	@Override
	public void onShowingResetResult() {
		this.search();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initButtons();
		this.initTables();
		this.initTextFields();
	}

	private void initTextFields() {
		this.nomCategorizer.setPromptText(this.getTextSearchByNomTextField());
	}

	private void initButtons() {
		this.addButton.setText(this.getTextAddButton());
		this.deleteButton.setText(this.getTextDeleteButton());
	}

	private void initTables() {
		this.tableCategorizers.setItems(this.getDatasource());

		this.indexColumn.setCellFactory(event -> {
			return new IndexCell<>();
		});

		this.nomColumn.setText(this.getTextNomColumn());
		this.nomColumn.setCellValueFactory(call -> {
			return call.getValue().designationProperty();
		});
		this.nomColumn.setCellFactory(call -> {
			return new AutoCommitStringTextFieldCell<C>() {
				@Override
				public void onEditCommit(String newValue) {
					C row = this.getTableView().getItems().get(this.getIndex());

					onUpdateRequest(row);
				}
			};
		});
	}

	@Override
	protected List<C> selectFromDatabase() throws NullPointerException {
		String nom = this.nomCategorizer.getText();

		return this.getSearchedCategorizers(nom);
	}

	protected abstract String getNomGestion();

	protected abstract String getTextAddButton();

	protected abstract String getTextDeleteButton();

	protected abstract String getTextSearchByNomTextField();

	protected abstract String getTextNomColumn();

	protected abstract String getHeaderSuppression();

	protected abstract String getElementNotSelectedErrorMessage();

	protected abstract void onDeleteRequest(C categorizer);

	protected abstract void onUpdateRequest(C categorizer);

	protected abstract void callCategorizerAdder();

	protected abstract List<C> getSearchedCategorizers(String nom);

	protected Window getWindow() {
		return this.tableCategorizers.getScene().getWindow();
	}

	@Override
	public void onKeyReleasedStageEvent(KeyEvent event) {
		KeyCode code = event.getCode();

		switch (code) {
		case F1:
			this.addCategorizer();
			break;
		case DELETE:
			this.deleteCategorizer();
			break;
		default:
			break;
		}
	}

	@Override
	public void onKeyPressedStageEvent(KeyEvent event) {
	}
}
