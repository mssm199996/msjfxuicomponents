package msjfxuicomponents.componentsStuffers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public abstract class DragNDropHandler {
	public void initiateDragNDrop(Node source, Node destination) {
		source.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Dragboard db = source.startDragAndDrop(TransferMode.ANY);

				ClipboardContent content = new ClipboardContent();
				content.putString("Draaaaaaag meeehhh !");
				db.setContent(content);

				event.consume();
			}
		});

		destination.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != destination && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			}
		});

		destination.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != destination && event.getDragboard().hasString()) {
					destination.setStyle("-fx-border-color: rgba(0, 255, 0, 0.75)");
				}

				event.consume();
			}
		});

		destination.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				destination.setStyle("-fx-border-color: rgba(0, 255, 0, 0)");

				event.consume();
			}
		});

		destination.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasString()) {
					performDragAchievement();

					success = true;
				}
				event.setDropCompleted(success);

				event.consume();
			}
		});

		source.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getTransferMode() == TransferMode.MOVE) {
				}
				event.consume();
			}
		});
	}

	public abstract void performDragAchievement();
}
