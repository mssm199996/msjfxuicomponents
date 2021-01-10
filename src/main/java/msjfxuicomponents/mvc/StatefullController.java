package msjfxuicomponents.mvc;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface StatefullController {

	public abstract Map<String, Supplier<String>> statefullUIComponentNameToValueRetrieverMap();

	public abstract Map<String, Consumer<String>> statefullUIComponentNameToValueModifiarMap();

}
