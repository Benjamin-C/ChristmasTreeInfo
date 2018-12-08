package christmastreeinfo;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class DataInputWindow extends JFrame {
	
	/**/ private static final long serialVersionUID = -8900248693643765822L;

	// Customer data
	Customer cst;
	//private String name = "Someone Important";
	
	//private String phone = "123-456-7890";
	//private String email = "example@example.com";
		
	private String dates[] = {"0", "1", "2", "3", "4", "5", "6", "7"};
	private String times[] = {"0", "1"};
	
	//private String address[] = {"123 St Wy Se" ,"City, State"};
	
	// JPanel stuffs
	private JPanel master = new JPanel();
	
	private JPanel datePanel;
	private JPanel dateSelectPanel;
	private JLabel dateLabel;
	private JRadioButton dateBox[];
	private ButtonGroup dateButtonGroup;
	private GridLayout dateLayout;
	
	private JPanel timesPanel;
	private JPanel timesSelectPanel;
	private JLabel timesLabel;
	private JRadioButton timesBox[];
	private ButtonGroup timesButtonGroup;
	private GridLayout timeLayout;
	
	private JPanel addressPanel;
	private JLabel addressLabel;
	private JPanel addressLayoutPanel;
	private JTextArea addressArea[];
	
	private JButton cancelButton;
	private JButton editButton;
	private JButton saveButton;
	private JButton sceduleButton;
	private JPanel buttonPanel;
	
	//Editing vars
	List<JTextArea> inputFields;
	
	HashMap<DataType, JTextArea> dataMap;
	
	public DataInputWindow(Customer c) {
		if(c != null) {
			cst = c;
		} else {
			cst = new Customer("John Doe");
		}
		this.setTitle(cst.get(DataType.NAME));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFields = new ArrayList<JTextArea>();
		dataMap = new HashMap<DataType, JTextArea>();
		display();
	}
	public DataInputWindow() {
		this(null);
	}
	
	public void display() {
		master = new JPanel();
		master.setLayout(new BoxLayout(master, BoxLayout.Y_AXIS));
		
		addTextZone(DataType.NAME);
		addTextZone(DataType.PHONE_NUMBER);
		addTextZone(DataType.EMAIL_ADDRESS);
		
		datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
		dateSelectPanel = new JPanel();
		dateLabel = new JLabel(Lang.PICKUP_DATE + ":");
		dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		dateBox = new JRadioButton[dates.length];
		int w = dates.length / 2;
		if((2*w) != dates.length) {
			w++;
		}
		dateLayout = new GridLayout(2, w);
		dateSelectPanel.setLayout(dateLayout);
		dateButtonGroup = new ButtonGroup();
		for(int i = 0; i < dates.length; i++) {
			dateBox[i] = new JRadioButton(dates[i]);
			dateBox[i].setText(dates[i]);
			dateSelectPanel.add(dateBox[i]);
			dateButtonGroup.add(dateBox[i]);
		}
		System.out.println(">" + master.hashCode());
		addToMaster(datePanel, dateLabel, dateSelectPanel);
		
		timesPanel = new JPanel();
		timesPanel.setLayout(new BoxLayout(timesPanel, BoxLayout.Y_AXIS));
		timesSelectPanel = new JPanel();
		timesLabel = new JLabel(Lang.PICKUP_TIME + ":");
		timesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timesPanel.add(timesLabel);
		timesBox = new JRadioButton[times.length];
		timeLayout = new GridLayout(1, times.length);
		timesSelectPanel.setLayout(timeLayout);
		timesButtonGroup = new ButtonGroup();
		for(int i = 0; i < times.length; i++) {
			timesBox[i] = new JRadioButton(times[i]);
			timesBox[i].setText(times[i]);
			timesSelectPanel.add(timesBox[i]);
			timesButtonGroup.add(timesBox[i]);
		}
		addToMaster(timesPanel, timesLabel, timesSelectPanel);
		
		addTextZone(DataType.ADDRESS);
		addTextZone(DataType.CITY);
//		addressPanel = new JPanel();
//		addressLabel = new JLabel(Lang.ADDRESS + ":"); 
//		addressArea = new JTextArea[2];
//		addressLayoutPanel = new JPanel();
//		addressLayoutPanel.setLayout(new BoxLayout(addressLayoutPanel, BoxLayout.Y_AXIS));
//		addressArea[0] = new JTextArea(address[0]);
//		inputFields.add(addressArea[0]);
//		addressArea[1] = new JTextArea(address[1]);
//		inputFields.add(addressArea[1]);
//		addressLayoutPanel.add(addressArea[0]);
//		addressLayoutPanel.add(addressArea[1]);
//		addToMaster(addressPanel, addressLabel, addressLayoutPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		cancelButton = new JButton(Lang.CANCEL);
		editButton = new JButton(Lang.EDIT);
		editButton.addActionListener(editButtonActionListener());
		saveButton = new JButton(Lang.SAVE);
		saveButton.addActionListener(saveButtonActionLisener());
		saveButton.setEnabled(false);
		sceduleButton = new JButton(Lang.SCEDULE);
		addToMaster(buttonPanel, cancelButton, editButton, saveButton, sceduleButton);
		
		setEditable(false);
		
		this.add(master);
		this.validate();
		this.pack();
		this.setVisible(true);
	}
	
	private void addTextZone(DataType type) {
		JLabel label = new JLabel(type.getText() + ":");
		JTextArea area = new JTextArea(cst.get(type));
		inputFields.add(area);
		dataMap.put(type, area);
		addToMaster(label, area);
	}
	
	private void setEditable(boolean editable) {
		for(JTextArea jta : inputFields) {
			jta.setEditable(editable);
		}
	}
	
	private void addToMaster(Component... parts) {
		addPanelToMaster(new JPanel(), parts);
	}
	private void addPanelToMaster(JPanel panel, Component... parts) {
		for(Component c : parts) {
			panel.add(c);
		}
		panel.setPreferredSize(panel.getPreferredSize());
		master.add(panel);
	}
	
	private ActionListener editButtonActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setEditable(true);
				saveButton.setEnabled(true);
				sceduleButton.setEnabled(false);
				editButton.setEnabled(false);
			}
		};
	}
	
	private ActionListener saveButtonActionLisener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setEditable(false);
				editButton.setEnabled(true);
				sceduleButton.setEnabled(true);
				saveButton.setEnabled(false);
			}
		};
	}
}
