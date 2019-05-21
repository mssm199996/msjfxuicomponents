package msjfxuicomponents.mvc.msactivation;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import Exceptions.UnrecognizedLicenseKey;
import MainPackage.CopyrightHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.ResetController;

public class MSActivationController implements Initializable, ResetController {

	@FXML
	private Label tempsRestant;

	@FXML
	private JFXTextField idPoste;

	@FXML
	private JFXTextField cleeActivation;

	private CopyrightHandler copyrightHandler;

	@FXML
	void cancel(ActionEvent event) {
		this.getWindow().hide();
	}

	@FXML
	void confirm(ActionEvent event) {
		String givenCipherText = this.cleeActivation.getText();

		try {
			this.copyrightHandler.validateSoftware(givenCipherText);

			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayInformationAlert(this.getWindow().getTitle(),
					"Activation du logiciel", "Logiciel a été activé avec succès !", this.getWindow());

			this.getWindow().hide();
		} catch (UnrecognizedLicenseKey e) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getWindow().getTitle(),
					"Activation du logiciel", "Erreur ! clée invalide...", this.getWindow());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@Override
	public void onShownResetResult() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy à hh:mm:ss", Locale.FRANCE);
		String caption = this.copyrightHandler.getRemainingTimeAsString().format(formatter);

		this.idPoste.setText(this.copyrightHandler.getIdForValidation());
		this.idPoste.setLabelFloat(true);
		
		this.tempsRestant.setText("La license se terminera le " + caption);
		
		this.cleeActivation.setText("");
		this.cleeActivation.requestFocus();
	}

	public Stage getWindow() {
		return (Stage) this.tempsRestant.getScene().getWindow();
	}

	@Override
	public void onShowingResetResult() {
	}

	public CopyrightHandler getCopyrightHandler() {
		return copyrightHandler;
	}

	public void setCopyrightHandler(CopyrightHandler copyrightHandler) {
		this.copyrightHandler = copyrightHandler;
	}
}
