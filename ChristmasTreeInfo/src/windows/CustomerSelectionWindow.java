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
import christmastreeinfo.WaitingRoom;
import customer.Customer;
import customer.DataType;

public class CustomerSelectionWindow extends JFrame {

	/**/ private static final long serialVersionUID = 9188929133770395180L;

	private JPanel main;
	
	private JPanel personButtonPanel;
	private HashMap<UUID, JButton> personButtons;
	
	private JLabel partLabel;
	
	private JPanel controlButtonPanel;
	private JButton nextbutton;
	private JButton prevButton;
	
	private int customerStartIndex;
	private int customerSeeSize = 8;
	
	public CustomerSelectionWindow() {
		this.setTitle("Select Person");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		personButtons = new HashMap<UUID, JButton>();
		customerStartIndex = 0;
		display();
	}
	
	private void display() {
		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
		partLabel = new JLabel();
		main.add(partLabel);
		updatePartLabel();
		
		personButtonPanel = new JPanel();
		personButtonPanel.setLayout(new BoxLayout(personButtonPanel, BoxLayout.Y_AXIS));
		main.add(personButtonPanel);
		
		updateButtons();
		
		controlButtonPanel = new JPanel();
		prevButton = new JButton(Lang.PREVIOUS);
		prevButton.addActionListener(prevButtonActionListener());
		controlButtonPanel.add(prevButton);
		nextbutton = new JButton(Lang.NEXT);
		nextbutton.addActionListener(nextButtonActionListener());
		controlButtonPanel.add(nextbutton);
		
		main.add(controlButtonPanel);
		
		this.add(main);
		
		this.pack();
		this.setVisible(true);
	}
	private void updatePartLabel() {
		partLabel.setText(customerStartIndex + " - " + Math.min(WaitingRoom.size(), (customerStartIndex + customerSeeSize)) + " of " + WaitingRoom.size());
	}
	private void updateButtons() {
		personButtonPanel.removeAll();
		for(int i = customerStartIndex; i < Math.min((customerStartIndex + customerSeeSize), WaitingRoom.size()); i++) {
			makePersonButton(WaitingRoom.getCustomerBySpot(i));
		}
		updatePartLabel();
		this.validate();
		this.pack();
	}
	private void makePersonButton(Customer c) {
		JButton btn = new JButton(c.get(DataType.NAME));
		btn.addActionListener(personButtonActionListener((UUID) c.getraw(DataType.UUID))); 
		personButtonPanel.add(btn);
		personButtons.put((UUID) c.getraw(DataType.UUID), btn);
	}
	
	private ActionListener nextButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if((customerStartIndex + customerSeeSize) < WaitingRoom.lastindex()) {
					customerStartIndex += customerSeeSize;
				}
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
				System.out.println(uuid);
				new DataInputWindow(WaitingRoom.getCustomerByUUID(uuid));
			}
		};
	}
}
