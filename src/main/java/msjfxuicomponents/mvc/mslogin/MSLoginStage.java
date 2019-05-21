package msjfxuicomponents.mvc.mslogin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.hibernate.HibernateException;

import MainPackage.CopyrightHandler;
import javafx.stage.Modality;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.SessionFactoryHandler;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.MainApplicationStage;
import msjfxuicomponents.mvc.SimpleStageType;
import msjfxuicomponents.mvc.networkscanner.MSMySQLNetworkScannerStage;
import msjfxuicomponents.others.ICompte;
import msjfxuicomponents.others.ICompteValidator;
import msjfxuicomponents.others.IConfiguration;

public abstract class MSLoginStage<S extends MainApplicationStage<?>> extends SimpleStageType<MSLoginController> {

	public MSLoginStage(String title) throws MalformedURLException, IOException, URISyntaxException {

		super(title, "/msjfxuicomponents/mvc/mslogin/MSLogin.fxml");

		this.getController().setApplicationName(title);
		this.getController().setMsLoginStage(this);

		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setOnCloseRequest(event -> {
			System.exit(0);
		});

		new MSMySQLNetworkScannerStage(title);

		this.show();

		MSJFXUIComponentsHolder.MS_LOGIN_STAGE = this;
	}

	public abstract SessionFactoryHandler onFirstLoginInitSessionFactoryHandler()
			throws HibernateException, MalformedURLException;

	public abstract CopyrightHandler onFirstLoginInitCopyrightHandler();

	public abstract ICompteValidator onFirstLoginInitCompteValidator();

	public abstract IConfiguration onFirstLoginInitConfiguration();

	public abstract IOperationTypeDAO<?> onFirstLoginInitOperationTypeDAO();

	public abstract S onFirstLoginConstructMainStage();

	public abstract void setMainCompte(ICompte compte);
}
