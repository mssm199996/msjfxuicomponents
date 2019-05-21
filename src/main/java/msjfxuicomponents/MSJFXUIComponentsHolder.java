package msjfxuicomponents;

import msjfxuicomponents.mvc.MainApplicationStage;
import msjfxuicomponents.mvc.about.MSAboutStage;
import msjfxuicomponents.mvc.msactivation.MSActivationStage;
import msjfxuicomponents.mvc.mscalculatrice.MSCalculatriceStage;
import msjfxuicomponents.mvc.msclavier.MSClavierStage;
import msjfxuicomponents.mvc.msintroduction.MSIntroductionStage;
import msjfxuicomponents.mvc.msjournalaudit.MSJournalAuditStage;
import msjfxuicomponents.mvc.msjrviewer.MSJRViewerStage;
import msjfxuicomponents.mvc.mslogin.MSLoginStage;
import msjfxuicomponents.mvc.mspavenumerique.MSPaveNumeriqueStage;
import msjfxuicomponents.mvc.mssauvegardebuilder.MSSauvegardeBuilderStage;
import msjfxuicomponents.mvc.mssauvegardeloader.MSSauvegardeLoaderStage;
import msjfxuicomponents.mvc.networkscanner.MSNetworkScannerStage;
import msjfxuicomponents.others.MSAlertsDisplayer;

public class MSJFXUIComponentsHolder {

	public static MSPaveNumeriqueStage MS_PAVE_NUMERIQUE_STAGE = new MSPaveNumeriqueStage();
	public static MSClavierStage MS_CLAVIER_STAGE = new MSClavierStage();
	public static MSCalculatriceStage MS_CALCULATRICE_STAGE = new MSCalculatriceStage();
	public static MSAlertsDisplayer MS_ALERT_DISPLAYER = new MSAlertsDisplayer();
	public static MSAboutStage MS_ABOUT_STAGE = new MSAboutStage();
	public static MSJournalAuditStage<?> MS_JOURNAL_AUDIT_STAGE = new MSJournalAuditStage<>();

	// Auto-loaded Components
	public static MSSauvegardeLoaderStage MS_SAUVEGARDE_LOADER_STAGE;
	public static MSSauvegardeBuilderStage MS_SAUVEGARDE_BUILDER_STAGE;
	public static MSJRViewerStage MS_JR_VIEWER_STAGE;

	// Components to create
	public static MSIntroductionStage MS_INTRODUCTION_STAGE;
	public static MSLoginStage<?> MS_LOGIN_STAGE;
	public static MainApplicationStage<?> MAIN_APPLICATION_STAGE;
	public static MSNetworkScannerStage MS_NETWOR_SCANNER_STAGE;
	public static MSActivationStage MS_ACTIVATION_STAGE;
}
