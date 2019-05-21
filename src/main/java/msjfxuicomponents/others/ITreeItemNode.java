package msjfxuicomponents.others;

public interface ITreeItemNode<P> {

	public abstract P getParent();

	public abstract String getStringRepresentation();

	public abstract void setStringRepresention(String newValue);
}
