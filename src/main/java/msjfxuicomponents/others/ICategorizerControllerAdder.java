package msjfxuicomponents.others;

import javafx.stage.Stage;
import msdatabaseutils.ICategorizer;

public interface ICategorizerControllerAdder<T extends ICategorizer> {

	public abstract Stage getStage();

	public abstract T getConstructedCategorizer();
}
