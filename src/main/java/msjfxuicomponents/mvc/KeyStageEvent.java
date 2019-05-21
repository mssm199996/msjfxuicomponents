package msjfxuicomponents.mvc;

import javafx.scene.input.KeyEvent;

public interface KeyStageEvent {
	public abstract void onKeyReleasedStageEvent(KeyEvent event);
	public abstract void onKeyPressedStageEvent(KeyEvent event);
}
