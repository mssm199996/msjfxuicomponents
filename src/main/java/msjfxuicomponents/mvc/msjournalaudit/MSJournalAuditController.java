package msjfxuicomponents.mvc.msjournalaudit;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.OperationType;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.cells.WellFormattedLocalDateCell;
import msjfxuicomponents.mvc.ResetController;
import mssoftutils.others.SearchController;

public class MSJournalAuditController<O extends OperationType> extends SearchController<O>
		implements Initializable, ResetController {

	@FXML
	private JFXDatePicker dateFilter;

	@FXML
	private JFXTextField description;

	@FXML
	private TableView<O> tableOperations;

	@FXML
	private TableColumn<O, Integer> indexColumn;

	@FXML
	private TableColumn<O, String> descriptionColumn;

	@FXML
	private TableColumn<O, LocalDate> dateColumn;

	@FXML
	private TableColumn<O, LocalTime> heureOperation;

	@FXML
	private TableColumn<O, String> compteColumn;

	private IOperationTypeDAO<O> searcher = null;

	@FXML
	@Override
	public void search() {
		super.search();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTables();
		this.initDatePickers();
	}

	private void initDatePickers() {
		this.dateFilter.valueProperty().addListener(event -> {
			this.search();
		});
	}

	private void initTables() {
		this.tableOperations.setItems(this.getDatasource());

		this.indexColumn.setCellFactory(event -> {
			return new IndexCell<>();
		});

		this.descriptionColumn.setCellValueFactory(IOperation -> {
			return IOperation.getValue().descriptionProperty();
		});

		this.dateColumn.setCellValueFactory(IOperation -> {
			return IOperation.getValue().dateIOperationProperty();
		});
		this.dateColumn.setCellFactory(call -> {
			return new WellFormattedLocalDateCell<>();
		});

		this.heureOperation.setCellValueFactory(IOperation -> {
			return IOperation.getValue().heureIOperationProperty();
		});

		this.compteColumn.setCellValueFactory(IOperation -> {
			return IOperation.getValue().designationOperateurProperty();
		});
	}

	@Override
	public void onShowingResetResult() {
		this.search();
	}

	@Override
	protected List<O> selectFromDatabase() throws NullPointerException {
		if (this.searcher != null) {
			String description = this.description.getText();
			LocalDate date = this.dateFilter.getValue();

			try {
				return this.searcher.fetchFromDatabase(description, date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new LinkedList<O>();
	}

	public IOperationTypeDAO<O> getSearcher() {
		return searcher;
	}

	@SuppressWarnings("unchecked")
	public void setSearcher(IOperationTypeDAO<?> searcher) {
		this.searcher = (IOperationTypeDAO<O>) searcher;
	}
}
