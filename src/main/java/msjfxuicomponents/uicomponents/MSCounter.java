package msjfxuicomponents.uicomponents;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class MSCounter extends HBox {

	private JFXButton plus, minus;
	private Label counter;
	private IntegerProperty count;

	private boolean preventNegatives = true;
	private StringConverter<Integer> converter = new StringConverter<Integer>() {

		@Override
		public String toString(Integer object) {
			return Integer.toString(object);
		}

		@Override
		public Integer fromString(String string) {
			return Integer.parseInt(string);
		}
	};

	public MSCounter() {
		super();

		this.setSpacing(15.0);
		this.setAlignment(Pos.CENTER);

		this.initCountProperty();
		this.addComponents();
	}

	protected void initCountProperty() {
		this.count = new SimpleIntegerProperty(0);
		this.count.addListener((observable, oldValue, newValue) -> {
			this.counter.setText(this.converter.toString(newValue.intValue()));
		});
	}

	protected void addComponents() {
		FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
		plusIcon.setSize("4em");
		plusIcon.setFill(Color.GREEN);

		FontAwesomeIconView minusIcon = new FontAwesomeIconView(FontAwesomeIcon.MINUS_CIRCLE);
		minusIcon.setSize("4em");
		minusIcon.setFill(Color.RED);

		this.counter = new Label("0");
		this.counter.getStyleClass().add("mscounter-counter");

		this.plus = new JFXButton();
		this.plus.setGraphic(plusIcon);
		this.plus.getStyleClass().addAll("lightGreenButton", "mscounter-plus");
		this.plus.addEventHandler(ActionEvent.ACTION, e -> {
			this.count.setValue(this.count.getValue() + 1);
		});

		this.minus = new JFXButton();
		this.minus.setGraphic(minusIcon);
		this.minus.getStyleClass().addAll("lightRedButton", "mscounter-minus");
		this.minus.addEventHandler(ActionEvent.ACTION, e -> {
			if (this.count.getValue() > 0 || !this.preventNegatives)
				this.count.setValue(this.count.getValue() - 1);
		});

		this.getChildren().addAll(this.minus, this.counter, this.plus);
	}

	public Integer getCount() {
		return count.getValue();
	}

	public void setCount(Integer count) {
		this.count.setValue(count);
	}

	public StringConverter<Integer> getConverter() {
		return converter;
	}

	public void setConverter(StringConverter<Integer> converter) {
		this.converter = converter;
	}

	public JFXButton getPlus() {
		return plus;
	}

	public void setPlus(JFXButton plus) {
		this.plus = plus;
	}

	public JFXButton getMinus() {
		return minus;
	}

	public void setMinus(JFXButton minus) {
		this.minus = minus;
	}

	public boolean isPreventNegatives() {
		return preventNegatives;
	}

	public void setPreventNegatives(boolean preventNegatives) {
		this.preventNegatives = preventNegatives;
	}
}
