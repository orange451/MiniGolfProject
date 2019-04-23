package golf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import engine.GameCanvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Main {
	
	final static String GAME_TITLE = "Matt McCaslin's ProMiniGolf Tour";
	static Clip clip;
	
	public static void main(String[] args) {
		
		// Create the JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(GAME_TITLE);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.setLayout(new BorderLayout());
		
		// Create amazing title
		JLabel title = new JLabel("<html>" + GAME_TITLE + "</html>", SwingConstants.RIGHT);
		title.setFont(new Font("Courier", Font.BOLD, 38));
		title.setBorder(new CompoundBorder(title.getBorder(), new EmptyBorder(10, 225, 0, 5)));
		
		// Create start button
		JButton startBtn = new JButton("Start");
		startBtn.setFont(new Font("Serif", Font.BOLD,25));
		startBtn.setSize(new Dimension(80, 65));
		
		// Create UI for welcome screen
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Resources\\Images\\mg_back.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		frame.setContentPane(new WelcomeUI(img, title, startBtn));

		// Display application
		frame.pack();
		frame.setVisible(true);
		
		// Play best golf music known to man
		String greatGolfMusic = "Resources\\Sounds\\start_menu_theme.wav";    
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(greatGolfMusic).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		// Start playing the next best golf game since Tiger Woods PGA Tour
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clip.stop();
				// Add 3d canvas
				GameCanvas c = new GameCanvas(new GolfGame());
				c.setPreferredSize(new Dimension(640,480));
				//frame.add(c);
				frame.setContentPane(c);
				frame.setSize(new Dimension(640, 480));
				frame.revalidate();
				frame.repaint();
			}
		});
	}
}

// Welcome screen
class WelcomeUI extends JPanel {
    private Image image;
    public WelcomeUI(Image image, JLabel title, JButton startBtn) {
        this.image = image;
        setLayout(new BorderLayout());
        JPanel btnHolder = new JPanel();
        btnHolder.add(startBtn);
        btnHolder.setOpaque(false);
		add(title, BorderLayout.NORTH);
		add(btnHolder, BorderLayout.CENTER);
    }
    
    // Put image as background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}