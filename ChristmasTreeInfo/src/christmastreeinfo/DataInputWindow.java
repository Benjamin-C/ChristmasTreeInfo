package christmastreeinfo;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class DataInputWindow extends JFrame {
	
	/**/ private static final long serialVersionUID = -8900248693643765822L;

	// Customer data
	Customer cst;
		
	private String dates[] = {"0", "1", "2", "3", "4", "5", "6", "7"};
	private String times[] = {"0", "1"};
	
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
	
	private JButton cancelButton;
	private JButton editButton;
	private JButton saveButton;
	private JButton sceduleButton;
	private JPanel buttonPanel;
	
	//Editing vars	
	HashMap<DataType, JTextArea> dataMap;
	
	public DataInputWindow(Customer c, String[] dates, String[] times) {
		if(c != null) {
			cst = c;
		} else {
			cst = new Customer("John Doe");
		}
		this.setTitle(cst.get(DataType.NAME));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dataMap = new HashMap<DataType, JTextArea>();
		this.dates = dates;
		this.times = times;
		display();
	}
	public DataInputWindow(Customer c) {
		this(c, Lang.DEFAULT_DATES, Lang.DEFAULT_TIMES);
	}
	
	public DataInputWindow() {
		this(null);
	}

	public void display() {
		master = new JPanel();
		this.setTitle(cst.get(DataType.NAME));
		
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
		addPanelToMaster(datePanel, dateLabel, dateSelectPanel);
		
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
		addPanelToMaster(timesPanel, timesLabel, timesSelectPanel);
		
		addTextZone(DataType.ADDRESS);
		addTextZone(DataType.CITY);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		cancelButton = new JButton(Lang.CANCEL);
		cancelButton.addActionListener(cancelButtonActionLisener());
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
	
	public void update() {
		for (Map.Entry<DataType, JTextArea> e : dataMap.entrySet()) {
		    e.getValue().setText(cst.get(e.getKey()));
		}
	}
	private void addTextZone(DataType type) {
		JLabel label = new JLabel(type.getText() + ":");
		JTextArea area = new JTextArea(cst.get(type));
		dataMap.put(type, area);
		addToMaster(label, area);
	}
	
	private void setEditable(boolean editable) {
		for (Map.Entry<DataType, JTextArea> e : dataMap.entrySet()) {
		    e.getValue().setEditable(editable);
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
	
	private void startEditing() {
		setEditable(true);
				saveButton.setEnabled(true);
				sceduleButton.setEnabled(false);
				editButton.setEnabled(false);
	}
	private void endEditing() {
		setEditable(false);
		editButton.setEnabled(true);
		sceduleButton.setEnabled(true);
		saveButton.setEnabled(false);
	}
	private ActionListener editButtonActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startEditing();
			}
		};
	}
	
	private ActionListener saveButtonActionLisener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endEditing();
				for (Map.Entry<DataType, JTextArea> e : dataMap.entrySet()) {
				    cst.set(e.getKey(), e.getValue().getText());
				}
			}
		};
	}
	
	private ActionListener cancelButtonActionLisener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(saveButton.isEnabled()) {
					endEditing();
					update();
				} else {
					System.exit(0);
				}
			}
		};
	}
	
	public DataInputWindow clone() {
		return new DataInputWindow(cst, dates, times);
	}
}
