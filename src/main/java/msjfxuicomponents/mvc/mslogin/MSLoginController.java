package msjfxuicomponents.mvc.mslogin;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.hibernate.service.spi.ServiceException;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import DomainModel.IConfiguration;
import Exceptions.AlreadySignUpPosteAndModifiedCredentials;
import Exceptions.NoneActiveException;
import LC.Exceptions.DeviceNotOpenedException;
import LC.Exceptions.DongleOperationFailedException;
import LC.Exceptions.PasswordAuthenticationException;
import MainPackage.CopyrightHandler;
import MainPackage.SerialNumberCopyrightHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.SessionFactoryHandler;
import msdatabaseutils.SoftwareFeatureDAO;
import msjfxuicomponents.MSComponentsHolder;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightComponentsHolder;
import msjfxuicomponents.daos.UIComponentParamDAO;
import msjfxuicomponents.mvc.msactivation.MSActivationStage;
import msjfxuicomponents.mvc.msjrviewer.MSJRViewerStage;
import msjfxuicomponents.mvc.mssauvegardebuilder.MSSauvegardeBuilderStage;
import msjfxuicomponents.mvc.mssauvegardeloader.MSSauvegardeLoaderStage;
import msjfxuicomponents.mvc.mssoftwarefeatures.MSSoftwareFeaturesStage;
import msjfxuicomponents.mvc.mssoftwareupdates.MSSoftwareUpdatesStage;
import msjfxuicomponents.others.ICompte;
import msjfxuicomponents.others.ICompteValidator;
import msjfxuicomponents.others.WebcamHandler;
import mssoftutils.xml.ApplicationStatupXMLConfiguration;

public class MSLoginController implements Initializable {

	public static WebcamHandler WEBCAM_HANDLER;
	public static ICompte LOGGED_IN_COMPTE;
	public static IConfiguration MAIN_CONFIGURATION;

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXButton connexion, quitter, modifyServerButton, repairDatabaseButton;

	@FXML
	private JFXCheckBox rememberPassword;

	@FXML
	private ProgressIndicator progression;

	private MSLoginStage<?> msLoginStage;

	private ApplicationStatupXMLConfiguration applicationStatupXMLConfiguration = new ApplicationStatupXMLConfiguration();
	private CopyrightHandler copyrightHandler;
	private SessionFactoryHandler sessionFactoryHandler;
	private ICompteValidator compteValidator = null;
	private IOperationTypeDAO<?> operationTypeDAO = null;
	private SoftwareFeatureDAO softwareFeatureDAO = null;

	private boolean firstLoggin = true;
	private String applicationName;

	@FXML
	private void close() {
		System.exit(0);
	}

	@FXML
	private void modifyServer() {
		MSJFXUIComponentsHolder.MS_NETWOR_SCANNER_STAGE.show();
	}

	@FXML
	private void repairDatabase() {
		MSJFXUIComponentsHolder.MS_DATABASE_REPAIR_STAGE.show();
	}

