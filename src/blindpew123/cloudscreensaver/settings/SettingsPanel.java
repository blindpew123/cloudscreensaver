package blindpew123.cloudscreensaver.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel {
	
	private JTextField textField;
	private JFormattedTextField timeOutBetween;
	private SettingsFile settingsFile;
	private ImageFileListReadersManager readersManager;
	private JCheckBox chckbxShowFilenames;
	private JCheckBox chckbxShowExif;
	private JFrame parent;
	
	public SettingsPanel(JFrame parent, ImageFileListReadersManager readersManager) {
		this.parent = parent;
		this.settingsFile = SettingsFile.getInstance();
		this.readersManager = readersManager;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[] {10, 10, 15, 25, 25};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.25, 0.1, 0.15, 0.25, 0.25};
		setLayout(gridBagLayout);
		
		JPanel pathFieldPanel = new JPanel();
		pathFieldPanel.setLayout(new BoxLayout(pathFieldPanel, BoxLayout.LINE_AXIS));

		JLabel pathLabel = new JLabel(settingsFile.getResource("pathName"));
		pathLabel.setAlignmentX(0.5f);
		pathFieldPanel.add(pathLabel);
		
		Component rigidArea = Box.createRigidArea(new Dimension(7, 0));
		rigidArea.setBackground(Color.LIGHT_GRAY);
		pathFieldPanel.add(rigidArea);
		
		textField = new JTextField();
		textField.setColumns(50);
		textField.setText(settingsFile.getSettingsStringValue("pathsValues")); 
		textField.setToolTipText(settingsFile.getSettingsStringValue("textFilesPathsTooltip")); 
		pathFieldPanel.add(textField); 
		
		GridBagConstraints gbc_pathFieldPanel = new GridBagConstraints();
		gbc_pathFieldPanel.gridy = 0;
		gbc_pathFieldPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pathFieldPanel.insets = new Insets(0, 5, 5, 0);
		gbc_pathFieldPanel.anchor = GridBagConstraints.WEST;
		gbc_pathFieldPanel.gridwidth = 3;
		gbc_pathFieldPanel.gridx = 0;
		add(pathFieldPanel, gbc_pathFieldPanel);
		
		JLabel lblWarning = new JLabel("");
		lblWarning.setForeground(Color.RED);
		lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblWarning.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblWarning = new GridBagConstraints();
		gbc_lblWarning.fill = GridBagConstraints.VERTICAL;
		gbc_lblWarning.anchor = GridBagConstraints.EAST;
		gbc_lblWarning.gridwidth = 2;
		gbc_lblWarning.insets = new Insets(0, 0, 5, 0);
		gbc_lblWarning.gridx = 1;
		gbc_lblWarning.gridy = 1;
		add(lblWarning, gbc_lblWarning);
		
		JPanel timeOutpanel = new JPanel();
		GridBagConstraints gbc_timeOutpanel = new GridBagConstraints();
		gbc_timeOutpanel.gridwidth = 2;
		gbc_timeOutpanel.insets = new Insets(0, 0, 0, 5);
		gbc_timeOutpanel.fill = GridBagConstraints.BOTH;
		gbc_timeOutpanel.gridx = 0;
		gbc_timeOutpanel.gridy = 2;
		add(timeOutpanel, gbc_timeOutpanel);
		timeOutpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		Label label = new Label(settingsFile.getResource("imagesTimeoutLabel"));
		timeOutpanel.add(label);
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
		timeOutBetween = new JFormattedTextField(formatter);
		timeOutBetween.setText(settingsFile.getSettingsStringValue("imagesTimeOut").isEmpty() ? "20":settingsFile.getSettingsStringValue("imagesTimeOut"));
		timeOutBetween.setColumns(2);
		timeOutpanel.add(timeOutBetween);
		
		JPanel checkBoxesPanel = new JPanel();
		checkBoxesPanel.setLayout(new BoxLayout(checkBoxesPanel, BoxLayout.Y_AXIS));
		checkBoxesPanel.setBorder(BorderFactory.createEmptyBorder());
		
		chckbxShowFilenames = new JCheckBox(settingsFile.getResource("showFileNames"));
		chckbxShowFilenames.setSelected(settingsFile.getSettingsValue("showFileNamesValue").equals("true"));
		checkBoxesPanel.add(chckbxShowFilenames);
		
		chckbxShowExif = new JCheckBox(settingsFile.getResource("showExif"));
		chckbxShowExif.setSelected(settingsFile.getSettingsValue("showExifValue").equals("true"));
		checkBoxesPanel.add(chckbxShowExif);
		
		GridBagConstraints gbc_checkBoxesPanel = new GridBagConstraints();
		gbc_checkBoxesPanel.anchor = GridBagConstraints.WEST;
		
		gbc_checkBoxesPanel.gridwidth = 2;
		gbc_checkBoxesPanel.gridheight = 2;
		
		gbc_checkBoxesPanel.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxesPanel.gridx = 0;
		gbc_checkBoxesPanel.gridy = 3;
		add(checkBoxesPanel, gbc_checkBoxesPanel);
		
		JButton btnOk = new JButton("OK");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.insets = new Insets(0, 0, 5, 0);
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 3;
		
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] imagePaths = textField.getText().split(";");
				for(String path:imagePaths) {
					if (!readersManager.isReaderAvailable(new ImagePath(path,true))) {  // Can't parse this path, so user must remove it.
						lblWarning.setText(settingsFile.getResource("textWarning")+" "+path);
						return; 
					}
				}
				saveSettings();
				parent.dispose();
				
			}			
		});
		
		add(btnOk, gbc_btnOk);
		
		JButton btnCancel = new JButton(settingsFile.getResource("cancelText"));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 4;
		btnCancel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.dispose();				
			}
		});
		add(btnCancel, gbc_btnCancel);	
	}

	private void saveSettings() {
		Properties properties = new Properties();
		properties.setProperty("showFileNamesValue", Boolean.toString(chckbxShowFilenames.isSelected()));
		properties.setProperty("showExifValue",Boolean.toString(chckbxShowExif.isSelected()));
		properties.setProperty("pathsValues", textField.getText());
		properties.setProperty("imagesTimeOut",timeOutBetween.getText());
		settingsFile.saveSettings(properties);
	}
}
