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
import christmastreeinfo.Main;
import christmastreeinfo.WaitingRoom;
import christmastreeinfo.Lobby;
import customer.Customer;
import customer.DataType;

public class CustomerSelectionWindow extends JFrame {

	/**/ private static final long serialVersionUID = 9188929133770395180L;

	private JPanel main;
	
	private JPanel personButtonPanel[];
	private HashMap<UUID, JButton> personButtons;
	private HashMap<UUID, JCheckBox> personCheckboxes;
	
	private JLabel partLabel;
	
	private JPanel controlButtonPanel;
	private JButton selectButton;
	private JButton nextbutton;
	private JButton prevButton;
	private JButton exitButton;
	private JButton goButton;
	private JButton deleteButton;
	
	private int customerStartIndex;
	private int customerSeeSize = 8;
	private int colums = 3;
	
	private boolean selectingMode;
	
	@SuppressWarnings("unused")
	private JFrame me;
	
	private WaitingRoom customers;
	
	public CustomerSelectionWindow() {
		this(new WaitingRoom(Lobby.getAllCustomers()));
	}
	public CustomerSelectionWindow(WaitingRoom customers) {
		me = this;
		if(customers == null) {
			customers = new WaitingRoom(Lobby.getAllCustomers());
		}
		this.customers = customers;
		
		this.setTitle("Select Person");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(getWindowListener());
		
		personButtons = new HashMap<UUID, JButton>();
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
		selectButton = new JButton(Lang.SELECT);
		selectButton.addActionListener(selectButtonActionListener());
		controlButtonPanel.add(selectButton);
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
		goButton.setEnabled(false);
		controlButtonPanel.add(goButton);
		deleteButton = new JButton(Lang.REMOVE);
		deleteButton.addActionListener(deleteButtonActionListener());
		deleteButton.setEnabled(false);
		controlButtonPanel.add(deleteButton);
		
		main.add(controlButtonPanel);
		
		this.add(main);
		
		this.pack();
		this.setVisible(true);
	}
	private void updatePartLabel() {
		partLabel.setText(customerStartIndex + " - " + Math.min(customers.size(), (customerStartIndex + customerSeeSize - 1)) + Lang.OF + (customers.size() - 1));
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
		if(selectingMode) {
			if(personCheckboxes.containsKey(c.getraw(DataType.UUID))) {
				panel.add(personCheckboxes.get(c.getraw(DataType.UUID)));
			} else {
				JCheckBox ckbx = new JCheckBox(c.get(DataType.NAME));
				panel.add(ckbx);
				personCheckboxes.put((UUID) c.getraw(DataType.UUID), ckbx);
			}
		} else {
			JButton btn = new JButton(c.get(DataType.NAME));
			btn.addActionListener(personButtonActionListener((UUID) c.getraw(DataType.UUID))); 
			panel.add(btn);
			personButtons.put((UUID) c.getraw(DataType.UUID), btn);
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
	private void startSelecting() {
		selectingMode = true;
		goButton.setEnabled(true);
		deleteButton.setEnabled(true);
		selectButton.setText(Lang.CANCEL);
		updateButtons();
	}
	private void stopSelecting() {
		selectingMode = false;
		goButton.setEnabled(false);
		deleteButton.setEnabled(false);
		selectButton.setText(Lang.SELECT);
		for (Map.Entry<UUID, JCheckBox> e : personCheckboxes.entrySet()) {
			e.getValue().setSelected(false);
		}
		updateButtons();
	}
	private ActionListener selectButtonActionListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(selectingMode) {
					stopSelecting();
				} else {
					startSelecting();
				}
			}
		};
	}
	private ActionListener prevButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				customerStartIndex = Math.max((customerStartIndex - customerSeeSize), 0);
				updateButtons();
		} };
	}
	private ActionListener personButtonActionListener(UUID uuid) {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				new DataInputWindow(Lobby.getCustomerByUUID(uuid));
				
		} };
	}
	private ActionListener exitButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { toCloseInfoWindow();
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
				stopSelecting();
		} };
	}
	private ActionListener deleteButtonActionListener() {
		return new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				for (Map.Entry<UUID, JCheckBox> e : personCheckboxes.entrySet()) {
				    if(e.getValue().isSelected()) {
				    	customers.remove(e.getKey());
				    }
				}
				stopSelecting();
		} };
	}
	private void toCloseInfoWindow() {
		new InfoWindow(Lang.ARE_YOU_SURE_MSG + Lang.EXIT.toLowerCase(), Lang.PROGRAM_NAME, this,
					new InfoWindowButton(Lang.YES, new Runnable() { @Override public void run() { Main.exit(); } }),
					new InfoWindowButton("Hard", new Runnable() { @Override public void run() { System.exit(1); } }),
					new InfoWindowButton("This", new Runnable() { @Override public void run() { me.dispose(); } }, true),
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
