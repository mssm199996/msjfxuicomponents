package msjfxuicomponents.mvc.msclavier;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import msjfxuicomponents.mvc.KeyStageEvent;
import msjfxuicomponents.mvc.ResetController;

public class MSClavierController implements Initializable, ResetController, KeyStageEvent {
	public static SimpleStringProperty RESULT = new SimpleStringProperty("");
	public static boolean CONFIRMED = false;

	@FXML
	private Label result;

	@FXML
	private void backspace(ActionEvent event) {
		this.result.setText(this.result.getText().substring(0, this.result.getText().length() - 2));
	}

	@FXML
	private void cancel() {
		this.getWindow().hide();
	}

	@FXML
	private void clear(ActionEvent event) {
		this.result.setText("");
	}

	@FXML
	private void confirm() {
		MSClavierController.CONFIRMED = true;

		this.getWindow().hide();
	}

	@FXML
	private void writeSpace(ActionEvent event) {
		this.result.setText(this.result.getText() + " ");
	}

	@FXML
	private void writeText(ActionEvent event) {
		JFXButton trigger = (JFXButton) event.getSource();

		this.result.setText(this.result.getText() + trigger.getText());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initBindings();
	}

	private void initBindings() {
		this.result.textProperty().bindBidirectional(MSClavierController.RESULT);
	}

	public void onShowingResetResult() {
		MSClavierController.CONFIRMED = false;

		this.result.setText("");
	}

	public Window getWindow() {
		return this.result.getScene().getWindow();
	}

	@Override
	public void onKeyReleasedStageEvent(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER:
			this.confirm();
			break;
		default:
			break;
		}
	}

	@Override
	public void onKeyPressedStageEvent(KeyEvent event) {
	}

	@Override
	public MenuButton getKeyShortcutDescriber() {
		return null;
	}

	@Override
	public LinkedHashMap<String, String> fromKeyCodeToDescriptionsShortcutsMap() {
		return null;
	}
}
