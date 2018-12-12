package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import christmastreeinfo.Lang;
import christmastreeinfo.Main;
import christmastreeinfo.PersonFactory;
import christmastreeinfo.WaitingRoom;
import customer.Customer;
import customer.DataType;

public class CustomerSelectionWindow extends JFrame {

	/**/ private static final long serialVersionUID = 9188929133770395180L;

	private JPanel main;
	
	private JPanel personButtonPanel[];
	private HashMap<UUID, JButton> personButtons;
	
	private JLabel partLabel;
	
	private JPanel controlButtonPanel;
	private JButton nextbutton;
	private JButton prevButton;
	private JButton exitButton;
	
	private int customerStartIndex;
	private int customerSeeSize = 8;
	private int colums = 3;
	
	public CustomerSelectionWindow() {
		this.setTitle("Select Person");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		personButtons = new HashMap<UUID, JButton>();
		personButtonPanel = new JPanel[colums];
		customerStartIndex = 0;
		customerSeeSize = customerSeeSize * colums;
		display();
	}
	
	private void display() {
		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
		partLabel = new JLabel();
		main.add(partLabel);
		updatePartLabel();
		
		JPanel people = new JPanel();
		people.setLayout(new BoxLayout(people, BoxLayout.X_AXIS));
		
		for(int i = 0; i < personButtonPanel.length; i++) {
			personButtonPanel[i] = new JPanel();
			personButtonPanel[i].setLayout(new BoxLayout(personButtonPanel[i], BoxLayout.Y_AXIS));
			people.add(personButtonPanel[i]);
		}
		for(JPanel jp:personButtonPanel) {
			System.out.println(jp);
		}
		
		main.add(people);
		updateButtons();
		
		controlButtonPanel = new JPanel();
		prevButton = new JButton(Lang.PREVIOUS);
		prevButton.addActionListener(prevButtonActionListener());
		controlButtonPanel.add(prevButton);
		nextbutton = new JButton(Lang.NEXT);
		nextbutton.addActionListener(nextButtonActionListener());
		controlButtonPanel.add(nextbutton);
		exitButton = new JButton(Lang.EXIT);
		exitButton.addActionListener(exitButtonActionListener());
		controlButtonPanel.add(exitButton);
		
		main.add(controlButtonPanel);
		
		this.add(main);
		
		this.pack();
		this.setVisible(true);
	}
	private void updatePartLabel() {
		partLabel.setText(customerStartIndex + " - " + Math.min(WaitingRoom.size(), (customerStartIndex + customerSeeSize)) + " of " + WaitingRoom.size());
	}
	private void updateButtons() {
		for(int i = 0; i < colums; i++) {
			personButtonPanel[i].removeAll();
			for(int j = customerStartIndex + (customerSeeSize / colums * i); j < Math.min((customerStartIndex + (customerSeeSize / colums * i) + (customerSeeSize / colums)), WaitingRoom.size()); j++) {
				makePersonButton(WaitingRoom.getCustomerBySpot(j), personButtonPanel[i]);
			}
		}
		updatePartLabel();
		this.validate();
		this.pack();
	}
	private void makePersonButton(Customer c, JPanel panel) {
		JButton btn = new JButton(c.get(DataType.NAME));
		btn.addActionListener(personButtonActionListener((UUID) c.getraw(DataType.UUID))); 
		panel.add(btn);
		personButtons.put((UUID) c.getraw(DataType.UUID), btn);
	}
	
	private ActionListener nextButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				customerStartIndex = Math.min((customerStartIndex + customerSeeSize), (WaitingRoom.lastindex() - customerSeeSize + 1));
				updateButtons();
			}
		};
	}
	private ActionListener prevButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				customerStartIndex = Math.max((customerStartIndex - customerSeeSize), 0);
				updateButtons();
			}
		};
	}
	private ActionListener personButtonActionListener(UUID uuid) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DataInputWindow(WaitingRoom.getCustomerByUUID(uuid));
			}
		};
	}
	private ActionListener exitButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new InfoWindow(Lang.ARE_YOU_SURE_MSG + Lang.EXIT, Lang.PROGRAM_NAME,
					new InfoWindowButton(Lang.YES, new Runnable() { @Override public void run() { Main.exit(); } }),
					new InfoWindowButton(Lang.NO, null));
			}
		};
	}
}
