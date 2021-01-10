package msjfxuicomponents.uicomponents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import msjfxuicomponents.others.WebcamHandler;

public class MSPicturePicker extends TitledPane {

	private int imageHeight = 300;
	private int imageWidth = 300;

	private Image directoryImage = null;
	private Image cameraImage = null;

	private ImageView imageView = new ImageView();
	private JFXButton pickFromLibrairyButton = new JFXButton();
	private JFXButton pickUsingCameraButton = new JFXButton();

	private FileChooser fileChooser = new FileChooser();
	private WebcamHandler webcamHandler;

	private byte[] lastTakenPicture;

	private Runnable onPictureUnableToRead;
	private Runnable onWebcamUnableToUse;

	public MSPicturePicker() {
		this.initComponents();
		this.initAdditionalComponents();
		this.initListeners();
	}

	private void initComponents() {
		this.pickFromLibrairyButton.setText("Importer");
		this.pickFromLibrairyButton.setMaxWidth(Double.MAX_VALUE);
		this.pickFromLibrairyButton.setPrefHeight(40);

		this.pickUsingCameraButton.setText("Camera");
		this.pickUsingCameraButton.setMaxWidth(Double.MAX_VALUE);
		this.pickUsingCameraButton.setPrefHeight(40);

		this.imageView.setFitWidth(this.getImageWidth());
		this.imageView.setFitHeight(this.getImageHeight());

		BorderPane root = new BorderPane();
		HBox bottomContainer = new HBox();

		bottomContainer.setSpacing(5.0);
		bottomContainer.setAlignment(Pos.CENTER);
		bottomContainer.getChildren().addAll(this.pickFromLibrairyButton, this.pickUsingCameraButton);

		root.setCenter(this.imageView);
		root.setBottom(bottomContainer);

		HBox.setHgrow(this.pickFromLibrairyButton, Priority.ALWAYS);
		HBox.setHgrow(this.pickUsingCameraButton, Priority.ALWAYS);

		BorderPane.setMargin(bottomContainer, new Insets(10.0, 0.0, 0.0, 0.0));

		this.setExpanded(true);
		this.setCollapsible(false);
		this.setContent(root);
	}

	private void initAdditionalComponents() {
		this.fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Fichiers images", "*.jpg", "*.png", "*.jpeg"));
	}

	private void initListeners() {
		this.pickFromLibrairyButton.setOnAction(event -> {
			File fichier = this.fileChooser.showOpenDialog(this.getWindow());

			if (fichier != null) {
				try {
					this.setLastTakenPicture(Files.readAllBytes(fichier.toPath()));
				} catch (Exception e) {
					e.printStackTrace();

					if (this.getOnPictureUnableToRead() != null)
						this.getOnPictureUnableToRead().run();
				}
			}
		});

		this.pickUsingCameraButton.setOnAction(event -> {
			if (this.webcamHandler != null) {
				try {
					if (this.webcamHandler.openJFrame())
						this.setLastTakenPicture(this.getWebcamHandler().getLastTakenPicture());
				} catch (Exception e) {
					e.printStackTrace();

					if (this.getOnWebcamUnableToUse() != null)
						this.getOnWebcamUnableToUse().run();
				}
			}
		});
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public byte[] getLastTakenPicture() {
		return this.lastTakenPicture;
	}

	public void setLastTakenPicture(byte[] lastTakenPicture) {
		this.lastTakenPicture = lastTakenPicture;

		if (this.getLastTakenPicture() != null)
			this.imageView.setImage(new Image(new ByteArrayInputStream(this.getLastTakenPicture())));
		else
			this.imageView.setImage(null);
	}

	public void setLastTakenPicture(Blob lastTakenPicture) throws SQLException {
		if (lastTakenPicture != null){
			this.setLastTakenPicture(lastTakenPicture.getBytes(1, (int) lastTakenPicture.length()));
		}
		
		else
			this.imageView.setImage(null);
	}

	public Runnable getOnPictureUnableToRead() {
		return onPictureUnableToRead;
	}

	public void setOnPictureUnableToRead(Runnable onPictureUnableToRead) {
		this.onPictureUnableToRead = onPictureUnableToRead;
	}

	public Runnable getOnWebcamUnableToUse() {
		return onWebcamUnableToUse;
	}

	public void setOnWebcamUnableToUse(Runnable onWebcamUnableToUse) {
		this.onWebcamUnableToUse = onWebcamUnableToUse;
	}

	public WebcamHandler getWebcamHandler() {
		return webcamHandler;
	}

	public void setWebcamHandler(WebcamHandler webcamHandler) {
		this.webcamHandler = webcamHandler;
	}

	public Image getDirectoryImage() {
		return directoryImage;
	}

	public void setDirectoryImage(Image directoryImage) {
		this.directoryImage = directoryImage;
		this.pickFromLibrairyButton.setGraphic(new ImageView(this.directoryImage));
	}

	public Image getCameraImage() {
		return cameraImage;
	}

	public void setCameraImage(Image cameraImage) {
		this.cameraImage = cameraImage;
		this.pickUsingCameraButton.setGraphic(new ImageView(this.cameraImage));
	}

	private Window getWindow() {
		return this.getScene().getWindow();
	}
}
