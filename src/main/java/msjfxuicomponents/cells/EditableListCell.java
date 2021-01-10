package msjfxuicomponents.cells;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public abstract class EditableListCell<T> extends TextFieldListCell<T>{

	public EditableListCell() {
		super();
		
		this.setConverter(new StringConverter<T>() {
			@Override
			public String toString(T object) {
				return object.toString();
			}

			@Override
			public T fromString(String string) {
				return getItem();
			}
		});
	}
	
	@Override
    public void startEdit() {
		super.startEdit();
		
		TextField textField = (TextField) this.getGraphic();
		T value = this.getItem();
		
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue){
					commitEdit(value);
				}
			}
		});
	}
	
	@Override
	public void commitEdit(T newValue){
		TextField textField = (TextField) this.getGraphic();
		
		this.onEditCommit(newValue);
		this.setValue(newValue, textField.getText());
		
		super.commitEdit(newValue);
	}
	
	public abstract void setValue(T newValue, String newString);
	public abstract void onEditCommit(T newValue);
}
