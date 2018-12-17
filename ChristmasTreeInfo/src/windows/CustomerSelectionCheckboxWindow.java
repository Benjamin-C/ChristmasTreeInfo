package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import christmastreeinfo.Lang;
import christmastreeinfo.Lobby;
import christmastreeinfo.Main;
import christmastreeinfo.WaitingRoom;
import customer.Customer;
import customer.DataType;

public class CustomerSelectionCheckboxWindow extends JFrame {

	/**/ private static final long serialVersionUID = 9188929133770395180L;

	private JPanel main;
	
	private JPanel personButtonPanel[];
	private HashMap<UUID, JCheckBox> personCheckboxes;
	
	private JLabel partLabel;
	
	private JPanel controlButtonPanel;
	private JButton nextbutton;
	private JButton prevButton;
	private JButton exitButton;
	private JButton goButton;
	
	private int customerStartIndex;
	private int customerSeeSize = 8;
	private int colums = 3;
	
	private JFrame me;
	
	private WaitingRoom customers;
	
	public CustomerSelectionCheckboxWindow(WaitingRoom customers) {
		if(customers == null) {
			customers = new WaitingRoom(Lobby.getAllCustomers());
		}
		this.customers = customers;
		me = this;
		this.setTitle("Select Person");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(getWindowListener());
		
		personCheckboxes = new HashMap<UUID, JCheckBox>();
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
		goButton = new JButton(Lang.GO);
		goButton.addActionListener(goButtonActionListener());
		controlButtonPanel.add(goButton);
		
		main.add(controlButtonPanel);
		
		this.add(main);
		
		this.pack();
		this.setVisible(true);
	}
	private void updatePartLabel() {
		partLabel.setText(customerStartIndex + " - " + Math.min(customers.size(), (customerStartIndex + customerSeeSize - 1)) + Lang.OF + customers.lastindex());
	}
	private void updateButtons() {
		for(int i = 0; i < colums; i++) {
			personButtonPanel[i].removeAll();
			for(int j = customerStartIndex + (customerSeeSize / colums * i); j < Math.min((customerStartIndex + (customerSeeSize / colums * i) + (customerSeeSize / colums)), customers.size()); j++) {
				makePersonButton(customers.getCustomerBySpot(j), personButtonPanel[i]);
			}
		}
		updatePartLabel();
		this.validate();
		this.pack();
	}
	private void makePersonButton(Customer c, JPanel panel) {
		if(personCheckboxes.containsKey(c.getraw(DataType.UUID))) {
			panel.add(personCheckboxes.get(c.getraw(DataType.UUID)));
		} else {
			JCheckBox ckbx = new JCheckBox(c.get(DataType.NAME));
			panel.add(ckbx);
			personCheckboxes.put((UUID) c.getraw(DataType.UUID), ckbx);
		}
	}
	
	private ActionListener nextButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if(customerStartIndex + customerSeeSize < customers.size()) {
					customerStartIndex = customerStartIndex + customerSeeSize;
				}
				updateButtons();
		} };
	}
	private ActionListener prevButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				customerStartIndex = Math.max((customerStartIndex - customerSeeSize), 0);
				updateButtons();
		} };
	}
	
	private ActionListener exitButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { me.dispose();
		} };
	}
	private ActionListener goButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				WaitingRoom r = new WaitingRoom();
				for (Map.Entry<UUID, JCheckBox> e : personCheckboxes.entrySet()) {
				    if(e.getValue().isSelected()) {
				    	r.add(customers.getCustomerByUUID(e.getKey()));
				    }
				}
				new CustomerSelectionWindow(r);
				me.dispose();
		} };
	}
	
	private void toCloseInfoWindow() {
		new InfoWindow(Lang.ARE_YOU_SURE_MSG + Lang.EXIT.toLowerCase(), Lang.PROGRAM_NAME, this,
					new InfoWindowButton(Lang.YES, new Runnable() { @Override public void run() { Main.exit(); } }),
					new InfoWindowButton("Hard", new Runnable() { @Override public void run() { System.exit(1); } }),
					new InfoWindowButton(Lang.NO, null, true));
	}
	private WindowListener getWindowListener() {
		return new WindowListener() {
			@Override public void windowOpened(WindowEvent arg0) { }
			@Override public void windowIconified(WindowEvent arg0) { }
			@Override public void windowDeiconified(WindowEvent arg0) { }
			@Override public void windowDeactivated(WindowEvent arg0) { }
			@Override public void windowClosing(WindowEvent arg0) { toCloseInfoWindow(); }
			@Override public void windowClosed(WindowEvent arg0) { }
			@Override public void windowActivated(WindowEvent arg0) { }
		};
	}
}