	@FXML
	private void checkConnection() throws IOException {
		this.modifyServerButton.setDisable(true);
		this.repairDatabaseButton.setDisable(true);

		this.updateComponentsState(true, ProgressIndicator.INDETERMINATE_PROGRESS);

		(new Thread(() -> {
			try {
				if (this.sessionFactoryHandler == null) {
					this.sessionFactoryHandler = this.msLoginStage.onFirstLoginInitSessionFactoryHandler();
					this.copyrightHandler = this.msLoginStage.onFirstLoginInitCopyrightHandler();
					this.compteValidator = this.msLoginStage.onFirstLoginInitCompteValidator();
					this.operationTypeDAO = this.msLoginStage.onFirstLoginInitOperationTypeDAO();
					this.softwareFeatureDAO = new SoftwareFeatureDAO(this.sessionFactoryHandler, this.operationTypeDAO);

					MSComponentsHolder.UI_COMPONENT_PARAM_DAO = new UIComponentParamDAO(this.sessionFactoryHandler,
							this.operationTypeDAO);

					MSJFXUIComponentsHolder.MS_JOURNAL_AUDIT_STAGE.setSearcher(this.operationTypeDAO);

					Platform.runLater(() -> {
						new MSJRViewerStage(this.applicationName);
						new MSActivationStage(this.applicationName, this.copyrightHandler);
						new MSSauvegardeBuilderStage(this.applicationName,
								this.sessionFactoryHandler.getBackupsManager());
						new MSSauvegardeLoaderStage(this.applicationName,
								this.sessionFactoryHandler.getBackupsManager());
						new MSSoftwareFeaturesStage(this.msLoginStage.softwareFeatureTypes(), this.softwareFeatureDAO,
								this.copyrightHandler, this.applicationName);
						new MSSoftwareUpdatesStage(this.applicationName, this.msLoginStage.getSoftware());
					});
				}

				this.copyrightHandler.verifyCopyright();
				this.sessionFactoryHandler.constructDatabase();

				String userCompte = this.username.getText();
				String passwordCompte = this.password.getText();

				this.compteValidator.onAttemptingToConnect(userCompte, passwordCompte);

				MSLoginController.LOGGED_IN_COMPTE = this.compteValidator.isAccountValid(userCompte, passwordCompte);

				if (MSLoginController.LOGGED_IN_COMPTE != null) {
					this.msLoginStage.setMainCompte(MSLoginController.LOGGED_IN_COMPTE);

					MSLoginController.MAIN_CONFIGURATION = this.msLoginStage.onFirstLoginInitConfiguration();

					MSLoginController.LOGGED_IN_COMPTE.setDateDerniereConnexion(LocalDate.now());
					MSLoginController.LOGGED_IN_COMPTE.setHeureDerniereConnexion(LocalTime.now());

					this.sessionFactoryHandler.updateArray(MSLoginController.LOGGED_IN_COMPTE);
					this.sessionFactoryHandler
							.setSauvegardesPath(MSLoginController.MAIN_CONFIGURATION.getCheminSauvegarde());
					this.sessionFactoryHandler.setMysqlFolder(MSLoginController.MAIN_CONFIGURATION.getCheminMysql());

					Platform.runLater(() -> {
						loggIn();
					});
				} else {
					Platform.runLater(() -> {
						MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
								"Erreur d'authentification", "Nom d'utilisateur ou mot de passe invalide",
								this.getWindow());

						this.updateComponentsState(false, 1.0);
					});
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (AlreadySignUpPosteAndModifiedCredentials | IOException e) {
				e.printStackTrace();

				Platform.runLater(() -> {
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
							"Systéme de sécurité",
							"Le logiciel a été modifié, veuillez contacter votre fournisseur pour une réparation",
							this.getWindow());

					System.exit(0);
				});
			} catch (NoneActiveException e) {
				e.printStackTrace();

				Platform.runLater(() -> {
					this.askForLicense();
				});
			} catch (ServiceException | ExceptionInInitializerError e) {
				e.printStackTrace();

				Platform.runLater(() -> {
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
							"Erreur de connexion au serveur",
							"Le logiciel n'a pas pu se connecter au serveur, soit l'hôte de déstination est injoignable, soit MySQL n'est pas installé, soit la configuration a été mal faite !",
							this.getWindow());

					System.exit(0);
				});
			} catch (DeviceNotOpenedException | DongleOperationFailedException e) {
				e.printStackTrace();

				this.displayDongleNotConnectedError(true);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (PasswordAuthenticationException e) {
				e.printStackTrace();

				this.displayDongleNotProgrammedError(true);
			}
		})).start();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initInitialInformations();
		this.updateComponentsState(false, 1.0);

		MSLightweightComponentsHolder.APPLICATION_STARTUP_XML_CONFIGURATION = this.applicationStatupXMLConfiguration;
	}

	private void loggIn() {
		this.updateInitialInformations(this.rememberPassword.isSelected());

		if (this.firstLoggin) {
			if (MSLoginController.MAIN_CONFIGURATION.isActivateCamera())
				MSLoginController.WEBCAM_HANDLER = new WebcamHandler(Webcam.getDefault());

			MSJFXUIComponentsHolder.MAIN_APPLICATION_STAGE = this.msLoginStage.onFirstLoginConstructMainStage();
			MSJFXUIComponentsHolder.MAIN_APPLICATION_STAGE.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
				this.updateComponentsState(false, 1.0);
			});

			MSJFXUIComponentsHolder.MAIN_APPLICATION_STAGE.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
				MSJFXUIComponentsHolder.MS_INTRODUCTION_STAGE.show();
				MSJFXUIComponentsHolder.MS_LOGIN_STAGE.show();

				MSLoginController.LOGGED_IN_COMPTE = null;

