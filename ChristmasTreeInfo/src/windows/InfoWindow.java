package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import christmastreeinfo.Lang;

public class InfoWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4564265207005050986L;
	
	public InfoWindow(String text, String title) {
		this(text, title, (InfoWindowButton[]) null);
	}
	public InfoWindow(String text, String title, InfoWindowButton... buttons) {
		JFrame me = this;
		if(buttons == null || buttons.length == 0) {
			buttons = new InfoWindowButton[1];
			buttons[0] = new InfoWindowButton(Lang.OK, null, true);
		}
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		String msg[] = text.split("\n");
		for(String s : msg) {
			JLabel jt = new JLabel(s);
			jp.add(jt);
		}
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		for(InfoWindowButton b : buttons) {
			JButton button = new JButton(b.getText());
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(b.hasRun()) {
						b.getRun().run();
					}
					if(b.isClose()) {
						me.dispose();
					}
				}
			});
			buttonPanel.add(button);
		}
		jp.add(buttonPanel);
		this.add(jp);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}
