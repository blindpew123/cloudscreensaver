package blindpew123.cloudscreensaver.display;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;

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
		lblNewLabel.setFont(new Font("Tekton Pro", Font.PLAIN, 12));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(new Rectangle());
		add(lblNewLabel);

	}
}
