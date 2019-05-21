package msjfxuicomponents.componentsStuffers;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.util.Duration;

public class ComponentsUpdateGlower {
	private List<Node> nodes = new ArrayList<Node>();
	private int frequency = 250;
	private double from = 1.0;
	private double to = 0.3;
	private boolean autoReverse = true;

	public <T> void registerAnimation(Node animationTarget, ReadOnlyProperty<T> property) {
		Animation animation = this.constructAnimation(animationTarget);

		property.addListener(new ChangeListener<T>() {
			@Override
			public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
				animate(animationTarget, animation);
			}
		});
		animationTarget.addEventHandler(ActionEvent.ACTION, event -> {
			stop(animationTarget, animation);
		});
	}

	public <T> void registerAnimation(Node animationTarget, Node eventSource, EventType<?> eventType) {
		Animation animation = this.constructAnimation(animationTarget);

		eventSource.addEventHandler(eventType, event -> {
			animate(animationTarget, animation);
		});
		animationTarget.addEventHandler(ActionEvent.ACTION, event -> {
			stop(animationTarget, animation);
		});
	}

	private Animation constructAnimation(Node animationTarget) {
		FadeTransition ft = new FadeTransition(Duration.millis(this.frequency));
		ft.setFromValue(this.from);
		ft.setToValue(this.to);
		ft.setAutoReverse(this.autoReverse);
		ft.setCycleCount(FadeTransition.INDEFINITE);
		ft.setNode(animationTarget);

		return ft;
	}

	private void animate(Node animationTarget, Animation animation) {
		if (!nodes.contains(animationTarget)) {
			nodes.add(animationTarget);

			animation.play();
		}
	}

	private void stop(Node animationTarget, Animation animation) {
		this.nodes.remove(animationTarget);

		animation.stop();

		animationTarget.setOpacity(1.0);
	}
}
