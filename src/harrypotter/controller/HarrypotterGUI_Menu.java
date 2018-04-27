package harrypotter.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import harrypotter.model.character.*;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.tournament.Task;
import harrypotter.model.tournament.Tournament;
import harrypotter.view.Main_Menu;
import harrypotter.view.SpelllsTable;
import harrypotter.view.SpellsPanel;

public class HarrypotterGUI_Menu implements ActionListener {
	private Main_Menu menu;
	private Tournament tournament;
	private static int counterG ;
	private static int counterH ;
	private static int counterR ;
	private static int counterS ;

	public HarrypotterGUI_Menu() throws IOException {
		tournament = new Tournament();
		menu = new Main_Menu(this, tournament.getSpells());
		menu.clearFrame();

	}

	public static void main(String args[]) throws IOException {
		new HarrypotterGUI_Menu();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

			JButton bttn = (JButton) e.getSource();
			if (bttn.getName() == "Left") {
				menu.left();
			} else if (bttn.getName() == "Right")
				menu.right();

			else if (bttn.getName() == "Next") {
				boolean duplicate = false;
				if (SpelllsTable.getCount() == 3 && !menu.getEditableName().getText().equals("")) {
					
					for (int i = 0; i < this.tournament.getChampions().size(); i++) {
						if (((Wizard) this.tournament.getChampions().get(i)).getName()
								.equals(menu.getEditableName().getText())) {
							duplicate = true;
						}
						
					}
					if (duplicate) {
						JOptionPane.showMessageDialog(null,
								"This name is already chosen, Please Choose A Different Name!", "Warning",
								JOptionPane.ERROR_MESSAGE);
					} else {
						this.instializeChamp(menu.getWizardType(), menu.getEditableName().getText());
						menu.clearFrame();
					}

				} else
					JOptionPane.showMessageDialog(null, "Please enter a champion's name, and choose three spells!",
							"Warning", JOptionPane.ERROR_MESSAGE);

				if (this.tournament.getChampions().size() == 3) {
					bttn.setText("Start Tournamet");
				} else if (this.tournament.getChampions().size() == 4) {
					// start the new tourmnemt here
					 menu.setVisible(false);
					 menu.dispose();
					try {
						tournament.beginTournament();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					HarrypotterGUI_Task taskView = new HarrypotterGUI_Task(tournament);

				}
			}
		

	}

	public void instializeChamp(String champ, String name) {

		if (champ.equals("Gryffindor")) {
			GryffindorWizard Gryffindor = new GryffindorWizard(name);
			Gryffindor.setSpells(SpelllsTable.returnSpells());
			this.tournament.addChampion((Champion) Gryffindor);
			Gryffindor.setPictureIndex( updateIndex( Gryffindor));


		} else if (champ.equals("Hufflepuff")) {

			HufflepuffWizard Hufflepuff = new HufflepuffWizard(name);
			Hufflepuff.setSpells(SpelllsTable.returnSpells());
			this.tournament.addChampion((Champion) Hufflepuff);
			Hufflepuff.setPictureIndex( updateIndex( Hufflepuff));


		} else if (champ.equals("RavenClaw")) {
			RavenclawWizard RavenClaw = new RavenclawWizard(name);
			RavenClaw.setSpells(SpelllsTable.returnSpells());

			this.tournament.addChampion((Champion) RavenClaw);
			RavenClaw.setPictureIndex( updateIndex( RavenClaw));

		} else if (champ.equals("Slytherin")) {
			SlytherinWizard Slytherin = new SlytherinWizard(name);
			Slytherin.setSpells(SpelllsTable.returnSpells());
			this.tournament.addChampion((Champion) Slytherin);
			Slytherin.setPictureIndex( updateIndex( Slytherin));
		}

	}

	public String updateIndex(Wizard current) {
		counterG=0;
		counterH=0;
		counterR=0;
		counterS=0;
		
		for (int i = 0; i < this.tournament.getChampions().size(); i++) {
			Wizard currentWizard = ((Wizard)this.tournament.getChampions().get(i));
			if (currentWizard instanceof GryffindorWizard){
				counterG++;
				}
			else if (currentWizard instanceof HufflepuffWizard)
				counterH++;

			else if (currentWizard instanceof RavenclawWizard)
				counterR++;

			else if (currentWizard instanceof SlytherinWizard)
				counterS++;
		}
		String name = "";
		if (current instanceof GryffindorWizard)
			name = "G" + counterG + ".png";
		else if (current instanceof HufflepuffWizard)
			name = "H" + counterH + ".png";

		else if (current instanceof RavenclawWizard)
			name = "R" + counterR + ".png";

		else if (current instanceof SlytherinWizard)
			name = "S" + counterS + ".png";

		return name;

	}

}
