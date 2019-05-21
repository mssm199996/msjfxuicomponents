package msjfxuicomponents.mvc;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public interface SplitableController<S extends Enum<S>> {

	public default void splitInterface() {
		S splitter = this.getSelectedSplitter();

		for (S noneSplitter : this.getSplitterMap().keySet()) {
			Node[] visibleNodes = this.getSplitterMap().get(noneSplitter);

			// Si ce n'est pas les elements � supprimer => on les remet � leurs
			// place
			if (!noneSplitter.equals(splitter)) {
				for (Node node : visibleNodes) {
					Entry<Pane, Integer> parentEntry = this.getParentsMap().get(node);

					// On remet les elements � leurs place seulements s'ils
					// n'ont pas �t� enlev� �t qu'ils n'y sont pas d�ja
					if (parentEntry != null) {
						Pane parent = parentEntry.getKey();

						if (!parent.getChildren().contains(node)) {
							Integer index = parentEntry.getValue();
							index = Math.min(index, parent.getChildren().size());

							parent.getChildren().add(index, node);
						}
					}
				}
			}
			// c'est un element � cacher => on le cache s'il n'est pas d�ja
			// cach�
			// et on sauvegarde son parent s'il n'a pas �t� sauvegard� pour le
			// remetre � sa place plus tard
			else {
				for (Node node : visibleNodes) {
					Pane parent = (Pane) node.getParent();

					if (parent != null) {
						if (!this.getParentsMap().containsKey(node))
							this.getParentsMap().put(node,
									new SimpleEntry<Pane, Integer>(parent, parent.getChildren().indexOf(node)));
					}
				}
				for (Node node : visibleNodes) {
					Pane parent = (Pane) node.getParent();

					if (parent != null)
						parent.getChildren().remove(node);
				}
			}
		}

		Runnable runnable = this.getRunnablesMap().get(splitter);
		runnable.run();
	}

	public abstract LinkedHashMap<S, Node[]> getSplitterMap();

	public abstract LinkedHashMap<Node, SimpleEntry<Pane, Integer>> getParentsMap();

	public abstract LinkedHashMap<S, Runnable> getRunnablesMap();

	public abstract S getSelectedSplitter();
}
