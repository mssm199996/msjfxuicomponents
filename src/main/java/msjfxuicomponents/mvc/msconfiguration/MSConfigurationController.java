package msjfxuicomponents.mvc.msconfiguration;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;

import DomainModel.IConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Window;
import msjfxuicomponents.mvc.ResetController;
import msjfxuicomponents.uicomponents.MSDirectoryChooser;

public abstract class MSConfigurationController<T extends IConfiguration> implements Initializable, ResetController {

	@FXML
	private MSDirectoryChooser cheminSauvegarde;

	@FXML
	private MSDirectoryChooser cheminMysql;

	@FXML
	private JFXCheckBox activateCamera;

	@FXML
	private JFXCheckBox advancedPrinting;

	@FXML
	void cancel(ActionEvent event) {
		this.getWindow().hide();
	}

	@FXML
	public void confirm() {
		String cheminSauvegarde = this.cheminSauvegarde.getText();
		String cheminMysql = this.cheminMysql.getText();
		boolean activateCamera = this.activateCamera.isSelected();
		boolean advancedPrinting = this.advancedPrinting.isSelected();

		this.updateCheminSauvegardeInsideSessionFactoryHandler(cheminSauvegarde);
		this.updateMysqlFolderInsideSessionFactoryHandler(cheminMysql);

		this.initMainConfiguration(this.getMainConfiguration());

		this.getMainConfiguration().setCheminSauvegarde(cheminSauvegarde);
		this.getMainConfiguration().setActivateCamera(activateCamera);
		this.getMainConfiguration().setCheminMysql(cheminMysql);
		this.getMainConfiguration().setAdvancedPrinting(advancedPrinting);

		this.updateMainConfiguration(this.getMainConfiguration());
		this.getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void onShowingResetResult() {
		if (this.getMainConfiguration().getCheminSauvegarde() != null)
			this.cheminSauvegarde.setText(this.getMainConfiguration().getCheminSauvegarde());
		else
			this.cheminSauvegarde.setText("Sauvegardes");

		if (this.getMainConfiguration().isActivateCamera() != null)
			this.activateCamera.setSelected(this.getMainConfiguration().isActivateCamera());
		else
			this.activateCamera.setSelected(false);

		if (this.getMainConfiguration().getCheminMysql() != null)
			this.cheminMysql.setText(this.getMainConfiguration().getCheminMysql());
		else
			this.cheminMysql.setText("");

		if (this.getMainConfiguration().isAdvancedPrinting() != null)
			this.advancedPrinting.setSelected(this.getMainConfiguration().isAdvancedPrinting());
		else
			this.advancedPrinting.setSelected(false);
	}

	public Window getWindow() {
		return this.cheminSauvegarde.getScene().getWindow();
	}

	public abstract T getMainConfiguration();

	public abstract void initMainConfiguration(T mainConfiguration);

	public abstract void updateMainConfiguration(T mainConfiguration);

	public abstract void updateCheminSauvegardeInsideSessionFactoryHandler(String cheminSauvegarde);

	public abstract void updateMysqlFolderInsideSessionFactoryHandler(String cheminMysql);
}