				this.msLoginStage.setMainCompte(null);
				this.password.setText("");
				this.password.requestFocus();
			});

			MSJFXUIComponentsHolder.MS_SOFTWARE_FEATURES_STAGE.initCopyrightHandlerFeatures();

			this.firstLoggin = false;
		}

		this.msLoginStage.onLoggedInSuccesfulled(MSLoginController.LOGGED_IN_COMPTE);

		MSJFXUIComponentsHolder.MS_INTRODUCTION_STAGE.close();
		MSJFXUIComponentsHolder.MS_LOGIN_STAGE.close();
		MSJFXUIComponentsHolder.MAIN_APPLICATION_STAGE.show();
	}

	private void askForLicense() {
		this.updateComponentsState(true, 1.0);

		if (MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE == null)
			MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE = new MSActivationStage(this.applicationName,
					this.copyrightHandler);

		MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE.centerOnScreen();
		MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE.setOnCloseRequest(event -> {
			System.exit(0);
		});

		boolean authenticated = false;

		while (!authenticated) {
			try {
				authenticated = this.copyrightHandler.isPosteStillActive();

				if (!authenticated) {
					boolean isUserActivable = this.copyrightHandler.isUserActivable();

					if (isUserActivable)
						MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE.showAndWait();
					else
						this.displayDongleNotProgrammedError(false);
				}
			} catch (DongleOperationFailedException | DeviceNotOpenedException e) {
				e.printStackTrace();

				this.displayDongleNotConnectedError(false);
			}
		}

		MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE.setOnCloseRequest(event -> {
			MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE.hide();
		});

		this.updateComponentsState(false, 1.0);
	}

	private void updateComponentsState(boolean function, double progress) {
		this.progression.setProgress(progress);
		this.username.setDisable(function);
		this.password.setDisable(function);
		this.connexion.setDisable(function);
		this.quitter.setDisable(function);
		this.rememberPassword.setDisable(function);
		this.progression.setPrefSize(45, 45);
	}

	private void initInitialInformations() {
		String initialUsername = this.applicationStatupXMLConfiguration.getInitialUsername();
		String initialPassword = this.applicationStatupXMLConfiguration.getInitialPassword();
		boolean initialSavePassword = this.applicationStatupXMLConfiguration.getInitialSavePassword();

		this.username.setText(initialUsername);
		this.password.setText(initialPassword);
		this.rememberPassword.setSelected(initialSavePassword);
	}

	private void updateInitialInformations(boolean rememberPassword) {
		String initialUsername = this.username.getText();
		String initialPassword = rememberPassword ? this.password.getText() : "";
		boolean initialSavePassword = this.rememberPassword.isSelected();

		this.applicationStatupXMLConfiguration.setInitialUsername(initialUsername);
		this.applicationStatupXMLConfiguration.setInitialPassword(initialPassword);
		this.applicationStatupXMLConfiguration.setInitialSavePassword(initialSavePassword);
		this.applicationStatupXMLConfiguration.validateDocument();
	}

	public Window getWindow() {
		return this.connexion.getScene().getWindow();
	}

	public CopyrightHandler getCopyrightHandler() {
		return copyrightHandler;
	}

	public void setCopyrightHandler(SerialNumberCopyrightHandler serialNumberCopyrightHandler) {
		this.copyrightHandler = serialNumberCopyrightHandler;
	}

	public SessionFactoryHandler getSessionFactoryHandler() {
		return sessionFactoryHandler;
	}

	public void setSessionFactoryHandler(SessionFactoryHandler sessionFactoryHandler) {
		this.sessionFactoryHandler = sessionFactoryHandler;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public MSLoginStage<?> getMsLoginStage() {
		return msLoginStage;
	}

	public void setMsLoginStage(MSLoginStage<?> msLoginStage) {
		this.msLoginStage = msLoginStage;
	}

	public void displayDongleNotConnectedError(boolean isMainLoginEntry) {
		if (isMainLoginEntry) {
			Platform.runLater(() -> {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
						"Erreur d'authentification", "Le dongle n'est pas branché !", this.getWindow());

				this.updateComponentsState(false, 1.0);
			});
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
					"Erreur d'authentification", "Le dongle n'est pas branché !", this.getWindow());
	}

	public void displayDongleNotProgrammedError(boolean isMainLoginEntry) {
		if (isMainLoginEntry) {
			Platform.runLater(() -> {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
						"Erreur d'authentification", "Le dongle n'est pas correctement programmé !", this.getWindow());

				this.updateComponentsState(false, 1.0);

				System.exit(0);
			});
		} else {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getApplicationName(),
					"Erreur d'authentification", "Le dongle n'est pas correctement programmé !", this.getWindow());

			System.exit(0);
		}
	}
}
