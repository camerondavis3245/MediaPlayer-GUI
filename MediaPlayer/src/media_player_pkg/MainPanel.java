package media_player_pkg;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;



public class MainPanel extends JPanel {

	private JPanel panel;
	private JLabel songLabel;
	private JButton playButton;
	private JButton pauseButton;
	private JButton rewindButton;
	private JButton fastForwardButton;
	private JSlider volumeSlider;
	private Audio audio;


	private long clipTimePosition;

	public MainPanel() {

		//Create Audio Object
		audio = new Audio(".//src//music//");

		//Creates panel for media player
		setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 450, 300);
		panel.setBackground(UIManager.getColor("Slider.background"));
		add(panel);
		panel.setLayout(null);

		//Song name label
		songLabel = new JLabel("");
		songLabel.setHorizontalAlignment(SwingConstants.CENTER);
		songLabel.setFont(new Font("Baskerville Old Face", Font.BOLD | Font.ITALIC, 25));
		songLabel.setBounds(10, 112, 430, 69);
		panel.add(songLabel);

		//Creates JComboBox to be able to select song
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 430, 32);
		comboBox.setModel(new DefaultComboBoxModel(audio.getSongList().toArray()));
		panel.add(comboBox);

		//Pause button
		pauseButton = new JButton("Pause");
		pauseButton.setEnabled(false); //Set button to not clickable until play button is pressed
		pauseButton.setBounds(232, 206, 89, 23);
		panel.add(pauseButton);


		//Play button
		playButton = new JButton("Play");
		playButton.setBounds(125, 206, 89, 23);
		panel.add(playButton);


		//Fast forward button
		rewindButton = new JButton("<<");
		rewindButton.setBounds(10, 251, 126, 23);
		panel.add(rewindButton);

		//Rewind button
		fastForwardButton = new JButton(">>");
		fastForwardButton.setBounds(305, 251, 135, 23);
		panel.add(fastForwardButton);

		//Volume label
		JLabel volumeLabel = new JLabel("Volume: -17");
		volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		volumeLabel.setBounds(146, 268, 139, 32);
		panel.add(volumeLabel);

		//Volume slider controller
		volumeSlider = new JSlider(-40,6);
		volumeSlider.setValue(-17);
		volumeSlider.setMaximum(6);
		volumeLabel.setText("Volume: 5");


		volumeSlider.setBounds(146, 242, 149, 32);
		panel.add(volumeSlider);
		
		
		

		//Change listener for slider that changes volume of music
		volumeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				volumeLabel.setText("Volume: " + Integer.toString(audio.changeVolume(volumeSlider.getValue())));
			}
		});

		//If button is pressed, song will be paused/resumed from that position
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * If button text is set to "Pause", the audio will be stopped on current position,
				 * and button text will be set to "Resume".
				 * If button text is set to "Resume", the audio will start where it left off,
				 * and will set button text to "Pause".
				 */
				if (pauseButton.getText() == "Pause") {
					pauseButton.setText("Resume");
					clipTimePosition = audio.pauseSong(); //clip.getMicrosecondPosition(); //save current position of file
					//stops audio clip
				}else if (pauseButton.getText() == "Resume") {
					pauseButton.setText("Pause");
					audio.resumeSong(clipTimePosition); //sets position and starts audio clip

				}

			}
		});

		//Action listener for play button
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == playButton) {

					//Check to see if stream is not null, If song is playing already, stop the current song
					if(!(audio.getAudioStream() == null)) {
						audio.stopSong();
						pauseButton.setText("Pause"); //if you hit play again after pressing pause, it will reset the pause/resume button
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					try {
						//Plays song that is selected in ComboBox
						audio.playSong((String)comboBox.getSelectedItem(), pauseButton, songLabel, volumeSlider);


					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		});
		//Action listener for rewinding song by 10 seconds
		rewindButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audio.rewindSong();
			}
		});
		
		//Action listener for fast forwarding song by 10 seconds
		fastForwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audio.fastForwardSong();
			}
		});
	}
}
