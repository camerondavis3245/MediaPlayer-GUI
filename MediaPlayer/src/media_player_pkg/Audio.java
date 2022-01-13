package media_player_pkg;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;


public class Audio {

	private ArrayList<String> songs;
	private String folder;
	private File file;
	private Clip clip;
	private AudioInputStream stream;
	private long clipTimePosition;

	//Constructor for audio class
	public Audio(String folder) {
		this.folder = folder;
		//Stores songs for play
		songs = new ArrayList<>();

		//Load files as audioTracks
		File dir = new File(folder);
		addSongFiles(dir.list());
	}
	//Adds audio files to ArrayList
	private void addSongFiles(String[] files) {
		for (int i = 0; i < files.length; i++) {
			songs.add(files[i]);
		}
		System.out.println(songs);
	}

	//Getter for audio stream
	public AudioInputStream getAudioStream() {
		return stream;
	}

	//Getter for song list information
	public ArrayList<String> getSongList() {
		return songs;
	}

	//Plays audio for specified file
	public void playSong(String song, JButton pauseButton, JLabel songLabel, JSlider volumeSlider) {

		try {

			file = new File(folder + song);
			stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			pauseButton.setEnabled(true);
			//Splits song file name by .wav to get rid of extension
			String[] songSplit = song.split(".wav");
			songLabel.setText(songSplit[0]);

			clip.open(stream);
			changeVolume(volumeSlider.getValue());
			clip.start();
			
			//sleep to allow enough time for the clip to play
			Thread.sleep(500);

			stream.close();
			
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	//Stops audio for specified song
	public void stopSong() {
		clip.stop();
	}

	//Rewinds song by 10 seconds
	public void rewindSong() {
		long seconds10 = 10_000_000;
		clipTimePosition = clip.getMicrosecondPosition();
		System.out.println(clipTimePosition);
		clip.setMicrosecondPosition(clipTimePosition - seconds10);
		System.out.println(clip.getMicrosecondPosition());
	}
	//Fast forwards song by 10 seconds
	public void fastForwardSong() {
		long seconds10 = 10_000_000;
		clipTimePosition = clip.getMicrosecondPosition();
		System.out.println(clipTimePosition);
		clip.setMicrosecondPosition(clipTimePosition + seconds10);
		System.out.println(clip.getMicrosecondPosition());
	}
	
	//Pauses audio
	public long pauseSong() {
		clipTimePosition = clip.getMicrosecondPosition();
		clip.stop();

		return clipTimePosition;
	}

	//Resumes song
	public void resumeSong(long clipTimePosition) {
		clip.setMicrosecondPosition(clipTimePosition);
		clip.start();
	}

	//Changes volume for audio playing
	public int changeVolume(int volume) {
		//Get the gain control from clip

		if (clip != null) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);



			//set the gain (between 0.0 and 1.0)
			if (volume == -40) {
				volume = -80;
			}
			gainControl.setValue(volume);
		}
		//makes pretty volume number
		if (volume <= 6 && volume >= 2)
			volume = 10;
		else if (volume < 2 && volume >= -3)
			volume = 9;
		else if (volume < -3 && volume >= -8)
			volume = 8;
		else if (volume < -8 && volume >= -13)
			volume = 7;
		else if (volume < -13 && volume >= -18)
			volume = 6;
		else if (volume < -18 && volume >= -22)
			volume = 5;
		else if (volume < -22 && volume >= -26)
			volume = 4;
		else if (volume < -26  && volume >= -30)
			volume = 3;
		else if (volume < -30 && volume >= -35)
			volume = 2;
		else if (volume < -35 && volume >= -39)
			volume = 1;
		else if (volume == -80 || volume == -40)
			volume = 0;

		return volume;
	}

}
