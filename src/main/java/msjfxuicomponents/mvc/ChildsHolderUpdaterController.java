package msjfxuicomponents.mvc;

import java.util.Set;

public interface ChildsHolderUpdaterController<C> {

	public abstract Set<C> getAddedChildsSet();

	public abstract Set<C> getUpdatedChildsSet();

	public abstract Set<C> getRemovedChildsSet();

	public default void addChildToParent(C child) {
		this.setParentAttibuteInChildToParent(child);
		this.getAddedChildsSet().add(child);
	}
	
	public default void clearCollections(){
		this.getAddedChildsSet().clear();
		this.getRemovedChildsSet().clear();

		if(this.getUpdatedChildsSet() != null)
			this.getUpdatedChildsSet().clear();
	}

	public default void deleteChildFromParent(C child, Integer childId) {
		this.getAddedChildsSet().remove(child);

		if (this.getUpdatedChildsSet() != null)
			this.getUpdatedChildsSet().remove(child);

		if (childId != 0)
			this.getRemovedChildsSet().add(child);
	}

	public default void updateChild(C Child) {
		this.getUpdatedChildsSet().add(Child);
	}

	public abstract void setParentAttibuteInChildToParent(C child);
}
