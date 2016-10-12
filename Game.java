// Sanketh Hegde
// 04/08/13
// Game.java
// Grid 9 is a game meant to test the user's intelligence, timing, and memory through 5 games
// and 4 subject questions. The 5 games are: BucketCatcher, KnockBack, Pong, KeypadRevenge, and
// Helicopter. The four subjects are: Math, Literature, Biology, and Java. The game works by
// randomly selecting a game or question from the main grid and then let the user play or 
// answer that panel in a certain amount of time. If the user is playing a game and the time
// runs out, the game will zoom back out and pick another question or game. If the time runs out
// when the user is answering a question, the user will lose that panel for the rest of that game.
// If the user loses a game while playing, they lose that panel. When the user loses all 9 panels,
// the user loses the entire game and they are given a score based on their intelligence, memory,
// and timing capabilities. This game also features a very simple username/password interface
// as well as quality control in the options menu. 

// In BucketCatcher, the user moves a bucket left and right in order to catch falling eggs. The user
// can move across the screen as well. If the user fails to catch an egg, they lose.

// In KnockBack, the user moves an arrow up and down as it shoots balls right and left to knock back
// oncoming rectangles. If the rectangles hit the center, the user loses.

// In Pong, the user controls a rectangular block and bounces a ball around the screen. If the ball
// hits the ground, the user loses.

// In Helicopter, the user controls a helicopter up and down and tries to avoid red rectangles that
// come across the screen.

// In KeypadRevenge, the user tries to match the key with the keys that come up on the screen. If the 
// key they press does not match the key on the screen, they lose.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import sun.audio.*;

// MY GAME!
public class Game
{
	String menuuser;
	boolean skipintro = false; // boolean for if the user wants to skip the main intro of the game
	
	// format for grid9accounts.txt:
	//    username        password        % record   intelligence, memory, timing record     bucket, heli, key, pong, dodge, average records 
	
	int rec_percent, rec_intel, rec_mem, rec_time, rec_bucket, rec_heli, rec_key, rec_pong, rec_dodge, rec_average;

	// all random variables that control different aspects of the game and option panel
	int audioloop = 0;
	int quality = 2;
	JFrame frame;
	WelcomePanel welcome;
	TopUserPanel top;
	PrintWriter outFile;
	Scanner inFile;
	File accountdetails;
	String gameuser = "Log In!";
	boolean correct = false;
	boolean fakegridtimerstart = true;
	boolean paintpaint;
	int backcounter = 0;
	boolean logoff = false;
	boolean playplay = false;
	int mathcounter, biocounter, javacounter, litcounter;
	boolean playmath, playbio, playlit, playjava, playbucket, playkey, playheli, playdodge, playpong;
	int diff_math, diff_lit, diff_bio, diff_java, diff_bucket, diff_key, diff_heli, diff_dodge, diff_pong;
	boolean secondtime = false;
	
	// Audio is disabled (that's why it's commented out)
	/*
	InputStream playmusic_in = null;
	AudioStream playmusic_as = null;
	InputStream intromusic_in = null;
	AudioStream intromusic_as = null;
	InputStream playalive_in = null;
	InputStream playdead_in = null;
	AudioStream playdead_as = null;
	AudioStream playalive_as = null;
	*/
	
	
	// All string arrays for all subject questions including math, lit, bio, java
	String [] mathq;
	String [] matha1;
	String [] matha2;
	String [] matha3;
	String [] matha4;
	String [] matharight;
	
	String [] litq;
	String [] lita1;
	String [] lita2;
	String [] lita3;
	String [] lita4;
	String [] litaright;
	
	String [] javaq;
	String [] javaa1;
	String [] javaa2;
	String [] javaa3;
	String [] javaa4;
	String [] javaaright;
	
	String [] bioq;
	String [] bioa1;
	String [] bioa2;
	String [] bioa3;
	String [] bioa4;
	String [] bioaright;
	
	WelcomePanel2 welcome2;
	InstructionsPanel instructions; // instructions of everything
	OptionsPanel options; // options menu
	GamePanel game; // where all games take place
	PracticePanel practice; // not used
	
	JPanel cards; // cards is the main jpanel where every single panel is stored
	Container con;
	CardLayout maincards;
	
	Timer timerbackground; // the background animation of the boxes
	Mover moverbackground;
	
	// All Images for everything
	BufferedImage volumemute;
	BufferedImage volumefull;
	BufferedImage math, science, lit, java;
	BufferedImage arrowpad;
	BufferedImage tut, thegrid;
	BufferedImage pongblock, pongball, pongbackground;
	BufferedImage bucketimage, egg, farm, bucketover;
	BufferedImage helicopterimage, cloudimage, helibackgroundimage, asteroidimage;
	BufferedImage upimage, downimage, leftimage, rightimage, keyupimage, keydownimage, keyleftimage, keyrightimage, binarybackgroundimage;
	BufferedImage bucketcatcherpic, pongpic, helicopterpic, keypadrevengepic, dodgepic;
	BufferedImage fourarrow_up, fourarrow_down, fourarrow_left, fourarrow_right;
	BufferedImage constructionimage;
	
	// booleans for if the user clicks a button on the main menu
	boolean goinstructions, gooptions, gogame, gopractice;
	SoundMover soundmover = new SoundMover();
	Timer soundtimer = new Timer(1000, soundmover);
	
	// Constructor for Game
	public Game()
	{
		gameuser = "Log In!";
		//playheli = playpong = playdodge = playbucket = playkey = playmath = playbio = playlit = playjava = true;
		//TEMP:
		playheli = playpong = playbucket = playkey = playmath = playbio = playlit = playjava = playdodge = false;
		mathcounter = biocounter = litcounter = javacounter = -1;
		diff_lit = diff_math = diff_pong = diff_java = diff_bucket = diff_key = diff_heli = diff_pong = diff_dodge = 0;
		
		// initializing all question types.
		mathq = new String[10];
		matha1 = new String[10];
		matha2 = new String[10];
		matha3 = new String[10];
		matha4 = new String[10];
		matharight = new String[10];
		
		litq = new String[10];
		lita1 = new String[10];
		lita2 = new String[10];
		lita3 = new String[10];
		lita4 = new String[10];
		litaright = new String[10];
		
		javaq = new String[10];
		javaa1 = new String[10];
		javaa2 = new String[10];
		javaa3 = new String[10];
		javaa4 = new String[10];
		javaaright = new String[10];
		
		bioq = new String[10];
		bioa1 = new String[10];
		bioa2 = new String[10];
		bioa3 = new String[10];
		bioa4 = new String[10];
		bioaright = new String[10];
		goinstructions = gooptions = gogame = gopractice = false;
		accountdetails = new File("grid9accounts.txt");
	}
	
	// Main Class
	public static void main(String [] args)
	{
		Game finalgame = new Game();
		finalgame.getQuestions(); // inits all questions
		finalgame.getImages(); // inits all bufferedimages
		finalgame.getSounds(); // inits all sounds (disabled)
		finalgame.Run(); // makes frame and starts actual game
	}
	
	// Method 1: inits all frames and panels and kicks off game
	public void Run()
	{
		if (!logoff)
			frame = new JFrame("Grid 9"); // window title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to exit window
		welcome = new WelcomePanel(); // initializes panel object
		top = new TopUserPanel();
		instructions = new InstructionsPanel();
		options = new OptionsPanel();
		game = new GamePanel();
		practice = new PracticePanel();
		welcome2 = new WelcomePanel2();
		
		cards = new JPanel(new CardLayout());
		cards.add(welcome, "WelcomePanel");
		cards.add(welcome2, "WelcomePanel2");
		cards.add(instructions, "InstructionsPanel");
		cards.add(options, "OptionsPanel");
		cards.add(practice, "PracticePanel");
		cards.add(game, "GamePanel");
		
		frame.getContentPane().add(top, BorderLayout.NORTH);
		top.repaint();
		frame.getContentPane().add(cards, BorderLayout.CENTER);
		frame.setSize(1270, 750); // expands window to fit 500x640 // sets sizes
		frame.setVisible(true);
		welcome.requestFocusInWindow();
		
		moverbackground = new Mover();
		timerbackground = new Timer(20, moverbackground);
		timerbackground.start();
	}
	
	// Method 2: if the user presses retry or menu, this will come up
	public void RunAgain(String user, boolean playrightaway)
	{
		//AudioPlayer.player.stop(playmusic_as);
		//System.out.println("gameuser before welcomepanel = " + user);
		gameuser = user;
		if (!logoff)
			frame = new JFrame("Grid 9"); // window title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to exit window
		welcome = new WelcomePanel(); // initializes panel object
		top = new TopUserPanel();
		instructions = new InstructionsPanel();
		options = new OptionsPanel();
		game = new GamePanel();
		practice = new PracticePanel();
		welcome2 = new WelcomePanel2();
		cards = new JPanel(new CardLayout());
		cards.add(welcome2, "WelcomePanel2");
		cards.add(instructions, "InstructionsPanel");
		cards.add(options, "OptionsPanel");
		cards.add(practice, "PracticePanel");
		cards.add(game, "GamePanel");
		
		CardLayout c = (CardLayout)(cards.getLayout());
		if (playrightaway)
		{
			soundtimer.start();
			//AudioPlayer.player.stop(intromusic_as);
			//AudioPlayer.player.start(playmusic_as);
			c.show(cards, "GamePanel");
		}
		
		//System.out.println("gameuser after wlecomepale = " + user);
		
		frame.getContentPane().add(top, BorderLayout.NORTH);
		top.repaint();
		frame.getContentPane().add(cards, BorderLayout.CENTER);
		frame.setSize(1270, 750); // expands window to fit 500x640 // sets sizes
		frame.setVisible(true);
		//welcome.requestFocusInWindow();
		
		moverbackground = new Mover();
		timerbackground = new Timer(20, moverbackground);
		timerbackground.start();
	}
	
