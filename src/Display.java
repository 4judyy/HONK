import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Display class used to create JFrame window, screens, and adjust audio.
 * <br><br>
 * Creates JFrame and initializes each screen, shows screens, controls audio. <br><br>
 * <p>
 *     Uses cardlayout to manage screens, has method for showing screens and adjusting audio.
 * </p>
 * @author Tyler Charles Inwood
 */
public class Display {
	/** default tile size */
	final int defaultTileSize = 16;
	/** scale of each tile */
	final int scale = 3;
	/** calculating actual tile size */
	final int tileSize = defaultTileSize * scale;
	/** maximum screen columns */
	final int maxScreenCol = 20;
	/** maximum screen rows */
	final int maxScreenRow = 15;
	/** final width of screen */
	final int screenWidth = tileSize * maxScreenCol;    // 960 pixels
	/** final height of screen */
	final int screenHeight = tileSize * maxScreenRow;   // 832 pixels
	/** window for displaying everything */
	private JFrame window;
	/** container to hold each screen */
	private JPanel container;
	/** cardlayout to manage screens */
	private CardLayout cardLayout;
	/** login panel object for login panel screen */
	private Login loginPanel;
	/** main map object for main map screen */
	private MainMap MainMap;
	/** settings object for settings screen */
	private Settings settings;
	/** leaderboard object for leaderboard screen */
	private Leaderboard leaderboard;
	/** middlesex object for middlesex screen */
	private Middlesex middlesex;
	/** tutorial object for tutorial screen */
	private Tutorial tutorial;
	/** teacher view object for teacher view screen */
	private TeacherView teacher;
	/** coming soon object  for talbot screen */
	private ComingSoon talbot;
	/** coming soon object  for natural science screen */
	private ComingSoon naturalSci;
	/** clip object for background music */
	private MiddlesexLeaderboard middlesexLeaderboard;
	private Clip music;
	/** float control object controlling volume level */
	public FloatControl volumeControl;

	/**
	 * Display constructor.
	 * creates window for the game.
	 * creates container and cardlayout to manage screens
	 * creates objectcts for all screens in the game
	 * @throws IOException when music is unavailable
	 * @throws FontFormatException when font is not formatted correctly
	 */
	public Display() throws IOException, FontFormatException {

		// create window for game screens
		window = new JFrame("HONK!");
		window.setSize(screenWidth, screenHeight);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.black);
		window.setResizable(false);

		// create container to store screens, set cardlayout as layout
		container = new JPanel();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);

		//instantiate screen objects
		loginPanel = new Login(this);
		MainMap = new MainMap(this);
		settings = new Settings(this, "MainMap", volumeControl);
		leaderboard = new Leaderboard(this);
		tutorial = new Tutorial(this);
		middlesex = new Middlesex(this);
		middlesexLeaderboard = new MiddlesexLeaderboard(this);
		middlesex = new Middlesex(this);

		//middlesexStart = new MiddlesexStart(this);
		teacher = new TeacherView((this));
		talbot = new ComingSoon(this, "Talbot   ", "Welcome to Talbot College! Test your ability " +
				"to recognize different notes & music rhythm. ", new Color(250, 135, 138), new Color(255, 213, 215),
				"media/talbot.jpg");
		naturalSci = new ComingSoon(this, "Natural Science", "Welcome to Natural Science! Test out" +
				"your general science knowledge with some rapid fast multiple choice questions!", new Color(248, 140, 18), new Color(255, 194, 126),
				"media/naturalsci.jpg");

		// load and play the background music
		try {
			File musicFile = new File("media/goose_music.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
			music = AudioSystem.getClip();
			music.open(audioInputStream);
			music.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
			// Obtain the FloatControl for adjusting volume
			if (music != null && music.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				volumeControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
			}
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
			ex.printStackTrace();
		}

		//ADD SCREENS

		// add screens to the container
		container.add(loginPanel,"Login");
		container.add(MainMap, "MainMap");
		container.add(settings, "Settings");
		container.add(leaderboard, "Leaderboard");
		container.add(tutorial, "Tutorial");
		container.add(middlesex, "Middlesex");
		container.add(middlesexLeaderboard, "MiddlesexLeaderboard");
		container.add(teacher, "Teacher");
		container.add(talbot, "Talbot");
		container.add(naturalSci, "NaturalScience");

		// add container to window and show window
		window.add(container);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	/**
	 * adjust volume method.
	 * sets the volume according to the volume slider in settings
	 * @param value of volume slider in settings
	 */
	public void adjustVolume(int value){
		//switch case for chosen level of volume by user
		switch (value){
			case 10:
				volumeControl.setValue((float)6.0206);
				break;
			case 9:
				volumeControl.setValue((float)5);
				break;
			case 8:
				volumeControl.setValue((float)3);
				break;
			case 7:
				volumeControl.setValue((float)0);
				break;
			case 6:
				volumeControl.setValue((float)-3);
				break;
			case 5:
				volumeControl.setValue((float)-5);
				break;
			case 4:
				volumeControl.setValue((float)-10);
				break;
			case 3:
				volumeControl.setValue((float)-15);
				break;
			case 2:
				volumeControl.setValue((float)-25);
				break;
			case 1:
				volumeControl.setValue((float)-35);
				break;
			default:
				//mute audio
				volumeControl.setValue(volumeControl.getMinimum());
				break;
		}
	}

	/**
	 * Show Screen Method.
	 * Uses show method in display to show screen with given name
	 * Called when switching from one page to another.
	 * @param name of screen in which to switch to
	 */
	public void show(String name) {
		cardLayout.show(container, name);
	}

	/**
	 * getter method for settings object
	 * @return settings object
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * getter method for main map object
	 * @return main map object
	 */
	public MainMap getMainMap() {
		return MainMap;
	}

	/**
	 * getter method for talbot coming soon object
	 * @return talbot coming soon object
	 */
	public ComingSoon getTalbot(){
		return talbot;
	}

	/**
	 * getter method for natural science coming soon object
	 * @return natural science coming soon object
	 */
	public ComingSoon getNaturalSci() {
		return naturalSci;
	}

	/**
	 * getter method for middlsex object
	 * @return middlesex object
	 */
	public Middlesex getMiddlesex() {
		return middlesex;
	}

	/**
	 * getter method for teacher object
	 * @return teacher object
	 */
	public TeacherView getTeacher() {
		return teacher;
	}

	/**
	 * getter method for leaderboard object
	 * @return leaderboard object
	 */
	public Leaderboard getLeaderboard() {
		return leaderboard;
	}

	/**
	 * getter method for middlesex leaderboard object
	 * @return middlesex leaderboard object
	 */
	public MiddlesexLeaderboard getMiddlesexLeaderboard() {
		return middlesexLeaderboard;
	}
}

