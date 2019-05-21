package msjfxuicomponents.mvc.mspavenumerique;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import msjfxuicomponents.mvc.KeyStageEvent;
import msjfxuicomponents.mvc.ResetController;

public class MSPaveNumeriqueController implements Initializable, KeyStageEvent, ResetController {
	public static String INITIAL_VALUE = "0.0";
	public static String HEADER = "";

	public static boolean CONFIRMED = false;
	public static Double RESULT = 0.0;

	private boolean isFirstStep = true;

	@FXML
	private Label result, header;

	@FXML
	private JFXButton confirmButton;

	@FXML
	private HBox headerContainer;

	@FXML
	private void cancel() {
		MSPaveNumeriqueController.HEADER = "";

		this.getWindow().hide();
	}

	@FXML
	private void clear() {
		this.result.setText("0.0");

		isFirstStep = true;
	}

	@FXML
	private void confirm() {
		MSPaveNumeriqueController.CONFIRMED = true;
		MSPaveNumeriqueController.HEADER = "";

		this.getWindow().hide();
	}

	@FXML
	private void dotAction() {
		if (isFirstStep)
			result.setText("");

		if (!result.getText().contains("."))
			result.setText(result.getText() + ".");

		isFirstStep = false;
	}

	@FXML
	private void eightAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "8");

		isFirstStep = false;
	}

	@FXML
	private void fiveAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "5");

		isFirstStep = false;
	}

	@FXML
	private void fourAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "4");

		isFirstStep = false;
	}

	@FXML
	private void nineAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "9");

		isFirstStep = false;
	}

	@FXML
	private void oneAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "1");

		isFirstStep = false;
	}

	@FXML
	private void sevenAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "7");

		isFirstStep = false;
	}

	@FXML
	private void sixAction() {
		if (isFirstStep)
			result.setText("");

		result.setText(result.getText() + "6");

		isFirstStep = false;
	}

	@FXML
	private void threeAction() {
		if (isFirstStep)
			result.setText("");
		result.setText(result.getText() + "3");

		isFirstStep = false;
	}

	@FXML
	private void twoAction() {
		if (isFirstStep)
			result.setText("");
		result.setText(result.getText() + "2");

		isFirstStep = false;
	}

	@FXML
	private void zeroAction() {
		if (isFirstStep)
			result.setText("");

		if (!result.getText().equals("0"))
			result.setText(result.getText() + "0");

		isFirstStep = false;
	}

	private void backSpaceAction() {
		if (result.getText().length() > 0)
			result.setText(result.getText().substring(0, result.getText().length() - 1));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initBindings();
	}

	private void initBindings() {
		this.result.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					MSPaveNumeriqueController.RESULT = Double.parseDouble(newValue);
				} catch (NumberFormatException exp) {
				}
			}
		});
	}

	public void onShowingResetResult() {
		MSPaveNumeriqueController.CONFIRMED = false;

		this.isFirstStep = true;
		this.result.setText(MSPaveNumeriqueController.INITIAL_VALUE);

		if (MSPaveNumeriqueController.HEADER.equals("") && this.header.getParent() != null)
			this.headerContainer.getChildren().remove(this.header);
		else if (!MSPaveNumeriqueController.HEADER.equals("")) {
			this.header.setText(MSPaveNumeriqueController.HEADER);

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
}