	// backcounter controls the grid animation in the background
	class Mover implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			backcounter++;
			welcome.repaint();
			welcome2.repaint();
			if (goinstructions)
			{}
				instructions.repaint();
			if (gooptions)
			{}
				options.repaint();
		}
	}
	
	// The main password/username interface panel with everything
	class WelcomePanel extends JPanel implements MouseListener, MouseMotionListener
	{
		JLabel title;
		JPanel second;
		ThirdPanel third;
		FourthPanel fourth;
		Container comp;
		boolean showthird = true;
		boolean playhover, instructionshover, optionshover, practicehover;
		JPasswordField username;
		JPasswordField password;
		
		// Constructor
		public WelcomePanel()
		{
			//System.out.println("WelcomePanel");
			this.setLayout(new GridLayout(4, 0));
			
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
			
			title = new JLabel("Grid 9");
			title.setFont(new Font("Monotype", Font.BOLD, 144));
			title.setForeground(Color.gray);
			
			second = new JPanel();
			second.setLayout(new FlowLayout());
			second.add(title);
			second.setBackground(new Color(0,0,0,0));
			
			third = new ThirdPanel();
			third.setLayout(new GridLayout(0, 3));
			
			fourth = new FourthPanel();
			fourth.setLayout(new GridLayout(0, 3));
			fourth.setVisible(false);
			
			this.add(new JLabel(""));
			this.add(second);
			this.add(third);
			this.add(fourth);
		}
		
		// is grey and has username password bars
		class ThirdPanel extends JPanel
		{
			PasswordField passwordfield;	
		
			public ThirdPanel()
			{
				setBackground(Color.gray);
				
				passwordfield = new PasswordField();
				passwordfield.setBackground(Color.gray);
				this.add(new JLabel(""));
				this.add(passwordfield);
			}
			
			class PasswordField extends JPanel implements ActionListener, KeyListener
			{
				JPasswordField username;
				JPasswordField password;	
				JLabel userlabel, passlabel;		
				JButton logon;
				JButton register;
				String LogOn = "Log On";
				String Register = "Register";
			
				public PasswordField()
				{
					this.setLayout(new GridLayout(5, 0));
					
					username = new JPasswordField(25);
					username.setEchoChar((char)0);
					password = new JPasswordField(25);
					userlabel = new JLabel("Username: ");
					userlabel.setFont(new Font("Monotype", Font.PLAIN, 16));
					userlabel.setForeground(Color.white);
					userlabel.setLabelFor(username);
					passlabel = new JLabel("Password: ");
					passlabel.setFont(new Font("Monotype", Font.PLAIN, 16));
					passlabel.setForeground(Color.white);
					passlabel.setLabelFor(password);
					
					//username.addActionListener(this);
					//password.addActionListener(this);
					password.addKeyListener(this);
					password.requestFocus();
					
					JPanel user = new JPanel();
					user.setBackground(Color.gray);
					user.setLayout(new FlowLayout(FlowLayout.TRAILING));
					user.add(userlabel);
					user.add(username);
					JPanel pass = new JPanel();
					pass.setBackground(Color.gray);
					pass.setLayout(new FlowLayout(FlowLayout.TRAILING));
					pass.add(passlabel);
					pass.add(password);
					
					JPanel bottompassword = new JPanel();
					bottompassword.setLayout(new GridLayout(0, 4));
					bottompassword.setBackground(Color.gray);
					register = new JButton("Register");
					register.addActionListener(this);
					logon = new JButton("Log In");
					logon.addActionListener(this);
					logon.setActionCommand(LogOn);
					register.setActionCommand(Register);
					bottompassword.add(register);
					bottompassword.add(new JLabel(""));
					bottompassword.add(new JLabel(""));
					bottompassword.add(logon);
					password.setActionCommand(LogOn);
					
					this.add(new JLabel(""));
					this.add(user);
					this.add(pass);
					this.add(bottompassword);
					
				}
				
				// KeyListener methods
				public void keyTyped(KeyEvent e)
				{}
				
				public void keyPressed(KeyEvent e)
				{
					int value = e.getKeyCode();
					if (value == KeyEvent.VK_ENTER)
					{
						ArrayList<String> accountuser = new ArrayList<String>();
						ArrayList<String> accountpass = new ArrayList<String>();
						int onpass = 0;
						
						try
						{
							inFile = new Scanner(accountdetails);
						}
						catch(Exception ex)
						{
							//System.out.println("grid9accounts.txt not found!");
						}
						int readcounter = 0;
						while(inFile.hasNext())
						{
							if (onpass == 0)
							{
								accountuser.add(readcounter, (inFile.next()));
								////System.out.println("username = " + accountuser);
								onpass++;
							}
							else if (onpass == 1)
							{
								accountpass.add(readcounter, (inFile.next()));
								////System.out.println("password = " + accountpass);
								readcounter++;
								onpass=0;
							}
						}
						inFile.close();	
						////System.out.println(rec_percent + " " +  rec_intel + " " +  rec_mem + " " +  rec_time + " " +  rec_bucket + " " +  rec_heli + " " +  rec_key + " " +  rec_pong + " " +  rec_dodge + " " +  rec_average);
						String typeuser = "" + username.getText();
						String typepass = "" + password.getText();
						////System.out.println("Username Typed = " + typeuser);
						////System.out.println("Password Typed = " + typepass);
						////System.out.println("Username Recorded = " + accountuser.get(0));
						////System.out.println("Password Recorded = " + accountpass.get(0));
						for (int i = 0; i < (accountuser.size()); i++)
						{
							if (typeuser.equals(accountuser.get(i)) && typepass.equals(accountpass.get(i)))
							{
								correct = true;
								if (!secondtime)
									gameuser = accountuser.get(i);
								top.repaint();
								//third.setVisible(false);
								welcome.repaint();
								break;
							}
						}
						if (correct)
						{
							//System.out.println("Correct!");
						}
						else
						{
							//System.out.println("Wrong!");
							JOptionPane.showMessageDialog(frame,
							"Invalid password or username.", "Invalid Login",
							JOptionPane.ERROR_MESSAGE);
						}	
					}
				}
				
				public void keyReleased(KeyEvent e)
				{}
				
				public void actionPerformed(ActionEvent e)
				{
					//grid9accounts.txt format:
					//    username        password        % record   intelligence, memory, timing record     bucket, heli, key, pong, dodge, average records 
					String command = e.getActionCommand();
					ArrayList<String> accountuser = new ArrayList<String>();
					ArrayList<String> accountpass = new ArrayList<String>();
					int onpass = 0;
					
					try
					{
						inFile = new Scanner(accountdetails);
					}
					catch(Exception ex)
					{
						//System.out.println("grid9accounts.txt not found!");
					}
					int readcounter = 0;
					while(inFile.hasNext())
					{
						if (onpass == 0)
						{
							accountuser.add(readcounter, (inFile.next()));
							////System.out.println("username = " + accountuser);
							onpass++;
						}
						else if (onpass == 1)
						{
							accountpass.add(readcounter, (inFile.next()));
							////System.out.println("password = " + accountpass);
							readcounter++;
							onpass=0;
						}
					}
					inFile.close();
					////System.out.println(rec_percent + " " +  rec_intel + " " +  rec_mem + " " +  rec_time + " " +  rec_bucket + " " +  rec_heli + " " +  rec_key + " " +  rec_pong + " " +  rec_dodge + " " +  rec_average);
					
					if (LogOn.equals(command))
					{
						String typeuser = "" + username.getText();
						String typepass = "" + password.getText();
						////System.out.println("Username Typed = " + typeuser);
						////System.out.println("Password Typed = " + typepass);
						////System.out.println("Username Recorded = " + accountuser.get(0));
						////System.out.println("Password Recorded = " + accountpass.get(0));
						for (int i = 0; i < (accountuser.size()); i++)
						{
							if (typeuser.equals(accountuser.get(i)) && typepass.equals(accountpass.get(i)))
							{
								correct = true;
								if (!secondtime)
									gameuser = accountuser.get(i);
								top.repaint();
								//third.setVisible(false);
								welcome.repaint();
								break;
							}
						}
						if (correct)
						{
							//System.out.println("Correct!");
						}
						else
						{
							//System.out.println("Wrong!");
							JOptionPane.showMessageDialog(frame,
                			"Invalid password or username.", "Invalid Login",
                			JOptionPane.ERROR_MESSAGE);
						}
					}
					if (Register.equals(command))
					{
						fourth.setVisible(true);
					}
				}
				
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
			}
		}
		
		// if the user presses register, this comes up
		class FourthPanel extends JPanel
		{
			PasswordField passwordfield;		
		
			public FourthPanel()
			{
				setVisible(true);
				passwordfield = new PasswordField();
				this.add(new JLabel(""));
				this.add(passwordfield);
			}
			
			class PasswordField extends JPanel implements ActionListener
			{	
				JLabel userlabel, passlabel;		
				JButton logon;
				JButton register;
				String LogOn = "Log On";
				String Register = "Register";
			
				public PasswordField()
				{
					this.setLayout(new GridLayout(5, 0));
					username = new JPasswordField(25);
					username.setEchoChar((char)0);
					password = new JPasswordField(25);
					userlabel = new JLabel("Username: ");
					userlabel.setFont(new Font("Monotype", Font.PLAIN, 16));
					userlabel.setForeground(Color.gray);
					userlabel.setLabelFor(username);
					passlabel = new JLabel("Password: ");
					passlabel.setFont(new Font("Monotype", Font.PLAIN, 16));
					passlabel.setForeground(Color.gray);
					passlabel.setLabelFor(password);
					
					//username.addActionListener(this);
					//password.addActionListener(this);
					
					JPanel user = new JPanel();
					//user.setBackground(Color.gray);
					user.setLayout(new FlowLayout(FlowLayout.TRAILING));
					user.add(userlabel);
					user.add(username);
					JPanel pass = new JPanel();
					//pass.setBackground(Color.gray);
					pass.setLayout(new FlowLayout(FlowLayout.TRAILING));
					pass.add(passlabel);
					pass.add(password);
					
					JPanel bottompassword = new JPanel();
					bottompassword.setLayout(new GridLayout(0, 4));
					//bottompassword.setBackground(Color.gray);
					register = new JButton("Register!");
					register.addActionListener(this);
					logon = new JButton("Cancel");
					logon.addActionListener(this);
					logon.setActionCommand(LogOn);
					register.setActionCommand(Register);
					bottompassword.add(logon);
					bottompassword.add(new JLabel(""));
					bottompassword.add(new JLabel(""));
					bottompassword.add(register);
					password.setActionCommand(LogOn);
					
					JPanel regtitle = new JPanel();
					regtitle.setLayout(new FlowLayout());
					JLabel registertitle = new JLabel("Register Now!");
					registertitle.setFont(new Font("Monotype", Font.BOLD, 18));
					registertitle.setForeground(Color.gray);
					regtitle.add(registertitle);
					this.add(regtitle);
					this.add(user);
					this.add(pass);
					this.add(bottompassword);
					
				}
				
				public void actionPerformed(ActionEvent e)
				{
					String command = e.getActionCommand();
					
					if (Register.equals(command))
					{
						String newpass, newuser;
						newuser = username.getText();
						newpass = password.getText();
						
						if (newuser.equals("") || newpass.equals("") || (newuser.length() < 3 || newpass.length() < 3))
						{
							JOptionPane.showMessageDialog(frame, "Username or Password too short", "Invalid Registration", JOptionPane.ERROR_MESSAGE);
						}
						else if (newuser.equals(newpass))
						{
							JOptionPane.showMessageDialog(frame, "Username and Password cannot match", "Invalid Registration", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							try
							{
								outFile = new PrintWriter(new FileWriter(accountdetails, true));
							}
							catch (Exception ex)
							{
								//System.out.println("Could not write to grid9accounts.txt!");
							}
							outFile.println(newuser + " " + newpass);
											//    username        password        % record   intelligence, memory, timing record     bucket, heli, key, pong, dodge, average records          
							outFile.close();
							fourth.setVisible(false);
						}
					}
					
					if (LogOn.equals(command))
					{
						fourth.setVisible(false);
					}
				}
			
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
				}
			}
		}
			
		// MouseEvent methods
		public void mouseClicked(MouseEvent e)
		{
		
		}
		
		public void mousePressed(MouseEvent e)
		{

		}
		
		public void mouseEntered(MouseEvent e)
		{
		
		}
		
		public void mouseExited(MouseEvent e)
		{
		
		}
		
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
		public void mouseMoved(MouseEvent e)
		{

		}
		
		public void mouseDragged(MouseEvent e)
		{
		
		}
		
		public void paintComponent(Graphics g) // WelcomePanel paintcomp
		{
			super.paintComponent(g);
			g.setColor(Color.lightGray);
			////System.out.println("backcounter = " + backcounter);
			for (int x = -2000; x < 2000; x+=200)
			{
				for (int y = -1000; y < 1000; y+=200)
				{
					if (backcounter > 300)
					{
						backcounter *= -1;
					}
					g.drawRect(x-backcounter, y-backcounter, 200, 200);
				}
			}
			if (correct)
			{
				CardLayout c = (CardLayout)(cards.getLayout());
				c.show(cards, "WelcomePanel2");
			}
			if (logoff)
			{

			}
		}
	}
	
	//
	///
	//
	/// WELCOMEPANEL2
	//
	///
	//
	///
	
	// welcome panel 2 is where the actual play menu is (instructions and options tambien)
	class WelcomePanel2 extends JPanel implements MouseListener, MouseMotionListener
	{
		JLabel title;
		JPanel second;
		Container comp;
		PlayPanel play;
		PractPanel pract;
		boolean showthird = true;
		boolean playhover, instructionshover, optionshover, practicehover;
		
		public WelcomePanel2()
		{
			//System.out.println("welcome2Panel");
			this.setLayout(new GridLayout(4, 0));
			
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
			
			title = new JLabel("Grid 9");
			title.setFont(new Font("Monotype", Font.BOLD, 144));
			title.setForeground(Color.gray);
			
			second = new JPanel();
			second.setLayout(new FlowLayout());
			second.add(title);
			second.setBackground(new Color(0,0,0,0));
			
			play = new PlayPanel();
			play.setBackground(new Color(0,0,0,0));
			
			pract = new PractPanel();
			
			this.removeAll();
			this.setLayout(new GridLayout(3, 0));
			//this.add(new JLabel(""));
			this.add(second);
			this.add(play);
			this.add(pract);
		}
		
		// play button
		class PlayPanel extends JPanel
		{
			JLabel playlabel, instructionslabel, optionslabel;
			Border labelborder;
			JPanel p, i, o;
			JPanel color = new JPanel();
			
			public PlayPanel()
			{
				playhover = instructionshover = optionshover = false;
				labelborder = BorderFactory.createLineBorder(Color.white);
				this.setLayout(new GridLayout(0, 3));
				setBackground(new Color(0,0,0,0));
				
				p = new JPanel();
				p.setLayout(new BorderLayout());
				i = new JPanel();
				i.setLayout(new BorderLayout());
				o = new JPanel();
				o.setLayout(new BorderLayout());
				p.setBackground(Color.gray);
				i.setBackground(new Color(0,0,0,0));
				o.setBackground(new Color(0,0,0,0));
				
				playlabel = new JLabel();
				playlabel.setText("PLAY");
				playlabel.setFont(new Font("Monotype", Font.BOLD, 105));
				playlabel.setForeground(color.getBackground());
				playlabel.setHorizontalAlignment(SwingConstants.CENTER);
				p.add(playlabel, BorderLayout.CENTER);
				
				instructionslabel = new JLabel();
				instructionslabel.setText("INSTRUCTIONS");
				instructionslabel.setFont(new Font("Monotype", Font.BOLD, 45));
				instructionslabel.setForeground(Color.gray);
				instructionslabel.setHorizontalAlignment(SwingConstants.CENTER);
				i.add(instructionslabel, BorderLayout.CENTER);
				
				optionslabel = new JLabel();
				optionslabel.setText("OPTIONS");
				optionslabel.setFont(new Font("Monotype", Font.BOLD, 45));
				optionslabel.setForeground(Color.gray);
				optionslabel.setHorizontalAlignment(SwingConstants.CENTER);
				o.add(optionslabel, BorderLayout.CENTER);
				
				this.add(i);
				this.add(p);
				this.add(o);
			}
			
			public void paintComponent(Graphics g) // FOR welcome2PANEL
			{
				super.paintComponent(g);
				g.setColor(Color.white);
				if (instructionshover)
				{
					instructionslabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.gray));
				}
				else
				{
					instructionslabel.setBorder(BorderFactory.createEmptyBorder());
				}
				if (playhover)
				{
					playlabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.white));
					playlabel.setForeground(Color.white);
				}
				else
				{
					playlabel.setBorder(BorderFactory.createEmptyBorder());
					playlabel.setForeground(color.getBackground());
				}
				if (optionshover)
				{
					optionslabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.gray));
				}
				else
				{
					optionslabel.setBorder(BorderFactory.createEmptyBorder());
				}
				
				if (goinstructions)
				{
					CardLayout c = (CardLayout)(cards.getLayout());
					instructions.repaint();
					c.show(cards, "InstructionsPanel");
				}
				if (gooptions)
				{
					CardLayout c = (CardLayout)(cards.getLayout());
					options.repaint();
					c.show(cards, "OptionsPanel");
				}
				if (gogame)
				{
					timerbackground.stop();
					CardLayout c = (CardLayout)(cards.getLayout());
					game.repaint();
					game.requestFocusInWindow();
					c.show(cards, "GamePanel");
				}
			}
		}
		
		class PractPanel extends JPanel
		{			
			JLabel practicelabel;
			JPanel color = new JPanel();
		
			public PractPanel()
			{
				setVisible(false);
				this.setLayout(new GridLayout(0, 3));
				setBackground(new Color(0,0,0,0));
				
				JPanel p;
				p = new JPanel();
				p.setLayout(new BorderLayout());
				p.setBackground(new Color(0,0,0,0));
				
				practicelabel = new JLabel();
				practicelabel.setText("PRACTICE");
				practicelabel.setFont(new Font("Monotype", Font.BOLD, 50));
				practicelabel.setForeground(Color.gray);
				practicelabel.setHorizontalAlignment(SwingConstants.CENTER);
				p.add(practicelabel, BorderLayout.CENTER);
				
				this.add(new JLabel(""));
				this.add(p);
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if (practicehover)
				{
					//practicelabel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.gray));
				}
				else
				{
					//practicelabel.setBorder(BorderFactory.createEmptyBorder());
				}
			}
		}
			
		public void mouseClicked(MouseEvent e)
		{
		
		}
		
		public void mousePressed(MouseEvent e)
		{
			if (e.getY() > 235 && e.getY() < 466)
			{
				if (e.getX() > 0 && e.getX() < 423)
				{
					goinstructions = true;
				}
				if (e.getX() > 422 && e.getX() < 846)
				{
					gogame = true;
					soundtimer.start();
					//AudioPlayer.player.stop(intromusic_as);
					//AudioPlayer.player.start(playmusic_as);
				}
				if (e.getX() > 845 && e.getX() < 1268)
				{
					gooptions = true;
				}
			}
			if ((e.getY() > 466 && e.getY() < 750) && (e.getX() > 422 && e.getX() < 846))
			{
				gopractice = true;
				pract.repaint();
			}
			play.repaint();
		}
		
		public void mouseEntered(MouseEvent e)
		{
		
		}
		
		public void mouseExited(MouseEvent e)
		{
		
		}
		
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
		public void mouseMoved(MouseEvent e)
		{
			////System.out.println("(" + e.getX() + "," + e.getY() + ")");
			if (e.getY() > 235 && e.getY() < 466)
			{
				if (e.getX() > 0 && e.getX() < 423)
				{
					instructionshover = true;
				}
				else
					instructionshover = false;
				if (e.getX() > 422 && e.getX() < 846)
				{
					playhover = true;
				}
				else
					playhover = false;
				if (e.getX() > 845 && e.getX() < 1268)
				{
					optionshover = true;
				}
				else
					optionshover = false;
			}
			else
			{
				instructionshover = optionshover = playhover = false;
			}
			if ((e.getY() > 466 && e.getY() < 750) && (e.getX() > 422 && e.getX() < 846))
			{
				practicehover = true;
				pract.repaint();
			}
			else
			{
				practicehover = false;
				pract.repaint();
			}
			play.repaint();
		}
		
		public void mouseDragged(MouseEvent e)
		{
		
		}
		
		public void paintComponent(Graphics g) // welcome2Panel paintcomp
		{
			super.paintComponent(g);
			g.setColor(Color.lightGray);
			////System.out.println("backcounter = " + backcounter);
			for (int x = -2000; x < 2000; x+=200)
			{
				for (int y = -1000; y < 1000; y+=200)
				{
					if (backcounter > 300)
					{
						backcounter *= -1;
					}
					g.drawRect(x-backcounter, y-backcounter, 200, 200);
				}
			}
			if (correct)
			{
				correct = false;
			}
		}
	}
	
	// top user panel where the 'Logout and exit button are 
	class TopUserPanel extends JPanel implements ActionListener
	{
		JLabel userlabel, construction;
		JButton signout;
		JCheckBox mute;
		JLabel blank = new JLabel("                                                                                                    ");
		JButton exit;
		
		public TopUserPanel()
		{
			this.setLayout(new FlowLayout());
			setBackground(Color.gray);
			userlabel = new JLabel();
			signout = new JButton("Log Out");
			signout.addActionListener(this);
			exit = new JButton("Exit");
			construction = new JLabel("Grid9 Account Under Construction!");
			construction.setFont(new Font("Monotype", Font.PLAIN, 14));
			construction.setForeground(Color.white);
			exit.addActionListener(this);
			mute = new JCheckBox("Mute");
			mute.setFont(new Font("Monotype", Font.PLAIN, 14));
			mute.setForeground(Color.white);
			this.add(userlabel);
			this.add(exit);
			this.add(construction);
			this.add(blank);
			this.add(mute);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			userlabel.setText(gameuser);
			userlabel.setFont(new Font("Monotype", Font.PLAIN, 14));
			userlabel.setForeground(Color.white);
			if (gameuser.equals("Log In!"))
			{
				this.remove(signout);
			}
			else
			{
				this.remove(exit);
				this.remove(construction);
				this.remove(mute);
				this.remove(blank);
				this.add(signout);
				this.add(exit);
				this.add(construction);
				this.add(blank);
				this.add(mute);
			}
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if (command.equals("Log Out"))
			{
				int logint = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Confirm Log Out", JOptionPane.YES_NO_OPTION);
				if (logint == JOptionPane.YES_OPTION)
				{
					logoff = true;
					frame.dispose();
					//AudioPlayer.player.stop(playmusic_as);
					//AudioPlayer.player.stop(intromusic_as);
					Game game = new Game();
					game.getImages();
					game.getSounds();
					game.getQuestions();
					game.Run();
				}
				else{}
			}
			if (command.equals("Exit"))
			{
				int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
				else
				{}
			}
		}
	}
	
	// All instructions for all games and general overview
	class InstructionsPanel extends JPanel implements ActionListener
	{
		JLabel instructionstitle;
		JTextPane instructionsarea;
		JPanel color = new JPanel();
		JComboBox type;
		JButton back;
		String [] typestring = {"General", "BucketCatcher", "KeypadRevenge", "KnockBack", "Pong", "Helicopter", "Grid 9 Account", "Credits"};
		boolean gogeneral, gobucketcatcher, gokeypadrevenge, goknockback, gopong, gohelicopter, gogrid9, gocredits;
		StyledDocument doc;
		SimpleAttributeSet subtitle = new SimpleAttributeSet();
		SimpleAttributeSet body = new SimpleAttributeSet();
			
		// Constructor
		public InstructionsPanel()
		{
			gogeneral = true;
			gobucketcatcher = gokeypadrevenge = goknockback = gopong = gohelicopter = gogrid9 = gocredits = false;
			this.setLayout(null);
			
			instructionstitle = new JLabel("Instructions");
			instructionstitle.setFont(new Font("Monotype", Font.BOLD, 65));
			instructionstitle.setForeground(Color.gray);
			Dimension size = instructionstitle.getPreferredSize();
			instructionstitle.setBounds(50, 30, size.width, size.height);
			
			instructionsarea = new JTextPane();
			
			instructionsarea.setEditable(false);
			instructionsarea.setBackground(new Color(0,0,0,0));
			instructionsarea.setBounds(80, 160, 1140, 490);
			instructionsarea.setMargin(new Insets(0, 15, 15, 15));
			
			doc = instructionsarea.getStyledDocument();
			
			subtitle.addAttribute(StyleConstants.FontConstants.FontSize, 20);
			subtitle.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.white);
			subtitle.addAttribute(StyleConstants.FontConstants.Bold, true);
			subtitle.addAttribute(StyleConstants.FontConstants.Family, "Monotype");
			
			body.addAttribute(StyleConstants.FontConstants.FontSize, 16);
			body.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.white);
			body.addAttribute(StyleConstants.FontConstants.Family, "Monotype");
									
			back = new JButton("Main Menu");
			size = back.getPreferredSize();
			back.setBounds(50, 653, size.width, size.height);	
			back.addActionListener(this);
			
			type = new JComboBox(typestring);
			type.setSelectedIndex(0);
			size = type.getPreferredSize();
			type.setBounds(60, 120, size.width, size.height);
			type.addActionListener(this);
			
			this.add(instructionstitle);
			this.add(type);
			this.add(instructionsarea);
			this.add(back);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.lightGray);
			////System.out.println("backcounter = " + backcounter);
			for (int x = -2000; x < 2000; x+=200)
			{
				for (int y = -1000; y < 1000; y+=200)
				{
					if (backcounter > 300)
					{
						backcounter *= -1;
					}
					g.drawRect(x-backcounter, y-backcounter, 200, 200);
				}
			}
			g.setColor(Color.gray);
			g.fillRect(50, 110, 1170, 540);
			if (gogeneral)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "General\n\n", subtitle);
					doc.insertString(doc.getLength(), "Grid 9 is a game revolved around the arrowkeys that is meant to enhance your overall gaming and academic capability. The game measures your ability with three basic factors: timing, memory, and intelligence. This is possible through 5 mini-games and 4 educational questions.\nThe 5 games are: \n\t-BucketCatcher\n\t-KeypadRevenge\n\t-Pong\n\t-KnockBack\n\t-Helicopter\nThe 4 question subjects are:\n\t-Math\n\t-Biology\n\t-Java\n\t-Literature\n\nIn Grid 9, the computer randomly picks either a game or question for you, and you are forced to play or answer that element for an allocated amount of time. Once that time is over, the current panel is frozen and you are returned back to Grid 9, where the computer will select another panel randomly and the process will start again.\n\nRemember, Grid 9 is fully based on the arrowkeys, so there's no need to touch the mouse in any situation, including questions.", body);
				}
				catch (Exception ex)
				{}
				g.drawImage(thegrid, 500, 270, 350, 190, null);
				//gogeneral = false;
			}
			else if (gocredits)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "Credits\n", subtitle);
					doc.insertString(doc.getLength(), "\nDeveloped by Sanketh Hegde\n\nBased on Grid16 by jmbt02.\n\nMusic:\n\t-Advance 303 by jmbt02\n\t-In Motion by Trent Reznor and Atticus Ross (The Social Network)", body);
				}
				catch (Exception ex)
				{}
				//gocredits = false;
			}
			else if (gobucketcatcher)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "BucketCatcher\n\n", subtitle);
					doc.insertString(doc.getLength(), "BucketCatcher is a game meant to test reflexes and memory. The user plays BucketCatcher by controlling a gray bucket side to side in order to catch falling eggs. As the rounds progress, the eggs spawn at a faster rate and they fall faster as well, making the game more difficult as a whole.\n\nControls:\n\t-LEFT: Move bucket left\n\t-RIGHT: Move bucket right", body);
				}
				catch (Exception e)
				{}
				g.drawImage(bucketcatcherpic, 200, 380, null);
			}
			else if (gokeypadrevenge)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "KeypadRevenge\n\n", subtitle);
					doc.insertString(doc.getLength(), "KeypadRevenge is a game meant to test reflexes and memory. The game works by making the user match oncoming tiles with arrows on their arrowkeys. If they fail to do so, they will lose that game form Grid9.\n\nControls:\n\t-LEFT: Set arrow left\t\t-UP: Set arrow up\n\t-RIGHT: Set arrow right\t\t-DOWN: Set arrow down", body);
				}
				catch (Exception e)
				{}
				g.drawImage(keypadrevengepic, 200, 380, null);
			}
			else if (goknockback)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "KnockBack\n\n", subtitle);
					doc.insertString(doc.getLength(), "KnockBack is a game meant to test reflexes in dire situations. The user plays as a triangle which is constantly shooting orbs out either right or left. The user controls the triangle up and down, and also controls which way it shoots. The goal is to prevent the red rectangles that constantly spawn on the right and left to hit the white line in the midle.\n\nControls:\n\t-LEFT: Change direction to left\t\t-UP: Move triangle up\n\t-RIGHT: Change direction to right\t\t-DOWN: Move triangle down", body);
				}
				catch (Exception e)
				{}
				g.drawImage(dodgepic, 200, 380, null);	
			}
			else if (gopong)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "Pong\n\n", subtitle);
					doc.insertString(doc.getLength(), "Pong tests the user's reflexes and memory of the ball's position. The user controls a red rectangular block which they can move left and right. They have to prevent a ball from hitting the ground as it bounces around the screen.\n\nControls:\n\t-LEFT: Move left\n\t-RIGHT: Move right", body);
				}
				catch (Exception e)
				{}
				g.drawImage(pongpic, 200, 380, null);	
			}
			else if (gohelicopter)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "Helicopter\n\n", subtitle);
					doc.insertString(doc.getLength(), "Helicopter tests the user's reflexes and memory of the helicopter and its environment. The user plays as a helicopter and is restricted to only moving up and down. The user has to prevent the helicopter from hitting any oncoming red blocks of doom. Also, the helicopter automatically moves down, so the only key the user needs to press is UP.\n\nControls:\n\t-UP: Move up\n\t-(Helicopter moves down through gravity)", body);
				}
				catch (Exception e)
				{}
				g.drawImage(helicopterpic, 200, 380, null);	
			}
			else if (gogrid9)
			{
				instructionsarea.setText("");
				try
				{
					doc.insertString(0, "Grid 9 Account\n\n", subtitle);
					doc.insertString(doc.getLength(), "Currently, the Grid 9 username and password interface is very developmental and does not do much. In the future, it may be possible to store statistics such as records and play time, but for now, it is simply a secure way of accessing Grid 9.", body);
				}
				catch (Exception e)
				{}
			}
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			String x = type.getSelectedItem().toString();
			
			if (command.equals("Main Menu"))
			{
				goinstructions = false;
				CardLayout c = (CardLayout)(cards.getLayout());
				c.show(cards, "WelcomePanel2");
			}
			
			if (x.equals("General"))
			{
				gogeneral = true;
			}
			else
				gogeneral = false;
				
			if (x.equals("Credits"))
			{
				gocredits = true;
			}
			else
				gocredits = false;
				
			if (x.equals("BucketCatcher"))
			{
				gobucketcatcher = true;
			}
			else
				gobucketcatcher = false;
				
			if (x.equals("Pong"))
			{
				gopong = true;
			}
			else
				gopong = false;
				
			if (x.equals("Helicopter"))
			{
				gohelicopter = true;
			}
			else
				gohelicopter = false;
			
			if (x.equals("KnockBack"))
			{
				goknockback = true;
			}
			else
				goknockback = false;
				
			if (x.equals("KeypadRevenge"))
			{
				gokeypadrevenge = true;
			}
			else
				gokeypadrevenge = false;
				
			if (x.equals("Grid 9 Account"))
			{
				gogrid9 = true;
			}
			else
				gogrid9 = false;
				
			repaint();
		}
	}

	// class for all options (including quality and sound (disabled))
	class OptionsPanel extends JPanel implements ActionListener, ChangeListener, ItemListener
	{
		JPanel titlepanel, contentpanel;
		JLabel optionstitle;
		JButton back;
		JSlider difficulty,  music, effect;
		JLabel usergame;
		JButton stats;
		JCheckBox inverted;
	
		// Constructor
		public OptionsPanel()
		{
			this.setLayout(null);
			titlepanel = new JPanel();
			optionstitle = new JLabel("Options");
			optionstitle.setFont(new Font("Monotype", Font.BOLD, 65));
			optionstitle.setForeground(Color.gray);
			Dimension size = optionstitle.getPreferredSize();
			optionstitle.setBounds(50, 30, size.width, size.height);
			
			back = new JButton("Main Menu");
			size = back.getPreferredSize();
			back.setBounds(50, 653, size.width, size.height);	
			back.addActionListener(this);
			
			contentpanel = new JPanel();
			contentpanel.setLayout(new GridLayout(3, 4));
			contentpanel.setBackground(new Color(0, 0, 0, 0));
			contentpanel.setBounds(90, 150, 1090, 460);
			
			Font subtitle = new Font("Monotype", Font.BOLD, 20);
			Font compbody = new Font("Monotype", Font.PLAIN, 17);
			
			difficulty = new JSlider(JSlider.HORIZONTAL, 0, 2, 2);
			difficulty.addChangeListener(this);
			difficulty.setForeground(Color.white);
			difficulty.setBackground(new Color(0,0,0,0));
			Hashtable labelTable = new Hashtable();
			JLabel easy = new JLabel("Low");
			easy.setForeground(Color.white);
			easy.setFont(compbody);
			JLabel normal = new JLabel("Medium");
			normal.setForeground(Color.white);
			normal.setFont(compbody);
			JLabel hard = new JLabel("High");
			hard.setForeground(Color.white);
			hard.setFont(compbody);
			labelTable.put( new Integer( 0 ), easy);
			labelTable.put( new Integer( 1 ), normal);
			labelTable.put( new Integer( 2 ), hard);
			difficulty.setLabelTable( labelTable );
			difficulty.setMajorTickSpacing(1);
			difficulty.setPaintTicks(false);
			difficulty.setPaintLabels(true);
			difficulty.setSnapToTicks(true);
			
			music = new JSlider(0,48, 48);
			music.setMajorTickSpacing(3);
			music.setSnapToTicks(true);
			music.setBackground(new Color(0,0,0,0));
			effect = new JSlider(0, 48, 48);
			effect.setMajorTickSpacing(3);
			effect.setSnapToTicks(true);
			effect.setBackground(new Color(0,0,0,0));
			
			JLabel startinglevel = new JLabel("       Quality:");
			startinglevel.setFont(subtitle);
			startinglevel.setForeground(Color.white);
			contentpanel.add(startinglevel);
			contentpanel.add(difficulty);
			
			JLabel musiclevel = new JLabel("       Music Volume:");
			musiclevel.setFont(subtitle);
			musiclevel.setForeground(Color.white);
			contentpanel.add(musiclevel);
			contentpanel.add(music);
			
			JLabel accountlabel = new JLabel("       Grid9 Account:");
			accountlabel.setFont(subtitle);
			accountlabel.setForeground(Color.white);
			contentpanel.add(accountlabel);
			JPanel acc = new JPanel(new GridLayout(0,2));
			usergame = new JLabel(gameuser);
			JLabel otratext = new JLabel("Signed in as");
			otratext.setForeground(Color.white);
			usergame.setForeground(Color.white);
			usergame.setFont(new Font("Monotype", Font.BOLD, 17));
			otratext.setFont(new Font("Monotype", Font.PLAIN, 17));
			stats = new JButton("Stats");
			acc.add(otratext);
			acc.add(usergame);
			acc.setBackground(new Color(0,0,0,0));
			contentpanel.add(acc);
			
			JLabel soundlevel = new JLabel("       Effect Volume:");
			soundlevel.setFont(subtitle);
			soundlevel.setForeground(Color.white);
			contentpanel.add(soundlevel);
			contentpanel.add(effect);

			JLabel colorlabel = new JLabel("       Introduction:");
			colorlabel.setFont(subtitle);
			colorlabel.setForeground(Color.white);
			contentpanel.add(colorlabel);
			inverted = new JCheckBox("Skip");
			inverted.setBackground(new Color(0,0,0,0));
			inverted.setForeground(Color.white);
			inverted.setFont(compbody);
			inverted.addItemListener(this);
			contentpanel.add(inverted);
			
			JLabel practicelabel = new JLabel("       Practice Round:");
			practicelabel.setFont(subtitle);
			practicelabel.setForeground(Color.white);
			//contentpanel.add(practicelabel);
			//contentpanel.add(new JLabel(""));
			
			this.add(optionstitle);
			this.add(contentpanel);
			this.add(back);
			
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			usergame.setText(gameuser);
			g.setColor(Color.lightGray);
			////System.out.println("backcounter = " + backcounter);
			for (int x = -2000; x < 2000; x+=200)
			{
				for (int y = -1000; y < 1000; y+=200)
				{
					if (backcounter > 300)
					{
						backcounter *= -1;
					}
					g.drawRect(x-backcounter, y-backcounter, 200, 200);
				}
			}
			g.setColor(Color.gray);
			g.fillRect(50, 110, 1170, 540);
			g.setColor(Color.white);
			g.drawImage(volumemute, 890, 216, null);
			g.drawImage(volumefull, 1170, 216, null);
			g.drawImage(volumemute, 890, 368, null);
			g.drawImage(volumefull, 1170, 368, null);
		}
		
		// Event used for if a checkbox is checked
		public void itemStateChanged(ItemEvent i)
		{
			Object source = i.getItemSelectable();
			
			if (source == inverted)
			{
				//System.out.println("Skip Intro!");
				skipintro = true;
			}
			
			if (i.getStateChange() == ItemEvent.DESELECTED)
			{
				//System.out.println("Do Not Skip Intro!");
				skipintro = false;
			}
		}
		
		// used for if JButton is pressed to go back to main menu
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			
			if (command.equals("Main Menu"))
			{
				gooptions = false;
				CardLayout c = (CardLayout)(cards.getLayout());
				c.show(cards, "WelcomePanel2");
			}
		}
		
		// used when the JSlider is changed from low to medium to high quality.
		public void stateChanged(ChangeEvent e)
		{
			int x = difficulty.getValue();
			//System.out.println("quality = " + quality);
			quality = x;
		}
	}
	
	///
	//
	///           GAME
	//
	///           PANEL
	//
	///
	
	/// Where all the games and questions take place (about 4000 lines)
	class GamePanel extends JPanel 
	{
		boolean buckettime, helitime, pongtime, keytime, dodgetime;
		boolean bucketdone, helidone, pongdone, keydone, dodgedone, mathdone, litdone, biodone, javadone;
		int clockbucket, clockheli, clockkey, clockpong, clockdodge, clockmath, clocklit, clockjava, clockbio;
		GridPanel grid;
		BucketPanel bucket;
		HeliPanel heli;
		PongPanel pong;
		KeyPanel key;
		DodgePanel dodge;
		MathPanel mathpanel;
		LitPanel litpanel;
		BioPanel biopanel;
		JavaPanel javapanel;
		TimePanel timepanel;
		LostPanel lostpanel;
		int opcounter = 0;
		boolean losegame;
		int intelligence, memory, timing;
		
		public GamePanel()
		{
			intelligence = timing = memory = 5;
			losegame = false;
			playheli = playpong = playdodge = playbucket = playkey = playmath = playbio = playlit = playjava = true;
			//
			//playmath = playbio = playlit = playjava = true;
			//
			mathcounter = biocounter = litcounter = javacounter = -1;
			diff_lit = diff_math = diff_pong = diff_java = diff_bucket = diff_key = diff_heli = diff_pong = diff_dodge = 0;
			goinstructions = gooptions = gogame = gopractice = false;
			losegame = false;
			fakegridtimerstart = true;
			paintpaint = true;
			backcounter = 0;
			logoff = false;
			playplay = false;
			clockbucket = clockheli = clockkey = clockpong = clockdodge = clockmath = clocklit = clockjava = clockbio = 1270;
			buckettime = helitime = pongtime = keytime = dodgetime = true;
			bucketdone = helidone = pongdone = keydone = dodgedone = mathdone = litdone = biodone = javadone = false;
			this.setLayout(new CardLayout());
			grid = new GridPanel();
			// All of these are commented out because they are later inited
			// when the program selects a panel
			//lostpanel = new LostPanel();
			//bucket = new BucketPanel();
			//heli = new HeliPanel();
			//pong = new PongPanel();
			//key = new KeyPanel();
			//dodge = new DodgePanel();
			//mathpanel = new MathPanel();
			//biopanel = new BioPanel();
			//litpanel = new LitPanel();
			//javapanel = new JavaPanel();
			timepanel = new TimePanel();
			this.add(grid, "GridPanel");
			//this.add(timepanel, "TimePanel");
			//this.add(bucket, "BucketPanel");
			//this.add(heli, "HeliPanel");
			//this.add(pong, "PongPanel");
			//this.add(key, "KeyPanel");
			//this.add(dodge, "DodgePanel");
			//this.add(mathpanel, "MathPanel");  
			//this.add(biopanel, "BioPanel");
			//this.add(litpanel, "LitPanel");
			//this.add(javapanel, "JavaPanel");
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
		}
		
		class GridPanel extends JPanel
		{
			JPanel bucketimage, heliimage, pongimage, keyimage, dodgeimage;
			JPanel mathimage, scienceimage, litimage, javaimage;
			FakeGrid fakegrid;
			JPanel color = new JPanel();
			TutorialGrid tutorial;
			JPanel black;
		
			public GridPanel()
			{
				this.setLayout(null);
				
				fakegrid = new FakeGrid();
				fakegrid.repaint();
				tutorial = new TutorialGrid(); 
				tutorial.repaint();
				black = new JPanel();
				black.setBackground(new Color(0,0,0));
				black.setBounds(0,0,1270, 689);
				tutorial.requestFocusInWindow();
				this.add(tutorial);
				this.add(black);
				this.add(fakegrid);
				tutorial.requestFocusInWindow();
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
			}	
			
			// Tutorial Grid is the fading in and introduction of Grid 9
			class TutorialGrid extends JPanel
			{
				JLabel handson, panic, logic, buffer;
				boolean hand, pan, log, buff, tutpic, first, second;
				boolean play;
				int opcounter;
			
				public TutorialGrid()
				{
				
					play = false;
					opcounter = 0;
					buff = true;
					pan = log = hand = false;
					tutpic = false;
					first = true;second = false;


/*
					buff = false;
					pan = log = hand = false;
					tutpic = false;
					play = true;
*/

				
					this.setBounds(0, 0, 1270, 689);
					this.setBackground(new Color(0,0,0,0));
					this.setLayout(null);
					
					Font labelfont = new Font("Monotype", Font.PLAIN, 36);
					
					buffer = new JLabel("");
					handson = new JLabel("Hands on the arrowkeys.");
					logic = new JLabel("Use your gaming logic.");
					panic = new JLabel("Do not panic.");
					
					handson.setFont(labelfont);
					handson.setForeground(Color.black);
					Dimension size = handson.getPreferredSize();
					handson.setBounds(420, 200, size.width, size.height);
					
					logic.setFont(labelfont);
					logic.setForeground(Color.black);
					size = logic.getPreferredSize();
					logic.setBounds(437, 300, size.width, size.height);
					
					panic.setFont(labelfont);
					panic.setForeground(Color.black);
					size = panic.getPreferredSize();
					panic.setBounds(520, 400, size.width, size.height);
					
					//this.add(handson);
					//this.add(logic);
					//this.add(panic);
					fakegrid.setVisible(false);
					
					Mover mover = new Mover();
					Timer faketimer = new Timer(1, mover);
					faketimer.start();
				}
				
				public void paintComponent(Graphics g)
				{	
					super.paintComponent(g);
					Dimension framesize = frame.getSize();
					//black.setVisible(false);
					this.setBounds(0,0,framesize.width, framesize.height-61);
					
					if (buff)
					{
						//this.add(buffer);
						if (opcounter <= 255)
						{
							buffer.setForeground(new Color(250, 250, 250, 0+opcounter));
						}
						else 
						{
							opcounter = 0;
							buff = false;
							hand = true;
						}
					}
					if (!skipintro)
					{
						if (hand)
						{
							this.add(handson);
							if (opcounter <= 255)
							{
								handson.setForeground(new Color(250, 250, 250, 0+opcounter));
							}
							else 
							{
								opcounter = 0;
								hand = false;
								log = true;
							}
						}
						if (log)
						{
							this.add(logic);
							if (opcounter <= 255)
							{
								logic.setForeground(new Color(250, 250, 250, 0+opcounter));
							}
							else 
							{
								opcounter = 0;
								log = false;
								pan = true;
							}
						}
						if (pan)
						{
							this.add(panic);
							if (opcounter <= 255)
							{
								panic.setForeground(new Color(250, 250, 250, 0+opcounter));
							}
							else 
							{
								opcounter = 0;
								pan = false;
								first = false;
								second = true;
								try
								{
									//Thread.sleep(2000);
								}
								catch(Exception e)
								{
									//System.out.println("thread not slept");
								}
								//System.out.println("opcounter = " + opcounter);
								black.setVisible(false);
								handson.setVisible(false);
								logic.setVisible(false);
								panic.setVisible(false);
								tutpic = true;
							}
						}
						if (tutpic)
						{
							if (opcounter <= 100)
							{
								super.paintComponent(g);
								g.drawImage(thegrid, 0, 0, null);
								Graphics2D g2d = (Graphics2D)g.create();
								float alpha = 1f-(.01f*(float)opcounter);
								g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
								g2d.drawImage(tut, 0, 0, null);
								g2d.dispose();
							}
							else
							{
								opcounter = 0;
								tutpic = false;
								tutorial.setVisible(false);
								fakegrid.setVisible(true);
								play = true;
							}
						}
						if (play)
						{
							playplay = true;
							//fakegrid.setVisible(true);
						}
					}
					else
					{	
						opcounter = 0;
						tutpic = false;
						tutorial.setVisible(false);
						fakegrid.setVisible(true);
						playplay = true;
					}
					//g.setFont(new Font("Monotype", Font.PLAIN, 16));
					//g.setColor(Color.lightGray);
					//g.drawString("Any key to skip", 10, 30);
				}
				
				class Mover implements ActionListener
				{
					public void actionPerformed(ActionEvent e)
					{
						if (!tutpic)
							opcounter+=2;
						else
						{
							opcounter+=2;
						}
						repaint();
					}	 
				}
			}
			
			// FakeGrid basically is the intro of the text and fading
			// The user can skip this intro in the optionspanel
			class FakeGrid extends JPanel
			{
				Mover mover = new Mover();
				Timer timer = new Timer(1, mover);
				int randpanel = 0;
				
				public FakeGrid()
				{
					paintpaint = true;
					
					bucketimage = new JPanel(new BorderLayout());
					heliimage = new JPanel(new BorderLayout());
					pongimage = new JPanel(new BorderLayout());
					keyimage = new JPanel(new BorderLayout());
					dodgeimage = new JPanel(new BorderLayout());
					mathimage = new JPanel(new BorderLayout());
					scienceimage = new JPanel(new BorderLayout());
					litimage = new JPanel(new BorderLayout());
					javaimage = new JPanel(new BorderLayout());
					
					JLabel mathpic = new JLabel(new ImageIcon(math));
					mathimage.add(mathpic, BorderLayout.CENTER);
					
					JLabel sciencepic = new JLabel(new ImageIcon(science));
					scienceimage.add(sciencepic, BorderLayout.CENTER);
					
					JLabel litpic = new JLabel(new ImageIcon(lit));
					litimage.add(litpic, BorderLayout.CENTER);
					
					JLabel javapic = new JLabel(new ImageIcon(java));
					javaimage.add(javapic, BorderLayout.CENTER);
					
					JLabel bucketcatcherimg = new JLabel(new ImageIcon(bucketcatcherpic));
					JLabel pongimg = new JLabel(new ImageIcon(pongpic));
					JLabel keypadrevengeimg = new JLabel(new ImageIcon(keypadrevengepic));
					JLabel helicopterimg = new JLabel(new ImageIcon(helicopterpic));
					JLabel dodgeimg = new JLabel(new ImageIcon(dodgepic));
					
					Color transgray = new Color(128, 128, 128, 110);
					Color clear = new Color(0,0,0,0);
					mathimage.setBackground(transgray);
					bucketimage.setBackground(clear);
					heliimage.setBackground(clear);
					pongimage.setBackground(clear);
					keyimage.setBackground(clear);
					dodgeimage.setBackground(clear);
					scienceimage.setBackground(transgray);
					litimage.setBackground(transgray);
					javaimage.setBackground(transgray);
					
					
					
					this.setLayout(new GridLayout(3, 3, 10, 10));
					this.add(mathimage);
					this.add(bucketimage);
					this.add(scienceimage);
					this.add(heliimage);
					this.add(pongimage);
					this.add(keyimage);
					this.add(litimage);
					this.add(dodgeimage);
					this.add(javaimage);
			
					this.setBounds(0,0,1270,689);
				}
				
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					setBackground(color.getBackground());
					g.setColor(new Color(128, 128, 128, 110));
					g.fillRect(426, 0, 416, 223);
					g.fillRect(0, 233, 416, 223);
					g.fillRect(426, 233, 416, 223);
					g.fillRect(852, 233, 416, 233);
					g.fillRect(426, 466, 416, 233);
					Dimension size = frame.getSize();
					g.drawImage(arrowpad, 0, 0, size.width, size.height-61, null);
					g.drawImage(bucketcatcherpic, 426, 0, 416, 223, null);
					g.drawImage(helicopterpic, 0, 233, 416, 223, null);
					g.drawImage(dodgepic, 426, 233, 416, 223, null);
					g.drawImage(pongpic, 852, 233, 416, 223, null);
					g.drawImage(keypadrevengepic, 426, 466, 416, 223, null);
					this.setBounds(0, 0, size.width, size.height-61);
					////System.out.println("Fakegrid");
					if (fakegridtimerstart)
					{
						timer.start();
					}
					if (playplay)
					{
						CardLayout c = (CardLayout)(game.getLayout());
						g.setFont(new Font("Monotype", Font.BOLD, 36));
						if (paintpaint)
						{
							randpanel = (int)(Math.random()*9);
							if (!playdodge)
							{
								while(randpanel == 0)
									randpanel = (int)(Math.random()*9);
							}
							if (!playbucket)
							{
								while(randpanel == 1)
									randpanel = (int)(Math.random()*9);
							}
							if (!playkey)
							{
								while(randpanel == 2)
									randpanel = (int)(Math.random()*9);
							}
							if (!playheli)
							{
								while (randpanel == 3)
									randpanel = (int)(Math.random()*9);
							}
							if (!playpong)
							{
								while (randpanel == 4)
									randpanel = (int)(Math.random()*9);
							}
							if (!playlit)
							{
								while(randpanel == 5)
									randpanel = (int)(Math.random()*9);
							}
							if (!playbio)
							{
								while (randpanel == 6)
									randpanel = (int)(Math.random()*9);
							}
							if (!playjava)
							{
								while (randpanel == 7)
									randpanel = (int)(Math.random()*9);
							}
							if (!playmath)
							{
								while (randpanel == 8)
									randpanel = (int)(Math.random()*9);
							}
						}
						////System.out.println("randpanel = " + randpanel);
						//randpanel = 8;		
						g.setColor(Color.white);
						if (randpanel == 0 && playdodge)
						{
							paintpaint = false;
							////System.out.println("ranpanel ==1200");
							if (!dodgedone)
							{
								dodge = new DodgePanel();
								game.add(dodge, "DodgePanel");
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(426, 233, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(426, 233, 416, 223);
								//g.drawString("Dodge", 500, 125);
							}
							else
							{
								diff_dodge++;
								timer.stop();
								fakegridtimerstart = false;
								c.show(game, "DodgePanel");
								dodge.requestFocusInWindow();							
							}
						}
						else if (randpanel == 1 && playbucket)
						{
							paintpaint = false;
							if (!bucketdone)
							{
								bucket = new BucketPanel();
								game.add(bucket, "BucketPanel");
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(426, 0, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(426, 0, 416, 223);
								//g.drawString("BucketCatcher", 500, 125);
							}
							else
							{
								diff_bucket++;
								timer.stop();
								fakegridtimerstart = false;
								c.show(game, "BucketPanel");
								bucket.requestFocusInWindow();							
							}
						}
						else if (randpanel == 2 && playkey)
						{
							paintpaint = false;
							if (!keydone)
							{
								key = new KeyPanel();
								game.add(key, "KeyPanel");
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(426, 466, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(426, 466, 416, 223);
								//g.drawString("Key", 500, 125);
							}
							else
							{
								diff_key++;
								timer.stop();
								fakegridtimerstart = false;
								////System.out.println("gokey");
								c.show(game, "KeyPanel");
								key.requestFocusInWindow();							
							}
						}
						else if (randpanel == 3 && playheli)
						{
							paintpaint = false;
							if (!helidone)
							{
								heli = new HeliPanel();
								game.add(heli, "HeliPanel");
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(0, 233, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(0, 233, 416, 223);
								//g.drawString("BucketCatcher", 500, 125);
							}
							else
							{
								diff_heli++;
								timer.stop();
								fakegridtimerstart = false;
								c.show(game, "HeliPanel");
								heli.requestFocusInWindow();							
							}
						}
						else if (randpanel == 4 && playpong)
						{
							paintpaint = false;
							if (!pongdone)
							{
								pong = new PongPanel();
								game.add(pong, "PongPanel");
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(852, 233, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(852, 233, 416, 223);
								//g.drawString("BucketCatcher", 500, 125);
							}
							else
							{
								diff_pong++;
								timer.stop();
								fakegridtimerstart = false;
								c.show(game, "PongPanel");
								pong.requestFocusInWindow();							
							}
						}
						else if (randpanel == 5 && playlit)
						{
							//System.out.println("litcounter = " + litcounter);
							//paintpaint = false;
							if (paintpaint)
								litcounter++;
							paintpaint = false;
							if (litcounter == litq.length)
							{
								litcounter = 0;
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(0, 466, 416, 233);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(0, 466, 416, 233);
								//g.drawString("Literature", 500, 125);
							}
							else
							{
								diff_lit++;
								timer.stop();
								fakegridtimerstart = false;
								litpanel = new LitPanel();
								game.add(litpanel, "LitPanel");
								c.show(game, "LitPanel");
								//paintpaint = true;
								litpanel.requestFocusInWindow();
							}
						}
						else if (randpanel == 6 && playbio)
						{
							//System.out.println("biocounter = " + biocounter);
							//paintpaint = false;
							if (paintpaint)
								biocounter++;
							paintpaint = false;
							if (biocounter == bioq.length)
							{
								biocounter = 0;
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(852, 0, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(852, 0, 416, 223);
								//g.drawString("Biology", 500, 125);
							}
							else
							{
								diff_bio++;
								timer.stop();
								fakegridtimerstart = false;
								biopanel = new BioPanel();
								game.add(biopanel, "BioPanel");
								c.show(game, "BioPanel");
								//paintpaint = true;
								biopanel.requestFocusInWindow();
							}
						}
						else if (randpanel == 7 && playjava)
						{	
							//System.out.println("javacounter = " + javacounter);
							//paintpaint = false;
							if (paintpaint)
								javacounter++;
							paintpaint = false;
							if (javacounter == javaq.length)
							{
								javacounter = 0;
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(852, 466, 416, 233);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(852, 466, 416, 233);
								//g.drawString("Biology", 500, 125);
							}
							else
							{
								diff_java++;
								timer.stop();
								fakegridtimerstart = false;
								javapanel = new JavaPanel();
								game.add(javapanel, "JavaPanel");
								c.show(game, "JavaPanel");
								//paintpaint = true;
								javapanel.requestFocusInWindow();
							}
						}
						else if (randpanel == 8 && playmath) 
						{
							//System.out.println("mathcounter = " + mathcounter);
							//paintpaint = false;
							if (paintpaint)
								mathcounter++;
							paintpaint = false;
							if (mathcounter == mathq.length)
							{
								mathcounter = 0;
							}
							if (opcounter <= 180)
							{
								g.setColor(new Color(124, 252, 0, opcounter));
								g.fillRect(0, 0, 416, 223);
								g.setColor(new Color(0, 0, 0, opcounter));
								g.drawRect(0, 0, 416, 223);
								//g.drawString("Biology", 500, 125);
							}
							else
							{
								diff_math++;
								timer.stop();
								fakegridtimerstart = false;
								mathpanel = new MathPanel();
								game.add(mathpanel, "MathPanel");
								c.show(game, "MathPanel");
								//paintpaint = true;
								mathpanel.requestFocusInWindow();
							}
							
							//math is 8, java is 7, bio is 6, it is 5
						}
						
						///
						///
						if (!playpong)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(852, 233, 416, 223);
						}
						if (!playheli)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(0, 233, 416, 223);	
						}
						if (!playdodge)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(426, 233, 416, 223);					
						}
						if (!playkey)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(426, 466, 416, 223);	
						}
						if (!playbucket)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(426, 0, 416, 223);		
						}
						if (!playlit)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(0, 466, 416, 233);
							g.setColor(Color.white);
							//g.drawString("Literature Lost", 100, 260);
						}
						if (!playmath)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(0, 0, 416, 223);
							g.setColor(Color.white);
							//g.drawString("Math Lost", 100, 260);
						}
						if (!playbio)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(852, 0, 416, 223);
							g.setColor(Color.white);
							//g.drawString("Biology Lost", 100, 260);
						}
						if (!playjava)
						{
							g.setColor(new Color(0, 0, 0, 210));
							g.fillRect(852, 466, 416, 233);
							g.setColor(Color.white);
							//g.drawString("Java Lost", 100, 260);			
						}
						if (!playpong && !playheli && !playdodge && !playkey && !playbucket && !playlit && !playmath && !playbio && !playjava)
						{
							losegame = true;
							lostpanel = new LostPanel();
							game.add(lostpanel, "LostPanel");
							lostpanel.requestFocusInWindow();
							c.show(game, "LostPanel");
							timer.stop();
							fakegridtimerstart = false;
						}
						
						//c.show(game, "TimePanel");
					}
				}
				
				class Mover implements ActionListener
				{
					public void actionPerformed(ActionEvent e)
					{
						opcounter+=3;
						repaint();						
					}
				}
			}
		}
		
		// If the user loses, the LostPanel will show up
		// with detailed stats and retry/menu buttons. Stats
		// button does not work yet.
		class LostPanel extends JPanel implements MouseMotionListener, MouseListener
		{
			Mover mover = new Mover();
			Timer timer = new Timer(1, mover);
			int xcounter = -200;
			int repaintcounter = 0;
			boolean aftertitle = false;
			JLabel retry, menu, stats;
			Color clear = new Color(0,0,0,0);
			Font label = new Font("Monotype", Font.BOLD, 46);
			Font statlabel = new Font("Monotype", Font.PLAIN, 20);
			JLabel bucketround, heliround, ponground, dodgeround, keyround, avground;
			boolean retryhover, menuhover, statshover;
			int [] origx = {850, 600, 1100};
			int [] origy = {240, 620, 620};
			int [] scorex = {850, 600+2*(100-timing), 1100-2*(100-memory)};
			int [] scorey = {240+2*(100-intelligence), 620-(100-timing), 620-(100-memory)};
		
			// Constructor
			public LostPanel()
			{
				this.addMouseMotionListener(this);
				this.addMouseListener(this);
				retryhover = menuhover = statshover = false;
				setLayout(null);
				retry = new JLabel("Retry");
				menu = new JLabel("Menu");
				stats = new JLabel("Stats");
				
				retry.setForeground(Color.lightGray);
				retry.setBackground(clear);
				retry.setFont(label);
				retry.setVisible(false);
				retry.setBounds(260, 120, 130, 75);
				
				menu.setForeground(Color.lightGray);
				menu.setBackground(clear);
				menu.setFont(label);
				menu.setVisible(false);
				menu.setBounds(560, 120, 137, 75);
				
				stats.setForeground(Color.lightGray);
				stats.setBackground(clear);
				stats.setFont(label);
				stats.setVisible(false);
				stats.setBounds(860, 120, 120, 75);
				
				bucketround = new JLabel();
				heliround = new JLabel();
				ponground = new JLabel();
				dodgeround = new JLabel();
				keyround = new JLabel();
				avground = new JLabel();
				
				bucketround.setForeground(Color.white);
				bucketround.setFont(statlabel);
				bucketround.setBackground(clear);
				bucketround.setVisible(false);
				bucketround.setBounds(100, 190, 500, 100);
				
				heliround.setForeground(Color.white);
				heliround.setFont(statlabel);
				heliround.setBackground(clear);
				heliround.setVisible(false);
				heliround.setBounds(100, 250, 500, 100);
				
				ponground.setForeground(Color.white);
				ponground.setFont(statlabel);
				ponground.setBackground(clear);
				ponground.setVisible(false);
				ponground.setBounds(100, 310, 500, 100);
				
				dodgeround.setForeground(Color.white);
				dodgeround.setFont(statlabel);
				dodgeround.setBackground(clear);
				dodgeround.setVisible(false);
				dodgeround.setBounds(100, 370, 500, 100);
				
				keyround.setForeground(Color.white);
				keyround.setFont(statlabel);
				keyround.setBackground(clear);
				keyround.setVisible(false);
				keyround.setBounds(100, 430, 500, 100);
				
				avground.setForeground(Color.white);
				avground.setFont(statlabel);
				avground.setBackground(clear);
				avground.setVisible(false);
				avground.setBounds(100, 490, 500, 100);
				
				keyround.setText("KeypadRevenge Highest Round: " + diff_key);
				bucketround.setText("BucketCatcher Highest Round: " + diff_bucket);
				heliround.setText("Helicopter Highest Round: " + diff_heli);
				ponground.setText("Pong Highest Round: " + diff_pong);
				dodgeround.setText("KnockBack Highest Round: " + diff_dodge);
				int avg = (diff_key+diff_bucket+diff_heli+diff_pong+diff_dodge)/5;
				avground.setText("Average Highest Round: " + avg);
				
				setBackground(new Color(0,0,0,10));
				timer.start();
				
				this.add(retry);
				this.add(menu);
				this.add(stats);
				this.add(keyround);
				this.add(dodgeround);
				this.add(ponground);
				this.add(heliround);
				this.add(bucketround);
				this.add(avground);
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.white);
				g.setFont(new Font("Monotype", Font.BOLD, 72));
				g.drawString("Grid 9 Lost.", xcounter, 100);
				if (aftertitle)
				{
					// after the grid 9 fades in, show all this stuff
					retry.setVisible(true);
					menu.setVisible(true);
					stats.setVisible(true);
					keyround.setVisible(true);
					dodgeround.setVisible(true);
					ponground.setVisible(true);
					heliround.setVisible(true);
					bucketround.setVisible(true);
					avground.setVisible(true);
				}
				
				if (retryhover)
				{
					retry.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.lightGray));
				}
				else if (!retryhover)
				{
					retry.setBorder(BorderFactory.createEmptyBorder());
				}
				if (menuhover)
				{
					menu.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.lightGray));
				}
				else if (!menuhover)
				{
					menu.setBorder(BorderFactory.createEmptyBorder());
				}
				if (statshover)
				{
					g.drawImage(constructionimage, 990, 110, null);
				}
				else if (!statshover)
				{

				}
				
				if (aftertitle)
				{
				g.setColor(Color.lightGray);
				g.fillPolygon(origx, origy, 3);
				
				Font gradefont = new Font("Monotype", Font.BOLD, 144);
				int finalscore = (intelligence + timing + memory)/3;
				//finalscore = 85;
				// Gives the user a final score/grade.
				if (finalscore >= 0 && finalscore < 60)
				{
					g.setFont(gradefont);
					g.setColor(Color.red);
					g.drawString("F", 490, 435);
					g.setColor(Color.red);
				}
				else if (finalscore >= 60 && finalscore < 70)
				{
					g.setFont(gradefont);
					g.setColor(Color.red);
					g.drawString("D", 490, 435);	
					g.setColor(Color.red);
				}
				else if (finalscore >= 70 && finalscore < 80)
				{
					g.setFont(gradefont);
					g.setColor(Color.yellow);
					g.drawString("C", 490, 435);	
					g.setColor(Color.yellow);		
				}
				else if (finalscore >= 80 && finalscore < 90)
				{
					g.setFont(gradefont);
					g.setColor(Color.cyan);
					g.drawString("B", 490, 435);	
					g.setColor(Color.cyan);				
				}
				else if (finalscore >= 90)
				{
					g.setFont(gradefont);
					g.setColor(Color.green);
					g.drawString("A", 490, 435);	
					g.setColor(Color.green);		
				}
				g.fillPolygon(scorex, scorey, 3);
				
				g.setFont(statlabel);
				g.setColor(Color.white);
				g.drawString("Intelligence", 802, 230);
				g.drawString("Timing", 526, 630);
				g.drawString("Memory", 1110, 630);
				
				g.setColor(Color.black);
				g.drawString(intelligence + " %", 830, 300);
				g.drawString(timing + " %", 645, 600);
				g.drawString(memory + " %", 1025, 600);
				
				g.setFont(new Font("Monotype", Font.BOLD, 30));
				g.setColor(Color.black);
				g.drawString(finalscore + " %", 820, 500);
				}
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if (xcounter <= 400)
						xcounter+=5;
					else
					{
						aftertitle = true;
					}
					repaint();
				}
			}
			
			public void mouseEntered(MouseEvent e)
			{}
			
			public void mouseExited(MouseEvent e)
			{}
			
			public void mouseClicked(MouseEvent e)
			{}
			
			// Allows user to retry or go to menu while staying logged in
			public void mousePressed(MouseEvent e)
			{
				if (e.getX() >= 260 && e.getX() <= 390)
				{
					if (e.getY() >= 120 && e.getY() <= 195)
					{
						try
						{
							outFile = new PrintWriter(new File("currentuser.txt"));
						}
						catch (Exception ex)
						{
							//System.out.println("Cannot Find currentuser.txt");
						}
						outFile.println(gameuser);
						outFile.close();
						frame.dispose();
						//AudioPlayer.player.stop(playmusic_as);
						//AudioPlayer.player.stop(intromusic_as);
						Game game = new Game();
						game.getImages();
						game.getSounds();
						game.getQuestions();
						try
						{
							inFile = new Scanner(new File("currentuser.txt"));
						}
						catch (Exception ex)
						{
							//System.out.println("Cannot Find currentuser.txt");	
						}
						while (inFile.hasNext())
						{
							menuuser = inFile.nextLine();
							//System.out.println("menuuser = " + menuuser);
						}
						inFile.close();
						gameuser = menuuser;
						//System.out.println("gameuser = " + gameuser);
						game.RunAgain(gameuser, true);
						secondtime = true;
					}
				}
				if (e.getX() >= 560 && e.getX() <= 697) //menu
				{
					if (e.getY() >= 120 && e.getY() <= 195)
					{

						try
						{
							outFile = new PrintWriter(new File("currentuser.txt"));
						}
						catch (Exception ex)
						{
							//System.out.println("Cannot Find currentuser.txt");
						}
						outFile.println(gameuser);
						outFile.close();
						frame.dispose();
						//AudioPlayer.player.stop(playmusic_as);
						//AudioPlayer.player.stop(intromusic_as);
						Game game = new Game();
						game.getImages();
						//game.getSounds();
						game.getQuestions();
						try
						{
							inFile = new Scanner(new File("currentuser.txt"));
						}
						catch (Exception ex)
						{
							//System.out.println("Cannot Find currentuser.txt");	
						}
						while (inFile.hasNext())
						{
							menuuser = inFile.nextLine();
							//System.out.println("menuuser = " + menuuser);
						}
						inFile.close();
						gameuser = menuuser;
						//System.out.println("gameuser = " + gameuser);
						game.RunAgain(gameuser, false);
						secondtime = true;
					}
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{}
			
			public void mouseDragged(MouseEvent e)
			{}
			
			// Hovering effect of underline for each button
			public void mouseMoved(MouseEvent e)
			{
				if (e.getX() >= 260 && e.getX() <= 390)
				{
					if (e.getY() >= 120 && e.getY() <= 195)
					{
						retryhover = true;
					}
					else
						retryhover = false;
				}
				else
					retryhover = false;
				if (e.getX() >= 560 && e.getX() <= 697)
				{
					if (e.getY() >= 120 && e.getY() <= 195)
					{
						menuhover = true;
					}
					else
						menuhover = false;
				}
				else
					menuhover = false;
				if (e.getX() >= 860 && e.getX() <= 980)
				{
					if (e.getY() >= 120 && e.getY() <= 195)
					{
						statshover = true;
					}
					else
						statshover = false;
				}
				else
					statshover = false;
				repaint();
			}
			
			
		}
		
		// Not used at all
		class TimePanel extends JPanel
		{
			public TimePanel()
			{
				setBackground(new Color(0,0,0,0));
			}
			
			public void paintComponent(Graphics g)
			{
				g.setColor(Color.green);
				g.fillRect(0, 0, 300, 300);
			}
		}
		
		// BucketCatcher game
		class BucketPanel extends JPanel
		{
			boolean right, left;
			int poscounter = 20;
			int bucketpos = 550;
			int [] eggy = new int[10000];
			ArrayList<Integer> eggpos = new ArrayList<Integer>();
			int eggcount = 0;
			int timecount = 0;
			Mover2 mover2 = new Mover2();
			Timer blocktimer = new Timer(1, mover2);
			Mover3 mover3 = new Mover3();
			Timer timetimer = new Timer(1, mover3);
			boolean west, east;
			boolean done;
			boolean first;
			Action rightAction, leftAction, stillAction;
			int modtemp = 260;
			
			public BucketPanel()
			{
				first = true;
				done = false;
				west = east = false;
				this.setLayout(null);
				right = left = false;
				leftAction = new LeftAction();
				rightAction = new RightAction();
				stillAction = new StillAction();
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "doStillAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "doStillAction");
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
				this.getActionMap().put("doStillAction", stillAction);
			}
			
			public void paintComponent(Graphics g)
			{
				if (buckettime)
				{
					timetimer.start();
					buckettime = false;
				}
				super.paintComponent(g);
				if (quality >= 1)
					g.drawImage(farm, 0, 0, null);
				else
				{
					g.setColor(new Color(154, 205, 50));
					g.fillRect(0,0,1270, 750);
				}
				
				g.drawImage(bucketimage, bucketpos, 600, null);
		
				for (int i = 0; i < eggpos.size(); i++)
				{
					if (quality == 2)
						g.drawImage(egg, eggpos.get(i), eggy[i], null);
					else if (quality <= 1)
					{
						g.setColor(Color.white);
						g.fillOval(eggpos.get(i), eggy[i], 57, 82);
					}
				}
				
				g.drawImage(bucketover, bucketpos, 600, null);
				
				for (int x = 0; x < eggcount; x++)
				{
					if (eggpos.get(x) >= bucketpos && eggpos.get(x) <= bucketpos + 160)
					{
						if (eggy[x] >= 600)
						{
							g.setColor(Color.red);
							g.setFont(new Font("Monotype", Font.BOLD, 18));
							eggy[x] = 850;
							eggpos.remove(x);
							eggpos.add(x, -100);
						}
					}
					else
					{
						if (eggy[x] >=625 && eggy[x] <= 750)
						{
							if (timing >= 5)
								timing-=3;
							if (memory >= 5)
								memory-=2;
							fakegridtimerstart = true;
							paintpaint = true;
							opcounter = 0;
							blocktimer.stop();
							timetimer.stop();
							done = true;
							g.drawImage(farm, 0, 0, null);
							g.drawImage(bucketimage, bucketpos, 600, null);
							for (int i = 0; i < eggpos.size(); i++)
								g.drawImage(egg, eggpos.get(i), eggy[i], null);
							g.drawImage(bucketover, bucketpos, 600, null);
							g.setColor(new Color(0, 0, 0, 160));
							g.fillRect(0, 0, 1270, 750);
							g.setColor(Color.white);
							g.setFont(new Font("Monotype", Font.BOLD, 36));
							g.drawString("BucketCatcher Lost", 460, 350);
							playbucket = false;
							CardLayout c = (CardLayout)(game.getLayout());
							c.show(game, "GridPanel");
							
						}
					}
				}
				
				if (clockbucket <= 0)
				{
					if (memory <= 99)
						memory+=5;
					if (timing <= 99)
						timing+=5;
					fakegridtimerstart = true;
					paintpaint = true;
					blocktimer.stop();
					timetimer.stop();
					g.drawImage(farm, 0, 0, null);
					g.drawImage(bucketimage, bucketpos, 600, null);
					for (int i = 0; i < eggpos.size(); i++)
						g.drawImage(egg, eggpos.get(i), eggy[i], null);
					g.drawImage(bucketover, bucketpos, 600, null);
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					bucketdone = true;
					buckettime = true;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockbucket = 1270;
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockbucket, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockbucket, 15, 20);
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_bucket, 1130, 20);
			}	
			
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent e)
				{
					east = true;
					west = false;
					blocktimer.start();
				}
			}
			
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent e)
				{
					west = true;
					east = false;
					blocktimer.start();
				}
			}
			
			class StillAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent e)
				{
					blocktimer.stop();
				}
			}
			
			class Mover3 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					timecount++;
					clockbucket-=3;
					int modtemp = 165;
					if (diff_bucket >= 1)
						modtemp-=3;
					if (diff_bucket >= 2)
						modtemp-=3;
					if (diff_bucket >= 3)
						modtemp-=3;
					if (diff_bucket >= 4)
						modtemp-=3;
					if (diff_bucket >= 5)
						modtemp-=3;
					if (diff_bucket >= 6)
						modtemp-=3;
					if (diff_bucket >= 7)
						modtemp-=3;
					if (diff_bucket >= 8)
						modtemp-=3;
					if (diff_bucket >= 9)
						modtemp-=3;
					if (diff_bucket >= 10)
						modtemp-=3;
						
					if (timecount % modtemp == 0)
					{
						int eggrand = (int)(Math.random()*1180);
						while (eggrand < 50)
							eggrand = (int)(Math.random()*1180);
						eggpos.add(eggcount, eggrand);
						eggy[eggcount] = -60;
						eggcount++;
					}
					for (int i = 0; i < 10000; i++)
					{
						if (diff_bucket >= 1)
							eggy[i]++;
						if (diff_bucket >= 2)
							eggy[i]++;
						if (diff_bucket >= 3)
							eggy[i]++;
						if (diff_bucket >= 4)
							eggy[i]++;
						if (diff_bucket >= 5)
							eggy[i]++;
						if (diff_bucket >= 6)
							eggy[i]++;
						if (diff_bucket >= 7)
							eggy[i]++;
						if (diff_bucket >= 8)
							eggy[i]++;
						if (diff_bucket >= 9)
							eggy[i]++;
						if (diff_bucket >= 10)
							eggy[i]++;
					}
					repaint();
				}
			}
			
			class Mover2 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if (east)
					{
						if (bucketpos <= 1270)
							bucketpos+=5;
						else
							bucketpos = -160;
					}
					else if (west)
					{
						if (bucketpos >= -160)
							bucketpos-=5;
						else
							bucketpos = 1270;
					}
					repaint();
				}
			}
		}

		// Helicopter Game
		class HeliPanel extends JPanel
		{
			int poscounter = 20;
			int stepcounter = 0;
			int timecounter = 0;
			int backcounter = 0;
			int blockcounter = 0;
			int numblock = 0;
			int yheli = 0;
			boolean north, done;
			Mover mover = new Mover();
			Mover3 mover3 = new Mover3();
			HeadMover headmover = new HeadMover();
			Timer timer = new Timer(1, mover);
			Timer helitimer = new Timer(1, mover);
			Timer blocktimer = new Timer(1, mover3);
			Timer headtimer = new Timer(15, headmover);
			ArrayList<Integer> helipos = new ArrayList<Integer>();
			ArrayList<Integer> xblock = new ArrayList<Integer>();
			ArrayList<Integer> yblock = new ArrayList<Integer>();
			ArrayList<Integer> blocklength = new ArrayList<Integer>();
			ArrayList<Integer> blockheight = new ArrayList<Integer>();
			ArrayList<Integer> blockspeed = new ArrayList<Integer>();
			Action upAction, downAction;
			
			public HeliPanel()
			{
				upAction = new UpAction();
				downAction = new DownAction();
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "doDownAction");
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doUpAction", upAction);
				done = false;
				north = false;
				setBackground(Color.black);
			}
			
			public void paintComponent(Graphics g)
			{
				if (helitime)
				{
					timer.start();
					blocktimer.start();
					headtimer.start();
					helitime = false;
				}
				super.paintComponent(g);
				
				if (quality == 1)
					g.drawImage(helibackgroundimage, 0, 0, null);
				else if (quality == 2)
				{
					g.drawImage(helibackgroundimage, backcounter, 0, null);
					g.drawImage(helibackgroundimage, backcounter+1270, 0, null);
				}
				
				int xposition = 224;

				for (int i = helipos.size()-1; i >=0; i-=25)
				{
					//g.drawImage(cloudimage, xposition, (helipos.get(i)+20), null);
					xposition-=26;
				}
				g.drawImage(helicopterimage, 250, yheli, null);
				///
				
				int modtemp = 70;
				if (diff_heli >= 1)
					modtemp-=5;
				if (diff_heli >= 2)
					modtemp-=5;
				if (diff_heli >= 3)
					modtemp-=5;
				if (diff_heli >= 4)
					modtemp-=5;
				if (diff_heli >= 5)
					modtemp-=5;
				if (diff_heli >= 6)
					modtemp-=5;
				if (diff_heli >= 7)
					modtemp-=5;
				if (diff_heli >= 8)
					modtemp-=5;
				if (diff_heli >= 9)
					modtemp-=5;
				if (diff_heli >= 10)
					modtemp-=5;

				if (blockcounter % modtemp == 0)
				{
					int randblock = (int)(Math.random()*800);
					yblock.add(numblock, randblock-100);
					xblock.add(numblock, 1270);
					int randx = (int)(Math.random()*200);
					while (randx < 95)
						randx = (int)(Math.random()*200);
					int randy = (int)(Math.random()*200);
					while (randy < 95)
						randy = (int)(Math.random()*200);
					blocklength.add(numblock, randx);
					blockheight.add(numblock, randy);
					int randspeed = (int)((Math.random()*4));
					while (randspeed < 1)
						randspeed = (int)((Math.random()*4));
					blockspeed.add(numblock, randspeed);
					numblock++;
				}
				
				for (int i = 0; i < xblock.size(); i++)
				{
					if (quality == 0)
					{
						g.setColor(Color.red);
						g.fillRect(xblock.get(i), yblock.get(i), blocklength.get(i), blockheight.get(i));
					}
					else if (quality >= 1)
						g.drawImage(asteroidimage, xblock.get(i), yblock.get(i), blocklength.get(i), blockheight.get(i), null);
				}
				
				for (int i = 0; i < xblock.size(); i++)
				{
					if (xblock.get(i) <= 335 && xblock.get(i) >= 335 - blocklength.get(i))
					{
						if (yheli+50 >= yblock.get(i) && yheli+15 <= (yblock.get(i) + blockheight.get(i)))
						{
							if (timing >= 5)
								timing-=3;
							if (memory >= 5)
								memory-=2;
							fakegridtimerstart = true;
							paintpaint = true;
							opcounter = 0;
							headtimer.stop();
							timer.stop();
							blocktimer.stop();
							helitimer.stop();
							done = true;
							g.setColor(new Color(0, 0, 0, 160));
							g.fillRect(0, 0, 1270, 750);
							g.setColor(Color.white);
							g.setFont(new Font("Monotype", Font.BOLD, 36));
							g.drawString("Helicopter Lost", 460, 350);
							CardLayout c = (CardLayout)(game.getLayout());
							playheli = false;
							c.show(game, "GridPanel");
						}
					}
				}
				
				if (clockheli <= 0)
				{
					if (memory <= 99)
						memory+=5;
					if (timing <= 99)
						timing+=5;;
					fakegridtimerstart = true;
					paintpaint = true;
					north = false;
					headtimer.stop();
					timer.stop();
					blocktimer.stop();
					helitimer.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					helidone = true;
					helitime = true;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockheli = 1270;
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockheli, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockheli, 15, 20);
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_heli, 1130, 20);
			}	
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					north = true;
					helitimer.start();
					repaint();
				}
			}
			
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					helitimer.stop();
					north = false;
					repaint();
				}
			}
			
			class Mover3 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					clockheli-=3;
					for (int i = 0; i < xblock.size(); i++)
					{
						// all the if-statements code for different difficulty levels
						int temp = xblock.get(i);
						xblock.remove(i);
						if (diff_heli == 1)
							xblock.add(i, (temp-blockspeed.get(i)));
						else if (diff_heli == 2)
							xblock.add(i, (temp-(blockspeed.get(i)+1)));
						else if (diff_heli == 3)
							xblock.add(i, (temp-(blockspeed.get(i)+2)));
						else if (diff_heli == 4)
							xblock.add(i, (temp-(blockspeed.get(i)+3)));
						else if (diff_heli == 5)
							xblock.add(i, (temp-(blockspeed.get(i)+4)));
						else if (diff_heli == 6)
							xblock.add(i, (temp-(blockspeed.get(i)+5)));
						else if (diff_heli == 7)
							xblock.add(i, (temp-(blockspeed.get(i)+6)));
						else if (diff_heli == 8)
							xblock.add(i, (temp-(blockspeed.get(i)+7)));
						else if (diff_heli == 9)
							xblock.add(i, (temp-(blockspeed.get(i)+8)));
						else if (diff_heli >= 10)
							xblock.add(i, (temp-(blockspeed.get(i)+9)));

					}
					blockcounter++;
					repaint();
				}
			}
			
			class HeadMover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if (backcounter == 1270)
					{
						backcounter = 0;
					}
					backcounter--;
					repaint();
				}
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					helipos.add(stepcounter, yheli);
					stepcounter++;
					if (north)
					{
						if (yheli >= 0)
							yheli-=3;
					}
					else
					{
						if (yheli <= 625)
							yheli+=3;
					}
					timecounter++;
					repaint();
				}
			}
		}
		
		// Pong game
		class PongPanel extends JPanel
		{
			boolean east, west;
			int poscounter = 20;
			int blockpos = 500;
			int ballx = 500;
			int bally = 0;
			boolean right, down;
			int randmove = (int)(Math.random()*4);
			boolean done = false;
			Mover mover = new Mover();
			Mover2 mover2 = new Mover2();
			Mover3 mover3 = new Mover3();
			Timer timer = new Timer(1, mover);
			Timer blocktimer = new Timer(1, mover2);
			Timer headtimer = new Timer(15, mover3);
			Action leftAction, rightAction, stillAction;
			
			public PongPanel()
			{
				leftAction = new LeftAction();
				rightAction = new RightAction();
				stillAction = new StillAction();
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "doStillAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "doStillAction");
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
				this.getActionMap().put("doStillAction", stillAction);
				east = west = false;
	
				right = down = true;

			}
			
			public void paintComponent(Graphics g)
			{
				if (pongtime)
				{
					timer.start();
					//blocktimer.start();;
					pongtime = false;
				}
				super.paintComponent(g);
				
				if (done)
				{
					if (timing >= 5)
						timing-=3;
					if (memory >= 5)
						memory-=2;
					fakegridtimerstart = true;
					g.drawImage(pongbackground, 0, 0, null);
					paintpaint = true;
					g.setColor(Color.red);
					g.drawImage(pongball, ballx, bally, null);
					g.drawImage(pongblock, blockpos, 650, 125, 25, null);
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					g.setColor(Color.white);
					g.setFont(new Font("Monotype", Font.BOLD, 36));
					g.drawString("Pong Lost", 530, 350);
					timer.stop();
					headtimer.stop();
					blocktimer.stop();
					opcounter = 0;
					CardLayout c = (CardLayout)(game.getLayout());
					playpong = false;
					c.show(game, "GridPanel");
				}
				else
				{
					// a little bit of hardcoding for difficulty and moving the ball	
					if (right && down)
					{
						if (diff_pong >= 1)
						{
							ballx+=2;
							bally+=2;
						}
						if (diff_pong >= 2)
						{
							ballx+=2;
							bally+=2;
						}	 
						if (diff_pong >= 3)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 4)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 5)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 6)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 7)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 8)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 9)
						{
							ballx++;
							bally++;
						}	 
						if (diff_pong >= 10)
						{
							ballx++;
							bally++;
						}	 

					}
					else if (right && !down)
					{
						if (diff_pong >= 1)
						{
							ballx+=2;
							bally-=2;
						}
						if (diff_pong >= 2)
						{
							ballx+=2;
							bally-=2;
						}	 
						if (diff_pong >= 3)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 4)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 5)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 6)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 7)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 8)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 9)
						{
							ballx++;
							bally--;
						}	 
						if (diff_pong >= 10)
						{
							ballx++;
							bally--;
						}
					}
					else if (!right && down)
					{
						if (diff_pong >= 1)
						{
							ballx-=2;
							bally+=2;
						}
						if (diff_pong >= 2)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 3)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 4)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 5)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 6)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 7)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 8)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 9)
						{
							ballx--;
							bally++;
						}	 
						if (diff_pong >= 10)
						{
							ballx--;
							bally++;
						}
					}
					else if (!right && !down)
					{
						if (diff_pong >= 1)
						{
							ballx-=2;
							bally-=2;
						}
						if (diff_pong >= 2)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 3)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 4)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 5)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 6)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 7)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 8)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 9)
						{
							ballx--;
							bally--;
						}	 
						if (diff_pong >= 10)
						{
							ballx--;
							bally--;
						}
					}
					
					if (quality >= 1)
						g.drawImage(pongbackground, 0, 0, null);
					else
					{
						g.setColor(new Color(105, 105, 105));
						g.fillRect(0,0,1270,750);
					}
					
					g.setColor(Color.red);
					if (quality == 2)
					{
						g.drawImage(pongball, ballx, bally, null);
						g.drawImage(pongblock, blockpos, 650, 125, 25, null);
					}
					else if (quality <= 1)
					{
						g.setColor(Color.white);
						g.fillOval(ballx, bally, 50, 50);
						g.setColor(Color.red);
						g.fillRect(blockpos, 650, 125, 25);
					}
				}
				
				if (clockpong <= 0)
				{
					if (memory <= 99)
						memory+=5;
					if (timing <= 99)
						timing+=5;;
					fakegridtimerstart = true;
					paintpaint = true;
					opcounter = 0;
					timer.stop();
					blocktimer.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					pongdone = true;
					pongtime = true;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockpong = 1270;
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockpong, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockpong, 15, 20);
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_pong, 1130, 20);
			}
			
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent e)
				{
					east = true;
					west = false;
					blocktimer.start();
					repaint();
				}
			}
			
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent e)
				{
					west = true;
					east = false;
					blocktimer.start();
					repaint();
				}
			}
			
			class StillAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent e)
				{
					blocktimer.stop();
				}
			}
			
			class Mover3 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if (poscounter == 1020)
					{
						poscounter = 0;
					}
					poscounter++;
					repaint();
				}
			}
			
			class Mover2 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if (east)
					{
						if (blockpos <= 1135)
							blockpos+=5;
					}
					else if (west)
					{
						if (blockpos >= 20)
							blockpos-=5;
					}
					repaint();
				}
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{	
					clockpong-=3;
					if (ballx >= frame.getWidth()-50)
					{
						//randmove = (int)(Math.random()*4);
						right = false;
					}
					if (ballx <= 0)
					{
						//randmove = (int)(Math.random()*4);
						right = true;
					}
					if (bally >= frame.getHeight()-100)
					{
						//randmove = (int)(Math.random()*4);
						down = false;
					}
					if (bally <= 0)
					{
						//randmove = (int)(Math.random()*4);
						down = true;
					}
						
					if ((ballx >= (blockpos-55) && ballx <= (blockpos + 125)))
					{
						if (bally >= 600)
						{
							//randmove = (int)(Math.random()*4);
							down = false;
						}
					}
					else
					{
						////System.out.println("else");
						if (bally > 635)
						{
							////System.out.println("Done");
							done = true;
						}	
					}
					repaint();
				}
			}
		}
		
		// KeypadRevenge game
		class KeyPanel extends JPanel
		{
			int poscounter = 20;
			int backcounter = 0;
			int timecount = 0;
			int numblock = 0;
			boolean rightkey, leftkey, upkey, downkey;
			boolean done;
			ArrayList<Integer> blockx = new ArrayList<Integer>();
			ArrayList<Integer> blocktype = new ArrayList<Integer>();
			ArrayList<Integer> blockdone = new ArrayList<Integer>(); // 0 is not done, 1 is done
			Mover mover = new Mover();
			Mover2 mover2 = new Mover2();
			Timer timer = new Timer(15, mover);
			Timer blocktimer = new Timer(1, mover2);
			int userkey = -1;
			Action upAction, downAction, leftAction, rightAction;
			
			public KeyPanel()
			{
				////System.out.println("keypanel paintCopm");
				upAction = new UpAction();
				downAction = new DownAction();
				leftAction = new LeftAction();
				rightAction = new RightAction();
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doDownAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getActionMap().put("doUpAction", upAction);
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
				done = false;
				rightkey = leftkey = upkey = downkey = false;
				setBackground(Color.black);
			}
			
			public void paintComponent(Graphics g)
			{
				if (keytime)
				{
					timer.start();
					blocktimer.start();
					keytime = false;
				}
				super.paintComponent(g);
				
				if (quality == 2)
				{
					g.drawImage(binarybackgroundimage, backcounter, 0, null);
					g.drawImage(binarybackgroundimage, backcounter-1270, 0, null);
				}
				else if (quality == 1)
				{
					g.drawImage(binarybackgroundimage, 0, 0, null);
				}
				else
				{
					g.setColor(Color.black);
					g.fillRect(0,0, 1270, 750);
				}
				
				g.setColor(Color.white);
				g.fillRect(0, 500, frame.getWidth(), 10);
				
				for (int i = 0; i < blockx.size(); i++)
				{
					if (blocktype.get(i) == 0)
					{
						g.drawImage(keyupimage, blockx.get(i), 410, null);
					}
					else if (blocktype.get(i) == 1)
					{
						g.drawImage(keydownimage, blockx.get(i), 410, null);
					}
					else if (blocktype.get(i) == 2)
					{
						g.drawImage(keyrightimage, blockx.get(i), 410, null);
					}
					else if (blocktype.get(i) == 3)
					{
						g.drawImage(keyleftimage, blockx.get(i), 410, null);
					}
				}
				
				g.fillRect(850, 400, 10, 100);
				g.drawRect(860, 409, 90, 91);
				
				if (upkey)
				{
					//userkey = 0;
					g.drawImage(upimage, 860, 410, null);
				}
				else if (rightkey)
				{
					//userkey = 2;
					g.drawImage(rightimage, 860, 410, null);
				}
				else if (leftkey)
				{
					//userkey = 3;
					g.drawImage(leftimage, 860, 410, null);
				}
				else if (downkey)
				{	
					//userkey = 1;
					g.drawImage(downimage, 860, 410, null);
				}
				
				for (int i = 0; i < blockx.size(); i++)
				{
					if (blockx.get(i) >= 860 && blockx.get(i) <= 950)
					{
						//System.out.println("-----\n blocktype = " + blocktype.get(i) + ", userkey = " + userkey);
						if (blocktype.get(i) == userkey)
						{
							blockdone.remove(i);
							blockdone.add(i, 1);
							g.setColor(Color.green);
							g.fillRect(850, 400, 10, 100);
							g.drawRect(860, 409, 90, 91);
							break;
						}
						else if (blocktype.get(i) != userkey && blockdone.get(i) == 0)
						{
							if (timing >= 5)
								timing-=3;
							if (memory >= 5)
								memory-=2;
							fakegridtimerstart = true;
							paintpaint = true;
							g.setColor(Color.red);
							g.fillRect(850, 400, 10, 100);
							g.drawRect(860, 409, 90, 91);
							timer.stop();
							blocktimer.stop();
							g.setColor(new Color(0, 0, 0, 160));
							g.fillRect(0, 0, 1270, 750);
							g.setColor(Color.white);
							g.setFont(new Font("Monotype", Font.BOLD, 36));
							g.drawString("KeypadRevenge Lost", 460, 350);
							playkey = false;
							CardLayout c = (CardLayout)(game.getLayout());
							opcounter = 0;
							c.show(game, "GridPanel");
						}
					}
				}
				if (clockkey <= 0)
				{
					if (memory <= 99)
						memory+=5;
					if (timing <= 99)
						timing+=5;;
					fakegridtimerstart = true;
					paintpaint = true;
					timer.stop();
					blocktimer.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					keydone = true;
					keytime = true;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockkey = 1270;
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockkey, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockkey, 15, 20);
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_key, 1130, 20);
			}
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					userkey = 0;
					upkey = true;
					rightkey = downkey = leftkey = false;
					repaint();
				}
			}
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					userkey = 1;
					downkey = true;
					upkey = rightkey = leftkey = false;
					repaint();
				}
			}
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					userkey = 3;
					leftkey = true;
					rightkey = upkey = downkey = false;
					repaint();
				}
			}
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					userkey = 2;
					rightkey = true;
					leftkey = upkey = downkey = false;
					repaint();
				}
			}
			
			class Mover2 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					timecount++;
					int modtemp = 100;
					if (diff_key >= 1)
						modtemp-=8;
					if (diff_key >= 2)
						modtemp-=8;
					if (diff_key >= 3)
						modtemp-=8;
					if (diff_key >= 4)
						modtemp-=10;
					if (diff_key >= 5)
						modtemp-=10;
					if (diff_key >= 6)
						modtemp-=10;
					if (diff_key >= 7)
						modtemp-=10;
					if (diff_key >= 8)
						modtemp-=11;
					if (diff_key >= 9)
						modtemp-=11;
					if (diff_key >= 10)
						modtemp-=11;
						
					////System.out.println("modtemp = " + modtemp);
					
					if (timecount % modtemp == 0)
					{
						int randkey = (int)(Math.random()*4);
						blocktype.add(numblock, randkey);
						blockx.add(numblock, -100);
						blockdone.add(numblock, 0);
						numblock++;
					}
					clockkey-=3;
					for (int i = 0; i < blockx.size(); i++)
					{
						int temp = blockx.get(i);
						blockx.remove(i);
						int tempadd = 0;
						if (diff_key >= 1)
							tempadd+=3;
						if (diff_key >= 2)
							tempadd+=2;
						if (diff_key >= 3)
							tempadd+=2;
						if (diff_key >= 4)
							tempadd+=2;
						if (diff_key >= 5)
							tempadd+=2;
						if (diff_key >= 6)
							tempadd+=2;
						if (diff_key >= 7)
							tempadd+=2;
						if (diff_key >= 8)
							tempadd+=2;
						if (diff_key >= 9)
							tempadd+=2;
						if (diff_key >= 10)
							tempadd+=2;
						
						blockx.add(i, temp+tempadd);
					}
					repaint();
				}
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					poscounter++;
					if (backcounter == 1270)
					{
						backcounter = 0;
					}
					backcounter++;
					repaint();
				}
			}
		}
		
		// KnockBack game (all variables and classes related to KnockBack are called Dodge
		// because originally, the game was named Dodge, but I changed the game so much,
		// that Dodge no longer applied.
		class DodgePanel extends JPanel
		{
			int poscounter = 20;
			int timecount = 0;
			int numblock = 0;
			int num2block = 0;
			boolean done = false;
			Mover4 mover4 = new Mover4();
			Mover mover = new Mover();
			Mover2 mover2 = new Mover2();
			Mover3 mover3 = new Mover3();
			Timer movetimer = new Timer(1, mover2);
			Timer movetimer2 = new Timer(1, mover2);
			Timer movetimer3 = new Timer(1, mover2);
			Timer movetimer4 = new Timer(1, mover2);
			Timer shottimer = new Timer(1, mover3);
			Timer shottimer2 = new Timer(1, mover3);
			Timer timetimer = new Timer(1, mover4);
			MoverTimer movertimer = new MoverTimer();
			Timer clock = new Timer(1, movertimer);
			int [] xtrieast = {585, 645, 645};
			int [] xtriwest = {685, 624, 624};
			int [] ytri = {375, 340, 410};
			boolean north, south, east, west;
			ArrayList<Integer> shotx = new ArrayList<Integer>();
			ArrayList<Integer> shoty = new ArrayList<Integer>();
			ArrayList<Integer> shotd = new ArrayList<Integer>();
			ArrayList<Integer> blockx = new ArrayList<Integer>();
			ArrayList<Integer> blocky = new ArrayList<Integer>();
			ArrayList<Integer> blocklength = new ArrayList<Integer>();
			ArrayList<Integer> blockd = new ArrayList<Integer>();
			Action upAction;
			Action downAction;
			Action leftAction;
			Action rightAction;
			
			public DodgePanel()
			{
				upAction = new UpAction();
				downAction = new DownAction();
				leftAction = new LeftAction();
				rightAction = new RightAction();
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doDownAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getActionMap().put("doUpAction", upAction);
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
				north = south = false;
				east = false;
				west = true;
				setBackground(new Color(255, 165, 0));
			}
			
			public void paintComponent(Graphics g)
			{
				if (dodgetime)
				{
					movetimer.start();
					movetimer2.start();
					movetimer3.start();
					movetimer4.start();
					timetimer.start();
					shottimer.start();
					shottimer2.start();
					clock.start();
					dodgetime = false;
				}
				super.paintComponent(g);
				
				g.setColor(Color.white);
				g.fillRect(630, 0, 10, frame.getHeight());
				
				if (timecount % 1 == 0)
				{
					int shottemp = ((ytri[1]+ytri[2])/2) - 10;
					shoty.add(numblock, shottemp);
					if (east)
					{
						shotd.add(numblock, 0);
						shotx.add(numblock, 635);
					}
					else if (west)
					{
						shotd.add(numblock, 1);
						shotx.add(numblock, 620);
					}
					numblock++;
				}
				
				for (int i = 0; i < shotx.size(); i+=10)
				{
					g.setColor(Color.black);
					g.fillOval(shotx.get(i), shoty.get(i), 20, 20);
					g.setColor(Color.white);
					g.fillOval(shotx.get(i)+5, shoty.get(i)+5, 10, 10);
				}
				
				if (timecount % 60 == 0) //comment yay
				{
					int randy = (int)(Math.random()*630);
					while (randy < 30)
						randy = (int)(Math.random()*630);
					blocky.add(num2block, randy);
					int randd = (int)(Math.random()*2);
					blockd.add(num2block, randd);
					blocklength.add(num2block, 0);
					if (randd == 0)
						blockx.add(num2block, 1270);
					else if (randd == 1)
						blockx.add(num2block, -10);
				}
				
				for (int i = 0; i < blockx.size(); i++)
				{
					g.setColor(Color.black);
					g.fillRect(blockx.get(i), blocky.get(i), blocklength.get(i), 60);
					g.setColor(Color.red);
					g.fillRect(blockx.get(i) + 10, blocky.get(i) + 10, blocklength.get(i) - 20, 40);
				}
				
				for (int x = 0; x < blockx.size(); x++)
				{
					for (int y = 0; y < shotx.size(); y++)
					{
						if (blockd.get(x) == 0)
						{
						if (shotx.get(y)+20 >= blockx.get(x) && shotx.get(y)+20 <= blockx.get(x) + 10)
						{
							if (shoty.get(y)+20 >= blocky.get(x) && shoty.get(y) <= blocky.get(x)+60)
							{
								shotx.remove(y);
								shoty.remove(y);
								shotx.add(y, 0);
								shoty.add(y, 1500);
								int temp = blockx.get(x);
								blockx.remove(x);
								blockx.add(x, temp+50);
							}
						}
						}
						else if (blockd.get(x) == 1)
						{
						if (shotx.get(y) <= -10+blocklength.get(x) && shotx.get(y) >= -10+blocklength.get(x) - 10)
						{
							if (shoty.get(y)+20 >= blocky.get(x) && shoty.get(y) <= blocky.get(x)+60)
							{
								shotx.remove(y);
								shoty.remove(y);
								shotx.add(y, 0);
								shoty.add(y, 1500);
								int temp = blocklength.get(x);
								blocklength.remove(x);
								blocklength.add(x, temp-50);
							}
						}
						}
					}
				}
				
				for (int i = 0; i < blockx.size(); i++)
				{
					if (blockd.get(i) == 0)
					{
						if (blockx.get(i) <= 635)
						{
							if (timing >= 5)
								timing-=3;
							if (memory >= 5)
								memory-=2;
							done = true;
							fakegridtimerstart = true;
							paintpaint = true;
							g.setColor(new Color(0, 0, 0, 160));
							g.fillRect(0, 0, 1270, 750);
							g.setColor(Color.white);
							g.setFont(new Font("Monotype", Font.BOLD, 36));
							g.drawString("KnockBack Lost", 515, 350);
							shottimer.stop();
							shottimer2.stop();
							timetimer.stop();
							movetimer.stop();
							movetimer2.stop();
							movetimer3.stop();
							movetimer4.stop();
							CardLayout c = (CardLayout)(game.getLayout());
							playdodge = false;
							opcounter = 0;
							c.show(game, "GridPanel");
							break;
						}
					}
					else if (blockd.get(i) == 1)
					{
						if (-10 + blocklength.get(i) >= 635)
						{
							if (timing >= 5)
								timing-=3;
							if (memory >= 5)
								memory-=2;
							fakegridtimerstart = true;
							paintpaint = true;
							done = true;
							g.setColor(new Color(0, 0, 0, 160));
							g.fillRect(0, 0, 1270, 750);
							g.setColor(Color.white);
							g.setFont(new Font("Monotype", Font.BOLD, 36));
							g.drawString("KnockBack Lost", 515, 350);
							shottimer.stop();
							shottimer2.stop();
							timetimer.stop();
							movetimer.stop();
							movetimer2.stop();
							movetimer3.stop();
							movetimer4.stop();
							CardLayout c = (CardLayout)(game.getLayout());
							opcounter = 0;
							playdodge = false;
							c.show(game, "GridPanel");
							break;
						}
					}
				}
				
				if (west)
				{
					g.setColor(Color.green);
					g.fillPolygon(xtrieast, ytri, 3);
					g.setColor(Color.white);
					g.drawPolygon(xtrieast, ytri, 3);
				}
				else if (east)
				{
					g.setColor(Color.green);
					g.fillPolygon(xtriwest, ytri, 3);
					g.setColor(Color.white);
					g.drawPolygon(xtriwest, ytri, 3);
				}
				
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockdodge, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockdodge, 15, 20);
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_dodge, 1130, 20);
				
				if (clockdodge <= 0)
				{
					if (memory <= 99)
						memory+=5;
					if (timing <= 99)
						timing+=5;
					fakegridtimerstart = true;
					paintpaint = true;
					shottimer.stop();
					shottimer2.stop();
					movetimer.stop();
					movetimer2.stop();
					movetimer3.stop();
					movetimer4.stop();
					clock.stop();
					done = true;
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					dodgedone = true;
					dodgetime = true;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockdodge = 1270;
				}
				
			}
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					north = true;
					south = false;
					movetimer.start();
					movetimer2.start();
					movetimer3.start();
					movetimer4.start();	
				}
			}
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					south = true;
					north = false;
					movetimer.start();
					movetimer2.start();
					movetimer3.start();
					movetimer4.start();
				}
			}
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					west = true;
					east = false;
					repaint();
				}
			}
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					east = true;
					west = false;
					repaint();
				}
			}
			
			class MoverTimer implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					clockdodge-=3;
					repaint();
				}
			}
			
			class Mover4 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					timecount++;
					repaint();
				}
			}
			
			class Mover3 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					for (int i = 0; i < shotx.size(); i++)
					{
						int temp = shotx.get(i);
						shotx.remove(i);
						if (shotd.get(i) == 1)
							shotx.add(i, temp-=6);
						else if (shotd.get(i) == 0)
							shotx.add(i, temp+=6);
					}
					for (int i = 0; i < blockx.size(); i++)
					{
						if (blockd.get(i) == 0)
						{
							blocklength.remove(i);
							blocklength.add(i, 635);
							int temp = blockx.get(i);
							blockx.remove(i);
							blockx.add(i, temp-1);
						}
						else if (blockd.get(i) == 1)
						{
							int temp = blocklength.get(i);
							blocklength.remove(i);
							blocklength.add(i, temp+1);
						}
					}
					repaint();
				}
			}
			
			class Mover2 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if (north && ytri[1] > 0)
					{
						for (int i = 0; i < 3; i++)
						{
							ytri[i]-=4;
						}
					}
					else if (south && ytri[2] < frame.getHeight()-62)
					{
						for (int i = 0; i < 3; i++)
						{
							ytri[i]+=4;
						}
					}
					repaint();
				}
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					poscounter++;
					repaint();
				}
			}
		}
		
		
		// MATH Questions
		class MathPanel extends JPanel implements ActionListener
		{
			int poscounter = 20;
			ButtonGroup mathgroup;
			JRadioButton ans1, ans2, ans3, ans4;
			JLabel subjecttitle;
			JTextArea que;
			Mover mover = new Mover();
			Timer timer = new Timer(15, mover);
			JPanel anspanel;
			MoverTimer movertimer;
			Timer clock;
			boolean done = false;
			boolean finish = false;
			
			Action upAction, downAction, leftAction, rightAction;
			
			public MathPanel()
			{
				
				movertimer = new MoverTimer();
				clock = new Timer(1, movertimer);
				this.setLayout(null);
				
				subjecttitle = new JLabel("Math");
				subjecttitle.setFont(new Font("Monotype", Font.BOLD, 65));
				subjecttitle.setForeground(Color.gray);
				Dimension size = subjecttitle.getPreferredSize();
				subjecttitle.setBounds(50, 30, size.width, size.height);
				
				que = new JTextArea(mathq[mathcounter]);
				que.setLineWrap(true);
				que.setWrapStyleWord(true);
				que.setBackground(Color.gray);
				que.setFont(new Font("Monotype", Font.BOLD, 28));
				que.setForeground(Color.white);
				que.setBounds(150, 175, 1000, 110);
				que.setEditable(false);
				
				Font ansfont = new Font("Monotype", Font.PLAIN, 22);
				
				ans1 = new JRadioButton(matha1[mathcounter]);
				ans2 = new JRadioButton(matha2[mathcounter]);
				ans3 = new JRadioButton(matha3[mathcounter]);
				ans4 = new JRadioButton(matha4[mathcounter]);
				
				ans1.setBackground(Color.gray);
				ans1.setForeground(Color.white);
				ans1.setFont(ansfont);
				ans2.setBackground(Color.gray);
				ans2.setForeground(Color.white);
				ans2.setFont(ansfont);
				ans3.setBackground(Color.gray);
				ans3.setForeground(Color.white);
				ans3.setFont(ansfont);
				ans4.setBackground(Color.gray);
				ans4.setForeground(Color.white);
				ans4.setFont(ansfont);
				ans1.addActionListener(this);
				ans2.addActionListener(this);
				ans3.addActionListener(this);
				ans4.addActionListener(this);
				
				mathgroup = new ButtonGroup();
				mathgroup.add(ans1);
				mathgroup.add(ans2);
				mathgroup.add(ans3);
				mathgroup.add(ans4);
				
				anspanel = new JPanel(new GridLayout(4,0));
				anspanel.add(ans1);
				anspanel.add(ans2);
				anspanel.add(ans3);
				anspanel.add(ans4);
				
				anspanel.setBounds(170, 280, 1000, 320);
				anspanel.setBackground(Color.gray);
						
				this.add(subjecttitle);
				this.add(que);
				this.add(anspanel);
				timer.start();
				clock.start();
				
				upAction = new UpAction();
				downAction = new DownAction();
				leftAction = new LeftAction();
				rightAction = new RightAction();
				
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doDownAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getActionMap().put("doUpAction", upAction);
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
			}
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans1.setSelected(true);
					String command = ans1.getText();
					if (command.equals(matharight[mathcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans2.setSelected(true);
					String command = ans2.getText();
					if (command.equals(matharight[mathcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans3.setSelected(true);
					String command = ans3.getText();
					if (command.equals(matharight[mathcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans4.setSelected(true);
					String command = ans4.getText();
					if (command.equals(matharight[mathcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);	
				//System.out.println("mathcounter = " + mathcounter);
				que.setText(mathq[mathcounter]);
				ans1.setText(matha1[mathcounter]);
				ans2.setText(matha2[mathcounter]);
				ans3.setText(matha3[mathcounter]);
				ans4.setText(matha4[mathcounter]);
				
				g.setColor(Color.lightGray);
				////System.out.println("backcounter = " + backcounter);
				for (int x = -2000; x < 2000; x+=200)
				{
					for (int y = -1000; y < 1000; y+=200)
					{
						if (backcounter > 300)
						{
							backcounter *= -1;
						}
						g.drawRect(x-backcounter, y-backcounter, 200, 200);
					}
				}
				
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockmath, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockmath, 15, 20);
				g.setColor(Color.gray);
				g.fillRect(50, 110, 1170, 540);	
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_math, 1130, 20);
				
		
				
				if (done)
				{
					if (intelligence <= 95)
						intelligence+=6;
					fakegridtimerstart = true;
					paintpaint = true;
					timer.stop();
					clock.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					opcounter = 0;
					clockmath = 1270;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}		
				
				if (finish || clockmath <= 0)
				{
					intelligence -= 3;
					opcounter = 0;
					fakegridtimerstart = true;
					paintpaint = true;
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					g.setColor(Color.white);
					g.setFont(new Font("Monotype", Font.BOLD, 36));
					g.drawString("Math Lost", 515, 350);
					timer.stop();
					clock.stop();
					CardLayout c = (CardLayout)(game.getLayout());
					playmath = false;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}
				
				g.drawImage(fourarrow_up, 90, 300, null);
				g.drawImage(fourarrow_down, 90, 380, null);
				g.drawImage(fourarrow_left, 90, 460, null);
				g.drawImage(fourarrow_right, 90, 540, null);
				
				
			}
			
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				
				if (command.equals(matharight[mathcounter]))
				{
					done = true;
				}
				else
				{
					finish = true;
				}
				repaint();
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					backcounter++;
					repaint();
				}
			}
			
			class MoverTimer implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					clockmath-=diff_math;
					repaint();
				}
			}
		}
		
		// BIO Questions
		class BioPanel extends JPanel implements ActionListener
		{
			int poscounter = 20;
			ButtonGroup biogroup;
			JRadioButton ans1, ans2, ans3, ans4;
			JLabel subjecttitle;
			JTextArea que;
			Mover mover = new Mover();
			Timer timer = new Timer(15, mover);
			JPanel anspanel;
			MoverTimer movertimer;
			Timer clock;
			boolean done = false;
			boolean finish = false; // finish is when you get a question wrong
			Action upAction, leftAction, rightAction, downAction;
			
			public BioPanel()
			{
				movertimer = new MoverTimer();
				clock = new Timer(1, movertimer);
				this.setLayout(null);
				
				subjecttitle = new JLabel("Biology");
				subjecttitle.setFont(new Font("Monotype", Font.BOLD, 65));
				subjecttitle.setForeground(Color.gray);
				Dimension size = subjecttitle.getPreferredSize();
				subjecttitle.setBounds(50, 30, size.width, size.height);
				
				que = new JTextArea(bioq[biocounter]);
				que.setLineWrap(true);
				que.setWrapStyleWord(true);
				que.setBackground(Color.gray);
				que.setFont(new Font("Monotype", Font.BOLD, 28));
				que.setForeground(Color.white);
				que.setBounds(150, 175, 1000, 110);
				que.setEditable(false);
				
				Font ansfont = new Font("Monotype", Font.PLAIN, 22);
				
				ans1 = new JRadioButton(bioa1[biocounter]);
				ans2 = new JRadioButton(bioa2[biocounter]);
				ans3 = new JRadioButton(bioa3[biocounter]);
				ans4 = new JRadioButton(bioa4[biocounter]);
				
				ans1.setBackground(Color.gray);
				ans1.setForeground(Color.white);
				ans1.setFont(ansfont);
				ans2.setBackground(Color.gray);
				ans2.setForeground(Color.white);
				ans2.setFont(ansfont);
				ans3.setBackground(Color.gray);
				ans3.setForeground(Color.white);
				ans3.setFont(ansfont);
				ans4.setBackground(Color.gray);
				ans4.setForeground(Color.white);
				ans4.setFont(ansfont);
				ans1.addActionListener(this);
				ans2.addActionListener(this);
				ans3.addActionListener(this);
				ans4.addActionListener(this);
				
				biogroup = new ButtonGroup();
				biogroup.add(ans1);
				biogroup.add(ans2);
				biogroup.add(ans3);
				biogroup.add(ans4);
				
				anspanel = new JPanel(new GridLayout(4, 0));
				anspanel.add(ans1);
				anspanel.add(ans2);
				anspanel.add(ans3);
				anspanel.add(ans4);
				
				anspanel.setBounds(170, 280, 1000, 320);
				anspanel.setBackground(Color.gray);
						
				this.add(subjecttitle);
				this.add(que);
				this.add(anspanel);
				timer.start();
				clock.start();
				
				upAction = new UpAction();
				downAction = new DownAction();
				leftAction = new LeftAction();
				rightAction = new RightAction();
				
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doDownAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getActionMap().put("doUpAction", upAction);
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
			}
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans1.setSelected(true);
					String command = ans1.getText();
					if (command.equals(bioaright[biocounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans2.setSelected(true);
					String command = ans2.getText();
					if (command.equals(bioaright[biocounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans3.setSelected(true);
					String command = ans3.getText();
					if (command.equals(bioaright[biocounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans4.setSelected(true);
					String command = ans4.getText();
					if (command.equals(bioaright[biocounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);	
				//System.out.println("biocounter = " + biocounter);
				que.setText(bioq[biocounter]);
				ans1.setText(bioa1[biocounter]);
				ans2.setText(bioa2[biocounter]);
				ans3.setText(bioa3[biocounter]);
				ans4.setText(bioa4[biocounter]);
				
				g.setColor(Color.lightGray);
				////System.out.println("backcounter = " + backcounter);
				for (int x = -2000; x < 2000; x+=200)
				{
					for (int y = -1000; y < 1000; y+=200)
					{
						if (backcounter > 300)
						{
							backcounter *= -1;
						}
						g.drawRect(x-backcounter, y-backcounter, 200, 200);
					}
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockbio, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockbio, 15, 20);
				g.setColor(Color.gray);
				g.fillRect(50, 110, 1170, 540);		
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_bio, 1130, 20);
				
				if (done)
				{
					if (intelligence <= 95)
						intelligence+=6;
					fakegridtimerstart = true;
					paintpaint = true;
					timer.stop();
					clock.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					clockmath = 1270;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}		
				
				if (finish || clockbio <= 0)
				{
					intelligence -= 3;
					opcounter = 0;
					fakegridtimerstart = true;	
					paintpaint = true;

					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					g.setColor(Color.white);
					g.setFont(new Font("Monotype", Font.BOLD, 36));
					g.drawString("Biology Lost", 515, 350);
					timer.stop();
					clock.stop();
					CardLayout c = (CardLayout)(game.getLayout());
					playbio = false;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}	
				
				g.drawImage(fourarrow_up, 90, 300, null);
				g.drawImage(fourarrow_down, 90, 380, null);
				g.drawImage(fourarrow_left, 90, 460, null);
				g.drawImage(fourarrow_right, 90, 540, null);
			}
			
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				
				if (command.equals(bioaright[biocounter]))
				{
					done = true;
				}
				else
				{
					finish = true;
				}
				repaint();
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					backcounter++;
					repaint();
				}
			}
			
			class MoverTimer implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					clockbio-=diff_bio;
					repaint();
				}
			}	
		}
		
		// LIT questions
		class LitPanel extends JPanel implements ActionListener
		{
			int poscounter = 20;
			ButtonGroup litgroup;
			JRadioButton ans1, ans2, ans3, ans4;
			JLabel subjecttitle;
			JTextArea que;
			Mover mover = new Mover();
			Timer timer = new Timer(15, mover);
			JPanel anspanel;
			MoverTimer movertimer;
			Timer clock;
			boolean done = false;
			boolean finish = false; // finish is when you get a question wrong
			Action upAction, leftAction, rightAction, downAction;
			
			public LitPanel()
			{
				movertimer = new MoverTimer();
				clock = new Timer(1, movertimer);
				this.setLayout(null);
				
				subjecttitle = new JLabel("Literature");
				subjecttitle.setFont(new Font("Monotype", Font.BOLD, 65));
				subjecttitle.setForeground(Color.gray);
				Dimension size = subjecttitle.getPreferredSize();
				subjecttitle.setBounds(50, 30, size.width, size.height);
				
				que = new JTextArea(litq[litcounter]);
				que.setLineWrap(true);
				que.setWrapStyleWord(true);
				que.setBackground(Color.gray);
				que.setFont(new Font("Monotype", Font.BOLD, 28));
				que.setForeground(Color.white);
				que.setBounds(150, 175, 1000, 110);
				que.setEditable(false);
				
				Font ansfont = new Font("Monotype", Font.PLAIN, 22);
				
				ans1 = new JRadioButton(lita1[litcounter]);
				ans2 = new JRadioButton(lita2[litcounter]);
				ans3 = new JRadioButton(lita3[litcounter]);
				ans4 = new JRadioButton(lita4[litcounter]);
				
				ans1.setBackground(Color.gray);
				ans1.setForeground(Color.white);
				ans1.setFont(ansfont);
				ans2.setBackground(Color.gray);
				ans2.setForeground(Color.white);
				ans2.setFont(ansfont);
				ans3.setBackground(Color.gray);
				ans3.setForeground(Color.white);
				ans3.setFont(ansfont);
				ans4.setBackground(Color.gray);
				ans4.setForeground(Color.white);
				ans4.setFont(ansfont);
				ans1.addActionListener(this);
				ans2.addActionListener(this);
				ans3.addActionListener(this);
				ans4.addActionListener(this);
				
				litgroup = new ButtonGroup();
				litgroup.add(ans1);
				litgroup.add(ans2);
				litgroup.add(ans3);
				litgroup.add(ans4);
				
				anspanel = new JPanel(new GridLayout(4, 0));
				anspanel.add(ans1);
				anspanel.add(ans2);
				anspanel.add(ans3);
				anspanel.add(ans4);
				
				anspanel.setBounds(170, 280, 1000, 320);
				anspanel.setBackground(Color.gray);
						
				this.add(subjecttitle);
				this.add(que);
				this.add(anspanel);
				timer.start();
				clock.start();
				
				upAction = new UpAction();
				downAction = new DownAction();
				leftAction = new LeftAction();
				rightAction = new RightAction();
				
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doDownAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getActionMap().put("doUpAction", upAction);
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
			}
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans1.setSelected(true);
					String command = ans1.getText();
					if (command.equals(litaright[litcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans2.setSelected(true);
					String command = ans2.getText();
					if (command.equals(litaright[litcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans3.setSelected(true);
					String command = ans3.getText();
					if (command.equals(litaright[litcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans4.setSelected(true);
					String command = ans4.getText();
					if (command.equals(litaright[litcounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);	
				//System.out.println("litcounter = " + litcounter);
				que.setText(litq[litcounter]);
				ans1.setText(lita1[litcounter]);
				ans2.setText(lita2[litcounter]);
				ans3.setText(lita3[litcounter]);
				ans4.setText(lita4[litcounter]);
				
				g.setColor(Color.lightGray);
				////System.out.println("backcounter = " + backcounter);
				for (int x = -2000; x < 2000; x+=200)
				{
					for (int y = -1000; y < 1000; y+=200)
					{
						if (backcounter > 300)
						{
							backcounter *= -1;
						}
						g.drawRect(x-backcounter, y-backcounter, 200, 200);
					}
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clocklit, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clocklit, 15, 20);
				g.setColor(Color.gray);
				g.fillRect(50, 110, 1170, 540);	
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_lit, 1130, 20);
				
				if (done)
				{
					if (intelligence <= 95)
						intelligence+=6;
					fakegridtimerstart = true;	
					paintpaint = true;
					timer.stop();
					clock.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					clockmath = 1270;
					opcounter = 0;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}		
				
				if (finish || clocklit <= 0)
				{
					intelligence -= 3;
					opcounter = 0;
					fakegridtimerstart = true;
					paintpaint = true;
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					g.setColor(Color.white);
					g.setFont(new Font("Monotype", Font.BOLD, 36));
					g.drawString("Literature Lost", 515, 350);
					timer.stop();
					clock.stop();
					CardLayout c = (CardLayout)(game.getLayout());
					playlit = false;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}	
				
				g.drawImage(fourarrow_up, 90, 300, null);
				g.drawImage(fourarrow_down, 90, 380, null);
				g.drawImage(fourarrow_left, 90, 460, null);
				g.drawImage(fourarrow_right, 90, 540, null);
			}
			
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				
				if (command.equals(litaright[litcounter]))
				{
					done = true;
				}
				else
				{
					finish = true;
				}
				repaint();
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					backcounter++;
					repaint();
				}
			}
			
			class MoverTimer implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					clocklit-=diff_lit;
					repaint();
				}
			}
		}
		
		// JAVA questions
		class JavaPanel extends JPanel implements ActionListener
		{
			int poscounter = 20;
			ButtonGroup javagroup;
			JRadioButton ans1, ans2, ans3, ans4;
			JLabel subjecttitle;
			JTextArea que;
			Mover mover = new Mover();
			Timer timer = new Timer(15, mover);
			JPanel anspanel;
			MoverTimer movertimer;
			Timer clock;
			boolean done = false;
			boolean finish = false; // finish is when you get a question wrong
			Action upAction, downAction, leftAction, rightAction;
			
			public JavaPanel()
			{
				movertimer = new MoverTimer();
				clock = new Timer(1, movertimer);
				this.setLayout(null);
				
				subjecttitle = new JLabel("Java");
				subjecttitle.setFont(new Font("Monotype", Font.BOLD, 65));
				subjecttitle.setForeground(Color.gray);
				Dimension size = subjecttitle.getPreferredSize();
				subjecttitle.setBounds(50, 30, size.width, size.height);
				
				que = new JTextArea(javaq[javacounter]);
				que.setLineWrap(true);
				que.setWrapStyleWord(true);
				que.setBackground(Color.gray);
				que.setFont(new Font("Monotype", Font.BOLD, 28));
				que.setForeground(Color.white);
				que.setBounds(150, 175, 1000, 110);
				que.setEditable(false);
				
				Font ansfont = new Font("Monotype", Font.PLAIN, 22);
				
				ans1 = new JRadioButton(javaa1[javacounter]);
				ans2 = new JRadioButton(javaa2[javacounter]);
				ans3 = new JRadioButton(javaa3[javacounter]);
				ans4 = new JRadioButton(javaa4[javacounter]);
				
				ans1.setBackground(Color.gray);
				ans1.setForeground(Color.white);
				ans1.setFont(ansfont);
				ans2.setBackground(Color.gray);
				ans2.setForeground(Color.white);
				ans2.setFont(ansfont);
				ans3.setBackground(Color.gray);
				ans3.setForeground(Color.white);
				ans3.setFont(ansfont);
				ans4.setBackground(Color.gray);
				ans4.setForeground(Color.white);
				ans4.setFont(ansfont);
				ans1.addActionListener(this);
				ans2.addActionListener(this);
				ans3.addActionListener(this);
				ans4.addActionListener(this);
				
				javagroup = new ButtonGroup();
				javagroup.add(ans1);
				javagroup.add(ans2);
				javagroup.add(ans3);
				javagroup.add(ans4);
				
				anspanel = new JPanel(new GridLayout(4, 0));
				anspanel.add(ans1);
				anspanel.add(ans2);
				anspanel.add(ans3);
				anspanel.add(ans4);
				
				anspanel.setBounds(170, 280, 1000, 320);
				anspanel.setBackground(Color.gray);
						
				this.add(subjecttitle);
				this.add(que);
				this.add(anspanel);
				timer.start();
				clock.start();

				upAction = new UpAction();
				downAction = new DownAction();
				leftAction = new LeftAction();
				rightAction = new RightAction();
				
				this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doUpAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doDownAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doLeftAction");
				this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doRightAction");
				this.getActionMap().put("doUpAction", upAction);
				this.getActionMap().put("doDownAction", downAction);
				this.getActionMap().put("doLeftAction", leftAction);
				this.getActionMap().put("doRightAction", rightAction);
			}
			
			class UpAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans1.setSelected(true);
					String command = ans1.getText();
					if (command.equals(javaaright[javacounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class DownAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans2.setSelected(true);
					String command = ans2.getText();
					if (command.equals(javaaright[javacounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class LeftAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans3.setSelected(true);
					String command = ans3.getText();
					if (command.equals(javaaright[javacounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			class RightAction extends AbstractAction
			{
				public void actionPerformed(ActionEvent tf)
				{
					ans4.setSelected(true);
					String command = ans4.getText();
					if (command.equals(javaaright[javacounter]))
					{
						done = true;
					}
					else
					{
						finish = true;
					}
					//repaint();
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);	
				//System.out.println("javacounter = " + javacounter);
				que.setText(javaq[javacounter]);
				ans1.setText(javaa1[javacounter]);
				ans2.setText(javaa2[javacounter]);
				ans3.setText(javaa3[javacounter]);
				ans4.setText(javaa4[javacounter]);
				
				g.setColor(Color.lightGray);
				////System.out.println("backcounter = " + backcounter);
				for (int x = -2000; x < 2000; x+=200)
				{
					for (int y = -1000; y < 1000; y+=200)
					{
						if (backcounter > 300)
						{
							backcounter *= -1;
						}
						g.drawRect(x-backcounter, y-backcounter, 200, 200);
					}
				}
				g.setFont(new Font("Monotype", Font.PLAIN, 20));
				g.setColor(new Color(124, 252, 0, 120));
				g.fillRect(0, 0, clockjava, 25);
				g.setColor(Color.black);
				g.drawString("Time: " + clockjava, 15, 20);
				g.setColor(Color.gray);
				g.fillRect(50, 110, 1170, 540);	
				g.setColor(new Color(0,0,0,160));
				g.fillRect(1075, 0, 500, 25);
				g.setColor(Color.white);
				g.drawString("Round: " + diff_java, 1130, 20);
				
				if (done)
				{
					if (intelligence <= 95)
						intelligence+=6;
					fakegridtimerstart = true;
					paintpaint = true;
					timer.stop();
					clock.stop();
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					CardLayout c = (CardLayout)(game.getLayout());
					opcounter = 0;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}		
				
				if (finish || clockjava <= 0)
				{
					intelligence -= 3;
					opcounter = 0;
					fakegridtimerstart = true;
					paintpaint = true;
					g.setColor(new Color(0, 0, 0, 160));
					g.fillRect(0, 0, 1270, 750);
					g.setColor(Color.white);
					g.setFont(new Font("Monotype", Font.BOLD, 36));
					g.drawString("Java Lost", 515, 350);
					timer.stop();
					clock.stop();
					CardLayout c = (CardLayout)(game.getLayout());
					playjava = false;
					c.show(game, "GridPanel");
					clockmath = 1270;
					clocklit = 1270;
					clockjava = 1270;
					clockbio = 1270;
				}	
				
				g.drawImage(fourarrow_up, 90, 300, null);
				g.drawImage(fourarrow_down, 90, 380, null);
				g.drawImage(fourarrow_left, 90, 460, null);
				g.drawImage(fourarrow_right, 90, 540, null);
			}
			
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				
				if (command.equals(javaaright[javacounter]))
				{
					done = true;
				}
				else
				{
					finish = true;
				}
				repaint();
			}
			
			class Mover implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					backcounter++;
					repaint();
				}
			}
			
			class MoverTimer implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					clockjava-=diff_java;
					repaint();
				}
			}
		}
	}
	
	// PracticePanel still in dev. Not used
	class PracticePanel extends JPanel
	{
		public PracticePanel()
		{
		
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
		}
	}
	
	// inits all BufferedImages
	public void getImages()
	{
		try
		{
			volumemute = ImageIO.read(new File("volumemute.png"));
			volumefull = ImageIO.read(new File("volumefull.png"));
		}
		catch(Exception e)
		{
			//System.out.println("Cannot Find volumemute.png, volumefull.png");
		}
		
		try
		{
			math = ImageIO.read(new File("math.png"));
			science = ImageIO.read(new File("science.png"));
			lit = ImageIO.read(new File("lit.png"));
			java = ImageIO.read(new File("java.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find math.png, science.png, lit.png, java.png");
		}
		
		try
		{
			arrowpad = ImageIO.read(new File("arrowpad.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find arrowpad.png");
		}
		
		try
		{
			tut = ImageIO.read(new File("tutorial.png"));
			thegrid= ImageIO.read(new File("fakegrid.png"));
			
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find tutorial.png, fakegrid.png");
		}
		
		try
		{
			pongblock = ImageIO.read(new File("pongblock.png"));
			pongball = ImageIO.read(new File("pongball.png"));
			pongbackground = ImageIO.read(new File("pongbackground.png"));
			
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find pongblock.png, pongball.png, pongbackground.png");
		}
		
		try
		{
			bucketimage = ImageIO.read(new File("bucketimage.png"));
			egg = ImageIO.read(new File("egg.png"));
			farm = ImageIO.read(new File("farm.png"));
			bucketover = ImageIO.read(new File("bucketover.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find bucketimage.png, egg.png, farm.png, bucketover.png");
		}
		
		try
		{
			helicopterimage = ImageIO.read(new File("helicopter.png"));
			cloudimage = ImageIO.read(new File("cloud.png"));
			helibackgroundimage = ImageIO.read(new File("helibackground.png"));
			asteroidimage = ImageIO.read(new File("asteroid.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find helicopter.png, cloud.png, helibackground.png, asteroid.png");
		}
		
		try
		{
			upimage = ImageIO.read(new File("upkey.png"));
			downimage = ImageIO.read(new File("downkey.png"));
			leftimage = ImageIO.read(new File("leftkey.png"));
			rightimage = ImageIO.read(new File("rightkey.png"));
			keyupimage = ImageIO.read(new File("upkey2.png"));
			keydownimage = ImageIO.read(new File("downkey2.png"));
			keyrightimage = ImageIO.read(new File("rightkey2.png"));
			keyleftimage = ImageIO.read(new File("leftkey2.png"));
			binarybackgroundimage = ImageIO.read(new File("binarybackground.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find upkey.png, downkey.png, leftkey.png, rightkey.png, upkey2.png, downkey2.png, rightkey2.png, leftkey2.png, binarybackground.png");
		}
		try
		{
			bucketcatcherpic = ImageIO.read(new File("bucketcatcherpic.png"));
			pongpic = ImageIO.read(new File("pongpic.png"));
			helicopterpic = ImageIO.read(new File("helicopterpic.png"));
			keypadrevengepic = ImageIO.read(new File("keypadrevengepic.png"));
			dodgepic = ImageIO.read(new File("dodgepic.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find bucketcatcherpic.png, pongpic.png, helicopterpic.png, keypadrevengepic.png, dodgepic.png");
		}
		try
		{
			fourarrow_up = ImageIO.read(new File("fourarrow_up.png"));	
			fourarrow_down = ImageIO.read(new File("fourarrow_down.png"));
			fourarrow_left = ImageIO.read(new File("fourarrow_left.png"));
			fourarrow_right = ImageIO.read(new File("fourarrow_right.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find fourarrow_up.png, fourarrow_left.png, fourarrow_down.png, fourarrow_right.png");
		}
		try
		{
			constructionimage = ImageIO.read(new File("construction.png"));
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find construction.png");
		}
	}
	
	// Gets all sounds (sounds are disabled because they take up too much space)
	public void getSounds()
	{
		/*
		try
		{
			playmusic_in = new FileInputStream("playmusic.wav");
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find playmusic.wav");
		}
		try
		{
			playmusic_as = new AudioStream(playmusic_in);
		}
		catch(Exception e)
		{
			//System.out.println("Audio stream not created");
		}
		
		try
		{
			intromusic_in = new FileInputStream("intromusic.wav");
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find intromusic.wav");
		}	
		try
		{
			intromusic_as = new AudioStream(intromusic_in);
			//AudioPlayer.player.start(intromusic_as);
		}
		catch (Exception e)
		{
			//System.out.println("Audio stream not created");
		}
		
		try
		{
			playalive_in = new FileInputStream("playalive.wav");
			playalive_as = new AudioStream(playalive_in);
		}
		catch(Exception e)
		{
			//System.out.println("Cannot Find playalive.wav");
		}

		try
		{
			playdead_in = new FileInputStream("playdead.wav");
			playdead_as = new AudioStream(playdead_in);
		}
		catch (Exception e)
		{
			//System.out.println("Cannot Find playdead.wav");
		}	
		*/
	}
	
	// ALL QUESTIONS (INCLUDING MATH, LIT, BIO, JAVA)
	public void getQuestions()
	{
		//MATH
		
		mathq[0] = "If i = root(-1), what is the value of i^4?";
		matha1[0] = "A    i";
		matha2[0] = "B    -i";
		matha3[0] = "C    1";
		matha4[0] = "D    -1";
		matharight[0] = "C    1";
		
		mathq[1] = "What is an equivalent form of 2/(3+i)?";
		matha1[1] = "A    (3-i)/4";
		matha2[1] = "B    (3-i)/5";
		matha3[1] = "C    (4-i)/4";
		matha4[1] = "D    (4-i)/5";
		matharight[1] = "B    (3-i)/5";
		
		mathq[2] = "Which of the following sets of numbers could represent the lengths of the sides of a triangle?";
		matha1[2] = "A    2, 2, 5";
		matha2[2] = "B    3, 3, 5";
		matha3[2] = "C    4, 4, 8";
		matha4[2] = "D    5, 5, 15";
		matharight[2] = "B    3, 3, 5";
		
		mathq[3] = "csc(300) = ";
		matha1[3] = "A    (-2root(3))/3";
		matha2[3] = "B    (root(3)/3)";
		matha3[3] = "C    1/2";
		matha4[3] = "D    (root(2))/2";
		matharight[3] = "A    (-2root(3))/3";
		
		mathq[4] = "Which product is equivalent to (4x^2-16)/(2-x)?";
		matha1[4] = "A    4(x-2)";
		matha2[4] = "B    4(x+2)";
		matha3[4] = "C    -4(x-2)";
		matha4[4] = "D    -4(x+2)";
		matharight[4] = "D    -4(x+2)";
		
		mathq[5] = "If i = root(-1), then 4i(6i) = ";
		matha1[5] = "A    48";
		matha2[5] = "B    24";
		matha3[5] = "C    -24";
		matha4[5] = "D    -48";
		matharight[5] = "C    -24";
		
		mathq[6] = "The sum of the interior angles of a polygon is the same as the sum of its exterior angles. What type of polygon is it?";
		matha1[6] = "A    quadrilateral";
		matha2[6] = "B    hexagon";
		matha3[6] = "C    octagon";
		matha4[6] = "D    decagon";
		matharight[6] = "A    quadrilateral";
		
		mathq[7] = "A square is circumscribed about a cricle. What is the ratio of the area of the circle to the area of the square?";
		matha1[7] = "A    1/4";
		matha2[7] = "B    1/2";
		matha3[7] = "C    2/pi";
		matha4[7] = "D    pi/4";
		matharight[7] = "D    pi/4";
		
		//LIT
		
		litq[0] = "The word distracted is derived from a Latin root meaning";
		lita1[0] = "A    to wish or hope";
		lita2[0] = "B    to draw or pull back";
		lita3[0] = "C    to look or see";
		lita4[0] = "D    to say or tell";
		litaright[0] = "B    to draw or pull back";
		
		litq[1] = "The word glorify most nearly means";
		lita1[1] = "A    imagine.";
		lita2[1] = "B    predict.";
		lita3[1] = "C    examine.";
		lita4[1] = "D    honor.";
		litaright[1] = "D    honor.";
		
		litq[2] = "In which of these sentences is the marked word not spelled correctly?";
		lita1[2] = "A    *Chivalry* was the code by which knights lived.";
		lita2[2] = "B    Many *duels* were fought over the disrespect shown to a woman.";
		lita3[2] = "C    If a man had been *knighted*, he had shown the propor honor in battle";
		lita4[2] = "D    Knights must show a respectful *demeaner* toward ladies of the court";
		litaright[2] = "D    Knights must show a respectful *demeaner* toward ladies of the court";
		
		litq[3] = "Which word is derived from the name of the nymph in Greek mythology who pined away for Narcissus until all that was left was her voice?";
		lita1[3] = "A    vocal";
		lita2[3] = "B    echo";
		lita3[3] = "C    larynx";
		lita4[3] = "D    articulate";
		litaright[3] = "B    echo";
		
		litq[4] = "The word effect is derived from a Latin root meaning";
		lita1[4] = "A    to make.";
		lita2[4] = "B    to gather.";
		lita3[4] = "C    to urge.";
		lita4[4] = "D    to believe";
		litaright[4] = "A    to make.";
		
		litq[5] = "When the final draft of a report is typed, what line spacing should be used?";
		lita1[5] = "A    single-line spacing";
		lita2[5] = "B    double-line spacing";
		lita3[5] = "C    1.5-line spacing";
		lita4[5] = "D    2.5 line-spacing";
		litaright[5] = "B    double-line spacing";
		
		litq[6] = "The word volcano is derived from which of the following names for the god of fire?";
		lita1[6] = "A    Odin";
		lita2[6] = "B    Hephaestus";
		lita3[6] = "C    Vulcan";
		lita4[6] = "D    Pele";
		litaright[6] = "C    Vulcan";
		
		litq[7] = "Which of the following words is spelled incorrectly?";
		lita1[7] = "A    orangutan";
		lita2[7] = "B    atheroslerosis"; 
		lita3[7] = "C    massachusetts"; 
		lita4[7] = "D    inexplicable";
		litaright[7] = "B    atheroslerosis";
		
		//BIO
		
		bioq[0] = "The first stage of photosynthesis in a chloroplast is";
		bioa1[0] = "A    light-dependent";
		bioa2[0] = "B    temperature-dependent";
		bioa3[0] = "C    glucose-driven";
		bioa4[0] = "D    ATP-driven";
		bioaright[0] = "A    light-dependent";
		
		bioq[1] = "A base sequence is shown: ACAGTGC\nHow would the base sequence be coded on mRNA?";
		bioa1[1] = "A    TGTCACG";
		bioa2[1] = "B    GUGACAU";
		bioa3[1] = "C    UGUCACG";
		bioa4[1] = "D    CACUGUA";
		bioaright[1] = "C    UGUCACG";
		
		bioq[2] = "Which of these secretes a hormone that regulates the rate of metabolism of the body?";
		bioa1[2] = "A    spleen";
		bioa2[2] = "B    cerebrum";
		bioa3[2] = "C    thyroid";
		bioa4[2] = "D    kidney";
		bioaright[2] = "C    thyroid";
		
		bioq[3] = "A small portion of a population that is geographically isolated form the rest of the population runs the risk of decreased";
		bioa1[3] = "A    genetic drift.";
		bioa2[3] = "B    mutation rate.";
		bioa3[3] = "C    natural selection.";
		bioa4[3] = "D    genetic variation.";
		bioaright[3] = "D    genetic variation.";
		
		bioq[4] = "Which of these organisms are most helpful in preventing Earth from being covered with the bodies of dead organisms?";
		bioa1[4] = "A    herbivores";
		bioa2[4] = "B    producers";
		bioa3[4] = "C    parasites and viruses";
		bioa4[4] = "D    fungi and bacteria";
		bioaright[4] = "D    fungi and bacteria";
		
		bioq[5] = "Mutations within a DNA sequence are";
		bioa1[5] = "A    natural processes that produce genetic diversity.";
		bioa2[5] = "B    natural processes that always affect the phenotype.";
		bioa3[5] = "C    unnatural processes that always effect the phenotype.";
		bioa4[5] = "D    unnatural processes that are harmful to genetic diversity.";
		bioaright[5] = "A    natural processes that produce genetic diversity.";
		
		bioq[6] = "In aerobic respiration, the Krebs cycle takes place in";
		bioa1[6] = "A    chloroplasts.";
		bioa2[6] = "B    nuclei.";
		bioa3[6] = "C    lysosomes.";
		bioa4[6] = "D    mitochondria.";
		bioaright[6] = "D    mitochondria.";
		
		bioq[7] = "Which of these organisms would most likely be found at the bottom of a biomass pyramid?";
		bioa1[7] = "A    giant squids";
		bioa2[7] = "B    sand sharks";
		bioa3[7] = "C    sea cucumbers";
		bioa4[7] = "D    green algae";
		bioaright[7] = "D    green algae";
		
		
		//JAVA
		
		javaq[0] = "The following is NOT an example of a data type.";
		javaa1[0] = "A    int";
		javaa2[0] = "B    public";
		javaa3[0] = "C    Button";
		javaa4[0] = "D    void";
		javaaright[0] = "B    public";
		
		javaq[1] = "6 / 1 % 3 * 3"; //right answer is 0
		javaa1[1] = "A    3";
		javaa2[1] = "B    1.5";
		javaa3[1] = "C    0";
		javaa4[1] = "D    6";
		javaaright[1]  = "C    0";
		
		javaq[2] = "How do you declare and initialize a 2D String array?";
		javaa1[2] = "A    String 2[] line = new [10][10];";
		javaa2[2] = "B    String [][] line = new String[10][10];";
		javaa3[2] = "C    [][] String line = new String[10][10];";
		javaa4[2] = "D    String line [][] = new String[10][10];";
		javaaright[2] = "B    String [][] line = new String[10][10];";
		
		javaq[3] = "The data type for numbers such as 3.13 is:";
		javaa1[3] = "A    float";
		javaa2[3] = "B    int";
		javaa3[3] = "C    real";
		javaa4[3] = "D    String";
		javaaright[3] = "A    float";
		
		javaq[4] = "int k = 4; int n = 12;\nwhile (k < n)\nk++";
		javaa1[4] = "A    4";
		javaa2[4] = "B    11";
		javaa3[4] = "C    12";
		javaa4[4] = "D    unknown";
		javaaright[4] = "C    12";
		
		javaq[5] = "In what method is setContentPane() normally written?";
		javaa1[5] = "A    paintComponent()";
		javaa2[5] = "B    init()";
		javaa3[5] = "C    Run()";
		javaa4[5] = "D    Mover()";
		javaaright[5] = "B    init()";
		
		javaq[6] = "char [] c = new char[100];\nWhat is the value of c[50]?";
		javaa1[6] = "A    50";
		javaa2[6] = "B    null";
		javaa3[6] = "C    cannot be determined";
		javaa4[6] = "D    49";
		javaaright[6] = "C    cannot be determined";
		
		javaq[7] = "Represent the number 6 as binary";
		javaa1[7] = "A    101";
		javaa2[7] = "B    110";
		javaa3[7] = "C    1010";
		javaa4[7] = "D    6";
		javaaright[7] = "B    110";
	}
	
	public void playAudio()
	{
		/*
		if (audioloop % 162 == 0)
		{
			AudioPlayer.player.stop(playmusic_as);
			//System.out.
			try
			{
				playmusic_in = new FileInputStream("playmusic.wav");
			}
			catch (Exception e)
			{
				//System.out.println("Cannot Find playmusic.wav");
			}
			try
			{
				playmusic_as = new AudioStream(playmusic_in);
			}
			catch(Exception e)
			{
				//System.out.println("Audio stream not created");
			}
			AudioPlayer.player.start(playmusic_as);
		}
		*/
	}
	
	class SoundMover implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			audioloop++;
			playAudio();
		}
	}
}