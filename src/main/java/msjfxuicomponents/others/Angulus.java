/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msjfxuicomponents.others;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Angulus extends AnchorPane {
	private Arc arc;
	private Line l1, l2;
	private Circle c0, c1, c2;
	private Text lbangle;
	private boolean dragContainer;

	public Angulus() {
		this.initStyle();
		this.initSelfListeners();
		this.initCercles();
		this.init2D();
		this.initLines();
		this.initLabels();
		this.addChildrens();

		this.moveFirstCircle(10, 10);
		this.moveSecondCircle(10, 100);

		this.dragContainer = true;
	}

	// ------------------------- Init functions ----------------------------

	private void initStyle() {
		this.setStyle("-fx-background-color: transparent;");
	}

	private void initSelfListeners() {
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				Pane parent = (Pane) this.getParent();

				parent.getChildren().remove(this);
			}
		});

		this.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
			if (this.dragContainer) {
				setManaged(false);

				this.setTranslateX(event.getX() + this.getTranslateX() - this.c0.getCenterX());
				this.setTranslateY(event.getY() + this.getTranslateY() - this.c0.getCenterY());
			}

			event.consume();
		});

		this.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			this.setCursor(Cursor.MOVE);
		});

		this.setOnScroll(event -> {
			if (event.getDeltaY() > 0)
				this.changeColors(ChangeColorType.DARKER);
			else
				this.changeColors(ChangeColorType.BRIGHTER);
		});
	}

	private void initCercles() {
		this.c0 = new Circle(55, 55, 5);
		this.c0.setFill(Color.GREEN);
		this.c0.setStroke(Color.GREEN);
		this.c0.setStrokeWidth(2.0);

		this.c1 = new Circle(5);
		this.c1.setFill(Color.BLUE);
		this.c1.setStroke(Color.BLUE);
		this.c1.setCursor(Cursor.CROSSHAIR);
		this.c1.setOnMouseDragged(event -> {
			this.moveFirstCircle(event.getX(), event.getY());
		});

		this.c1.setOnMouseExited(event -> {
			this.dragContainer = true;
		});

		this.c2 = new Circle(5);
		this.c2.setFill(Color.RED);
		this.c2.setStroke(Color.RED);
		this.c2.setCursor(Cursor.CROSSHAIR);
		this.c2.setOnMouseDragged(event -> {
			this.moveSecondCircle(event.getX(), event.getY());
		});

		this.c2.setOnMouseExited(event -> {
			this.dragContainer = true;
		});
	}

	private void init2D() {
		this.arc = new Arc();
		this.arc.centerXProperty().bindBidirectional(this.c0.centerXProperty());
		this.arc.centerYProperty().bindBidirectional(this.c0.centerYProperty());
		this.arc.setRadiusX(20.0);
		this.arc.setRadiusY(20.0);
		this.arc.setFill(Color.TRANSPARENT);
		this.arc.setStroke(Color.BLACK);
		this.arc.setStrokeWidth(2.0);
	}

	private void initLines() {
		this.l1 = new Line();
		this.l1.setStrokeWidth(2.0);
		this.l1.setStroke(Color.BLUE);

		this.l2 = new Line();
		this.l2.setStrokeWidth(2.0);
		this.l2.setStroke(Color.RED);
	}

	private void initLabels() {
		this.lbangle = new Text("180");
		this.lbangle.setLayoutX(this.c0.getCenterX() - this.arc.getRadiusX());
		this.lbangle.setLayoutY(this.c0.getCenterY() - this.arc.getRadiusY());
		this.lbangle.setFill(Color.MAROON);
		this.lbangle.setFont(Font.font("arial", FontWeight.BOLD, 16));
	}

	private void addChildrens() {
		this.getChildren().addAll(this.arc, this.l1, this.l2, this.c0, this.c1, this.c2, this.lbangle);
	}

	// -------------------------------------------------------------------------

	private void moveFirstCircle(double x, double y) {
		this.moveCircle(this.l1, this.c1, x, y);
	}

	private void moveSecondCircle(double x, double y) {
		this.moveCircle(this.l2, this.c2, x, y);
	}

	// ----------------------- Reusable functions ------------------------------

	private void moveCircle(Line line, Circle circle, double x, double y) {
		this.dragContainer = false;

		circle.setCenterX(x);
		circle.setCenterY(y);

		line.setStartX(this.c0.getCenterX());
		line.setStartY(this.c0.getCenterY());
		line.setEndX(circle.getCenterX());
		line.setEndY(circle.getCenterY());

		double angle = this.calculateAngleBetweenLines(this.l2, this.l1);
		double startAngle = this.calculateAngleBetweenLineAndHorizon(this.l1);

		this.arc.setStartAngle(startAngle);
		this.arc.setLength(angle);

		this.initArcLabel(angle);
	}

	private double calculateAngleBetweenLines(Line line1, Line line2) {
		double angle1 = Math.atan2(line1.getEndY() - line1.getStartY(), line1.getStartX() - line1.getEndX());
		double angle2 = Math.atan2(line2.getEndY() - line2.getStartY(), line2.getStartX() - line2.getEndX());

		return this.calculateAngleBetweenAngles(angle1, angle2);
	}

	private double calculateAngleBetweenLineAndHorizon(Line line) {
		double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getStartX() - line.getEndX());
		angle -= Math.PI;

		return this.calculateAngleBetweenAngles(angle, 0);
	}

	private double calculateAngleBetweenAngles(double angle1, double angle2) {
		float dAngle = (float) Math.toDegrees(angle1 - angle2);

		if (dAngle < 0)
			dAngle += 360;

		return Math.round(dAngle);
	}

	private void initArcLabel(double angle) {
		this.lbangle.setText(((int) angle) + "°");
		this.lbangle.setLayoutX(this.c0.getCenterX() - (this.arc.getRadiusX() * 2.8));
		this.lbangle.setLayoutY(this.c0.getCenterY() - 5);
	}

	private void changeColors(ChangeColorType changeColorType) {

		try {
			switch (changeColorType) {
			case BRIGHTER:
				this.setBrighterColorForCircle(this.c0);
				this.setBrighterColorForCircle(this.c1);
				this.setBrighterColorForCircle(this.c2);

				this.setBrighterColorForLine(this.l1);
				this.setBrighterColorForLine(this.l2);

				this.setBrighterColorForArc(this.arc);
				this.setBrighterColorForText(this.lbangle);

				break;
			case DARKER:
				this.setDarkerColorForCircle(this.c0);
				this.setDarkerColorForCircle(this.c1);
				this.setDarkerColorForCircle(this.c2);

				this.setDarkerColorForLine(this.l1);
				this.setDarkerColorForLine(this.l2);

				this.setDarkerColorForArc(this.arc);
				this.setDarkerColorForText(this.lbangle);

				break;
			}
		} catch (Exception e) {
		}
	}

	private void setBrighterColorForText(Text text) {
		Color initialColor = (Color) text.getFill();

		text.setFill(initialColor.brighter());
	}

	private void setDarkerColorForText(Text text) {
		Color initialColor = (Color) text.getFill();

		text.setFill(initialColor.darker());
	}

	private void setBrighterColorForArc(Arc arc) {
		Color initialColor = (Color) arc.getStroke();

		arc.setStroke(initialColor.brighter());
	}

	private void setDarkerColorForArc(Arc arc) {
		Color initialColor = (Color) arc.getStroke();

		arc.setStroke(initialColor.darker());
	}

	private void setBrighterColorForLine(Line line) {
		Color initialColor = (Color) line.getStroke();

		line.setStroke(initialColor.brighter());
	}

	private void setDarkerColorForLine(Line line) {
		Color initialColor = (Color) line.getStroke();

		line.setStroke(initialColor.darker());
	}

	private void setBrighterColorForCircle(Circle circle) {
		Color initialColor = (Color) circle.getFill();

		circle.setFill(initialColor.brighter());
		circle.setStroke(initialColor.brighter().brighter());
	}

	private void setDarkerColorForCircle(Circle circle) {
		Color initialColor = (Color) circle.getFill();

		circle.setFill(initialColor.darker());
		circle.setStroke(initialColor.darker().darker());
	}

	enum ChangeColorType {
		DARKER, BRIGHTER
	}
}
