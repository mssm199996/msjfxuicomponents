package msjfxuicomponents.mvc;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;

public interface KeyStageEvent {
	public abstract void onKeyReleasedStageEvent(KeyEvent event);

	public abstract void onKeyPressedStageEvent(KeyEvent event);

	public abstract MenuButton getKeyShortcutDescriber();

	public LinkedHashMap<String, String> fromKeyCodeToDescriptionsShortcutsMap();

	public default void initDescribers() {
		MenuButton menuButton = this.getKeyShortcutDescriber();

		if (menuButton != null) {
			LinkedHashMap<String, String> map = this.fromKeyCodeToDescriptionsShortcutsMap();
			Set<String> keys = new TreeSet<>();
			map.forEach((key, value) -> keys.add(key));

			menuButton.getStyleClass().add("wellFormattedLittleMediumBlackText");

			keys.forEach(code -> {
				MenuItem menuItem = new MenuItem();
				menuItem.setText(code + ": " + map.get(code));
				menuItem.setStyle("-fx-font-size: 0.6em");

				menuButton.getItems().add(menuItem);
			});
		}
	}
}
