package harrypotter.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InductoryMenu extends JFrame{
	public InductoryMenu(){
		// 154 156 67
		int x = Toolkit.getDefaultToolkit().getScreenSize().width;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height;
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setUndecorated(true);
	    URL url = this.getClass().getResource("/resources/Dragon_Fire.gif");
        Icon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
        
		
    label.setSize(x, y);

  
   
    add(label);
this.setVisible(true);
	}
	public static void main(String args[]){
		new InductoryMenu();
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

}
