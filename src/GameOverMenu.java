import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverMenu extends MainMenu implements ActionListener
{
	private static final long serialVersionUID = 1L;

	protected static double buttonWidth = 225;
	protected static double buttonHeight = 50;
	protected static int fontSize = 28;
	
	private static BorderLayout mainLayout = new BorderLayout();
	
	private static JPanel topPanel;
	private static JPanel buttonPanel;

	private JButton retry = new JButton("Retry");
	private JButton shipMenu = new JButton("Ship");
	private JButton mainMenu = new JButton("Main Menu");

	private int stepCounter = 0;
	private int experienceAdded = 0;

	public GameOverMenu(int width, int height)
	{
		setBackground(Color.BLACK);

		panelWidth = width;
		panelHeight = height;

		setPreferredSize(new Dimension(panelWidth, panelHeight));
		
		setLayout(mainLayout);
		
		initializePanels();
		initializeButtons();
	}
	
	public void initializePanels()
	{
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);
		buttonPanel.setBackground(Color.BLACK);
		add(buttonPanel, BorderLayout.CENTER);
		
		topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setPreferredSize(new Dimension(10, 90));
		
		JLabel scoreLabel = new JLabel("Score:  " + GamePanel.getScore());
		scoreLabel.setFont(new Font("Lucida", Font.ITALIC, 20));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setPreferredSize(new Dimension(250, 60));

		topPanel.add(scoreLabel);
		
		add(topPanel, BorderLayout.NORTH);
	}

	public void initializeButtons()
	{
		retry.setAlignmentX(Component.CENTER_ALIGNMENT);
		shipMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

		setButtonLook(retry, "menuButton.png");
		setButtonLook(shipMenu, "menuButton.png");
		setButtonLook(mainMenu, "menuButton.png");

		retry.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(experienceAdded < GamePanel.getScore())
				{
					ProfileManager.addExperience(GamePanel.getScore() - experienceAdded);
				}
				
				ProfileManager.saveProfile();
				GamePanel.setMultiplayer(false);
				Gravity.setState("gameRunning");
			}
		});
		
		shipMenu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Gravity.setState("shipMenu");
			}
		});

		mainMenu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(experienceAdded < GamePanel.getScore())
				{
					ProfileManager.addExperience(GamePanel.getScore() - experienceAdded);
				}
				
				ProfileManager.saveProfile();
				GamePanel.setMultiplayer(true);
				Gravity.setState("mainMenu");
			}
		});

		buttonPanel.add(Box.createVerticalStrut(panelHeight / 3));
		buttonPanel.add(retry);
		buttonPanel.add(Box.createVerticalStrut(6));
		buttonPanel.add(shipMenu);
		buttonPanel.add(Box.createVerticalStrut(6));
		buttonPanel.add(mainMenu);
	}

	public void actionPerformed(ActionEvent e)
	{
		this.requestFocus();

		stepCounter++;

		if(stepCounter >= 50)
		{
			if(experienceAdded < GamePanel.getScore())
			{
				ProfileManager.addExperience(1);
				experienceAdded++;
			}
		}

		this.repaint(); 
	}

	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);  // What does this even do?

		g.setFont(new Font("Lucida", Font.ITALIC, 22));

		g.setColor(Color.WHITE);

		g.drawString("Score: " + GamePanel.getScore(), 550, 80);
	}
}
