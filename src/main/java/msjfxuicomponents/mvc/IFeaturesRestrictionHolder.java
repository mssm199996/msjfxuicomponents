package msjfxuicomponents.mvc;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

import DomainModel.ISoftwareFeatureType;
import DomainModel.SoftwareFeature;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventTarget;
import msjfxuicomponents.mvc.mssoftwarefeatures.MSSoftwareFeaturesController;

public interface IFeaturesRestrictionHolder {

	public HashMap<BooleanProperty, ISoftwareFeatureType> getFeaturesVisibilityRestrictionsDefinitions();

	public HashMap<BooleanProperty, ISoftwareFeatureType> getFeaturesDisabilityRestrictionsDefinitions();

	public HashMap<BooleanProperty, ISoftwareFeatureType> getFeaturesEditabilityRestrictionsDefinitions();

	public default Collection<SoftwareFeatureRemovableNode> getFeaturesRemovabilityRestrictionsDefinitions() {
		return new HashSet<>();
	}

	public default boolean hasRightForVisilibity(ISoftwareFeatureType softwareFeatureType) {
		for (SoftwareFeature softwareFeature : MSSoftwareFeaturesController.SOFTWARE_FEATURES)
			if (softwareFeature.compareToISoftwareFeatureType(softwareFeatureType))
				return softwareFeature.getVisiblity();

		return false;
	}

	public default boolean hasRightForEnability(ISoftwareFeatureType softwareFeatureType) {
		for (SoftwareFeature softwareFeature : MSSoftwareFeaturesController.SOFTWARE_FEATURES)
			if (softwareFeature.compareToISoftwareFeatureType(softwareFeatureType))
				return softwareFeature.getEnability();

		return false;
	}

	public default void applyFeaturesRestriction() {
		HashMap<BooleanProperty, ISoftwareFeatureType> disabilityMap = this
				.getFeaturesDisabilityRestrictionsDefinitions();
		HashMap<BooleanProperty, ISoftwareFeatureType> visiblityMap = this
				.getFeaturesVisibilityRestrictionsDefinitions();
		HashMap<BooleanProperty, ISoftwareFeatureType> editabilityMap = this
				.getFeaturesEditabilityRestrictionsDefinitions();

		// -------------------------------------------------------------------------------------

		Collection<BooleanProperty> disabilityProperties = disabilityMap.keySet();

		for (BooleanProperty property : disabilityProperties) {
			ISoftwareFeatureType requiredFeatureType = disabilityMap.get(property);

			boolean hasRightForEnability = this.hasRightForEnability(requiredFeatureType);

			property.set(!hasRightForEnability);
		}

		// -------------------------------------------------------------------------------------

		Collection<BooleanProperty> visibilityProperties = visiblityMap.keySet();

		for (BooleanProperty property : visibilityProperties) {
			ISoftwareFeatureType requiredFeatureType = visiblityMap.get(property);

			boolean hasRightForVisibility = this.hasRightForVisilibity(requiredFeatureType);

			property.set(hasRightForVisibility);
		}

		// -------------------------------------------------------------------------------------

		Collection<BooleanProperty> editabilityProperties = editabilityMap.keySet();

		for (BooleanProperty property : editabilityProperties) {
			ISoftwareFeatureType requiredFeatureType = editabilityMap.get(property);

			boolean hasRightForEditability = this.hasRightForEnability(requiredFeatureType);

			property.set(hasRightForEditability);
		}

		this.applyFeaturesRemoving();
	}

	public default void applyFeaturesRemoving() {
		Collection<SoftwareFeatureRemovableNode> nodes = this.getFeaturesRemovabilityRestrictionsDefinitions();

		for (SoftwareFeatureRemovableNode softwareFeatureRemovableNode : nodes) {
			ISoftwareFeatureType requiredFeatureType = softwareFeatureRemovableNode.getSoftwareFeatureType();

			boolean hasRightForVisibility = this.hasRightForVisilibity(requiredFeatureType);

			Collection<? extends EventTarget> parentCollection = softwareFeatureRemovableNode.getParentCollection();
			EventTarget node = softwareFeatureRemovableNode.getNode();

			if (!hasRightForVisibility && parentCollection.contains(node))
				parentCollection.remove(node);
			else if (hasRightForVisibility && !parentCollection.contains(node)) {
				BiConsumer<Collection<? extends EventTarget>, EventTarget> consumer = softwareFeatureRemovableNode
						.getAdder();
				consumer.accept(parentCollection, node);
			}
		}
	}
}
