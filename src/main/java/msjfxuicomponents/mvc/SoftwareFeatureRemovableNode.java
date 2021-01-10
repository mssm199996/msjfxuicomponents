package msjfxuicomponents.mvc;

import java.util.Collection;
import java.util.function.BiConsumer;

import DomainModel.ISoftwareFeatureType;
import javafx.event.EventTarget;

public class SoftwareFeatureRemovableNode {

	private EventTarget node;
	private Collection<? extends EventTarget> parentCollection;
	private ISoftwareFeatureType softwareFeatureType;
	private BiConsumer<Collection<? extends EventTarget>, EventTarget> adder;

	public EventTarget getNode() {
		return node;
	}

	public void setNode(EventTarget node) {
		this.node = node;
	}

	public Collection<? extends EventTarget> getParentCollection() {
		return parentCollection;
	}

	public void setParentCollection(Collection<? extends EventTarget> parentCollection) {
		this.parentCollection = parentCollection;
	}

	public ISoftwareFeatureType getSoftwareFeatureType() {
		return softwareFeatureType;
	}

	public void setSoftwareFeatureType(ISoftwareFeatureType softwareFeatureType) {
		this.softwareFeatureType = softwareFeatureType;
	}

	public BiConsumer<Collection<? extends EventTarget>, EventTarget> getAdder() {
		return adder;
	}

	public void setAdder(BiConsumer<Collection<? extends EventTarget>, EventTarget> adder) {
		this.adder = adder;
	}
}
