package msjfxuicomponents.others;

import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Window;
import msjfxuicomponents.mvc.mspavenumerique.MSSimplePaveNumeriqueStage;
import msjfxuicomponents.mvc.mspavenumerique.advanced.MSAdvancedPaveNumeriqueStage;

public class MSAlertsDisplayer {
	private DirectoryChooser directoryChooser = new DirectoryChooser();

	public <T> T displayChoiceAlert(String title, String header, List<T> content, T defaultValue, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		ComboBox<T> elementsContainer = new ComboBox<>();
		elementsContainer.getItems().addAll(content);
		elementsContainer.setPromptText("Votre choix: ");

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, elementsContainer,
				mother, okButton, okButton, cancelButton);

		Optional<ButtonType> button = alert.showAndWait();

		if (button.get() == okButton)
			return elementsContainer.getValue();

		return null;
	}

	// *********************************************************************************************

	public String displayPasswordAlert(String title, String header, String content, String defaultValue,
			Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		JFXPasswordField textContainer = new JFXPasswordField();
		textContainer.setPromptText("Mot de passe");
		textContainer.setLabelFloat(true);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, textContainer,
				mother, okButton, okButton, cancelButton);

		Optional<ButtonType> button = alert.showAndWait();

		if (button.get() == okButton)
			return textContainer.getText();

		return null;
	}

	public String displayPasswordAlert(String title, String header, String content, String defaultValue) {
		return this.displayPasswordAlert(title, header, content, defaultValue, null);
	}

	// *********************************************************************************************

	public void displayInformationAlert(String title, String header, String content, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

		this.constructAlert("win.sound.default", AlertType.INFORMATION, title, header, content, mother, okButton,
				okButton).showAndWait();
	}

	// *********************************************************************************************

	public void displayErrorAlert(String title, String header, String content, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

		this.constructAlert("win.sound.exclamation", AlertType.ERROR, title, header, content, mother, okButton,
				okButton).showAndWait();
	}

	// *********************************************************************************************

	public Boolean displayYesNoAlert(String title, String header, String content, Window mother) {
		ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, content, mother,
				yesButton, yesButton, noButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == yesButton)
			return true;
		else if (result.get() == noButton)
			return false;
		else
			return null;
	}

	// *********************************************************************************************

	public boolean displayConfirmationAlert(String title, String header, String content, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		Optional<ButtonType> choice = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header,
				content, mother, okButton, okButton, cancelButton).showAndWait();

		return choice.get() == okButton;
	}

	// *********************************************************************************************

	public String displayBigStringAlert(String title, String header, String content, String defaultValue,
			Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		JFXTextArea dialogContent = new JFXTextArea(defaultValue);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, dialogContent,
				mother, null, okButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == okButton)
			return dialogContent.getText();

		return null;
	}

	public String displayBigStringAlert(String title, String header, String defaultValue, Window mother) {
		return this.displayBigStringAlert(title, header, "", defaultValue, mother);
	}

	// *********************************************************************************************

	public String displayStringAlert(String title, String header, String content, String defaultValue, Window mother) {
		ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

		JFXTextField dialogContent = new JFXTextField(defaultValue);
		dialogContent.setPromptText(content);
		dialogContent.setMinWidth(400);

		Alert alert = this.constructAlert("win.sound.default", AlertType.CONFIRMATION, title, header, dialogContent,
				mother, okButton, okButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == okButton)
			return dialogContent.getText();

		return null;
	}

	public String displayStringAlert(String title, String header, String defaultValue, Window mother) {
		return this.displayStringAlert(title, header, "", defaultValue, mother);
	}

	// *********************************************************************************************

	public String displayBarcodeAlert(String title, String header, String content, Window mother) {
		return this.displayStringAlert(title, header, content, mother);
	}

	// *********************************************************************************************

	public Double displayAdvancedDoubleAlert(MSAdvancedPaveNumeriqueStage stage, String header, String defaultValue,
			Double target) {
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

		if (runnable != null)
			runnable.run();

		stage.setInitialValue(defaultValue);
		stage.setHeader(header);
		stage.setTarget(target);
		stage.showAndWait();

		if (stage.isConfirmed())
			return stage.getResultValue();

		return null;
	}

	// *********************************************************************************************

	public Double displayDoubleAlert(MSSimplePaveNumeriqueStage stage, String title, String header, String content,
			String defaultValue, Window mother) {
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

		if (runnable != null)
			runnable.run();

		stage.setInitialValue(defaultValue);
		stage.setHeader(header);
		stage.showAndWait();

		if (stage.isConfirmed())
			return stage.getResultValue();

		return null;
	}

	public Double displayDoubleAlert(MSSimplePaveNumeriqueStage stage, String title, String header, String content,
			String defaultValue, Double minValue, Double maxValue, Window mother) {

		boolean keepAsking = true;
		Double result = null;

		while (keepAsking) {
			result = this.displayDoubleAlert(stage, title, header, content, defaultValue, mother);

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

	// *********************************************************************************************

	public Integer displayIntegerAlert(MSSimplePaveNumeriqueStage stage, String title, String header, String content,
			String defaultValue, Window mother) {

		Double result = this.displayDoubleAlert(stage, title, header, content, defaultValue, mother);

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

	// ------------------------------------------------------------------------------------------

	public void displayCapitalErrorAlert(String title, String header, String content) {
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");

		if (runnable != null)
			runnable.run();

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.getDialogPane().getStylesheets().add("/MVC/CSS/GeneralPurposes.css");
		alert.showAndWait();
	}

	private Alert constructAlert(String popupSound, AlertType alertType, String title, String header, Node content,
			Window mother, ButtonType defaultButtonType, ButtonType... buttons) {
		Alert alert = this.constructAlert(popupSound, alertType, title, header, "", mother, defaultButtonType, buttons);

		BorderPane root = new BorderPane();
		root.setCenter(content);

		BorderPane.setMargin(content, new Insets(15.0, 5.0, 0.0, 5.0));

		alert.getDialogPane().setContent(root);
		alert.setOnShown(event -> {
			Platform.runLater(() -> {
				content.requestFocus();
			});
		});

		return alert;
	}

	private Alert constructAlert(String popupSound, AlertType alertType, String title, String header, String content,
			Window mother, ButtonType defaultButtonType, ButtonType... buttons) {
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty(popupSound);

		if (runnable != null)
			runnable.run();

		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initOwner(mother);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.getButtonTypes().setAll(buttons);
		alert.getDialogPane().getStylesheets().add("/MVC/CSS/GeneralPurposes.css");

		for (ButtonType buttonType : buttons) {
			Node node = alert.getDialogPane().lookupButton(buttonType);

			if (node instanceof Button) {
				Button button = (Button) node;
				button.setDefaultButton(false);
			}
		}

		alert.getDialogPane().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				for (ButtonType buttonType : buttons) {
					Node node = alert.getDialogPane().lookupButton(buttonType);

					if (node.isFocused()) {
						alert.setResult(buttonType);
						alert.close();

						return;
					}
				}

				// No Button is selected, take the default one
				if (defaultButtonType != null) {
					alert.setResult(defaultButtonType);
					alert.close();
				}
			} else if (event.getCode() == KeyCode.ESCAPE) {
				alert.setResult(ButtonType.CANCEL);
				alert.close();
			}
		});

		return alert;
	}
}
