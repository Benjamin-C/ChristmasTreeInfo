package christmastreeinfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CustomerSelectionWindow extends JFrame {

	/**/ private static final long serialVersionUID = 9188929133770395180L;

	private JPanel main;
	
	private JPanel personButtonPanel;
	private HashMap<UUID, JButton> personButtons;
	
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
		
		personButtonPanel = new JPanel();
		personButtonPanel.setLayout(new BoxLayout(personButtonPanel, BoxLayout.Y_AXIS));
		main.add(personButtonPanel);
		
		String pplText = "";
		int start = 0;
		int count = 8;
		
		for(int i = start; i < Math.min(count, WaitingRoom.size()); i++) {
			makePersonButton(WaitingRoom.getCustomerBySpot(i));
		}
		for(Customer c : WaitingRoom.getAllCustomers()) {
			pplText = pplText + "\n" + c.get(DataType.NAME);
		}
		
		this.add(main);
		
		this.pack();
		this.setVisible(true);
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
				// TODO Auto-generated method stub
				
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
}
