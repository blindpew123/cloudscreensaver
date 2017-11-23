package blindpew123.cloudscreensaver.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;


class ShadowFormattedTextBlock {
	
	private StringBuilder text = new StringBuilder();
	private JLabel fore = new JLabel();;
	private JLabel back = new JLabel();;
	
	ShadowFormattedTextBlock() {
		
	}
	
	ShadowFormattedTextBlock(String text){
		this.text.append(text);
	}
	
	ShadowFormattedTextBlock(Properties map){
		this.text = htmlFormattedText(map);
	}
			
	ShadowFormattedTextBlock setText(Properties map) {
		this.text = htmlFormattedText(map);
		setTextToLabels();
		return this;
	}
	
	ShadowFormattedTextBlock setText(String text) {
		this.text = new StringBuilder(text);
		setTextToLabels();
		return this;
	}
	
	ShadowFormattedTextBlock setForeColor(Color color) {
		fore.setForeground(color);
		return this;
	}
	
	ShadowFormattedTextBlock setBackColor(Color color) {
		back.setForeground(color);
		return this;
	}
	
	ShadowFormattedTextBlock setFontSize(int size) {
		fore.setFont(new Font("Tahoma", Font.PLAIN, size));
		back.setFont(new Font("Tahoma", Font.PLAIN, size));
		return this;
	}	
	
	ShadowFormattedTextBlock setPosition(Rectangle bound, int hAlignment, int vAlignment) {
		fore.setHorizontalAlignment(hAlignment);
		fore.setVerticalAlignment(vAlignment);
		back.setHorizontalAlignment(hAlignment);
		back.setVerticalAlignment(vAlignment);
		fore.setBounds(bound);
		back.setBounds(new Rectangle(bound.x+1,bound.y+1,bound.width-1,bound.height-1));
		return this;
	}
	
	void placeTo(JPanel panel) {
		setTextToLabels();
		panel.add(fore);
		panel.add(back);
	}
	
	Font getFont() {
		return fore.getFont();
	}

	private StringBuilder htmlFormattedText(Properties map) {
		StringBuilder sb = new StringBuilder("<html>");
		for(Object key : map.keySet()) {
			sb.append(key).append(": ").append(map.get(key)).append("<br>");
		}
		return sb.append("</html>");
	}
	
	private void setTextToLabels() {
		fore.setText(text.toString());
		back.setText(text.toString());
	}
	
	JLabel fore() {
		return fore;
	}
	
	JLabel back() {
		return back;
	}
	
}
