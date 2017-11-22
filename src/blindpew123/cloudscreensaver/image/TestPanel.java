package blindpew123.cloudscreensaver.image;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JLabel;

public class TestPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html> <style>" + 
				"   p:{" + 
				"    text-shadow: 1px 1px 2px black, 0 0 1em red;" + 
				"    color: white; " + 
				"    font-size: 2em;" + 
				"   }" + 
				"  </style> <br>" +
	               "<p \"style='color:red'\">это наш текст <br>" +
	               "вот ещё одна строка<br>" +
	               "Жизнь прекрасна))<br></html>");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(76, 201, 364, 88);
		add(lblNewLabel);

	}
}
