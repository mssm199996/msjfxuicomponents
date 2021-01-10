package msjfxuicomponents.mvc.mspavenumerique.advanced;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import msjfxuicomponents.mvc.mspavenumerique.MSPaveNumeriqueController;

public class MSAdvancedPaveNumeriqueController extends MSPaveNumeriqueController {
	public Double target = 0.0;

	@FXML
	private Label rest;

	@Override
	public void initBindings() {
		this.result.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					try {
						setResultValue(Double.parseDouble(newValue));
					} catch (Exception e) {
						setResultValue(0.0);
					}

					calculateRest();
				} catch (NumberFormatException exp) {
				}
			}
		});
	}

	private void calculateRest() {
		Double rest = this.getResultValue() - this.target;

		this.rest.setText(String.format("%.2f", rest));
	}

	public Double getTarget() {
		return target;
	}

	public void setTarget(Double target) {
		this.target = target;
	}
}
