package msjfxuicomponents.componentsStuffers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public abstract class TablePaginator {

	private int rowsCount = 50;

	private Pagination pagination = null;
	private TableView<?> table = null;

	public TablePaginator(TableView<?> table) {
		this.table = table;
		this.pagination = new Pagination();
		this.pagination.setPageFactory(this::createPage);

		BorderPane parent = (BorderPane) this.table.getParent();
				   parent.getChildren().remove(this.table);
				   parent.setCenter(this.pagination);

		EventHandler<KeyEvent> paginationKeyEvent = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE)
					pagination.setCurrentPageIndex(0);
				else if (event.getCode() == KeyCode.LEFT)
					pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() - 1);
				else if (event.getCode() == KeyCode.RIGHT)
					pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);
			}
		};
		
		parent.sceneProperty().addListener(new ChangeListener<Scene>() {
			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				if(newValue != null) 
					newValue.getRoot().setOnKeyReleased(paginationKeyEvent);
			}
		});
	}

	private TableView<?> createPage(int pageIndex) {
		this.updateItems();

		return this.table;
	}

	public abstract void updateItems();

	public int getCurrentIndex() {
		return this.pagination.getCurrentPageIndex();
	}
	
	public void goToPage(int index) {
		this.pagination.setCurrentPageIndex(index);
	}

	public int getRowsCount() {
		return this.rowsCount;
	}
}
