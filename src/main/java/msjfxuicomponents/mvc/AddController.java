package msjfxuicomponents.mvc;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Window;
import msjfxuicomponents.mvc.compositions.AbstractController;

public abstract class AddController<T> implements Initializable, ResetController, AbstractController {

	@FXML
	public void cancel() {
		this.getWindow().hide();
	}

	@FXML
	public void confirm() throws Exception {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void onShowingResetResult() {
		this.setLastInsertedEntity(null);
	}

	public abstract Window getWindow();

	public abstract String getNomGestion();

	public abstract String getInsertHeader();

	public abstract void setLastInsertedEntity(T entity);

	public abstract void onInsertCallback(T entity);

	public void beforeInsertCallback(T entity) throws Exception {
	}
}
