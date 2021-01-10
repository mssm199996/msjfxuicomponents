package msjfxuicomponents.uicomponents;

import com.google.common.base.Supplier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.componentsStuffers.ComponentsUpdateGlower;
import msjfxuicomponents.others.IDescriptor;
import msjfxuicomponents.others.IDescriptorUpdator;

public class MSSimpleObservationTitledPane<T extends IDescriptor> extends TitledPane {

	protected ImageView titledPaneGraphic, updateButtonGraphic;
	protected JFXTextArea textArea;
	protected JFXButton updateButton;
	protected SimpleStringProperty updateButtonLabel = new SimpleStringProperty("No description");
	protected SimpleObjectProperty<Image> titledPaneImage = new SimpleObjectProperty<Image>(),
			updateButtonImage = new SimpleObjectProperty<Image>();

	protected SimpleDoubleProperty titledPaneImageWidthFit = new SimpleDoubleProperty(40.0),
			titledPaneImageHeightFit = new SimpleDoubleProperty(40.0),
			updateButtonImageHeightFit = new SimpleDoubleProperty(30.0),
			updateButtonImageWidthFit = new SimpleDoubleProperty(30.0);

	private IDescriptorUpdator<T> updator;
	private Supplier<T> entityFetcher;

	public MSSimpleObservationTitledPane() {
		super();
		this.constructModules();
		this.assembleModules();
	}

	protected void assembleModules() {
		BorderPane root = new BorderPane();

		root.setCenter(this.textArea);
		root.setBottom(this.updateButton);

		BorderPane.setMargin(this.textArea, new Insets(10.0, 0.0, 0.0, 0.0));
		BorderPane.setMargin(this.updateButton, new Insets(15.0, 0.0, 0.0, 0.0));

		this.setContent(root);
		this.setText("Untitled");
		this.setGraphic(this.titledPaneGraphic);
	}

	protected void constructUpdateButton() {
		this.updateButton = new JFXButton(this.updateButtonLabel.getValue(), this.updateButtonGraphic);
		this.updateButton.textProperty().bind(this.updateButtonLabel);
		this.updateButton.getStyleClass().add("lightYellowButton");
		this.updateButton.setMaxWidth(Double.MAX_VALUE);
		this.updateButton.setOnMouseClicked(event -> {
			Stage mother = this.getStage();

			if (this.updator != null) {
				T entity = this.entityFetcher.get();
				String title = mother.getTitle();
				String header = "Mise à jour de la déscription";

				if (entity != null) {
					String content = "Veuillez confirmer pour procéder à la mise à jour";

					boolean confirmation = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(title,
							header, content, mother);

					if (confirmation) {
						entity.setDescription(this.textArea.getText());

						(new Thread(() -> {
							this.updator.update(entity);
						})).start();
					}
				} else
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(title, header,
							"Erreur... veuillez d'abords selectionner un élement à mettre à jour", mother);
			} else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("MSJFXUIComponents", "Fatal error...",
						"No updator specified !", mother);
		});
	}

	protected void constructAnimation() {
		ComponentsUpdateGlower glower = new ComponentsUpdateGlower();
		glower.registerAnimation(this.updateButton, this.textArea.textProperty());
	}

	protected void constructTextArea() {
		this.textArea = new JFXTextArea();
		this.textArea.promptTextProperty().bind(this.textProperty());
		this.textArea.setLabelFloat(true);
		this.textArea.setMaxWidth(Double.MAX_VALUE);
	}

	protected void constructImageView() {
		this.titledPaneGraphic = new ImageView(this.titledPaneImage.getValue());
		this.titledPaneGraphic.setFitWidth(this.titledPaneImageWidthFit.getValue());
		this.titledPaneGraphic.setFitHeight(this.titledPaneImageHeightFit.getValue());

		this.titledPaneGraphic.imageProperty().bind(this.titledPaneImage);
		this.titledPaneGraphic.fitWidthProperty().bind(this.titledPaneImageWidthFit);
		this.titledPaneGraphic.fitHeightProperty().bind(this.titledPaneImageHeightFit);

		// -----------------------------------------------------------------------------

		this.updateButtonGraphic = new ImageView(this.updateButtonImage.getValue());
		this.updateButtonGraphic.setFitWidth(this.updateButtonImageWidthFit.getValue());
		this.updateButtonGraphic.setFitHeight(this.updateButtonImageHeightFit.getValue());

		this.updateButtonGraphic.imageProperty().bind(this.updateButtonImage);
		this.updateButtonGraphic.fitWidthProperty().bind(this.updateButtonImageWidthFit);
		this.updateButtonGraphic.fitHeightProperty().bind(this.updateButtonImageHeightFit);
	}

	protected void constructModules() {
		this.constructImageView();
		this.constructTextArea();
		this.constructUpdateButton();
		this.constructAnimation();
	}

	public String getUpdateButtonLabel() {
		return updateButtonLabel.getValue();
	}

	public void setUpdateButtonLabel(String updateButtonLabel) {
		this.updateButtonLabel.setValue(updateButtonLabel);
	}

	public Image getTitledPaneImage() {
		return titledPaneImage.getValue();
	}

	public void setTitledPaneImage(Image titledPaneImage) {
		this.titledPaneImage.setValue(titledPaneImage);
	}

	public Image getUpdateButtonImage() {
		return updateButtonImage.getValue();
	}

	public void setUpdateButtonImage(Image updateButtonImage) {
		this.updateButtonImage.setValue(updateButtonImage);
	}

	public Double getTitledPaneImageWidthFit() {
		return titledPaneImageWidthFit.getValue();
	}

	public void setTitledPaneImageWidthFit(Double titledPaneImageWidthFit) {
		this.titledPaneImageWidthFit.setValue(titledPaneImageWidthFit);
	}

	public Double getTitledPaneImageHeightFit() {
		return titledPaneImageHeightFit.getValue();
	}

	public void setTitledPaneImageHeightFit(Double titledPaneImageHeightFit) {
		this.titledPaneImageHeightFit.setValue(titledPaneImageHeightFit);
	}

	public Double getUpdateButtonImageHeightFit() {
		return updateButtonImageHeightFit.getValue();
	}

	public void setUpdateButtonImageHeightFit(Double updateButtonImageHeightFit) {
		this.updateButtonImageHeightFit.setValue(updateButtonImageHeightFit);
	}

	public Double getUpdateButtonImageWidthFit() {
		return updateButtonImageWidthFit.getValue();
	}

	public void setUpdateButtonImageWidthFit(Double updateButtonImageWidthFit) {
		this.updateButtonImageWidthFit.setValue(updateButtonImageWidthFit);
	}

	public IDescriptorUpdator<T> getUpdator() {
		return updator;
	}

	public void setUpdator(IDescriptorUpdator<T> updator) {
		this.updator = updator;
	}

	public Supplier<T> getEntityFetcher() {
		return entityFetcher;
	}

	public void setEntityFetcher(Supplier<T> entityFetcher) {
		this.entityFetcher = entityFetcher;
	}

	public Stage getStage() {
		return (Stage) this.getScene().getWindow();
	}

	public void setObservationContent(String observation) {
		this.textArea.setText(observation);
	}
}
