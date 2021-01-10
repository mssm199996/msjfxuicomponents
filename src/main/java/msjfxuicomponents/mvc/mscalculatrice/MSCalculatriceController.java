package msjfxuicomponents.mvc.mscalculatrice;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import msjfxuicomponents.mvc.ResetController;

public class MSCalculatriceController implements Initializable, ResetController{

	private Double temp = 0.0, sum = 0.0;
    boolean isOperatorPressed = false;
    private String operatorPressed = "";
    
    @FXML
    private Label outputTF;       
    
    @FXML
    private void onNumberClick(ActionEvent event) {
        if(event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();
            
            if(isOperatorPressed) {
                outputTF.setText(btn.getText().trim());            
            } else {
                outputTF.setText(outputTF.getText().trim() + btn.getText().trim());
            }
            isOperatorPressed = false;
        }
    }
    
    @FXML
    private void onOperatorClick(ActionEvent event) {
        if(event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();           
            
            if (!outputTF.getText().isEmpty()) {
                temp = Double.valueOf(outputTF.getText());

                switch (operatorPressed) {                    
                    case "/":
                        sum /= temp;
                        break;
                    case "x":
                        sum *= temp;
                        break;
                    case "+":
                        sum += temp;
                        break;
                    case "-":
                        sum -= temp;
                        break;
                    default:
                        sum = temp;
                }
            }
                        
            if (btn.getText().equals("=")) {
                outputTF.setText(String.valueOf(sum));                
                operatorPressed = "";
            } else {
                //outputTF.setText("");                
                outputTF.setText(String.valueOf(sum));
            	operatorPressed = btn.getText().trim();
            }
            
            isOperatorPressed = true;
        }
    }        
    
    @FXML
    private void onDELClick(ActionEvent event) {
        if(outputTF.getText().length() > 0) {
            outputTF.setText(outputTF.getText().substring(0, outputTF.getText().length() - 1));
        }        
    }
    
    @FXML
    private void onCEClick(ActionEvent event) {
        outputTF.setText("");
        temp = 0.0;
        sum = 0.0;
        isOperatorPressed = false;
        operatorPressed = "";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outputTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    outputTF.setText(oldValue);
                }
            }
        });
    }

	public void onShowingResetResult() {
        outputTF.setText("");
        temp = 0.0;
        sum = 0.0;
        isOperatorPressed = false;
        operatorPressed = "";
	}
}
