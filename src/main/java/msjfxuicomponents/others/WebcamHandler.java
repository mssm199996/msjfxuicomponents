package msjfxuicomponents.others;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WebcamHandler {

	private Webcam webcam;
	private WebcamPanel webcamPanel;
	private Stage window;
	private byte[] lastTakenPicture;
	private boolean confirmed;

	public WebcamHandler(Webcam webcam) {
		this.webcam = webcam;

		if (this.webcam != null)
			try {
				this.initPanel();
			} catch(Exception exp){
				exp.printStackTrace();
			}
	}

	private void initPanel() {
		try {
			final Glow glow = new Glow();
			glow.setLevel(0.4);

			this.webcam.setViewSize(WebcamResolution.VGA.getSize());

			this.webcamPanel = new WebcamPanel(this.webcam, true);
			this.webcamPanel.setMirrored(true);
			this.webcamPanel.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					webcamPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});

			SwingNode converter = new SwingNode();
			converter.setContent(this.webcamPanel);

			converter.setOnMouseClicked(event -> {
				byte[] image = WebcamUtils.getImageBytes(webcam, "jpg");

				setLastTakenPicture(image);

				confirmed = true;
				window.close();
			});

			converter.setOnMouseExited(event -> {
				converter.setEffect(null);
			});
			converter.setOnMouseEntered(event -> {
				converter.setEffect(glow);
			});
			converter.setOnMouseReleased(event -> {
				glow.setLevel(0.4);
			});
			converter.setOnMousePressed(event -> {
				glow.setLevel(0.2);
			});

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					window = new Stage();
					window.setTitle("MSMarket webcam utility");
					window.setScene(new Scene(new BorderPane(converter)));
					window.setResizable(false);
					window.setAlwaysOnTop(true);
					window.setOnShown(event -> {
						confirmed = false;
						webcamPanel.resume();
					});
					window.setOnHidden(event -> {
						webcamPanel.pause();
					});
				}
			});
		} catch (com.github.sarxos.webcam.WebcamLockException exp) {
			exp.printStackTrace();
		}
	}

	public boolean openJFrame() throws NoWebcamException {
		if (this.webcam == null || this.window == null)
			throw new NoWebcamException();

		this.window.showAndWait();

		return this.confirmed;
	}

	public WebcamPanel getWebcamPanel() {
		return webcamPanel;
	}

	public void setWebcamPanel(WebcamPanel webcamPanel) {
		this.webcamPanel = webcamPanel;
	}

	public Stage getWindow() {
		return window;
	}

	public void setWindow(Stage window) {
		this.window = window;
	}

	public Webcam getWebcam() {
		return webcam;
	}

	public void setWebcam(Webcam webcam) {
		this.webcam = webcam;
	}

	public byte[] getLastTakenPicture() {
		return lastTakenPicture;
	}

	public void setLastTakenPicture(byte[] lastTakenPicture) {
		this.lastTakenPicture = lastTakenPicture;
	}

	public class NoWebcamException extends Exception {
		
		private static final long serialVersionUID = 7534536462079559416L;

		public NoWebcamException() {
			super("No webcam");
		}
	}
}
