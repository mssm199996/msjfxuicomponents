package msjfxuicomponents.componentsStuffers;

import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import msjfxuicomponents.others.ITreeItemNode;

public class TreeViewConstructor<T extends ITreeItemNode<T>> {

	public TreeItem<T> constructRootElement(List<? extends T> dataSource) {
		TreeItem<T> rootElement = new TreeItem<T>();

		HashMap<T, TreeItem<T>> tree = new HashMap<T, TreeItem<T>>();

		for (T itemToAdd : dataSource)
			this.addElementToTree(rootElement, itemToAdd, tree);

		return rootElement;
	}

	private TreeItem<T> addElementToTree(TreeItem<T> rootElement, T itemToAdd, HashMap<T, TreeItem<T>> tree) {
		TreeItem<T> myTreeItem = tree.get(itemToAdd);

		boolean elementHasBeenAdded = myTreeItem != null;

		// if i'm a son of the root element and i'ven't been added yet
		if (itemToAdd.getParent() == null && !elementHasBeenAdded) {
			// I add myself directly (don't need any kind of recursivity)

			TreeItem<T> subRootElement = new TreeItem<T>();
			subRootElement.setExpanded(true);
			subRootElement.setValue(itemToAdd);

			tree.put(itemToAdd, subRootElement);

			rootElement.getChildren().add(subRootElement);

			return subRootElement;
		}

		// if i'm not the son of the root and i'ven't been added yet (which
		// implicate that my parent may or NOT have been added)
		else if (itemToAdd.getParent() != null && !elementHasBeenAdded) {
			// i've to start adding my parent first (which may have already been
			// added)
			TreeItem<T> myParent = this.addElementToTree(rootElement, itemToAdd.getParent(), tree);

			// now it's my turn
			TreeItem<T> mySelfElement = new TreeItem<T>();
			mySelfElement.setExpanded(true);
			mySelfElement.setValue(itemToAdd);

			tree.put(itemToAdd, mySelfElement);

			myParent.getChildren().add(mySelfElement);
		}

		return myTreeItem;
	}

	public void removeElement(TreeItem<T> rootElement, TreeItem<T> elementToRemove) {
		if (rootElement != null && elementToRemove != null) {

			ObservableList<TreeItem<T>> children = rootElement.getChildren();

			// we remove occurencies of elementToRemove from child trees first
			for (TreeItem<T> child : children)
				this.removeElement(child, elementToRemove);

			// now remove all occurencies of elementToRemove in rootElement's
			// childs
			@SuppressWarnings({ "unchecked", "unused" })
			boolean removeState = rootElement.getChildren().removeAll(elementToRemove);
		}
	}
}
