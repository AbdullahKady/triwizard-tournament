package harrypotter.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;

public class SpelllsTable extends AbstractTableModel {
	String[] columnNames = { "Spell Type", "Name", "Cost", "Damaging Amount", "Healing Amount", "Range", "CoolDown",
			"USE" };

	public SpelllsTable(ArrayList<Spell> spells) {
		super();
		spellsArray = new ArrayList<Spell>();

		setValues(spells);
		counter = 0;
	}

	private static int counter;
	private static ArrayList<Spell> spellsArray;
	private Object[][] data = new Object[21][5];

	public int getColumnCount() {
		return columnNames.length;
	}

	public static int getCount() {

		return counter;
	}

	public void setCount() {

		counter = 0;
	}

	public static ArrayList<Spell> returnSpells() {
		return spellsArray;
	}

	public static void resetSpells() {
		spellsArray = null;

		spellsArray = new ArrayList<Spell>();
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {

		return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void addToSpells(int row) {

		String type = (String) data[row][0];
		String name = (String) data[row][1];
		int cost = (int) data[row][2];
		int damage = (int) data[row][3];
		int heal = (int) data[row][4];
		int relocate = (int) data[row][5];
		int deafultCoolDown = (int) data[row][6];

		if (type.equals("Damage")) {
			spellsArray.add(new DamagingSpell(name, cost, deafultCoolDown, damage));
		} else if (type.equals("Heal")) {
			spellsArray.add(new HealingSpell(name, cost, deafultCoolDown, heal));

		} else if (type.equals("Relocate")) {
			spellsArray.add(new RelocatingSpell(name, cost, deafultCoolDown, relocate));

		}
	}

	public void removeSpell(int row) {
		String name = (String) data[row][1];
		for (int i = 0; i < spellsArray.size(); i++) {
			if (spellsArray.get(i).getName().equals(name))
				spellsArray.remove(spellsArray.get(i));

		}

	}

	public boolean isCellEditable(int row, int col) {

		if (col == 7) {
			if ((boolean) this.getValueAt(row, col) == false && counter < 3) {
				this.addToSpells(row);
				counter++;
				setValueAt(true, row, col);

			}

			else if (counter > 0 && (boolean) this.getValueAt(row, col) == true) {
				this.removeSpell(row);
				counter--;

				setValueAt(false, row, col);

			}

			else {
				JOptionPane.showMessageDialog(null, "You should choose 3 spells! ", "Warning",
						JOptionPane.ERROR_MESSAGE);

			}

		}
		return false;

	}

	public void setValues(ArrayList<Spell> spells) {
		data = new Object[21][5];
		for (int i = 0; i < 21; i++) {
			Spell s = spells.get(i);
			String spellType = "";
			if (s instanceof DamagingSpell) {
				spellType = "Damage";
				data[i] = new Object[] { spellType, s.getName(), s.getCost(), ((DamagingSpell) s).getDamageAmount(), 0,
						0, s.getDefaultCooldown(), false };
			} else if (s instanceof HealingSpell) {
				spellType = "Heal";
				data[i] = new Object[] { spellType, s.getName(), s.getCost(), 0, ((HealingSpell) s).getHealingAmount(),
						0, s.getDefaultCooldown(), false };

			} else if (s instanceof RelocatingSpell) {
				spellType = "Relocate";
				data[i] = new Object[] { spellType, s.getName(), s.getCost(), 0, 0, ((RelocatingSpell) s).getRange(),
						s.getDefaultCooldown(), false };

			}

		}
	}

	public void setValueAt(Object value, int row, int col) {

		data[row][col] = value;

		fireTableCellUpdated(row, col);

	}

}
