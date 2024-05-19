package game2D;

import java.io.*;
import javax.sound.sampled.*;

public class Sound extends Thread {
	Clip clip = null;
	String filename;	// The name of the file to play
	boolean finished;	// A flag showing that the thread has finished
	
	public Sound(String fname) {
		filename = fname;
		finished = false;
	}

	/**
	 * run will play the actual sound but you should not call it directly.
	 * You need to call the 'start' method of your sound object (inherited
	 * from Thread, you do not need to declare your own). 'run' will
	 * eventually be called by 'start' when it has been scheduled by
	 * the process scheduler.
	 */
	public void run() {
		try {
			File file = new File(filename);
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			AudioFormat	format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
			Thread.sleep(100);
			while (clip.isRunning()) { Thread.sleep(100); }
			clip.close();
		}
		catch (Exception e) {	}
		finished = true;

	}
	public void playMusic(String file)
	{
		try {
			// Creates a file instance to hold an audio file that has been passed into the
			// method
			File midi = new File(file);
			// Creates an audio input stream that obtains an audio input steam provided from
			// the music file
			AudioInputStream Input = AudioSystem.getAudioInputStream(midi);
			// Creates a clip to load in audio
			clip = AudioSystem.getClip();
			// Opens the clip with the format and audio data present in the provided audio
			// input stream
			clip.open(Input);
			// Starts the clip plays the audio stored within
			clip.start();
			// Keeps the audio in a infinite loop
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			// Displays an error message to the console if the music file cannot be found
		} catch (FileNotFoundException e) {
			System.out.println("Music file could not be found");
			// Displays an error message to the console if the music could not play
		} catch (Exception e) {
			System.out.println("Error while playing music");
		}
		
	}

	public void stopMusic() {
		if(clip !=null) {
		clip.close();
		}
	}
    
}
