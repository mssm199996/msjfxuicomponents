package msjfxuicomponents.mvc;

import java.util.Collection;
import java.util.HashMap;

import DomainModel.Droit;
import javafx.beans.property.BooleanProperty;
import msdatabaseutils.ITypeDroit;
import msjfxuicomponents.mvc.mslogin.MSLoginController;
import msjfxuicomponents.others.ICompte;

public interface RestrictionsHolder {

	public HashMap<BooleanProperty, ITypeDroit> getDisabilityRestrictionsDefinitions();

	public HashMap<BooleanProperty, ITypeDroit> getVisibilityRestrictionsDefinitions();

	public default boolean hasDroit(ITypeDroit typeDroit, ICompte compte) {
		if (compte.isAdmin())
			return true;

		for (Droit droit : compte.getDroits())
			if (droit.hasRight(typeDroit))
				return true;

		return false;
	}

	public default void applyRestriction() {
		ICompte mainAccount = MSLoginController.LOGGED_IN_COMPTE;

		HashMap<BooleanProperty, ITypeDroit> disabilityRestrictionsDefinitions = this
				.getDisabilityRestrictionsDefinitions();
		HashMap<BooleanProperty, ITypeDroit> visibilityRestrictionsDefinitions = this
				.getVisibilityRestrictionsDefinitions();

		Collection<BooleanProperty> disabilityProperties = disabilityRestrictionsDefinitions.keySet();
		Collection<BooleanProperty> visibilityProperties = visibilityRestrictionsDefinitions.keySet();

		for (BooleanProperty disabilityProperty : disabilityProperties) {
			ITypeDroit requiredPrevilege = disabilityRestrictionsDefinitions.get(disabilityProperty);

			boolean hasRightForPrevilege = mainAccount.isAdmin() || this.hasDroit(requiredPrevilege, mainAccount);

			disabilityProperty.setValue(!hasRightForPrevilege);
		}

		for (BooleanProperty visibilityProperty : visibilityProperties) {
			ITypeDroit requiredPrevilege = visibilityRestrictionsDefinitions.get(visibilityProperty);

			boolean hasRightForPrevilege = mainAccount.isAdmin() || this.hasDroit(requiredPrevilege, mainAccount);

			visibilityProperty.setValue(hasRightForPrevilege);
		}
	}
}
