package msjfxuicomponents;

import mssoftutils.xml.XMLConfigurationsHandler;

public interface MSHibernateBasedConfigurationStage {

	public static XMLConfigurationsHandler XML_CONFIGURATIONS_HANDLER = new XMLConfigurationsHandler(
			"config/hibernate.cfg.xml");

	public default String getInitialDatabaseName() {
		String databaseConfigurationXPath = "/hibernate-configuration/session-factory/property[@name = 'connection.url']";
		String databaseConfiguration = MSHibernateBasedConfigurationStage.XML_CONFIGURATIONS_HANDLER
				.getNodeContent(databaseConfigurationXPath);

		int thirdTwoPoint = databaseConfiguration.indexOf(':', 11);
		int firstSlashAfterThirdTwoPoint = databaseConfiguration.indexOf('/', thirdTwoPoint);
		int firstQuestionMarkAfterThirdTwoPoint = databaseConfiguration.indexOf('?', thirdTwoPoint);

		return databaseConfiguration.substring(firstSlashAfterThirdTwoPoint + 1, firstQuestionMarkAfterThirdTwoPoint);
	}

	public default String getInitialDatabaseUsername() {
		String databaseUsernameXPath = "/hibernate-configuration/session-factory/property[@name = 'hibernate.connection.username']";

		return MSHibernateBasedConfigurationStage.XML_CONFIGURATIONS_HANDLER.getNodeContent(databaseUsernameXPath);
	}

	public default String getInitialDatabasePassword() {
		String databasePasswordXPath = "/hibernate-configuration/session-factory/property[@name = 'hibernate.connection.password']";

		return MSHibernateBasedConfigurationStage.XML_CONFIGURATIONS_HANDLER.getNodeContent(databasePasswordXPath);
	}

	public default String getInitialServerPort() {
		String databaseConfigurationXPath = "/hibernate-configuration/session-factory/property[@name = 'connection.url']";
		String databaseConfiguration = MSHibernateBasedConfigurationStage.XML_CONFIGURATIONS_HANDLER
				.getNodeContent(databaseConfigurationXPath);

		int thirdTwoPoint = databaseConfiguration.indexOf(':', 11);
		int firstSlashAfterThirdTwoPoint = databaseConfiguration.indexOf('/', thirdTwoPoint);

		return databaseConfiguration.substring(thirdTwoPoint + 1, firstSlashAfterThirdTwoPoint);
	}

	public default String getInitialCentralIpServer() {
		String databaseConfigurationXPath = "/hibernate-configuration/session-factory/property[@name = 'connection.url']";
		String databaseConfiguration = MSHibernateBasedConfigurationStage.XML_CONFIGURATIONS_HANDLER
				.getNodeContent(databaseConfigurationXPath);

		int firstDoubleSlash = databaseConfiguration.indexOf("//");
		int thirdTwoPoint = databaseConfiguration.indexOf(':', 11);

		return databaseConfiguration.substring(firstDoubleSlash + 2, thirdTwoPoint);
	}
}
