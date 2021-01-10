package msjfxuicomponents.mvc.compositions;

import java.util.Map;

public interface FromAbtractionsComposedController extends ComposedController {

	public abstract Map<String, AbstractNestedController<?>> nestedComponentsControllers();
}
