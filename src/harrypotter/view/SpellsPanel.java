package harrypotter.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;

/**
 * TableDemo is just like SimpleTableDemo, except that it uses a custom
 * TableModel.
 */
public class SpellsPanel extends JPanel{
	private JFrame frame;
	private JTable table ;
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		
//		if(SpelllsTable.getCount()!=3 ){
//			
//			//((SpelllsTable)table).getCount()
//			
//			JOptionPane.showMessageDialog(null, "You should choose 3 spells!", "Warning",
//					JOptionPane.ERROR_MESSAGE);
//			}else{
//				frame.setVisible(false);
//				frame.dispose(); 
//				}
//		
//	}
	private ArrayList<Spell> spells;
	@SuppressWarnings("static-access")
	public void reset(){
		((SpelllsTable)table.getModel()).resetSpells();
		((SpelllsTable)table.getModel()).setValues(spells);
		((SpelllsTable)table.getModel()).setCount();
	}
	public SpellsPanel(ArrayList<Spell> spells) {
		super(new GridLayout(1, 0));

		 table = new JTable(new SpelllsTable(spells));
		 this.spells = spells;
		 table.setBackground(	new Color(58, 0, 0));
		 table.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		 table.setForeground(new Color(150, 145, 137));
		 
		 table.setShowGrid(false);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		}

	
	public  void createAndShowGUI(ArrayList<Spell> spells) {
//		 frame = new JFrame("Spells");
//		frame.setBounds(50, 50, 980, 500);
//
//
//		// Create and set up the content pane.
//		SpellsPanel newContentPane = new SpellsPanel(spells);
//		
//		frame.setLayout(new BorderLayout());
//		frame.add(newContentPane,BorderLayout.CENTER);
//		JButton next= new JButton();
//		next.setText("Choose");
//		next.setPreferredSize(new Dimension(10, 40));
//		frame.add(next,BorderLayout.SOUTH);
//		next.addActionListener(this);
//		frame.setVisible(true);

	}


	
}