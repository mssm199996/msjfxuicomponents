package msjfxuicomponents.cells;

public class PositiveTwoNumbersAfterCommaCell<T> extends TwoNumbersAfterCommaCell<T>{

	@Override
	public void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);
		
		if(!empty && item < 0.0) {
			setText("-");
		}
	}
}
