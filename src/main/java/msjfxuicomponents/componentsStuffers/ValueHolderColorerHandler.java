package msjfxuicomponents.componentsStuffers;

import javafx.beans.value.ObservableValue;
import javafx.css.Styleable;

public class ValueHolderColorerHandler {

    public static <T> void registerValueObserverColorer(Styleable styleable, ObservableValue<T> observableValue,
                                                        String cssClassIfValueAvailable, String cssClassIfValueNotAvailable) {
        observableValue.addListener((__, ___, value) -> {
            boolean isEmpty = (value == null) || (value instanceof String && value.equals("")) ||
                    (value instanceof Boolean && value.equals(false));

            if (!isEmpty) {
                styleable.getStyleClass().remove(cssClassIfValueNotAvailable);

                if (!styleable.getStyleClass().contains(cssClassIfValueAvailable))
                    styleable.getStyleClass().add(cssClassIfValueAvailable);
            } else {
                styleable.getStyleClass().remove(cssClassIfValueAvailable);

                if (!styleable.getStyleClass().contains(cssClassIfValueNotAvailable))
                    styleable.getStyleClass().add(cssClassIfValueNotAvailable);
            }
        });
    }

    public static <T> void registerLightCyanBackgroundValueObserverColorer(Styleable styleable,
                                                                           ObservableValue<T> observableValue) {

        ValueHolderColorerHandler.registerValueObserverColorer(styleable, observableValue, "cyan-background-color",
                "default-background-color");
    }

    public static <T> void registerLightYellowBackgroundValueObserverColorer(Styleable styleable,
                                                                           ObservableValue<T> observableValue) {

        ValueHolderColorerHandler.registerValueObserverColorer(styleable, observableValue, "yellow-background-color",
                "default-background-color");
    }
}
