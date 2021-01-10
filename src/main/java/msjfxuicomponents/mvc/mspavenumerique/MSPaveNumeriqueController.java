package msjfxuicomponents.mvc.mspavenumerique;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import msjfxuicomponents.mvc.KeyStageEvent;
import msjfxuicomponents.mvc.ResetController;

public class MSPaveNumeriqueController implements Initializable, KeyStageEvent, ResetController {
	public String initialValue = "0.0";
	public String headerValue = "";

	public boolean confirmed = false;
	public Double resultValue = 0.0;

	private boolean isFirstStep = true;

	@FXML
	protected Label result, header;

	@FXML
	private JFXButton confirmButton;

	@FXML
	private HBox headerContainer;

	@FXML
	private void cancel() {
		this.headerValue = "";
		this.getWindow().hide();
	}

	@FXML
	private void clear() {
		this.result.setText("0.0");

		this.isFirstStep = true;
	}

	@FXML
	private void confirm() {
		this.confirmed = true;
		this.headerValue = "";

		this.getWindow().hide();
	}

	@FXML
	private void dotAction() {
		if (this.isFirstStep)
			this.result.setText("");

		if (!this.result.getText().contains("."))
			this.result.setText(this.result.getText() + ".");

		this.isFirstStep = false;
	}

	@FXML
	private void eightAction() {
		if (this.isFirstStep)
			this.result.setText("");

		this.result.setText(this.result.getText() + "8");

		this.isFirstStep = false;
	}

	@FXML
	private void fiveAction() {
		if (this.isFirstStep)
			this.result.setText("");

		this.result.setText(this.result.getText() + "5");

		this.isFirstStep = false;
	}

	@FXML
	private void fourAction() {
		if (this.isFirstStep)
			this.result.setText("");

		this.result.setText(this.result.getText() + "4");

		this.isFirstStep = false;
	}

	@FXML
	private void nineAction() {
		if (this.isFirstStep)
			this.result.setText("");

		this.result.setText(result.getText() + "9");

		this.isFirstStep = false;
	}

	@FXML
	private void oneAction() {
		if (this.isFirstStep)
			this.result.setText("");

		this.result.setText(result.getText() + "1");

		isFirstStep = false;
	}

	@FXML
	private void sevenAction() {
		if (isFirstStep)
			result.setText("");

		this.result.setText(this.result.getText() + "7");

		this.isFirstStep = false;
	}

	@FXML
	private void sixAction() {
		if (this.isFirstStep)
			this.result.setText("");

		this.result.setText(this.result.getText() + "6");

		this.isFirstStep = false;
	}

	@FXML
	private void threeAction() {
		if (this.isFirstStep)
			this.result.setText("");
		this.result.setText(this.result.getText() + "3");

		this.isFirstStep = false;
	}

	@FXML
	private void twoAction() {
		if (this.isFirstStep)
			this.result.setText("");
		this.result.setText(this.result.getText() + "2");

		this.isFirstStep = false;
	}

	@FXML
	private void zeroAction() {
		if (this.isFirstStep)
			this.result.setText("");

		if (!this.result.getText().equals("0"))
			this.result.setText(this.result.getText() + "0");

		this.isFirstStep = false;
	}

	private void backSpaceAction() {
		if (this.result.getText().length() > 0)
			this.result.setText(this.result.getText().substring(0, this.result.getText().length() - 1));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initBindings();
	}

	protected void initBindings() {
		this.result.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					resultValue = Double.parseDouble(newValue);
				} catch (NumberFormatException exp) {
				}
			}
		});
	}

	public void onShowingResetResult() {
		this.confirmed = false;
		this.isFirstStep = true;
		this.result.setText(this.initialValue);

		if (this.headerValue.equals("") && this.header.getParent() != null)
			this.headerContainer.getChildren().remove(this.header);
		else if (!this.headerValue.equals("")) {
			this.header.setText(this.headerValue);

			if (this.header.getParent() == null)
				this.headerContainer.getChildren().add(this.header);
		}
	}

	@Override
	public void onKeyReleasedStageEvent(KeyEvent event) {
		switch (event.getCode()) {
		case NUMPAD0:
			this.zeroAction();
			break;
		case NUMPAD1:
			this.oneAction();
			break;
		case NUMPAD2:
			this.twoAction();
			break;
		case NUMPAD3:
			this.threeAction();
			break;
		case NUMPAD4:
			this.fourAction();
			break;
		case NUMPAD5:
			this.fiveAction();
			break;
		case NUMPAD6:
			this.sixAction();
			break;
		case NUMPAD7:
			this.sevenAction();
			break;
		case NUMPAD8:
			this.eightAction();
			break;
		case NUMPAD9:
			this.nineAction();
			break;
		case DECIMAL:
			this.dotAction();
			break;
		case ENTER:
			this.confirm();
			break;
		default:
			break;
		}
	}

	@Override
	public void onKeyPressedStageEvent(KeyEvent event) {
		switch (event.getCode()) {
		case BACK_SPACE:
			this.backSpaceAction();
			break;
		default:
			break;
		}
	}

	public Window getWindow() {
		return this.confirmButton.getScene().getWindow();
	}

	@Override
	public MenuButton getKeyShortcutDescriber() {
		return null;
	}

	@Override
	public LinkedHashMap<String, String> fromKeyCodeToDescriptionsShortcutsMap() {
		return null;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Double getResultValue() {
		return resultValue;
	}

	public void setResultValue(Double resultValue) {
		this.resultValue = resultValue;
	}
}
