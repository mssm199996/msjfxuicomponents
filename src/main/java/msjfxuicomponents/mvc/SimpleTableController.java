package msjfxuicomponents.mvc;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import msjfxuicomponents.cells.AutoCommitStringTextFieldCell;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.mvc.compositions.NestedController;
import msjfxuicomponents.mvc.compositions.SimpleTableComposedController;

public abstract class SimpleTableController<P extends SimpleTableComposedController<T>, T>
		implements Initializable, NestedController<P> {

	protected P parent;

	@FXML
	protected TableView<T> resultTable;

	@FXML
	private TableColumn<T, Integer> indexColumn;

	protected Callback<TableColumn<T, String>, TableCell<T, String>> textCellUpdater = new Callback<TableColumn<T, String>, TableCell<T, String>>() {

		@Override
		public TableCell<T, String> call(TableColumn<T, String> param) {
			return new AutoCommitStringTextFieldCell<T>() {
				@Override
				public void onEditCommit(String newValue) {
					updateEntityRow(this.getIndex());
				}
			};
		}
	};

	@Override
	public void setParent(P parent) {
		this.parent = parent;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTables();
	}

	public void initTables() {
		this.indexColumn.setCellFactory(call -> {
			return new IndexCell<>();
		});

		this.parent.setTableView(this.resultTable);
	}

	public void setItems(ObservableList<T> datasource) {
		this.resultTable.setItems(datasource);
	}

	public void clear() {
		this.resultTable.getItems().clear();
	}

	public void addAll(Collection<T> datasource) {
		this.resultTable.getItems().addAll(datasource);
	}

	public SelectionModel<T> getSelectionModel() {
		return this.resultTable.getSelectionModel();
	}

	public void updateEntityRow(int index) {
		T entity = this.resultTable.getItems().get(index);

		this.updateEntityRow(entity);
	}

	public void updateEntityRow(T entity) {
		(new Thread(() -> {
			this.onUpdateCallback(entity);
		})).start();
	}

	public TableView<T> getResultTable() {
		return this.resultTable;
	}

	public abstract void onUpdateCallback(T entity);
}
