package msjfxuicomponents.mvc;

public interface ResetController {
	public abstract void onShowingResetResult();
	public default void onShownResetResult(){}
	public default void onHidingResetResul(){}
	public default void onCloseRequestResetResult(){}
}
