package msjfxuicomponents.componentsStuffers;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;

public class ScalableContentPane extends Region {

	private Scale contentScaleTransform;
	private Node contentPaneProperty = null;
	private double contentScaleWidth = 1.0;
	private double contentScaleHeight = 1.0;
	private boolean autoRescale = true;

	public ScalableContentPane(Node content, double xScale, double yScale) {
		this.contentPaneProperty = content;
		this.contentScaleTransform = new Scale(xScale, yScale);
		this.contentScaleWidth = xScale;
		this.contentScaleHeight = yScale;

		this.needsLayoutProperty().addListener((ov, oldV, newV) -> {
			if (newV && (getWidth() <= getPrefWidth() || getHeight() <= getPrefHeight())
					|| getPrefWidth() == USE_COMPUTED_SIZE || getPrefHeight() == USE_COMPUTED_SIZE) {
				computeScale();
			}
		});

		this.initContentPaneListener();
		this.getContentScaleTransform().setPivotX(0);
		this.getContentScaleTransform().setPivotY(0);
		this.getContentScaleTransform().setPivotZ(0);
		this.getContent().getTransforms().add(getContentScaleTransform());

		this.getChildren().add(content);
		this.getContentScaleTransform().setOnTransformChanged((event) -> {
			requestLayout();
			setNeedsLayout(false);
		});
	}

	private void initContentPaneListener() {
		final ChangeListener<Bounds> boundsListener = (ov, oldValue, newValue) -> {
			if (isAutoRescale()) {
				if (getContent() instanceof Region) {
					((Region) getContent()).requestLayout();
				}

				requestLayout();
				setNeedsLayout(false);
			}
		};

		final ChangeListener<Number> numberListener = (ov, oldValue, newValue) -> {
			if (isAutoRescale()) {
				if (getContent() instanceof Parent) {
					((Parent) getContent()).requestLayout();
				}

				requestLayout();
				setNeedsLayout(false);
			}
		};

		if (getContent() instanceof Pane) {
			((Pane) getContent()).getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
				while (c.next()) {
					if (c.wasRemoved()) {
						for (Node n : c.getRemoved()) {
							n.boundsInLocalProperty().removeListener(boundsListener);
							n.layoutXProperty().removeListener(numberListener);
							n.layoutYProperty().removeListener(numberListener);
						}
					} else if (c.wasAdded()) {
						for (Node n : c.getAddedSubList()) {
							n.boundsInLocalProperty().addListener(boundsListener);
							n.layoutXProperty().addListener(numberListener);
							n.layoutYProperty().addListener(numberListener);
						}
					}

				}
			});
		}
	}

	private void computeScale() {
		double leftAndRight = this.getInsets().getLeft() + this.getInsets().getRight();
		double topAndBottom = this.getInsets().getTop() + this.getInsets().getBottom();

		double contentWidth = this.getLayoutBounds().getWidth() - leftAndRight;
		double contentHeight = this.getLayoutBounds().getHeight() - topAndBottom;

		this.getContentScaleTransform().setX(this.contentScaleWidth);
		this.getContentScaleTransform().setY(this.contentScaleHeight);

		double resizeScaleW = this.getContentScaleTransform().getX();
		double resizeScaleH = this.getContentScaleTransform().getY();

		this.getContent().relocate(this.getInsets().getLeft(), this.getInsets().getTop());

		double realContentWidth = contentWidth / resizeScaleW;
		double realContentHeight = contentHeight / resizeScaleH;

		this.getContent().resize(realContentWidth, realContentHeight);
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		this.computeScale();
	}

	@Override
	protected double computeMinWidth(double d) {
		double result = getInsets().getLeft() + getInsets().getRight();

		result += getContent().prefWidth(d) * contentScaleWidth;

		return result;
	}

	@Override
	protected double computeMinHeight(double d) {
		double result = getInsets().getTop() + getInsets().getBottom();

		result += getContent().prefHeight(d) * contentScaleWidth;

		return result;
	}

	@Override
	protected double computePrefWidth(double d) {
		double result = getInsets().getLeft() + getInsets().getRight();

		result += getContent().prefWidth(d) * contentScaleWidth;

		return result;
	}

	@Override
	protected double computePrefHeight(double d) {
		double result = getInsets().getTop() + getInsets().getBottom();

		result += getContent().prefHeight(d) * contentScaleHeight;

		return result;
	}

	@Deprecated
	public void requestScale() {
		computeScale();
	}

	public boolean isAutoRescale() {
		return autoRescale;
	}

	public void setAutoRescale(boolean autoRescale) {
		this.autoRescale = autoRescale;
	}

	public Node getContent() {
		return contentPaneProperty;
	}

	public Scale getContentScaleTransform() {
		return contentScaleTransform;
	}

	public void setContent(Node content) {
		this.contentPaneProperty = content;
	}
}
