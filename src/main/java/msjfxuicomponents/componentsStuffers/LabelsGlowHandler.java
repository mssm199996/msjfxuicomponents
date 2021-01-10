package msjfxuicomponents.componentsStuffers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

public class LabelsGlowHandler {

	private AnimationTimer animator = null;
	private List<Node> nodes = new ArrayList<Node>();

	private InnerShadow innerShadow = new InnerShadow();
	private Glow glow = new Glow();
	private int frequency = 1000;

	public LabelsGlowHandler() {		
		this.intInnerShadow();
		this.initGlow();
		this.startIt();
	}
	
	private void intInnerShadow(){
		innerShadow.setColor(Color.web("#48ff00"));
		innerShadow.setChoke(0.0);
		innerShadow.setHeight(80.0);
		innerShadow.setWidth(80.0);
		innerShadow.setRadius(39.5);
		innerShadow.setBlurType(BlurType.THREE_PASS_BOX);
		innerShadow.setOffsetX(0.0);
		innerShadow.setOffsetY(0.0);
	}
	
	private void initGlow(){
		glow.setLevel(0.7);
		glow.setInput(innerShadow);
	}
	
	public void addNodes(Collection<Node> nodes){
		this.nodes.addAll(nodes);
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	public void startIt(){
		animator = new AnimationTimer() {
			private long lastVisit = 0;
			private boolean isGlow = false;

			@Override
			public void handle(long now) {
				long dt = now - this.lastVisit;

				if (dt >= frequency * 1_000_000) {
					if (this.isGlow)
						for (Node node : nodes){
							if(node != null)
								node.setEffect(glow);
						}
					else 
						for (Node node : nodes){
							if(node != null)
								node.setEffect(null);
							}
					
					this.isGlow = !this.isGlow;
					this.lastVisit = now;
				}
			}
		};
		
		animator.start();
	}
}
