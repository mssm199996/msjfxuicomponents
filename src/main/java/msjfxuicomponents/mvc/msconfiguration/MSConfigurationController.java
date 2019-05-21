package msjfxuicomponents.mvc.msconfiguration;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Window;
import msjfxuicomponents.mvc.ResetController;
import msjfxuicomponents.others.IConfiguration;
import msjfxuicomponents.uicomponents.MSDirectoryChooser;

public abstract class MSConfigurationController<T extends IConfiguration> implements Initializable, ResetController {

	@FXML
	private MSDirectoryChooser cheminSauvegarde;

	@FXML
	private JFXCheckBox activateCamera;

	@FXML
	void cancel(ActionEvent event) {
		this.getWindow().hide();
	}

	@FXML
	public void confirm() {
		String cheminSauvegarde = this.cheminSauvegarde.getText();
		boolean activateCamera = this.activateCamera.isSelected();

		this.updateCheminSauvegardeInsideSessionFactoryHandler(cheminSauvegarde);
		this.initMainConfiguration(this.getMainConfiguration());
		this.getMainConfiguration().setCheminSauvegarde(cheminSauvegarde);
		this.getMainConfiguration().setActivateCamera(activateCamera);
		this.updateMainConfiguration(this.getMainConfiguration());
		this.getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void onShowingResetResult() {
		this.cheminSauvegarde.setText(this.getMainConfiguration().getCheminSauvegarde());
		this.activateCamera.setSelected(this.getMainConfiguration().isActivateCamera());
	}

	public Window getWindow() {
		return this.cheminSauvegarde.getScene().getWindow();
	}

	public abstract T getMainConfiguration();

	public abstract void initMainConfiguration(T mainConfiguration);

	public abstract void updateMainConfiguration(T mainConfiguration);

	public abstract void updateCheminSauvegardeInsideSessionFactoryHandler(String cheminSauvegarde);
}
