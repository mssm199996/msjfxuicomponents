package msjfxuicomponents.mvc.mslogin;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.List;

import DomainModel.ICompte;
import msdatabaseutils.ICompteValidator;
import org.hibernate.HibernateException;

import DomainModel.IConfiguration;
import DomainModel.ISoftwareFeatureType;
import MainPackage.CopyrightHandler;
import javafx.application.Platform;
import javafx.stage.Modality;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.SessionFactoryHandler;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.MainApplicationStage;
import msjfxuicomponents.mvc.SimpleStageType;
import msjfxuicomponents.mvc.msdatabaserepair.MSHibernateDatabaseRepairStage;
import msjfxuicomponents.mvc.networkscanner.MSMySQLNetworkScannerStage;
import mssoftutils.update.SoftwareVersionDownloader.Software;

public abstract class MSLoginStage<S extends MainApplicationStage<?>> extends SimpleStageType<MSLoginController> {

	public MSLoginStage(String title) throws MalformedURLException, URISyntaxException {

		super(title, "/msjfxuicomponents/mvc/mslogin/MSLogin.fxml");

		this.getController().setApplicationName(title);
		this.getController().setMsLoginStage(this);

		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setOnCloseRequest(event -> {
			System.exit(0);
		});

		try {
			new MSHibernateDatabaseRepairStage(title);

			MSMySQLNetworkScannerStage myScannerStage = new MSMySQLNetworkScannerStage(title);
			myScannerStage.initServer();

			this.show();
		} catch (SocketException e) {
			e.printStackTrace();

			Platform.runLater(() -> {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayCapitalErrorAlert(title, "Erreur de lancement",
						"Il éxiste déja une instance du logiciel en cours d'éxécution");

				System.exit(0);
			});
		}

		MSJFXUIComponentsHolder.MS_LOGIN_STAGE = this;
	}

	public abstract SessionFactoryHandler onFirstLoginInitSessionFactoryHandler()
			throws HibernateException, MalformedURLException;

	public abstract CopyrightHandler onFirstLoginInitCopyrightHandler();

	public abstract ICompteValidator onFirstLoginInitCompteValidator();

	public abstract IConfiguration onFirstLoginInitConfiguration();

	public abstract IOperationTypeDAO<?> onFirstLoginInitOperationTypeDAO();

	public abstract List<? extends ISoftwareFeatureType> softwareFeatureTypes();

	public abstract Software getSoftware();

	public abstract S onFirstLoginConstructMainStage();

	public abstract void setMainCompte(ICompte compte);

	public void onLoggedInSuccesfulled(ICompte compte) {

	}
}
