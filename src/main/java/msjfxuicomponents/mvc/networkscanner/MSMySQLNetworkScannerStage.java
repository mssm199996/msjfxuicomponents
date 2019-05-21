package msjfxuicomponents.mvc.networkscanner;

import mssoftutils.network.RemoteMachine;
import mssoftutils.xml.XMLConfigurationsHandler;

public class MSMySQLNetworkScannerStage extends MSNetworkScannerStage {

	public static XMLConfigurationsHandler XML_CONFIGURATIONS_HANDLER = new XMLConfigurationsHandler(
			"config/hibernate.cfg.xml");

	public MSMySQLNetworkScannerStage(String stageTitle) {
		super(stageTitle);
	}

	@Override
	public void onRemoteMachineSelected(RemoteMachine remoteMachine) {
		(new Thread(() -> {
			String databaseConfigurationXPath = "/hibernate-configuration/session-factory/property[@name = 'connection.url']";
			String databaseUsernameXPath = "/hibernate-configuration/session-factory/property[@name = 'hibernate.connection.username']";
			String databasePasswordXPath = "/hibernate-configuration/session-factory/property[@name = 'hibernate.connection.password']";

			String oldRemoteMachine = MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER
					.getNodeContent(databaseConfigurationXPath);

			int doubleSlashIndex = oldRemoteMachine.indexOf("//");
			int twoPointIndex = oldRemoteMachine.indexOf(':', 11);

			String oldIpAddress = oldRemoteMachine.substring(doubleSlashIndex + 2, twoPointIndex);
			String newIpAddress = remoteMachine.getIpAddress();

			String oldPort = this.getInitialServerPort();
			String newPort = this.getPort();

			String newRemoteMachine = oldRemoteMachine.replace(oldIpAddress, newIpAddress).replace(":" + oldPort,
					":" + newPort);

			String newDatabaseUsername = this.getDatabaseUsername();
			String newDatabasePassword = this.getDatabasePassword();

			MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER.modifyNode(databaseUsernameXPath,
					newDatabaseUsername);
			MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER.modifyNode(databasePasswordXPath,
					newDatabasePassword);
			MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER.modifyNode(databaseConfigurationXPath,
					newRemoteMachine);
			MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER.validateDocument();
		})).start();
	}

	@Override
	public String getInitialDatabaseUsername() {
		String databaseUsernameXPath = "/hibernate-configuration/session-factory/property[@name = 'hibernate.connection.username']";

		return MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER.getNodeContent(databaseUsernameXPath);
	}

	@Override
	public String getInitialDatabasePassword() {
		String databasePasswordXPath = "/hibernate-configuration/session-factory/property[@name = 'hibernate.connection.password']";

		return MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER.getNodeContent(databasePasswordXPath);
	}

	@Override
	public String getInitialServerPort() {
		String databaseConfigurationXPath = "/hibernate-configuration/session-factory/property[@name = 'connection.url']";
		String databaseConfiguration = MSMySQLNetworkScannerStage.XML_CONFIGURATIONS_HANDLER
				.getNodeContent(databaseConfigurationXPath);

		int thirdTwoPoint = databaseConfiguration.indexOf(':', 11);
		int firstSlashAfterThirdTwoPoint = databaseConfiguration.indexOf('/', thirdTwoPoint);

		return databaseConfiguration.substring(thirdTwoPoint + 1, firstSlashAfterThirdTwoPoint);
	}
}
