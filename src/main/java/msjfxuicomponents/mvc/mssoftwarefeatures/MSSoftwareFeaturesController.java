package msjfxuicomponents.mvc.mssoftwarefeatures;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import DomainModel.ISoftwareFeatureType;
import DomainModel.SoftwareFeature;
import LC.Exceptions.DeviceNotOpenedException;
import LC.Exceptions.DongleOperationFailedException;
import MainPackage.CopyrightHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import msdatabaseutils.SoftwareFeatureDAO;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.cells.JFXCheckboxCell;

public class MSSoftwareFeaturesController implements Initializable {

	public static ObservableList<SoftwareFeature> SOFTWARE_FEATURES = FXCollections.observableList(new LinkedList<>());
	public static Map<ISoftwareFeatureType, Boolean> SOFTWARE_FEATURES_ENABILITY = new HashMap<>();

	private SoftwareFeatureDAO softwareFeatureDao;
	private CopyrightHandler copyrightHandler;

	@FXML
	private TableView<SoftwareFeature> tableParametres;

	@FXML
	private TableColumn<SoftwareFeature, Integer> indexColumn;

	@FXML
	private TableColumn<SoftwareFeature, String> descriptionParametreColumn;

	@FXML
	private TableColumn<SoftwareFeature, Boolean> interactivityColumn, visibilityColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.tableParametres.setItems(MSSoftwareFeaturesController.SOFTWARE_FEATURES);

		this.indexColumn.setCellFactory(call -> {
			return new IndexCell<>();
		});

		this.descriptionParametreColumn.setCellValueFactory(call -> {
			return call.getValue().representationProperty();
		});

		this.interactivityColumn.setCellValueFactory(call -> {
			return call.getValue().enabilityProperty().asObject();
		});
		this.interactivityColumn.setCellFactory(call -> {
			return new JFXCheckboxCell<SoftwareFeature>() {

				@Override
				public void onUpdateEntity(SoftwareFeature entity) {
					softwareFeatureDao.updateEntity(entity);
				}

				@Override
				public void onUpdateRow(SoftwareFeature row, Boolean newValue) {
					row.setEnability(newValue);
				}
			};
		});

		this.visibilityColumn.setCellValueFactory(call -> {
			return call.getValue().visibilityProperty().asObject();
		});
		this.visibilityColumn.setCellFactory(call -> {
			return new JFXCheckboxCell<SoftwareFeature>() {

				@Override
				public void onUpdateEntity(SoftwareFeature entity) {
					softwareFeatureDao.updateEntity(entity);
				}

				@Override
				public void onUpdateRow(SoftwareFeature row, Boolean newValue) {
					row.setVisiblity(newValue);
				}

			};
		});
	}

	public SoftwareFeatureDAO getSoftwareFeatureDao() {
		return softwareFeatureDao;
	}

	public void setSoftwareFeatureDao(SoftwareFeatureDAO softwareFeatureDao) {
		this.softwareFeatureDao = softwareFeatureDao;
	}

	public CopyrightHandler getCopyrightHandler() {
		return copyrightHandler;
	}

	public void setCopyrightHandler(CopyrightHandler copyrightHandler) {
		this.copyrightHandler = copyrightHandler;
	}

	public void fillData(List<? extends ISoftwareFeatureType> iSoftwareFeaturesTypes) {
		List<SoftwareFeature> softwareFeatures = this.softwareFeatureDao.getAll();
		List<SoftwareFeature> newSoftwareFeatures = new LinkedList<>();

		MSSoftwareFeaturesController.SOFTWARE_FEATURES.clear();
		MSSoftwareFeaturesController.SOFTWARE_FEATURES.addAll(softwareFeatures);

		for (ISoftwareFeatureType iSoftwareFeature : iSoftwareFeaturesTypes) {
			boolean exists = false;

			for (SoftwareFeature softwareFeature : softwareFeatures) {
				if (softwareFeature.compareToISoftwareFeatureType(iSoftwareFeature)) {
					exists = true;

					softwareFeature.setISoftwareFeatureType(iSoftwareFeature);

					if (exists)
						break;
				}
			}

			if (!exists) {
				SoftwareFeature softwareFeature = new SoftwareFeature();
				softwareFeature.setRepresentation(iSoftwareFeature.getDesignation());
				softwareFeature.setVisiblity(false);
				softwareFeature.setEnability(false);
				softwareFeature.setISoftwareFeatureType(iSoftwareFeature);

				newSoftwareFeatures.add(softwareFeature);
			}
		}

		MSSoftwareFeaturesController.SOFTWARE_FEATURES.addAll(newSoftwareFeatures);
		MSSoftwareFeaturesController.SOFTWARE_FEATURES_ENABILITY = softwareFeatures.stream()
				.collect(Collectors.toMap(SoftwareFeature::getISoftwareFeatureType, SoftwareFeature::getEnability));

		this.softwareFeatureDao.insertCollection(newSoftwareFeatures);
	}

	public void initCopyrightHandlerFeatures() {
		try {
			Collection<String> additionalEnabledFeatures = this.copyrightHandler.getEnabledModules();

			for (String enabledFeature : additionalEnabledFeatures) {
				for (SoftwareFeature softwareFeature : MSSoftwareFeaturesController.SOFTWARE_FEATURES) {
					if (softwareFeature.getISoftwareFeatureType().getInternalReference().endsWith(enabledFeature)) {
						softwareFeature.setEnability(true);
						softwareFeature.setVisiblity(true);
					}
				}
			}
		} catch (DongleOperationFailedException | DeviceNotOpenedException e) {
			e.printStackTrace();
		}
	}
}
