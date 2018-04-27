package harrypotter.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import harrypotter.model.character.Champion;
import harrypotter.model.character.GryffindorWizard;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.RavenclawWizard;
import harrypotter.model.character.SlytherinWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.Potion;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.tournament.FirstTask;
import harrypotter.model.tournament.SecondTask;
import harrypotter.model.tournament.Task;
import harrypotter.model.tournament.ThirdTask;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.Direction;
import harrypotter.model.world.Merperson;
import harrypotter.model.world.ObstacleCell;
import harrypotter.model.world.WallCell;

public class TaskView extends JFrame {
	private JPanel gridPanel;
	// private JPanel traitPanel;
	private JPanel spellsPanel;
	private JPanel infoPanelRight;
	private JPanel infoPanelLeft;

	private JPanel infoPanel;

	private JLabel[][] grid;
	private JLabel nameLabel;
	private JLabel hpLabel;
	private JLabel ipLabel;

	private JButton traitButton;
	private JLabel traitInfo;

	private JButton spell1Button;
	private JButton spell2Button;
	private JButton spell3Button;
	private Task currentTask;
	private JButton usePotion;

	private JLabel allowedMoves;
	private JLabel Winners;
	private JButton skipTurn;
	private JComboBox<Object> potions;
	private JLabel spell3;
	private JLabel spell1;
	private JLabel spell2;

