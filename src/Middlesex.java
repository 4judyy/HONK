import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Map;
import java.util.Objects;

/**
 * The middlesex class is responsible for the functionality and creation of the middlesex mini-game screen.
 * <p>
 *     This class sets up the user interface of the middlesex page.
 *     Allows users to play our mini game.
 * </p>
 * @author Di Zhou
 */
public class Middlesex extends JPanel implements ActionListener {
    /** display object */
    private Display display;
    /** userProfile object of current user */
    private UserProfile user;
    /** database object */
    private Database database = new Database();
    /** question database object */
    QuestionDatabase questionDatabase = new QuestionDatabase();
    /** UserProfile map to parse user information */
    private Map userProfileMap = database.getUserProfileMap();
    /** score integer value, keeps track of current middlesex score */
    private int score;
    /** total seconds of one middlesex game */
    int seconds = 59;
    /** milliseconds for the timer UI */
    int mseconds = 99;
    /** question field for showing question */
    JTextArea questionText = new JTextArea();
    /** user text field for inputting answer */
    JTextField userText = new JTextField();
    /** submit button */
    JButton submit = new JButton();
    /** label for timer */
    JLabel timerLabel = new JLabel();
    /** label for score */
    JLabel scoreLabel = new JLabel();
    /** image object for background image */
    private Image background;
    /** font object for custom font */
    private Font pixelFont;

