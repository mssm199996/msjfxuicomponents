package msjfxuicomponents.others;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Created by MSSM on 17/10/2017.
 */
public class SoundsPlayer {

	private Synthesizer synthesizeur = null;
	private MidiChannel canal = null;
	private Circle signal = null;

	public SoundsPlayer() {
		try {
			this.synthesizeur = MidiSystem.getSynthesizer();
			this.synthesizeur.open();
			this.canal = this.synthesizeur.getChannels()[0];
			this.canal.programChange(0);
		} catch (MidiUnavailableException exp) {
			exp.printStackTrace();
		}
	}

	public void ring(int seconds) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			private boolean isForward = false;

			@Override
			public void handle(ActionEvent event) {
				if (this.isForward) {
					if (signal != null)
						signal.setFill(Paint.valueOf("#00FF00"));
					playNote(65, 100);
				} else {
					if (signal != null)
						signal.setFill(Paint.valueOf("#FF0000"));
					playNote(70, 100);
				}

				this.isForward = !this.isForward;
			}
		}));
		timeline.setCycleCount(seconds);
		timeline.setDelay(Duration.ZERO);
		timeline.play();
	}

	public void playNote(int note, int volume) {
		this.canal.noteOn(note, volume);
	}

	public void stopNote(int note) {
		this.canal.noteOff(note);
	}

	public void setSignal(Circle signal) {
		this.signal = signal;
	}
}
