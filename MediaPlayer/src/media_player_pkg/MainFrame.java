package media_player_pkg;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainFrame extends JFrame{

	public static void main(String[] args) {

		//Creates window for application
		JFrame frame = new JFrame("Media Player");
		frame.pack();

		//Be able to close window by red "X"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create ImageIcon object and set to musicIcon.png
		ImageIcon image = new ImageIcon(".//src//media_player_pkg//musicIcon.png");
		//Set Icon to window with the music Icon
		frame.setIconImage(image.getImage());

		//Set size of window to 300 by 400
		frame.setSize(465, 335);
		frame.setResizable(false);
		frame.add(new MainPanel());

		//Make window visible
		frame.setVisible(true);

	}

}
