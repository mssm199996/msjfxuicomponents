package msjfxuicomponents.mvc.mssoftwarefeatures;

import java.util.List;

import DomainModel.ISoftwareFeatureType;
import MainPackage.CopyrightHandler;
import javafx.stage.Modality;
import msdatabaseutils.SoftwareFeatureDAO;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSSoftwareFeaturesStage extends SimpleStageType<MSSoftwareFeaturesController> {

	public MSSoftwareFeaturesStage(List<? extends ISoftwareFeatureType> initialData,
			SoftwareFeatureDAO softwareFeatureDAO, CopyrightHandler copyrightHandler, String title) {
		super(title, "/msjfxuicomponents/mvc/mssoftwarefeatures/MSSoftwareFeatures.fxml");

		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.initModality(Modality.APPLICATION_MODAL);

		this.getController().setCopyrightHandler(copyrightHandler);
		this.getController().setSoftwareFeatureDao(softwareFeatureDAO);
		this.getController().fillData(initialData);

		MSJFXUIComponentsHolder.MS_SOFTWARE_FEATURES_STAGE = this;
	}

	public void askPasswordAndShow() {
		String password = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayPasswordAlert(this.getTitle(),
				"Modification des fonctionnalitées",
				"Veuillez saisir le mot de passe pour le changement des fonctionnalitées", "");

		if (password != null && password.equals("mssm1996nticetsbenhabib"))
			this.show();
		else if (password != null) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getTitle(),
					"Modification des fonctionnalitées",
					"Mot de passe incorrect ! (cette partie du logiciel n'est accessible que par le fournisseur !)",
					null);
		}
	}

	public void initCopyrightHandlerFeatures() {
		this.getController().initCopyrightHandlerFeatures();
	}
}
