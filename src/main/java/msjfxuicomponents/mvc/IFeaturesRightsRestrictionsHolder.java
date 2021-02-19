package msjfxuicomponents.mvc;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

import DomainModel.ICompte;
import DomainModel.ISoftwareFeatureType;
import javafx.beans.property.BooleanProperty;
import msdatabaseutils.ITypeDroit;
import msjfxuicomponents.mvc.mslogin.MSLoginController;

public interface IFeaturesRightsRestrictionsHolder extends RestrictionsHolder, IFeaturesRestrictionHolder {

	public default void applyFeaturesRightsRestrictions() {
		ICompte mainAccount = MSLoginController.LOGGED_IN_COMPTE;

		HashMap<BooleanProperty, ITypeDroit> disabilityRightRestrictionsDefinitions = this
				.getDisabilityRestrictionsDefinitions();
		HashMap<BooleanProperty, ISoftwareFeatureType> disabilityFeaturesRestrictionsDefinitions = this
				.getFeaturesDisabilityRestrictionsDefinitions();

		Collection<BooleanProperty> rightsDisabilityProperties = disabilityRightRestrictionsDefinitions.keySet();
		Collection<BooleanProperty> featuresDisabilityProperties = disabilityFeaturesRestrictionsDefinitions.keySet();

		Stream.concat(rightsDisabilityProperties.stream(), featuresDisabilityProperties.stream()).distinct()
				.forEach(property -> {
					boolean hasFeatureRule = featuresDisabilityProperties.contains(property);
					boolean hasRightRule = rightsDisabilityProperties.contains(property);
					boolean propertyValue = true;

					if (hasFeatureRule) {
						ISoftwareFeatureType requiredFeatureType = disabilityFeaturesRestrictionsDefinitions
								.get(property);

						propertyValue &= IFeaturesRightsRestrictionsHolder.this
								.hasRightForEnability(requiredFeatureType);
					}

					if (hasRightRule) {
						ITypeDroit requiredPrevilege = disabilityRightRestrictionsDefinitions.get(property);

						propertyValue &= mainAccount.isAdmin() || this.hasDroit(requiredPrevilege, mainAccount);
					}

					property.set(!propertyValue);
				});

		// ----------------------------------------------------------------------------------------------------

		HashMap<BooleanProperty, ISoftwareFeatureType> visibilityFeaturesRestrictionsDefinitions = this
				.getFeaturesVisibilityRestrictionsDefinitions();
		HashMap<BooleanProperty, ITypeDroit> visibilityRightRestrictionsDefinitions = this
				.getVisibilityRestrictionsDefinitions();

		Collection<BooleanProperty> featuresVisibilityProperties = visibilityFeaturesRestrictionsDefinitions.keySet();
		Collection<BooleanProperty> rightsVisibilityProperties = visibilityRightRestrictionsDefinitions.keySet();

		Stream.concat(featuresVisibilityProperties.stream(), rightsVisibilityProperties.stream()).distinct()
				.forEach(property -> {
					boolean hasFeatureRule = featuresVisibilityProperties.contains(property);
					boolean hasRightRule = rightsVisibilityProperties.contains(property);
					boolean propertyValue = true;

					if (hasFeatureRule) {
						ISoftwareFeatureType requiredFeatureType = visibilityFeaturesRestrictionsDefinitions
								.get(property);

						propertyValue &= IFeaturesRightsRestrictionsHolder.this
								.hasRightForVisilibity(requiredFeatureType);
					}

					if (hasRightRule) {
						ITypeDroit requiredPrevilege = visibilityRightRestrictionsDefinitions.get(property);

						propertyValue &= mainAccount.isAdmin() || this.hasDroit(requiredPrevilege, mainAccount);
					}

					property.set(propertyValue);
				});

		// -------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------

		HashMap<BooleanProperty, ISoftwareFeatureType> editabilityMap = this
				.getFeaturesEditabilityRestrictionsDefinitions();

		Collection<BooleanProperty> featuresEditabilityProperties = editabilityMap.keySet();

		for (BooleanProperty property : featuresEditabilityProperties) {
			ISoftwareFeatureType requiredFeatureType = editabilityMap.get(property);

			boolean hasRightForEditability = this.hasRightForEnability(requiredFeatureType);

			property.set(hasRightForEditability);
		}

		// -------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------

		this.applyFeaturesRemoving();
	}
}
