package msjfxuicomponents.others.CategorizerControllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.cells.AutoCommitStringTextFieldCell;
import msjfxuicomponents.others.CrudController;

public abstract class ListeCategorizerController<T extends ICategorizer> extends CrudController<T> {

	@FXML
	protected TableColumn<T, String> designationColumn;

	@FXML
	private JFXTextField designation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initTextFields();
	}

	@Override
	public void initTables() {
		super.initTables();

		this.designationColumn.setCellFactory(call -> {
			return new AutoCommitStringTextFieldCell<T>() {

				@Override
				public void onEditCommit(String newValue) {
					onUpdateCallback(this.getEntity());
				}
			};
		});

		this.designationColumn.setCellValueFactory(call -> {
			return call.getValue().designationProperty();
		});
	}

	private void initTextFields() {
		this.designation.setOnKeyReleased(event -> {
			this.search();
		});
	}

	@Override
	protected List<T> selectFromDatabase() throws NullPointerException {
		String designation = this.designation.getText();

		return this.selectFromDatabase(designation);
	}

	public abstract List<T> selectFromDatabase(String designation);
}
