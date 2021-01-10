package msjfxuicomponents.uicomponents;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import com.sun.javafx.css.Size;
import com.sun.javafx.css.SizeUnits;

import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

public class MSAutoCompleteComboBox<T> extends ComboBox<T> {

	private Function<String, Collection<T>> entitiesLoader = null;
	private Double maxAllowedWidth = 175.0;
	private Double popupMaxHeight = 200.0;
	private Double rowHeight = 25.0;

	private boolean triggerEvent = true;

	public MSAutoCompleteComboBox() {
		super();

		this.initSize();
		this.initTooltip();
		this.initVariables();

		this.setOnHidden(this::handleOnHiding);
		this.setOnKeyReleased(this::handleOnKeyPressed);
	}

	public void addValueChangedListener(Consumer<T> consumer) {
		this.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (this.triggerEvent) {
				if (newValue.doubleValue() != -1.0)
					consumer.accept(getEntity());
				else
					consumer.accept(null);
			}
		});

		this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
				this.filter = "";
		});
	}

	public void reloadItems() {
		this.filter = "";
		this.performSearch();
	}

	public void reloadSelectionWithFirstEntity() {
		this.setEntity(null, false);
		this.setEntity(null, true);
	}

	public void setEntity(T value) {
		this.setEntity(value, true);
	}

	public void setEntity(T value, boolean selectFirstIfNull) {
		if (value == null && selectFirstIfNull)
			this.getSelectionModel().select(0);
		else if (value == null)
			this.getSelectionModel().clearSelection();
		else
			this.getSelectionModel().select(this.getItems().indexOf(value));
	}

	public void setEntityWithoutTriggeringEvent(T value, boolean selectFirstIfNull) {
		this.triggerEvent = false;
		this.setEntity(value, selectFirstIfNull);
		this.triggerEvent = true;
	}

	public boolean isSelectedElementEntity() {
		return this.getSelectionModel().getSelectedIndex() >= 0;
	}

	public T getEntity() {
		if (this.getSelectionModel().getSelectedIndex() < 0)
			return null;
		else
			try {
				return this.getItems().get(this.getSelectionModel().getSelectedIndex());
			} catch (IndexOutOfBoundsException exp) {
				exp.printStackTrace();

				return null;
			}
	}

	// ---------------------------------------------------------------------------

	public Function<String, Collection<T>> getEntitiesLoader() {
		return this.entitiesLoader;
	}

	public void setEntitiesLoader(Function<String, Collection<T>> entitiesLoader) {
		this.entitiesLoader = entitiesLoader;
	}

	public Double getPopupMaxHeight() {
		return this.popupMaxHeight;
	}

	public void setPopupMaxHeight(Double popupMaxHeight) {
		this.popupMaxHeight = popupMaxHeight;
	}

	public double getMaxAllowedWidth() {
		return this.maxAllowedWidth;
	}

	public void setMaxAllowedWidth(double maxAllowedWidth) {
		this.maxAllowedWidth = maxAllowedWidth;
	}

	public Double getRowHeight() {
		return this.rowHeight;
	}

	public void setRowHeight(Double rowHeight) {
		this.rowHeight = rowHeight;
	}

	// ---------------------------------------------------------------------------

	private String filter = "";

	public void handleOnKeyPressed(KeyEvent e) {
		KeyCode code = e.getCode();

		String oldFilter = this.filter;

		if (code.isLetterKey() || code.isDigitKey()) {
			this.filter += e.getText();
		}

		if (code == KeyCode.BACK_SPACE && this.filter.length() > 0) {
			this.filter = this.filter.substring(0, this.filter.length() - 1);
		}

		if (code == KeyCode.ESCAPE) {
			this.filter = "";
		}

		if (code == KeyCode.SPACE) {
			this.filter += " ";
		}

		if (this.filter.length() == 0) {
			this.getTooltip().hide();
		} else {
			this.getTooltip().setText(this.filter);

			Window stage = this.getScene().getWindow();

			double posX = stage.getX() + this.localToScene(this.getBoundsInLocal()).getMinX() - 20;
			double posY = stage.getY() + this.localToScene(this.getBoundsInLocal()).getMinY() - 20;

			this.getTooltip().show(stage, posX, posY);
			this.show();
		}

		// If not, when i do F2 => new window in msmarket and removes the search
		if (!this.filter.equals(oldFilter))
			this.performSearch();
	}

	public void handleOnHiding(Event e) {
		String intermediate = this.filter;

		this.filter = "";
		this.getTooltip().hide();

		T s = this.getSelectionModel().getSelectedItem();

		this.performSearch();
		this.getSelectionModel().select(s);

		this.filter = intermediate;
	}

	// ---------------------------------------------------------------------------

	private void performSearch() throws NullPointerException {
		this.getItems().clear();
		this.getItems().addAll(this.entitiesLoader.apply(this.filter));
	}

	// ---------------------------------------------------------------------------

	private void initSize() {
		this.setPrefWidth(this.maxAllowedWidth);
		this.setMaxWidth(this.maxAllowedWidth);
	}

	private void initVariables() {
		this.rowHeight = (new Size(2.5, SizeUnits.EM)).pixels();
	}

	private void initTooltip() {
		Tooltip tooltip = new Tooltip();
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		this.setTooltip(tooltip);
	}
}