    /** Timer object to be used for the game timer and to update per tick
     */
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mseconds == 0) {
                if (seconds == 0) {
                    // Timer is finished
                    timer.stop();
                    results();
                    return;
                } else {
                    seconds--;
                    mseconds = 99; // Reset milliseconds
                }
            }
            mseconds--;
            timerLabel.setText("seconds left: " + String.format("%02d:%02d", seconds, mseconds));
        }
    });

    /**
     * Middlesex constructor. creates start of middlesex game screen
     * @param display object
     * @throws IOException when database has an error
     */
    public Middlesex(Display display) throws IOException{
        // initialize variables
        this.display = display;
        setLayout(null);

        // load the background image
        try {
            background = ImageIO.read(new File("media/middlesex.jpg"));
        } catch (IOException e) {
            System.out.println("Could not find image");
        }

        //load pixelhugger
        try {
            File pixelHuggerFile = new File("media/PixelHugger.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, pixelHuggerFile).deriveFont(Font.PLAIN, 39);
        } catch (IOException | FontFormatException e) {
            // Handle font loading errors
            e.printStackTrace();
        }

        // create the start UI of middlesex game
        createStart();
        createActionButtons();
        createLabels();
    }

    /**
     * create start method. creates the UI of the start page of the middlesex game
     * uses helper method to create all the UI components
     */
    private void createStart() {

        // set description text
        questionText.setLineWrap(true); // Enable line wrapping
        questionText.setWrapStyleWord(true); // Wrap at word boundaries
        questionText.setEditable(false);
        questionText.setCursor(null);
        questionText.setFont(pixelFont.deriveFont(Font.PLAIN, 20));
        questionText.append("Welcome to Middlesex College! Congratulations on embarking on this exciting journey to test and" +
                " expand your knowledge in the fascinating world of computer science. Whether you're a seasoned or just starting to explore the realm of bits and bytes," +
                " every challenge brings you one step closer to mastering the complexities of computer science");
        questionText.setForeground(Color.BLACK);
        // Set transparent background
        questionText.setOpaque(false);
        questionText.setBounds(270, 260, 457, 216);
        add(questionText);

        // add background box for question text
        addRoundedCornerLabel(250, 260, 500, 230, 500, 230, 100, 100, new Color(195, 173, 243, 200), 0, Color.WHITE, 0);

        // start button, same as submit button
        submit = new JButton();
        submit.setText("start game");
        submit.setForeground(Color.WHITE);
        submit.setFont(pixelFont.deriveFont(Font.PLAIN, 37));
        submit.setBackground(new Color(195, 173, 243, 255));
        submit.setBounds(340, 600, 300, 55);
        submit.setOpaque(true);
        submit.setBorderPainted(false);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // grab the user, set their score
                UserSession sessionManager = UserSession.getInstance();
                user = sessionManager.getUserProfile();
                score = user.getMiddlesexScore();

                //create question panel and repaint
                createQuestionPanel();
                repaint();

                //begin timer
                timer = new Timer(10, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (mseconds == 0) {
                            if (seconds == 0) {
                                // Timer is finished
                                timer.stop();
                                results();
                                return;
                            } else {
                                seconds--;
                                mseconds = 99; // Reset milliseconds
                            }
                        }
                        mseconds--;
                        timerLabel.setText("seconds left: " + String.format("%02d:%02d", seconds, mseconds));
                    }
                });
                timer.start();
            }
        });
        add(submit);
    }

    /**
     * method to create question panel UI
     */
    private void createQuestionPanel() {

        // returns the initial question & sets text
        Question q = returnRandomQuestion(score);
        questionText.setText(q.getQuestion());
        questionText.setFont(pixelFont.deriveFont(Font.PLAIN, 37));

        // this will change the text of our submit button to what we want!
        submit.setText("submit");

        // Remove previous action listeners
        ActionListener[] listeners = submit.getActionListeners();
        for (ActionListener listener : listeners) {
            submit.removeActionListener(listener);
        }

        // Add new action listener
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // actually grab the question

                Question q = questionDatabase.getQuestion(questionText.getText());
                // check if right or wrong.
                boolean valid = checkValid(q, userText.getText());
                if (!valid) {

                    // if answer is wrong, stop timer and popup to show correct answer
                    timer.stop();

                    String userAnswer = userText.getText();
                    String correctAnswer = q.getAnswer().get(0);
                    CustomDialog popup = new CustomDialog();
                    popup.createPopup("Your Answer: " + userAnswer + "\nExpected Answer: " + correctAnswer,"media/wronganswer.jpg","Incorrect Answer", JOptionPane.ERROR_MESSAGE, new Color(255, 182, 182));
                    timer.start();
                }
                // calls our next question
                nextQuestion();
            }
        });

        // make the enter key do the same as clicking submit
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // actually grab the question

                Question q = questionDatabase.getQuestion(questionText.getText());
                // check if right or wrong.
                boolean valid = checkValid(q, userText.getText());
                if (!valid) {

                    timer.stop();
                    String userAnswer = userText.getText();
                    String correctAnswer = q.getAnswer().get(0);

                    // create popup to show wrong answer's correct answer
                    CustomDialog popup = new CustomDialog();
                    popup.createPopup("Your Answer: " + userAnswer + "\nExpected Answer: " + correctAnswer,"media/wronganswer.jpg","Incorrect Answer", JOptionPane.ERROR_MESSAGE, new Color(255, 182, 182));
                    timer.start();
                }

                // calls our next question
                nextQuestion();
            }
        });

        // this will change the question to the question we want!

        // add our score label
        scoreLabel.setText("score: " + score);
        scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        scoreLabel.setBounds(35, 20, 300, 30);
        add(scoreLabel);

        // add our timer label
        timerLabel.setText("seconds left: " + String.format("%02d:%02d", seconds, mseconds));
        timerLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        timerLabel.setBounds(410, 80, 600, 50);
        add(timerLabel);

        // set up user enter text
        userText.setBounds(260, 500, 500, 60);
        userText.setText("enter your answer");
        userText.setFont(new Font("Helvetica", Font.BOLD, 35));
        userText.setBorder(BorderFactory.createEmptyBorder()); // Remove border
        userText.setBackground(new Color(195, 173, 243));
        userText.setForeground(Color.WHITE);

        userText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userText.getText().equals("enter your answer")) {
                    userText.setText("");
                    userText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userText.getText().isEmpty()) {
                    userText.setForeground(Color.BLACK);
                    userText.setText("enter your answer");
                }
            }
        });

        add(userText);
    }

    /**
     * method to retrieve next question
     * updates question shown and score
     */
    private void nextQuestion() {
        if (seconds <= 60 && seconds >= 0) {
            Question q = returnRandomQuestion(score);

            questionText.setText(q.getQuestion());
            userText.setText(""); // Clear user input field

            scoreLabel.setText("score: " + score); // Update score label
        } else {
            results();
        }
    }

    /**
     * show results, show middlesex leaderbord
     */
    private void results() {

        // update score of individual
        user.setMiddlesexScore(score);
        user.setTotalScore(score + user.getTalbotScore() + user.getNaturalSciencesScore());

        userProfileMap.put(user.getUsername(), user);

        // save to json file
        database.saveDataToJson();

        updateStats();
        showScreen("MiddlesexLeaderboard");
    }

    /**
     * check if users answer is correct
     * @param question current question
     * @param text user's answer submitted
     * @return boolean, true -> user answer was correct, false -> user answer was wrong
     */
    private boolean checkValid(Question question, String text) {
        if (seconds < 61 && seconds >= 0) {
            //if the users answer to the question is correct
            if (questionDatabase.validateAnswer(question, text)) {
                if (question.getDifficulty() == 1) {
                    score += 10;
                    return true;
                } else if (question.getDifficulty() == 2) {
                    score += 15;
                    return true;
                } else if (question.getDifficulty() == 3) {
                    score += 30;
                    return true;
                }
                // if user is incorrect
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Debugger exclusive method.
     * Debuggers get new input field in bottom left,
     * can submit number 1-3 to skip levels
     */
    public void ifDebugger() {

        // create debug text field
        JTextField debugCode = new JTextField();
        debugCode.setText("input progression (1-3)");
        debugCode.setBackground(new Color(164, 99, 202));
        debugCode.setForeground(Color.WHITE);
        debugCode.setFont(pixelFont.deriveFont(0, 18));
        debugCode.setBorder(null); // Remove border
        debugCode.setBounds(39, 600, 230, 55);

        debugCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (debugCode.getText().equals("input progression (1-3)")) {
                    debugCode.setText("");
                    debugCode.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (debugCode.getText().isEmpty()) {
                    debugCode.setForeground(Color.BLACK);
                    debugCode.setText("input progression (1-3)");
                }
            }
        });

        debugCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserSession sessionManager = UserSession.getInstance();
                user = sessionManager.getUserProfile();
                debug(Integer.parseInt(debugCode.getText()));
            }
        });

        add(debugCode);
        repaint();
    }

    /**
     * updates debug users middlesex score to gain access to difficulty
     * @param difficulty difficulty level that corresponds to the level being skipped to
     */
    private void debug(int difficulty) {
        //update score according to what difficulty was selected
        if (difficulty == 1) {
            user.setMiddlesexScore(0);
            score = 0;
        }
        if (difficulty == 2) {
            user.setMiddlesexScore(500);
            score = 500;
        }
        if (difficulty == 3) {
            user.setMiddlesexScore(1000);
            score = 1000;
        }
    }

    /**
     * Creates action buttons helper method
     * uses create actions buttons helper method
     */
    private void createActionButtons() {
        // Action buttons with common attributes
        JButton[] actionButtons = {
                createActionButton("back", 724, 20, 194, 77, Color.WHITE, new Color(0, 0, 0, 0), new Font("Helvetica", Font.BOLD, 44))
        };

        actionButtons[0].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();

                if (user != null && seconds != 0) {
                    results();

                }
                seconds = 60;
                mseconds = 0;

                showScreen("MainMap");

                // reset everything
                newScreen();

            }
        });

        // make the escape key mapped to the back button
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapePressed");
        ActionMap actionMap = getActionMap();
        actionMap.put("escapePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();

                showScreen("MainMap");
                newScreen();


            }
        });

        // Add action buttons to the panel
        for (JButton button : actionButtons) {
            add(button);
        }
    }

    /**
     * Create action button helper method
     * given params, creates a specific action button
     * @param text title of action button
     * @param x coordinate of action button
     * @param y coordinate of action button
     * @param width of action button
     * @param height of action button
     * @param foreground color of foreground of action button
     * @param background color of background of action button
     * @param font used for text of action button
     * @return unique action button created with parameters
     */
    private JButton createActionButton(String text, int x, int y, int width, int height, Color foreground, Color background, Font font) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setBackground(background);
        button.setFont(font);
        button.setForeground(foreground);

        return button;
    }

    /**
     * Method to create labels
     */
    private void createLabels() {

        // Custom label for the location of the page
        CustomJLabel title = new CustomJLabel("Middlesex ", -1.6);
        title.setFont(new Font("Helvetica", Font.BOLD, 60));
        title.setOpaque(false);

        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int textWidth = metrics.stringWidth("Middlesex ");

        title.setBounds(77, 78, textWidth, 100);
        title.setLetterSpacing(-3);
        title.setForeground(Color.WHITE);
        add(title);

        addRoundedCornerLabel(23, 43, textWidth + 50, 120, textWidth + 50, 100, 90, 90, new Color(130, 84, 205), -1.6, Color.WHITE, 0); // title pill
        addRoundedCornerLabel(743, 24, 165, 64, 161, 62, 64, 64, new Color(37, 165, 92), 0, Color.WHITE, 0); // back pill


        addRoundedCornerLabel(30, 10, 900, 50, 900, 50, 30, 30, new Color(164, 99, 202, 80), 0, Color.BLACK, 0); // Top banner
        addRoundedCornerLabel(400, 80, 260, 50, 260, 50, 30, 30, new Color(164, 99, 202, 80), 0, Color.BLACK, 0); // Top banner

    }

    /**
     * Creates a rounded corner label with given specifications as parameters
     * @param labelX x coordinate of label
     * @param labelY y coordinate of label
     * @param labelWidth width of label
     * @param labelHeight height of label
     * @param stickerWidth width of sticker
     * @param stickerHeight height of sticker
     * @param arcWidth width of the arc
     * @param arcHeight height of the arc
     * @param fillColor background color of the label
     * @param degrees degree of the label corner
     * @param borderColor color of the label border
     * @param borderWidth width of the label border
     */
    private void addRoundedCornerLabel(int labelX, int labelY, int labelWidth, int labelHeight, int stickerWidth, int stickerHeight, int arcWidth, int arcHeight, Color fillColor, double degrees, Color borderColor, int borderWidth) {
        RoundIcon icon = new RoundIcon(stickerWidth, stickerHeight, arcWidth, arcHeight, fillColor, degrees, borderColor, borderWidth);
        JLabel label = new JLabel("", icon, JLabel.LEFT);
        label.setBounds(labelX, labelY, labelWidth + borderWidth, labelHeight + borderWidth); // Adjust bounds to include border width
        add(label);
    }

    /**
     * returns random question, based on users current score
     * @param score users current score
     * @return question that is the proper difficulty
     */
    private Question returnRandomQuestion(int score) {
        Question question = questionDatabase.getRandomQuestion(score, "middlesex");
        return question;
    }

    /**
     * update users stats based on their performance in middlesex game
     */
    private void updateStats() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // this way we only grab settings ONCE it was created
                TeacherView teacherView = display.getTeacher();
                try {
                    teacherView.updateView();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Leaderboard leaderboard = display.getLeaderboard();
                try {
                    leaderboard.updateTopTotalScore();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                MiddlesexLeaderboard middlesexLeaderboard = display.getMiddlesexLeaderboard();
                try {
                    middlesexLeaderboard.updateMiddlesexScore();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Show Screen Method.
     * Uses show method in display to show screen with given name
     * Called when switching from one page to another.
     * @param name of screen in which to switch to
     */
    private void showScreen(String name) {
        timer.restart();
        timer.stop();
        display.show(name);
    }

    /**
     * used to paint the background image
     * @param g the <code>Graphics</code> object to protect
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image at (0, 0) with the same size as the panel
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * New Screen Method.
     * Resets the entire page in order for everything be ready for next game if they want to play again
     */
    public void newScreen() {

        score = 0;
        seconds = 60;
        mseconds = 0;

        questionText.setText("");

        // reset everything
        removeAll();

        createStart();
        createActionButtons();
        createLabels();

        if (user!= null) {
            if (Objects.equals(user.getPermission(), "Developer")) {
                ifDebugger();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        //blank method, but must be included
    }
}
