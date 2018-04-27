package harrypotter.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.InvalidTargetCellException;
import harrypotter.exceptions.NotEnoughIPException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.exceptions.OutOfRangeException;
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
import harrypotter.model.tournament.Tournament;
import harrypotter.model.world.Direction;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.WallCell;
import harrypotter.view.SpelllsTable;
import harrypotter.view.TaskView;

public class HarrypotterGUI_Task implements ActionListener, KeyListener {
	private TaskView taskView;
	private Task currentTask;
	private Tournament tournamet;

	private ArrayList<String> champNames;
	private ArrayList<Integer> champHealths;
	private ArrayList<Integer> numberOfPotions;
	private ArrayList<Integer> champIPS;

	private ArrayList<Wizard> wizards;

	private ArrayList<Point> markedPoints;

	public HarrypotterGUI_Task(Tournament tournamet) {
		this.tournamet = tournamet;

		champNames = new ArrayList<String>();
		champHealths = new ArrayList<Integer>();
		wizards = new ArrayList<Wizard>();

		this.currentTask = tournamet.getFirstTask();
		taskView = new TaskView(this.currentTask, this, this);
		resetChampStatus();

	}

	public void resetChampStatus() {
		champNames = new ArrayList<String>();
		champHealths = new ArrayList<Integer>();
		wizards = new ArrayList<Wizard>();
		numberOfPotions = new ArrayList<Integer>();
		champIPS = new ArrayList<Integer>();
		for (int i = 0; i < currentTask.getChampions().size(); i++) {
			champNames.add(((Wizard) currentTask.getChampions().get(i)).getName());
			champHealths.add(((Wizard) currentTask.getChampions().get(i)).getHp());
			numberOfPotions.add(((Wizard) currentTask.getChampions().get(i)).getInventory().size());
			wizards.add((Wizard) currentTask.getChampions().get(i));
			champIPS.add(((Wizard) currentTask.getChampions().get(i)).getIp());

		}

	}

	public void prepareForFire() {

		markedPoints = new ArrayList<Point>();

		if (currentTask instanceof FirstTask) {

			markedPoints = new ArrayList<Point>();
			Point first = ((FirstTask) currentTask).getMarkedCells().get(0);
			Point second = ((FirstTask) currentTask).getMarkedCells().get(1);

			markedPoints.add(new Point(first.x, first.y));
			markedPoints.add(new Point(second.x, second.y));

		}

	}

	public void showGameOver() {
		if (this.currentTask instanceof FirstTask) {
			if (((FirstTask) this.currentTask).getWinners().size() == 0
					&& ((FirstTask) this.currentTask).getChampions().size() == 0) {
				JOptionPane.showMessageDialog(null, "GameOver!", "", JOptionPane.INFORMATION_MESSAGE);
				this.taskView.dispose();
			}
		} else if (this.currentTask instanceof SecondTask) {
			if (((SecondTask) this.currentTask).getWinners().size() == 0
					&& ((SecondTask) this.currentTask).getChampions().size() == 0) {
				JOptionPane.showMessageDialog(null, "GameOver!", "", JOptionPane.INFORMATION_MESSAGE);
				this.taskView.dispose();
			}

		} else if (this.currentTask instanceof ThirdTask) {
			if (((ThirdTask) this.currentTask).getChampions().size() == 0) {
				JOptionPane.showMessageDialog(null, "GameOver!", "", JOptionPane.INFORMATION_MESSAGE);
				this.taskView.dispose();
			}
		}
	}

