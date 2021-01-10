package msjfxuicomponents.uicomponents;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Node;

public class SimpleMSBoostrapCard extends MSBootstrapCard {

	@Override
	protected boolean isAvailable() {
		return false;
	}

	@Override
	protected Node getBottomRightMoseComponent() {
		return new FontAwesomeIconView(FontAwesomeIcon.CHECK);
	}
}