	public TaskView(Task currentTask, ActionListener l, KeyListener K) {
		this.currentTask = currentTask;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JFrame frame = this;

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, "Are you sure to close this window?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "GameOver!", "", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
			}
		});
		this.addKeyListener(K);
		setFocusable(false);
		int fixedX = 800;
		int fixedY = 1200;
		int x = Toolkit.getDefaultToolkit().getScreenSize().width;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height;

		setTitle("HarryPotter");
		setBounds(0, 0, x, y);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		setUndecorated(false);

		gridPanel = new JPanel();
		// traitPanel = new JPanel();
		spellsPanel = new JPanel();

		infoPanel = new JPanel();
		infoPanelRight = new JPanel();
		infoPanelLeft = new JPanel();

		infoPanel.setLayout(new BorderLayout());
		infoPanelRight.setLayout(new BorderLayout());
		infoPanelLeft.setLayout(new BorderLayout());

		spellsPanel.setBackground(new Color(150, 98, 15));
		infoPanel.setBackground(new Color(150, 98, 15));
		gridPanel.setBackground(new Color(150, 98, 15));

		infoPanelRight.setBackground(new Color(150, 98, 15));
		infoPanelLeft.setBackground(new Color(150, 98, 15));

		int widthPanel = 200 * fixedX / x;
		int HeightPanel = 200 * fixedY / x;

		infoPanelRight.setPreferredSize(new Dimension(x - x / 4, HeightPanel));
		infoPanelLeft.setPreferredSize(new Dimension(x / 4, HeightPanel));

		spellsPanel.setPreferredSize(new Dimension(widthPanel * 2, 0));
		gridPanel.setSize(new Dimension(x - widthPanel * 3, y - HeightPanel));

		int width = (int) ((gridPanel.getWidth()) / 10);
		int height = (int) ((gridPanel.getHeight()) / 10);

		add(infoPanel, BorderLayout.SOUTH);
		add(spellsPanel, BorderLayout.WEST);
		add(gridPanel, BorderLayout.CENTER);
		gridPanel.setLayout(new GridLayout(10, 10));

		grid = new JLabel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = new JLabel();
				grid[i][j].setOpaque(false);
				grid[i][j].setSize(new Dimension(width, height));
				if (this.currentTask.getMap()[i][j] instanceof ObstacleCell) {
					ObstacleCell cell = (ObstacleCell) this.currentTask.getMap()[i][j];
						grid[i][j].setToolTipText("HP: " + cell.getObstacle().getHp());
			

				}
				Border emptyBorder = BorderFactory.createEmptyBorder();
				grid[i][j].setBorder(emptyBorder);

				grid[i][j].setIcon(getIconFromMap(grid[i][j], i, j));

				gridPanel.add(grid[i][j]);

			}
		}

		Wizard current = (Wizard) (currentTask.getChampions().get(0));

		nameLabel = new JLabel();
		nameLabel.setText(" Name: " + current.getName());
		nameLabel.setSize(new Dimension(150, 150));
		nameLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		infoPanel.add(nameLabel);
		infoPanel.setLayout(new BorderLayout());
		hpLabel = new JLabel();
		hpLabel.setText(" HP: " + current.getDefaultHp());
		hpLabel.setSize(new Dimension(150, 150));
		hpLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		ipLabel = new JLabel();
		ipLabel.setText(" IP: " + current.getDefaultIp());
		ipLabel.setSize(new Dimension(150, 150));
		ipLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		infoPanelLeft.add(nameLabel, BorderLayout.NORTH);
		infoPanelLeft.add(hpLabel, BorderLayout.WEST);
		infoPanelLeft.add(ipLabel, BorderLayout.SOUTH);

		allowedMoves = new JLabel();
		allowedMoves.setText("Allowed Moves: " + this.currentTask.getAllowedMoves());
		allowedMoves.setSize(new Dimension(150, 50));
		allowedMoves.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		Winners = new JLabel();
		Winners.setText("Task Winners: No Winners Yet!");
		Winners.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		JLabel temp = new JLabel();
		temp.setSize(new Dimension(150, 200));
		temp.setText(" ");

		temp.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		// infoPanelRight.add(skipTurn, BorderLayout.NORTH);
		infoPanelRight.add(allowedMoves, BorderLayout.NORTH);
		infoPanelRight.add(Winners, BorderLayout.WEST);

		infoPanelRight.add(temp, BorderLayout.SOUTH);

		infoPanel.add(infoPanelRight, BorderLayout.EAST);
		infoPanel.add(infoPanelLeft, BorderLayout.WEST);

		// traitPanel.setLayout(new BorderLayout());
		// traitPanel.add(traitButton, BorderLayout.NORTH);
		// traitPanel.add(traitInfo, BorderLayout.CENTER);

		spell1Button = new JButton();
		spell1Button.setName("spell1Button");
		spell1Button.setText(current.getSpells().get(0).getName());
		spell1Button.setSize(new Dimension(30, 10));
		spell1Button.addActionListener(l);
		spell1Button.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));
		spell1Button.setToolTipText(this.getSpellType(current.getSpells().get(0)));
		spell1 = new JLabel();
		spell1.setText("Cost: " + current.getSpells().get(0).getCost() + " CoolDown "
				+ current.getSpells().get(0).getCoolDown());

		spell1.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

		spell2Button = new JButton();
		spell2Button.setName("spell2Button");
		spell2Button.setText(current.getSpells().get(1).getName());
		spell2Button.setSize(new Dimension(30, 10));
		spell2Button.addActionListener(l);
		spell2Button.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));
		spell2Button.setToolTipText(this.getSpellType(current.getSpells().get(1)));

		spell2 = new JLabel();
		spell2.setText("Cost: " + current.getSpells().get(1).getCost() + " CoolDown "
				+ current.getSpells().get(1).getCoolDown());

		spell2.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

		spell3Button = new JButton();
		spell3Button.setName("spell3Button");
		spell3Button.setText(current.getSpells().get(2).getName());
		spell3Button.setSize(new Dimension(30, 10));
		spell3Button.addActionListener(l);
		spell3Button.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

		spell3Button.setToolTipText(this.getSpellType(current.getSpells().get(2)));

		spell3 = new JLabel();
		spell3.setText("Cost: " + current.getSpells().get(2).getCost() + " CoolDown "
				+ current.getSpells().get(2).getCoolDown());
		spell3.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

		traitButton = new JButton();
		traitButton.setName("traitButton");
		traitButton.setText("USE TRAIT");
		traitButton.setSize(new Dimension(30, 10));
		traitButton.addActionListener(l);
		traitButton.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));
		traitButton.setToolTipText(getTraitDescription());
		traitInfo = new JLabel();
		traitInfo.setText(" Trait CoolDown: " + current.getTraitCooldown());
		traitInfo.setSize(new Dimension(150, 150));
		traitInfo.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

		usePotion = new JButton();
		usePotion.setText("Use Potion");
		usePotion.setName("usePotion");
		usePotion.addActionListener(l);
		usePotion.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));

		usePotion.setEnabled(false);

		String[] potion = { "You Have No Potions" };

		potions = new JComboBox<Object>(potion);
		potions.setSelectedIndex(0);
		potions.addActionListener(l);

		skipTurn = new JButton();
		skipTurn.setText("Skip Turn ");
		skipTurn.setName("skipTurn");

		skipTurn.setSize(new Dimension(150, 150));
		skipTurn.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));
		skipTurn.addActionListener(l);
		spellsPanel.setLayout(new GridLayout(11, 1));
		spellsPanel.add(spell1Button);
		spellsPanel.add(spell1);

		spellsPanel.add(spell2Button);
		spellsPanel.add(spell2);

		spellsPanel.add(spell3Button);
		spellsPanel.add(spell3);

		spellsPanel.add(traitButton);
		spellsPanel.add(traitInfo);

		spellsPanel.add(usePotion);
		spellsPanel.add(potions);
		spellsPanel.add(skipTurn);

		setVisible(true);
		setFocusable(true);
		this.requestFocus();
		requestFocusInWindow();

	}

	public String getSpellType(Spell spell) {
		if (spell instanceof DamagingSpell)
			return "Type: Damaging ,Damaging Amount: "+ ((DamagingSpell)spell).getDamageAmount();
		else if (spell instanceof RelocatingSpell)
			return "Type: Relocating ,Range: "+ ((RelocatingSpell)spell).getRange();
		else
			return "Type: Healing ,Healing Amount: "+ ((HealingSpell)spell).getHealingAmount();
		

	}

	public String getTraitDescription() {
		Wizard wizard = (Wizard) this.currentTask.getCurrentChamp();
		if (wizard instanceof GryffindorWizard)
			return "Enabling This Trait Allow You To Do Two Consecutive Actions Per Turn";
		if (wizard instanceof SlytherinWizard)
			return "Enabling This Trait Allow You To Traverse Two Cells";
		
		if (this.currentTask instanceof FirstTask) {
			if (wizard instanceof HufflepuffWizard)
				return "Enabling This Trait Doesn't Let The Dragon Attack You";
			if(wizard instanceof RavenclawWizard)
				return "Enabling This Trait Show You Where The Dragon Is Going To Attack";
		}
		if (this.currentTask instanceof SecondTask) {
			if (wizard instanceof HufflepuffWizard)
				return "Enabling This Trait Doesn't Let The MerPerson Attack You";
			if(wizard instanceof RavenclawWizard)
				return "Enabling This Trait Show A Hint About Where Is Your Treasure";
		}
		if (this.currentTask instanceof ThirdTask) {
			if (wizard instanceof HufflepuffWizard)
				return "Enabling This Trait Decrease Damage By Other Champions On You By 2";
			if(wizard instanceof RavenclawWizard)
				return "Enabling This Trait Show A Hint About Where Is The Cup";
		}

		return null;

	}

	public ImageIcon getIconFromMap(JLabel button, int i, int j) {
		String path = "Cell.png";
		if (this.currentTask.getMap()[i][j] instanceof ChampionCell) {
			path = ((Wizard) ((ChampionCell) this.currentTask.getMap()[i][j]).getChamp()).getPictureIndex();
		} else if (i == 4 && j == 4 && (this.currentTask instanceof FirstTask)) {
			path = "EggCell.png";

		} else if (this.currentTask.getMap()[i][j] instanceof ObstacleCell) {
			ObstacleCell cell = (ObstacleCell) this.currentTask.getMap()[i][j];
			if (cell.getObstacle() instanceof Merperson)
				path = "Mer.png";
			else
				path = "physical.png";

		} else if (this.currentTask.getMap()[i][j] instanceof WallCell) {
			path = "Wall.png";

		}
		return getIcon(path, button);
	}

	public static Dimension returnLabelSize() {
		int fixedX = 800;
		int fixedY = 1200;
		int x = Toolkit.getDefaultToolkit().getScreenSize().width;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height;
		int widthPanel = 200 * fixedX / x;
		int HeightPanel = 200 * fixedY / x;
		Dimension d = new Dimension(x - widthPanel * 3, y - HeightPanel);
		int width = (int) ((d.getWidth()) / 10);
		int height = (int) ((d.getHeight()) / 10);
		return new Dimension(width, height);
	}

	public ImageIcon getIcon(String path, JLabel button) {

		int index = returnEquvilantname(path);
		return Main_Menu.getIcons().get(index);
	}

	public void changeMapInfo() {
		Wizard current = (Wizard) (currentTask.getCurrentChamp());

		nameLabel.setText(" Name: " + current.getName());
		hpLabel.setText(" HP: " + current.getHp());
		ipLabel.setText(" IP: " + current.getIp());
		traitInfo.setText(" Trait CoolDown: " + current.getTraitCooldown());
		traitButton.setToolTipText(getTraitDescription());

		spell1Button.setText(current.getSpells().get(0).getName());
		spell2Button.setText(current.getSpells().get(1).getName());
		spell3Button.setText(current.getSpells().get(2).getName());
		spell1Button.setToolTipText(this.getSpellType(current.getSpells().get(0)));

		spell2Button.setToolTipText(this.getSpellType(current.getSpells().get(1)));

		spell3Button.setToolTipText(this.getSpellType(current.getSpells().get(2)));
		this.updatePotions();
		allowedMoves.setText("Allowed Moves: " + this.currentTask.getAllowedMoves());

		spell1.setText("Cost: " + current.getSpells().get(0).getCost() + " CoolDown "
				+ current.getSpells().get(0).getCoolDown());
		spell2.setText("Cost: " + current.getSpells().get(1).getCost() + " CoolDown "
				+ current.getSpells().get(1).getCoolDown());
		spell3.setText("Cost: " + current.getSpells().get(2).getCost() + " CoolDown "
				+ current.getSpells().get(2).getCoolDown());

		if (this.currentTask instanceof FirstTask)
			if (((FirstTask) this.currentTask).getWinners().size() == 0)
				Winners.setText("Task Winners: No Winners Yet!");
			else {
				System.out.println("here");
				String text = "Task Winners: ";
				for (int i = 0; i < ((FirstTask) this.currentTask).getWinners().size(); i++) {
					text += ((Wizard) ((FirstTask) this.currentTask).getWinners().get(i)).getName() + " ";

				}
				Winners.setText(text);

			}

		if (this.currentTask instanceof SecondTask)
			if (((SecondTask) this.currentTask).getWinners().size() == 0)
				Winners.setText("Task Winners: No Winners Yet!");
			else {
				String text = "Task Winners: ";
				for (int i = 0; i < ((SecondTask) this.currentTask).getWinners().size(); i++) {
					text += ((Wizard) ((SecondTask) this.currentTask).getWinners().get(i)).getName() + " ";

				}
				Winners.setText(text);

			}

	}

	public void updatePotions() {
		Wizard current = (Wizard) (currentTask.getCurrentChamp());

		if (current.getInventory().size() == 0) {
			potions.removeAllItems();
			usePotion.setEnabled(false);
			potions.addItem("You Have No Potions");
			potions.setSelectedIndex(0);

		} else {
			potions.removeAllItems();

			int size = current.getInventory().size();
			String[] potionStrings = new String[size];
			for (int i = 0; i < size; i++) {
				potionStrings[i] = current.getInventory().get(i).getName() + " "
						+ ((Potion) current.getInventory().get(i)).getAmount();
				potions.addItem(potionStrings[i]);
			}
			usePotion.setEnabled(true);

			potions.setSelectedIndex(0);
		}
	}

	public void changeMapHPandIP(Wizard wizard, int add) {

		hpLabel.setText(" HP: " + (wizard.getHp() + add));
		ipLabel.setText(" IP: " + wizard.getIp());

		this.updatePotions();

		spell1.setText("Cost: " + wizard.getSpells().get(0).getCost() + " CoolDown "
				+ wizard.getSpells().get(0).getCoolDown());
		spell2.setText("Cost: " + wizard.getSpells().get(1).getCost() + " CoolDown "
				+ wizard.getSpells().get(1).getCoolDown());
		spell3.setText("Cost: " + wizard.getSpells().get(2).getCost() + " CoolDown "
				+ wizard.getSpells().get(2).getCoolDown());

	}
	public void showHPZero() {

		hpLabel.setText(" HP: 0" );

		

	}
	public void changeMapIP(Wizard wizard) {
		ipLabel.setText(" IP: " + wizard.getIp());
		traitInfo.setText(" Trait CoolDown: " + wizard.getTraitCooldown());
		if (!(this.currentTask.isTraitActivated() && (wizard instanceof GryffindorWizard)))
			allowedMoves.setText("Allowed Moves: " + (this.currentTask.getAllowedMoves() - 1));
		spell1.setText("Cost: " + wizard.getSpells().get(0).getCost() + " CoolDown "
				+ wizard.getSpells().get(0).getCoolDown());
		spell2.setText("Cost: " + wizard.getSpells().get(1).getCost() + " CoolDown "
				+ wizard.getSpells().get(1).getCoolDown());
		spell3.setText("Cost: " + wizard.getSpells().get(2).getCost() + " CoolDown "
				+ wizard.getSpells().get(2).getCoolDown());

	}

	public void showDragonFire(int i, int j, int x, int y) {
		// Fire.gif
		// URL url = this.getClass().getResource("/resources/Dragon_Fire.gif");
		Icon icon = Main_Menu.getIcons().get(21);
		grid[i][j].setIcon(icon);
		grid[x][y].setIcon(icon);

	}

	public void resetGrid() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j].setIcon(getIconFromMap(grid[i][j], i, j));
			
				if (this.currentTask.getMap()[i][j] instanceof ObstacleCell) {
					ObstacleCell cell = (ObstacleCell) this.currentTask.getMap()[i][j];
						grid[i][j].setToolTipText("HP: " + cell.getObstacle().getHp());
			

				}else
					grid[i][j].setToolTipText(null);


			}
		}

	}

	public int returnEquvilantname(String name) {
		switch (name) {
		case "Cell.png":
			return 0;
		case "EggCell.png":
			return 1;
		case "Mer.png":
			return 2;
		case "physical.png":
			return 3;
		case "Wall.png":
			return 4;
		case "G1.png":
			return 5;
		case "G2.png":
			return 6;
		case "G3.png":
			return 7;
		case "G4.png":
			return 8;
		case "H1.png":
			return 9;
		case "H2.png":
			return 10;
		case "H3.png":
			return 11;
		case "H4.png":
			return 12;
		case "S1.png":
			return 13;
		case "S2.png":
			return 14;
		case "S3.png":
			return 15;
		case "S4.png":
			return 16;
		case "R1.png":
			return 17;
		case "R2.png":
			return 18;
		case "R3.png":
			return 19;
		case "R4.png":
			return 20;
		default:
			return 0;
		}
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public JPanel getGridPanel() {
		return gridPanel;
	}

	public JComboBox<Object> getPotions() {
		return potions;
	}
}