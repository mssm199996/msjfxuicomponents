package msjfxuicomponents.others;

import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.mspavenumerique.MSPaveNumeriqueController;

public class MSAlertsDisplayer {
	private DirectoryChooser directoryChooser = new DirectoryChooser();

	public void displayInformationAlert(String title, String header, String content, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

		this.constructAlert("win.sound.default", AlertType.INFORMATION, title, header, content, mother, okButton)
				.showAndWait();
	}

	public void displayErrorAlert(String title, String header, String content, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

		this.constructAlert("win.sound.exclamation", AlertType.ERROR, title, header, content, mother, okButton)
				.showAndWait();
	}

	public Boolean displayYesNoAlert(String title, String header, String content, Window mother) {
		ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, content, mother,
				yesButton, noButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == yesButton)
			return true;
		else if (result.get() == noButton)
			return false;
		else
			return null;
	}

	public boolean displayConfirmationAlert(String title, String header, String content, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		Optional<ButtonType> choice = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header,
				content, mother, okButton, cancelButton).showAndWait();

		return choice.get() == okButton;
	}

	public String displayBigStringAlert(String title, String header, String content, String defaultValue,
			Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		JFXTextArea dialogContent = new JFXTextArea(defaultValue);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, content, mother,
				okButton, cancelButton);
		alert.getDialogPane().setExpandableContent(dialogContent);
		alert.getDialogPane().setExpanded(true);
		
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == okButton)
			return dialogContent.getText();

		return null;
	}

	public String displayBigStringAlert(String title, String header, String defaultValue, Window mother) {
		return this.displayBigStringAlert(title, header, "", defaultValue, mother);
	}

	public String displayStringAlert(String title, String header, String content, String defaultValue, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		JFXTextField dialogContent = new JFXTextField(defaultValue);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, content, mother,
				okButton, cancelButton);
		alert.getDialogPane().setExpandableContent(dialogContent);
		alert.getDialogPane().setExpanded(true);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == okButton)
			return dialogContent.getText();

		return null;
	}

	public String displayStringAlert(String title, String header, String defaultValue, Window mother) {
		return this.displayStringAlert(title, header, "", defaultValue, mother);
	}

	public String displayBarcodeAlert(String title, String header, String content, Window mother) {
		return this.displayStringAlert(title, header, content, mother);
	}

	// *********************************************************************************************

	public Double displayDoubleAlert(String title, String header, String content, String defaultValue, Window mother) {
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

		if (runnable != null)
			runnable.run();

		MSPaveNumeriqueController.INITIAL_VALUE = defaultValue;
		MSPaveNumeriqueController.HEADER = header;

		MSJFXUIComponentsHolder.MS_PAVE_NUMERIQUE_STAGE.showAndWait();

		if (MSPaveNumeriqueController.CONFIRMED)
			return MSPaveNumeriqueController.RESULT;

		return null;
	}

	public Double displayDoubleAlert(String title, String header, String content, String defaultValue, Double minValue,
			Double maxValue, Window mother) {

		boolean keepAsking = true;
		Double result = null;

		while (keepAsking) {
			result = this.displayDoubleAlert(title, header, content, defaultValue, mother);

			if (result != null) {
				if (result >= minValue && result <= maxValue)
					keepAsking = false;
				else
					this.displayErrorAlert(title, header,
							"La saisie doit être comprise entre " + minValue + " et " + maxValue, mother);
			} else
				keepAsking = false;
		}

		return result;
	}

	public Integer displayIntegerAlert(String title, String header, String content, String defaultValue,
			Window mother) {

		Double result = this.displayDoubleAlert(title, header, content, defaultValue, mother);

		if (result != null)
			return result.intValue();

		return null;
	}

	// *********************************************************************************************

	public Path displayPathAlert(String title, String nom, String format, Window mother) {
		this.directoryChooser.setTitle(title);

		File choosenDirectory = this.directoryChooser.showDialog(mother);

		if (choosenDirectory != null) {
			LocalDateTime now = LocalDateTime.now();
			String stringedNow = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh-mm-ss", Locale.FRANCE));

			return Paths.get(choosenDirectory.getAbsolutePath() + "\\" + nom + " " + stringedNow + "." + format);
		}

		return null;
	}

	// *********************************************************************************************

	public Double displayDoubleAlertWithoutPave(String title, String header, String content, String defaultValue,
			Window mother) {

		String result = this.displayStringAlert(title, header, content, defaultValue, mother);

		if (result != null) {
			try {
				return Double.parseDouble(result);
			} catch (Exception exp) {
				return null;
			}
		}

		return null;
	}

	// ------------------------------------------------------------------------------------------

	private Alert constructAlert(String popupSound, AlertType alertType, String title, String header, String content,
			Window mother, ButtonType... buttons) {
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty(popupSound);

		if (runnable != null)
			runnable.run();

		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initOwner(mother);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.getButtonTypes().setAll(buttons);
		alert.getDialogPane().getStylesheets().add("/MVC/CSS/GeneralPurposes.css");

		for (ButtonType buttonType : buttons) {
			Node node = alert.getDialogPane().lookupButton(buttonType);

			if (node instanceof Button) {
				Button button = (Button) node;
				button.setDefaultButton(false);
			}
		}

		alert.getDialogPane().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				for (ButtonType buttonType : buttons) {
					Node node = alert.getDialogPane().lookupButton(buttonType);

					if (node.isFocused())
						alert.setResult(buttonType);
				}

				alert.close();
			}
		});

		return alert;
	}
}
