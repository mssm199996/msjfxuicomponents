package msjfxuicomponents.others.CategorizerControllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Window;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;

public abstract class AddCategorizerController<T extends ICategorizer> extends AddController<T> {

	@FXML
	private Label title;

	@FXML
	private JFXTextField designation;

	@Override
	public void confirm() throws Exception {
		String designation = this.designation.getText();

		if (!designation.equals("")) {
			T categorizer = this.constructCategorizer();
			categorizer.setDesignation(designation);

			this.beforeInsertCallback(categorizer);

			if (!this.isUsingMSCategorizer())
				this.onInsertCallback(categorizer);

			this.setLastInsertedEntity(categorizer);

			// Des fois je n'utilise pas une datasource commune EXP:
			// OrdonnanceType

			if (this.getDataSource() != null && !this.isUsingMSCategorizer())
				this.getDataSource().add(categorizer);

			this.getWindow().hide();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Veuillez saisir le nom de la maladie", this.getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.title.setText(this.getInsertHeader());
		this.designation.setPromptText(this.getDesignationPromptText());
	}

	@Override
	public void onShowingResetResult() {
		super.onShowingResetResult();

		this.designation.setText("");
		this.designation.requestFocus();
	}

	public Window getWindow() {
		return this.designation.getScene().getWindow();
	}

	public abstract List<T> getDataSource();

	public abstract T constructCategorizer();

	public abstract String getDesignationPromptText();

	public abstract boolean isUsingMSCategorizer();
}