	public void showLoss() {
		for (int i = 0; i < this.wizards.size(); i++) {
			if (!this.currentTask.getChampions().contains(wizards.get(i))
					|| this.currentTask.getChampions().size() == 0) {

				if (this.currentTask instanceof FirstTask) {
					if (!(((FirstTask) this.currentTask).getWinners().contains(wizards.get(i)))) {
						this.taskView.showHPZero();

						JOptionPane.showMessageDialog(null, "Wizard " + wizards.get(i).getName() + " Has Died.", "",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else if (this.currentTask instanceof SecondTask) {
					if (!(((SecondTask) this.currentTask).getWinners().contains(wizards.get(i)))) {
						this.taskView.showHPZero();

						JOptionPane.showMessageDialog(null, "Wizard " + wizards.get(i).getName() + " Has Died.", "",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} else if (this.currentTask instanceof ThirdTask) {
					if (!(((ThirdTask) this.currentTask)).getChampions().contains(wizards.get(i))) {
						this.taskView.showHPZero();

						JOptionPane.showMessageDialog(null, "Wizard " + wizards.get(i).getName() + " Has Died.", "",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}

			}

		}
	}

	public void showWin() {

		for (int i = 0; i < this.wizards.size(); i++) {
			if (!this.currentTask.getChampions().contains(wizards.get(i))
					|| this.currentTask.getChampions().size() == 0) {

				if (this.currentTask instanceof FirstTask) {
					if (((FirstTask) this.currentTask).getWinners().contains(wizards.get(i)))
						JOptionPane.showMessageDialog(null,
								"Wizard " + wizards.get(i).getName() + " Has procceded to the Second Task.", "",
								JOptionPane.INFORMATION_MESSAGE);

				} else if (this.currentTask instanceof SecondTask) {
					if (((SecondTask) this.currentTask).getWinners().contains(wizards.get(i)))
						JOptionPane.showMessageDialog(null,
								"Wizard " + wizards.get(i).getName() + " Has procceded to the Third Task.", "",
								JOptionPane.INFORMATION_MESSAGE);

				}

			}

		}
	}

	public void showPotion() {
		for (int i = 0; i < this.currentTask.getChampions().size(); i++) {
			Wizard wizard = (Wizard) this.currentTask.getChampions().get(i);
			for (int j = 0; j < this.champNames.size(); j++) {
				if (champNames.get(j).equals(wizard.getName())) {
					if (numberOfPotions.get(j) < wizard.getInventory().size()) {
						JOptionPane.showMessageDialog(null, "Wizard " + wizard.getName() + " has got a new Potion.", "",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}

	public void showFire() {

		if (this.currentTask instanceof FirstTask && !(this.currentTask.isTraitActivated()
				&& ((Wizard) this.currentTask.getCurrentChamp() instanceof RavenclawWizard))) {
			JOptionPane.showMessageDialog(null, "The Dragon Will Fire", "", JOptionPane.INFORMATION_MESSAGE);

			Point p1 = markedPoints.get(0);
			Point p2 = markedPoints.get(1);

			this.taskView.showDragonFire(p1.x, p1.y, p2.x, p2.y);

		}

	}

	public void showFire2() {

		if (this.currentTask instanceof FirstTask) {
			Point p1 = markedPoints.get(0);
			Point p2 = markedPoints.get(1);

			this.taskView.showDragonFire(p1.x, p1.y, p2.x, p2.y);
			JOptionPane.showMessageDialog(null, "The Dragon Has Fired", "", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	public void showHealing() {

		boolean safe = true;
		for (int i = 0; i < this.currentTask.getChampions().size(); i++) {
			Wizard wizard = (Wizard) this.currentTask.getChampions().get(i);
			if (champNames.get(0).equals(wizard.getName())) {
				if (champHealths.get(0) >= wizard.getHp()) {
					safe = false;
				}

			}
		}

		for (int i = 0; i < this.currentTask.getChampions().size(); i++) {
			Wizard wizard = (Wizard) this.currentTask.getChampions().get(i);
			if (champNames.get(0).equals(wizard.getName()) && safe) {
				this.taskView.changeMapHPandIP(wizard, 0);

				JOptionPane.showMessageDialog(null,
						"Wizard " + wizard.getName() + " HP Is Increased to " + (wizard.getHp()), "",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (champNames.get(0).equals(wizard.getName()) && !safe) {
				this.taskView.changeMapHPandIP(wizard, 150);

				JOptionPane.showMessageDialog(null,
						"Wizard " + wizard.getName() + " HP Is Increased to " + (wizard.getHp() + 150), "",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void changeInfo() {
		boolean safe = true;
		//
		for (int i = 0; i < this.currentTask.getChampions().size(); i++) {
			Wizard wizard = (Wizard) this.currentTask.getChampions().get(i);
			if (champNames.get(0).equals(wizard.getName())) {

				this.taskView.changeMapIP(wizard);

			}
		}
	}

	public void showDamage() {

		boolean safe = true;
		//
		for (int i = 0; i < this.currentTask.getChampions().size(); i++) {
			Wizard wizard = (Wizard) this.currentTask.getChampions().get(i);
			for (int j = 0; j < this.champNames.size(); j++) {
				if (champNames.get(j).equals(wizard.getName())) {
					int x = 0;
					if (healCheck && this.currentTask instanceof FirstTask
							&& champNames.get(0).equals(wizard.getName()))
						x = 150;
					// DAS IST SHIT
					if (champHealths.get(j) + x > wizard.getHp() && champHealths.get(j) != 0) {
						
						if (j == 0)
							this.taskView.changeMapHPandIP(wizard, 0);
						JOptionPane.showMessageDialog(null,
								"Wizard " + wizard.getName() + " HP was decreased to " + wizard.getHp(), "",
								JOptionPane.INFORMATION_MESSAGE);

						safe = false;
					}

				}
			}
		}
		if (safe && (champNames.size() == this.currentTask.getChampions().size())
				&& !this.currentTask.isTraitActivated() && this.currentTask.getAllowedMoves() == 1
				&& !(this.currentTask instanceof ThirdTask)) {
			JOptionPane.showMessageDialog(null, "Wizard " + champNames.get(0) + " Wasn't Damaged!", "",
					JOptionPane.INFORMATION_MESSAGE);
		}

		this.taskView.resetGrid();
		healCheck = false;
	}

	public void nextMap() {

		if (this.currentTask instanceof FirstTask) {
			this.taskView.changeMapInfo();
			JOptionPane.showMessageDialog(null, "Task Two Will Begin", "Next Task", JOptionPane.INFORMATION_MESSAGE);
			this.taskView.setCurrentTask(this.tournamet.getSecondTask());
			this.currentTask = this.tournamet.getSecondTask();
		} else if (this.currentTask instanceof SecondTask) {
			changeInfo();
			JOptionPane.showMessageDialog(null, "Task Three Will Begin", "Next Task", JOptionPane.INFORMATION_MESSAGE);
			this.taskView.setCurrentTask(this.tournamet.getThirdTask());
			this.currentTask = this.tournamet.getThirdTask();
		}
		this.taskView.resetGrid();
		this.taskView.changeMapInfo();

	}

	public void updateMap() {
		changeInfo();

		this.taskView.resetGrid();
		this.tournamet.endGame();
		showPotion();

		showWin();

		if (this.currentTask instanceof FirstTask)
			if (this.currentTask.getChampions().size() == 0
					&& ((FirstTask) this.currentTask).getWinners().size() != 0) {
				showFire2();
				showDamage();
				showLoss();

				this.nextMap();
				resetChampStatus();

				return;
			}

		if (this.currentTask instanceof SecondTask)
			if (this.currentTask.getChampions().size() == 0
					&& ((SecondTask) this.currentTask).getWinners().size() != 0) {
				showDamage();
				showLoss();

				this.nextMap();
				resetChampStatus();

				return;
			}

		if ((this.currentTask.getChampions().size() == 0)
				|| (this.currentTask.isTurnEnded() && this.currentTask.getAllowedMoves() == 1)) {

			if (champNames.size() == this.currentTask.getChampions().size())
				showFire();
			else if (champNames.size() != this.currentTask.getChampions().size())
				showFire2();
			showDamage();
			showLoss();
			this.taskView.resetGrid();

		} else {
			showDamage();
			showLoss();
			this.taskView.resetGrid();
		}

		showGameOver();

		this.taskView.changeMapInfo();
		resetChampStatus();

	}

	public Direction getMessageDirection(String message) throws Exception {
		Object[] possibilities = { "Up", "Down", "Right", "Left" };

		String inputDirecion = "";
		inputDirecion = (String) JOptionPane.showInputDialog(this.taskView, "DIRECTION ", message,
				JOptionPane.INFORMATION_MESSAGE, null, possibilities, "Forward");

		Direction d = Direction.FORWARD;

		if (inputDirecion.equals("Up"))
			d = Direction.FORWARD;
		else if (inputDirecion.equals("Down"))
			d = Direction.BACKWARD;
		else if (inputDirecion.equals("Right"))
			d = Direction.RIGHT;
		else if (inputDirecion.equals("Left"))
			d = Direction.LEFT;

		return d;
	}

	public String getEquivilentMessage(Direction d) {
		String name = "";
		if (d == Direction.FORWARD)
			name = "Above";
		if (d == Direction.BACKWARD)
			name = "Below";
		if (d == Direction.LEFT)
			name = "Left";
		if (d == Direction.RIGHT)
			name = "Right";
		return name;

	}

	private static boolean healCheck = false;

	public void castspells(Spell spell) {
		prepareForFire();

		if (spell instanceof DamagingSpell) {

			try {
				Direction d = this.getMessageDirection("Choose A Direction");
				currentTask.castDamagingSpell((DamagingSpell) spell, d);
				this.changeInfo();
				this.updateMap();

			} catch (OutOfBordersException e) {
				getErrorMessage(e);
			} catch (InvalidTargetCellException e) {
				getErrorMessage(e);
			} catch (InCooldownException e) {
				getErrorMessage(e);

			} catch (NotEnoughIPException e) {
				getErrorMessage(e);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		if (spell instanceof HealingSpell) {
			try {
				healCheck = true;
				if (((Wizard) this.currentTask.getCurrentChamp())
						.getHp() == ((Wizard) this.currentTask.getCurrentChamp()).getDefaultHp()) {
					JOptionPane.showMessageDialog(null, "Your HP Is Already Full !", "Full Health",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				currentTask.castHealingSpell((HealingSpell) spell);
				this.changeInfo();

				this.showHealing();
				this.updateMap();
			} catch (InCooldownException e) {
				getErrorMessage(e);
			} catch (NotEnoughIPException e) {
				getErrorMessage(e);

			} catch (IOException e) {
				getErrorMessage(e);

			}
		}
		if (spell instanceof RelocatingSpell) {

			try {
				Direction d = this.getMessageDirection("Choose Destation Direction");
				Direction t = this.getMessageDirection("Choose Target Direction");
				int range = 0;
				int raneMessage=1;
				if(((RelocatingSpell) spell).getRange()==raneMessage)
					raneMessage=0;

				String inputDirecion = (String) JOptionPane.showInputDialog(this.taskView, "DIRECTION ",
						"Choose a range between "+raneMessage+" and" + ((RelocatingSpell) spell).getRange(),
						JOptionPane.INFORMATION_MESSAGE);

				range = Integer.parseInt(inputDirecion);
				currentTask.castRelocatingSpell((RelocatingSpell) spell, d, t, range);
				this.changeInfo();

				this.updateMap();
			} catch (InCooldownException e) {
				getErrorMessage(e);
			} catch (NotEnoughIPException e) {
				getErrorMessage(e);

			} catch (OutOfBordersException e) {
				getErrorMessage(e);

			} catch (InvalidTargetCellException e) {
				getErrorMessage(e);

			} catch (OutOfRangeException e) {
				getErrorMessage(e);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		try {
			this.taskView.requestFocus();
			this.taskView.requestFocusInWindow();
			this.taskView.revalidate();
			JButton bttn = (JButton) e.getSource();
			Champion champ = (currentTask.getCurrentChamp());
			Wizard current = (Wizard) (currentTask.getCurrentChamp());

			// JComboBox cb = (JComboBox)e.getSource();
			// String petName = (String)cb.getSelectedItem();
			this.taskView.getGridPanel().revalidate();
			this.taskView.getGridPanel().requestFocus();
			if (bttn.getName() == "skipTurn") {
				prepareForFire();

				this.currentTask.setTurnEnded(false);
				this.currentTask.finalizeAction();
				this.updateMap();

			}
			if (bttn.getName() == "usePotion") {
				this.currentTask.usePotion((Potion) ((Wizard) this.currentTask.getCurrentChamp()).getInventory()
						.get(this.taskView.getPotions().getSelectedIndex()));
				this.taskView.changeMapHPandIP((Wizard) this.currentTask.getCurrentChamp(), 0);

			}

			if (bttn.getName() == "traitButton") {
				if (champ instanceof GryffindorWizard) {
					try {
						currentTask.onGryffindorTrait();
						JOptionPane.showMessageDialog(null, "Gryffindor Trait Is Acticated", "Trait Acticated",
								JOptionPane.INFORMATION_MESSAGE);
						this.taskView.changeMapInfo();
						this.updateMap();

					} catch (InCooldownException e1) {
						getErrorMessage(e1);
					}
				} else if (champ instanceof HufflepuffWizard) {
					try {
						currentTask.onHufflepuffTrait();
						JOptionPane.showMessageDialog(null, "Hufflepuff Trait Is Acticated", "Trait Acticated",
								JOptionPane.INFORMATION_MESSAGE);

					} catch (InCooldownException e1) {
						getErrorMessage(e1);
					}

				} else if (champ instanceof RavenclawWizard) {
					try {
						if (currentTask instanceof FirstTask) {
							@SuppressWarnings("unchecked")
							ArrayList<Point> marked = (ArrayList<Point>) currentTask.onRavenclawTrait();
							JOptionPane.showMessageDialog(null, "Ravenclaw Trait Is Acticated", "Trait Acticated",
									JOptionPane.INFORMATION_MESSAGE);

							Point p1 = marked.get(0);
							Point p2 = marked.get(1);
							this.taskView.showDragonFire(p1.x, p1.y, p2.x, p2.y);
							JOptionPane.showMessageDialog(null, "Dragon Is Going On These Cells!", "HINT",
									JOptionPane.INFORMATION_MESSAGE);
							this.updateMap();
						} else if (currentTask instanceof SecondTask) {

							@SuppressWarnings("unchecked")
							ArrayList<Direction> marked = (ArrayList<Direction>) currentTask.onRavenclawTrait();
							JOptionPane.showMessageDialog(null, "Ravenclaw Trait Is Acticated", "Trait Acticated",
									JOptionPane.INFORMATION_MESSAGE);

							String d1 = getEquivilentMessage(marked.get(0));
							String d2 = getEquivilentMessage(marked.get(1));
							JOptionPane.showMessageDialog(null,
									"Your Treasure Is Located to Your " + d1 + " And To Your " + d2 + " !", "HINT",
									JOptionPane.INFORMATION_MESSAGE);
							this.updateMap();
						} else if (currentTask instanceof ThirdTask) {

							@SuppressWarnings("unchecked")
							ArrayList<Direction> marked = (ArrayList<Direction>) currentTask.onRavenclawTrait();
							JOptionPane.showMessageDialog(null, "Ravenclaw Trait Is Acticated", "Trait Acticated",
									JOptionPane.INFORMATION_MESSAGE);

							String d1 = getEquivilentMessage(marked.get(0));
							String d2 = getEquivilentMessage(marked.get(1));
							JOptionPane.showMessageDialog(null,
									"The Cup Is Located to Your " + d1 + " And To Your " + d2 + " !", "HINT",
									JOptionPane.INFORMATION_MESSAGE);
							this.updateMap();
						}

					} catch (InCooldownException e1) {
						getErrorMessage(e1);
					}

				} else if (champ instanceof SlytherinWizard) {

					try {
						this.prepareForFire();
						Direction d = this.getMessageDirection("Choose A Direction");
						currentTask.onSlytherinTrait(d);
						JOptionPane.showMessageDialog(null, "Slytherin Trait Is Acticated", "Trait Acticated",
								JOptionPane.INFORMATION_MESSAGE);

						this.updateMap();

					} catch (InCooldownException e1) {
						getErrorMessage(e1);

					} catch (OutOfBordersException e1) {
						getErrorMessage(e1);

					} catch (InvalidTargetCellException e1) {
						getErrorMessage(e1);

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						// e1.printStackTrace();
					}

				}
			} else if (bttn.getName() == "spell1Button") {
				Spell spell = current.getSpells().get(0);
				castspells(spell);

			} else if (bttn.getName() == "spell2Button") {
				Spell spell = current.getSpells().get(1);
				castspells(spell);

			} else if (bttn.getName() == "spell3Button") {
				Spell spell = current.getSpells().get(2);
				castspells(spell);

			}
			this.taskView.getGridPanel().revalidate();
			this.taskView.getGridPanel().requestFocus();
			this.taskView.requestFocus();
			this.taskView.requestFocusInWindow();
			this.taskView.revalidate();
		} catch (Exception e1) {

		}
	}

	public void keyPressed(KeyEvent e) {
		this.taskView.requestFocus();
		this.taskView.requestFocusInWindow();
		this.taskView.revalidate();
		prepareForFire();

		Champion champ = (currentTask.getCurrentChamp());
		Wizard current = (Wizard) (currentTask.getCurrentChamp());
		Point p = current.getLocation();
		if (e.getKeyCode() == KeyEvent.VK_UP)
			try {
				this.currentTask.moveForward();

				this.updateMap();

			} catch (OutOfBordersException e1) {
				getErrorMessage(e1);
			} catch (InvalidTargetCellException e1) {
				if (!(this.currentTask.getMap()[p.x - 1][p.y] instanceof WallCell))
					getErrorMessage(e1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			try {
				this.currentTask.moveBackward();

				this.updateMap();

			} catch (OutOfBordersException e1) {
				getErrorMessage(e1);
			} catch (InvalidTargetCellException e1) {
				if (!(this.currentTask.getMap()[p.x + 1][p.y] instanceof WallCell))
					getErrorMessage(e1);
			} catch (IOException e1) {
				e1.printStackTrace();

			}

		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			try {

				this.currentTask.moveLeft();

				this.updateMap();

			} catch (OutOfBordersException e1) {
				getErrorMessage(e1);
			} catch (InvalidTargetCellException e1) {
				if (!(this.currentTask.getMap()[p.x][p.y - 1] instanceof WallCell))
					getErrorMessage(e1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			try {
				this.currentTask.moveRight();

				this.updateMap();

			} catch (OutOfBordersException e1) {
				getErrorMessage(e1);
			} catch (InvalidTargetCellException e1) {
				if (!(this.currentTask.getMap()[p.x][p.y + 1] instanceof WallCell))
					getErrorMessage(e1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

	}

	public static void getErrorMessage(Exception e1) {
		JOptionPane.showMessageDialog(null, e1.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void setCurrentTask(Task curretnTask) {
		this.currentTask = curretnTask;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

}