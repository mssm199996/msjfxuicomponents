package msjfxuicomponents.uicomponents;

import com.sun.javafx.css.Size;
import com.sun.javafx.css.SizeUnits;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class MSAutoCompleteComboBox<T> extends ComboBox<T> {

    private MSComboBoxListViewSkin<T> skin = null;
    private Function<String, Collection<T>> entitiesLoader = null;
    private Double maxAllowedWidth = 175.0;
    private Double popupMaxHeight = 200.0;
    private Double popupMinHeight = 50.0;
    private Double rowHeight = 25.0;

    private boolean triggerEvent = true;

    public MSAutoCompleteComboBox() {
        super();

        this.initListeners();
        this.initVariables();
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
    }

    public double getMaxAllowedWidth() {
        return this.maxAllowedWidth;
    }

    public void setMaxAllowedWidth(double maxAllowedWidth) {
        this.maxAllowedWidth = maxAllowedWidth;
    }

    private void performSearch() throws NullPointerException {
        String value = this.getEditor().getText();
        boolean selectedWasElementEntity = this.isSelectedElementEntity();

        if (value == null)
            value = "";

        Collection<T> result = this.getEntitiesLoader().apply(value);

        this.getItems().clear();
        this.getItems().addAll(result);

        if (selectedWasElementEntity) {
            this.getEditor().setText(value);
            this.getEditor().positionCaret((getEditor().getText().length()));
        }

        if (this.skin != null) {
            this.skin.msResize(this.getItems().size(), this.getRowHeight(), this.popupMinHeight, this.popupMaxHeight);
            this.show();
        }
    }

    public void reloadItems() {
        this.getEditor().setText("");
        this.performSearch();
    }

    private void initVariables() {
        this.rowHeight = (new Size(2.5, SizeUnits.EM)).pixels();
    }

    private void initListeners() {
        this.setEditable(true);
        this.getEditor().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || (event.getCode() == KeyCode.DOWN)
                    || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.ENTER
                    || event.getCode().equals(KeyCode.SHIFT) || event.getCode() == KeyCode.CONTROL
                    || event.isControlDown() || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
                    || event.getCode() == KeyCode.TAB || event.getCode().isFunctionKey()) {

                return;
            }

            performSearch();
        });

        this.getEditor().setOnMouseClicked(event -> {
            Scene scene = this.getScene();

            if (scene != null) {
                try {
                    Region root = Region.class.cast(scene.getRoot());

                    if (root != null) {
                        root.requestFocus();

                        this.getEditor().requestFocus();
                        this.getEditor().end();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.getEditor().focusedProperty().addListener((__, ___, isFocused) -> {
            if (!isSelectedElementEntity()) {
                getEditor().setText(null);

                performSearch();
            }

            if (isFocused) {
                this.show();

                this.skin.msResize(this.getItems().size(), this.getRowHeight(), this.popupMinHeight, this.popupMaxHeight);
            } else
                this.hide();
        });
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

    public Function<String, Collection<T>> getEntitiesLoader() {
        return this.entitiesLoader;
    }

    public void setEntitiesLoader(Function<String, Collection<T>> entitiesLoader) {
        this.entitiesLoader = entitiesLoader;
    }

    public Double getPopupMaxHeight() {
        return this.popupMaxHeight;
    }

    public Double getPopupMinHeight() {
        return this.popupMinHeight;
    }

    public void setPopupMaxHeight(Double popupMaxHeight) {
        this.popupMaxHeight = popupMaxHeight;
    }

    public void setPopupMinHeight(Double popupMinHeight) {
        this.popupMinHeight = popupMinHeight;
    }

    public Double getRowHeight() {
        return this.rowHeight;
    }

    public void setRowHeight(Double rowHeight) {
        this.rowHeight = rowHeight;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        this.skin = new MSComboBoxListViewSkin<>(this);
        this.skin.msResize(this.getItems().size(), this.getRowHeight(), this.popupMinHeight, this.popupMaxHeight);

        return this.skin;
    }

    public class MSComboBoxListViewSkin<S> extends ComboBoxListViewSkin<S> {
        public MSComboBoxListViewSkin(final ComboBox<S> comboBox) {
            super(comboBox);
        }

        public void msResize(int rows, double rowHeight, double minHeight, double maxHeight) {
            double height = Math.min(rows * rowHeight, maxHeight);
            height = Math.max(height, minHeight);

            this.getListView().setMinHeight(height);
            this.getListView().setMaxHeight(height);
            this.getListView().setPrefHeight(height);
            this.getListView().resize(this.getListView().getWidth(), height);

            double oldHeight = getHeight();

            setHeight(0);
            setHeight(oldHeight);
            setMaxWidth(getMaxAllowedWidth());
            this.getPopup().setMaxWidth(getMaxAllowedWidth());
            this.getListView().setMaxWidth(getMaxAllowedWidth());
            this.getEditor().setMaxWidth(getMaxAllowedWidth());
        }
    }
}
