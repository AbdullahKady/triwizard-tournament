package harrypotter.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import harrypotter.model.magic.Spell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.Merperson;
import harrypotter.model.world.ObstacleCell;
import harrypotter.model.world.WallCell;

public class Main_Menu extends JFrame  {
	private JPanel bottomPanel;
	private JLabel nameLabel;
	private JTextField editableName;
	private JButton next;
	private Icon iconArow;
	private JLabel houseLabel;
	private JLabel houseLabelName;
	private JLabel tempPanel;
	private JPanel upperPanel;
	private JPanel centeralChampPanel;
	private JLabel champLabel;
	private Icon iconChamp;
	private JButton btnLeft;
	private Icon iconLeft;
	private JButton btnRight;
	private Icon iconRight;
	private JPanel champInfo;
	private JLabel champInfoLabel;
	private JPanel spellPanel;
	private JPanel Title;
	private ArrayList<JCheckBox> boxes;
	private int indexChamp = 0;
	private SpellsPanel newContentPane;
	private JPanel fillPanel;
	private JPanel fillPanel2;
	private static ArrayList<ImageIcon> icons;

	public void windowClosing(WindowEvent e) {
	}

	public Main_Menu(ActionListener l, ArrayList<Spell> spells) {
		setTitle("Menu");
		JFrame frame = this;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, "Are you sure to close this window?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null,
							"Bye!", "",
							JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
			}
		});

		int x = Toolkit.getDefaultToolkit().getScreenSize().width;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, x, y);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setUndecorated(false);
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout());

		nameLabel = new JLabel();
		nameLabel.setText(" Name: ");
		nameLabel.setForeground(Color.yellow);

		nameLabel.setSize(new Dimension(100, 100));
		nameLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		// nameLabel.setBorder(new LineBorder(Color.BLACK));

		editableName = new JTextField();
		editableName.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		editableName.setColumns(10);

		next = new JButton();
		next.setName("Next");
		next.setText("NEXT CHAMPION");
		next.setSize(new Dimension(30, 30));
		next.addActionListener(l);

		// Icon iconArow = getIcon("arrowRight.png", next);
		// next.setIcon(iconArow);
		next.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		bottomPanel.add(nameLabel);
		bottomPanel.add(editableName);
		bottomPanel.add(next);
		add(bottomPanel, BorderLayout.SOUTH);

		houseLabel = new JLabel();
		houseLabel.setText("House:   ");
		houseLabel.setForeground(Color.yellow);

		houseLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		houseLabelName = new JLabel();
		houseLabelName.setText("Gryffindor");
		houseLabelName.setForeground(Color.yellow);

		houseLabelName.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));

		tempPanel = new JLabel(); // to occupy somespace
		tempPanel.setPreferredSize(new Dimension(200, 20));

		upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout());
		upperPanel.add(tempPanel, FlowLayout.LEFT);
		upperPanel.add(houseLabelName, FlowLayout.LEFT);
		upperPanel.add(houseLabel, FlowLayout.LEFT);

		centeralChampPanel = new JPanel();
		centeralChampPanel.setLayout(new BorderLayout());



		btnLeft = new JButton();
		btnLeft.setName("Left");
		btnLeft.setSize(new Dimension(80, 10));
		iconLeft = getIcon("arrowLeft.png", btnLeft);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		btnLeft.setBorder(emptyBorder);
		btnLeft.setIcon(iconLeft);

		btnRight = new JButton();
		btnRight.setName("Right");
		btnRight.setSize(new Dimension(80, 10));
		Icon iconRight = getIcon("arrowRight.png", btnRight);

		emptyBorder = BorderFactory.createEmptyBorder();
		btnRight.setBorder(emptyBorder);
		btnRight.setIcon(iconRight);

		btnLeft.addActionListener(l);
		btnRight.addActionListener(l);

		champInfo = new JPanel();
		champInfo.setLayout(new BorderLayout());
		champInfo.setPreferredSize(new Dimension(50, 50));
		champInfoLabel = new JLabel();
		champInfoLabel.setText("          HP: 900" + "   IP: 500");
		champInfoLabel.setForeground(Color.yellow);
		champInfoLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		champInfo.add(champInfoLabel, BorderLayout.NORTH);

		centeralChampPanel.add(champInfo, BorderLayout.SOUTH);
		centeralChampPanel.add(btnRight, BorderLayout.EAST);
		centeralChampPanel.add(btnLeft, BorderLayout.WEST);
		centeralChampPanel.add(upperPanel, BorderLayout.NORTH);
		add(centeralChampPanel, BorderLayout.CENTER);

		spellPanel = new JPanel();
		spellPanel.setLayout(new BorderLayout());
		newContentPane = new SpellsPanel(spells);
		spellPanel.setPreferredSize(new Dimension(820, 430));
		fillPanel = new JPanel();
		fillPanel.setPreferredSize(new Dimension(200, 120));
		fillPanel2 = new JPanel();
		fillPanel2.setPreferredSize(new Dimension(200, 50));

		fillPanel.setBackground(new Color(58, 0, 0));
		fillPanel2.setBackground(new Color(58, 0, 0));

		spellPanel.add(newContentPane, BorderLayout.CENTER);
		//spellPanel.add(fillPanel, BorderLayout.SOUTH);
		spellPanel.add(fillPanel2, BorderLayout.NORTH);


		add(spellPanel, BorderLayout.EAST);

		Title = new JPanel();
		Title.setPreferredSize(new Dimension(100, 200));
		add(Title, BorderLayout.NORTH);
		
		champLabel = new JLabel();
		champLabel.setSize(new Dimension(300, 350));
		iconChamp = getIconlabel("GryffindorHouse.png", champLabel);
		champLabel.setIcon(iconChamp);
		centeralChampPanel.add(champLabel, BorderLayout.CENTER);

		centeralChampPanel.setBackground(new Color(58, 0, 0));
		champInfo.setBackground(new Color(58, 0, 0));
		spellPanel.setBackground(new Color(58, 0, 0));
		upperPanel.setBackground(new Color(58, 0, 0));
		bottomPanel.setBackground(new Color(58, 0, 0));
		Title.setBackground(new Color(58, 0, 0));
		JLabel menuLabel = new JLabel();
		menuLabel.setSize(new Dimension(  (int)(getWidth()/1.5 ) , 170));
		ImageIcon image = getIconlabel("menu2.png", menuLabel);
		menuLabel.setIcon(image);
		Title.add(menuLabel);

		icons = new ArrayList<ImageIcon>();
		intializeIcons();
		// bottomPanel.setBackground(Color.red);
		setVisible(true);
		setFocusable(true);
		this.requestFocus();
		requestFocusInWindow();

	}

	public ImageIcon getIcon(String path, JButton label) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}

	public ImageIcon getIconlabel(String path, JLabel label) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}

	public void clearFrame() {

		this.editableName.setText("");
		checkIndexChamp(0);
		this.validate();
		this.indexChamp = 0;
		newContentPane.reset();
		this.repaint();
		newContentPane.repaint();

	}

	public void right() {
		if (indexChamp <= 2) {
			this.indexChamp++;
			this.checkIndexChamp(indexChamp);
		}
	}

	public void left() {
		if (indexChamp >= 1) {
			this.indexChamp--;
			this.checkIndexChamp(indexChamp);
		}
	}

	public void checkIndexChamp(int indexChamp) {
		String index = "";

		champLabel.setSize(new Dimension(300, 350));

		switch (indexChamp) {
		case 0:

			houseLabelName.setText("Gryffindor");
			String name = "GryffindorHouse" + ".png";
			iconChamp = getIconlabel(name, champLabel);
			champLabel.setIcon(iconChamp);
			champInfoLabel.setText("       HP: 900" + " IP: 500");

			break;
		case 1:
			houseLabelName.setText("Hufflepuff");
			String name2 = "HufflepuffHouse" + ".png";
			iconChamp = getIconlabel(name2, champLabel);

			champLabel.setIcon(iconChamp);
			champInfoLabel.setText("       HP: 850" + " IP: 550");

			break;
		case 2:
			houseLabelName.setText("RavenClaw");
			String name3 = "ravenclaw House" + ".png";
			iconChamp = getIconlabel(name3, champLabel);
			champLabel.setIcon(iconChamp);
			champInfoLabel.setText("       HP: 1000" + " IP: 450");

			break;
		case 3:
			houseLabelName.setText("Slytherin");
			String name4 = "slytherinHOUSE" + ".png";
			iconChamp = getIconlabel(name4, champLabel);
			champLabel.setIcon(iconChamp);
			champInfoLabel.setText("       HP: 750" + " IP: 700");

			break;

		}
	}

	public String getWizardType() {
		return this.houseLabelName.getText();
	}

	public ArrayList<JCheckBox> getBoxes() {
		return boxes;
	}

	public JTextField getEditableName() {
		return editableName;
	}

	public void intializeIcons() {
		ArrayList<String> paths = new ArrayList<String>();
		paths.add("Cell.png");
		paths.add("EggCell.png");
		paths.add("Mer.png");
		paths.add("physical.png");
		paths.add("Wall.png");
		paths.add("G1.png");
		paths.add("G2.png");
		paths.add("G3.png");
		paths.add("G4.png");
		paths.add("H1.png");
		paths.add("H2.png");
		paths.add("H3.png");
		paths.add("H4.png");
		paths.add("S1.png");
		paths.add("S2.png");
		paths.add("S3.png");
		paths.add("S4.png");
		paths.add("R1.png");
		paths.add("R2.png");
		paths.add("R3.png");
		paths.add("R4.png");
		paths.add("fire1.png");
		paths.add("fire2.png");

		for (int i = 0; i < paths.size(); i++)
			icons.add(getIcon(paths.get(i), TaskView.returnLabelSize()));

	}

	public ImageIcon getIcon(String path, Dimension d) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}

	public static ArrayList<ImageIcon> getIcons() {
		return icons;
	}


}
